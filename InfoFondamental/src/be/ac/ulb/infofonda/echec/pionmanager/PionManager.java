package be.ac.ulb.infofonda.echec.pionmanager;

import be.ac.ulb.infofonda.echec.NbrPions;
import be.ac.ulb.infofonda.echec.TypeProbleme;
import java.util.ArrayList;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

/**
 * Représente un manager de pion
 * 
 * @author Rémy
 */
public abstract class PionManager {
    
    private static final ArrayList<PionManager> allPion = new ArrayList<>();
    private static boolean DEBUG = false;
    
    private String _nom;
    protected int _index;
    protected final NbrPions _nbrPion;
    protected final int _tailleEchec;
    protected final char _symbole;
    protected final char _utf8Symbole;
    
    
    protected PionManager(final String nomPion, final int tailleEchec) {
        this(nomPion, NbrPions.getDefaultPion(), tailleEchec, '*', ' ');
    }
    
    protected PionManager(final String nomPion, final NbrPions nbrPion, 
            final int tailleEchec, final char symbole, final char utf8Symbole) {
        _nom = nomPion;
        _index = allPion.size();
        _nbrPion = nbrPion;
        _tailleEchec = tailleEchec; // Taille de l'échiquier
        
        _symbole = symbole;
        _utf8Symbole = utf8Symbole;
        
        printDebug("Manager: " + _nom + " (index: " + _index + ")");
        
        allPion.add(this);
    }
    
    /**
     * Permet de récupérer les cases accèssibles si un pion du type actuelle est
     * placé sur les positions passée en paramètres
     * 
     * @param currentLigne la ligne actuelle
     * @param currentColonne la colonne actuelle
     * @return un array d'Integer qui représente les coordonnées sur l'échiquier
     */
    public abstract ArrayList<Integer[]> getAccessibleCase(int currentLigne, 
            int currentColonne);
    
    public int getIndex() {
        return _index;
    }
    
    protected String getNom() {
        return _nom + "-" + _index;
    }
    
    protected NbrPions getNbrPion() {
        return _nbrPion;
    }
    
    protected String getSymbole(final boolean utf8) {
        return "" + (utf8 ? _utf8Symbole : _symbole);
    }
    
    
    /**
     * Permet de récupérer toutes les contraintes pour une case précise (en
     * fonction de l'objet actuelle)
     * 
     * @param model le model 
     * @param variables les variables que le programme peut modifier
     * @param ligne où se trouve la case que l'on observe
     * @param col où se trouve la case que l'on observe
     * @param domination True si c'est un problème de domination, False si 
     * c'est un problème d'indépendance
     */
    private Constraint getConstraints(final Model model, final IntVar[][] variables, 
            final int ligne, final int col, final TypeProbleme typeProbleme) {        
        Constraint res = null;
        final ArrayList<Constraint> allConstraint = new ArrayList<>();
        
        for(final Integer[] caseAccessible : getAccessibleCase(ligne, col)) {
            final int ligneDep = caseAccessible[0];
            final int colDep = caseAccessible[1];
            
            allConstraint.add(getConstraintCaseAttackToAnother(model, variables, ligne, 
                    col, ligneDep, colDep, typeProbleme));
        }
        
        if(!allConstraint.isEmpty()) {
            final Constraint[] arrayAllConstraint = allConstraint.toArray(new Constraint[]{});
            if(typeProbleme.isOrOperation()) {
                res = model.or(arrayAllConstraint);
            } else {
                res = model.and(arrayAllConstraint);
            }
        }
        
        return res;
    }
    
    /**
     * Permet de récupérer les contraintes tel que la case (ligne, col) est attaqué
     * par la case (ligneDep, colDep)
     * 
     * @param model le modèle
     * @param variables les variables que le programme peut modifier
     * @param ligne la ligne que l'on est entrain de regarder
     * @param col la colonne que l'on est entraint de regarder
     * @param ligneDep le déplacement potentiel sur la ligne 
     * @param colDep le déplacement potentiel sur la colonne
     * @param typeProbleme permet de savoir le problème que l'on veut modéliser
     * @return la contrainte
     */
    protected Constraint getConstraintCaseAttackToAnother(final Model model, 
            final IntVar[][] variables, final int ligne, final int col, 
            final int ligneDep, final int colDep, final TypeProbleme typeProbleme) {
        
        final int pieceIndex = getIndex();
        
        Constraint contrainte = null;
        if(typeProbleme.equals(TypeProbleme.DOMINATION)) {
            printDebug("[super] (" + ligneDep + ", " + colDep + ") = " + pieceIndex);
            contrainte = model.arithm(variables[ligneDep][colDep], "=", pieceIndex);
            
        } else if(typeProbleme.equals(TypeProbleme.INDEPENDANCE)) {
            final int pieceVide = VideManager.getInstance().getIndex();
            
            printDebug("[super] (" + ligneDep + ", " + colDep + ") = " + pieceVide);
            contrainte = model.arithm(variables[ligneDep][colDep], "=", pieceVide);
            
            printDebug("[super] (" + ligne + ", " + col + ") = " + pieceIndex);
            contrainte = model.and(contrainte, model.arithm(variables[ligne][col], "=", pieceIndex));
        }
        
        return contrainte;
    }
    
    public void applyContraintNbrPion(final Model model, final IntVar[][] variables) {
        if(_nbrPion.isPositiveNumber()) {
            final IntVar intVar = model.intVar(_nbrPion.getNombre());
            model.among(intVar, convertDim2ToDim1(variables), new int[]{getIndex()}).post();
        }
    }
    
    protected void printDebugRestult(final ArrayList<Integer[]> result, 
            final int currentLigne, final int currentColonne) {
        System.out.println("Déplacement (" + getNom() + ") pour " + currentLigne + 
                ", " + currentColonne);
        
        String strRes = "";
        for(final Integer[] val : result) {
            strRes += "(" + val[0] + ", " + val[1] + ") ";
        }
        System.out.println(strRes);
    }
    
    
    //////////////////////////////// STATIC ////////////////////////////////
    
    // PROTECTED
    protected static IntVar[] convertDim2ToDim1(final IntVar[][] variables) {
        final ArrayList<IntVar> allVar = new ArrayList<>();
        for(final IntVar[] ligneVar : variables) {
            for(final IntVar var : ligneVar) {
                allVar.add(var);
            }
        }
        return allVar.toArray(new IntVar[]{});
    }
    
    protected static Integer[] getCoord(final int ligne, final int col) {
        final Integer[] tempRes = new Integer[2];
        tempRes[0] = ligne;
        tempRes[1] = col;
        return tempRes;
    }
    
    protected static void printDebug(final String message) {
        if(DEBUG) {
            System.out.println("[DEBUG] " + message);
        }
    }
    
    // PUBLIC 
    public static int getNbrPionDomaine() {
        return allPion.size();
    }
    
    public static String pionIndex2String(final int index, final boolean utf8) {
        final PionManager pion = allPion.get(index);
        return (pion != null) ? pion.getSymbole(utf8) : " ";
    }
    
    public static void initAllManager(final NbrPions nbrFou, final NbrPions nbrCavalier, 
            final NbrPions nbrTour, final int tailleEchec) {
        VideManager.getInstance();
        new FouManager(nbrFou, tailleEchec);
        new CavalierManager(nbrCavalier, tailleEchec);
        new TourManager(nbrTour, tailleEchec);
    }
    
    public static void applyAllConstraints(final Model model, final IntVar[][] variables,
            final TypeProbleme typeProbleme) {
        
        for(int ligne = 0; ligne < variables.length; ++ligne) {
            for(int col = 0; col < variables[ligne].length; ++col) {
                printDebug("Contrainte pour (" + ligne + ", " + col + ")");
                
                final ArrayList<Constraint> allContrainte = new ArrayList<>();
                for(final PionManager pion : allPion) {
                    // Get all constraintes for a specific position to a specific
                    // move.
                    final Constraint pionContrainte = pion.getConstraints(model, 
                            variables, ligne, col, typeProbleme);
                    
                    if(pionContrainte != null) {
                        allContrainte.add(pionContrainte);
                    } else {
                        System.err.println("Aucune contrainte pour le pion " + 
                                pion.getNom() + " en coordonnée: "
                                + "(" + ligne + ", " + col + ")");
                    }
                    
                }
                
                if(!allContrainte.isEmpty()) {
                    model.or(allContrainte.toArray(new Constraint[]{})).post();
                }
            }
        }
    }
    
    public static void applyAllContraintNbrPion(final Model model, final IntVar[][] variables) {
        for(final PionManager pion : allPion) {
            pion.applyContraintNbrPion(model, variables);
        }
    }
    
    /**
     * Permet de récupérer la variable a optimiser
     * 
     * @param model le modèle sur lequel on va devoir faire des optimisations
     * @param tailleEchec la taille de l'échiquier
     * @param variables la liste des variables où l'on devra mettre une condition
     * @return la variable que le solver doit optimiser
     */
    public static IntVar getOptimiseVar(final Model model, final int tailleEchec, 
            final IntVar[][] variables) {
        
        final ArrayList<Integer> optimiseIndex = new ArrayList<>();
        for(final PionManager pionManager : allPion) {
            if(pionManager.getNbrPion().isOptimisation()) {
                optimiseIndex.add(pionManager.getIndex());
            }
        }
        
        IntVar result = null;
        if(!optimiseIndex.isEmpty()) {
            
            int[] allIndex = new int[optimiseIndex.size()];
            int i = 0;
            for(final int index : optimiseIndex) {
                allIndex[i] = index;
                ++i;
            }
            
            result = model.intVar("Optimisation", 0, (int) Math.pow(tailleEchec, 2));
            final IntVar[] allVar = convertDim2ToDim1(variables);

            model.among(result, allVar, allIndex).post();
        }
        
        return result;
    }
    
}

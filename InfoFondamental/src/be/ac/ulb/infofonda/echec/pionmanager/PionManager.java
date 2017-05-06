package be.ac.ulb.infofonda.echec.pionmanager;

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
    
    private static final ArrayList<PionManager> allPion = new ArrayList<PionManager>();
    
    private String _nom;
    protected int _index;
    protected final int _nbrPion;
    protected final int _tailleEchec;
    
    protected PionManager(String nomPion, int tailleEchec) {
        this(nomPion, -1, tailleEchec);
    }
    
    protected PionManager(String nomPion, int nbrPion, int tailleEchec) {
        _nom = nomPion;
        _index = allPion.size();
        _nbrPion = nbrPion;
        _tailleEchec = tailleEchec; // Taille de l'échiquier
        printDebug("Manager: " + _nom + " (index: " + _index + ")");
        
        allPion.add(this);
    }
    
    protected String getNom() {
        return _nom + "-" + _index;
    }
    
    public int getIndex() {
        return _index;
    }
    
    public abstract ArrayList<Integer[]> getAccessibleCase(int currentLigne, 
            int currentColonne);
    
    /**
     * Permet de récupérer toutes les contraintes pour une case précise (en
     * fonction de l'objet actuelle)
     * 
     * @param model le model 
     * @param variables les variables que le programme peut modifier
     * @param ligne où se trouve la case que l'on observe
     * @param col où se trouve la case que l'on observe
     */
    private Constraint getConstraints(Model model, IntVar[][] variables, int ligne, int col) {        
        Constraint res = null;
        ArrayList<Constraint> allConstraint = new ArrayList<>();

        for(Integer[] caseAccessible : getAccessibleCase(ligne, col)) {
            int ligneDep = caseAccessible[0];
            int colDep = caseAccessible[1];

            allConstraint.add(getConstraintToAttackFrom(model, variables, ligne, col, 
                    ligneDep, colDep));
        }
        
        if(!allConstraint.isEmpty()) {
            res = model.or(allConstraint.toArray(new Constraint[]{}));
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
     * @return la contrainte
     */
    protected Constraint getConstraintToAttackFrom(Model model, IntVar[][] variables,
            int ligne, int col, int ligneDep, int colDep) {
        return model.arithm(variables[ligneDep][colDep], "=", getIndex());
    }
    
    public void applyContraintNbrPion(Model model, IntVar[][] variables) {
        if(_nbrPion >= 0) {
            ArrayList<IntVar> allVar = new ArrayList<IntVar>();
            for(IntVar[] ligneVar : variables) {
                for(IntVar var : ligneVar) {
                    allVar.add(var);
                }
            }
            
            IntVar intVar = model.intVar(_nbrPion);
            model.among(intVar, allVar.toArray(new IntVar[]{}), new int[]{getIndex()}).post();
        }
    }
    
    
    ///////////// STATIC ///////////
    
    public static int getNbrPionDomaine() {
        return allPion.size();
    }
    
    public static String pionIndex2String(int index) {
        PionManager pion = allPion.get(index);
        return (pion != null) ? pion.getSymbole() : "";
    }
    
    protected static Integer[] getCoord(int ligne, int col) {
        Integer[] tempRes = new Integer[2];
        tempRes[0] = ligne;
        tempRes[1] = col;
        return tempRes;
    }
    
    public static void initAllManager(int nbrFou, int nbrCavalier, int nbrTour, 
            int tailleEchec) {
        VideManager.getInstance();
        new FouManager(nbrFou, tailleEchec);
        new CavalierManager(nbrCavalier, tailleEchec);
        new TourManager(nbrTour, tailleEchec);
    }
    
    public static void applyAllConstraints(Model model, IntVar[][] variables) {
        for(int ligne = 0; ligne < variables.length; ++ligne) {
            for(int col = 0; col < variables[ligne].length; ++col) {
                ArrayList<Constraint> allContrainte = new ArrayList<>();
                for(PionManager pion : allPion) {
                    Constraint pionContrainte = pion.getConstraints(model, variables, ligne, col);
                    if(pionContrainte != null) {
                        allContrainte.add(pionContrainte);
                    } else {
                        System.err.println("Aucune contrainte pour le pion " + pion.getNom());
                    }
                    
                }
                
                if(!allContrainte.isEmpty()) {
                    model.or(allContrainte.toArray(new Constraint[]{})).post();
                }
            }
        }
    }
    
    public static void applyAllContraintNbrPion(Model model, IntVar[][] variables) {
        for(PionManager pion : allPion) {
            pion.applyContraintNbrPion(model, variables);
        }
    }
    
    protected void printDebug(String message) {
//        System.out.println("[DEBUG] " + message);
    }

    private String getSymbole() {
        return getNom().substring(0, 1);
    }
    
    
}

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
     * Permet d'appliquer toutes les contraintes (pour que le pion actuelle
     * tel que le pion actuelle couvre un maximum de cases)
     * 
     * @param model le model 
     * @param variables les variables que le programme peut modifier
     */
    private void applyConstraints(Model model, IntVar[][] variables) {
        
        for(int ligne = 0; ligne < variables.length; ++ligne) {
            for(int col = 0; col < variables[ligne].length; ++col) {
                
                for(Integer[] caseAccessible : getAccessibleCase(ligne, col)) {
                    int ligneDep = caseAccessible[0];
                    int colDep = caseAccessible[1];
                    
                    getSpecificConstraints(model, variables, ligne, col, 
                            ligneDep, colDep).post();
                }
                
            }
        }
        
    }
    
    /**
     * Permet de récupérer les contraintes spécifique à une case et à un 
     * déplacement potentiel
     * 
     * @param model le modèle
     * @param variables les variables que le programme peut modifier
     * @param ligne la ligne que l'on est entrain de regarder
     * @param col la colonne que l'on est entraint de regarder
     * @param ligneDep le déplacement potentiel sur la ligne 
     * @param colDep le déplacement potentiel sur la colonne
     * @return la contrainte
     */
    protected Constraint getSpecificConstraints(Model model, IntVar[][] variables,
            int ligne, int col, int ligneDep, int colDep) {
        return model.arithm(variables[ligneDep][colDep], "=", getIndex());
    }
    
    public void applyContraintNbrPion(Model model, IntVar[][] variables) {
        if(_nbrPion > 0) {
            ArrayList<IntVar> allVar = new ArrayList<IntVar>();
            for(IntVar[] ligneVar : variables) {
                for(IntVar var : ligneVar) {
                    allVar.add(var);
                }
            }

            model.sum(allVar.toArray(new IntVar[]{}), "=", _nbrPion);
        }
    }
    
    
    ///////////// STATIC ///////////
    
    public static int getNbrPionDomaine() {
        return allPion.size();
    }
    
    public static String pionIndex2String(int index) {
        PionManager pion = allPion.get(index);
        return (pion != null) ? pion.getNom() : "";
    }
    
    protected static Integer[] getCoord(int ligne, int col) {
        Integer[] tempRes = new Integer[2];
        tempRes[0] = ligne;
        tempRes[1] = col;
        return tempRes;
    }
    
    public static void initAllManager(int nbrFou, int nbrCavalier, int nbrTour, 
            int tailleEchec) {
        new VideManager(tailleEchec);
        new FouManager(tailleEchec, nbrFou);
        new CavalierManager(tailleEchec, nbrCavalier);
        new TourManager(tailleEchec, nbrTour);
    }
    
    public static void applyAllConstraints(Model model, IntVar[][] variables) {
        for(PionManager pion : allPion) {
            pion.applyConstraints(model, variables);
        }
    }
    
    public static void applyAllContraintNbrPion(Model model, IntVar[][] variables) {
        for(PionManager pion : allPion) {
            pion.applyContraintNbrPion(model, variables);
        }
    }
    
}

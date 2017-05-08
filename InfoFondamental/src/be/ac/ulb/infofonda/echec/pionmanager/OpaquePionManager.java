package be.ac.ulb.infofonda.echec.pionmanager;

import java.util.ArrayList;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

/**
 * Représente un manager de pion qui ne peut pas en traverser d'autres
 * 
 * @author Rémy
 */
public abstract class OpaquePionManager extends PionManager {
    
    protected OpaquePionManager(String nomPion, int nbrPion, int tailleEchec) {
        super(nomPion, nbrPion, tailleEchec);
    }
    
    /**
     * Permet de récupérer toutes les cases qui doivent être vide pour que la 
     * case vide (currentLigne, currentColonne) est attaque par la case 
     * (currentDecalageLigne, currentDecalageColonne)
     * 
     * @param currentLigne la ligne de la case que l'on observe
     * @param currentColonne la colonne de la case que l'on observe
     * @param currentDecalageLigne le déclage de la ligne observée 
     *  (la où se trouve potentiellement le pion actuel)
     * @param currentDecalageColonne le décalage de la colonne observée
     *  (la où se trouve potentiellement le pion actuel)
     * @return la liste des cases qui doivent être vides
     */
    protected abstract ArrayList<Integer[]> getEmptyCase(int currentLigne, int currentColonne,
            int currentDecalageLigne, int currentDecalageColonne);
    
    
    /**
     * Permet de récupérer les contraintes tel que la case (ligne, col) est attaqué
     * par la case (ligneDep, colDep) et que les cases comprises entre ces deux point
     * sont bien des cases vides
     * 
     * @param model le modèle
     * @param variables les variables que le programme peut modifier
     * @param ligne la ligne que l'on est entrain de regarder
     * @param col la colonne que l'on est entraint de regarder
     * @param ligneDep le déplacement potentiel sur la ligne 
     * @param colDep le déplacement potentiel sur la colonne
     * @return la contrainte
     */
    @Override
    protected Constraint getConstraintToAttackFrom(Model model, IntVar[][] variables,
            int ligne, int col, int ligneDep, int colDep) {
        // Le resCase vérifie que la case "dep" est bien celle lié au manager actuelle
        Constraint resCase = super.getConstraintToAttackFrom(model, variables, ligne, 
                col, ligneDep, colDep);
        
        String strDebug = " AND ";
        for(Integer[] emptyCoord : getEmptyCase(ligne, col, ligneDep, colDep)) {
            Constraint newConstraint = model.arithm(
                    variables[emptyCoord[0]][emptyCoord[1]], "=", 
                    VideManager.getInstance().getIndex());
            strDebug += "(" + emptyCoord[0] + ", " + emptyCoord[1] + ") = " + VideManager.getInstance().getIndex() + " AND ";
            resCase = model.and(resCase, newConstraint);
        }
        printDebug("\t" + strDebug);
        
        return resCase;
    }
    
}

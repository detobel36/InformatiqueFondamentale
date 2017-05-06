package be.ac.ulb.infofonda.echec.pionmanager;

import java.util.ArrayList;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

/**
 *
 * @author Rémy
 */
class VideManager extends PionManager {
    
    private static VideManager instance = null;
    
    private VideManager() {
        super("", -1);
    }

    @Override
    public ArrayList<Integer[]> getAccessibleCase(int currentLigne, int currentColonne) {
        // Une case vide n'a pas de case accèssible
        return new ArrayList<>();
    }
    
    @Override
    protected Constraint getSpecificConstraints(Model model, IntVar[][] variables,
            int ligne, int col, int ligneDep, int colDep) {
        // Aucune contrainte dû au fait qu'il y ai une case vide
        return null;
    }
    
    
    public static VideManager getInstance() {
        if(instance == null) {
            instance = new VideManager();
        }
        return instance;
    }
    
}

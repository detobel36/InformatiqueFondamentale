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
        ArrayList<Integer[]> res = new ArrayList<>();
        res.add(getCoord(currentLigne, currentColonne));
        return res;
    }
    
    @Override
    protected Constraint getConstraintToAttackFrom(Model model, IntVar[][] variables,
            int ligne, int col, int ligneDep, int colDep) {
        // la case actuelle doit être différente du vide
        Constraint containte = model.arithm(variables[ligne][col], "!=", getIndex());
        return containte;
    }
    
    public static VideManager getInstance() {
        if(instance == null) {
            instance = new VideManager();
        }
        return instance;
    }
    
}

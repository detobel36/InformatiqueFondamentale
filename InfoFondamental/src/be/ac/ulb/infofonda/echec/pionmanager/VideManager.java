package be.ac.ulb.infofonda.echec.pionmanager;

import be.ac.ulb.infofonda.echec.TypeProbleme;
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
    public ArrayList<Integer[]> getAccessibleCase(final int currentLigne, final int currentColonne) {
        final ArrayList<Integer[]> res = new ArrayList<>();
        res.add(getCoord(currentLigne, currentColonne));
        return res;
    }
    
    @Override
    protected Constraint getConstraintCaseAttackToAnother(final Model model, 
            final IntVar[][] variables, final int ligne, final int col, 
            final int ligneDep, final int colDep, final TypeProbleme typeProbleme) {
        Constraint containte;
        if(typeProbleme.equals(TypeProbleme.DOMINATION)) {
            // la case actuelle doit être différente du vide
            containte = model.arithm(variables[ligne][col], "!=", getIndex());
        } else {
            containte = model.arithm(variables[ligne][col], "=", getIndex());
        }
        return containte;
    }
    
    public static VideManager getInstance() {
        if(instance == null) {
            instance = new VideManager();
        }
        return instance;
    }
    
}

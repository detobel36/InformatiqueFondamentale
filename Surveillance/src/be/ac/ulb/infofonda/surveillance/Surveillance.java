package be.ac.ulb.infofonda.surveillance;

import be.ac.ulb.infofonda.surveillance.cases.CaseManager;
import java.util.ArrayList;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;


/**
 * Permet de résoudre un problème de surveillance
 * 
 * @author Detobel
 */
public class Surveillance {
    
    private final Model _model;
    private final int _horizontal;
    private final int _vertical;
    private IntVar[][] _variables;
    
    public Surveillance(final int horizontal, final int vertical, 
            final ArrayList<Integer[]> listeObstacle) {
        _model = new Model("Surveillance");
        
        _vertical = vertical;
        _horizontal = horizontal;
        
        CaseManager.initAllManager();
        createVariables();
        CaseManager.applyObstracleConstraints(_model, _variables, listeObstacle);
        CaseManager.applyAllConstraints(_model, _variables);
        solveProblem();
    }
    
    private void createVariables() {
        _variables = _model.intVarMatrix(_vertical, _horizontal, 0, 
                CaseManager.getNbrCaseDomaine()-1);
    }
    
    
    /**
     * Resolv problem
     */
    private void solveProblem() {
        final Solver solver = _model.getSolver();
        int i = 0;
        
        final ArrayList<IntVar> allVar = new ArrayList<>();
        for(final IntVar[] ligneVar : _variables) {
            for(final IntVar var : ligneVar) {
                allVar.add(var);
            }
        }
        
//        final IntVar optimiseVar = PionManager.getOptimiseVar(_model, _tailleEchec, _variables);
//        if(optimiseVar != null) {
//            final List<Solution> allSolution = solver.findAllOptimalSolutions(optimiseVar, Model.MINIMIZE);
//            for(final Solution solution : allSolution) {
//                System.out.println("Solution: " + (++i));
//                viewResult(solution);
//                if(!allResult) {
//                    break;
//                }
//            }
//        }
        
        if(i == 0) {
            System.err.println("Aucune solution n'a été trouvée");
        }
    }
    
}

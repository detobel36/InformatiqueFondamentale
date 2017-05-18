package be.ac.ulb.infofonda.surveillance;

import be.ac.ulb.infofonda.surveillance.cases.CaseManager;
import java.util.ArrayList;
import java.util.List;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;


/**
 * Permet de résoudre un problème de surveillance
 * 
 * @author Detobel
 */
public class Surveillance {
    
    private final Model _model;
    private final int _colonne;
    private final int _ligne;
    private IntVar[][] _variables;
    
    
    
    public Surveillance(final int horizontal, final int vertical, 
            final ArrayList<Integer[]> listeObstacle, final boolean allResult,
            final boolean debug) {
        _model = new Model("Surveillance");
        
        _ligne = vertical;
        _colonne = horizontal;
        
        CaseManager.initAllManager(_ligne, _colonne);
        createVariables();
        CaseManager.applyObstracleConstraints(_model, _variables, listeObstacle);
        CaseManager.applyAllConstraints(_model, _variables, debug);
        solveProblem(allResult);
    }
    
    private void createVariables() {
        _variables = _model.intVarMatrix(_ligne, _colonne, 0, 
                CaseManager.getNbrCaseDomaine()-1);
    }
    
    
    /**
     * Resolv problem
     */
    private void solveProblem(final boolean allResult) {
        final Solver solver = _model.getSolver();
        int i = 0;
        
        final IntVar optimiseVar = CaseManager.getOptimiseVar(_model, _ligne, _colonne, _variables);
        if(optimiseVar != null) {
            final List<Solution> allSolution = solver.findAllOptimalSolutions(optimiseVar, Model.MINIMIZE);
            for(final Solution solution : allSolution) {
                System.out.println("Solution: " + (++i));
                viewResult(solution);
                if(!allResult) {
                    break;
                }
            }
        }
        
        if(i == 0) {
            System.err.println("Aucune solution n'a été trouvée");
        }
    }
    
    private void viewResult(final Solution solution) {
        for(int ligne = 0; ligne < _ligne; ++ligne) {
            String strLigne = "";
            
            for(int col = 0; col < _colonne; ++col) {
                int value;
                if(solution != null) { 
                    value = solution.getIntVal(_variables[ligne][col]); 
                } else {
                    value = _variables[ligne][col].getValue(); 
                }
                strLigne += CaseManager.caseIndex2String(value);
            }
            System.out.println(strLigne);
        }
        System.out.println("");
    }
    
}

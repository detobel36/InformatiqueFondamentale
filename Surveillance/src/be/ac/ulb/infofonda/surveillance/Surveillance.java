package be.ac.ulb.infofonda.surveillance;

import be.ac.ulb.infofonda.surveillance.cases.CaseManager;
import be.ac.ulb.infofonda.surveillance.cases.Obstacle;
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
    private int _colonne;
    private int _ligne;
    private IntVar[][] _variables;
    
    public Surveillance(final ArrayList<String> plan, final boolean allResult,
            final boolean debug) throws IllegalArgumentException {
        
        final ArrayList<Integer[]> listeObstacle;
        try {
            listeObstacle = readPlan(plan);
        } catch(ExceptionInInitializerError ex) {
            throw new IllegalArgumentException("Impossible de charger le plan "
                    + "(" + ex.getMessage() + ")");
        }
        
        _model = new Model("Surveillance");
        
        CaseManager.initAllManager(_ligne, _colonne);
        createVariables();
        CaseManager.applyObstracleConstraints(_model, _variables, listeObstacle);
        CaseManager.applyAllConstraints(_model, _variables, listeObstacle, debug);
        solveProblem(allResult);
    }
    
    /**
     * Permet de lire un plan contenu sous forme d'ArrayList.  Cette méthode définit
     * les attributs ligne, colonne et retourne la liste des obstacles
     * 
     * @param plan un ArrayList où un élément correspond à une ligne du plan
     * @return ArrayList d'Integer[] contenant les coordonnées des obstacles
     */
    private ArrayList<Integer[]> readPlan(final ArrayList<String> plan) 
            throws ExceptionInInitializerError {
        
        final ArrayList<Integer[]> result = new ArrayList<>();
        
        _colonne = -1;
        _ligne = plan.size();
        
        int currentLigne = 0;
        for(final String ligne : plan) {
            final char[] charOfLigne = ligne.toCharArray();
            
            if(_colonne == -1) {
                _colonne = charOfLigne.length;
            } else if(_colonne != charOfLigne.length) {
                throw new ExceptionInInitializerError("Le nombre de colonne n'est"
                        + " pas uniforme sur ce plan");
            }
            
            int currentCol = 0;
            for(final char elem : charOfLigne) {
                if(elem == Obstacle.getInstance().getCharSymbole()) {
                    result.add(CaseManager.getCoord(currentLigne, currentCol));
                }
                ++currentCol;
            }
            ++currentLigne;
        }
        
        return result;
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
            
            List<Solution> allSolution;
            if(allResult) {
               allSolution = solver.findAllOptimalSolutions(optimiseVar, Model.MINIMIZE);
            } else {
                final Solution solutionOptimal = solver.findOptimalSolution(optimiseVar, Model.MINIMIZE);
                allSolution = new ArrayList<>();
                allSolution.add(solutionOptimal);
            }
             
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

package be.ac.ulb.infofonda.surveillance;

import be.ac.ulb.infofonda.surveillance.cases.CaseManager;
import be.ac.ulb.infofonda.surveillance.cases.Obstacle;
import be.ac.ulb.infofonda.surveillance.utils.ProgramTimer;
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
    
    /**
     * Permet de lancer le programme de surveillance
     * 
     * @param plan le plan où se trouve chaque élément
     * @param allResult True s'il faut afficher <b>tous</b> les résultats
     * @param isTime True s'il faut afficher le temps d'exécution du programme
     * @param isFullTime True si on affiche le temps de manière réculière
     * @param utf8 True si l'affichage peut être fait en UTF8
     * @param debug True s'il faut afficher les messages de débug
     * 
     * @throws IllegalArgumentException exception en cas de refus des arguments
     */
    public Surveillance(final ArrayList<String> plan, final boolean allResult,
            final boolean isTime, final boolean isFullTime, final boolean utf8, 
            final boolean debug) throws IllegalArgumentException {
        
        final long startTime = System.currentTimeMillis();
        ProgramTimer programTimer = null;
        if(isFullTime) {
            programTimer = new ProgramTimer();
            programTimer.start();
        }
        
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
        solveProblem(allResult, utf8);
        
        if(programTimer != null) {
            programTimer.setStop();
        }
        
        if(isTime) {
            viewTotalTime(startTime);
        }
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
    private void solveProblem(final boolean allResult, final boolean utf8) {
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
                System.out.println("" + solution.getIntVal(optimiseVar));
                viewResult(solution, utf8);
                if(!allResult) {
                    break;
                }
            }
        }
        
        if(i == 0) {
            System.err.println("Aucune solution n'a été trouvée");
        }
    }
    
    private void viewResult(final Solution solution, final boolean utf8) {
        for(int ligne = 0; ligne < _ligne; ++ligne) {
            String strLigne = "";
            
            for(int col = 0; col < _colonne; ++col) {
                int value;
                if(solution != null) { 
                    value = solution.getIntVal(_variables[ligne][col]); 
                } else {
                    value = _variables[ligne][col].getValue(); 
                }
                strLigne += CaseManager.caseIndex2String(value, utf8);
            }
            System.out.println(strLigne);
        }
        System.out.println("");
    }
    
    /**
     * Permet de calculer et d'afficher la différence de temps entre l'heure
     * actuelle et l'heure passé en paramètre
     * 
     * @param startTime le temps de début que l'on veut compter
     */
    private void viewTotalTime(final Long startTime) {
        final Long difference = System.currentTimeMillis() - startTime;
        final long temps = difference/1000;
        
        final int sec = (int) (temps%60);
        final int min = (int) (temps/60)%60;
        String strTime = "";
        if(min > 0) {
            strTime += min + " min. ";
        }
        strTime += sec + " sec.";
        
        System.out.println("Le programme à mis: " + strTime + " pour s'exécuter (" + difference + " ms)");
    }
    
}

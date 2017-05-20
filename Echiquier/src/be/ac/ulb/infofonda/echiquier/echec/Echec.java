package be.ac.ulb.infofonda.echiquier.echec;

import be.ac.ulb.infofonda.echiquier.echec.pionmanager.PionManager;
import java.util.ArrayList;
import java.util.List;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

/**
 * Représente un problème d'un jeu d'échec
 * 
 * @author Remy
 */
public class Echec {
    
    private final Model _model;
    
    private final int _tailleEchec;
    private IntVar[][] _variables;
    private final boolean _utf8;
    private final boolean _debug;
    
    public Echec(final NbrPions nbrFou, final NbrPions nbrCavalier, final NbrPions nbrTour, 
            final int tailleEchec, final TypeProbleme typeProbleme, 
            final boolean allResults, final boolean isTime, final boolean utf8, 
            final boolean debug) {
        
        final long startTime = System.currentTimeMillis();
        _model = new Model("Echec");
        
        _tailleEchec = tailleEchec;
        _utf8 = utf8;
        _debug = debug;
        
        PionManager.initAllManager(nbrFou, nbrCavalier, nbrTour, _tailleEchec);
        createVariables();
        initConstraints(typeProbleme);
        solveProblem(allResults);
        
        if(isTime) {
            viewTotalTime(startTime);
        }
    }
    
    
    ////// Private //////
    
    /**
     * Resolv problem
     * 
     * @param allResult True if we will all the results
     */
    private void solveProblem(final boolean allResult) {
        final Solver solver = _model.getSolver();
        int i = 0;
        
        final IntVar optimiseVar = PionManager.getOptimiseVar(_model, _tailleEchec, _variables);
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
                viewResult(solution);
                if(!allResult) {
                    break;
                }
            }
            
        } else {
            while(solver.solve()) {
                System.out.println("Solution: " + (++i));
                viewResult(null);
                
                if(!allResult) {
                    break;
                }
            }
        }
        
        if(i == 0) {
            System.err.println("pas de solution");
        }
    }
    
    private void createVariables() {
        _variables = _model.intVarMatrix(_tailleEchec, _tailleEchec, 0, 
                PionManager.getNbrPionDomaine()-1);
        // Il peut prendre les valeurs 0 (vide), 1, 2, 3 {pion, fou, tour}
    }
    
    private void initConstraints(final TypeProbleme probleme) {
        PionManager.applyAllContraintNbrPion(_model, _variables);
        PionManager.applyAllConstraints(_model, _variables, probleme, _debug);
    }
    
    
    ///////////////// DISPLAY RESULTS /////////////////
    
    private void viewResult(final Solution solution) {
        for(int ligne = 0; ligne < _tailleEchec; ++ligne) {
            String strLigne = addBordureBegin(ligne);
            
            for(int col = 0; col < _tailleEchec; ++col) {
                int value;
                if(solution != null) { 
                    value = solution.getIntVal(_variables[ligne][col]); 
                } else {
                    value = _variables[ligne][col].getValue(); 
                }
                strLigne += PionManager.pionIndex2String(value, _utf8);
                strLigne += addBordureCentral(col);
            }
            strLigne += addBordureEnd(ligne);
            
            System.out.println(strLigne);
        }
        System.out.println("");
    }
    
    private String addBordureBegin(final int currentLigne) {
        String result = "";
        if(_utf8) {
            if(currentLigne == 0) {
                result = "┌─";
                for(int col = 0; col < _tailleEchec-1; ++col) {
                    result += "──┬─";
                }
                result += "──┐\n│ ";
                
            } else {
                result = "│ ";
            }
        }
        return result;
    }
    
    private String addBordureEnd(final int currentLigne) {
        String result = "";
        if(_utf8) {
            final int tailleMaximum = _tailleEchec-1;
            if(currentLigne == tailleMaximum) {
                result = " │\n└─";
                for(int col = 0; col < _tailleEchec-1; ++col) {
                    result += "──┴─";
                }
                result += "──┘";
                
            } else {
                result = " │\n├─";
                for(int col = 0; col < _tailleEchec-1; ++col) {
                    result += "──┼─";
                }
                result += "──┤";
            }
        }
        
        return result;
    }
    
    private String addBordureCentral(final int currentCol) {
        String result = "";
        if(_utf8 && currentCol < _tailleEchec-1) {
            result = " │ ";
        }
        return result;
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

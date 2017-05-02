package be.ac.ulb.infofonda.echec;

import be.ac.ulb.infofonda.echec.pionmanager.PionManager;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

/**
 *
 * @author Remy
 */
public class Echec {
    
    private final Model _model;
    
    private final int _tailleEchec;
    private IntVar[][] _variables;
    
    public Echec(int nbrTour, int nbrFou, int nbrCavalier, int tailleEchec) {
        _model = new Model("Echec");
        
        _tailleEchec = tailleEchec;
        
        PionManager.initAllManager(nbrTour, nbrFou, nbrCavalier, _tailleEchec);
        createVariables();
        initConstraintsDomination();
        solveProblem();
    }
    
    private void solveProblem() {
        // intVarMatrix
        
    }
    
    private void createVariables() {
        _variables = _model.intVarMatrix(_tailleEchec, _tailleEchec, 0, 
                PionManager.getNbrPionDomaine()-1);
        // Il peut prendre les valeurs 0 (vide), 1, 2, 3 {pion, fou, tour}
    }
    
    private void initConstraintsDomination() {
        PionManager.applyAllConstraints(_model, _variables);
        PionManager.applyAllContraintNbrPion(_model, _variables);
        
        // Pas utilisé... Uniquement pour backup :P
//                IntVar current = _variables[ligne][col];
//                _model.arithm(current, "=", videManager.getIndex()).post();
//                
//                for(Integer[] deplacement : fouManager.getAccessibleCase(ligne, col)) {
//                    int ligneDep = deplacement[0];
//                    int colDep = deplacement[1];
//                    if(ligneDep < _tailleEchec && colDep < _tailleEchec) { // TODO vérifier '<' ou '<='
//                        _model.or(_model.arithm(_variables[ligneDep][colDep], "=", fouManager.getIndex()));
//                    }
//                    
////                    BoolVar var = _model.arithm(_variables[0], "=", _variables[0]).reify();
////                    _model.arithm(var1, "=", var2);
//                }
//                
//            }
//        }
        
    }
    
    
}

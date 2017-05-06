package be.ac.ulb.infofonda.echec;

import be.ac.ulb.infofonda.echec.pionmanager.PionManager;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

/**
 *
 * @author Remy
 */
public class Echec {
    
    private final Model _model;
    
    private final int _tailleEchec;
    private IntVar[][] _variables;
    
    public Echec(int nbrFou, int nbrCavalier, int nbrTour, int tailleEchec) {
        _model = new Model("Echec");
        
        _tailleEchec = tailleEchec;
        
        PionManager.initAllManager(nbrFou, nbrCavalier, nbrTour, _tailleEchec);
        createVariables();
        initConstraintsDomination();
        solveProblem();
    }
    
    private void solveProblem() {
        Solver solver = _model.getSolver();
        solver.solve();
        
        System.out.println("Solution: " + solver.isSatisfied());
        viewResult();
    }
    
    private void viewResult() {
        System.out.println("-----------------------");
        for(int ligne = 0; ligne < _tailleEchec; ++ligne) {
            String strLigne = "";
            for(int col = 0; col < _tailleEchec; ++col) {
                int value = _variables[ligne][col].getValue();
                strLigne += PionManager.pionIndex2String(value);
            }
            System.out.println(strLigne);
        }
        System.out.println("-----------------------");
        
    }
    
    
    private void createVariables() {
        System.out.println("Nbr de pion : " + PionManager.getNbrPionDomaine());
        
        _variables = _model.intVarMatrix(_tailleEchec, _tailleEchec, 0, 
                PionManager.getNbrPionDomaine()-1);
        // Il peut prendre les valeurs 0 (vide), 1, 2, 3 {pion, fou, tour}
    }
    
    private void initConstraintsDomination() {
        PionManager.applyAllConstraints(_model, _variables); // TODO TEST
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

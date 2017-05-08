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
        int i = 0;
        while(solver.solve()) {
            System.out.println("Solution ! " + (++i));
            viewResult();
        }
        if(i == 0) {
            System.err.println("Aucune solution n'a été trouvée");
        }
        
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
    }
    
    
}

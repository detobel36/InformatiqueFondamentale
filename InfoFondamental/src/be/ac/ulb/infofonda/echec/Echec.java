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
    private boolean _utf8;
    
    public Echec(int nbrFou, int nbrCavalier, int nbrTour, int tailleEchec) {
        this(nbrFou, nbrCavalier, nbrTour, tailleEchec, false);
    }
        
    public Echec(int nbrFou, int nbrCavalier, int nbrTour, int tailleEchec, boolean
            utf8) {
        _model = new Model("Echec");
        
        _tailleEchec = tailleEchec;
        _utf8 = utf8;
        
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
            String strLigne = addBordureBegin(ligne);
            
            for(int col = 0; col < _tailleEchec; ++col) {
                int value = _variables[ligne][col].getValue();
                strLigne += PionManager.pionIndex2String(value, _utf8);
                strLigne += addBordureCentral(col);
            }
            strLigne += addBordureEnd(ligne);
            
            System.out.println(strLigne);
        }
        System.out.println("-----------------------");
    }
    
    private String addBordureBegin(int currentLigne) {
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
    
    private String addBordureEnd(int currentLigne) {
        String result = "";
        if(_utf8) {
            int tailleMaximum = _tailleEchec-1;
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
    
    private String addBordureCentral(int currentCol) {
        String result = "";
        if(_utf8 && currentCol < _tailleEchec-1) {
            result = " │ ";
        }
        return result;
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

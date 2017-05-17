package be.ac.ulb.infofonda.echec;

import be.ac.ulb.infofonda.echec.pionmanager.PionManager;
import org.chocosolver.solver.Model;
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
    private boolean _utf8;
    
    
    public Echec(final int nbrFou, final int nbrCavalier, final int nbrTour, 
            final int tailleEchec, final TypeProbleme typeProbleme) {
        this(nbrFou, nbrCavalier, nbrTour, tailleEchec, typeProbleme, false);
    }
        
    public Echec(final int nbrFou, final int nbrCavalier, final int nbrTour, 
            final int tailleEchec, final TypeProbleme typeProbleme, final boolean utf8) {
        _model = new Model("Echec");
        
        _tailleEchec = tailleEchec;
        _utf8 = utf8;
        
        PionManager.initAllManager(nbrFou, nbrCavalier, nbrTour, _tailleEchec);
        createVariables();
        initConstraints(typeProbleme);
        solveProblem();
    }
    
    
    ////// Private //////
    
    private void solveProblem() {
        final Solver solver = _model.getSolver();
        int i = 0;
        while(solver.solve()) {
            System.out.println("Solution ! " + (++i));
            viewResult();
        }
        if(i == 0) {
            System.err.println("Aucune solution n'a été trouvée");
        }
        
    }
    
    private void createVariables() {
        System.out.println("Nbr de pion : " + PionManager.getNbrPionDomaine());
        
        _variables = _model.intVarMatrix(_tailleEchec, _tailleEchec, 0, 
                PionManager.getNbrPionDomaine()-1);
        // Il peut prendre les valeurs 0 (vide), 1, 2, 3 {pion, fou, tour}
    }
    
    private void initConstraints(final TypeProbleme probleme) {
        PionManager.applyAllContraintNbrPion(_model, _variables);
        PionManager.applyAllConstraints(_model, _variables, probleme);
    }
    
    
    ///////////////// DISPLAY RESULTS /////////////////
    
    private void viewResult() {
        System.out.println("-----------------------");
        for(int ligne = 0; ligne < _tailleEchec; ++ligne) {
            String strLigne = addBordureBegin(ligne);
            
            for(int col = 0; col < _tailleEchec; ++col) {
                final int value = _variables[ligne][col].getValue();
                strLigne += PionManager.pionIndex2String(value, _utf8);
                strLigne += addBordureCentral(col);
            }
            strLigne += addBordureEnd(ligne);
            
            System.out.println(strLigne);
        }
        System.out.println("-----------------------");
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
    
}

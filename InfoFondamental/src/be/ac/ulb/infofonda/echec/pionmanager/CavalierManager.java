package be.ac.ulb.infofonda.echec.pionmanager;

import java.util.ArrayList;

/**
 *
 * @author Rémy
 */
public class CavalierManager extends OpaquePionManager {
    
    private static int globalIndex = 0; // Permet d'identifier chaque cavalier de manière unique
    
    private static String NOM = "Cavalier";
    
    public CavalierManager(int nbrCavalier, int tailleEchec) {
        super(NOM, nbrCavalier, tailleEchec);
    }

    @Override
    public ArrayList<Integer[]> getAccessibleCase(int currentLigne, int currentColonne) {
        // TODO
        return new ArrayList<Integer[]>();
    }

    @Override
    protected ArrayList<Integer[]> getEmptyCase(int currentLigne, int currentColonne, int currentDecalageLigne, int currentDecalageColonne) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

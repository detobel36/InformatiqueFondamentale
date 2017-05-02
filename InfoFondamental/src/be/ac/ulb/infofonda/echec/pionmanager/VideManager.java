package be.ac.ulb.infofonda.echec.pionmanager;

import java.util.ArrayList;

/**
 *
 * @author Rémy
 */
class VideManager extends PionManager {

    public VideManager(int tailleEchec) {
        super("", tailleEchec);
    }

    @Override
    /**
     * Une case vide n'a pas de case accèssible
     */
    public ArrayList<Integer[]> getAccessibleCase(int currentLigne, int currentColonne) {
//        ArrayList<Integer[]> res = ;
//        res.add(getCoord(currentLigne, currentColonne));
        return new ArrayList<Integer[]>();
    }
    
}

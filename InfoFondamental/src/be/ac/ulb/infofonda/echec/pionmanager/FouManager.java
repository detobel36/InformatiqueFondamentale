package be.ac.ulb.infofonda.echec.pionmanager;

import java.util.ArrayList;

/**
 *
 * @author Rémy
 */
public class FouManager extends OpaquePionManager {

    private static final String NOM = "Fou";
    private static final boolean DEBUG_DEPLACEMENT = false;
    
    public FouManager(int nbrFou, int tailleEchec) {
        super(NOM, nbrFou, tailleEchec);
    }
    
    @Override
    public ArrayList<Integer[]> getAccessibleCase(int currentLigne, int currentColonne) {
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();
        
        // TODO vérifier que l'on ne prend pas trop de nombre et si l'on ne peut
        // pas réduire
        int maxValue = Math.max(currentLigne, currentColonne);
        for(int deplacement = -_tailleEchec; deplacement < _tailleEchec; ++deplacement) {
            Integer[] depl = getDeplacement(currentLigne, currentColonne, true, deplacement);
            if(depl != null) {
                result.add(depl);
            }
        
            depl = getDeplacement(currentLigne, currentColonne, false, deplacement);
            if(depl != null) {
                result.add(depl);
            }
        }
        
        if(DEBUG_DEPLACEMENT) {
            printDebugRestult(result, currentLigne, currentColonne);
        }
        
        return result;
    }
    
    private Integer[] getDeplacement(int currentLigne, int currentCol, boolean inverse,
            int deplacement) {
        Integer[] res = null;
        int ligne = currentLigne + deplacement;
        int col;
        if(inverse) {
            col = currentCol + deplacement;
        } else {
            col = currentCol - deplacement;
        }

        // Si les lignes sont valides
        if(ligne >= 0 && col >= 0 && ligne < _tailleEchec && col < _tailleEchec &&
                (ligne != currentLigne && col != currentCol)) {
            res = getCoord(ligne, col);
        }
        return res;
    }
    
    /**
     * Permet de récupérer toutes les cases qui doivent être vide pour que la 
     * case vide (currentLigne, currentColonne) est attaque par la case 
     * (currentDecalageLigne, currentDecalageColonne)
     * 
     * @param currentLigne la ligne de la case que l'on observe
     * @param currentColonne la colonne de la case que l'on observe
     * @param currentDecalageLigne le déclage de la ligne observée 
     *  (la où se trouve potentiellement le pion actuel)
     * @param currentDecalageColonne le décalage de la colonne observée
     *  (la où se trouve potentiellement le pion actuel)
     * @return la liste des cases qui doivent être vides
     */
    @Override
    protected ArrayList<Integer[]> getEmptyCase(int currentLigne, 
            int currentColonne, int currentDecalageLigne, int currentDecalageColonne) {
        ArrayList<Integer[]> res = new ArrayList<>();
        
        int differenceLigne = currentDecalageLigne - currentLigne;
        int differenceCol = currentDecalageColonne - currentColonne;
        
        int signeLigne = 1;
        int signeCol = 1;
        
        if(differenceLigne < 0) {
            signeLigne = -1;
        }
        
        if(differenceCol < 0) {
            signeCol = -1;
        }
        
        int maxDiff = Math.max(Math.abs(differenceLigne), Math.abs(differenceCol));
        
        for(int deplacement = 0; deplacement < maxDiff; ++deplacement) {
            int emptyLigne = currentLigne + signeLigne * deplacement;
            int emptyCol = currentColonne + signeCol * deplacement;
            res.add(getCoord(emptyLigne, emptyCol));
        }
        
        return res;
    }

}

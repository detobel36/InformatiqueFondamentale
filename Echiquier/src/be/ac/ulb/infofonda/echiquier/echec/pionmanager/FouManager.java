package be.ac.ulb.infofonda.echiquier.echec.pionmanager;

import be.ac.ulb.infofonda.echiquier.echec.NbrPions;
import java.util.ArrayList;

/**
 *
 * @author Rémy
 */
public class FouManager extends OpaquePionManager {

    private static final boolean DEBUG_DEPLACEMENT = false;
    
    public FouManager(final NbrPions nbrFou, final int tailleEchec) {
        super("Fou", nbrFou, tailleEchec, 'F', '♝');
    }
    
    @Override
    public ArrayList<Integer[]> getAccessibleCase(final int currentLigne, 
            final int currentColonne) {
        final ArrayList<Integer[]> result = new ArrayList<>();
        
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
    
    private Integer[] getDeplacement(final int currentLigne, final int currentCol, 
            final boolean inverse, final int deplacement) {
        
        Integer[] res = null;
        final int ligne = currentLigne + deplacement;
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
    protected ArrayList<Integer[]> getEmptyCase(final int currentLigne, 
            final int currentColonne, final int currentDecalageLigne, 
            final int currentDecalageColonne) {
        final ArrayList<Integer[]> res = new ArrayList<>();
        
        final int differenceLigne = currentDecalageLigne - currentLigne;
        final int differenceCol = currentDecalageColonne - currentColonne;
        
        int signeLigne = 1;
        int signeCol = 1;
        
        if(differenceLigne < 0) {
            signeLigne = -1;
        }
        
        if(differenceCol < 0) {
            signeCol = -1;
        }
        
        final int maxDiff = Math.max(Math.abs(differenceLigne), Math.abs(differenceCol));
        
        for(int deplacement = 0; deplacement < maxDiff; ++deplacement) {
            final int emptyLigne = currentLigne + signeLigne * deplacement;
            final int emptyCol = currentColonne + signeCol * deplacement;
            res.add(getCoord(emptyLigne, emptyCol));
        }
//        
//        System.out.println("Cases vide pour le déplacement de (" + currentLigne + 
//                ", " + currentColonne + ") et (" + currentDecalageLigne + ", " + 
//                currentDecalageColonne + ") ");
//        String strRes = "";
//        for(Integer[] dep : res) {
//            strRes += "(" + dep[0] + ", " + dep[1] + ") ";
//        }
//        System.out.println(strRes);
//        
        
        return res;
    }

}

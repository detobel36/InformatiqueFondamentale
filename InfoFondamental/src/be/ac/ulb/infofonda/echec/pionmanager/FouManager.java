package be.ac.ulb.infofonda.echec.pionmanager;

import java.util.ArrayList;

/**
 *
 * @author Rémy
 */
public class FouManager extends OpaquePionManager {

    private static final String NOM = "Fou";
    
    
    public FouManager(int nbrFou, int tailleEchec) {
        super(NOM, nbrFou, tailleEchec);
    }
    
    @Override
    public ArrayList<Integer[]> getAccessibleCase(int currentLigne, int currentColonne) {
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();
        
        int maxValue = Math.max(currentLigne, currentColonne);
        for(int deplacement = -maxValue; deplacement < _tailleEchec-maxValue; ++deplacement) {
            int ligne = currentLigne + deplacement;
            int col = currentColonne + deplacement;
            
            // Si les lignes sont valides
            if(ligne >= 0 && col >= 0 && ligne < _tailleEchec && col < _tailleEchec) {
                result.add(getCoord(ligne, col));
            }
        }
        
        return result;
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

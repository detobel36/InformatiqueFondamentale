package be.ac.ulb.infofonda.surveillance.cases;

import be.ac.ulb.infofonda.surveillance.Direction;
import java.util.ArrayList;

/**
 *
 * @author Detobel
 */
public class Capteur extends CaseManager {
    
    private final Direction _direction;
    
    public Capteur(final Direction direction, final int maxLigne, final int maxCol) {
        super(direction.getSymbole(), maxLigne, maxCol, true);
        _direction = direction;
    }

    @Override
    protected ArrayList<Integer[]> getAccessibleCase(final int ligne, final int col) {
        
        final ArrayList<Integer[]> result = new ArrayList<>();
        int tailleMaximum;
        int operation;
        boolean isForLigne;
        
        switch(_direction) {
            
            case NORD:
                tailleMaximum = _maxLigne;
                isForLigne = true;
                operation = -1;
                break;
            
            case SUD:
                tailleMaximum = _maxLigne;
                isForLigne = true;
                operation = 1;
                break;
                
            case EST:
                tailleMaximum = _maxColonne;
                isForLigne = false;
                operation = 1;
                break;
                
            case OUEST:
                tailleMaximum = _maxColonne;
                isForLigne = false;
                operation = -1;
                break;
                
            default:
                throw new InternalError("Les capteurs doivent avoir une direction valide !");
                
        }
        
        
        for(int i = 0; i < tailleMaximum; ++i) {
            int newLigne = ligne;
            int newCol = col;
            
            if(isForLigne) {
                newLigne += operation * i;
            } else {
                newCol += operation * i;
            }
            
            if(newLigne >= tailleMaximum || newCol >= tailleMaximum) {
                break;
                
            } else if(newLigne >= 0 && newCol >= 0) { 
                System.out.println("I: " + i + "  (" + newLigne + ", " + newCol + ")");
                
                result.add(getCoord(newLigne, newCol));
            }
        }
        
        return result;
    }

    @Override
    protected ArrayList<Integer[]> getEmptyCase(final int currentLigne, 
            final int currentColonne, final int currentDecalageLigne, 
            final int currentDecalageColonne) {
        
        final ArrayList<Integer[]> result = new ArrayList<>();
        
        // Si on bouge uniquement de colonne
        if(currentLigne == currentDecalageLigne) {
            final int minCol = Math.min(currentColonne, currentDecalageColonne);
            final int maxCol = Math.max(currentColonne, currentDecalageColonne);
            
            for(int col = minCol; col < maxCol; ++col) {
                if(col != currentDecalageColonne) {
                    result.add(getCoord(currentLigne, col));
                }
            }
            
        } else if(currentColonne == currentDecalageColonne) {
            final int minLigne = Math.min(currentLigne, currentDecalageLigne);
            final int maxLigne = Math.max(currentLigne, currentDecalageLigne);
            
            for(int ligne = minLigne; ligne < maxLigne; ++ligne) {
                if(ligne != currentDecalageLigne) {
                    result.add(getCoord(ligne, currentColonne));
                }
            }
            
        } else {
            System.err.println("Error ! Impossible de déterminer les cases vides "
                    + "(de " + currentLigne + ", " + currentColonne + " à " + 
                    currentDecalageLigne + ", " + currentDecalageColonne + ")");
        }
        
        return result;
    }
    
    
    
}

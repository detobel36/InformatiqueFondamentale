package be.ac.ulb.infofonda.surveillance.cases;

import be.ac.ulb.infofonda.surveillance.Direction;
import java.util.ArrayList;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

/**
 * Chaque case représente une partie du plan
 * 
 * @author Rémy
 */
public abstract class CaseManager {
    
    private static final ArrayList<CaseManager> allCases = new ArrayList<>();
    private static boolean DEBUG = true;
    
    protected final char _symbole;
    protected int _index;
    
    protected CaseManager(final char symbole) {
        _symbole = symbole;
        _index = allCases.size();
        
        printDebug("Manager: " + symbole + " (index: " + _index + ")");
        
        allCases.add(this);
    }
    
    public char getSymbole() {
        return _symbole;
    }
    
    protected int getIndex() {
        return _index;
    }
    
    protected abstract ArrayList<Integer[]> getAccessibleCase(int ligne, int col);
    
    protected abstract ArrayList<Integer[]> getEmptyCase(int ligne, int col, 
            int ligneAcc, int colAcc);
    
    protected Constraint getConstraintCanSee(final Model model, final IntVar[][] variables,
            final int ligne, final int col, final int ligneAcc, final int colAcc) {
        
        final int pieceIndex = getIndex();
        Constraint resCase = model.arithm(variables[ligneAcc][colAcc], "=", pieceIndex);
        
        for(final Integer[] emptyCoord : getEmptyCase(ligne, col, ligneAcc, colAcc)) {
            final Constraint newConstraint = model.arithm(
            variables[emptyCoord[0]][emptyCoord[1]], "=", Vide.getInstance().getIndex());
            resCase = model.and(resCase, newConstraint);
        }
        
        return resCase;
    }
    
    private Constraint getConstraints(final Model model, final IntVar[][] variables,
            final int ligne, final int col) {
        
        Constraint res = null;
        final ArrayList<Constraint> allConstraint = new ArrayList<>();
        
        for(final Integer[] caseAccessible : getAccessibleCase(ligne, col)) {
            final int ligneAcc = caseAccessible[0];
            final int colAcc = caseAccessible[1];
            
            allConstraint.add(getConstraintCanSee(model, variables, ligne, 
                    col, ligneAcc, colAcc));
        }
        
        if(!allConstraint.isEmpty()) {
            final Constraint[] arrayAllConstraint = allConstraint.toArray(new Constraint[]{});
            res = model.or(arrayAllConstraint);
        }
        
        return res;
    }
    
    
    //////////////////// STATIC ////////////////////
    
    protected static void printDebug(final String message) {
        if(DEBUG) {
            System.out.println("[DEBUG] " + message);
        }
    }
    
    // PUBLIC 
    public static int getNbrCaseDomaine() {
        return allCases.size();
    }
    
    public static void applyAllConstraints(final Model model, final IntVar[][] variables) {
        
        for(int ligne = 0; ligne < variables.length; ++ligne) {
            for(int col = 0; col < variables[ligne].length; ++col) {
                printDebug("Contrainte pour (" + ligne + ", " + col + ")");
                
                final ArrayList<Constraint> allContrainte = new ArrayList<>();
                for(final CaseManager specificCase : allCases) {
                    if(specificCase.equals(Obstacle.getInstance())) {
                        continue;
                    }
                    
                    // Get all constraintes for a specific position to a specific
                    // case.
                    final Constraint caseContrainte = specificCase.getConstraints(model, 
                            variables, ligne, col);
                    
                    if(caseContrainte != null) {
                        allContrainte.add(caseContrainte);
                    } else {
                        System.err.println("Aucune contrainte pour la case " + 
                                specificCase.getSymbole() + " en coordonnée: "
                                + "(" + ligne + ", " + col + ")");
                    }
                }
                
                if(!allContrainte.isEmpty()) {
                    model.or(allContrainte.toArray(new Constraint[]{})).post();
                }
            }
        }
    }
    
    public static void applyObstracleConstraints(final Model model, 
            final IntVar[][] variables, final ArrayList<Integer[]> listeObstacle) {
        
        final int indexObstacle = Obstacle.getInstance().getIndex();
        for(final Integer[] position : listeObstacle) {
            final int ligne = position[0];
            final int col = position[1];
            
            model.arithm(variables[ligne][col], "=", indexObstacle).post();
        }
    }
    
    public static void initAllManager() {
        Vide.getInstance();
        Obstacle.getInstance();
        
        new Capteur(Direction.NORD);
        new Capteur(Direction.SUD);
        new Capteur(Direction.EST);
        new Capteur(Direction.OUEST);
    }
    
}

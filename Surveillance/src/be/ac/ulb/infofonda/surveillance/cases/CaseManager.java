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
    protected final int _index;
    protected final int _maxLigne;
    protected final int _maxColonne;
    protected final boolean _mustBeOptimised;
    
    protected CaseManager(final char symbole, final int maxLigne, final int maxCol,
            final boolean mustBeOptimise) {
        _symbole = symbole;
        _index = allCases.size();
        _maxLigne = maxLigne;
        _maxColonne = maxCol;
        _mustBeOptimised = mustBeOptimise;
        
        printDebug("Manager: " + symbole + " (index: " + _index + ")");
        
        allCases.add(this);
    }
    
    public String getSymbole() {
        return ""+_symbole;
    }
    
    protected int getIndex() {
        return _index;
    }
    
    protected boolean mustBeOptimised() {
        return _mustBeOptimised;
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
    
    protected static IntVar[] convertDim2ToDim1(final IntVar[][] variables) {
        final ArrayList<IntVar> allVar = new ArrayList<>();
        for(final IntVar[] ligneVar : variables) {
            for(final IntVar var : ligneVar) {
                allVar.add(var);
            }
        }
        return allVar.toArray(new IntVar[]{});
    }
    
    protected static Integer[] getCoord(final int ligne, final int col) {
        final Integer[] tempRes = new Integer[2];
        tempRes[0] = ligne;
        tempRes[1] = col;
        return tempRes;
    }
    
    // PUBLIC 
    public static int getNbrCaseDomaine() {
        return allCases.size()-1; // On retire le type "obstacle"
    }
    
    public static String caseIndex2String(final int index) {
        final CaseManager caseManager = allCases.get(index);
        return (caseManager != null) ? caseManager.getSymbole() : " ";
    }
    
    public static void applyAllConstraints(final Model model, final IntVar[][] variables) {
        
        for(int ligne = 0; ligne < variables.length; ++ligne) {
            for(int col = 0; col < variables[ligne].length; ++col) {
                printDebug("Contrainte pour (" + ligne + ", " + col + ")");
                
                final ArrayList<Constraint> allContrainte = new ArrayList<>();
                // La case est bonne s'il est différente du vide
//                allContrainte.add(model.arithm(variables[ligne][col], "!=", Vide.getInstance().getIndex()));
                
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
                        System.err.println("Aucune contrainte pour la case '" + 
                                specificCase.getSymbole() + "' en coordonnée: "
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
    
    public static void initAllManager(final int maxLigne, final int maxCol) {
        Vide.getInstance();
        
        new Capteur(Direction.NORD, maxLigne, maxCol);
        new Capteur(Direction.SUD, maxLigne, maxCol);
        new Capteur(Direction.EST, maxLigne, maxCol);
        new Capteur(Direction.OUEST, maxLigne, maxCol);
        Obstacle.getInstance();
    }
    
    public static IntVar getOptimiseVar(final Model model, final int ligne, 
            final int col, IntVar[][] variables) {
        
        final ArrayList<Integer> optimiseIndex = new ArrayList<>();
        for(final CaseManager specificCase : allCases) {
            if(specificCase.mustBeOptimised()) {
                optimiseIndex.add(specificCase.getIndex());
            }
        }
        
        IntVar result = null;
        if(!optimiseIndex.isEmpty()) {
            
            int[] allIndex = new int[optimiseIndex.size()];
            int i = 0;
            for(final int index : optimiseIndex) {
                allIndex[i] = index;
                ++i;
            }
            
            result = model.intVar("Optimisation", 0, ligne * col);
            final IntVar[] allVar = convertDim2ToDim1(variables);

            model.among(result, allVar, allIndex).post();
        }
        
        return result;
    }
    
}

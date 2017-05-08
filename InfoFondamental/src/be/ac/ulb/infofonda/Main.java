package be.ac.ulb.infofonda;

import be.ac.ulb.infofonda.echec.Echec;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

/**
 *
 * @author Remy
 */
public class Main {
    
    public static void main(String[] args) {
//        example();
//        queenProblem();

        // nbrFou, nbrCavalier, nbrTour
//        new Echec(0, 0, 5, 5);
//        new Echec(0, 0, 5, 5);
        new Echec(1, 1, 2, 3);
//        new Echec(0, 0, 2, 2);
        
//        modelit(4);
    }
    
    
    protected static void modelit(int n) {
        Model model = new Model();
        IntVar[] vars = model.intVarArray("var", n, 0, n - 1, true);
//        for (int i = 0; i < n; i++) {
//            model.count(i, vars, vars[i]).post();
//        }
        IntVar intVar = model.intVar(1);
        model.among(intVar, vars, new int[]{2}).post();
        
//        model.among(2, vars, 1);
//        model.sum(vars, "=", n).post(); // cstr redundant 1
//        int[] coeff2 = new int[n - 1];
//        IntVar[] vs2 = new IntVar[n - 1];
//        for (int i = 1; i < n; i++) {
//            coeff2[i - 1] = i;
//            vs2[i - 1] = vars[i];
//        }
//        model.scalar(vs2, coeff2, "=", n).post(); // cstr redundant 1
        Solver solver = model.getSolver();
        solver.solve();
        
        System.out.println("Solution: " + solver.isSatisfied());
        
        int i = 0;
        for(IntVar var : vars) {
            System.out.println("Var " + i + ": " + var.getValue());
            ++i;
        }
    }
    
    
    // model.not(model.absolute(var1, var2));
    
    private static void example() {
        // 1. Create a Model
        Model model = new Model("my first problem");
        // 2. Create variables
        IntVar x = model.intVar("X", 0, 5);                 // x in [0,5]
        IntVar y = model.intVar("Y", new int[]{2, 3, 8});   // y in {2, 3, 8}
        // 3. Post constraints
        model.arithm(x, "+", y, "<", 5).post(); // x + y < 5
        model.times(x,y,4).post();              // x * y = 4
        // 4. Solve the problem
        model.getSolver().solve();
        // 5. Print the solution
        System.out.println(x); // Prints X = 2
        System.out.println(y); // Prints Y = 2
    }
    
    /**
     * Solve the queen problem<br />
     * Source: https://github.com/chocoteam/samples/tree/master/src/main/java/org/chocosolver/samples/nqueen
     */
    private static void queenProblem() {
        int nbQueen = 8;
        //1- Create the model
        Model model = new Model("Queen");
        //2- Create the variables
        IntVar[] queens = model.intVarArray("Q", nbQueen, 1, nbQueen);
        //3- Post constraints
        model.allDifferent(queens).post();
        // model.arithm(queens[0], "=", 1).post(); force first
        
        for (int i = 0; i < nbQueen; ++i) {
            for (int j = i + 1; j < nbQueen; ++j) {
                int k = j - i;
                model.arithm(queens[i], "!=", queens[j], "+", -k).post(); // diagonal constraints
                model.arithm(queens[i], "!=", queens[j], "+", k).post(); // diagonal constraints
            }
        }
        //4- Create the solver
        Solver solver = model.getSolver();
        solver.solve();
        //5- Print the number of solutions found
        System.out.println("Number of solutions found:" + solver.getSolutionCount());
        
        String[][] plateau = new String[nbQueen][nbQueen];
        
        System.out.println("Force result: " + queens[0].getValue());
        
        int i = 0;
        for(IntVar queen : queens) {
            plateau[i][queen.getValue()-1] = "Q";
//            System.out.println(queen.getId() + " -> " + queen.getValue());
            ++i;
        }
        
        for (int ligne = 0; ligne < nbQueen; ++ligne) {
            for (int col = 0; col < nbQueen; ++col) {
                String output = " * ";
                String value = plateau[ligne][col];
                if(value != null) {
                    output = " " + value + " ";
                }
                System.out.print(output);
            }
            System.out.print("\n");
        }
        
    }
    
    
}

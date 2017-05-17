package be.ac.ulb.infofonda.echiquier;

import be.ac.ulb.infofonda.echiquier.echec.NbrPions;
import be.ac.ulb.infofonda.echiquier.echec.TypeProbleme;

/**
 *
 * @author Remy
 */
public class ReadingArguments {
    
    private int _index;
    
    private boolean _probDomination = false;
    private boolean _probIndependance = false;
    private int _tailleEchec = 1;
    private NbrPions _nbrTour = new NbrPions(0);
    private NbrPions _nbrCavalier = new NbrPions(0);
    private NbrPions _nbrFou = new NbrPions(0);
    private TypeProbleme _typeProbleme = null;
    private boolean _utf8 = false;
    private boolean _viewAll = false;
    private boolean _debug = false;
    
    
    public ReadingArguments(final String[] args) throws IllegalArgumentException {
        if(args.length == 0) {
            printHelp();
            return;
        }
        
        for(_index = 0; _index < args.length; ++_index) {
            
            switch(args[_index]) {
                
                /////// AIDE ///////
                
                case "-h":
                case "-help":
                    printHelp();
                    break;
                    
                    
                /////// TYPE DE PROBLEME ///////
                    
                case "-d":
                    if(_typeProbleme == null) {
                        _typeProbleme = TypeProbleme.DOMINATION;
                    } else {
                        throw new IllegalArgumentException("Le type de problème"
                                + " a déjà été définit !");
                    }
                    break;
                    
                case "-i":
                    if(_typeProbleme == null) {
                        _typeProbleme = TypeProbleme.INDEPENDANCE;
                    } else {
                        throw new IllegalArgumentException("Le type de problème"
                                + " a déjà été définit !");
                    }
                    break;
                    
                    
                /////// TAILLE ECHIQUIER ///////
                    
                case "-n":
                    final int newTailleEchec = getIntNextArgs(args);
                    
                    if(newTailleEchec > 0) {
                        _tailleEchec = newTailleEchec;
                    } else {
                        throw new IllegalArgumentException("La taille de "
                                + "l'échiquier doit être suppérieur à zéro");
                    }
                    
                    break;
                    
                    
                /////// NBR TOUR ///////
                    
                case "-t":
                case "-tour":
                    final NbrPions newNbrTour = getNbrPions(args);
                    if(newNbrTour.isValide()) {
                        _nbrTour = newNbrTour;
                    } else {
                        throw new IllegalArgumentException("Le nombre de tour doit"
                                + "être suppérieur ou égal à zéro");
                    }
                    break;
                    
                    
                /////// NBR CAVALIER ///////
                    
                case "-c":
                case "-cavalier":
                    final NbrPions newNbrCavalier = getNbrPions(args);
                    if(newNbrCavalier.isValide()) {
                        _nbrCavalier = newNbrCavalier;
                    } else {
                        throw new IllegalArgumentException("Le nombre de cavalier doit"
                                + "être suppérieur ou égal à zéro");
                    }
                    break;
                    
                    
                /////// NBR FOU ///////
                    
                case "-f":
                case "-fou":
                    final NbrPions newNbrFou = getNbrPions(args);
                    if(newNbrFou.isValide()) {
                        _nbrFou = newNbrFou;
                    } else {
                        throw new IllegalArgumentException("Le nombre de fou doit"
                                + "être suppérieur ou égal à zéro");
                    }
                    break;
                    
                    
                /////// All ///////
                    
                case "-all":
                    _viewAll = true;
                    break;
                    
                    
                /////// UTF8 ///////
                    
                case "-utf8":
                    _utf8 = true;
                    break;
                
                    
                /////// DEBUG ///////
                    
                case "-debug":
                    _debug = true;
                    break;
                    
                    
                /////// DEFAULT ///////
                    
                default:
                    throw new IllegalArgumentException("Cet argument est inconnu "
                            + "(" + args[_index] + "). "
                            + "Tappez '-h' pour plus d'informations.");
                    
                    
            }
            
        }
        
    }
    
    private int getIntNextArgs(final String args[]) throws IllegalArgumentException {
        final String intValue = getNextArgs(args);
        int value = -1;
        try {
            value = Integer.parseInt(intValue);
        } catch(NumberFormatException ex) {
            throw new IllegalArgumentException("Le paramètre: '" + intValue + "' "
                    + "n'est pas un nombre (" + ex.getMessage() + ")");
        }
        return value;
    }
    
    private String getNextArgs(final String args[]) throws IllegalArgumentException {
        if(_index >= args.length) {
            throw new IllegalArgumentException("Le nombre d'arguments semble "
                    + "incorrecte (n'avez vous rien oublié après: " 
                    + args[_index] + ")");
        }
        return args[++_index];
    }
    
    private NbrPions getNbrPions(final String args[]) throws IllegalArgumentException {
        final String nextValue = getNextArgs(args);
        NbrPions result;
        
        if(nextValue.equalsIgnoreCase("opti")) {
            result = NbrPions.getOptimalPion();
        } else {
            int value;
            try {
                value = Integer.parseInt(nextValue);
            } catch(NumberFormatException ex) {
                throw new IllegalArgumentException("Le paramètre: '" + nextValue + "' "
                        + "doit soit être 'opti', soit un nombre "
                        + "(" + ex.getMessage() + ")");
            }
            result = new NbrPions(value);
        }
        
        return result;
    }
    
    private void printHelp() {
        System.out.println("------------ INFO-F-302 ------------");
        System.out.println("Utilisation: java -jar <fichier> [options]");
        System.out.println("\t-h,-help\t\tPour afficher ce texte");
        System.out.println("\t-d/-i\t\t\tChoix entre le problème de domination (-d) ou d'indépendance (-i)");
        System.out.println("\t-n\t\t\tTaille de l'échiquier");
        System.out.println("\t-t,-tour <nbr/opti>\tNombre de tour");
        System.out.println("\t-f,-fou <nbr/opti>\tNombre de fou");
        System.out.println("\t-c,-cavalier <nbr/opti>\tNombre de cavalier");
        System.out.println("\t-utf8\t\t\tAffiche les pions via des caractères UTF-8");
        System.out.println("\t-all\t\t\tAffiche tous les résultats possibles");
        System.out.println("\t-debug\t\t\tAffiche les messages de débug");
        System.out.println("");
        System.out.println("Information:");
        System.out.println("\t<nbr/opti>");
        System.out.println("\t\t'nbr'  Vous permet de choisir soit un nombre précis");
        System.out.println("\t\t'opti' Vous permet d'optimiser ce nombre");
        System.out.println("");
    }

    /**
     * @return le type de problème
     */
    public TypeProbleme getTypeProbleme() {
        return _typeProbleme;
    }

    /**
     * @return the tailleEchec
     */
    public int getTailleEchec() {
        return _tailleEchec;
    }

    /**
     * @return the nbrTour
     */
    public NbrPions getNbrTour() {
        return _nbrTour;
    }

    /**
     * @return the nbrCavalier
     */
    public NbrPions getNbrCavalier() {
        return _nbrCavalier;
    }

    /**
     * @return the nbrFou
     */
    public NbrPions getNbrFou() {
        return _nbrFou;
    }

    /**
     * @return the utf8
     */
    public boolean isUtf8() {
        return _utf8;
    }

    /**
     * @return the probIndependance
     */
    public boolean isProbIndependance() {
        return _probIndependance;
    }
    
    public boolean viewAll() {
        return _viewAll;
    }
    
    public boolean isDebug() {
        return _debug;
    }
    
}

package be.ac.ulb.infofonda;

/**
 *
 * @author Remy
 */
public class ReadingArguments {
    
    private int _index;
    
    private boolean _probDomination = false;
    private boolean _probIndependance = false;
    private int _tailleEchec = 1;
    private int _nbrTour = 0;
    private int _nbrCavalier = 0;
    private int _nbrFou = 0;
    private boolean _utf8 = false;
    
    
    public ReadingArguments(final String[] args) throws IllegalArgumentException {
        if(args.length == 0) {
            printHelp();
            return;
        }
        
        for(_index = 0; _index < args.length; ++_index) {
            
            switch(args[_index]) {
                
                case "-h":
                case "-help":
                    printHelp();
                    break;
                    
                /////// TYPE DE PROBLEME ///////
                case "-d":
                    _probDomination = true;
                    break;
                    
                case "-i":
                    _probIndependance = true;
                    break;
                    
                /////// TAILLE ECHIQUIER ///////
                    
                case "-n":
                    final String strDimension = getNextArgs(args);
                    final int newTailleEchec = Integer.parseInt(strDimension);
                    
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
                    final int newNbrTour = getIntNextArgs(args);
                    if(newNbrTour >= 0) {
                        _nbrTour = newNbrTour;
                    } else {
                        throw new IllegalArgumentException("Le nombre de tour doit"
                                + "être suppérieur ou égal à zéro");
                    }
                    break;
                    
                    
                /////// NBR CAVALIER ///////
                    
                case "-c":
                case "-cavalier":
                    final int newNbrCavalier = getIntNextArgs(args);
                    if(newNbrCavalier >= 0) {
                        _nbrCavalier = newNbrCavalier;
                    } else {
                        throw new IllegalArgumentException("Le nombre de cavalier doit"
                                + "être suppérieur ou égal à zéro");
                    }
                    break;
                    
                    
                /////// NBR FOU ///////
                    
                case "-f":
                case "-fou":
                    final int newNbrFou = getIntNextArgs(args);
                    if(newNbrFou >= 0) {
                        _nbrFou = newNbrFou;
                    } else {
                        throw new IllegalArgumentException("Le nombre de fou doit"
                                + "être suppérieur ou égal à zéro");
                    }
                    break;
                    
                    
                /////// UTF8 ///////
                    
                case "-utf8":
                    _utf8 = true;
                    break;
                    
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
    
    
    private void printHelp() {
        System.out.println("------------ INFO-F-302 ------------");
        System.out.println("\t-h, -help\tPour afficher ce texte");
        System.out.println("\t-d/-i\t\tChoix entre le problème de domination (-d) ou d'indépendance (-i)");
        System.out.println("\t-n\t\tTaille de l'échiquier");
        System.out.println("\t-t, -tour\tNombre de tour");
        System.out.println("\t-f, -fou\tNombre de fou");
        System.out.println("\t-c, -cavalier\tNombre de cavalier");
        System.out.println("\t-utf8\t\tAffiche les pions via des caractères UTF-8");
        System.out.println("");
    }

    /**
     * @return the _probDomination
     */
    public boolean isProbDomination() {
        return _probDomination;
    }

    /**
     * @return the _tailleEchec
     */
    public int getTailleEchec() {
        return _tailleEchec;
    }

    /**
     * @return the _nbrTour
     */
    public int getNbrTour() {
        return _nbrTour;
    }

    /**
     * @return the _nbrCavalier
     */
    public int getNbrCavalier() {
        return _nbrCavalier;
    }

    /**
     * @return the _nbrFou
     */
    public int getNbrFou() {
        return _nbrFou;
    }

    /**
     * @return the _utf8
     */
    public boolean isUtf8() {
        return _utf8;
    }

    /**
     * @return the _probIndependance
     */
    public boolean isProbIndependance() {
        return _probIndependance;
    }
    
}

package be.ac.ulb.infofonda;

/**
 *
 * @author Remy
 */
public class ReadingArguments {
    
    private int index;
    
    private boolean probDomination = false;
    private int tailleEchec = 1;
    private int nbrTour = 0;
    private int nbrCavalier = 0;
    private int nbrFou = 0;
    
    
    public ReadingArguments(String[] args) throws IllegalArgumentException {
        
        for(index = 0; index < args.length; ++index) {
            
            switch(args[index]) {
                
                case "-h":
                case "-help":
                    printHelp();
                    break;
                    
                /////// TYPE DE PROBLEME ///////
                case "-d":
                    probDomination = true;
                    break;
                    
                case "-i":
                    probDomination = false;
                    throw new IllegalArgumentException("Pas encore implémenté"); // TODO
                    
                /////// TAILLE ECHIQUIER ///////
                    
                case "-n":
                    String strDimension = getNextArgs(args);
                    int newTailleEchec = Integer.parseInt(strDimension);
                    
                    if(newTailleEchec > 0) {
                        tailleEchec = newTailleEchec;
                    } else {
                        throw new IllegalArgumentException("La taille de "
                                + "l'échiquier doit être suppérieur à zéro");
                    }
                    
                    break;
                    
                    
                /////// NBR TOUR ///////
                    
                case "-t":
                case "-tour":
                    int newNbrTour = getIntNextArgs(args);
                    if(newNbrTour >= 0) {
                        nbrTour = newNbrTour;
                    } else {
                        throw new IllegalArgumentException("Le nombre de tour doit"
                                + "être suppérieur ou égal à zéro");
                    }
                    break;
                    
                    
                /////// NBR CAVALIER ///////
                    
                case "-c":
                case "-cavalier":
                    int newNbrCavalier = getIntNextArgs(args);
                    if(newNbrCavalier >= 0) {
                        nbrCavalier = newNbrCavalier;
                    } else {
                        throw new IllegalArgumentException("Le nombre de cavalier doit"
                                + "être suppérieur ou égal à zéro");
                    }
                    break;
                    
                    
                /////// NBR FOU ///////
                    
                case "-f":
                case "-fou":
                    int newNbrFou = getIntNextArgs(args);
                    if(newNbrFou >= 0) {
                        nbrFou = newNbrFou;
                    } else {
                        throw new IllegalArgumentException("Le nombre de fou doit"
                                + "être suppérieur ou égal à zéro");
                    }
                    break;
                    
                    
                default:
                    throw new IllegalArgumentException("Cet argument est inconnu "
                            + "(" + args[index] + "). "
                            + "Tappez '-h' pour plus d'informations.");
                    
                    
            }
            
        }
        
    }
    
    private int getIntNextArgs(String args[]) throws IllegalArgumentException {
        String intValue = getNextArgs(args);
        int value = -1;
        try {
            value = Integer.parseInt(intValue);
        } catch(NumberFormatException ex) {
            throw new IllegalArgumentException("Le paramètre: '" + intValue + "' "
                    + "n'est pas un nombre (" + ex.getMessage() + ")");
        }
        return value;
    }
    
    private String getNextArgs(String args[]) throws IllegalArgumentException {
        if(index >= args.length) {
            throw new IllegalArgumentException("Le nombre d'arguments semble "
                    + "incorrecte (n'avez vous rien oublié après: " 
                    + args[index] + ")");
        }
        return args[++index];
    }
    
    
    private void printHelp() {
        System.out.println("------------ INFO-F-302 ------------");
        System.out.println("\t-h, -help\tPour afficher ce texte");
    }

    /**
     * @return the probDomination
     */
    public boolean isProbDomination() {
        return probDomination;
    }

    /**
     * @return the tailleEchec
     */
    public int getTailleEchec() {
        return tailleEchec;
    }

    /**
     * @return the nbrTour
     */
    public int getNbrTour() {
        return nbrTour;
    }

    /**
     * @return the nbrCavalier
     */
    public int getNbrCavalier() {
        return nbrCavalier;
    }

    /**
     * @return the nbrFou
     */
    public int getNbrFou() {
        return nbrFou;
    }
    
    
    
}

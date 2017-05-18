package be.ac.ulb.infofonda.surveillance.input;

/**
 * Permet de lire les arguments passé en paramètre lors de l'appel de ce programme
 * 
 * @author Detobel
 */
public class ReadingArguments {
    
    private int _index = 0;
    private boolean _viewAll = false;
    private boolean _utf8 = false;
    private boolean _debug = false;
    private String _fichier = "";
    private boolean _time = false;
    private boolean _fullTime = false;
    private boolean _gui = false;
    
    
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
                    
                /////// FICHIER ///////
                    
                case "-file":
                case "-f":
                case "-fichier":
                    if(_gui) {
                        throw new IllegalArgumentException("Impossible de spécifier "
                            + "le fichier ET d'utiliser l'interface graphique");
                    }
                    _fichier = getNextArgs(args);
                    break;
                    
                /////// FICHIER GUI ///////
                
                case "-gui":
                case "-g":
                    if(!_fichier.isEmpty()) {
                        throw new IllegalArgumentException("Impossible d'ouvrir "
                            + "l'interface, vous avez déjà spécifié le fichier " 
                            + _fichier);
                    }
                    _gui = true;
                    break;
                   
                    
                /////// All ///////
                    
                case "-all":
                    _viewAll = true;
                    break;
                    
                    
                /////// UTF8 ///////
                    
                case "-utf8":
                    _utf8 = true;
                    break;
                
                /////// TIME ///////
                    
                case "-fulltime":
                case "-fullt":
                    _fullTime = true;
                    
                case "-time":
                case "-t":
                    _time = true;
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
        System.out.println("Utilisation: java -jar <fichier> [options]");
        System.out.println("\t-h,-help\t\tPour afficher ce texte");
        System.out.println("\t-file,-f\t\tSélectionner le fichier qui contient le plan");
        System.out.println("\t-gui,-g\t\t\tPermet de sélectionner le fichier de manière graphique");
        System.out.println("\t-time,-t\t\tPermet d'afficher le temps d'exécution total du programme");
        System.out.println("\t-fulltime,-fullt\tPermet d'afficher le temps d'execution régulièrement (20 sec)");
        System.out.println("\t-utf8\t\t\tAffiche les pions via des caractères UTF-8");
        System.out.println("\t-all\t\t\tAffiche tous les résultats possibles");
        System.out.println("\t-debug\t\t\tAffiche les messages de débug");
        System.out.println("");
    }

    /**
     * @return the viewAll
     */
    public boolean isViewAll() {
        return _viewAll;
    }

    /**
     * @return the utf8
     */
    public boolean isUtf8() {
        return _utf8;
    }

    /**
     * @return the debug
     */
    public boolean isDebug() {
        return _debug;
    }

    /**
     * @return the fichier
     */
    public String getFichier() {
        return _fichier;
    }

    /**
     * @return the time
     */
    public boolean isTime() {
        return _time;
    }

    /**
     * @return the fullTime
     */
    public boolean isFullTime() {
        return _fullTime;
    }

    /**
     * @return the gui
     */
    public boolean isGui() {
        return _gui;
    }
    
    
}

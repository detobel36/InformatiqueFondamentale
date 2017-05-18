package be.ac.ulb.infofonda.surveillance;

import be.ac.ulb.infofonda.surveillance.input.ReadingArguments;
import be.ac.ulb.infofonda.surveillance.input.ReadingFile;
import java.util.ArrayList;

public class Main {
    
    public static void main(final String[] args) {
        
        ReadingArguments readingArgs;
        try {
            readingArgs = new ReadingArguments(args);
            
            final String fichier = readingArgs.getFichier();

            if(fichier != null && !fichier.isEmpty()) {
                final ReadingFile readingFile = new ReadingFile(fichier);
                
                final ArrayList<String> allContent = readingFile.getAllContent();
                final boolean viewAll = readingArgs.isViewAll();
                final boolean isTime = readingArgs.isTime();
                final boolean isFullTime = readingArgs.isFullTime();
                final boolean utf8 = readingArgs.isUtf8();
                final boolean debug = readingArgs.isDebug();
                
                new Surveillance(allContent, viewAll, isTime, isFullTime, utf8, debug);
                
            } else {
                System.err.println("Le fichier indiqué n'est pas valide !");
            }
            
        } catch(IllegalArgumentException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }
    
}
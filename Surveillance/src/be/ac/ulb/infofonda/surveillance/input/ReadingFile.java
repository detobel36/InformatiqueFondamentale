package be.ac.ulb.infofonda.surveillance.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Permet de lire le contenu d'un fichier
 * 
 * @author Detobel
 */
public class ReadingFile {
    
    private ArrayList<String> _content;
    
    
    public ReadingFile(final String fileName) throws IllegalArgumentException {
        this(new File(fileName));
    }
    
    public ReadingFile(final File fichier) throws IllegalArgumentException {
        _content = new ArrayList<>();
        try {
            readContentOfFile(fichier);
        } catch(IOException ex) {
            throw new IllegalArgumentException("Le fichier indiqué n'exisste pas");
        }
    }
    
    private void readContentOfFile(final File fichier) throws FileNotFoundException, IOException {
        final BufferedReader br = new BufferedReader(new FileReader(fichier));
        try {
            String line = br.readLine();
            
            while (line != null) {
                _content.add(line);
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }
    
    /**
     * Permet de récupérer tout le contenu d'un fichier sous forme de tableau où
     * chaque élément correspond à une ligne
     * 
     * @return un array liste où un élément égale une ligne
     */
    public ArrayList<String> getAllContent() {
        return _content;
    }
    
    
}

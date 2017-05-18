package be.ac.ulb.infofonda.surveillance.javafx;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

/**
 *
 * @author Detobel
 */
public class MainJavaFX extends Application  {
    
    private static Stage _stage;
    
    private static boolean openFile = false;
    private static File selectedFile;
    
    
    @Override
    public void start(Stage stage) throws Exception {
        _stage = stage;
        
        final String title = "Surveillance de musée";
        _stage.setTitle(title);
        
        if(openFile) {
            openSelectFile();
        }
        
        _stage.show();
    }
    
    private void openSelectFile() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner le plan");
        selectedFile = fileChooser.showOpenDialog(_stage);
        
        Platform.exit();
    }
    
    private static void beginJavaFX() {
        launch();
    }
    
    public static void selectFile() {
        openFile = true;
        beginJavaFX();
    }
    
    public static File getSelectedFile() {
        return selectedFile;
    }
    
}

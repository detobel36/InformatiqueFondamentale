package be.ac.ulb.infofonda.surveillance.utils;

/**
 *
 * @author Detobel
 */
public class ProgramTimer extends Thread {
    
    private boolean notStop;
    
    @Override
    public void run() {
        notStop = true;
        int temps = 0;
        
        while(notStop) {
            
            printTime(temps++);
            try {
                // On attend 1 sec
                Thread.sleep(1000);
            }
            catch (InterruptedException ex) {}
        }
    }
    
    private void printTime(final int temps) {
        if(temps != 0 && (
            (temps <= 10 && temps%5 == 0) || temps%20 == 0 )) {
            String strTemps = "";
            if(temps >= 60) {
                strTemps = temps/60 + " min. ";
            }
            strTemps += (temps%60 > 0) ? (temps%60 + " sec.") : "";
            
            System.out.println(strTemps + " d'exécution");
        }
    }
    
    public void setStop() {
        notStop = false;
    }
    
}

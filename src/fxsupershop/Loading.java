
package fxsupershop;

import java.io.IOException;
import javafx.application.Preloader;
import javafx.application.Preloader.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author Rifat Rabbi
 */
public class Loading extends Preloader{
    
    ProgressBar bar;
    Stage stage;
    boolean noLoadingProgress=false;
    Loading_formController lc = new Loading_formController();
    
    private Scene createPreloaderScene() throws IOException {
        
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxsupershop/loading_form.fxml"));

        BorderPane p = new BorderPane();
        p.setCenter(a);
        return new Scene(p);        
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createPreloaderScene());   
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }
    
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        if (pn.getProgress() != 1.0 || !noLoadingProgress) {
          bar.setProgress(pn.getProgress()/2);
          if (pn.getProgress() > 0) {
              noLoadingProgress = false;
          }
        }
    }
@Override
public void handleApplicationNotification(PreloaderNotification pn){
    if (pn instanceof ProgressNotification) {
           //expect application to send us progress notifications 
           //with progress ranging from 0 to 1.0
           double v = ((ProgressNotification) pn).getProgress();
           if (!noLoadingProgress) {
               //if we were receiving loading progress notifications 
               //then progress is already at 50%. 
               //Rescale application progress to start from 50%               
               v = 0.5 + v/2;
           }
           bar.setProgress(v);            
        } else if (pn instanceof StateChangeNotification) {
            //hide after get any state update from application
            stage.hide();
        }
    } 

    
}
    


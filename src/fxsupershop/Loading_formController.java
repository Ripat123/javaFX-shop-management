
package fxsupershop;

import com.jfoenix.controls.JFXProgressBar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;

/**
 * FXML Controller class
 *
 * @author Rifat Rabbi
 */
public class Loading_formController implements Initializable {

    @FXML
    private JFXProgressBar Pbar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void pro(double v){
        Pbar.setProgress(v);
    }
    
}


package fxsupershop.Editor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.HTMLEditor;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Text_editController implements Initializable {

    @FXML
    private HTMLEditor htmlid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void Saveaction(ActionEvent event) {
        System.out.println(htmlid.getHtmlText());
    }
    
}

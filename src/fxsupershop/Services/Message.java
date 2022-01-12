package fxsupershop.Services;

import java.util.Optional;
import javafx.scene.control.*;

/**
 *
 * @author Ripat Rabbi
 */
public class Message {

    public void InformationMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
    
    public void WarningMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CANCEL);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
    
    public void ErrorMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.CLOSE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
    
    public Optional<ButtonType> ConfirmationMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        alert.show();
         return result;
    }
    
    public void NoneMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.NONE, "", ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
    
    public void ConditionalMessage(String message){
        Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK).showAndWait();
    }

}

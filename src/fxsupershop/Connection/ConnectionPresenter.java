package fxsupershop.Connection;

import fxsupershop.Services.PrepareQueryFunction;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Rifat Rabbi
 */
public class ConnectionPresenter {
    
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();

    public void setupConnection(Exception e) {
        try {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
            Stage s1 = new Stage();
            BorderPane ap = new BorderPane();
            Button b = new Button("Yes");
            Button b2 = new Button("No");
            BorderPane bp = new BorderPane();
            bp.setRight(b2);
            bp.setLeft(b);
            Label l = new Label(".\nDatabase not found. Are you want to setup database?\n.");
            l.setStyle("-fx-font-size: 15;");
            b2.setOnAction((e1) -> {
                System.exit(1);
            });
            b.setOnAction((event) -> {
                try {
                    Parent anchorPane = FXMLLoader.load(getClass().getResource("/fxsupershop/Connection/Connection_setup.fxml"));
                Scene s = new Scene(anchorPane);
                Stage stage = new Stage();
                stage.setScene(s);
                stage.setResizable(false);
                stage.setTitle("Database Setup");
                stage.show();
                s1.close();
                } catch (Exception e1) {
                }
                
            });
//            ap.getChildren().addAll(b,b2);
            ap.setCenter(bp);
            ap.setTop(l);
            Scene s2 = new Scene(ap);
            s1.setScene(s2);
            s1.setResizable(false);
            s1.setTitle("Confirmation");
            s1.show();
//            alert.setHeaderText("Database not found");
//            alert.setContentText("At first you should set server\nAre you want to setup database?\n"+e);
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.isPresent() && result.get() == ButtonType.YES) {
//                
//            }
        } catch (Exception ex) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem\n"+ex);
        }
    }
}

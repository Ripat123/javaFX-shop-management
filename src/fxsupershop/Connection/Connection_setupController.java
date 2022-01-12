package fxsupershop.Connection;

import com.jfoenix.controls.*;
import fxsupershop.FxSuperShop;
import fxsupershop.Services.PrepareQueryFunction;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rifat Rabbi
 */
public class Connection_setupController implements Initializable {

//    @FXML
//    private ImageView image_back;
//    @FXML
//    private ImageView logo;
    @FXML
    private JFXTextField database_name;
    @FXML
    private JFXTextField U_name;
    @FXML
    private JFXPasswordField u_pass;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    Connection con=null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void Restore(ActionEvent e) {
        String dbUserName = U_name.getText();
        String dbName = database_name.getText();
        String dbPassword = u_pass.getText();
        try {
            con = connection_Sql.DynamicConnection(dbName, dbUserName, dbPassword);
            queryFunction.getCon(con);
            queryFunction.report.GetCon(con);
            FxSuperShop fss = new FxSuperShop();
            fss.SetConnect(con);
            fss.start(new Stage());
            Stage window1 = (Stage) ((Node) e.getSource()).getScene().getWindow();
            window1.close();
            write("dbName.txt", dbName);
            write("UInfo.txt", dbUserName);
            write("PInfo.txt", dbPassword);
        } catch (Exception ex) {
            queryFunction.service.msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem in setup!\n" + ex);
        }
    }

    private void write(String name, String field) {
        String path = System.getProperty("user.home") + "\\Documents\\DigitalShop dbUserFile";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + "\\" + name, false))) {
            bw.write(queryFunction.service.encrypt(field));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            boolean dir = new File(path).mkdirs();
            if (!dir) {
                queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "File couldn't created");
            } else {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + "\\" + name, false))) {
                    bw.write(queryFunction.service.encrypt(field));
                    bw.newLine();
                    bw.close();
                } catch (Exception ex) {
                    queryFunction.service.msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem in file write\nnext time you may need again setup connection\n" + e);
                }
            }
        }
    }

    @FXML
    private void submit(ActionEvent event) {
        Restore(event);
    }

}

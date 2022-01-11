package fxsupershop;

import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.PrepareQueryFunction;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import javafx.animation.*;
import javafx.application.*;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import javafx.util.Duration;

/**
 *
 * @author Ripat Rabbi
 */
public class FxSuperShop extends Application {

    private double xdragset = 0;
    private double ydragset = 0;
    Connection con = null;
    ResultSet rs;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    LoginMultiFormController lmfc = new LoginMultiFormController();
    static Loading loading = new Loading();

    @Override
    public void start(Stage primaryStage) {
        try {
            loading.start(new Stage());
//            loadWorker.start();
lmfc.initview(con);
            Start(primaryStage);
//            test.play();
        } catch (Exception e) {
        }
    }

    public Timeline test = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            lmfc.initview(con);
            startApp(new Stage());
        }
    }));
    LoadWorker loadWorker = new LoadWorker(test);

    public void SetConnect(Connection c) {
        this.con = c;
    }

    private void startApp(Stage primaryStage) {
        try {
            String mac = macID();
            String sql = "SELECT * FROM tr";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                String hID = queryFunction.service.decrypt(rs.getString("H_address"));
                if (mac.equals(hID)) {
                    trial(primaryStage);
                } else {
                    queryFunction.service.msg.ConditionalMessage("It's not a valid "
                            + "Computer\nYou haven't purchased this Software\nIf "
                            + "you want to purchase this\nPlease contact with 01840241895");
                    System.exit(1);
                }
            }

        } catch (Exception e) {
        }
    }

    public void Start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxsupershop/Login/LoginMultiForm.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

        primaryStage.setScene(scene);
        loading.handleStateChangeNotification(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));

        primaryStage.show();

        root.setOnMousePressed((MouseEvent event) -> {
            xdragset = event.getSceneX();
            ydragset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xdragset);
            primaryStage.setY(event.getScreenY() - ydragset);
        });
    }

    private void trial(Stage primaryStage) {
        try {
            String date = LocalDate.now().toString();
            String sql = "SELECT * FROM tr WHERE date = '" + date + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                String sql2 = "SELECT * FROM tr WHERE `date` <= `end_date` AND `datenumber` > '0'";
                rs = queryFunction.getResult(sql2);
                if (rs.next()) {
                    Start(primaryStage);
                } else {
                    queryFunction.service.msg.WarningMessage("Warning", "Trial Information", "Your trial has been expired\n"
                            + "If you want to renew your trial licence, please contact with 01840241895");
                }
            } else {
                String sql1 = "UPDATE tr SET date = '" + date + "', datenumber = datenumber - '1'";
                queryFunction.UpdateMessageLess(sql1);
                String sql2 = "SELECT * FROM tr WHERE `date` <= `end_date` AND `datenumber` > '0'";
                rs = queryFunction.getResult(sql2);
                if (rs.next()) {
                    Start(primaryStage);
                } else {
                    queryFunction.service.msg.ConditionalMessage("Your trial has been expired\n"
                            + "If you want to renew your trial licence, please contact with 01840241895");
                    System.exit(1);
                }
            }
        } catch (Exception e) {
        }
    }

    private String macID() {
        String ID = null;
        try {
            Process p = Runtime.getRuntime().exec("getmac /fo csv /nh");
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
            String line;
            line = in.readLine();
            String[] result = line.split(",");

            ID = result[0].replace('"', ' ').trim();
        } catch (Exception e) {
        }
        return ID;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

}

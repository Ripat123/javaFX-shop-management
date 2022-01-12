package fxsupershop.Login;

import animatefx.animation.BounceIn;
import com.jfoenix.controls.*;
import fxsupershop.Connection.connection_Sql;
import fxsupershop.Services.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class LoginMultiFormController implements Initializable, Runnable {

    @FXML
    private Pane login_form;
    @FXML
    private TextField user1;
    @FXML
    private PasswordField pass1;
    @FXML
    private Button Login;
    @FXML
    private CheckBox show_pass1;
    @FXML
    private Hyperlink forgot_pass;
    @FXML
    private Hyperlink sign_up1;
    @FXML
    private Pane get_pass_form;
    @FXML
    private TextField user2;
    @FXML
    private Button get_pass;
    @FXML
    private Hyperlink sign_up2;
    @FXML
    private TextField email_or_phone1;
    @FXML
    private PasswordField pass2;
    @FXML
    private CheckBox show_pass;
    @FXML
    private ImageView submit1;
    @FXML
    private ImageView submit2;
    @FXML
    private Hyperlink go_login;
    @FXML
    private AnchorPane main_form;
    @FXML
    private Label close1;
    @FXML
    private Label close2;
    @FXML
    private TextField create_fullname;
    @FXML
    private Button signup_btn;
    @FXML
    private TextArea create_address;
    @FXML
    private RadioButton male;
    @FXML
    private RadioButton female;
    @FXML
    private PasswordField create_con_pass;
    @FXML
    private CheckBox show_pass_con;
    @FXML
    private TextField create_phon;
    @FXML
    private TextField create_email;
    @FXML
    private TextField create_user;
    @FXML
    private PasswordField create_pass;
    @FXML
    private CheckBox show_pass11;
    @FXML
    private Label close11;
    @FXML
    private Hyperlink go_login1;
    @FXML
    private Pane create_form;
    @FXML
    private ToggleGroup gender;
    ResultSet rs = (null);
    Thread th;
    @FXML
    private Pane proccessing;
    @FXML
    private Label close12;
    private ActionEvent e1;
    public String user_p;
    Message msg = new Message();
    private String txt_userPass, execCmd;
    ObservableList data = FXCollections.observableArrayList();
    @FXML
    private Pane questionPane;
    @FXML
    private JFXTextField ans_field;
    @FXML
    private Label ques_level;
    private JFXPopup pop = new JFXPopup();
    LoginPresenter presen = new LoginPresenter();
    private static int userID;
    @FXML
    private ImageView image_back;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    @FXML
    private ImageView logo;
    @FXML
    private Label desc;
    Connection c = null;
    Process process;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        initview();
        th = new Thread((Runnable) this);

        image(image_back, 60, 60);
        image(logo, 30, 30);
    }

    public void initview(Connection con) {
        c = con;
        try {
            if (c == null) {
                c = connection_Sql.ConnectDb();
            }
            if (c == null) {
                try {
                    execCmd = "C:/xampp/mysql/bin/mysqld.exe";
                    process = Runtime.getRuntime().exec(execCmd);
                    c = connection_Sql.newConnection();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
                    alert.setHeaderText("Server not found");
                    alert.setContentText("Please install the Xampp server\n" + e);
                    alert.showAndWait();
                    System.exit(1);
                }
            }
            queryFunction.getCon(c);
            queryFunction.report.GetCon(c);
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem\n" + e);
        }
    }

    private void image(ImageView Image, int width, int height) {
        new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        try {
                            Rectangle r = new Rectangle(Image.getFitWidth(), Image.getFitHeight());
                            r.setArcWidth(width);
                            r.setArcHeight(height);
                            Image.setClip(r);
                            SnapshotParameters parameters = new SnapshotParameters();
                            parameters.setFill(Color.TRANSPARENT);
                            WritableImage image = Image.snapshot(parameters, null);
                            Image.setClip(null);
                            Image.setImage(image);
                        } catch (Exception e) {
                        }
                        return null;
                    }
                };
            }
        }.start();

    }

    private void login() {
        Platform.runLater(() -> {
            if (user1.getText().equals("")) {
                msg.WarningMessage("Unsuccessful", "Warning", "Enter User Name.");
                return;
            }
            if (show_pass1.isSelected()) {
                show_pass1.setSelected(false);
                txt_userPass = pass1.getPromptText();
            } else if (!show_pass1.isSelected()) {
                if (pass1.getText().equals("")) {
                    msg.WarningMessage("Unsuccessful", "Warning", "Enter User Password.");
                    return;
                } else {
                    txt_userPass = pass1.getText();
                }
            }
            try {

                String sql = "SELECT * FROM `createadmin` WHERE `name`='" + user1.getText() + "' "
                        + "AND `password`='" + txt_userPass + "' AND Status = '1'";
                rs = queryFunction.getResult(sql);

                if (rs.next()) {
                    userID = rs.getInt("id");
                    th.start();
                    User();

                } else {
                    msg.WarningMessage("Unsuccessful", "Warning", "Login Unsuccessful!\nPlease Check User Name and Password.");
                }
            } catch (Exception f) {
                msg.ErrorMessage("Unsuccessful", "Error", "You have not successful\n" + f);

            } finally {
                try {
                    queryFunction.post.close();
                    rs.close();

                } catch (Exception e) {

                }
            }
        });
    }

    public static int User() {
        int ID = userID;
        return ID;
    }

    @FXML
    private void LogintoHome(ActionEvent event) throws IOException {

        e1 = event;
        new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        login();
                        return null;
                    }
                };
            }
        }.start();
        

    }
    String password;

    @FXML
    private void ShowPassword(ActionEvent event) {
        presen.ShowPassToPasswordField(pass1, show_pass1);
//showTrayMessage("Hello Ripat", "Welcome Digital shop");
    }

    @FXML
    private void ForgotPassword(ActionEvent event) {
        get_pass_form.setVisible(true);
    }

    @FXML
    private void SignUp(ActionEvent event) {
        create_form.setVisible(true);
    }

    @FXML
    private void GetPassword(ActionEvent event) {
        new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            try {
                                String sql = "SELECT * FROM createadmin WHERE name = '" + user2.getText().trim() + "'"
                                        + " AND (email = '" + email_or_phone1.getText().trim() + "' OR phone "
                                        + "= '" + email_or_phone1.getText().trim() + "')";
                                rs = queryFunction.getResult(sql);
                                if (rs.next()) {
                                    ques_level.setText(rs.getString("Security_question"));
                                    int size = rs.getString("Security_question").length();
                                    int width = size * 10;
                                    if (size > 20) {
                                        if (width > 311) {
                                            questionPane.setPrefWidth(width);
                                        }
                                    }

                                    pop.setPopupContent(questionPane);
                                    pop.show(get_pass_form, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, -50, 60);
                                } else {
                                    msg.WarningMessage("Unsuccessful", "Warning", "Please check User Name and Email or Phone.");
                                }
                            } catch (Exception e) {
                                msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
                            } finally {
                                try {
                                    queryFunction.post.close();
                                    rs.close();
                                } catch (Exception e) {

                                }
                            }
                        });
                        return null;
                    }
                };
            }
        }.start();
    }

    @FXML
    private void Entered1(MouseEvent event) {
    }

    @FXML
    private void Entered2(MouseEvent event) {
    }

    @FXML
    private void GotoLogin(ActionEvent event) {
        login_form.setVisible(true);
        create_form.setVisible(false);
        get_pass_form.setVisible(false);
    }

    @FXML
    private void CloseOp(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void setmove(MouseEvent event) {

    }

    @FXML
    private void getmove(MouseEvent event) {

    }

    @FXML
    private void SignUpAction(ActionEvent event) {

    }

    @FXML
    private void RadioMale(ActionEvent event) {
    }

    @FXML
    private void RadioFemale(ActionEvent event) {
    }

    @FXML
    private void enterPass(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            pass1.requestFocus();
        }
    }

    @FXML
    private void enterLogin(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Login.fire();
        }
    }

    @Override
    public void run() {
        try {
            proccessing.setVisible(true);
            new BounceIn(proccessing).play();
            for (int i = 1; i <= 30; i++) {
                if (i == 30) {
                    i = 31;
                    Platform.runLater(() -> {
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/fxsupershop/Home/homePage.fxml"));

                            Stage window = new Stage();
                            JFXDecorator decorator = new JFXDecorator(window, root);
                            String ui = getClass().getResource("/fxsupershop/Home/homepage.css").toExternalForm();
                            decorator.getStylesheets().add(ui);
                            ImageView imageView = new ImageView(getClass().getResource("/fxsupershop/Image/icon.png").toExternalForm());
                            imageView.setFitHeight(25);
                            imageView.setFitWidth(25);
                            image(imageView, 26, 26);
                            decorator.setGraphic(imageView);
                            Scene scene = new Scene(decorator);
                            window.setScene(scene);
                            window.show();

                            Stage window1 = (Stage) ((Node) e1.getSource()).getScene().getWindow();
                            window1.close();
                            window.setTitle("Digital Shop");
                            window.setMinWidth(1285);
                            window.setMinHeight(741);

                        } catch (Exception e) {
                            queryFunction.service.msg.WarningMessage("Unsuccessful",
                                    "Opening Exception", "Have a Problem in Home Page\n" + e);
                        }
                    });
                }
                Thread.sleep(40);
            }

        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem\n" + e);
        }

    }

    @FXML
    private void PasswordFieldClicked(MouseEvent event) {
        presen.CheckSelectedGetPass(show_pass1, pass1);
    }

    @FXML
    private void OKbtn(ActionEvent event) {
        try {
            String sql = "SELECT * FROM createadmin WHERE name = '" + user2.getText().trim() + "'"
                    + " AND (email = '" + email_or_phone1.getText().trim() + "' OR phone "
                    + "= '" + email_or_phone1.getText().trim() + "') AND Security_question "
                    + "= '" + ques_level.getText() + "' AND Answer = '" + ans_field.getText().trim() + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                pass2.setText(rs.getString("password"));
            } else {
                msg.WarningMessage("Unsuccessful", "Warning", "You are not right.");
            }
            pop.hide();
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    @FXML
    private void cancelbtn(ActionEvent event) {
        pop.hide();
    }

    @FXML
    private void GetPasswordFieldClicked(MouseEvent event) {
        presen.CheckSelectedGetPass(show_pass, pass2);
    }

    @FXML
    private void ShowGetPassword(ActionEvent event) {
        presen.ShowPassToPasswordField(pass2, show_pass);
    }

}

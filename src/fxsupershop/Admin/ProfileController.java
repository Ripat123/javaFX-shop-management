package fxsupershop.Admin;

import com.jfoenix.controls.*;
import com.mysql.jdbc.PreparedStatement;
import fxsupershop.Connection.connection_Sql;
import fxsupershop.FxSuperShop;
import fxsupershop.Home.Presenter;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class ProfileController implements Initializable {

    @FXML
    private Label name_lavel;
//    @FXML
//    private ImageView profile_img;
    @FXML
    private Circle c;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    LoginMultiFormController lmfc = new LoginMultiFormController();
    private int userID;
    ResultSet rs;
    private int len;
    private FileInputStream fin;
    PreparedStatement post;
    Connection con;
    private ImagePattern pattern;
    @FXML
    private Label user_name;
    @FXML
    private Label email;
    @FXML
    private Label phone;
    @FXML
    private Label address;
//    @FXML
//    private Label ques_ans;
    @FXML
    private Label gender;
//    @FXML
//    private Label password;
    @FXML
    private Label status;
    @FXML
    private Pane Panel1;
    @FXML
    private JFXTextField info_Text1;
//    @FXML
//    private AnchorPane main_pane;
    @FXML
    private AnchorPane stage_pane;
    private String Vfull_name, Vuser_name, Vemail, Vphone, Vaddress, Vgender, Vpassword, Vsecurity, Vans;
    JFXPopup popup = new JFXPopup();
    @FXML
    private Pane Panel2;
    @FXML
    private JFXTextField info_Text2;
    @FXML
    private JFXPasswordField info_pass;
    private int check;
    @FXML
    private Pane Panel3;
    ActionEvent e;
    @FXML
    private JFXComboBox info_combo;
    @FXML
    private Pane Panel4;
    @FXML
    private JFXTextField info_Text3;
    @FXML
    private JFXRadioButton male;
//    @FXML
//    private ToggleGroup gendre;
    @FXML
    private JFXRadioButton female;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        test.play();
        test.setOnFinished((event) -> {
        test.stop();
        });
//        service.start();
//        service.setOnSucceeded((event) -> {
//            service.cancel();
//        });
    }

    Timeline test = new Timeline(new KeyFrame(Duration.seconds(0.5), (ActionEvent event) -> {
        initSource();
    }));
//    Service service = new Service() {
//        @Override
//        protected Task createTask() {
//            return new Task() {
//                @Override
//                protected Void call() {
//                    initSource();
//                    return null;
//                }
//            };
//        }
//    };

    private void initSource() {
        try {
            con = connection_Sql.ConnectDb();
            userID = lmfc.User();
            SetInfo();
            image();
        } catch (Exception e) {
        }
    }

    private void SetInfo() {
        try {
            String sql = "SELECT * FROM createadmin WHERE id = '" + userID + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                name_lavel.setText(rs.getString("company_name"));
                Vfull_name = rs.getString("company_name");
                user_name.setText(rs.getString("name"));
                Vuser_name = rs.getString("name");
                Vpassword = rs.getString("password");
                email.setText(rs.getString("email"));
                Vemail = rs.getString("email");
                phone.setText(rs.getString("phone"));
                Vphone = rs.getString("phone");
                address.setText(rs.getString("Address"));
                Vaddress = rs.getString("Address");
                if (rs.getString("Status").equals("1")) {
                    status.setText("Active State");
                } else {
                    status.setText("Deactive State");
                }
                gender.setText(rs.getString("Gender"));
                Vgender = rs.getString("Gender");
                Vsecurity = rs.getString("Security_question");
                Vans = rs.getString("Answer");
            }
        } catch (Exception e) {
        }
    }

    private void image() {
        try {
//            Image image = imageview();
//            pattern = new ImagePattern(image);
//            c.setFill(pattern);
            Presenter p = new Presenter(queryFunction);
            p.ShowAvatar(pattern, c);
        } catch (Exception e) {
        }
    }

    private void ImageUpload() {
        try {
            File file = queryFunction.service.ImageUpload();
            if (file != null) {
                fin = new FileInputStream(file);
                len = (int) file.length();
                String sql = "UPDATE createadmin SET image = ? WHERE id = '" + userID + "'";
                post = (PreparedStatement) con.prepareStatement(sql);
                post.setBinaryStream(1, fin, len);
                post.execute();
            }

        } catch (Exception e) {

        }
    }

    private void viewSecurity() {
        try {
            String sql = "SELECT * FROM security_question";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                info_combo.getItems().add(rs.getString("question"));
            }
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private void Edit(String query) {
        try {
            String sql = query;
            queryFunction.UpdateMessageLess(sql);
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
        }
    }

    private void Fullname() {
        try {
            if (info_Text1.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Empty Value");
                return;
            }
            String sql = "UPDATE createadmin SET company_name = '" + info_Text1.getText().trim() + "' WHERE id = '" + userID + "'";
            Edit(sql);
            popup.hide();
        } catch (Exception e) {
        }
    }

    private void UserName() {
        try {
            if (info_pass.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Enter Password");
                return;
            }
            if (info_Text2.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Empty Value");
                return;
            }
            if (!info_pass.getText().equals(Vpassword)) {
                queryFunction.service.msg.ConditionalMessage("Password didn't matched");
                return;
            }
            String sql = "UPDATE createadmin SET name = '" + info_Text2.getText().trim() + "' "
                    + "WHERE id = '" + userID + "' AND password = '" + info_pass.getText() + "'";
            Edit(sql);
            popup.hide();
        } catch (Exception e) {
        }
    }

    private void Email() {
        try {
            if (info_Text1.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Empty Value");
                return;
            }
            String sql = "UPDATE createadmin SET email = '" + info_Text1.getText().trim() + "' WHERE id = '" + userID + "'";
            Edit(sql);
            popup.hide();
        } catch (Exception e) {
        }
    }

    private void Phone() {
        try {
            if (info_Text1.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Empty Value");
                return;
            }
            String sql = "UPDATE createadmin SET phone = '" + info_Text1.getText().trim() + "' WHERE id = '" + userID + "'";
            Edit(sql);
            popup.hide();
        } catch (Exception e) {
        }
    }

    private void Status(ActionEvent e) {
        String sql = "UPDATE createadmin SET Status = '" + 0 + "' WHERE id = '" + userID + "'";
        Edit(sql);
        popup.hide();
        try {

            Stage window1 = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Stage stage = new Stage();
            window1.close();
            FxSuperShop b = new FxSuperShop();
            b.start(stage);

        } catch (Exception f) {

        }
    }

    private void Password() {
        try {
            if (info_pass.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Enter Old Password");
                return;
            }
            if (info_Text2.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Enter New Password");
                return;
            }
            if (!info_pass.getText().equals(Vpassword)) {
                queryFunction.service.msg.ConditionalMessage("Password didn't matched");
                return;
            }
            String sql = "UPDATE createadmin SET password = '" + info_Text2.getText().trim() + "' "
                    + "WHERE id = '" + userID + "' AND password = '" + info_pass.getText() + "'";
            Edit(sql);
            popup.hide();
        } catch (Exception e) {
        }
    }

    private void Address() {
        try {
            if (info_Text1.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Empty Value");
                return;
            }
            String sql = "UPDATE createadmin SET Address = '" + info_Text1.getText().trim() + "' WHERE id = '" + userID + "'";
            Edit(sql);
            popup.hide();
        } catch (Exception e) {
        }
    }

    private void Security() {
        try {
            if (info_combo.getValue().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Select One");
                return;
            }
            if (info_Text3.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Enter your value");
                return;
            }

            String sql = "UPDATE createadmin SET Security_question = '" + info_combo.getValue() + "', Answer = '" + info_Text3.getText().trim() + "' "
                    + "WHERE id = '" + userID + "'";
            Edit(sql);
            popup.hide();
        } catch (Exception e) {
        }
    }

    private void Gender() {
        try {
            String g;
            if (male.isSelected()) {
                g = "Male";
            } else {
                g = "Female";
            }
            String sql = "UPDATE createadmin SET Gender = '" + g + "' "
                    + "WHERE id = '" + userID + "'";
            Edit(sql);
            popup.hide();
        } catch (Exception e) {
        }
    }

    @FXML
    private void ImgClick(MouseEvent event) {
    }

    @FXML
    private void imgUpload(ActionEvent event) throws FileNotFoundException {
        ImageUpload();
        image();
    }

    @FXML
    private void UpdateFullName(ActionEvent event) {
        check = 1;
        info_Text1.setText(Vfull_name);
        queryFunction.service.PopUPBlur(Panel1, stage_pane, popup = new JFXPopup(), -400, 190);
    }

    @FXML
    private void UpdateUserName(ActionEvent event) {
        check = 2;
        info_Text2.setText(Vuser_name);
        queryFunction.service.PopUPBlur(Panel2, stage_pane, popup = new JFXPopup(), -400, 190);
    }

    @FXML
    private void UpdateEmail(ActionEvent event) {
        check = 3;
        info_Text1.setText(Vemail);
        queryFunction.service.PopUPBlur(Panel1, stage_pane, popup = new JFXPopup(), -400, 190);
    }

    @FXML
    private void UpdatePhone(ActionEvent event) {
        check = 4;
        info_Text1.setText(Vphone);
        queryFunction.service.PopUPBlur(Panel1, stage_pane, popup = new JFXPopup(), -400, 190);
    }

    @FXML
    private void UpdateAddress(ActionEvent event) {
        check = 6;
        info_Text1.setText(Vaddress);
        queryFunction.service.PopUPBlur(Panel1, stage_pane, popup = new JFXPopup(), -400, 190);
    }

    @FXML
    private void UpdateQuesAns(ActionEvent event) {
        check = 7;
        viewSecurity();
        info_combo.setValue(Vsecurity);
        info_Text3.setText(Vans);
        queryFunction.service.PopUPBlur(Panel4, stage_pane, popup = new JFXPopup(), -400, 190);
    }

    @FXML
    private void UpdateGender(ActionEvent event) {
        try {
            check = 8;
            info_Text1.setVisible(false);
            male.setVisible(true);
            female.setVisible(true);
            if ((Vgender == null) == false) {
                switch (Vgender) {
                    case "Male":
                        male.setSelected(true);
                        break;
                    case "Female":
                        female.setSelected(true);
                        break;
                    default:
                        male.setSelected(true);
                        break;
                }
            }
            queryFunction.service.PopUPBlur(Panel1, stage_pane, popup = new JFXPopup(), -400, 190);
        } catch (Exception e) {
        }
    }

    @FXML
    private void UpdatePassword(ActionEvent event) {
        check = 5;
        info_Text2.setText(null);
        queryFunction.service.PopUPBlur(Panel2, stage_pane, popup = new JFXPopup(), -400, 190);
    }

    @FXML
    private void UpdateStatus(ActionEvent event) {
        queryFunction.service.PopUPBlur(Panel3, stage_pane, popup = new JFXPopup(), -400, 190);
        e = event;
    }

    @FXML
    private void CancelAction(ActionEvent event) {
        try {
            popup.hide();
            male.setVisible(false);
            female.setVisible(false);
            info_Text1.setVisible(true);
        } catch (Exception e) {
        }
    }

    @FXML
    private void OkAction(ActionEvent event) {
        try {
            switch (check) {
                case 1:
                    Fullname();
                    break;
                case 2:
                    UserName();
                    break;
                case 3:
                    Email();
                    break;
                case 4:
                    Phone();
                    break;
                case 5:
                    Password();
                    break;
                case 6:
                    Address();
                    break;
                case 7:
                    Security();
                    break;
                case 8:
                    Gender();
                    break;
            }
            male.setVisible(false);
            female.setVisible(false);
            info_Text1.setVisible(true);
            popup.hide();

            SetInfo();
        } catch (Exception e) {
        }
    }

    @FXML
    private void DeactiveAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Deactive Account");
            alert.setContentText("Are you sure you want to Deactive?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                Status(e);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }
    

}

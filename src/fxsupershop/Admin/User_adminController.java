package fxsupershop.Admin;

import com.jfoenix.controls.*;
import fxsupershop.Connection.connection_Sql;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.AdminView;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class User_adminController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
    @FXML
    private StackPane stackpane;
    @FXML
    private JFXDialogLayout dialogLayout;
    @FXML
    private JFXDialog dialog;
    @FXML
    private JFXButton closebtn;
    @FXML
    private JFXDialogLayout dialogLayout1;
    @FXML
    private JFXDialog dialog1;
    @FXML
    private JFXButton closebtn1;
    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs = null;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField fullname;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXPasswordField conpassword;
    @FXML
    private JFXComboBox admintype;
    @FXML
    private JFXTextField answer;
    @FXML
    private JFXComboBox userstatus;
    @FXML
    private JFXComboBox securityqes;
    @FXML
    private JFXTextArea address;
    String radio = null;
    @FXML
    private ImageView imageview;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField question;
    @FXML
    private TableColumn<AdminView, String> name_col;
    @FXML
    private TableColumn<AdminView, String> email_col;
    @FXML
    private TableColumn<AdminView, String> phone_col;
    @FXML
    private TableColumn<AdminView, String> status_col;
    @FXML
    private TableColumn<AdminView, String> adminType_col;
    @FXML
    private TableColumn<AdminView, String> gender_col;
    @FXML
    private TableColumn<AdminView, String> address_col;
    @FXML
    private TableView<AdminView> tableView;
    ObservableList data = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> id_col;
    @FXML
    private TableColumn<?, ?> question_col;
    @FXML
    private TableView<?> tableview2;
    Message msg = new Message();
    private int len;
    private FileInputStream fin;
    LoginMultiFormController loginMultiFormController = new LoginMultiFormController();
    static int ID;
    private int type, status;
    @FXML
    private Pane controlPane;
    @FXML
    private JFXButton controlID;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    @FXML
    private JFXRadioButton male;
    @FXML
    private JFXRadioButton female;
    @FXML
    private JFXButton subid;
    @FXML
    private JFXButton mainid;
    @FXML
    private JFXButton activeid;
    @FXML
    private JFXButton deactiveid;
    @FXML
    private AnchorPane Bstage;
    int adminID;
//    @FXML
//    private ToggleGroup gender;
//    @FXML
//    private ToggleGroup search;
//    @FXML
//    private ToggleGroup search1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        service.start();
        service.setOnSucceeded((event) -> {
            service.cancel();
        });
    }
//
//    Timeline test = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent event) {
//           
//        }
//    }));
    Service service = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    initSource();
                    return null;
                }
            };
        }
    };

    public void initSource() {
        con = connection_Sql.ConnectDb();
        view();
        securityQuestionView();
        viewSecurity();
        admintype.getItems().addAll("Main Admin", "Sub Admin");
        userstatus.getItems().addAll("Active", "Inactive");
        ID = loginMultiFormController.User();
//        RequiredFieldValidator val = new RequiredFieldValidator();
//        val.setMessage("For Example.");
//        username.getValidators().add(val);
//        username.validate();
    }

    private void viewSecurity() {

        try {
            securityqes.getItems().clear();
            String sql = "SELECT * FROM `security_question`";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {

                securityqes.getItems().addAll(rs.getString("question"));

            }

        } catch (Exception e) {

            msg.ErrorMessage("Unsuccessful", "Error", "View Unsuccessful.\n" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void insert() {
        try {
            if (username.getText().equals("")) {
                msg.WarningMessage("Unsuccessful", "Warning", "Enter User Name.");
                return;
            }
            if (fullname.getText().equals("")) {
                msg.WarningMessage("Unsuccessful", "Warning", "Enter Full Name.");
                return;
            }
            if (password.getText().equals(conpassword.getText())) {
                if (password.getText().length() < 4) {
                    msg.WarningMessage("Unsuccessful", "Warning", "Enter Password greater than 4.");
                    return;
                }
            } else {
                msg.WarningMessage("Unsuccessful", "Warning", "Password do not match.");
                return;
            }
            if (phone.getText().equals("")) {
                msg.WarningMessage("Unsuccessful", "Warning", "Enter Phone Number.");
                return;
            }
            if (admintype.getValue().equals("")) {
                msg.WarningMessage("Unsuccessful", "Warning", "Select Admintype.");
                return;
            }
            if (securityqes.getValue().equals("") || answer.getText().equals("")) {
                msg.WarningMessage("Unsuccessful", "Warning", "Select Security Question and Answer.");
                return;
            }
            if (userstatus.getValue().equals("")) {
                msg.WarningMessage("Unsuccessful", "Warning", "Select User Status.");
                return;
            }
            if (male.isSelected()) {
                radio = "Male";
            } else if (female.isSelected()) {
                radio = "Female";
            } else {
                msg.ConditionalMessage("Please Select your gender");
                return;
            }
            if (admintype.getValue().equals("Main Admin")) {
                type = 1;
            } else {
                type = 0;
                addPriority();
            }
            if (userstatus.getValue().equals("Active")) {
                status = 1;
            } else {
                status = 0;
            }
            String sql = "INSERT INTO createadmin(`id`,`name`,`company_name`,`Password`,"
                    + "`email`,`phone`,`Admin_Type`,`Address`,`Security_question`,"
                    + "`Answer`,`Status`,`Gender`,image) VALUES('" + adminID + "','" + username.getText().trim() + "',"
                    + "'" + fullname.getText().trim() + "','" + password.getText() + "','" + email.getText().trim() + "'"
                    + ",'" + phone.getText() + "','" + type + "',"
                    + "'" + address.getText() + "','" + securityqes.getValue() + "',"
                    + "'" + answer.getText() + "','" + status + "','" + radio + "',?) ";
            post = con.prepareStatement(sql);
            post.setBinaryStream(1, fin, len);
            post.execute();
            if (admintype.getValue().equals("Sub Admin")) {
                JFXPopup pop = new JFXPopup();
                Node node = (StackPane) FXMLLoader.load(getClass().getResource("/fxsupershop/AdminPriority/Priority_list.fxml"));
                node.setStyle("-fx-background-radius: 20; -fx-pref-height: 500; -fx-pref-width: 500;");
                queryFunction.service.PopUPRight(node, Bstage, pop, 0, 0);
            }
            clear();
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Insert Unseccessful.");
        } finally {
            try {
                post.close();
            } catch (Exception e) {

            }
        }
    }

    private void addPriority() {
        try {
            String sql2 = "SELECT MAX(id) AS 'id' FROM createadmin";
            rs = queryFunction.getResult(sql2);
            if (rs.next()) {
                adminID = Integer.parseInt(rs.getString("id"));
                adminID++;
            }
            String sql1 = "SELECT * FROM priority_form_list";
            rs = queryFunction.getResult(sql1);
            while (rs.next()) {
                String sql = "INSERT INTO access_priority (admin_id,form_name,switch)"
                        + "VALUES('" + adminID + "','" + rs.getString("name") + "','" + rs.getString("swh") + "')";
                queryFunction.InsertCustomise(sql, "Have a Problem in Priority.");
            }

        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem in AddPriority." + e);
        }
    }

    private void Delete() {
        String sql = "DELETE FROM createadmin WHERE name='" + username.getText() + "'";
        queryFunction.Delete(sql);
        try {
            String Uid = null;
            String sql2 = "SELECT id FROM createadmin WHERE name='" + username.getText() + "'";
            rs = queryFunction.getResult(sql2);
            if (rs.next()) {
                Uid = rs.getString("id");
            }
            String sql1 = "DELETE FROM access_priority WHERE admin_id = '" + Uid + "'";
            queryFunction.DeleteConditionLess(sql1);
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }

    }

    private void view() {
        data.clear();
        name_col.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        adminType_col.setCellValueFactory(new PropertyValueFactory<>("admintype"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("userstutas"));
        gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
        address_col.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            String sql = "SELECT * FROM `createadmin`";
            rs = queryFunction.getResult(sql);
            String status = null, type = null;
            while (rs.next()) {
                if (rs.getString("status").equals("1")) {
                    status = "Active";
                } else {
                    status = "Deactive";
                }
                if (rs.getString("Admin_Type").equals("1")) {
                    type = "Main Admin";
                } else {
                    type = "Sub Admin";
                }
                data.add(new AdminView(rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        type,
                        status,
                        rs.getString("Gender"),
                        rs.getString("Address")
                ));

            }
            tableView.setItems(data);
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "View Unseccessful!\n" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void securityQuestionInsert() {
        if (id.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Enter ID");
            return;
        }
        if (question.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Enter Question");
            return;
        } else if (id.getText() != null && question.getText() != null) {
            String sql = "INSERT INTO `security_question` (id,question)VALUES ('" + id.getText() + "','" + question.getText() + "')";
            queryFunction.Insert(sql);

        }
    }

    private void securityQuestiondelete() {
        String sql = "DELETE FROM `security_question` WHERE id = '" + id.getText() + "'";
        queryFunction.Delete(sql);
    }

    private void securityQuestionView() {
        data.clear();

        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        question_col.setCellValueFactory(new PropertyValueFactory<>("question"));
        try {

            String sql = "SELECT * FROM `security_question`";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                data.add(new AdminView(rs.getString("id"),
                        rs.getString("question")
                ));

            }
            tableview2.setItems(data);

        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "View Unseccessful!\n" + e);
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void securityQuestionclean() {
        id.setText("");
        question.setText("");
        id.requestFocus();
    }

    private void clear() {
        username.setText("");
        fullname.setText("");
        password.setText("");
        conpassword.setText("");
        phone.setText("");
        email.setText("");
        admintype.getEditor().setText("");
        userstatus.getEditor().setText("");
        securityqes.getEditor().setText("");
        answer.setText("");
        address.setText("");
        male.setSelected(true);
        imageview.setImage(null);
        username.requestFocus();
    }

    private void ImageUpload() {
        try {
            FileChooser filechoose = new FileChooser();
            filechoose.setTitle("Select image");
            filechoose.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
            File selectedfile = filechoose.showOpenDialog(new Stage());
            if (selectedfile != null) {
                String imageFile = selectedfile.toURI().toString();
                Image image = new Image(imageFile);
                imageview.setImage(image);
                fin = new FileInputStream(selectedfile);
                len = (int) selectedfile.length();
            }
        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Warning", "Have a Problem!\n" + e);

        }
    }

    @FXML
    public void report() {
        String path = "/fxsupershop/Admin/";
        String report = "Indivisually_Report.jrxml";
        String sql = "SELECT * FROM `createadmin`";
        queryFunction.report.getReport(path, report, sql);
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        Delete();
        view();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
        view();

    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clear();
    }

    @FXML
    private void onViiew(ActionEvent event) {
        view();
        stackpane.setVisible(true);
        dialogLayout1.setVisible(false);
        dialogLayout.setVisible(true);
        dialog = new JFXDialog(stackpane, dialogLayout, JFXDialog.DialogTransition.RIGHT);
//        dialogpane.setActions(closedialog);
        closebtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                stackpane.setVisible(false);
            }
        });
        dialog.show();
    }

    @FXML
    private void upload(ActionEvent event) {
        ImageUpload();
    }

    @FXML
    private void security(ActionEvent event) {
        securityQuestionView();
        stackpane.setVisible(true);
        dialogLayout.setVisible(false);
        dialogLayout1.setVisible(true);
        dialog1 = new JFXDialog(stackpane, dialogLayout1, JFXDialog.DialogTransition.BOTTOM);
//        dialogpane.setActions(closedialog);
        closebtn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dialog1.close();
                stackpane.setVisible(false);
            }
        });
        dialog1.show();
    }

    @FXML
    private void closestack(MouseEvent event) {
        stackpane.setVisible(false);
    }

    @FXML
    private void report(ActionEvent event) {
        report();
    }

    @FXML
    private void securityaddbtn(ActionEvent event) {
        securityQuestionInsert();
        securityQuestionView();
        viewSecurity();
    }

    @FXML
    private void securitycleanbtn(ActionEvent event) {
        securityQuestionclean();
    }

    @FXML
    private void securitydeletebtn(ActionEvent event) {
        securityQuestiondelete();
        securityQuestionView();
        viewSecurity();
    }

    @FXML
    private void subAction(ActionEvent event) {
        String sql = "UPDATE createadmin SET Admin_Type = '0' WHERE name = '" + username.getText() + "'";
        queryFunction.Update(sql);
    }

    @FXML
    private void mainAction(ActionEvent event) {
        String sql = "UPDATE createadmin SET Admin_Type = '1' WHERE name = '" + username.getText() + "'";
        queryFunction.Update(sql);
    }

    @FXML
    private void activeAction(ActionEvent event) {
        String sql = "UPDATE createadmin SET Status = '1' WHERE name = '" + username.getText() + "'";
        queryFunction.Update(sql);
    }

    @FXML
    private void deactiveAction(ActionEvent event) {
        String sql = "UPDATE createadmin SET Status = '0' WHERE name = '" + username.getText() + "'";
        queryFunction.Update(sql);
    }

    @FXML
    private void controlPopUp(ActionEvent event) {
        try {
            if (username.getText().equals("")) {
                msg.ConditionalMessage("Please Select Admin");
                return;
            }
            String sql = "SELECT * FROM createadmin WHERE name = '" + username.getText() + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                if (rs.getString("Admin_Type").equals("1")) {
                    subid.setDisable(false);
                    mainid.setDisable(true);
                } else {
                    mainid.setDisable(false);
                    subid.setDisable(true);
                }
                if (rs.getString("Status").equals("1")) {
                    activeid.setDisable(true);
                    deactiveid.setDisable(false);
                } else {
                    activeid.setDisable(false);
                    deactiveid.setDisable(true);
                }
            } else {
                msg.ConditionalMessage("Didn't found");
            }
        } catch (Exception e) {
        }
        JFXPopup popup = new JFXPopup();
        queryFunction.service.PopUPRight(controlPane, controlID, popup, 0, 0);
    }

    @FXML
    private void clicked(MouseEvent event) {
        try {
            if (event.getClickCount() == 1) {
                @SuppressWarnings("rawtypes")
                TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                int col = pos.getColumn();
                @SuppressWarnings("rawtypes")
                TableColumn column = pos.getTableColumn();
                String val = column.getCellData(row).toString();

                if (col == 0) {
                    String sql = "SELECT * FROM createadmin WHERE name = '" + val + "'";
                    rs = queryFunction.getResult(sql);
                    if (rs.next()) {
                        username.setText(rs.getString("name"));
                        fullname.setText(rs.getString("company_name"));
                    }
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

}

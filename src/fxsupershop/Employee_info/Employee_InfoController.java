package fxsupershop.Employee_info;

import com.jfoenix.controls.*;
import fxsupershop.Connection.connection_Sql;
import fxsupershop.Product.ProductPresenter;
import fxsupershop.Services.GeneralService;
import fxsupershop.Services.PrepareQueryFunction;
import fxsupershop.TableView.EmployeeView;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import win.zqxu.jrviewer.JRViewerFX;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Employee_InfoController implements Initializable {

    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs;

    ObservableList data = FXCollections.observableArrayList();
    @FXML
    private AnchorPane employeepane;
    @FXML
    private JFXTextField text_employeeID;
    @FXML
    private JFXTextField text_employeeName;
    @FXML
    private JFXTextField text_designation;
    @FXML
    private JFXTextField text_mobileNo;
    @FXML
    private JFXDatePicker text_joining_date;
    @FXML
    private JFXTextField text_email;
    @FXML
    private JFXTextField text_eduQualification;
    @FXML
    private JFXComboBox combo_employeeStatus;
    @FXML
    private JFXTextArea text_address;
    @FXML
    private StackPane stackpane;
    @FXML
    private JFXDialogLayout dialoglayout;
    @FXML
    private JFXRadioButton id_filter;
    @FXML
    private ToggleGroup search;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private JFXButton close;
    @FXML
    private TableView<?> tableview;
    @FXML
    private JFXDialog dialog;
    @FXML
    private TableColumn<?, ?> Tid;
    @FXML
    private TableColumn<?, ?> TEmployeeName;
    @FXML
    private TableColumn<?, ?> TMobileNo;
    @FXML
    private TableColumn<?, ?> TEmail;
    @FXML
    private TableColumn<?, ?> TStatus;
    @FXML
    private JFXComboBox<?> combo_SReport;
    @FXML
    private Pane repID;
    @FXML
    private JFXButton repbtn;
    @FXML
    private Rectangle img_shape;
    GeneralService service = new GeneralService();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    long len;
    static FileInputStream fin = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = fxsupershop.Connection.connection_Sql.ConnectDb();
//         veiwItem();
        view();
        combo_employeeStatus.getItems().addAll("Active", "Deactive");
    }

    public void insert() {
        try {
            if (text_employeeID.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Employee ID", ButtonType.OK).showAndWait();
                return;
            }

            if (text_employeeName.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Employee Name", ButtonType.OK).showAndWait();
                return;
            }
            if (text_designation.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Designation", ButtonType.OK).showAndWait();
                return;
            }

            if (text_joining_date.getValue().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Joining Date", ButtonType.OK).showAndWait();
                return;
            }
            if (text_mobileNo.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Mobile No", ButtonType.OK).showAndWait();
                return;
            }

            if (combo_employeeStatus.getValue().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Select Status", ButtonType.OK).showAndWait();
                return;
            }

            LocalDate objDate = text_joining_date.getValue();

            String sql = "INSERT INTO `employee_info` (`id`,`name`,`designation`,`joining_date`,`phone`,`email`,`education`,`address`,`status`)VALUES('" + text_employeeID.getText() + "','" + text_employeeName.getText() + "','" + text_designation.getText() + "','" + objDate + "','" + text_mobileNo.getText() + "','" + text_email.getText() + "','" + text_eduQualification.getText() + "','" + text_address.getText() + "','" + combo_employeeStatus.getValue() + "')";
            post = con.prepareStatement(sql);
            post.execute();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert1.setTitle("Successful");
            alert1.setContentText("Insert Successful");
            alert1.show();
            clean();
            view();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unsuccessful", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("Insert Unsuccessful\n" + e);
            alert.show();

        }
    }

    public void update() {
        try {
            LocalDate objDate = text_joining_date.getValue();
            String sql = "UPDATE  employee_info SET name='" + text_employeeName.getText() + "',designation='" + text_designation.getText() + "',joining_date='" + objDate + "',phone='" + text_mobileNo.getText() + "',email='" + text_email.getText() + "',education='" + text_eduQualification.getText() + "',address='" + text_address.getText() + "',status='" + combo_employeeStatus.getValue() + "' WHERE id= '" + text_employeeID.getText() + "'";
            post = con.prepareStatement(sql);
            post.execute();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert1.setTitle("Successful");
            alert1.setContentText("Update Successful");
            alert1.show();
            clean();
            view();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unsuccessful", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("Update Unsuccessful\n" + e);
            alert.show();
        }
    }

    void delete() {
        try {
            String sql = "delete from  employee_info WHERE id= '" + text_employeeID.getText() + "'";
            post = con.prepareStatement(sql);
            post.execute();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert1.setTitle("Successful");
            alert1.setContentText("Delete Successful");
            alert1.show();
            clean();
            view();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unsuccessful", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("Delete Unsuccessful\n" + e);
            alert.show();
        }
    }

    public void view() {
        data.clear();
        Tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        TEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TMobileNo.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        TStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        try {
            String sql = "SELECT employee_info.id,employee_info.name,employee_info.designation,employee_info.joining_date,employee_info.phone,employee_info.email,employee_info.status FROM employee_info";
//"INNER JOIN product_item  ON product_category.item_id=product_item.id   order by id asc";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            while (rs.next()) {

                data.add(new EmployeeView(rs.getString("id"),
                        rs.getString("name"), rs.getString("phone"),
                        rs.getString("email"), rs.getString("status")
                ));

            }
            tableview.setItems(data);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unsuccessful", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("View Unsuccessful");
            alert.show();

        }
    }

    public void search() {
        try {
            if (id_filter.isSelected()) {
                data.clear();

                String sql = "SELECT employee_info.id,employee_info.name,employee_info.designation,employee_info.joining_date,employee_info.phone,employee_info.email,employee_info.status FROM employee_info WHERE `name` LIKE '%" + search_filed.getText() + "%' or `id` LIKE '%" + search_filed.getText() + "%' or `phone` LIKE '%" + search_filed.getText() + "%'";
//"INNER JOIN product_item  ON product_category.item_id=product_item.id   WHERE `id`='"+search_filed.getText()+"'  order by id asc";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    data.add(new EmployeeView(rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"), rs.getString("status")
                    ));

                }
                tableview.setItems(data);
            } else if (name_filter.isSelected()) {
                data.clear();

                String sql = "SELECT employee_info.id,employee_info.name,employee_info.designation,employee_info.joining_date,employee_info.phone,employee_info.email,employee_info.status FROM employee_info WHERE `name` LIKE '%" + search_filed.getText() + "%' or `id` LIKE '%" + search_filed.getText() + "%' or `phone` LIKE '%" + search_filed.getText() + "%'";
//"INNER JOIN product_item  ON product_category.item_id=product_item.id WHERE `category_name` LIKE '%"+search_filed.getText()+"%' or `id` like '%"+search_filed.getText()+"%'";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    data.add(new EmployeeView(rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"), rs.getString("status")
                    ));

                }
                tableview.setItems(data);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("Search Unsuccessful");
            alert.show();

        }
    }

    public void Full_Report() {

        connection_Sql con_db = new connection_Sql();

        Connection con = con_db.ConnectDb();

        String path = "G:\\NetBeans Program.java\\fxSuperShop\\src\\fxsupershop\\Employee_info\\";
        String report = path + "Employee_Report.jrxml";

        try {

            JasperDesign jd = JRXmlLoader.load(report);

            String sql = "SELECT * FROM `employee_info`";
            //  JOptionPane.showMessageDialog(null, sql);

            JRDesignQuery deq = new JRDesignQuery();

            deq.setText(sql);

            jd.setQuery(deq);

            JasperReport jr = JasperCompileManager.compileReport(jd);

            JasperPrint pp = JasperFillManager.fillReport(jr, null, con);

            JRViewerFX.preview(new Stage(), pp);

        } catch (JRException ex) {
            // Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

            System.out.println(ex);
        }
    }

    public void clean() {
        text_employeeID.setText(null);
        text_employeeName.setText(null);
        text_designation.setText(null);
        text_mobileNo.setText(null);
        text_email.setText(null);
        text_eduQualification.setText(null);
        text_address.setText(null);
        combo_employeeStatus.getEditor().setText("");
        text_employeeID.requestFocus();
    }
    
    private void ImageUpload() {
        try {
            File file = service.ImageUpload();
            fin = new FileInputStream(file);
            len = file.length();
            if (len <= 0) {
                service.msg.WarningMessage("Unsuccessful", "Warning", "Not a image");
            } else {
                img_shape.setFill(service.getImagepattern(file));
            }
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem" + e);
        }
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        delete();
    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        update();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clean();
    }

    @FXML
    private void onViiew(ActionEvent event) {
        stackpane.setVisible(true);
//       view();
        dialog = new JFXDialog(stackpane, dialoglayout, JFXDialog.DialogTransition.RIGHT);
//        dialogpane.setActions(closedialog);
        close.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                stackpane.setVisible(false);
            }
        });
        dialog.show();
    }

    @FXML
    private void search_keyAction(KeyEvent event) {
        search();
    }

    @FXML
    private void stackclose(MouseEvent event) {
        stackpane.setVisible(false);
    }

    @FXML
    private void TableClick(MouseEvent event) {

        tableview.setEditable(true);
        try {
            if (event.getClickCount() == 1) {
                @SuppressWarnings("rawtypes")
                TablePosition pos = tableview.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                int col = pos.getColumn();
                @SuppressWarnings("rawtypes")
                TableColumn column = pos.getTableColumn();
                String val = column.getCellData(row).toString();

                if (col == 0 || col == 1 || col == 2 || col == 3 || col == 4 || col == 5 || col == 6 || col == 7) {
                    String sql = "SELECT * FROM `employee_info` WHERE `id` = '" + val + "' OR  `name` = '" + val + "' OR `designation` = '" + val + "' OR `phone` = '" + val + "' OR `email` = '" + val + "' OR `education` = '" + val + "' OR `address` = '" + val + "' OR `status` = '" + val + "'";
                    post = con.prepareStatement(sql);
                    rs = post.executeQuery();
                    if (rs.next()) {
                        text_employeeID.setText(rs.getString("id"));
                        text_employeeName.setText(rs.getString("name"));
                        text_designation.setText(rs.getString("designation"));
                        text_joining_date.setValue(rs.getDate("joining_date").toLocalDate());
                        text_mobileNo.setText(rs.getString("phone"));
                        text_email.setText(rs.getString("email"));
                        text_eduQualification.setText(rs.getString("education"));
                        text_address.setText(rs.getString("address"));
                        combo_employeeStatus.setValue(rs.getString("status"));
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    @FXML
    private void Report_Button(ActionEvent event) {

    }

    @FXML
    private void FullReport(ActionEvent event) {
        Full_Report();
    }

    @FXML
    private void ReportAction(ActionEvent event) {
        JFXPopup popup = new JFXPopup();
        ProductPresenter p = new ProductPresenter();
        p.PopUP(repID, repbtn, popup, 0, 0);
    }

    @FXML
    private void uploadbtn(ActionEvent event) {
        ImageUpload();
    }

    @FXML
    private void CleanImage(ActionEvent event) {
    }

    @FXML
    private void OpenPopupImage(MouseEvent event) {
    }

}

package fxsupershop.Company;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import fxsupershop.TableView.BranchView;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.*;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Brance_InfoController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
//
//    @FXML
//    private ToggleGroup search;
    @FXML
    private TableView<BranchView> tableview;
    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    ObservableList<BranchView> data = FXCollections.observableArrayList();

    @FXML
    private JFXRadioButton id_filter;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private JFXTextField text_companyName;
    @FXML
    private JFXTextField text_name;
    @FXML
    private JFXTextField text_mobile_no;
    @FXML
    private StackPane stackpane;
    @FXML
    private JFXDialogLayout dialoglayout;
    @FXML
    private JFXButton close;
    @FXML
    private JFXDialog dialog;
    @FXML
    private JFXTextField text_Brance_ID;
    @FXML
    private JFXTextField text_brance_address;
    @FXML
    private JFXComboBox combo_status;
    @FXML
    private JFXTextField text_email;
    @FXML
    private JFXTextField text_official_no;
//    @FXML
//    private JFXComboBox<?> selectReport;
    @FXML
    private TableColumn<BranchView, Integer> Tid;
    @FXML
    private TableColumn<BranchView, String> TCompanyName;
    @FXML
    private TableColumn<BranchView, String> TName;
    @FXML
    private TableColumn<BranchView, String> TMobileNo;
    @FXML
    private TableColumn<BranchView, String> TEmail;
    @FXML
    private TableColumn<BranchView, String> TOfficialNo;
    @FXML
    private TableColumn<BranchView, String> TStatus;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = fxsupershop.Connection.connection_Sql.ConnectDb();
//         veiwItem();
        view();
        combo_status.getItems().addAll("Active", "Deactive");
    }

    private void insert() {
        if (text_Brance_ID.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Enter Brance Id");
            return;
        }
        if (text_companyName.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Enter Company Name");
            return;
        }
        if (text_name.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Enter Name");
            return;
        }
           
        if (text_official_no.getText().equals("")) {
           queryFunction.service.msg.ConditionalMessage("Enter Official No");
            return;
        }
        if (combo_status.getValue().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Select Status");
            return;
        }
        String sql = "INSERT INTO `branceinfo` (`id`,`company_name`,`name`,`mobileNo`,`branceAdd`,"
                + "`email`,`officialNo`,`status`)VALUES('" + text_Brance_ID.getText() + "',"
                + "'" + text_companyName.getText() + "','" + text_name.getText() + "','" + text_mobile_no.getText() + "',"
                + "'" + text_brance_address.getText() + "','" + text_email.getText() + "','" + text_official_no.getText() + "','" + combo_status.getValue() + "')";
        queryFunction.Insert(sql);
    }

    private void update() {
//            if (text_Brance_ID.getText().equals("")) {
//             Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Brance Id", ButtonType.OK).showAndWait();
//                return;
//            }
//             if (text_companyName.getText().equals("")) {
//               Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Company Name", ButtonType.OK).showAndWait();
//                return;
//            }
//              if (text_name.getText().equals("")) {
//               Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Name", ButtonType.OK).showAndWait();
//                return;
//            }
////               if (text_mobile_no.getText().equals("")) {
////               Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Mobile No", ButtonType.OK).showAndWait();
////                return;
////            }
//                if (text_brance_address.getText().equals("")) {
//               Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Brance Addres", ButtonType.OK).showAndWait();
//                return;
//            }
////                 if (text_email.getText().equals("")) {
////               Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Email", ButtonType.OK).showAndWait();
////                return;
////            }
//                  if (text_official_no.getText().equals("")) {
//               Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Official No", ButtonType.OK).showAndWait();
//                return;
//            }
//              if(combo_status.getEditor().getText().equals("")){
//                 Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Select Status", ButtonType.OK).showAndWait();
//                return;
//            }
//             else if(text_Brance_ID.getText() != null && text_companyName.getText()!= null && text_name.getText() != null && text_brance_address.getText() != null &&text_official_no.getText()!= null && combo_status.getEditor().getText() != null){
            String sql = "UPDATE branceinfo SET company_name='" + text_companyName.getText() + "',name='"
                    + text_name.getText() + "',mobileNo='" + text_mobile_no.getText() + "',branceAdd='"
                    + text_brance_address.getText() + "',email='" + text_email.getText() + "',officialNo='"
                    + text_official_no.getText() + "',status='" + combo_status.getValue() + "' WHERE "
                    + "id= '" + text_Brance_ID.getText() + "'";
            queryFunction.Update(sql);
            clean();
//             }
        
    }

    private void delete() {
            String sql = "delete from  branceinfo WHERE id= '" + text_Brance_ID.getText() + "'";
            queryFunction.Delete(sql);
            clean();
    }
    
    private void initview(String query){
        try {
            String sql = query;
            rs = queryFunction.getResult(sql);
            while (rs.next()) {

                data.add(new BranchView(rs.getString("id"),
                        rs.getString("company_name"),
                        rs.getString("name"),
                        rs.getString("mobileNo"),
                        rs.getString("email"),
                        rs.getString("officialNo"),
                        rs.getString("status")
                ));

            }
            tableview.setItems(data);
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "View Unsuccessful\n"+e);

        }
    }

    private void view() {
        data.clear();
        Tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        TCompanyName.setCellValueFactory(new PropertyValueFactory<>("company_name"));
        TName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TMobileNo.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));

        TEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        TOfficialNo.setCellValueFactory(new PropertyValueFactory<>("officialNo"));
        TStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
            String sql = "SELECT * FROM `branceinfo`";
            initview(sql);
    }

    private void search() {
            if (id_filter.isSelected()) {
                data.clear();

                String sql = "SELECT branceinfo.id,branceinfo.company_name,branceinfo.name,branceinfo.mobileNo,branceinfo.branceAdd,branceinfo.email,branceinfo.officialNo,branceinfo.status FROM branceinfo WHERE `id`='" + search_filed.getText() + "'  order by id asc";
                initview(sql);
            } else if (name_filter.isSelected()) {
                data.clear();

                String sql = "SELECT branceinfo.id,branceinfo.company_name,branceinfo.name,branceinfo.mobileNo,branceinfo.branceAdd,branceinfo.email,branceinfo.officialNo,branceinfo.status FROM branceinfo"
                        + "WHERE `name` LIKE '%" + search_filed.getText() + "%' or `id` LIKE '%" + search_filed.getText() + "%'";
                initview(sql);
            }
        
    }

    private void Full_Report() {

        String path = "/fxsupershop/Company/";
        String report = "Brance_Report.jrxml";
        String sql = "SELECT * FROM `branceinfo`";
        queryFunction.report.getReport(path, report, sql);
    }

//    public void IndivisualReport() {
//        
//        connection_Sql con_db = new connection_Sql();
//        
//        Connection con = con_db.ConnectDb();
//        
//        String path = "G:\\NetBeans Program.java\\fxSuperShop\\src\\fxsupershop\\Company\\";
//        String report = path + "Brance_Report.jrxml";
//        
//        try {
//            
//            JasperDesign jd = JRXmlLoader.load(report);
//            
//            String sql = "SELECT * FROM `branceinfo` WHERE id = (SELECT id FROM branceinfo WHERE company_name = '"+text_companyName.getText()+"')";
//            //  JOptionPane.showMessageDialog(null, sql);
//
//            JRDesignQuery deq = new JRDesignQuery();
//            
//            deq.setText(sql);
//            
//            jd.setQuery(deq);
//            
//            JasperReport jr = JasperCompileManager.compileReport(jd);
//            
//            JasperPrint pp = JasperFillManager.fillReport(jr, null, con);
//            
//            Stage stage = new Stage();
//            JRViewerFX.preview(stage, pp);
//            
//        } catch (JRException ex) {
//            // Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
//
//            System.out.println(ex);
//        }
//    }
    private void clean() {
        text_Brance_ID.setText(null);
        text_companyName.setText(null);
        text_name.setText(null);
        text_mobile_no.setText(null);
        text_brance_address.setText(null);
        text_email.setText(null);
        text_official_no.setText(null);
        combo_status.setValue(null);
        text_Brance_ID.requestFocus();
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        delete();
        view();
    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        update();
        view();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
        view();
    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clean();
    }

    @FXML
    private void onViiew(ActionEvent event) {
        stackpane.setVisible(true);
        view();
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
    private void SetItem(ActionEvent event) {

    }

    @FXML
    private void ShowPressItem(KeyEvent event) {

    }

    @FXML
    private void shown(MouseEvent event) {

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
    private void ViewReport(ActionEvent event) {
        Full_Report();
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

                if (col == 0 || col == 1) {
                    String sql = "SELECT * FROM `branceinfo` WHERE `id` = '" + val + "' OR `company_name` = '" + val + "'";
                    post = con.prepareStatement(sql);
                    rs = post.executeQuery();
                    if (rs.next()) {
                        text_Brance_ID.setText(rs.getString("id"));
                        text_companyName.setText(rs.getString("company_name"));
                        text_name.setText(rs.getString("name"));
                        text_mobile_no.setText(rs.getString("mobileNo"));
                        text_brance_address.setText(rs.getString("branceAdd"));
                        text_email.setText(rs.getString("email"));
                        text_official_no.setText(rs.getString("officialNo"));
                        combo_status.setValue(rs.getString("status"));
                    }
                }
            }
        } catch (Exception e) {
           queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem\n"+e);

        }

    }

    @FXML
    private void SelectedReport(ActionEvent event) {

    }
    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize(); 
    }

}

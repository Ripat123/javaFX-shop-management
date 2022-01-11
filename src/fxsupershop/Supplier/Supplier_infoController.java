package fxsupershop.Supplier;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import fxsupershop.TableView.SupplierView;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class Supplier_infoController implements Initializable {

    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs;
    ObservableList data = FXCollections.observableArrayList();

    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> Tid;
    @FXML
    private TableColumn<?, ?> Tname;
    @FXML
    private TableColumn<?, ?> TSupplier_Phone;
    @FXML
    private TableColumn<?, ?> TCompany_Phone;
    @FXML
    private TableColumn<?, ?> TPresent_Address;
    @FXML
    private JFXComboBox sup_report_fieldID;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    @FXML
    private Pane repPane;
    @FXML
    private JFXTextField text_id;
    @FXML
    private JFXTextField text_name;
    @FXML
    private JFXTextField text_supPhone;
    @FXML
    private JFXTextField text_comPhone;
    @FXML
    private JFXTextArea text_present_Address;
    @FXML
    private JFXTextField text_email;
    @FXML
    private JFXRadioButton id_filter;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private JFXButton reportbtn;
    @FXML
    private ScrollPane scrollpane1;
    @FXML
    private JFXTextField open_bal;
    @FXML
    private ToggleGroup search1;
    int presentID;
    ObservableList rep_supplierList = FXCollections.observableArrayList();
    String supID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = queryFunction.getConnect();
        view();
        showSupplier();
        JFXScrollPane.smoothScrolling(scrollpane1);
    }

    public void showSupplier() {
            String sql = "SELECT * FROM `suplier_info`";
            rep_supplierList = queryFunction.ViewArrayJFXComboBox(sql, "company_name", "id", sup_report_fieldID, rep_supplierList);
            
    }

    private void autoID() {
        presentID = queryFunction.AutoJFXID("suplier_info");
    }

    public void insert() {
        try {
            if (text_name.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Enter Supplier Name");
                return;
            }
            if (text_comPhone.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Enter Company Phone");
                return;
            }
            autoID();
            String sql = "INSERT INTO `suplier_info` (id,`company_name`,`suplier_phone`,"
                    + "`company_phone`,`email`,`present_address`)VALUES('" + presentID + "',"
                    + "'" + text_name.getText().trim() + "','" + text_supPhone.getText().trim() + "',"
                    + "'" + text_comPhone.getText().trim() + "','" + text_email.getText().trim() + "','" + text_present_Address.getText().trim() + "')";
            post = con.prepareStatement(sql);
            post.execute();
            if (!open_bal.getText().equals("") && !open_bal.getText().equals("0")) {
                String sql2 = "INSERT INTO suplier_transaction (suplier_id,transaction_date,"
                        + "transaction_type,debit,credit,balance) VALUES ('" + presentID + "',"
                        + "'" + queryFunction.service.getnonFormatCurrentDate() + "','Opening',"
                        + "'" + open_bal.getText() + "','0','" + open_bal.getText() + "')";
                queryFunction.InsertCustomise(sql2, "Supplier Transaction Insert Unsuccessful");
            }
            queryFunction.service.msg.InformationMessage("Successful", "Information", "Insert Successful");
            clean();

        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Insert Unsuccessful" + e);

        } finally {
            try {
                post.close();
            } catch (Exception e) {

            }
        }
    }

    public void update() {
        try {
            String sql = "update suplier_info set  company_name='" + text_name.getText().trim() + "'"
                    + ",suplier_phone='" + text_supPhone.getText().trim() + "',company_phone="
                    + "'" + text_comPhone.getText().trim() + "',email='" + text_email.getText().trim() + "',"
                    + "present_address='" + text_present_Address.getText().trim() + "' WHERE id= "
                    + "'" + text_id.getText() + "'";
            post = con.prepareStatement(sql);
            post.execute();
            if (!open_bal.getText().equals("") && !open_bal.getText().equals("0")) {
                String sql1 = "SELECT * FROM suplier_transaction WHERE suplier_id = '" + text_id.getText() + ""
                        + "' AND transaction_type = 'Opening'";
                rs = queryFunction.getResult(sql1);
                if(rs.next()){
                    queryFunction.service.msg.WarningMessage("Unsuccessful", "Opening balance already created", "You can't open balance again");
                    return;
                }else{
                    double balance=0;
                    String sql3 = "SELECT balance FROM suplier_transaction WHERE suplier_id = '" + text_id.getText() + "' order by id desc";
                    rs = queryFunction.getResult(sql3);
                    if(rs.next()){
                        balance = Double.parseDouble(rs.getString("balance"));
                    }
                    double net_balance = balance + Double.parseDouble(open_bal.getText());
                    String sql2 = "INSERT INTO suplier_transaction (suplier_id,transaction_date,"
                        + "transaction_type,debit,credit,balance) VALUES ('" + text_id.getText() + "',"
                        + "'" + queryFunction.service.getnonFormatCurrentDate() + "','Opening',"
                        + "'" + open_bal.getText() + "','0','" + net_balance + "')";
                queryFunction.InsertCustomise(sql2, "Supplier Transaction Insert Unsuccessful");
                }
            } 
            queryFunction.service.msg.InformationMessage("Successful", "Information", "Update Successful");
            clean();

        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Update Unsuccessful" + e);
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    void delete() {
            String sql = "delete from suplier_info WHERE id= '" + text_id.getText() + "'";
            queryFunction.Delete(sql);
            clean();
    }

    public void view() {
        data.clear();
        Tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        Tname.setCellValueFactory(new PropertyValueFactory<>("company_name"));
        TSupplier_Phone.setCellValueFactory(new PropertyValueFactory<>("suplier_phone"));
        TCompany_Phone.setCellValueFactory(new PropertyValueFactory<>("company_phone"));
        TPresent_Address.setCellValueFactory(new PropertyValueFactory<>("present_address"));
        try {
            String sql = "SELECT suplier_info.id,suplier_info.company_name,suplier_info.suplier_phone,suplier_info.company_phone,suplier_info.email,suplier_info.present_address,suplier_info.permanent_address FROM suplier_info";
//"INNER JOIN product_item  ON product_category.item_id=product_item.id   order by id asc";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            while (rs.next()) {

                data.add(new SupplierView(rs.getString("id"),
                        rs.getString("company_name"), rs.getString("suplier_phone"),
                        rs.getString("company_phone"), rs.getString("email"),
                        rs.getString("present_address"), rs.getString("permanent_address")
                ));

            }
            tableview.setItems(data);
        } catch (Exception e) {
           queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "View Unsuccessful" + e);

        } finally {
            try {
                rs.close();
                post.close();
            } catch (Exception e) {

            }
        }
    }

    public void search() {
        try {
            if (id_filter.isSelected()) {
                data.clear();

                String sql = "SELECT suplier_info.id,suplier_info.company_name,suplier_info.suplier_phone,suplier_info.company_phone,suplier_info.email,suplier_info.present_address,suplier_info.permanent_address FROM suplier_info WHERE `id` LIKE '%" + search_filed.getText() + "%' or `company_name` LIKE '%" + search_filed.getText() + "%' or `suplier_phone` LIKE '%" + search_filed.getText() + "%'";
//"INNER JOIN product_item  ON product_category.item_id=product_item.id   WHERE `id`='"+search_filed.getText()+"'  order by id asc";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    data.add(new SupplierView(rs.getString("id"),
                            rs.getString("company_name"), rs.getString("suplier_phone"),
                            rs.getString("company_phone"), rs.getString("email"),
                            rs.getString("present_address"), rs.getString("permanent_address")
                    ));

                    text_id.setText(rs.getString("id"));
                    text_name.setText(rs.getString("company_name"));
                    text_supPhone.setText(rs.getString("suplier_phone"));
                    text_comPhone.setText(rs.getString("company_phone"));
                    text_email.setText(rs.getString("email"));
                    text_present_Address.setText(rs.getString("present_address"));

                }
                tableview.setItems(data);
            } else if (name_filter.isSelected()) {
                data.clear();

                String sql = "SELECT suplier_info.id,suplier_info.company_name,suplier_info.suplier_phone,suplier_info.company_phone,suplier_info.email,suplier_info.present_address,suplier_info.permanent_address FROM suplier_info WHERE `id` LIKE '%" + search_filed.getText() + "%' or `company_name` LIKE '%" + search_filed.getText() + "%' or `suplier_phone` LIKE '%" + search_filed.getText() + "%'";
//"INNER JOIN product_item  ON product_category.item_id=product_item.id WHERE `category_name` LIKE '%"+search_filed.getText()+"%' or `id` like '%"+search_filed.getText()+"%'";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    data.add(new SupplierView(rs.getString("id"),
                            rs.getString("company_name"), rs.getString("suplier_phone"),
                            rs.getString("company_phone"), rs.getString("email"),
                            rs.getString("present_address"), rs.getString("permanent_address")
                    ));

                    text_id.setText(rs.getString("id"));
                    text_name.setText(rs.getString("company_name"));
                    text_supPhone.setText(rs.getString("suplier_phone"));
                    text_comPhone.setText(rs.getString("company_phone"));
                    text_email.setText(rs.getString("email"));
                    text_present_Address.setText(rs.getString("present_address"));

                }
                tableview.setItems(data);
            }

        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Search Unsuccessful" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }

    }

    public void clean() {
        text_id.setText("");
        text_name.setText("");
        text_supPhone.setText("");
        text_comPhone.setText("");
        text_email.setText("");
        text_present_Address.setText("");
        search_filed.setText("");
        open_bal.setText("");
        text_name.requestFocus();
    }

    public void Full_Report() {
        String path = "/fxsupershop/Supplier/";
        String report = "Supplyer_Report.jrxml";
        String sql = "SELECT * FROM `suplier_info`";
        queryFunction.report.getReport(path, report, sql);
    }

    public void Indivisually_Report() {
        String path = "/fxsupershop/Supplier/";
        String report = "Indivisually_Supplier_Report.jrxml";
        String sql = "SELECT * FROM `suplier_info` WHERE id = '"+supID+"'";
        queryFunction.report.getReport(path, report, sql);
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        delete();
        view();
        showSupplier();
    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        update();
        view();
        showSupplier();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
        view();
        showSupplier();
    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clean();
    }

    @FXML
    private void onViiew(ActionEvent event) {
        view();
        showSupplier();
    }

    private void TableSearch(KeyEvent event) {

    }

    @FXML
    private void tableClick(MouseEvent event) {
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

                if (col == 0 || col == 1 || col == 2 || col == 3 || col == 4 || col == 5 || col == 6) {
                    String sql = "SELECT * FROM `suplier_info` WHERE `id` = '" + val + "' OR `company_name` = '" + val + "' OR  `suplier_phone` = '" + val + "' OR `company_phone` = '" + val + "' OR `email` = '" + val + "' OR `present_address` = '" + val + "' OR `permanent_address` = '" + val + "'";
                    post = con.prepareStatement(sql);
                    rs = post.executeQuery();
                    if (rs.next()) {
                        text_id.setText(rs.getString("id"));
                        text_name.setText(rs.getString("company_name"));
                        text_supPhone.setText(rs.getString("suplier_phone"));
                        text_comPhone.setText(rs.getString("company_phone"));
                        text_email.setText(rs.getString("email"));
                        text_present_Address.setText(rs.getString("present_address"));
                    }
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unsuccessful", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("View Unsuccessful\n" + e);
            alert.show();

        }
    }

    @FXML
    private void Full_Reports(ActionEvent event) {
        Full_Report();
    }

    @FXML
    private void Indivisually_Reports(ActionEvent event) {
        Indivisually_Report();
    }

    @FXML
    private void sup_Report_actionkey(KeyEvent event) {
        supID = rep_supplierList.get(sup_report_fieldID.getSelectionModel().getSelectedIndex()).toString();
    }

    @FXML
    private void Search(KeyEvent event) {
        search();
    }

    @FXML
    private void Report(ActionEvent event) {
        JFXPopup popup = new JFXPopup();
        queryFunction.service.PopUPRight(repPane, reportbtn, popup, 0, 0);
    }

}

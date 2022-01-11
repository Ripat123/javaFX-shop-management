package fxsupershop.Bank_Info;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import fxsupershop.TableView.BankViews;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Bank_InfoController implements Initializable {

    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs;
    ObservableList data = FXCollections.observableArrayList();
//    @FXML
//    private AnchorPane itempane;
    @FXML
    private JFXTextField text_bank_name;
    @FXML
    private JFXTextField text_mobile_no;
    @FXML
    private JFXComboBox combo_account_name;
    @FXML
    private JFXComboBox combo_BranceName;
    @FXML
    private JFXTextField text_ac_no;
    @FXML
    private JFXTextArea textarea_address;
    @FXML
    private JFXDatePicker datepick;
    @FXML
    private JFXRadioButton id_filter;
//    @FXML
//    private ToggleGroup search;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> TBranceName;
    @FXML
    private TableColumn<?, ?> TBankName;
    @FXML
    private TableColumn<?, ?> TAc_No;
    @FXML
    private TableColumn<?, ?> TAccountType;
    @FXML
    private TableColumn<?, ?> TMobileNo;
    @FXML
    private TableColumn<?, ?> TAddress;
    @FXML
    private ScrollPane scroll;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = fxsupershop.Connection.connection_Sql.ConnectDb();
        veiwItem();
        view();
        combo_account_name.getItems().addAll("Saving account", "Current account");
        JFXScrollPane.smoothScrolling(scroll);
    }

    private void veiwItem() {
        String sql = "SELECT * FROM `branceinfo`";
        queryFunction.ViewItemOnJFXComboBox(sql, "name", combo_BranceName);

    }

    private void insert() {
            if (combo_BranceName.getEditor().getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Select Brance Name");
                return;
            }
//             if (datepick.getDayCellFactory().equals("")) {
//               Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Date", ButtonType.OK).showAndWait();
//                return;
//            }
            if (text_bank_name.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Enter Bank Name");
                return;
            }

            if (text_ac_no.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Enter AC/No");
                return;
            }
            if (combo_account_name.getValue().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Select Account Name");
                return;
            }

            if (textarea_address.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Enter Address");
                return;
            }
            if (text_mobile_no.getText().equals("")) {
                queryFunction.service.msg.ConditionalMessage("Enter Mobile No");
                return;
            }

            LocalDate obDate = datepick.getValue();

            String sql = "INSERT INTO `bank_info`(`brance_name`,`date`,`bank_name`,`ac_no`,`type`,`add`,`mbl_no`)VALUES('" + combo_BranceName.getEditor().getText() + "','" + obDate + "','" + text_bank_name.getText() + "','" + text_ac_no.getText() + "','" + combo_account_name.getValue() + "','" + textarea_address.getText() + "','" + text_mobile_no.getText() + "')";
            queryFunction.Insert(sql);
            clean();
            view();

    }

    private void update() {
            LocalDate date = datepick.getValue();
            String sql = "UPDATE bank_info SET brance_name='" + combo_BranceName.getEditor().getText() + "',"
                    + "date='" + date + "',bank_name='" + text_bank_name.getText() + "',"
                    + "type='" + combo_account_name.getValue() + "',add='" + textarea_address.getText() + "',"
                    + "mbl_no='" + text_mobile_no.getText() + "' WHERE ac_no= '" + text_ac_no.getText() + "'";
            queryFunction.Update(sql);
            
    }

    private void delete() {
            String sql = "delete from  bank_info WHERE ac_no= '" + text_ac_no.getText() + "'";
            queryFunction.Delete(sql);
            
    }
    
    private void initview(String query){
        try {
            String sql = query;
            rs = queryFunction.getResult(sql);
            while (rs.next()) {

                data.add(new BankViews(rs.getString("brance_name"),
                        rs.getString("bank_name"), rs.getString("ac_no"),
                        rs.getString("type"), rs.getString("add"), rs.getString("mbl_no")
                ));

            }
            tableview.setItems(data);
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n"+e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void view() {
        data.clear();
        TBranceName.setCellValueFactory(new PropertyValueFactory<>("brance_name"));
        TBankName.setCellValueFactory(new PropertyValueFactory<>("bank_name"));
        TAc_No.setCellValueFactory(new PropertyValueFactory<>("ac_no"));
        TAccountType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TAddress.setCellValueFactory(new PropertyValueFactory<>("add"));
        TMobileNo.setCellValueFactory(new PropertyValueFactory<>("mbl_no"));

            String sql = "SELECT `brance_name`,`bank_name`,`ac_no`,`type`,`add`,`mbl_no` FROM `bank_info`";
            initview(sql);
    }

    private void search() {
            if (id_filter.isSelected()) {
                data.clear();

                String sql = "SELECT bank_info.brance_name,bank_info.bank_name,bank_info.ac_no,bank_info.type,bank_info.add,bank_info.mbl_no FROM bank_info WHERE `bank_name` LIKE '%" + search_filed.getText() + "%' or `ac_no` like '%" + search_filed.getText() + "%' or `mbl_no` like '%" + search_filed.getText() + "%'";
                initview(sql);
            } else if (name_filter.isSelected()) {
                data.clear();

                String sql = "SELECT bank_info.brance_name,bank_info.bank_name,bank_info.ac_no,bank_info.type,bank_info.add,bank_info.mbl_no FROM bank_info WHERE `bank_name` LIKE '%" + search_filed.getText() + "%' or `ac_no` like '%" + search_filed.getText() + "%' or `mbl_no` like '%" + search_filed.getText() + "%'";
                initview(sql);
            }
    }

    private void Full_Report() {
        String sql = "SELECT * FROM `bank_info`";
        String path = "/fxsupershop/Bank_Info/";
        queryFunction.report.getReport(path, "bankInfo.jrxml", sql);
    }

    private void clean() {
        combo_BranceName.getEditor().setText("");
        text_bank_name.setText(null);
        text_ac_no.setText(null);
        combo_account_name.getEditor().setText("");
        textarea_address.setText(null);
        text_mobile_no.setText(null);
        combo_BranceName.requestFocus();
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
        view();
    }

    @FXML
    private void SetItem(ActionEvent event) {

    }

    @FXML
    private void ShowPressItem(KeyEvent event) {
        String sql = "SELECT * FROM `branceinfo` WHERE name LIKE '%"+combo_BranceName.getValue()+"%'";
        queryFunction.ShowJFXItemOnkeyReleased(sql, "name", combo_BranceName,event);
    }

    @FXML
    private void shown(MouseEvent event) {
    }

    @FXML
    private void search_keyAction(KeyEvent event) {
        search();
    }

    @FXML
    private void TableView(MouseEvent event) {
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
                    String sql = "SELECT * FROM `bank_info` WHERE `brance_name` = '" + val + "' OR  `bank_name` = '" + val + "' OR `ac_no` = '" + val + "' OR `type` = '" + val + "' OR `add` = '" + val + "' OR `mbl_no` = '" + val + "' OR `add` = '" + val + "'";
                    rs = queryFunction.getResult(sql);
                    if (rs.next()) {
                        combo_BranceName.getEditor().setText(rs.getString("brance_name"));
                        datepick.setValue(rs.getDate("date").toLocalDate());
                        text_bank_name.setText(rs.getString("bank_name"));
                        text_ac_no.setText(rs.getString("ac_no"));
                        combo_account_name.setValue(rs.getString("type"));
                        textarea_address.setText(rs.getString("add"));
                        text_mobile_no.setText(rs.getString("mbl_no"));
                    }
                }
            }
        } catch (Exception e) {

        }

    }

    @FXML
    private void Report_Button(ActionEvent event) {
        Full_Report();
    }

}

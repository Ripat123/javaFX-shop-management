package fxsupershop.Bank_Transaction;

import com.jfoenix.controls.*;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.PrepareQueryFunction;
import fxsupershop.TableView.Transaction;
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
 * @author PC
 */
public class Bank_TransactionController implements Initializable {

//    @FXML
//    private ToggleGroup search;
//    private TableView tableview;
    ResultSet rs;
    ObservableList data = FXCollections.observableArrayList();
//    @FXML
//    private JFXRadioButton id_filter;
//    @FXML
//    private JFXRadioButton name_filter;
//    @FXML
//    private JFXTextField search_filed;
    @FXML
    private JFXTextField text_ac_no;
    @FXML
    private JFXTextField text_cheack_voucher_no;
    @FXML
    private JFXComboBox combo_transaction_type;
    @FXML
    private JFXComboBox<?> combo_bank_name;
    @FXML
    private JFXTextField text_amount;
    @FXML
    private JFXTextArea textarea_address;
    @FXML
    private TableView<?> tableview1;
    @FXML
    private TableColumn<?, ?> Tid1;
    @FXML
    private ScrollPane scroll;
    @FXML
    private JFXDatePicker date_field;
    @FXML
    private TableColumn<?, ?> acNO;
    @FXML
    private TableColumn<?, ?> transaction;
    @FXML
    private TableColumn<?, ?> check;
    @FXML
    private TableColumn<?, ?> amount;
    @FXML
    private TableColumn<?, ?> date;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
//    @FXML
//    private JFXDatePicker date_field1;
    @FXML
    private Pane veiwPnae;
    @FXML
    private Pane stageView;
    @FXML
    private JFXTextField balance;
    Calendar cal = new GregorianCalendar();
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int month = cal.get(Calendar.MONTH) + 1;
    int year = cal.get(Calendar.YEAR);
    private String transactionID;
    LoginMultiFormController lmfc = new LoginMultiFormController();
    private int userID;
    private double C_balance;
    @FXML
    private JFXDatePicker date1;
    @FXML
    private JFXDatePicker date2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        viewbankname();
        view();
        JFXScrollPane.smoothScrolling(scroll);
        combo_transaction_type.getItems().addAll("Saving", "Withdraw", "Bank Cost");
        userID = lmfc.User();
    }

    public void viewbankname() {
        String sql = "SELECT * FROM bank_info";
        queryFunction.ViewItemOnJFXComboBox(sql, "bank_name", combo_bank_name);
    }

    private void initID(String query,String tag) {
        try {

            String sql = query;
            rs = queryFunction.getResult(sql);

            if (rs.next()) {

                String s = rs.getString("id");
                String str[] = s.split("-");

                int id = Integer.parseInt(str[4]) + 1;

                if (id <= 9) {
                    transactionID = (tag+"-" + "" + day + "-" + month + "-" + year + "-0" + Integer.toString(id));
                } else {
                    transactionID = (tag+"-" + "" + day + "-" + month + "-" + year + "-" + Integer.toString(id));
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

        if (transactionID == null) {
            transactionID = (tag+"-" + "" + day + "-" + month + "-" + year + "-01");
        }
    }

    private void DepositID() {
        String sql = "SELECT id FROM bank_management WHERE transaction_type='Saving' order by id desc ";
        initID(sql,"S");
    }

    private void Withdrawal() {
        String sql = "SELECT id FROM bank_management WHERE transaction_type='Withdraw' order by id desc ";
        initID(sql,"W");
    }

    private void CostID() {
        String sql = "SELECT id FROM bank_management WHERE transaction_type='Bank Cost' order by id desc";
        initID(sql,"C");
    }

    private void insert() {
        String sql = "INSERT INTO bank_management(id,bank_name,date,ac_no,transaction_type,"
                + "voucherNo,ammount,address,fk_user_id)VALUES('" + transactionID + "',"
                + "'" + combo_bank_name.getValue() + "','" + queryFunction.service.getJFXDate(date_field) + "',"
                + "'" + text_ac_no.getText() + "','" + combo_transaction_type.getValue() + "',"
                + "'" + text_cheack_voucher_no.getText() + "','" + text_amount.getText() + "',"
                + "'" + textarea_address.getText().trim() + "','" + userID + "')";
        queryFunction.Insert(sql);
    }

//    private void SaveandReport() {
//
//    }

    private void BalanceSubmit() {
        try {
            String sql = "SELECT * FROM bank_balance WHERE bank_name = '" + combo_bank_name.getValue() + "' "
                    + "AND ac_No = '" + text_ac_no.getText() + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                String sql1 = "UPDATE bank_balance SET balance = '" + balance.getText() + "'"
                        + ",admin_id = '" + userID + "',updated_at = '" + queryFunction.service.getDateTime() + "' "
                        + "WHERE bank_name = '" + combo_bank_name.getValue() + "' AND ac_No = '" + text_ac_no.getText() + "'";
                queryFunction.UpdateMessageLess(sql1);
            } else {
                String sql2 = "INSERT INTO bank_balance (bank_name,ac_No,balance,"
                        + "admin_id,created_at) VALUES ('" + combo_bank_name.getValue() + "',"
                        + "'" + text_ac_no.getText() + "','" + balance.getText() + "',"
                        + "'" + userID + "','" + queryFunction.service.getDateTime() + "')";
                queryFunction.InsertCustomise(sql2, "Have a Problem in balance insert.");
            }
            clean();
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem in Balance Submit.");
        }
    }
    
    private void initView(String query){
        data.clear();
        Tid1.setCellValueFactory(new PropertyValueFactory<>("id"));
        acNO.setCellValueFactory(new PropertyValueFactory<>("ac_no"));
        transaction.setCellValueFactory(new PropertyValueFactory<>("transaction_type"));
        check.setCellValueFactory(new PropertyValueFactory<>("voucherNo"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        try {
            String sql = query;
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                data.add(new Transaction(rs.getString("id"),
                        rs.getString("ac_no"),
                        rs.getString("transaction_type"),
                        rs.getString("voucherNo"),
                        rs.getString("ammount"),
                        rs.getString("date")));
            }
            tableview1.setItems(data);
        } catch (Exception e) {
        }
        finally{
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {
            }
        }
    }

    private void view() {
            String sql = "SELECT * FROM bank_management";
            initView(sql);
    }

//    private void search() {
//
//    }
    
    private void DateSearch(){
        String sql = "SELECT * FROM bank_management WHERE date BETWEEN '"
                + ""+queryFunction.service.getJFXDate(date1)+"' AND '"+queryFunction.service.getJFXDate(date2)+"'";
        initView(sql);
    }

    private void clean() {
        combo_bank_name.setValue(null);
        combo_bank_name.requestFocus();
        date_field.setValue(null);
        text_ac_no.setText(null);
        combo_transaction_type.setValue(null);
        text_cheack_voucher_no.setText(null);
        text_amount.setText(null);
        balance.setText("");
        textarea_address.setText(null);
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        if (combo_bank_name.getValue().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Empty Bank Name");
            return;
        }
        if (date_field.getValue() == null) {
            queryFunction.service.msg.ConditionalMessage("Empty Date");
            return;
        }
        if (text_ac_no.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Empty Account No");
            return;
        }
        if (combo_transaction_type.getValue().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Empty Transaction Type");
            return;
        }
        if (text_cheack_voucher_no.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Empty Check No");
            return;
        }
        if (text_amount.getText().equals("") && balance.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Empty Amount and Balance");
            return;
        }
        insert();
        BalanceSubmit();
    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clean();
    }

    @FXML
    private void onViiew(ActionEvent event) {
        view();
        veiwPnae.setVisible(true);
        JFXPopup pop = new JFXPopup();
        queryFunction.service.PopUPBottom(veiwPnae, stageView, pop, 0, 0);
    }

    @FXML
    private void SetItem(ActionEvent event) {
        String sql = "SELECT ac_no FROM bank_info WHERE bank_name = '" + combo_bank_name.getValue() + "'";
        queryFunction.ViewSingleItemOnJFXTextfield(sql, "ac_no", text_ac_no);

    }

    @FXML
    private void ShowPressItem(KeyEvent event) {
    }

    @FXML
    private void shown(MouseEvent event) {
//        ItemName_cat.show();
    }

    @FXML
    private void search_keyAction(KeyEvent event) {
//        search();
    }

    @FXML
    private void TypeAction(ActionEvent event) {
        if (combo_transaction_type.getValue().equals("Saving")) {
            transactionID = null;
            DepositID();
        } else if (combo_transaction_type.getValue().equals("Withdraw")) {
            transactionID = null;
            Withdrawal();
        } else if (combo_transaction_type.getValue().equals("Bank Cost")) {
            transactionID = null;
            CostID();
        }
        try {
            String sql = "SELECT * FROM bank_balance WHERE bank_name = '" + combo_bank_name.getValue() + "' "
                    + "AND ac_No = '" + text_ac_no.getText() + "'";
            String s = queryFunction.getDataInSVeriable(sql, "balance");
            if (s == null) {
                balance.setText("0.0");
                C_balance = 0;
            } else {
                balance.setText(s);
                C_balance = Double.parseDouble(s);
            }
        } catch (Exception e) {

        }

    }

    @FXML
    private void SaveReport(ActionEvent event) {
    }

    @FXML
    private void amountReleased(KeyEvent event) {
        try {
            if (combo_transaction_type.getValue().equals("Saving")) {
                double Samount = Double.parseDouble(text_amount.getText());
                double total_balance = C_balance + Samount;
                balance.setText(String.valueOf(total_balance));
            } else if (combo_transaction_type.getValue().equals("Withdraw") || combo_transaction_type.getValue().equals("Bank Cost")) {
                double bal, Samount = 0, total_balance;
                bal = Double.parseDouble(text_amount.getText());
                if (bal <= C_balance) {
                    Samount = Double.parseDouble(text_amount.getText());
                    total_balance = C_balance - Samount;
                    balance.setText(String.valueOf(total_balance));
                } else {
                    text_amount.setText(String.valueOf(Samount));
                    balance.setText(String.valueOf(C_balance));
                    queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Insufficient balance");
                }

            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void DateSearch(ActionEvent event) {
        DateSearch();
    }

}

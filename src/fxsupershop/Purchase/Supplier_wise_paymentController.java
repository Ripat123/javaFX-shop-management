package fxsupershop.Purchase;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;

/**
 * FXML Controller class
 *
 * @author Rifat Rabbi
 */
public class Supplier_wise_paymentController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private JFXDatePicker entryDate;
    @FXML
    private JFXComboBox supplier;
    @FXML
    private JFXTextField totalID;
    @FXML
    private JFXTextArea textarea_comment;
    @FXML
    private JFXDatePicker paymentDate;
    @FXML
    private JFXTextField pay_amount;
    @FXML
    private JFXTextField dueID;
//    @FXML
//    private JFXToggleButton toggleID;
//    @FXML
//    private TableView<?> tableview;
//    @FXML
//    private TableColumn<?, ?> TInvoiceNo;
//    @FXML
//    private TableColumn<?, ?> TDate;
//    @FXML
//    private TableColumn<?, ?> T_TotalAmount;
//    @FXML
//    private TableColumn<?, ?> T_paymentDate;
//    @FXML
//    private TableColumn<?, ?> TpaidAmount;
//    @FXML
//    private TableColumn<?, ?> T_dueAmount;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    String supplierID;
    ObservableList supplierlist = FXCollections.observableArrayList();
    ResultSet rs, rss;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXScrollPane.smoothScrolling(scroll);
        showSupplier();
    }

    private void showSupplier() {
        String sql = "SELECT company_name,id FROM suplier_info";
        supplierlist = queryFunction.ViewArrayJFXComboBox(sql, "company_name", "id", supplier, supplierlist);
    }

    private void supplier_action() {
        try {
            supplierID = supplierlist.get(supplier.getSelectionModel().getSelectedIndex()).toString();
            String sql = "SELECT * FROM suplier_transaction WHERE suplier_id = '" + supplierID + "' order by id desc";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                totalID.setText(rs.getString("balance"));
                entryDate.setValue(rs.getDate("transaction_date").toLocalDate());
                textarea_comment.setText("Type Comments...");
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

    private void supplier_released(KeyEvent event) {
        String sql = "SELECT company_name,id FROM suplier_info WHERE "
                + "company_name LIKE '%" + supplier.getValue() + "%'";
        supplierlist = queryFunction.ShowArrayItemKeyReleased(sql, "company_name", "id", supplier, event, supplierlist);
    }

    private void clear() {
        supplier.setValue(null);
        entryDate.setValue(null);
        totalID.setText(null);
        textarea_comment.setText(null);
        paymentDate.setValue(null);
        pay_amount.setText(null);
        dueID.setText(null);
        supplier.requestFocus();
    }

    private void submit() {

        double balance = 0, netbalance = 0;
        try {
            String sql = "SELECT * FROM suplier_transaction WHERE suplier_id = '" + supplierID + "' ORDER BY id DESC";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                balance = Double.parseDouble(rs.getString("balance"));
            }
            netbalance = balance;
            double totalDue = netbalance - Double.parseDouble(pay_amount.getText());
            String sql2 = "INSERT INTO suplier_transaction (suplier_id,transaction_date,"
                    + "invoice_id,voucher_no,transaction_type,debit,credit,balance)VALUES ("
                    + "'" + supplierID + "','" + paymentDate.getValue() + "',"
                    + "'Supplier Pay','Paid','Cash','0',"
                    + "'" + pay_amount.getText() + "','" + totalDue + "')";
            queryFunction.InsertCustomise(sql2, "Have a Problem in Supplier Transaction");

        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private void submitPayment() {
        double payAmount = Double.parseDouble(pay_amount.getText());
        try {
            String sql = "SELECT * FROM purchase_ledger WHERE suplier_id = '" + supplierID + "'";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                if (payAmount > 0) {
                    
                    String sql1 = "SELECT * FROM purchase_payment_statement WHERE invoice_no = '" + rs.getString("invoice_no") + "' AND due != '0'";
                    rss = queryFunction.getResult(sql1);
                    if (rss.next()) {
                        double due = Double.parseDouble(rss.getString("due"));
                        double netDue = due - payAmount;
                        
                        if (String.valueOf(netDue).contains("-")) {
                            
                            String p = String.valueOf(netDue).replace("-", "");
                            double pay = payAmount - Double.parseDouble(p);
                            payAmount = Double.parseDouble(p);
                            
                            String sql2 = "UPDATE purchase_payment_statement SET payment = payment + '" + pay + "',"
                                    + "due = '0' WHERE invoice_no = '" + rss.getString("invoice_no") + "'";
                            queryFunction.UpdateMessageLess(sql2);
                            
                        } else if (!String.valueOf(netDue).contains("-") && netDue != 0) {
                            String sql3 = "UPDATE purchase_payment_statement SET payment = payment + '" + payAmount + "',"
                                    + "due = '" + netDue + "' WHERE invoice_no = '" + rss.getString("invoice_no") + "'";
                            queryFunction.UpdateMessageLess(sql3);
                            break;
                            
                        } else if (netDue == 0) 
                            break;
                    }
                }else
                    break;
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void supplierAction(ActionEvent event) {
        supplier_action();
    }

    @FXML
    private void SupplierReleased(KeyEvent event) {
        supplier_released(event);
    }

    @FXML
    private void paidRelesed(KeyEvent event) {
        try {
            double total_due = Double.parseDouble(totalID.getText());
            double total_paid = Double.parseDouble(pay_amount.getText());
            double due = total_due - total_paid;
            dueID.setText(String.valueOf(due));
        } catch (Exception e) {
        }
    }

    @FXML
    private void Submit_btn(ActionEvent event) {
        submit();
        submitPayment();
        clear();
    }

    @FXML
    private void ToggleAction(ActionEvent event) {
    }

    @FXML
    private void clearAction(ActionEvent event) {
        clear();
    }

    @FXML
    private void Table_Click(MouseEvent event) {
    }

}

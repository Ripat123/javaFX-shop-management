
package fxsupershop.Sales_cus_pay;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;

/**
 * FXML Controller class
 *
 * @author Rifat Rabbi
 */
public class Customer_paymentController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private JFXDatePicker entryDate;
    @FXML
    private JFXTextField totalID;
    @FXML
    private JFXTextArea textarea_comment;
    @FXML
    private JFXComboBox<?> cus_id;
    @FXML
    private JFXDatePicker paymentDate;
    @FXML
    private JFXTextField pay_amount;
    @FXML
    private JFXTextField dueID;
    @FXML
    private JFXToggleButton toggleID;
    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> TInvoiceNo;
    @FXML
    private TableColumn<?, ?> TDate;
    @FXML
    private TableColumn<?, ?> T_TotalAmount;
    @FXML
    private TableColumn<?, ?> T_paymentDate;
    @FXML
    private TableColumn<?, ?> TpaidAmount;
    @FXML
    private TableColumn<?, ?> T_dueAmount;
    ObservableList CustomerList = FXCollections.observableArrayList();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    ResultSet rs,rss;
    String customerID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXScrollPane.smoothScrolling(scroll);
        showCustomer();
    }    
    
    private void showCustomer() {
        String sql = "SELECT customer_name,id FROM customer_info";
        CustomerList = queryFunction.ViewArrayJFXComboBox(sql, "customer_name", "id", cus_id, CustomerList);
    }
    
    private void Customer_action() {
        try {
            customerID = CustomerList.get(cus_id.getSelectionModel().getSelectedIndex()).toString();
            String sql = "SELECT * FROM customer_transaction WHERE customer_id = '" + customerID + "' order by id desc";
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
    
    private void Customer_released(KeyEvent event) {
        String sql = "SELECT customer_name,id FROM customer_info WHERE "
                + "customer_name LIKE '%" + cus_id.getValue() + "%'";
        CustomerList = queryFunction.ShowArrayItemKeyReleased(sql, "customer_name", "id", cus_id, event, CustomerList);
    }
    
    private void clear() {
        cus_id.setValue(null);
        entryDate.setValue(null);
        totalID.setText(null);
        textarea_comment.setText(null);
        paymentDate.setValue(null);
        pay_amount.setText(null);
        dueID.setText(null);
        cus_id.requestFocus();
    }
    
    private void submit() {

        double balance = 0, netbalance = 0;
        try {
            String sql = "SELECT * FROM customer_transaction WHERE customer_id = '" + customerID + "' ORDER BY id DESC";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                balance = Double.parseDouble(rs.getString("balance"));
            }
            netbalance = balance;
            double totalDue = netbalance - Double.parseDouble(pay_amount.getText());
            String sql2 = "INSERT INTO customer_transaction (customer_id,transaction_date,"
                    + "invoice_id,voucher_no,transaction_type,debit,credit,balance)VALUES ("
                    + "'" + customerID + "','" + paymentDate.getValue() + "',"
                    + "'Customer Pay','Paid','Cash','0',"
                    + "'" + pay_amount.getText() + "','" + totalDue + "')";
            queryFunction.InsertCustomise(sql2, "Have a Problem in Customer Transaction");

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
            String sql = "SELECT * FROM sale_ledger WHERE customer_id = '" + customerID + "'";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                if (payAmount > 0) {
                    
                    String sql1 = "SELECT * FROM sale_payment_statement WHERE invoice_no = '" + rs.getString("invoice_id") + "' AND due != '0'";
                    rss = queryFunction.getResult(sql1);
                    if (rss.next()) {
                        double due = Double.parseDouble(rss.getString("due"));
                        double netDue = due - payAmount;
                        
                        if (String.valueOf(netDue).contains("-")) {
                            
                            String p = String.valueOf(netDue).replace("-", "");
                            double pay = payAmount - Double.parseDouble(p);
                            payAmount = Double.parseDouble(p);
                            
                            String sql2 = "UPDATE sale_payment_statement SET payment = payment + '" + pay + "',"
                                    + "due = '0' WHERE invoice_no = '" + rss.getString("invoice_no") + "'";
                            queryFunction.UpdateMessageLess(sql2);
                            
                        } else if (!String.valueOf(netDue).contains("-") && netDue != 0) {
                            String sql3 = "UPDATE sale_payment_statement SET payment = payment + '" + payAmount + "',"
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
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                rss.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
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

    @FXML
    private void customer_action(ActionEvent event) {
        Customer_action();
    }

    @FXML
    private void customerReleased(KeyEvent event) {
        Customer_released(event);
    }
    
}

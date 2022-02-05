package fxsupershop.Sales;

import com.jfoenix.controls.*;
import fxsupershop.Services.*;
import fxsupershop.TableView.SalePaymentView;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class Sales_paymentController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private JFXDatePicker entryDate;
    @FXML
    private JFXComboBox<?> invoice_id;
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
    @FXML
    private TableView<SalePaymentView> tableview;
    @FXML
    private TableColumn<?, ?> TInvoiceNo;
    @FXML
    private TableColumn<?, ?> TDate;
    GeneralService service = new GeneralService();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    Report report = new Report();
    Message msg = new Message();
    ResultSet rs = null;
    ObservableList<SalePaymentView> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> T_TotalAmount;
    @FXML
    private TableColumn<?, ?> T_paymentDate;
    @FXML
    private TableColumn<?, ?> TpaidAmount;
    @FXML
    private TableColumn<?, ?> T_dueAmount;
    @FXML
    private JFXComboBox<?> cus_id;
    ObservableList customerList = FXCollections.observableArrayList();
    String customerID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        JFXScrollPane.smoothScrolling(scroll);
        showCustomer();
        view();
    }

    private void showCustomer() {
        String sql = "SELECT customer_name,id FROM customer_info";
        customerList = queryFunction.ViewArrayJFXComboBox(sql, "customer_name", "id", cus_id, customerList);
    }

    private void customer_released(KeyEvent event) {
        try {
            String sql = "SELECT customer_name,id FROM customer_info WHERE "
                    + "customer_name LIKE '%" + cus_id.getValue() + "%'";
            customerList = queryFunction.ShowArrayItemKeyReleased(sql, "customer_name", "id", cus_id, event, customerList);
        } catch (Exception e) {
        }
    }

    private void Customer_action() {
        try {
            customerID = customerList.get(cus_id.getSelectionModel().getSelectedIndex()).toString();
            String sql = "SELECT * FROM sale_ledger WHERE customer_id = '" + customerID + "'";
            queryFunction.ViewItemOnJFXComboBox(sql, "invoice_id", invoice_id);
        } catch (Exception e) {
        }
    }

    private void invoiceAction() {
        try {
            String sql = "SELECT * FROM sale_payment_statement WHERE invoice_no = '" + invoice_id.getValue() + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                entryDate.setValue(rs.getDate("entry_date").toLocalDate());
                totalID.setText(rs.getString("due"));
                textarea_comment.setText("Type Comments...");
            }
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private void invoiceReleased(KeyEvent event) {
        String sql = "SELECT invoice_id FROM sale_ledger WHERE "
                + "invoice_id LIKE '%" + invoice_id.getValue() + "%' AND customer_id = '" + customerID + "'";
        queryFunction.ShowJFXItemOnkeyReleased(sql, "invoice_id", invoice_id, event);
    }

    private void clear() {
        invoice_id.setValue(null);
        entryDate.setValue(null);
        totalID.setText(null);
        textarea_comment.setText(null);
        paymentDate.setValue(null);
        pay_amount.setText(null);
        dueID.setText(null);
        cus_id.setValue(null);
        cus_id.requestFocus();
    }

    private void submit() {
        try {
            String sql1 = "UPDATE sale_payment_statement SET payment = payment + "
                    + "'" + pay_amount.getText() + "', due = '" + dueID.getText() + "' "
                    + "WHERE invoice_no = '" + invoice_id.getValue() + "'";
            queryFunction.UpdateMessageLess(sql1);

            double balance = 0, netbalance = 0;
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
                    + "'" + invoice_id.getValue() + "','Paid','Cash','0',"
                    + "'" + pay_amount.getText() + "','" + totalDue + "')";
            queryFunction.InsertCustomise(sql2, "Have a Problem in Customer Transaction");
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a problem.\n" + e);
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private void view() {
        try {
            data.clear();
            TInvoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoice"));
            TDate.setCellValueFactory(new PropertyValueFactory<>("entrydate"));
            T_TotalAmount.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
            T_paymentDate.setCellValueFactory(new PropertyValueFactory<>("payment_date"));
            TpaidAmount.setCellValueFactory(new PropertyValueFactory<>("paid_amount"));
            T_dueAmount.setCellValueFactory(new PropertyValueFactory<>("due"));

            String sql = "SELECT * FROM sale_payment_entry";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                data.add(new SalePaymentView(rs.getString("invioce_no"),
                        rs.getString("entry_date"),
                        rs.getString("total_amount"),
                        rs.getString("payment_date"),
                        rs.getString("paid_amount"),
                        rs.getString("due_amount")));
            }
            tableview.setItems(data);
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
        }
    }

    @FXML
    private void InvoiceAction(ActionEvent event) {
        invoiceAction();
    }

    @FXML
    private void InvoiceRelesed(KeyEvent event) {
        invoiceReleased(event);
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
        try {
            submit();
            clear();
            view();
            msg.InformationMessage("Successful", "Information", "Successfully Submited");
        } catch (Exception e) {
        }
    }

    @FXML
    private void ToggleAction(ActionEvent event) {
    }

    @FXML
    private void Table_Click(MouseEvent event) {
    }

    @FXML
    private void clearAction(ActionEvent event) {
        clear();
    }

    @FXML
    private void cus_Action(ActionEvent event) {
        Customer_action();
    }

    @FXML
    private void CusReleased(KeyEvent event) {
        customer_released(event);
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}

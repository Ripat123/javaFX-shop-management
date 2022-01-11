package fxsupershop.Purchase;

import com.jfoenix.controls.*;
import fxsupershop.Services.*;
import fxsupershop.TableView.PurchasePaymentView;
import java.net.URL;
import java.sql.ResultSet;
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
public class Purchase_paymentController implements Initializable {

    @FXML
    private JFXDatePicker entryDate;
    @FXML
    private JFXComboBox invoice_id;
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
    @FXML
    private JFXToggleButton toggleID;
    @FXML
    private TableView tableview;
    @FXML
    private TableColumn<?, ?> TInvoiceNo;
    @FXML
    private TableColumn<?, ?> TDate;
    @FXML
    private ScrollPane scroll;
    GeneralService service = new GeneralService();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    Report report = new Report();
    Message msg = new Message();
    private ResultSet rs = null;
    @FXML
    private TableColumn<?, ?> T_TotalAmount;
    @FXML
    private TableColumn<?, ?> TpaidAmount;
    @FXML
    private TableColumn<?, ?> T_dueAmount;
    ObservableList<PurchasePaymentView> data = FXCollections.observableArrayList();
    @FXML
    private JFXComboBox<?> sup_id;
    ObservableList supplierlist = FXCollections.observableArrayList();
    String supplierID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        JFXScrollPane.smoothScrolling(scroll);
        showSupplier();
        view();
    }
    
    private void showSupplier() {
        String sql = "SELECT company_name,id FROM suplier_info";
        supplierlist = queryFunction.ViewArrayJFXComboBox(sql, "company_name","id", sup_id,supplierlist);
    }
    
    private void supplier_released(KeyEvent event) {
        try {
            String sql = "SELECT company_name,id FROM suplier_info WHERE "
                + "company_name LIKE '%" + sup_id.getValue() + "%'";
        supplierlist = queryFunction.ShowArrayItemKeyReleased(sql, "company_name","id", sup_id, event,supplierlist);
        } catch (Exception e) {
        }
    }
    
    private void supplier_action(){
        try {
            supplierID = supplierlist.get(sup_id.getSelectionModel().getSelectedIndex()).toString();
            String sql = "SELECT * FROM purchase_ledger WHERE suplier_id = '" + supplierID + "'";
            queryFunction.ViewItemOnJFXComboBox(sql, "invoice_no", invoice_id);
        } catch (Exception e) {
        }    
    }

    private void invoiceAction() {
        try {
            String sql = "SELECT * FROM purchase_payment_statement WHERE invoice_no = '" + invoice_id.getValue() + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                entryDate.setValue(rs.getDate("entry_date").toLocalDate());
                totalID.setText(rs.getString("due"));
                textarea_comment.setText("Type Comments...");
            }
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
        }
        finally{
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private void invoiceReleased(KeyEvent event) {
        String sql = "SELECT invoice_no FROM purchase_ledger WHERE "
                + "invoice_no LIKE '%" + invoice_id.getValue() + "%' AND suplier_id = '"+supplierID+"'";
        queryFunction.ShowJFXItemOnkeyReleased(sql, "invoice_no", invoice_id, event);
    }
    
    private void clear(){
        invoice_id.setValue(null);
        entryDate.setValue(null);
        totalID.setText(null);
        textarea_comment.setText(null);
        paymentDate.setValue(null);
        pay_amount.setText(null);
        dueID.setText(null);
        sup_id.setValue(null);
        sup_id.requestFocus();
    }
    
    private void submit(){
        try {
            String sql1 = "UPDATE purchase_payment_statement SET payment = payment + "
                    + "'"+pay_amount.getText()+"', due = '"+dueID.getText()+"' "
                    + "WHERE invoice_no = '"+invoice_id.getValue()+"'";
            queryFunction.UpdateMessageLess(sql1);
            
            double balance = 0,netbalance = 0;
            String sql = "SELECT * FROM suplier_transaction WHERE suplier_id = '"+supplierID+"' ORDER BY id DESC";
            rs = queryFunction.getResult(sql);
            if(rs.next()){
                balance = Double.parseDouble(rs.getString("balance"));
            }
            netbalance = balance;
            double totalDue = netbalance - Double.parseDouble(pay_amount.getText());
            String sql2 = "INSERT INTO suplier_transaction (suplier_id,transaction_date,"
                    + "invoice_id,voucher_no,transaction_type,debit,credit,balance)VALUES ("
                    + "'"+supplierID+"','"+paymentDate.getValue()+"',"
                    + "'"+invoice_id.getValue()+"','Paid','Cash','0',"
                    + "'"+pay_amount.getText()+"','"+totalDue+"')";
            queryFunction.InsertCustomise(sql2, "Have a Problem in Supplier Transaction");
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a problem.\n"+e);
        }
        finally{
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }
    
    private void view(){
        try {
            data.clear();
            TInvoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoice"));
            TDate.setCellValueFactory(new PropertyValueFactory<>("entrydate"));
            T_TotalAmount.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
            TpaidAmount.setCellValueFactory(new PropertyValueFactory<>("paid_amount"));
            T_dueAmount.setCellValueFactory(new PropertyValueFactory<>("due"));
            
            String sql = "SELECT * FROM purchase_payment_statement";
            rs = queryFunction.getResult(sql);
            while(rs.next()){
                data.add(new PurchasePaymentView(rs.getString("invoice_no"),
                        rs.getString("entry_date"),
                        rs.getString("total_ammount"),
                        rs.getString("payment"),
                        rs.getString("due")));
            }
            tableview.setItems(data);
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n"+e);
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
            if(sup_id.getValue() == null){
                queryFunction.service.msg.ConditionalMessage("Empty Supplier");
                return;
            }if(invoice_id.getValue() == null){
                queryFunction.service.msg.ConditionalMessage("Empty Invoice");
                return;
            }if(paymentDate.getValue() == null){
                queryFunction.service.msg.ConditionalMessage("Empty Payment Date");
                return;
            }if(pay_amount.getText().equals("") || pay_amount.getText().equals("0")){
                queryFunction.service.msg.ConditionalMessage("Enter Pay amount");
                return;
            }
            submit();
            view();
            clear();
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
    private void SuppAction(ActionEvent event) {
        supplier_action();
    }

    @FXML
    private void SuppReleased(KeyEvent event) {
        supplier_released(event);
    }

}

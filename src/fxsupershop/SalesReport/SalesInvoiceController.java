package fxsupershop.SalesReport;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Rifat Rabbi
 */
public class SalesInvoiceController implements Initializable {

    @FXML
    private AnchorPane itempane;
    @FXML
    private AnchorPane stackcard;
    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> invoice_col;
    @FXML
    private TableColumn<?, ?> date_col;
    @FXML
    private TableColumn<?, ?> net_col;
    @FXML
    private TableColumn<?, ?> paid_col;
    @FXML
    private TableColumn<?, ?> due_col;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private JFXComboBox category_view;
    @FXML
    private JFXDatePicker date1;
    @FXML
    private JFXDatePicker date2;
    @FXML
    private JFXButton refbtn;
    @FXML
    private JFXButton search_btn;
    @FXML
    private TableColumn<?, ?> company_col;
    Sales_invoice_Presenter sip = new Sales_invoice_Presenter();
    @FXML
    private JFXTextField due_field;
    @FXML
    private JFXTextField paid_field;
    @FXML
    private JFXTextField netTotal_field;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    @FXML
    private JFXButton refbtn1;
    @FXML
    private JFXTextField profit;
    @FXML
    private JFXCheckBox check;
    @FXML
    private StackPane main_stack;
    @FXML
    private JFXDialogLayout dialogpane;
    @FXML
    private JFXDialog dialog;
    @FXML
    private Label msg;
    @FXML
    private JFXCheckBox check1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        t.play();
        view();
        category_view.getItems().addAll("This month", "This Year", "All Invoice");
        category_view.setValue("This month");
    }

    Timeline t = new Timeline(new KeyFrame(Duration.seconds(0.8), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            dialogpane.setVisible(true);
            msg.setVisible(true);
            dialogpane.setBody(msg);
            dialog = new JFXDialog(main_stack, dialogpane, JFXDialog.DialogTransition.RIGHT);
            Timeline test = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                    dialog.setOnDialogClosed((e) -> {
                        dialogpane.setVisible(false);
                        msg.setVisible(false);
                    });
                }
            }));
            dialog.show();
            test.play();
        }
    }));

    private void total() {
        String sql = "SELECT SUM(sale_payment_statement.`total_ammount`) AS 'total',sale_payment_statement.* FROM `sale_ledger` "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYearMonth() + "-01' "
                + "AND '" + queryFunction.service.getYearMonth() + "-31'";
        sip.sumFunction(sql, netTotal_field, "total");
    }

    private void totalYear() {
        String sql = "SELECT SUM(sale_payment_statement.`total_ammount`) AS 'total',sale_payment_statement.* FROM `sale_ledger` "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYear() + "-01-01' "
                + "AND '" + queryFunction.service.getYear() + "-12-31'";
        sip.sumFunction(sql, netTotal_field, "total");
    }

    private void nettotal() {
        String sql = "SELECT SUM(sale_payment_statement.`total_ammount`) AS 'total',sale_payment_statement.* FROM `sale_ledger` "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no ";
        sip.sumFunction(sql, netTotal_field, "total");
    }

    private void paid() {
        String sql = "SELECT SUM(sale_payment_statement.`payment`) AS 'total',sale_payment_statement.* FROM `sale_ledger` "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYearMonth() + "-01' "
                + "AND '" + queryFunction.service.getYearMonth() + "-31'";
        sip.sumFunction(sql, paid_field, "total");
    }

    private void paidYear() {
        String sql = "SELECT SUM(sale_payment_statement.`payment`) AS 'total',sale_payment_statement.* FROM `sale_ledger` "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYear() + "-01-01' "
                + "AND '" + queryFunction.service.getYear() + "-12-31'";
        sip.sumFunction(sql, paid_field, "total");
    }

    private void Allpaid() {
        String sql = "SELECT SUM(sale_payment_statement.`payment`) AS 'total',sale_payment_statement.* FROM `sale_ledger` "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no ";
        sip.sumFunction(sql, paid_field, "total");
    }

    private void due() {
        String sql = "SELECT SUM(sale_payment_statement.`due`) AS 'total',sale_payment_statement.*  FROM `sale_ledger` "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYearMonth() + "-01' "
                + "AND '" + queryFunction.service.getYearMonth() + "-31'";
        sip.sumFunction(sql, due_field, "total");
    }

    private void Yeardue() {
        String sql = "SELECT SUM(sale_payment_statement.`due`) AS 'total',sale_payment_statement.* FROM `sale_ledger` "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYear() + "-01-01' "
                + "AND '" + queryFunction.service.getYear() + "-12-31'";
        sip.sumFunction(sql, due_field, "total");
    }

    private void Alldue() {
        String sql = "SELECT SUM(sale_payment_statement.`due`) AS 'total',sale_payment_statement.* FROM `sale_ledger` "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no ";
        sip.sumFunction(sql, due_field, "total");
    }
    
    private void profit(){
        String sql = "SELECT invoice_id FROM sale_ledger WHERE invoice_date "
                + "BETWEEN '" + queryFunction.service.getYearMonth() + "-01' "
                + "AND '" + queryFunction.service.getYearMonth() + "-31'";
        sip.profitGenerator(sql, profit);
    }
    
    private void yearProfit(){
        String sql = "SELECT invoice_id FROM sale_ledger WHERE invoice_date "
                + "BETWEEN '" + queryFunction.service.getYear() + "-01-01' "
                + "AND '" + queryFunction.service.getYear() + "-12-31'";
        sip.profitGenerator(sql, profit);
    }
    
    private void AllProfit(){
        String sql = "SELECT invoice_id FROM sale_ledger";
        sip.profitGenerator(sql, profit);
    }

    private void view() {
        String sql = "SELECT sale_ledger.*,customer_info.*,sale_payment_statement.* FROM sale_ledger "
                + "INNER JOIN customer_info ON sale_ledger.customer_id = customer_info.id "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYearMonth() + "-01' "
                + "AND '" + queryFunction.service.getYearMonth() + "-31'";
        sip.view(sql, tableview, invoice_col, date_col, company_col, net_col, paid_col, due_col);
        total();
        paid();
        due();
    }

    private void AllView() {
        String sql = "SELECT sale_ledger.*, customer_info.*,sale_payment_statement.* FROM sale_ledger "
                + "INNER JOIN customer_info ON sale_ledger.customer_id = customer_info.id "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no";
        sip.Allview(sql, tableview);
        nettotal();
        Allpaid();
        Alldue();
    }

    private void YearView() {
        String sql = "SELECT sale_ledger.*,customer_info.*,sale_payment_statement.* FROM sale_ledger "
                + "INNER JOIN customer_info ON sale_ledger.customer_id = customer_info.id "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no"
                + " WHERE invoice_date BETWEEN '" + queryFunction.service.getYear() + "-01-01' "
                + "AND '" + queryFunction.service.getYear() + "-12-31'";
        sip.Allview(sql, tableview);
        totalYear();
        paidYear();
        Yeardue();
    }

    @FXML
    private void Clicked(MouseEvent event) {
        sip.clicked(tableview, event, check,check1, profit);
    }

    @FXML
    private void search_keyAction(KeyEvent event) {
        if (category_view.getValue().equals("This month")) {
            String query = "SELECT sale_ledger.*,customer_info.*,sale_payment_statement.* FROM sale_ledger "
                    + "INNER JOIN customer_info ON sale_ledger.customer_id = customer_info.id "
                    + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no "
                    + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYearMonth() + "-01' "
                    + "AND '" + queryFunction.service.getYearMonth() + "-31' AND"
                    + " sale_ledger.invoice_id LIKE '%" + search_filed.getText().trim() + "%'";
            sip.ThisSearch(query, tableview);
        } else if (category_view.getValue().equals("All Invoice")) {
            String query = "SELECT sale_ledger.*,customer_info.*,sale_payment_statement.* FROM sale_ledger "
                    + "INNER JOIN customer_info ON sale_ledger.customer_id = customer_info.id "
                    + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no "
                    + "WHERE sale_ledger.invoice_id LIKE '%" + search_filed.getText().trim() + "%'";
            sip.allSearch(query, tableview);
        } else if (category_view.getValue().equals("This Year")) {
            String query = "SELECT sale_ledger.*,customer_info.*,sale_payment_statement.* FROM sale_ledger "
                    + "INNER JOIN customer_info ON sale_ledger.customer_id = customer_info.id "
                    + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no "
                    + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYear() + "-01-01' "
                    + "AND '" + queryFunction.service.getYear() + "-12-31' AND "
                    + "sale_ledger.invoice_id LIKE '%" + search_filed.getText().trim() + "%'";
            sip.allSearch(query, tableview);
        }
    }

    @FXML
    private void Filter(ActionEvent event) {
        if (category_view.getValue().equals("This month")) {
            view();
        } else if (category_view.getValue().equals("All Invoice")) {
            AllView();
        } else if (category_view.getValue().equals("This Year")) {
            YearView();
        }
    }

    @FXML
    private void Refresh(ActionEvent event) {
        date1.setValue(null);
        date2.setValue(null);
        search_filed.setText(null);
        profit.setText(null);
        if (category_view.getValue().equals("This month")) {
            view();
        } else if (category_view.getValue().equals("All Invoice")) {
            AllView();
        } else if (category_view.getValue().equals("This Year")) {
            YearView();
        }
    }

    @FXML
    private void Search(ActionEvent event) {
        sip.DateSearch(date1, date2, tableview);
    }

    @FXML
    private void Profit(ActionEvent event) {
        if (category_view.getValue().equals("This month")) {
            profit();
        } else if (category_view.getValue().equals("All Invoice")) {
            AllProfit();
        } else if (category_view.getValue().equals("This Year")) {
            yearProfit();
        }
    }

    @FXML
    private void checkAction(ActionEvent event) {
        check1.setSelected(false);
    }

    @FXML
    private void Check1Action(ActionEvent event) {
        check.setSelected(false);
    }

}

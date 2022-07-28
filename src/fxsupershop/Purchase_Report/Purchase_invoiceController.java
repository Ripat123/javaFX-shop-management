package fxsupershop.Purchase_Report;

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
public class Purchase_invoiceController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
//    @FXML
//    private AnchorPane stackcard;
//    @FXML
//    private JFXButton refbtn;
    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> invoice_col;
    @FXML
    private TableColumn<?, ?> date_col;
    @FXML
    private TableColumn<?, ?> suplier_col;
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
    Purchase_invoice_presenter pip = new Purchase_invoice_presenter();
    @FXML
    private JFXDatePicker date1;
    @FXML
    private JFXDatePicker date2;
//    @FXML
//    private JFXButton search_btn;
    @FXML
    private JFXTextField due_field;
    @FXML
    private JFXTextField paid_field;
    @FXML
    private JFXTextField netTotal_field;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    @FXML
    private JFXDialogLayout dialogpane;
    @FXML
    private JFXDialog dialog;
    @FXML
    private Label msg;
    @FXML
    private StackPane main_stack;

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
        String sql = "SELECT SUM(purchase_payment_statement.`total_ammount`) AS 'total',purchase_payment_statement.* FROM `purchase_ledger` "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYearMonth() + "-01' "
                + "AND '" + queryFunction.service.getYearMonth() + "-31'";
        pip.sumFunction(sql, netTotal_field, "total");
    }

    private void totalYear() {
        String sql = "SELECT SUM(purchase_payment_statement.`total_ammount`) AS 'total',purchase_payment_statement.* FROM `purchase_ledger` "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYear() + "-01-01' "
                + "AND '" + queryFunction.service.getYear() + "-12-31'";
        pip.sumFunction(sql, netTotal_field, "total");
    }

    private void allTotal() {
        String sql = "SELECT SUM(purchase_payment_statement.`total_ammount`) AS 'alltotal',purchase_payment_statement.* FROM `purchase_ledger` "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no ";
        pip.sumFunction(sql, netTotal_field, "alltotal");
    }

    private void paid() {
        String sql = "SELECT SUM(purchase_payment_statement.`payment`) AS 'total',purchase_payment_statement.* FROM `purchase_ledger` "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYearMonth() + "-01' "
                + "AND '" + queryFunction.service.getYearMonth() + "-31'";
        pip.sumFunction(sql, paid_field, "total");
    }

    private void paidYear() {
        String sql = "SELECT SUM(purchase_payment_statement.`payment`) AS 'total',purchase_payment_statement.* FROM `purchase_ledger` "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYear() + "-01-01' "
                + "AND '" + queryFunction.service.getYear() + "-12-31'";
        pip.sumFunction(sql, paid_field, "total");
    }

    private void allPaid() {
        String sql = "SELECT SUM(purchase_payment_statement.`payment`) AS 'allpaid',purchase_payment_statement.* FROM `purchase_ledger` "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no ";
        pip.sumFunction(sql, paid_field, "allpaid");
    }

    private void due() {
        String sql = "SELECT SUM(purchase_payment_statement.`due`) AS 'total',purchase_payment_statement.* FROM `purchase_ledger` "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYearMonth() + "-01' "
                + "AND '" + queryFunction.service.getYearMonth() + "-31'";
        pip.sumFunction(sql, due_field, "total");
    }

    private void Yeardue() {
        String sql = "SELECT SUM(purchase_payment_statement.`due`) AS 'total',purchase_payment_statement.* FROM `purchase_ledger` "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no "
                + "WHERE invoice_date BETWEEN '" + queryFunction.service.getYear() + "-01-01' "
                + "AND '" + queryFunction.service.getYear() + "-12-31'";
        pip.sumFunction(sql, due_field, "total");
    }

    private void alldue() {
        String sql = "SELECT SUM(purchase_payment_statement.`due`) AS 'alldue',purchase_payment_statement.* FROM `purchase_ledger` "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no ";
        pip.sumFunction(sql, due_field, "alldue");
    }

    private void view() {
        String sql = "SELECT purchase_ledger.*,suplier_info.*,purchase_payment_statement.* FROM purchase_ledger "
                + "INNER JOIN suplier_info ON purchase_ledger.suplier_id = suplier_info.id "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no "
                + "WHERE purchase_ledger.invoice_date BETWEEN '" + queryFunction.service.getYearMonth() + "-01' "
                + "AND '" + queryFunction.service.getYearMonth() + "-31' LIMIT 100";
        pip.view(sql, tableview, invoice_col, date_col, suplier_col, net_col, paid_col, due_col);
        total();
        paid();
        due();
    }

    private void YearView() {
        String sql = "SELECT purchase_ledger.*,suplier_info.*,purchase_payment_statement.* FROM purchase_ledger "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no "
                + "INNER JOIN suplier_info ON purchase_ledger.suplier_id = suplier_info.id"
                + " WHERE purchase_ledger.invoice_date BETWEEN '" + queryFunction.service.getYear() + "-01-01' "
                + "AND '" + queryFunction.service.getYear() + "-12-31' LIMIT 100";
        pip.Allview(sql, tableview);
        totalYear();
        paidYear();
        Yeardue();
    }

    private void AllView() {
        String sql = "SELECT purchase_ledger.*,suplier_info.*,purchase_payment_statement.* FROM purchase_ledger "
                + "INNER JOIN suplier_info ON purchase_ledger.suplier_id = suplier_info.id "
                + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no LIMIT 100";
        pip.Allview(sql, tableview);
        allTotal();
        allPaid();
        alldue();
    }

    @FXML
    private void Refresh(ActionEvent event) {
        date1.setValue(null);
        date2.setValue(null);
        search_filed.setText(null);
        if (category_view.getValue().equals("This month")) {
            view();
        } else if (category_view.getValue().equals("All Invoice")) {
            AllView();
        } else if (category_view.getValue().equals("This Year")) {
            YearView();
        }
    }

    @FXML
    private void search_keyAction(KeyEvent event) {
//        if (category_view.getValue().equals("This month")) {
//            String query = "SELECT purchase_ledger.*, suplier_info.* FROM purchase_ledger "
//                    + "INNER JOIN suplier_info ON purchase_ledger.suplier_id = suplier_info.id "
//                    + "WHERE purchase_ledger.invoice_no LIKE '%" + search_filed.getText().trim() + "%'";
//            pip.ThisSearch(query, tableview);
//        } else if (category_view.getValue().equals("All Invoice")) {
//            String query = "SELECT purchase_ledger.*, suplier_info.* FROM purchase_ledger "
//                    + "INNER JOIN suplier_info ON purchase_ledger.suplier_id = suplier_info.id "
//                    + "WHERE purchase_ledger.invoice_no LIKE '%" + search_filed.getText().trim() + "%'";
//            pip.allSearch(query, tableview);
//        }

        if (category_view.getValue().equals("This month")) {
            String query = "SELECT purchase_ledger.*,suplier_info.*,purchase_payment_statement.* FROM purchase_ledger "
                    + "INNER JOIN suplier_info ON purchase_ledger.suplier_id = suplier_info.id "
                    + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no "
                    + "WHERE purchase_ledger.invoice_date BETWEEN '" + queryFunction.service.getYearMonth() + "-01' "
                    + "AND '" + queryFunction.service.getYearMonth() + "-31' AND"
                    + " purchase_ledger.invoice_no LIKE '%" + search_filed.getText().trim() + "%'";
            pip.ThisSearch(query, tableview);
        } else if (category_view.getValue().equals("All Invoice")) {
            String query = "SELECT purchase_ledger.*,suplier_info.*,purchase_payment_statement.* FROM purchase_ledger "
                    + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no "
                    + "INNER JOIN suplier_info ON purchase_ledger.suplier_id = suplier_info.id "
                    + "WHERE purchase_ledger.invoice_no LIKE '%" + search_filed.getText().trim() + "%'";
            pip.allSearch(query, tableview);
        } else if (category_view.getValue().equals("This Year")) {
            String query = "SELECT purchase_ledger.*,suplier_info.*,purchase_payment_statement.* FROM purchase_ledger "
                    + "INNER JOIN suplier_info ON purchase_ledger.suplier_id = suplier_info.id "
                    + "INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no "
                    + "WHERE purchase_ledger.invoice_date BETWEEN '" + queryFunction.service.getYear() + "-01-01' "
                    + "AND '" + queryFunction.service.getYear() + "-12-31' AND "
                    + "purchase_ledger.invoice_no LIKE '%" + search_filed.getText().trim() + "%'";
            pip.allSearch(query, tableview);
        }
    }

    @FXML
    private void Clicked(MouseEvent event) {
        pip.clicked(tableview, event);
    }

    @FXML
    private void Filter(ActionEvent event) {
        if (category_view.getValue().equals("This month")) {
            view();
        } else if (category_view.getValue().equals("All Invoice")) {
            AllView();
        } else if (category_view.getValue().equals(("This Year"))) {
            YearView();
        }
    }

    @FXML
    private void Search(ActionEvent event) {
        pip.DateSearch(date1, date2, tableview);
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }

}

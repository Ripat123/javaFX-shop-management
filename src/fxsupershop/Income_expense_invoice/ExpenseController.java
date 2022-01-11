package fxsupershop.Income_expense_invoice;

import com.jfoenix.controls.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.NotificationPane;

/**
 * FXML Controller class
 *
 * @author Rifat Rabbi
 */
public class ExpenseController implements Initializable {

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
    private TableColumn<?, ?> title_col;
    @FXML
    private TableColumn<?, ?> amount_col;
    @FXML
    private TableColumn<?, ?> com_col;
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
    private JFXTextField netTotal_field;
    Expense_presenter ep = new Expense_presenter();
    @FXML
    private JFXButton printbtn;
    String sql;
    @FXML
    private NotificationPane noti;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        view();
        category_view.getItems().addAll("This Month", "This Year", "All Invoice");
        category_view.setValue("This Month");
    }

    private void view() {
        sql = "SELECT income_expense_source.source_title,expenseinfo.* FROM expenseinfo "
                + "INNER JOIN income_expense_source ON expenseinfo.fk_title_id = "
                + "income_expense_source.id WHERE expenseinfo.date"
                + " BETWEEN '" + ep.queryFunction.service.getYearMonth() + "-01' "
                + "AND '" + ep.queryFunction.service.getYearMonth() + "-31'";
        ep.view(sql, tableview, invoice_col, date_col, title_col, amount_col, com_col);
        yearMonthTotal();
    }

    private void Allview() {
        sql = "SELECT income_expense_source.source_title,expenseinfo.* FROM expenseinfo "
                + "INNER JOIN income_expense_source ON expenseinfo.fk_title_id = "
                + "income_expense_source.id";
        ep.view(sql, tableview, invoice_col, date_col, title_col, amount_col, com_col);
        total();
    }

    private void yearView() {
        sql = "SELECT income_expense_source.source_title,expenseinfo.* FROM expenseinfo "
                + "INNER JOIN income_expense_source ON expenseinfo.fk_title_id = "
                + "income_expense_source.id WHERE expenseinfo.date BETWEEN '" + ep.queryFunction.service.getYear() + "-01-01' "
                + "AND '" + ep.queryFunction.service.getYear() + "-12-31'";
        ep.view(sql, tableview, invoice_col, date_col, title_col, amount_col, com_col);
        yearTotal();
    }

    private void total() {
        String query = "SELECT SUM(`ammount`) AS 'total' FROM `expenseinfo`";
        ep.sumFunction(query, netTotal_field, "total");
    }

    private void yearTotal() {
        String query = "SELECT SUM(`ammount`) AS 'total' FROM `expenseinfo` "
                + "WHERE expenseinfo.date BETWEEN '" + ep.queryFunction.service.getYear() + "-01-01' "
                + "AND '" + ep.queryFunction.service.getYear() + "-12-31'";
        ep.sumFunction(query, netTotal_field, "total");
    }

    private void yearMonthTotal() {
        String query = "SELECT SUM(`ammount`) AS 'total' FROM `expenseinfo` WHERE expenseinfo.date"
                + " BETWEEN '" + ep.queryFunction.service.getYearMonth() + "-01' "
                + "AND '" + ep.queryFunction.service.getYearMonth() + "-31'";
        ep.sumFunction(query, netTotal_field, "total");
    }

    @FXML
    private void Clicked(MouseEvent event) {
    }

    @FXML
    private void search_keyAction(KeyEvent event) {
        if (category_view.getValue().equals("This Month")) {
            String sql = "SELECT income_expense_source.source_title,expenseinfo.* FROM expenseinfo "
                    + "INNER JOIN income_expense_source ON expenseinfo.fk_title_id = "
                    + "income_expense_source.id WHERE expenseinfo.date"
                    + " BETWEEN '" + ep.queryFunction.service.getYearMonth() + "-01' "
                    + "AND '" + ep.queryFunction.service.getYearMonth() + "-31'"
                    + " AND expenseinfo.id LIKE '%" + search_filed.getText() + "%'";
            ep.view(sql, tableview, invoice_col, date_col, title_col, amount_col, com_col);
            
        } else if (category_view.getValue().equals("This Year")) {
            String sql = "SELECT income_expense_source.source_title,expenseinfo.* FROM expenseinfo "
                    + "INNER JOIN income_expense_source ON expenseinfo.fk_title_id = "
                    + "income_expense_source.id WHERE expenseinfo.date BETWEEN '" + ep.queryFunction.service.getYear() + "-01-01' "
                    + "AND '" + ep.queryFunction.service.getYear() + "-12-31'"
                    + " AND expenseinfo.id LIKE '%" + search_filed.getText() + "%'";
            ep.view(sql, tableview, invoice_col, date_col, title_col, amount_col, com_col);
            
        } else if (category_view.getValue().equals("All Invoice")) {
            String sql = "SELECT income_expense_source.source_title,expenseinfo.* FROM expenseinfo "
                + "INNER JOIN income_expense_source ON expenseinfo.fk_title_id = "
                + "income_expense_source.id WHERE expenseinfo.id LIKE '%" + search_filed.getText() + "%'";
            ep.view(sql, tableview, invoice_col, date_col, title_col, amount_col, com_col);
            
        }
    }

    @FXML
    private void Filter(ActionEvent event) {
        Refresh(null);
    }

    @FXML
    private void Refresh(ActionEvent event) {
        date1.setValue(null);
        date2.setValue(null);
        search_filed.setText(null);
        if (category_view.getValue().equals("This Month")) {
            view();
        } else if (category_view.getValue().equals("This Year")) {
            yearView();
        } else if (category_view.getValue().equals("All Invoice")) {
            Allview();
        }
    }

    @FXML
    private void Search(ActionEvent event) {
        ep.DateSearch(date1, date2, tableview);
//if(noti.isShowing()){
//    noti.hide();
//    noti.show();
//}else{
//    noti.show();
//}
        
    }

    @FXML
    private void Print(ActionEvent event) {
        if(date1.getValue() != null){
            ep.Report(null);
        }
        else{
            ep.Report(ep.repQuery(sql));
        }
    }

}

package fxsupershop.Income_Expense_Entry;

import com.jfoenix.controls.*;
import fxsupershop.Login.LoginMultiFormController;
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
 * @author PC
 */
public class Income_EntryController implements Initializable {

    @FXML
    private JFXTextField amount;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXComboBox<?> type;
    @FXML
    private JFXTextArea desc;
    @FXML
    private JFXRadioButton id_filter;
    @FXML
    private ToggleGroup search;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private JFXButton reportbtn;
    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_date;
    @FXML
    private TableColumn<?, ?> col_type;
    @FXML
    private TableColumn<?, ?> col_amount;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    Presenter p = new Presenter();
    LoginMultiFormController lmfc = new LoginMultiFormController();
    int uid;
    String titleID, id;
    ResultSet rs;
    Model m;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private JFXComboBox transaction;
    ObservableList sourceType = FXCollections.observableArrayList();
    @FXML
    private JFXRadioButton income_id;
    @FXML
    private ToggleGroup view;
    @FXML
    private JFXRadioButton expense_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        uid = lmfc.User();
        View();
        JFXScrollPane.smoothScrolling(scrollpane);
        transaction.getItems().addAll("Income", "Expense");
    }

    private void type() {
        sourceType.clear();
        String sql = "SELECT * FROM income_expense_source WHERE source_type = '" + transaction.getValue() + "'";
        sourceType = queryFunction.ViewArrayJFXComboBox(sql, "source_title", "id", type,sourceType);
    }

    private void insert() {
        if (transaction.getValue().equals("Income")) {
            String sql = "INSERT INTO incomeinfo (date,fk_title_id,ammount,comments,fk_user_id)"
                    + "VALUES('" + date.getValue() + "','" + titleID + "','" + amount.getText() + "'"
                    + ",'" + desc.getText().trim() + "','" + uid + "')";
            queryFunction.Insert(sql);
            clean();
        } else if (transaction.getValue().equals("Expense")) {
            String sql = "INSERT INTO expenseinfo (date,fk_title_id,ammount,comments,fk_user_id)"
                    + "VALUES('" + date.getValue() + "','" + titleID + "','" + amount.getText() + "'"
                    + ",'" + desc.getText().trim() + "','" + uid + "')";
            queryFunction.Insert(sql);
            clean();
        }
    }

    private void delete() {
        if (transaction.getValue().equals("Income")) {
            String sql = "DELETE FROM incomeinfo WHERE id = '" + id + "'";
            queryFunction.Delete(sql);
            clean();
        } else if (transaction.getValue().equals("Expense")) {
            String sql = "DELETE FROM expenseinfo WHERE id = '" + id + "'";
            queryFunction.Delete(sql);
            clean();
        }
    }

    private void update() {
        if (transaction.getValue().equals("Income")) {
            String sql = "UPDATE incomeinfo SET date = '" + date.getValue() + "',fk_title_id ="
                    + "'" + titleID + "',ammount='" + amount.getText() + "',comments='"
                    + "" + desc.getText().trim() + "',fk_user_id='" + uid + "' WHERE id = '" + id + "'";
            queryFunction.Update(sql);
            clean();
        } else if (transaction.getValue().equals("Expense")) {
            String sql = "UPDATE expenseinfo SET date = '" + date.getValue() + "',fk_title_id ="
                    + "'" + titleID + "',ammount='" + amount.getText() + "',comments='"
                    + "" + desc.getText().trim() + "',fk_user_id='" + uid + "' WHERE id = '" + id + "'";
            queryFunction.Update(sql);
            clean();
        }
    }

    private void View() {
        if (income_id.isSelected()) {
            incomeView();
        } else if (expense_id.isSelected()) {
            expenseView();
        }
    }

    private void incomeView() {
        String sql = "SELECT * FROM `incomeinfo` INNER JOIN income_expense_source "
                + "ON incomeinfo.fk_title_id = income_expense_source.id";
        p.view(tableview, col_id, col_date, col_type, col_amount, sql);
    }

    private void expenseView() {
        String sql = "SELECT * FROM `expenseinfo` INNER JOIN income_expense_source "
                + "ON expenseinfo.fk_title_id = income_expense_source.id";
        p.view(tableview, col_id, col_date, col_type, col_amount, sql);
    }

    private void clean() {
        date.setValue(null);
        type.setValue(null);
        desc.setText(null);
        amount.setText(null);
        date.requestFocus();
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        delete();
        View();
    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        update();
        View();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
        View();
    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clean();
    }

    @FXML
    private void onViiew(ActionEvent event) {
        View();
    }

    @FXML
    private void TypeAction(ActionEvent event) {
        try {
            titleID = sourceType.get(type.getSelectionModel().getSelectedIndex()).toString();
        } catch (Exception e) {
        }
    }

    @FXML
    private void Search(KeyEvent event) {
        if(income_id.isSelected()){
        String sql = "SELECT * FROM `incomeinfo` INNER JOIN income_expense_source "
                + "ON incomeinfo.fk_title_id = income_expense_source.id WHERE "
                + "incomeinfo.id LIKE '%" + search_filed.getText() + "%' OR source_title LIKE '%" + search_filed.getText() + "%'";
        p.Search(tableview, col_id, col_date, col_type, col_amount, search_filed, sql);
        }else if (expense_id.isSelected()){
            String sql = "SELECT * FROM `expenseinfo` INNER JOIN income_expense_source "
                + "ON expenseinfo.fk_title_id = income_expense_source.id WHERE "
                + "expenseinfo.id LIKE '%" + search_filed.getText() + "%' OR source_title LIKE '%" + search_filed.getText() + "%'";
        p.Search(tableview, col_id, col_date, col_type, col_amount, search_filed, sql);
        }
    }

    @FXML
    private void Report(ActionEvent event) {
        if(income_id.isSelected()){
        String sql = "SELECT incomeinfo.*,income_expense_source.*,project_info.* FROM "
                + "incomeinfo,income_expense_source,project_info WHERE incomeinfo.fk_title_id = income_expense_source.id";
        queryFunction.getImagePath(sql, "image");
        queryFunction.report.getReport("/fxsupershop/Income_Expense_Entry/Report/", "Income_report.jrxml", sql);
        }else if(expense_id.isSelected()){
            p.Report();
        }
    }

    @FXML
    private void TableView(MouseEvent event) {
        try {
            if(income_id.isSelected()){
            String sql = "SELECT * FROM `incomeinfo` INNER JOIN income_expense_source "
                    + "ON incomeinfo.fk_title_id = income_expense_source.id WHERE incomeinfo.`id` = ";
            m = p.ClickedData(tableview, event, id, type, desc, amount, titleID, date,transaction,income_id, sql);
            id = m.getId();
            titleID = m.getDate();
            }else if(expense_id.isSelected()){
                String sql = "SELECT * FROM `expenseinfo` INNER JOIN income_expense_source "
                    + "ON expenseinfo.fk_title_id = income_expense_source.id WHERE expenseinfo.`id` = ";
            m = p.ClickedData(tableview, event, id, type, desc, amount, titleID, date,transaction,income_id, sql);
            id = m.getId();
            titleID = m.getDate();
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void TransactionAction(ActionEvent event) {
        type();
    }

    @FXML
    private void incomeViewAction(ActionEvent event) {
        incomeView();
    }

    @FXML
    private void ExpenseViewAction(ActionEvent event) {
        expenseView();
    }

}

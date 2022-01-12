package fxsupershop.Income_Expense_Entry;

import com.jfoenix.controls.*;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.PrepareQueryFunction;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Expense_EntryController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXComboBox type;
//    @FXML
//    private JFXRadioButton id_filter;
//    @FXML
//    private ToggleGroup search;
//    @FXML
//    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
//    @FXML
//    private JFXButton reportbtn;
    @FXML
    private TableView tableview;
    @FXML
    private TableColumn<?, ?> col_date;
    @FXML
    private TableColumn<?, ?> col_type;
    @FXML
    private TableColumn<?, ?> col_amount;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    Presenter p = new Presenter();
    @FXML
    private JFXTextField amount;
    @FXML
    private JFXTextArea desc;
    LoginMultiFormController lmfc = new LoginMultiFormController();
    int uid;
    String titleID, id;
    @FXML
    private TableColumn<?, ?> col_id;
    ResultSet rs;
    Model m;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        type();
        uid = lmfc.User();
        View();
    }

    private void type() {
        String sql = "SELECT * FROM income_expense_source WHERE source_type = 'Expense'";
        queryFunction.ViewItemOnJFXComboBox(sql, "source_title", type);
    }

    private void insert() {
        String sql = "INSERT INTO expenseinfo (date,fk_title_id,ammount,comments,fk_user_id)"
                + "VALUES('" + date.getValue() + "','" + titleID + "','" + amount.getText() + "'"
                + ",'" + desc.getText().trim() + "','" + uid + "')";
        queryFunction.Insert(sql);
        clean();
    }

    private void delete() {
        String sql = "DELETE FROM expenseinfo WHERE id = '" + id + "'";
        queryFunction.Delete(sql);
        clean();
    }

    private void update() {
        String sql = "UPDATE expenseinfo SET date = '" + date.getValue() + "',fk_title_id ="
                + "'" + titleID + "',ammount='" + amount.getText() + "',comments='"
                + "" + desc.getText().trim() + "',fk_user_id='" + uid + "' WHERE id = '" + id + "'";
        queryFunction.Update(sql);
        clean();
    }

    private void clean() {
        date.setValue(null);
        type.setValue(null);
        desc.setText(null);
        amount.setText(null);
        date.requestFocus();
    }

    private void View() {
        String sql = "SELECT * FROM `expenseinfo` INNER JOIN income_expense_source "
                + "ON expenseinfo.fk_title_id = income_expense_source.id";
        p.view(tableview, col_id, col_date, col_type, col_amount, sql);
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
    private void Search(KeyEvent event) {
        String sql = "SELECT * FROM `expenseinfo` INNER JOIN income_expense_source "
                + "ON expenseinfo.fk_title_id = income_expense_source.id WHERE "
                + "expenseinfo.id LIKE '%" + search_filed.getText() + "%' OR source_title LIKE '%" + search_filed.getText() + "%'";
        p.Search(tableview, col_id, col_date, col_type, col_amount, search_filed, sql);
    }

    @FXML
    private void Report(ActionEvent event) throws IOException {
        p.Report();
    }

    @FXML
    private void TableView(MouseEvent event) {
        try {
//            String sql = "SELECT * FROM `expenseinfo` INNER JOIN income_expense_source "
//                    + "ON expenseinfo.fk_title_id = income_expense_source.id WHERE expenseinfo.`id` = ";
//            m = p.ClickedData(tableview, event, id, type, desc, amount, titleID, date, sql);
//            id = m.getId();
//            titleID = m.getDate();
        } catch (Exception e) {
        }
    }

    @FXML
    private void TypeAction(ActionEvent event) {
        String sql = "SELECT id FROM income_expense_source WHERE source_title = '" + type.getValue() + "'";
        titleID = queryFunction.getDataInSVeriable(sql, "id");
    }

}

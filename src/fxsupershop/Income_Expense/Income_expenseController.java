package fxsupershop.Income_Expense;

import com.jfoenix.controls.*;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.PrepareQueryFunction;
import fxsupershop.TableView.IncomeExpenseView;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Income_expenseController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
    @FXML
    private JFXTextField text_source_id;
    @FXML
    private JFXTextField text_source_title;
    @FXML
    private JFXComboBox combo_source_type;
    @FXML
    private JFXRadioButton id_filter;
//    @FXML
//    private ToggleGroup search;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> Tid;
    @FXML
    private TableColumn<?, ?> TSourceType;
    @FXML
    private TableColumn<?, ?> TSourceTitle;
    ObservableList data = FXCollections.observableArrayList();
    ResultSet rs;
    LoginMultiFormController lmfc = new LoginMultiFormController();
    int uid,autoid;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        view();
        combo_source_type.getItems().addAll("Income", "Expense");
        uid = lmfc.User();
        autoID();
    }
    
    private void autoID(){
        autoid = queryFunction.AutoJFXID("income_expense_source");
        text_source_id.setText(String.valueOf(autoid));
    }

    private void insert() {
        if (text_source_id.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Enter Source ID");
            return;
        }
        if (combo_source_type.getValue().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Select Source Type");
            return;
        }
        if (text_source_title.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Enter Source Title");
            return;
        }
        autoID();
        String sql = "INSERT INTO `income_expense_source` (`id`,`source_type`,"
                + "`source_title`,admin_id)VALUES('" + text_source_id.getText() + "',"
                + "'" + combo_source_type.getValue() + "','" + text_source_title.getText() + "','" + uid + "')";
        queryFunction.Insert(sql);
        clean();

    }

    private void update() {
        if (text_source_id.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Empty Source ID");
            return;
        }
        if (combo_source_type.getValue().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Select Source Type");
            return;
        }
        if (text_source_title.getText().equals("")) {
            queryFunction.service.msg.ConditionalMessage("Enter Source Title");
            return;
        }
        String sql = "update  income_expense_source set  source_type='"
                + "" + combo_source_type.getValue() + "',source_title='"
                + "" + text_source_title.getText() + "',admin_id='" + uid + "' WHERE id= '"
                + "" + text_source_id.getText() + "'";
        queryFunction.Update(sql);
        clean();
    }

    private void delete() {
        String sql = "delete from  income_expense_source WHERE id= '" + text_source_id.getText() + "'";
        queryFunction.Delete(sql);
        clean();
    }

    private void initview(String query) {
        try {
            String sql = query;
            rs = queryFunction.getResult(sql);
            while (rs.next()) {

                data.add(new IncomeExpenseView(rs.getString("id"),
                        rs.getString("source_type"), rs.getString("source_title")
                ));

            }
            tableview.setItems(data);
        } catch (Exception e) {
            queryFunction.service.msg.ErrorMessage("Unsucccessful", "Error", "Have a Problem.\n" + e);

        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void view() {
        data.clear();
        Tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        TSourceType.setCellValueFactory(new PropertyValueFactory<>("source_type"));
        TSourceTitle.setCellValueFactory(new PropertyValueFactory<>("source_title"));

        String sql = "SELECT income_expense_source.id,income_expense_source.source_type,"
                + "income_expense_source.source_title FROM income_expense_source";
        initview(sql);

    }

    private void search() {
        if (id_filter.isSelected()) {
            data.clear();

            String sql = "SELECT income_expense_source.id,income_expense_source.source_type,"
                    + "income_expense_source.source_title FROM income_expense_source WHERE `id` LIKE '%" + search_filed.getText() + "%'";
            initview(sql);
        } else if (name_filter.isSelected()) {
            data.clear();

            String sql = "SELECT income_expense_source.id,income_expense_source.source_type,"
                    + "income_expense_source.source_title FROM income_expense_source WHERE `id` LIKE "
                    + "'%" + search_filed.getText() + "%' OR source_type LIKE "
                    + "'%" + search_filed.getText() + "%' OR source_title LIKE '%" + search_filed.getText() + "%'";
            initview(sql);
        }

    }

    private void Full_Report() {
        String path = "/fxsupershop/Income_Expense/";
        String report ="Income_Expense_Report.jrxml";
        String sql = "SELECT * FROM `income_expense_source`";
        queryFunction.report.getReport(path, report, sql);
    }

    public void clean() {
        text_source_id.setText(null);
        combo_source_type.setValue(null);
        text_source_title.setText(null);
        text_source_id.requestFocus();
        autoID();
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        delete();
        view();
    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        update();
        view();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
        view();
    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clean();
    }

    @FXML
    private void onViiew(ActionEvent event) {
        view();
    }

    @FXML
    private void SetItem(ActionEvent event) {
    }

    @FXML
    private void search_keyAction(KeyEvent event) {
        search();
    }

    @FXML
    private void Full_Reports(ActionEvent event) {
        Full_Report();
    }

    @FXML
    private void TableClick(MouseEvent event) {
        tableview.setEditable(true);
        try {
            if (event.getClickCount() == 1) {
                @SuppressWarnings("rawtypes")
                TablePosition pos = tableview.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                int col = pos.getColumn();
                @SuppressWarnings("rawtypes")
                TableColumn column = pos.getTableColumn();
                String val = column.getCellData(row).toString();

                if (col == 0 || col == 1 || col == 2) {
                    String sql = "SELECT * FROM `income_expense_source` WHERE `id` = '" + val + "' OR  `source_type` = '" + val + "' OR `source_title` = '" + val + "'";
                    rs = queryFunction.getResult(sql);
                    if (rs.next()) {
                        text_source_id.setText(rs.getString("id"));
                        combo_source_type.setValue(rs.getString("source_type"));
                        text_source_title.setText(rs.getString("source_title"));
                    }
                }
            }
        } catch (Exception e) {

        }
        finally{
            try{
                rs.close();
                queryFunction.post.close();
            }
            catch(Exception e){
                
            }
        }

    }

}

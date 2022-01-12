package fxsupershop.Employee_salarySetup;

import com.jfoenix.controls.*;
import fxsupershop.Connection.connection_Sql;
import fxsupershop.Services.*;
import fxsupershop.TableView.SalarySetupView;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
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
public class Salary_SetupController implements Initializable {

    @FXML
    private JFXTextField amount;
    @FXML
    private JFXComboBox<?> employee_name;
    @FXML
    private JFXComboBox status;
//    @FXML
//    private ToggleGroup search;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> Temployee_name;
    @FXML
    private TableColumn<?, ?> TAmount;
    @FXML
    private TableColumn<?, ?> Tdate;
    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs;
    PrepareQueryFunction prepareQueryFunction = new PrepareQueryFunction();
    Message msg = new Message();
    Report report = new Report();
    GeneralService generalService = new GeneralService();
    @FXML
    private JFXDatePicker date;
    private String employeeID;
    ObservableList data = FXCollections.observableArrayList();
    @FXML
    private JFXRadioButton other_filter;
    @FXML
    private TableColumn<?, ?> Tstatus;
    private String id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        con = connection_Sql.ConnectDb();
        status.getItems().addAll("Active", "Inactive");
       initSource();
    }
    
    public void initSource(){
        con = connection_Sql.ConnectDb();
         view();
         EmployeeView();
    }

    private void EmployeeView() {
        String sql = "SELECT * FROM employee_info";
        prepareQueryFunction.ViewItemOnJFXComboBox(sql, "name", employee_name);
    }

    private void getEmployeeID() {
        try {
            String sql = "SELECT id FROM employee_info WHERE name = '" + employee_name.getValue() + "'";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            if (rs.next()) {
                employeeID = null;
                employeeID = rs.getString("id");
            }
        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem.");
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void Insert() {
        if (employee_name.getValue().equals("")) {
            msg.ConditionalMessage("Please select a Employer name.");
            return;
        }
        if (status.getValue().equals("")) {
            msg.ConditionalMessage("select Status.");
            return;
        }
        if (amount.getText().equals("")) {
            msg.ConditionalMessage("Please Enter salary amount.");
            return;
        }
        if (date.getValue().equals("")) {
            msg.ConditionalMessage("Select current date.");
            return;
        }
        LocalDate currentDate = date.getValue();
        String sql = "INSERT INTO emp_salary_setup (fk_emp_id,status,amount,date,fk_user_id)"
                + "VALUES ('" + employeeID + "','" + status.getValue() + "','" + amount.getText() + "','" + currentDate + "','sbit')";
        prepareQueryFunction.Insert(sql);
    }

    private void update() {
        if (employee_name.getValue().equals("")) {
            msg.ConditionalMessage("Please select a Employer name.");
            return;
        }
        if (status.getValue().equals("")) {
            msg.ConditionalMessage("select Status.");
            return;
        }
        if (amount.getText().equals("")) {
            msg.ConditionalMessage("Please Enter salary amount.");
            return;
        }
        if (date.getValue().equals("")) {
            msg.ConditionalMessage("Select current date.");
            return;
        }
        LocalDate currentDate = date.getValue();
        String sql = "UPDATE emp_salary_setup SET fk_emp_id = '" + employeeID + "',"
                + "status = '" + status.getValue() + "',amount = '" + amount.getText() + "'"
                + ",date = '" + currentDate + "',fk_user_id = 'sbit' WHERE fk_emp_id = '" + employeeID + "'";
        prepareQueryFunction.Update(sql);
    }

    private void delete() {
        String sql = "DELETE FROM emp_salary_setup WHERE id = '" + id + "'";
        prepareQueryFunction.Delete(sql);
    }

    private void view() {
        try {
            data.clear();
            Temployee_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            TAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            Tdate.setCellValueFactory(new PropertyValueFactory<>("date"));
            Tstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            String sql = "SELECT * FROM emp_salary_setup,employee_info WHERE emp_salary_setup.fk_emp_id = employee_info.id";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            while (rs.next()) {
                data.add(new SalarySetupView(rs.getString("name"),
                        rs.getString("amount"),
                        rs.getString("date"),
                        rs.getString("status")
                ));
            }
            tableview.setItems(data);
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public void search() {
        try {
            if (name_filter.isSelected()) {
                data.clear();

                String sql = "SELECT * FROM emp_salary_setup,employee_info WHERE "
                        + "emp_salary_setup.fk_emp_id = (SELECT id FROM employee_info WHERE name = '" + search_filed.getText() + "')";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {
                    data.add(new SalarySetupView(rs.getString("name"),
                            rs.getString("amount"),
                            rs.getString("date"),
                            rs.getString("status")
                    ));
                }
                tableview.setItems(data);
            } else if (other_filter.isSelected()) {
                data.clear();

                String sql = "SELECT * FROM emp_salary_setup,employee_info WHERE "
                        + "emp_salary_setup.amount = '" + search_filed.getText() + "' "
                        + "OR emp_salary_setup.date = '" + search_filed.getText() + "'";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {
                    data.add(new SalarySetupView(rs.getString("name"),
                            rs.getString("amount"),
                            rs.getString("date"),
                            rs.getString("status")
                    ));
                }
                tableview.setItems(data);
            }
        } catch (SQLException | HeadlessException e) {
            msg.ErrorMessage("Unsucccessful", "Error", "Have a Problem.\n" + e);
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void clean() {
        employee_name.setValue(null);
        status.setValue(null);
        amount.setText(null);
        date.setValue(null);
        employee_name.requestFocus();
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        try {
            delete();
            view();
            clean();
        } catch (Exception e) {

        }

    }

    @FXML
    private void Updatebtn(ActionEvent event) {

        try {
            update();
            view();
            clean();
        } catch (Exception e) {

        }
    }

    @FXML
    private void valueAdd(ActionEvent event) {

        try {
            Insert();
            view();
            clean();
        } catch (Exception e) {

        }
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
    private void shown(MouseEvent event) {
        employee_name.show();
    }

    @FXML
    private void Setname(ActionEvent event) {
        getEmployeeID();
    }

    @FXML
    private void ShowPressItem(KeyEvent event) {
        String sql = "SELECT name FROM employee_info";
        prepareQueryFunction.ShowJFXItemOnkeyReleased(sql, "name", employee_name, event);
    }

    @FXML
    private void search_keyAction(KeyEvent event) {
    }

    @FXML
    private void reportbtn(ActionEvent event) {

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

                if (col == 0) {
                    String sql = "SELECT emp_salary_setup.id,emp_salary_setup.status,emp_salary_setup."
                            + "amount,emp_salary_setup.date,employee_info.name "
                            + "FROM emp_salary_setup INNER JOIN employee_info "
                            + "ON emp_salary_setup.fk_emp_id = employee_info.id WHERE emp_salary_setup"
                            + ".fk_emp_id = (SELECT id FROM employee_info "
                            + "WHERE name = '" + val + "')";
                    post = con.prepareStatement(sql);
                    rs = post.executeQuery();
                    if (rs.next()) {
                        id = rs.getString("id");
                        employee_name.getEditor().setText(rs.getString("name"));
                        status.setValue(rs.getString("status"));
                        amount.setText(rs.getString("amount"));
                        date.setValue(rs.getDate("date").toLocalDate());
                    }
                }
            }
        } catch (Exception e) {
//            msg.ErrorMessage("Unsucccessful", "Error", "Have a Problem.\n" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

}

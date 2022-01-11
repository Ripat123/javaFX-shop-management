package fxsupershop.Employee_Salary;

import com.jfoenix.controls.*;
import fxsupershop.Connection.connection_Sql;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.Employee_Salary_View;
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
import javafx.scene.layout.*;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Employee_SalaryController implements Initializable {

    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs;
    ObservableList data = FXCollections.observableArrayList();
    @FXML
    private JFXTextField text_mobile_no;
    @FXML
    private JFXTextField text_amount;
    @FXML
    private JFXTextField text_InvoiceNo;
    @FXML
    private JFXComboBox combo_select_month;
    @FXML
    private JFXComboBox combo_paymrnt_type;
    @FXML
    private JFXComboBox combo_employee_name;
    @FXML
    private JFXTextArea textarea_comment;
    private JFXRadioButton id_filter;
    private JFXRadioButton name_filter;
    private JFXTextField search_filed;
    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> TInvoiceNo;
    @FXML
    private TableColumn<?, ?> TDate;
    @FXML
    private TableColumn<?, ?> TSelectMonth;
    @FXML
    private TableColumn<?, ?> Tpayment_Type;
    @FXML
    private TableColumn<?, ?> TAmount;
    @FXML
    private TableColumn<?, ?> TEmployee_Name;
    @FXML
    private TableColumn<?, ?> TPhone;
    private TableColumn<?, ?> TComments;
    @FXML
    private JFXDatePicker text_dates;
    @FXML
    private AnchorPane Employee_Pane;
    @FXML
    private JFXTextField text_total_amount;
    @FXML
    private JFXTextField text_paid_amount;
    @FXML
    private JFXTextField text_due_amount;
    Message msg = new Message();
    PrepareQueryFunction prepareQueryFunction = new PrepareQueryFunction();
    Report report = new Report();
    GeneralService generalService = new GeneralService();
    @FXML
    private JFXButton more_btn;
    @FXML
    private Pane pop_pane;
    private int id;
    LoginMultiFormController loginMultiFormController = new LoginMultiFormController();
    private static int user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//         con = fxsupershop.Connection.connection_Sql.ConnectDb();
        initSource();
        autoid();
        combo_select_month.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        combo_paymrnt_type.getItems().addAll("Salary", "Bonus");
    }

    public void initSource() {
        con = connection_Sql.ConnectDb();
        veiwItem();
        view();
        text_paid_amount.setText("0.0");
        text_due_amount.setText("0.0");
        text_amount.setText("0.0");
        text_total_amount.setText("0.0");
        user = loginMultiFormController.User();
    }

    private void autoid() {

        try {
            String sql = "SELECT MAX(id) AS 'id' FROM emp_salary_collection";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            if (rs.next()) {
                id = Integer.parseInt(rs.getString("id"));
                id++;

                if (id <= 9) {
                    text_InvoiceNo.setText("Invoice-" + "00" + "" + Integer.toString(id));
                } else if(id <= 99){
                    text_InvoiceNo.setText("Invoice-" + "0" + "" + Integer.toString(id));
                }else{
                    text_InvoiceNo.setText("Invoice-" + "" + Integer.toString(id));
                }

            } else {
                text_InvoiceNo.setText("Invoice-" + "01");

            }

        } catch (Exception e) {

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
            if (text_InvoiceNo.getText().equals("")) {
                text_InvoiceNo.setText("Invoice-" + "01");
            }
        }
    }

    private void Employee_number_View() {
        try {
            if (combo_paymrnt_type.getValue().equals("Salary")) {
                String sql = "select * from `employee_info`,`emp_salary_setup` where "
                        + "employee_info.`name`='" + combo_employee_name.getEditor().getText() + ""
                        + "' AND emp_salary_setup.fk_emp_id = employee_info.id";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    text_mobile_no.setText(rs.getString("phone"));
                    text_amount.setText(rs.getString("amount"));
                    text_total_amount.setText(rs.getString("amount"));
                    textarea_comment.setText("Type Comments...");

                }
            } else {
                String sql = "select * from `employee_info`,`emp_salary_setup` where "
                        + "employee_info.`name`='" + combo_employee_name.getEditor().getText() + ""
                        + "' AND emp_salary_setup.fk_emp_id = employee_info.id";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    text_mobile_no.setText(rs.getString("phone"));
                    text_amount.setText("0.0");
                    textarea_comment.setText("Type Comments...");

                }

            }

        } catch (Exception e) {
//            msg.WarningMessage("Unsuccessful", "Warning", "View Item Unsuccessful\n" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public void veiwItem() {
        String sql = "SELECT * FROM `employee_info`";
        prepareQueryFunction.ViewItemOnJFXComboBox(sql, "name", combo_employee_name);

    }

    public void insert() {

        LocalDate obj2 = text_dates.getValue();
        String sql = "INSERT INTO `employee_salary`(`id`,`InvoiceNo`,`date`,"
                + "`month`,`employee_name`,`phone`,`payment_type`,`amount`,"
                + "`comment`,total_amount,paid_amuont,deu_amount) VALUES('1','" + text_InvoiceNo.getText().trim() + "','" + obj2 + "',"
                + "'" + combo_select_month.getEditor().getText() + "','" + combo_employee_name.getEditor().getText() + "','" + text_mobile_no.getText().trim() + "',"
                + "'" + combo_paymrnt_type.getEditor().getText() + "',"
                + "'" + text_amount.getText().trim() + "','" + textarea_comment.getText().trim() + "',"
                + "'" + text_total_amount.getText().trim() + "','" + text_paid_amount.getText().trim() + "','" + text_due_amount.getText().trim() + "')";
        prepareQueryFunction.Insert(sql);
    }

    public void submit_insert() {

        if (tableview.getItems().isEmpty()) {
            msg.ConditionalMessage("Please Add Salary...");
            return;
        }
        try {
            String s = LocalDate.now().toString();

            String sql = "SELECT * FROM employee_salary";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            while (rs.next()) {
                String sql1 = "INSERT INTO `emp_salary_collection`(`invoice_no`,`date`,"
                        + "`select_month`,`payment_type`,`amount`,`employee_name`,`phone`,"
                        + "`comments`,total_amount,paid_amount,due_amount,fk_user_id,created_at) VALUES('" + rs.getString("InvoiceNo") + "',"
                        + "'" + rs.getString("date") + "',"
                        + "'" + rs.getString("month") + "','" + rs.getString("payment_type") + "','" + rs.getString("amount") + "',"
                        + "'" + rs.getString("employee_name") + "',"
                        + "'" + rs.getString("phone") + "','" + rs.getString("comment") + "',"
                        + "'" + rs.getString("total_amount") + "','" + rs.getString("paid_amuont") + "','" + rs.getString("deu_amount") + "',"
                        + "'" + user + "','" + s + "')";
                prepareQueryFunction.Insert(sql1);
                String sql2 = "SELECT * FROM employee_salary WHERE InvoiceNo = '" + rs.getString("InvoiceNo") + "'";
                String path = "/fxsupershop/Employee_Salary/";
                report.getReport(path, "salaryreportView.jrxml", sql2);
            }
        } catch (Exception e) {

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void salaryclean() {
        String sql = "DELETE FROM employee_salary WHERE id = '1'";
        prepareQueryFunction.DeleteConditionLess(sql);
    }

    public void view() {
        data.clear();
        TInvoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoice_no"));
        TDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        TSelectMonth.setCellValueFactory(new PropertyValueFactory<>("select_month"));
        Tpayment_Type.setCellValueFactory(new PropertyValueFactory<>("payment_type"));
        TAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TEmployee_Name.setCellValueFactory(new PropertyValueFactory<>("employee_name"));
        TPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        try {
            String sql = "SELECT employee_salary.InvoiceNo,employee_salary."
                    + "date,employee_salary.month,employee_salary."
                    + "payment_type,employee_salary.amount,employee_salary."
                    + "employee_name,employee_salary.phone,employee_salary."
                    + "comment FROM employee_salary order by id asc";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            while (rs.next()) {

                data.add(new Employee_Salary_View(rs.getString("InvoiceNo"),
                        rs.getString("date"), rs.getString("month"),
                        rs.getString("payment_type"), rs.getString("amount"),
                        rs.getString("employee_name"), rs.getString("phone"),
                        rs.getString("comment")
                ));

            }
            tableview.setItems(data);
        } catch (Exception e) {

        }
    }

    public void clean() {
        text_InvoiceNo.setText(null);
        text_dates.setValue(null);
        combo_select_month.setValue(null);
        combo_paymrnt_type.setValue(null);
        text_amount.setText(null);
        combo_employee_name.setValue(null);
        text_mobile_no.setText(null);
        textarea_comment.setText(null);
        text_total_amount.setText(null);
        text_paid_amount.setText(null);
        text_due_amount.setText(null);
        text_paid_amount.setText("0.0");
        text_due_amount.setText("0.0");
        text_amount.setText("0.0");
        text_total_amount.setText("0.0");
        text_InvoiceNo.requestFocus();
    }

    public void total_minus() {
        try {
            double value1 = Double.parseDouble(text_total_amount.getText());
            double value2 = Double.parseDouble(text_paid_amount.getText());
            double result = value1 - value2;
            text_due_amount.setText(String.valueOf(result));
        } catch (Exception e) {

        }
    }

    @FXML
    private void SetItem(ActionEvent event) {
        Employee_number_View();

    }

    @FXML
    private void ShowPressItem(KeyEvent event) {

    }

    @FXML
    private void shown(MouseEvent event) {
//        ItemName_cat.show();
    }

    private void View_Btn(ActionEvent event) {
        view();
    }

    @FXML
    private void Clean_Btn(ActionEvent event) {
        try {
            clean();

        } catch (Exception e) {

        }

    }

    @FXML
    private void New_Btn(ActionEvent event) {
        try {
            salaryclean();
            view();
            clean();
            autoid();
        } catch (Exception e) {

        }

    }

    @FXML
    private void valueAdd(ActionEvent event) {
        try {
            if (text_InvoiceNo.getText().equals("")) {
                msg.ConditionalMessage("Enter Employee ID");
                return;
            }
            if (combo_select_month.getEditor().getText().equals("")) {
                msg.ConditionalMessage("Select Month");
                return;
            }
            if (combo_paymrnt_type.getEditor().getText().equals("")) {
                msg.ConditionalMessage("Select Payment Type");
                return;
            }
            if (text_amount.getText().equals("")) {
                msg.ConditionalMessage("Enter Amount");
                return;
            }
            if (combo_employee_name.getEditor().getText().equals("")) {
                msg.ConditionalMessage("Select Employee Name");
                return;
            }
            if (text_mobile_no.getText().equals("")) {
                msg.ConditionalMessage("Enter Mobile No");
                return;
            }
            if (text_total_amount.getText().equals("")) {
                msg.ConditionalMessage("Enter Total Amount");
                return;
            }
            if (text_paid_amount.getText().equals("")) {
                msg.ConditionalMessage("Enter Paid Amount");
                return;
            }

            insert();
            view();
            clean();
            id++;
            text_InvoiceNo.setText("Invoice-" + id);
        } catch (Exception e) {

        }

    }

    @FXML
    private void Submit_btn(ActionEvent event) {

        try {
            submit_insert();
            salaryclean();
            view();
            clean();
            autoid();
        } catch (Exception e) {

        }
    }

    @FXML
    private void Report_Button(ActionEvent event) {
         String sql2 = "SELECT * FROM employee_salary";
                String path = "/fxsupershop/Employee_Salary/";
                report.ExportReport(path, "salaryreportView.jrxml", sql2);
    }

    @FXML
    private void Table_Click(MouseEvent event) {

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

                if (col == 0 || col == 5) {
                    String sql = "SELECT * FROM `employee_salary` WHERE `InvoiceNo` = '" + val + "' OR `employee_name` = '" + val + "'";
                    post = con.prepareStatement(sql);
                    rs = post.executeQuery();
                    if (rs.next()) {
                        text_InvoiceNo.setText(rs.getString("InvoiceNo"));
                        text_dates.setValue(rs.getDate("date").toLocalDate());
                        combo_select_month.getEditor().setText(rs.getString("month"));
                        combo_paymrnt_type.getEditor().setText(rs.getString("payment_type"));
                        text_amount.setText(rs.getString("amount"));
                        combo_employee_name.getEditor().setText(rs.getString("employee_name"));
                        text_mobile_no.setText(rs.getString("phone"));
                        textarea_comment.setText(rs.getString("comment"));
                    }
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }

    }


    @FXML
    private void MoreAction(ActionEvent event) {
        JFXPopup pop = new JFXPopup();
        pop.setStyle("-fx-background-radius: 20; -fx-pref-height: 600; -fx-pref-width: 700;");
        generalService.PopUPRight(pop_pane, more_btn, pop, 0, 0);
    }

    @FXML
    private void paidKeyreleasd(KeyEvent event) {
        total_minus();
    }

}

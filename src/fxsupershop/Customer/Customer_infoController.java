package fxsupershop.Customer;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import fxsupershop.TableView.CustomerView;
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
public class Customer_infoController implements Initializable {

    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs;
    ObservableList data = FXCollections.observableArrayList();

    @FXML
    private AnchorPane Customer_Pane;
    @FXML
    private JFXTextField text_customerID;
    @FXML
    private JFXTextField text_customerName;
    @FXML
    private JFXTextField text_Customerphone;
    @FXML
    private JFXTextField text_customeremail;
    @FXML
    private JFXTextArea text_customeraddress;
    @FXML
    private JFXRadioButton id_filter;
    @FXML
    private ToggleGroup search;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> Tid;
    @FXML
    private TableColumn<?, ?> Tname;
    @FXML
    private TableColumn<?, ?> Tphone;
    @FXML
    private TableColumn<?, ?> Temail;
    @FXML
    private TableColumn<?, ?> Taddress;
    @FXML
    private JFXComboBox customer_selection;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    @FXML
    private JFXTextField text_open;
    int presentID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = fxsupershop.Connection.connection_Sql.ConnectDb();
        view();
        customerView();
    }

    private void autoID() {
        presentID = queryFunction.AutoJFXID("customer_info");
    }

    public void insert() {
        try {
            if (text_customerName.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Customer Name", ButtonType.OK).showAndWait();
                return;
            }
            if (text_Customerphone.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Customer Phone", ButtonType.OK).showAndWait();
                return;
            }
            autoID();
            String sql = "INSERT INTO `customer_info`(id,`customer_name`,"
                    + "`customer_email`,`customer_phone`,`customer_address`)"
                    + "VALUES('" + presentID + "','" + text_customerName.getText() + "','" + text_customeremail.getText() + "',"
                    + "'" + text_Customerphone.getText() + "','" + text_customeraddress.getText() + "')";
            post = con.prepareStatement(sql);
            post.execute();
            if (!text_open.getText().trim().equals("") && !text_open.getText().
                    equals("0") && text_open.getText() != null) {
                String sql2 = "INSERT INTO customer_transaction (customer_id,transaction_date,"
                        + "transaction_type,debit,credit,balance) VALUES ('" + presentID + "',"
                        + "'" + queryFunction.service.getnonFormatCurrentDate() + "','Opening',"
                        + "'" + text_open.getText() + "','0','" + text_open.getText() + "')";
                queryFunction.InsertCustomise(sql2, "Customer Transaction Insert Unsuccessful");
            }
            queryFunction.service.msg.InformationMessage("Successful", "Information", "Insert Successful");
            clean();
            view();

        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Insert Unsuccessful\n" + e);

        } finally {
            try {
                post.close();

            } catch (Exception e) {

            }
        }
    }

    public void update() {
        try {
            String sql = "update  customer_info set  customer_name='" + text_customerName.getText() + "'"
                    + ",customer_email='" + text_customeremail.getText() + "',"
                    + "customer_phone='" + text_Customerphone.getText() + "',"
                    + "customer_address='" + text_customeraddress.getText() + ""
                    + "' WHERE id= '" + text_customerID.getText() + "'";
            post = con.prepareStatement(sql);
            post.execute();
            if (!text_open.getText().equals("") && !text_open.getText().equals("0")) {
                String sql1 = "SELECT * FROM customer_transaction WHERE customer_id = '" + text_customerID.getText() + ""
                        + "' AND transaction_type = 'Opening'";
                rs = queryFunction.getResult(sql1);
                if (rs.next()) {
                    queryFunction.service.msg.WarningMessage("Unsuccessful", "Opening balance already created", "You can't open balance again");
                    return;
                } else {
                    double balance = 0;
                    String sql3 = "SELECT balance FROM customer_transaction WHERE customer_id = '" + text_customerID.getText() + "' order by id desc";
                    rs = queryFunction.getResult(sql3);
                    if (rs.next()) {
                        balance = Double.parseDouble(rs.getString("balance"));
                    }
                    double net_balance = balance + Double.parseDouble(text_open.getText());
                    String sql2 = "INSERT INTO customer_transaction (customer_id,transaction_date,"
                            + "transaction_type,debit,credit,balance) VALUES ('" + text_customerID.getText() + "',"
                            + "'" + queryFunction.service.getnonFormatCurrentDate() + "','Opening',"
                            + "'" + text_open.getText() + "','0','" + net_balance + "')";
                    queryFunction.InsertCustomise(sql2, "Customer Transaction Insert Unsuccessful");
                }
            }
            queryFunction.service.msg.InformationMessage("Successful", "Information", "Updated Successful");
            clean();

        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Update Unsuccessful\n" + e);
        } finally {
            try {
                post.close();

            } catch (Exception e) {

            }
        }
    }

    void delete() {
        try {
            String sql = "delete from  customer_info WHERE id= '" + text_customerID.getText() + "'";
            post = con.prepareStatement(sql);
            post.execute();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert1.setTitle("Successful");
            alert1.setContentText("Delete Successful");
            alert1.show();
            clean();
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Delete Unsuccessful\n" + e);
        } finally {
            try {
                post.close();

            } catch (Exception e) {

            }
        }
    }

    public void view() {
        data.clear();
        Tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        Tname.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        Temail.setCellValueFactory(new PropertyValueFactory<>("customer_email"));
        Tphone.setCellValueFactory(new PropertyValueFactory<>("customer_phone"));
        Taddress.setCellValueFactory(new PropertyValueFactory<>("customer_address"));
        try {
            String sql = "SELECT customer_info.id,customer_info.customer_name,"
                    + "customer_info.customer_email,customer_info.customer_phone,"
                    + "customer_info.customer_address FROM customer_info";
//"INNER JOIN product_item  ON product_category.item_id=product_item.id   order by id asc";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            while (rs.next()) {

                data.add(new CustomerView(rs.getString("id"),
                        rs.getString("customer_name"), rs.getString("customer_email"),
                        rs.getString("customer_phone"), rs.getString("customer_address")
                ));

            }
            tableview.setItems(data);
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "View Unsuccessful\n" + e);

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
            if (id_filter.isSelected()) {
                data.clear();

                String sql = "SELECT customer_info.id,customer_info.customer_name,customer_info.customer_email,customer_info.customer_phone,customer_info.customer_address FROM customer_info WHERE `id` LIKE '%" + search_filed.getText() + "%' or `customer_name` LIKE '%" + search_filed.getText() + "%' or `customer_email` LIKE '%" + search_filed.getText() + "%' or `customer_phone` LIKE '%" + search_filed.getText() + "%'";
//"INNER JOIN product_item  ON product_category.item_id=product_item.id   WHERE `id`='"+search_filed.getText()+"'  order by id asc";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    data.add(new CustomerView(rs.getString("id"),
                            rs.getString("customer_name"), rs.getString("customer_email"),
                            rs.getString("customer_phone"), rs.getString("customer_address")
                    ));

                    text_customerID.setText(rs.getString("id"));
                    text_customerName.setText(rs.getString("customer_name"));
                    text_customeremail.setText(rs.getString("customer_email"));
                    text_Customerphone.setText(rs.getString("customer_phone"));
                    text_customeraddress.setText(rs.getString("customer_address"));

                }
                tableview.setItems(data);
            } else if (name_filter.isSelected()) {
                data.clear();

                String sql = "SELECT customer_info.id,customer_info.customer_name,customer_info.customer_email,customer_info.customer_phone,customer_info.customer_address FROM customer_info WHERE `id` LIKE '%" + search_filed.getText() + "%' or `customer_name` LIKE '%" + search_filed.getText() + "%' or `customer_email` LIKE '%" + search_filed.getText() + "%' or `customer_phone` LIKE '%" + search_filed.getText() + "%'";
//"INNER JOIN product_item  ON product_category.item_id=product_item.id WHERE `category_name` LIKE '%"+search_filed.getText()+"%' or `id` like '%"+search_filed.getText()+"%'";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    data.add(new CustomerView(rs.getString("id"),
                            rs.getString("customer_name"), rs.getString("customer_email"),
                            rs.getString("customer_phone"), rs.getString("customer_address")
                    ));

                    text_customerID.setText(rs.getString("id"));
                    text_customerName.setText(rs.getString("customer_name"));
                    text_customeremail.setText(rs.getString("customer_email"));
                    text_Customerphone.setText(rs.getString("customer_phone"));
                    text_customeraddress.setText(rs.getString("customer_address"));

                }
                tableview.setItems(data);
            }

        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Search Unsuccessful\n" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }

    }

    public void customerView() {
        try {
            customer_selection.getItems().clear();
            String sql = "SELECT customer_name FROM customer_info";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            while (rs.next()) {
                customer_selection.getItems().addAll(rs.getString("customer_name"));
            }
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem\n" + e);
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public void clean() {
        text_customerID.setText(null);
        text_customerName.setText(null);
        text_customeremail.setText(null);
        text_Customerphone.setText(null);
        text_customeraddress.setText(null);
        search_filed.setText(null);
        text_open.setText(null);
        text_customerName.requestFocus();

    }

    public void Full_Report() {
        String path = "/fxsupershop/Customer/";
        String report = "Customer_Report.jrxml";
        String sql = "SELECT * FROM `customer_info`";
        queryFunction.report.getReport(path, report, sql);
    }

    public void Indivisually_Report() {
        String path = "/fxsupershop/Customer/";
        String report = "Indivisually_Customer_Report.jrxml";
        String sql = "SELECT * FROM `customer_info` WHERE id = (SELECT id FROM "
                + "customer_info WHERE customer_name = '" + customer_selection.getEditor().getText() + "')";
        queryFunction.report.getReport(path, report, sql);
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        delete();
        view();
        customerView();
    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        update();
        view();
        customerView();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
        view();
        customerView();
    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clean();
    }

    @FXML
    private void onViiew(ActionEvent event) {
        view();
        customerView();
    }

    @FXML
    private void TableSearch(KeyEvent event) {
        search();
    }

    @FXML
    private void tableClick(MouseEvent event) {

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

                if (col == 0 || col == 1 || col == 2 || col == 3 || col == 4) {
                    String sql = "SELECT * FROM `customer_info` WHERE `id` = '" + val + "' OR `customer_name` = '" + val + "' OR  `customer_email` = '" + val + "' OR `customer_phone` = '" + val + "' OR `customer_address` = '" + val + "'";
                    rs = queryFunction.getResult(sql);
                    if (rs.next()) {
                        text_customerID.setText(rs.getString("id"));
                        text_customerName.setText(rs.getString("customer_name"));
                        text_customeremail.setText(rs.getString("customer_email"));
                        text_Customerphone.setText(rs.getString("customer_phone"));
                        text_customeraddress.setText(rs.getString("customer_address"));
                    }
                }

            }
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem\n" + e);

        }
    }

    @FXML
    private void Indivisually_Button(ActionEvent event) {
        Indivisually_Report();
    }

    @FXML
    private void full_Button(ActionEvent event) {
        Full_Report();
    }

    @FXML
    private void customerReleased(KeyEvent event) {
    }

}

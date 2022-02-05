package fxsupershop.Company;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import fxsupershop.TableView.CompanyView;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class CompanyController implements Initializable {

    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs;
    ObservableList data = FXCollections.observableArrayList();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
//    @FXML
//    private AnchorPane itempane;
    @FXML
    private JFXTextField text_company_id;
    @FXML
    private JFXTextField text_company_name;
    @FXML
    private JFXComboBox text_status;
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
    private TableColumn<?, ?> T_company_id;

    @FXML
    private TableColumn<?, ?> T_Status;
    @FXML
    private TableColumn<?, ?> T_company_Name;
    @FXML
    private JFXComboBox selectReport;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = fxsupershop.Connection.connection_Sql.ConnectDb();
        view();
        companycomboview();
        text_status.getItems().addAll("Active", "Deactive");

    }

    public void companycomboview() {
        try {
            selectReport.getItems().removeAll(data);
            String sql = "SELECT * FROM `company_info`";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            while (rs.next()) {

                selectReport.getItems().addAll(rs.getString("company_name"));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("Have a Problem!\n" + e);
            alert.show();
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public void insert() {
        try {
            if (text_company_id.getText().equals(null)) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty ID.").showAndWait();
                return;
            }
            if (text_company_name.getText().equals(null)) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty Company Name.").showAndWait();
                return;
            }
            if (text_status.getValue().equals(null)) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Select Status.").showAndWait();
                return;
            } else if (text_company_id.getText() != null && text_company_name.getText() != null && text_status.getValue() != null) {
                String sql = "INSERT INTO `company_info`(`id`,`company_name`,`status`)VALUES('" + text_company_id.getText() + "','" + text_company_name.getText() + "','" + text_status.getValue() + "')";
                post = con.prepareStatement(sql);
                post.execute();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert1.setTitle("Successful");
                alert1.setContentText("Insert Successful");
                alert1.show();
                clean();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unsuccessful", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("Insert Unsuccessful\n" + e);
            alert.show();

        } finally {
            try {
                post.close();
            } catch (Exception e) {

            }
        }
    }

    public void update() {
        try {

            String sql = "UPDATE  company_info SET  company_name='" + text_company_name.getText() + "', status='" + text_status.getValue() + "' WHERE id= '" + text_company_id.getText() + "'";
            post = con.prepareStatement(sql);
            post.execute();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert1.setTitle("Successful");
            alert1.setContentText("Update Successful");
            alert1.show();
            clean();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unsuccessful", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("Update Unsuccessful\n" + e);
            alert.show();
        } finally {
            try {
                post.close();
            } catch (Exception e) {

            }
        }
    }

    void delete() {
        try {
            String sql = "DELETE from  company_info WHERE id= '" + text_company_id.getText() + "'";
            post = con.prepareStatement(sql);
            post.execute();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert1.setTitle("Successful");
            alert1.setContentText("Delete Successful");
            alert1.show();
            clean();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unsuccessful", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("Delete Unsuccessful\n" + e);
            alert.show();
        } finally {
            try {
                post.close();
            } catch (Exception e) {

            }
        }
    }

    public void view() {
        data.clear();
        T_company_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        T_company_Name.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
        T_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        try {
            String sql = "SELECT `id`,`company_name`,`status` FROM `company_info`";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            while (rs.next()) {

                data.add(new CompanyView(rs.getString("id"),
                        rs.getString("company_name"), rs.getString("status")
                ));

            }
            tableview.setItems(data);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unsuccessful", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("View Unsuccessful\n" + e);
            alert.show();

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

                String sql = "SELECT company_info.id,company_info.company_name,company_info.status FROM company_info WHERE `id` LIKE '%" + search_filed.getText() + "%' OR `company_name` LIKE '%" + search_filed.getText() + "%'";
//"INNER JOIN product_item  ON product_category.item_id=product_item.id   WHERE `id`='"+search_filed.getText()+"'  order by id asc";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    data.add(new CompanyView(rs.getString("id"),
                            rs.getString("company_name"), rs.getString("status")
                    ));

//          text_company_id.setText(rs.getString("id"));
//          text_company_name.setText(rs.getString("company_name"));
//          text_status.getEditor().setText(rs.getString("status"));
//         
                }
                tableview.setItems(data);
            } else if (name_filter.isSelected()) {
                data.clear();

                String sql = "SELECT company_info.id,company_info.company_name,company_info.status FROM company_info WHERE `id` LIKE '%" + search_filed.getText() + "%' OR `company_name` LIKE '%" + search_filed.getText() + "%'";
//"INNER JOIN product_item  ON product_category.item_id=product_item.id WHERE `category_name` LIKE '%"+search_filed.getText()+"%' or `id` like '%"+search_filed.getText()+"%'";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    data.add(new CompanyView(rs.getString("id"),
                            rs.getString("company_name"), rs.getString("status")
                    ));
//          text_company_id.setText(rs.getString("id"));
//          text_company_name.setText(rs.getString("company_name"));
//          text_status.getEditor().setText(rs.getString("status"));
                }
                tableview.setItems(data);
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("Search Unsuccessful\n" + e);
            alert.show();

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }

    }

    public void Full_Report() {
        String path = "/fxsupershop/Company/";
        String report = "Company_Report.jrxml";
        String sql = "SELECT * FROM `company_info`";
        queryFunction.report.getReport(path, report, sql);

    }

    public void Indivisually_Report() {
        String path = "/fxsupershop/Company/";
        String report = "Indivisually_Company_Report.jrxml";
        String sql = "SELECT * FROM `company_info` WHERE id = (SELECT id FROM company_info WHERE company_name = '" + selectReport.getValue() + "')";
        queryFunction.report.getReport(path, report, sql);
    }

    public void clean() {
        text_company_id.setText(null);
        text_company_name.setText(null);
        text_status.setValue("");
        search_filed.setText(null);
        text_company_id.requestFocus();
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        delete();
        view();
        companycomboview();
    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        update();
        view();
        companycomboview();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
        view();
        companycomboview();
    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clean();
    }

    @FXML
    private void onViiew(ActionEvent event) {
        view();
        companycomboview();
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

                if (col == 0 || col == 1 || col == 2) {
                    String sql = "SELECT * FROM `company_info` WHERE `id` = '" + val + "' OR  `company_name` = '" + val + "' OR `status` = '" + val + "'";
                    post = con.prepareStatement(sql);
                    rs = post.executeQuery();
                    if (rs.next()) {
                        text_company_id.setText(rs.getString("id"));
                        text_company_name.setText(rs.getString("company_name"));
                        text_status.getEditor().setText(rs.getString("status"));
                    }
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Unsuccessful", ButtonType.CLOSE);
            alert.setTitle("Unsuccessful");
            alert.setContentText("View Unsuccessful\n" + e);
            alert.show();

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }

        }
    }

    @FXML
    private void Report_Button(ActionEvent event) {
        Full_Report();
    }

    @FXML
    private void Table_Search(KeyEvent event) {
        search();
    }

    @FXML
    private void Indivisually_Report_Button(ActionEvent event) {
        Indivisually_Report();
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize(); 
    }

}

package fxsupershop.Measurment;

import com.jfoenix.controls.*;
import fxsupershop.Services.*;
import fxsupershop.TableView.MeasurementSubUnit;
import java.net.URL;
import java.sql.*;
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
 * @author PC
 */
public class Mesurement_subunitController implements Initializable {

    @FXML
    private JFXTextField subID;
    @FXML
    private JFXComboBox mesurUID;
    @FXML
    private JFXTextField subNID;
    @FXML
    private JFXTextField subDID;
    @FXML
    private JFXRadioButton id_filter;
//    @FXML
//    private ToggleGroup search;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private TableView tableview;
    @FXML
    private TableColumn<?, ?> Tid;
    @FXML
    private TableColumn<?, ?> col_subname;
    @FXML
    private TableColumn<?, ?> col_subdata;
    Message msg = new Message();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    GeneralService service = new GeneralService();
    Report report = new Report();
    ResultSet rs;
    private int id, unitid;
    ObservableList<MeasurementSubUnit> data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        measurementVeiw();
        autoid();
        view();
    }

    private void autoid() {
        id = queryFunction.AutoJFXID("product_measurement_subunit","id");
        subID.setText(String.valueOf(id));
    }

    private void insert() {
        String sql = "INSERT INTO product_measurement_subunit (id,measurement_unit_id,"
                + "sub_unit_name,sub_unit_data) VALUES ('" + subID.getText().trim() + "','" + unitid + "',"
                + "'" + subNID.getText().trim() + "','" + subDID.getText().trim() + "')";
        queryFunction.Insert(sql);
    }

    private void update() {
        String sql = "UPDATE product_measurement_subunit SET measurement_unit_id ="
                + "'" + unitid + "',sub_unit_name ='" + subNID.getText().trim() + "',"
                + "sub_unit_data ='" + subDID.getText().trim() + "' WHERE id = '" + subID.getText() + "'";
        queryFunction.Update(sql);
    }

    private void delete() {
        String sql = "DELETE FROM product_measurement_subunit WHERE id = '" + subID.getText() + "'";
        queryFunction.Delete(sql);
    }

    private void internalView(String query) {
        try {
            data.clear();
            String sql = query;
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                data.addAll(new MeasurementSubUnit(rs.getString("id"),
                        rs.getString("sub_unit_name"),
                        rs.getString("sub_unit_data")
                ));
            }
            tableview.setItems(data);
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {

            }
        }
    }

    private void view() {
        data.clear();
        Tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_subname.setCellValueFactory(new PropertyValueFactory<>("subUnitName"));
        col_subdata.setCellValueFactory(new PropertyValueFactory<>("subUnitData"));

        String sql = "SELECT * FROM product_measurement_subunit";
        internalView(sql);
    }

    private void clean() {
        subID.setText(null);
        mesurUID.setValue(null);
        subNID.setText(null);
        subDID.setText(null);
        subID.requestFocus();
    }

    private void Report() {
        String path = "C:\\Users\\PC\\Documents\\SuperShop Project\\fxSuperShop\\src\\fxsupershop\\Measurment\\";
        String sql = "SELECT * FROM product_measurement_subunit";
        report.getReport(path, "measurement_subunit.jrxml", sql);
    }

    private void tableclick(MouseEvent event) {
        try {
            if (event.getClickCount() == 1) {
                @SuppressWarnings("rawtypes")
                TablePosition pos = (TablePosition) tableview.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                int col = pos.getColumn();
                @SuppressWarnings("rawtypes")
                TableColumn column = pos.getTableColumn();
                String val = column.getCellData(row).toString();

                if (col == 0) {
                    String sql = "SELECT * FROM product_measurement_subunit,product_measurement"
                            + " WHERE product_measurement_subunit.id = '" + val + "' AND product_measurement.id = "
                            + "product_measurement_subunit.measurement_unit_id";
                    rs = queryFunction.getResult(sql);
                    if (rs.next()) {
                        subID.setText(rs.getString("product_measurement_subunit.id"));
                        mesurUID.setValue(rs.getString("measurement_type"));
                        subNID.setText(rs.getString("sub_unit_name"));
                        subDID.setText(rs.getString("sub_unit_data"));
                    }
                }
            }
        } catch (Exception e) {
            msg.WarningMessage("", "", "" + e);
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {

            }
        }
    }

    private void measurementVeiw() {
        String sql = "SELECT * FROM product_measurement";
        queryFunction.ViewItemOnJFXComboBox(sql, "measurement_type", mesurUID);
    }

    private void measurementUnitAction() {
        String sql = "SELECT id FROM product_measurement WHERE measurement_type"
                + "= '" + mesurUID.getValue() + "'";
        unitid = queryFunction.getDataIniVeriable(sql, "id");
    }

    private void search() {

        if (id_filter.isSelected()) {
            String sql = "SELECT * FROM product_measurement_subunit WHERE id LIKE '%" + search_filed.getText().trim() + "%'";
            internalView(sql);
        } else if (name_filter.isSelected()) {
            String sql = "SELECT * FROM product_measurement_subunit WHERE id LIKE "
                    + "'%" + search_filed.getText().trim() + "%' OR sub_unit_name LIKE '%" + search_filed.getText().trim() + "%'";
            internalView(sql);
        }
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        try {
            delete();
            view();
            clean();
            autoid();
        } catch (Exception e) {

        }

    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        try {
            update();
            view();
            clean();
            autoid();
        } catch (Exception e) {

        }
    }

    @FXML
    private void Addbtn(ActionEvent event) {
        try {
            insert();
            view();
            clean();
            autoid();
        } catch (Exception e) {

        }
    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clean();
        autoid();
    }

    @FXML
    private void Viiewbtn(ActionEvent event) {
        view();
    }

    @FXML
    private void search_keyAction(KeyEvent event) {
        search();
    }

    @FXML
    private void reportbtn(ActionEvent event) {
        Report();
    }

    @FXML
    private void TableClick(MouseEvent event) {
        tableclick(event);
    }

    @FXML
    private void MeasurementAction(ActionEvent event) {
        measurementUnitAction();
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }

}

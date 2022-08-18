package fxsupershop.Measurment;

import com.jfoenix.controls.*;
import fxsupershop.TableView.MesurementView;
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
public class Mesurement_InfoController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
//
//    @FXML
//    private ToggleGroup search;
    @FXML
    private TableView tableview;
    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs;
//    private JFXComboBox ItemName_cat;
    ObservableList data = FXCollections.observableArrayList();
    @FXML
    private TableColumn Tid;

    @FXML
    private JFXRadioButton id_filter;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private JFXTextField text_mesure_id;
    @FXML
    private JFXTextField text_mesure_type;
    @FXML
    private TableColumn<?, ?> TmeasurementType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = fxsupershop.Connection.connection_Sql.ConnectDb();

        view();
    }

    public void insert() {
        try {
            if (text_mesure_id.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Measurement ID", ButtonType.OK).showAndWait();
                return;
            }
            if (text_mesure_type.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Measurement Type", ButtonType.OK).showAndWait();
                return;
            } else if (text_mesure_id.getText() != null && text_mesure_type.getText() != null) {
                String sql = "INSERT INTO `measurement_unit` (`measurement_id`,`measurement_unit`"
                        + ",measurement_sl)VALUES('" + text_mesure_id.getText() + "','" + text_mesure_type.getText() + "','" + text_mesure_id.getText() + "')";
                post = con.prepareStatement(sql);
                post.execute();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert1.setTitle("Successful");
                alert1.setContentText("Insert Successful");
                alert1.show();
                view();
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
            if (text_mesure_id.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Measurement ID", ButtonType.OK).showAndWait();
                return;
            }
            if (text_mesure_type.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Measurement Type", ButtonType.OK).showAndWait();
                return;
            } else if (text_mesure_id.getText() != null && text_mesure_type.getText() != null) {
                String sql = "update measurement_unit set measurement_unit='" + text_mesure_type.getText() + "'  WHERE measurement_id= '" + text_mesure_id.getText() + "'";
                post = con.prepareStatement(sql);
                post.execute();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert1.setTitle("Successful");
                alert1.setContentText("Update Successful");
                alert1.show();
                view();
                clean();
            }

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
            String sql = "delete from measurement_unit WHERE measurement_id= '" + text_mesure_id.getText() + "'";
            post = con.prepareStatement(sql);
            post.execute();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert1.setTitle("Successful");
            alert1.setContentText("Delete Successful");
            alert1.show();
            view();
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
        Tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        TmeasurementType.setCellValueFactory(new PropertyValueFactory<>("mesurement_type"));

        try {
            String sql = "SELECT * FROM measurement_unit order by measurement_id asc";
            post = con.prepareStatement(sql);
            rs = post.executeQuery();
            while (rs.next()) {

                data.add(new MesurementView(rs.getString("measurement_id"),
                        rs.getString("measurement_unit")
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

                String sql = "SELECT * FROM measurement_unit WHERE measurement_id LIKE '%" + search_filed.getText() + "%' order by measurement_id asc";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    data.add(new MesurementView(rs.getString("measurement_id"),
                            rs.getString("measurement_unit")
                    ));

                }
                tableview.setItems(data);
            } else if (name_filter.isSelected()) {
                data.clear();

                String sql = "SELECT * FROM measurement_unit WHERE measurement_id LIKE '%" + search_filed.getText() + "%' "
                        + "OR measurement_unit LIKE '%" + search_filed.getText() + "%' order by measurement_id asc";
                post = con.prepareStatement(sql);
                rs = post.executeQuery();
                while (rs.next()) {

                    data.add(new MesurementView(rs.getString("measurement_id"),
                            rs.getString("measurement_unit")
                    ));

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

    public void Click(MouseEvent event) {
        tableview.setEditable(true);
        try {
            if (event.getClickCount() == 1) {
                @SuppressWarnings("rawtypes")
                TablePosition pos = (TablePosition) tableview.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                int col = pos.getColumn();
                @SuppressWarnings("rawtypes")
                TableColumn column = pos.getTableColumn();
                String val = column.getCellData(row).toString();

                if (col == 0 || col == 1) {
                    String sql = "SELECT * FROM measurement_unit WHERE measurement_id = '" + val + "' "
                            + "OR measurement_unit = '" + val + "'";
                    post = con.prepareStatement(sql);
                    rs = post.executeQuery();
                    if (rs.next()) {
                        text_mesure_id.setText(rs.getString("measurement_id"));
                        text_mesure_type.setText(rs.getString("measurement_unit"));

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

    public void clean() {
        text_mesure_id.setText(null);
        text_mesure_type.setText(null);
        text_mesure_id.requestFocus();
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        delete();
    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        update();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
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
    private void search_keyAction(KeyEvent event) {
        search();
    }

    @FXML
    private void TableClick(MouseEvent event) {
        Click(event);
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
    }

}


package fxsupershop.Vat;

import com.jfoenix.controls.*;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.PrepareQueryFunction;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
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
public class VatController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTextField percent;
//    @FXML
//    private JFXRadioButton id_filter;
//    @FXML
//    private ToggleGroup search;
//    @FXML
//    private JFXRadioButton name_filter;
//    @FXML
//    private JFXTextField search_filed;
//    @FXML
//    private JFXButton reportbtn;
    @FXML
    private TableView tableview;
    @FXML
    private TableColumn<?, ?> col_date;
    @FXML
    private TableColumn<?, ?> col_percentage;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    LoginMultiFormController lmfc = new LoginMultiFormController();
    int id;
    ObservableList<VatView> data = FXCollections.observableArrayList();
    ResultSet rs;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        id = lmfc.User();
        view();
    }  
    
    private void insert(){
        String sql = "INSERT INTO vat (date,vat_parcentage,admin_id)VALUES ('"
                + ""+date.getValue()+"','"+percent.getText().trim()+"','"+id+"')";
        queryFunction.Insert(sql);
    }
    
    private void update(){
        String sql = "UPDATE vat SET vat_parcentage = '"+percent.getText().trim()+"',"
                + "admin_id = '"+id+"' WHERE date = '"+date.getValue()+"'";
        queryFunction.Update(sql);
    }
    
    private void delete(){
        String sql = "DELETE FROM vat WHERE date = '"+date.getValue()+"'";
        queryFunction.Delete(sql);
    }
    
    private void view(){
        data.clear();
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_percentage.setCellValueFactory(new PropertyValueFactory<>("percent"));
        try {
            String sql = "SELECT * FROM vat order by id desc";

            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                data.add(new VatView(queryFunction.service.DateFormate(rs.getString("date")),
                        rs.getString("vat_parcentage")
                ));
            }
            tableview.setItems(data);
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "View Unsuccessful\n" + e);

        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }
    
    private void clean(){
        date.setValue(null);
        percent.setText(null);
        date.requestFocus();
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        delete();
        view();
        clean();
    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        update();
        view();
        clean();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
        view();
        clean();
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
    private void Search(KeyEvent event) {
    }

    @FXML
    private void Report(ActionEvent event) {
    }

    @FXML
    private void TableView(MouseEvent event) {
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

                if (col == 0) {
                    String sql = "SELECT * FROM vat WHERE date = '" + val + "'";

                    rs = queryFunction.getResult(sql);
                    if (rs.next()) {
                        date.setValue(rs.getDate("date").toLocalDate());
                        percent.setText(rs.getString("vat_parcentage"));
                    }
                }
            }
        } catch (SQLException | HeadlessException e) {

        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

                
            }
        }
    }
    
}

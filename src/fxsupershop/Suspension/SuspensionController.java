
package fxsupershop.Suspension;

import com.jfoenix.controls.*;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.PrepareQueryFunction;
import java.net.URL;
import java.sql.ResultSet;
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
 * @author Rifat Rabbi
 */
public class SuspensionController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
    @FXML
    private JFXTextField susID;
    @FXML
    private JFXTextField susName;
//    @FXML
//    private AnchorPane table_top_pane;
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
    private TableColumn<?, ?> itemid;
    @FXML
    private TableColumn<?, ?> susname;
    int presentID;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    int user;
    LoginMultiFormController lmfc = new LoginMultiFormController();
    ObservableList<Model> data = FXCollections.observableArrayList();
    ResultSet rs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        autoID();
        user = lmfc.User();
        view();    }   
    
    private void autoID(){
        presentID = queryFunction.AutoJFXID("suspension");
        susID.setText(String.valueOf(presentID));
    }
    
    private void insert() {

        if (susID.getText().equals("")) {
            Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty ID", ButtonType.OK).showAndWait();
            return;
        }
        if (susName.getText().equals("")) {
            Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Suspension Name", ButtonType.OK).showAndWait();
            return;
        }
        String sql = "INSERT INTO `suspension` (`id`,`sus_name`,admin_id,created_at)VALUES"
                + "('" + susID.getText().trim() + "','" + susName.getText().trim() + "',"
                + "'"+user+"','"+queryFunction.service.getDateTime()+"')";
        queryFunction.Insert(sql);
        view();
        clean();
    }
    
    private void update() {

        String sql = "UPDATE suspension SET`sus_name`='" + susName.getText().trim() + "' "
                + "WHERE `id`='" + susID.getText() + "'";
        queryFunction.Update(sql);
        view();
        clean();
    }
    
    private void delete() {

        String sql = "DELETE FROM suspension WHERE `id`='" + susID.getText() + "'";
        queryFunction.Delete(sql);
        view();
        clean();
    }
    
    public void initview(String query){
        try {
            data.clear();
            String sql = query;
            rs = queryFunction.getResult(sql);
            while (rs.next()) {

                data.add(new Model(rs.getString("id"),
                        rs.getString("sus_name")
                ));

            }

            tableview.setItems(data);
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("View Unsuccessful", "Warning", "Have a Problem.\n" + e);
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {

            }
        }
       
    }
    
    private void view() {
        itemid.setCellValueFactory(new PropertyValueFactory<>("id"));
        susname.setCellValueFactory(new PropertyValueFactory<>("sus_name"));
            String sql = "SELECT `id`,`sus_name` FROM `suspension`";
            initview(sql);
    }
    
    private void search() {
            if (id_filter.isSelected()) {
                
                String sql = "SELECT `id`,`sus_name` FROM `suspension` WHERE `id`='" + search_filed.getText() + "'";
                initview(sql);
            } else if (name_filter.isSelected()) {
               
                String sql = "SELECT `id`,`sus_name` FROM `suspension` WHERE `sus_name` LIKE '%" + search_filed.getText() + "%' or `id` like '%" + search_filed.getText() + "%'";
                initview(sql);
            }
    }
    
    private void clean() {
        susID.setText(null);
        susName.setText(null);
        susID.requestFocus();
        autoID();
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
    private void TableSearch(KeyEvent event) {
        search();
    }

    @FXML
    private void reportbtn(ActionEvent event) {
    }

    @FXML
    private void tableClick(MouseEvent event) {
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
                    String sql = "SELECT * FROM `suspension` WHERE `id` = '" + val + "' OR `sus_name` = '" + val + "'";
                    rs = queryFunction.getResult(sql);
                    if (rs.next()) {
                        susID.setText(rs.getString("id"));
                        susName.setText(rs.getString("sus_name"));
                    }
                }
            }
        } catch (Exception e) {

        }
    }
    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize(); 
    }
}

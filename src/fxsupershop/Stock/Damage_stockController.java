
package fxsupershop.Stock;

import com.jfoenix.controls.*;
import fxsupershop.Services.*;
import fxsupershop.TableView.CurrentStockView;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Damage_stockController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
//    @FXML
//    private AnchorPane stackcard;
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
    private TableColumn<?, ?> proname_col;
    @FXML
    private TableColumn<?, ?> quanName_col;
    @FXML
    private TableColumn<?, ?> unit_col;
    @FXML
    private TableColumn<?, ?> cus_col;
//    @FXML
//    private JFXButton refbtn;
    ResultSet rs;
    ObservableList data = FXCollections.observableArrayList();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    Message msg = new Message();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        view();
    }    

    private void initview(String query){
        try {
            String sql = query;
            rs = queryFunction.getResult(sql);
            while (rs.next()) {

                data.add(new CurrentStockView(rs.getString("product_name"),
                        rs.getString("damage_qunt"), rs.getString("sub_unit_name"), rs.getString("customer_name")
                ));
            }
            tableview.setItems(data);
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "View Unsuccessful\n" + e);

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
        
        proname_col.setCellValueFactory(new PropertyValueFactory<>("proName"));
        quanName_col.setCellValueFactory(new PropertyValueFactory<>("quan"));
        unit_col.setCellValueFactory(new PropertyValueFactory<>("purch_price"));
        cus_col.setCellValueFactory(new PropertyValueFactory<>("sl_price"));
       
            String sql = "SELECT damage_stock.id,damage_stock.damage_qunt,product_measurement_subunit.sub_unit_name,damage_stock."
                    + "unit,damage_stock.cus_id,product_productinfo.product_name,customer_info.customer_name FROM damage_stock\n"
                    + "INNER JOIN product_productinfo ON damage_stock.fk_pro_id=product_productinfo."
                    + "id INNER JOIN customer_info ON damage_stock.cus_id=customer_info.id"
                    + " INNER JOIN product_measurement_subunit ON damage_stock.unit=product_measurement_subunit.id order by damage_stock.id asc";
            initview(sql);
    }

    private void search() {
            if (id_filter.isSelected()) {
                data.clear();

                String sql = "SELECT damage_stock.id,damage_stock.damage_qunt,product_measurement_subunit.sub_unit_name,damage_stock."
                    + "unit,damage_stock.cus_id,product_productinfo.product_name,customer_info.customer_name FROM damage_stock\n"
                    + "INNER JOIN product_productinfo ON damage_stock.fk_pro_id=product_productinfo."
                    + "id INNER JOIN customer_info ON damage_stock.cus_id=customer_info.id"
                    + "INNER JOIN product_measurement_subunit ON damage_stock.unit=product_measurement_subunit.id WHERE "
                        + "damage_stock.id LIKE '%" + search_filed.getText() + "%' OR product_productinfo.product_name LIKE '%" + search_filed.getText() + "%' order by damage_stock.id asc";
                
                initview(sql);
            } else if (name_filter.isSelected()) {
                data.clear();

                String sql = "SELECT damage_stock.id,damage_stock.damage_qunt,product_measurement_subunit.sub_unit_name,damage_stock."
                    + "unit,damage_stock.cus_id,product_productinfo.product_name,customer_info.customer_name FROM damage_stock\n"
                    + "INNER JOIN product_productinfo ON damage_stock.fk_pro_id=product_productinfo."
                    + "id INNER JOIN customer_info ON damage_stock.cus_id=customer_info.id"
                    + "INNER JOIN product_measurement_subunit ON damage_stock.unit=product_measurement_subunit.id WHERE "
                        + "damage_stock.id LIKE '%" + search_filed.getText() + "%' OR product_productinfo.product_name LIKE '%" + search_filed.getText() + "%' order by damage_stock.id asc";
                initview(sql);
            }
        
    }
    
    @FXML
    private void search_keyAction(KeyEvent event) {
        search();
    }

    @FXML
    private void viewallbtn(ActionEvent event) {
        view();
    }
    
}

package fxsupershop.Stock;

import com.jfoenix.controls.*;
import fxsupershop.Services.*;
import fxsupershop.TableView.CurrentStockView;
import java.net.URL;
import java.sql.*;
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
public class Stock_InfoController implements Initializable {

    
//    @FXML
//    private ToggleGroup search;
    @FXML
    private TableView tableview;
    ResultSet rs;
    ObservableList data = FXCollections.observableArrayList();
    @FXML
    private JFXRadioButton id_filter;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private TableColumn<?, ?> proname_col;
    @FXML
    private TableColumn<?, ?> quanName_col;
    @FXML
    private TableColumn<?, ?> purchesPrice_col;
    @FXML
    private TableColumn<?, ?> salePrice_col;
    Message msg = new Message();
//    @FXML
//    private AnchorPane itempane;
//    @FXML
//    private AnchorPane stackcard;
//    @FXML
//    private JFXButton refbtn;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    
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
                        rs.getString("quantity"), rs.getString("purchase_price"), rs.getString("sale_price")
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
        purchesPrice_col.setCellValueFactory(new PropertyValueFactory<>("purch_price"));
        salePrice_col.setCellValueFactory(new PropertyValueFactory<>("sale_price"));
       
            String sql = "SELECT stock_product.id,stock_product.quantity,stock_product.purchase_price,stock_product.sale_price,product_productinfo.product_name FROM stock_product\n"
                    + "INNER JOIN product_productinfo ON stock_product.product_id=product_productinfo.id order by stock_product.id asc";
            initview(sql);
    }

    private void search() {
            if (id_filter.isSelected()) {
                data.clear();

                String sql = "SELECT stock_product.id,stock_product.quantity,stock_product.purchase_price,stock_product.sale_price,product_productinfo.product_name FROM stock_product\n"
                        + "INNER JOIN product_productinfo ON stock_product.product_id=product_productinfo.id WHERE "
                        + "stock_product.id LIKE '%" + search_filed.getText() + "%' OR product_productinfo.product_name LIKE '%" + search_filed.getText() + "%' order by stock_product.id asc";
                
                initview(sql);
            } else if (name_filter.isSelected()) {
                data.clear();

                String sql = "SELECT stock_product.id,stock_product.quantity,stock_product.purchase_price,stock_product.sale_price,product_productinfo.product_name FROM stock_product\n"
                        + "INNER JOIN product_productinfo ON stock_product.product_id=product_productinfo.id WHERE "
                        + "stock_product.id LIKE '%" + search_filed.getText() + "%' OR product_productinfo.product_name LIKE '%" + search_filed.getText() + "%' order by stock_product.id asc";
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


package fxsupershop.Most_saleProduct;

import fxsupershop.Services.*;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Rifat Rabbi
 */
public class Most_productListController implements Initializable {

    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> pro_col;
    @FXML
    private TableColumn<?, ?> count_col;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    ResultSet rs,rss;
    ObservableList data = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        view();
    } 
    
    private void view(){
        try {
            data.clear();
            pro_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            count_col.setCellValueFactory(new PropertyValueFactory<>("count"));
            
            String sql = "SELECT COUNT(`product_id`) AS 'count',`product_id` FROM sale_entry "
                    + "GROUP BY `product_id` ORDER BY COUNT(`product_id`) DESC";
            rs = queryFunction.getResult(sql);
            while(rs.next()){
                String sql1 = "SELECT * FROM product_productinfo WHERE id = '"+rs.getString("product_id")+"'";
                rss = queryFunction.getResult(sql1);
                if(rss.next()){
                    data.addAll(new Model(rss.getString("product_name"), rs.getString("count")));
                }
            }
            tableview.setItems(data);
        } catch (Exception e) {
        }finally{
            try {
                rs.close();
                rss.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    @FXML
    private void Clicked(MouseEvent event) {
    }
    
}

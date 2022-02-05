package fxsupershop.Services.View_by_date_pane;

import com.jfoenix.controls.JFXDatePicker;
import fxsupershop.Services.PrepareQueryFunction;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class View_by_dateController implements Initializable {

//    @FXML
//    private Pane date_search_pane;
    @FXML
    private JFXDatePicker date1;
    @FXML
    private JFXDatePicker date2;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    ResultSet rs;
    String sql;
    static Model m;
    static Object ob;
    static int func;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        date1.setValue(null);
        date2.setValue(null);
    }

    public static void setdata(Model m, Object ob, int func) {
        View_by_dateController.m = m;
        View_by_dateController.ob = ob;
        View_by_dateController.func = func;
    }

    private void function1() {
        try {
            String start_date = queryFunction.service.getJFXDate(date1);
            String end_date = queryFunction.service.getJFXDate(date2);

            sql = "SELECT * FROM " + m.getTableName() + " WHERE " + m.getColumn() + " BETWEEN "
                    + "'" + start_date + "' AND '" + end_date + "'";
            Class parameter = String.class;
            ob.getClass().getMethod("initview", parameter).invoke(ob, sql);
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("View Unsuccessful", "Warning", "Have a Problem.\n" + e);
        }

    }
//    SELECT * FROM `product_item` WHERE id BETWEEN 
//(SELECT id FROM `product_item` WHERE created_at LIKE '2018-02-01_________') AND 
//(SELECT id FROM `product_item` WHERE created_at LIKE '2018-02-01_________')

    private void function2() {
        try {
            String start_id = null, end_id = null;
            String start_date = queryFunction.service.getJFXDate(date1);
            String end_date = queryFunction.service.getJFXDate(date2);

            String sql1 = "SELECT id FROM " + m.getTableName() + " WHERE " + m.getColumn() + " "
                    + "LIKE '" + start_date + "_________' ";
            queryFunction.getResult(sql1);
            if (rs.next()) {
                start_id = rs.getString("id");
            }
            String sql2 = "SELECT id FROM " + m.getTableName() + " WHERE " + m.getColumn() + " "
                    + "LIKE '" + end_date + "_________' ";
            queryFunction.getResult(sql2);
            while (rs.next()) {
                end_id = rs.getString("id");
            }
            System.out.println(start_id);
            System.out.println(end_id);
            sql = "SELECT * FROM " + m.getTableName() + " WHERE id BETWEEN '" + start_id + "' "
                    + "AND '" + end_id + "'";
            System.out.println(sql);
            Class parameter = String.class;
            ob.getClass().getMethod("initview", parameter).invoke(ob, sql);
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("View Unsuccessful", "Warning", "Have a Problem.\n" + e);
        }
    }

    @FXML
    private void DateSearch(ActionEvent event) {
        if (date1.getValue() == null && date2.getValue() == null) {
            queryFunction.service.msg.ConditionalMessage("Empty Value");
        } else if (date1.getValue() != null && date2.getValue() != null) {
            if (func == 1) {
                function1();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}

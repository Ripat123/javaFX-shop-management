package fxsupershop.Product;

import com.jfoenix.controls.*;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.ItemView;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Item_infoController implements Initializable {

//    @FXML
//    private ToggleGroup search;
//    @FXML
//    private AnchorPane itempane;
    ResultSet rs;
    @FXML
    private TableView<ItemView> tableview;
    ObservableList<ItemView> data = FXCollections.observableArrayList();
    @FXML
    private JFXTextField itemId;
    @FXML
    private JFXTextField itemName;
    @FXML
    private TableColumn<ItemView, Integer> itemid;
    @FXML
    private TableColumn<ItemView, String> itemname;
    @FXML
    private JFXRadioButton id_filter;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    Message msg = new Message();
    PrepareQueryFunction prepareQueryFunction = new PrepareQueryFunction();
    Report report = new Report();
    GeneralService gs = new GeneralService();
//    @FXML
//    private AnchorPane table_top_pane;
    int user;
    LoginMultiFormController lmfc = new LoginMultiFormController();
    int presentID;
    @FXML
    private Pane action_pane;
    @FXML
    private JFXButton report_id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        view();
        user = lmfc.User();
        autoID();
    }

    private void autoID() {
        presentID = prepareQueryFunction.AutoJFXID("product_item");
        itemId.setText(String.valueOf(presentID));
    }

    private void insert() {

        if (itemId.getText().equals("")) {
            Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Item ID", ButtonType.OK).showAndWait();
            return;
        }
        if (itemName.getText().equals("")) {
            Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Item Name", ButtonType.OK).showAndWait();
            return;
        }
        String sql = "INSERT INTO `product_item` (`id`,`item_name`,admin_id,created_at)VALUES"
                + "('" + itemId.getText().trim() + "','" + itemName.getText().trim() + "',"
                + "'" + user + "','" + prepareQueryFunction.service.getDateTime() + "')";
        prepareQueryFunction.Insert(sql);

    }

    private void update() {

        String sql = "UPDATE product_item SET`item_name`='" + itemName.getText().trim() + "' "
                + "WHERE `id`='" + itemId.getText() + "'";
        prepareQueryFunction.Update(sql);

    }

    private void delete() {

        String sql = "DELETE FROM product_item WHERE `id`='" + itemId.getText() + "'";
        prepareQueryFunction.Delete(sql);

    }

    public void initview(String query) {
        try {
            data.clear();
            String sql = query;
            rs = prepareQueryFunction.getResult(sql);
            while (rs.next()) {

                data.add(new ItemView(rs.getString("id"),
                        rs.getString("item_name")
                ));

            }

            tableview.setItems(data);
        } catch (Exception e) {
            msg.WarningMessage("View Unsuccessful", "Warning", "Have a Problem.\n" + e);
        } finally {
            try {
                rs.close();
                prepareQueryFunction.post.close();
            } catch (Exception e) {

            }
        }

    }

    private void search() {
        if (id_filter.isSelected()) {

            String sql = "SELECT `id`,`item_name` FROM `product_item` WHERE `id`='" + search_filed.getText() + "'";
            initview(sql);
        } else if (name_filter.isSelected()) {

            String sql = "SELECT `id`,`item_name` FROM `product_item` WHERE `item_name` LIKE '%" + search_filed.getText() + "%' or `id` like '%" + search_filed.getText() + "%'";
            initview(sql);
        }
    }

    private void view() {
        itemid.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemname.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        String sql = "SELECT `id`,`item_name` FROM `product_item`";
        initview(sql);
    }

    private void clean() {
        itemId.setText(null);
        itemName.setText(null);
        itemId.requestFocus();
        autoID();
    }

    public void Report() {
        String sql = "SELECT * FROM `product_item`,project_info order by product_item.id asc";
        prepareQueryFunction.getImagePath(sql, "image");
        report.getReport("/fxsupershop/Product/Report/", "Item_Report.jrxml", sql);

    }

    @FXML
    private void onViiew(ActionEvent event) {
        view();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        try {
            insert();
            view();
            clean();
        } catch (Exception e) {

        }

    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        try {
            update();
            view();
            clean();
        } catch (Exception e) {

        }

    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        try {
            delete();
            view();
            clean();
        } catch (Exception e) {

        }

    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clean();
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

                if (col == 0 || col == 1) {
                    String sql = "SELECT * FROM `product_item` WHERE `id` = '" + val + "' OR `item_name` = '" + val + "'";
                    rs = prepareQueryFunction.getResult(sql);
                    if (rs.next()) {
                        itemId.setText(rs.getString("id"));
                        itemName.setText(rs.getString("item_name"));
                    }
                }
            }
        } catch (Exception e) {

        }

    }

    @FXML
    private void TableSearch(KeyEvent event) {
        search();
    }

    @FXML
    private void reportbtn(ActionEvent event) {
        JFXPopup popup = new JFXPopup();
        prepareQueryFunction.service.PopUPRight(action_pane, report_id, popup, 0, 0);
    }

//    private void DateSearch(ActionEvent event) throws IOException {
//        JFXPopup popup = new JFXPopup();
//        Node node = (Pane) FXMLLoader.load(getClass().getResource("/fxsupershop/Services/View_by_date_pane/View_by_date.fxml"));
//        prepareQueryFunction.service.PopUP(node, table_top_pane, popup, 0, 0);
//        View_by_dateController vc = new View_by_dateController();
//        Model m = new Model("product_item", "created_at");
//        vc.setdata(m,this,1);
//    }
    @FXML
    private void ReportAction(ActionEvent event) {
        Report();
    }

    @FXML
    private void ImportAction(ActionEvent event) {
        Connection con = prepareQueryFunction.getConnect();
        PreparedStatement post = null;
        FileChooser fc;
        fc = prepareQueryFunction.service.getOpenChooser("Select Location");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel File", "*.xls", "*.xlsx", "*.xlsm"));

        String Path = prepareQueryFunction.service.getOpenDialogPath(fc);
        if (Path != null) {
            try {
                String sql = "INSERT INTO `product_item`(`id`,`item_name`,admin_id,created_at) VALUES(?,?,?,?)";
                post = con.prepareStatement(sql);

                FileInputStream fileIn = new FileInputStream(new File(Path));
                XSSFWorkbook wb = new XSSFWorkbook(fileIn);

                XSSFSheet sheet = wb.getSheetAt(0);
                Row row;
                String name = null;
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    try {
                        presentID = prepareQueryFunction.AutoJFXID("product_item");
                            name = prefareData(row);
                            if(!name.equals("") && !name.equals(" ")){
                            post.setString(1, String.valueOf(presentID));
                            post.setString(2, name);
                            post.setString(3, String.valueOf(user));
                            post.setString(4, prepareQueryFunction.service.getDateTime());
                            post.execute();
                            }
                    } catch (Exception e) {
                    }
                }
                fileIn.close();
                post.close();
                rs.close();

            } catch (Exception e) {
                prepareQueryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
            }
        }
    }
    
    private String prefareData(Row row){
        String name;
        try {
            name = row.getCell(0).getStringCellValue();
        } catch (Exception e) {
            name = "";
        }
        return name;
    }

    @FXML
    private void exportAction(ActionEvent event) {
        String sql = "SELECT * FROM `product_item`,project_info order by product_item.id asc";
        prepareQueryFunction.getImagePath(sql, "image");
        report.ExportReport("/fxsupershop/Product/Report/", "item_format_data.jrxml", sql);
    }

    @FXML
    private void exportFormat(ActionEvent event) {
        String sql = "SELECT * FROM `product_item`";
        report.ExportReport("/fxsupershop/Product/Report/", "item_format.jrxml", sql);
    }

}

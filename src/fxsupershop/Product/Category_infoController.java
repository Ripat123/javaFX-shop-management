package fxsupershop.Product;

import com.jfoenix.controls.*;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.CategoryView;
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
public class Category_infoController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
    @FXML
    private JFXTextField categoryID;
    @FXML
    private JFXTextField categoryName;
//    @FXML
//    private ToggleGroup search;
    @FXML
    private TableView<CategoryView> tableview;
    ResultSet rs;
    @FXML
    private JFXComboBox ItemName_cat;
    ObservableList<CategoryView> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn<CategoryView, Integer> Tid;
    @FXML
    private TableColumn<CategoryView, String> Titemname;
    @FXML
    private TableColumn<CategoryView, String> Tcategoryname;
    @FXML
    private JFXRadioButton id_filter;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    Message msg = new Message();
    GeneralService service = new GeneralService();
    PrepareQueryFunction prepareQueryFunction = new PrepareQueryFunction();
    Report report = new Report();
    int presentID;
    @FXML
    private Pane action_pane;
    @FXML
    private JFXButton morebtn;
    private String itemID, category_Name;
    private int user;
    LoginMultiFormController lmfc = new LoginMultiFormController();
    ObservableList itemlist = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        veiwItem();
        view();
        autoID();
        user = lmfc.User();
    }

    private void autoID() {
        presentID = prepareQueryFunction.AutoJFXID("product_category");
        categoryID.setText(String.valueOf(presentID));
    }

    private void veiwItem() {
        String sql = "SELECT * FROM `product_item` LIMIT 100";
        itemlist = prepareQueryFunction.ViewArrayJFXComboBox(sql, "item_name", "id", ItemName_cat, itemlist);

    }

    private void insert() {
        if (categoryID.getText().equals("")) {
            msg.ConditionalMessage("Enter Category ID");
            return;
        }
        if (ItemName_cat.getEditor().getText().equals("")) {
            msg.ConditionalMessage("Select Item Name");
            return;
        }
        if (categoryName.getText().equals("")) {
            msg.ConditionalMessage("Enter Category Name");
            return;
        }
        presentID = prepareQueryFunction.AutoJFXID("product_category");
        String sql = "INSERT INTO `product_category` (`id`,`item_id`,`category_name`"
                + ")VALUES('" + presentID + "','" + itemID + "','" + categoryName.getText().trim() + "')";
        prepareQueryFunction.Insert(sql);
        clean();
    }

    private void update() {

        if (categoryID.getText().equals("")) {
            msg.ConditionalMessage("Enter Category ID");
            return;
        }
        if (ItemName_cat.getEditor().getText().equals("")) {
            msg.ConditionalMessage("Select Item Name");
            return;
        }
        if (categoryName.getText().equals("")) {
            msg.ConditionalMessage("Enter Category Name");
            return;
        }

        String sql = "update product_category set item_id='" + itemID + "',category_Name='" + categoryName.getText().trim() + "'  WHERE id= '" + categoryID.getText() + "'";
        prepareQueryFunction.Update(sql);
        clean();
    }

    private void delete() {
        String sql = "delete from  product_category WHERE id= '" + categoryID.getText() + "'";
        prepareQueryFunction.Delete(sql);
        clean();
    }

    private void initview(String query) {
        try {
            String sql = query;
            rs = prepareQueryFunction.getResult(sql);
            while (rs.next()) {

                data.add(new CategoryView(rs.getString("id"),
                        rs.getString("item_name"), rs.getString("category_name")
                ));
            }
            tableview.setItems(data);
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "View Unsuccessful\n" + e);

        } finally {
            try {
                prepareQueryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void view() {
        data.clear();
        Tid.setCellValueFactory(new PropertyValueFactory<>("id"));
        Titemname.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        Tcategoryname.setCellValueFactory(new PropertyValueFactory<>("catagoryname"));

        String sql = "SELECT product_category.id,product_category.category_name,product_item.item_name FROM product_category\n"
                + "INNER JOIN product_item  ON product_category.item_id=product_item.id order by id asc LIMIT 100";
        initview(sql);
    }

    private void search() {
        if (id_filter.isSelected()) {
            data.clear();

            String sql = "SELECT product_category.id,product_category.category_name,product_item.item_name FROM product_category INNER JOIN product_item ON product_category.item_id=product_item.id WHERE product_category.id='" + search_filed.getText() + "'";
            initview(sql);
        } else if (name_filter.isSelected()) {
            data.clear();

            String sql = "SELECT product_category.id,product_category.category_name,product_item.item_name FROM product_category INNER JOIN product_item ON product_category.item_id=product_item.id WHERE product_category.category_name LIKE '%" + search_filed.getText() + "%' or product_category.id LIKE '%" + search_filed.getText() + "%'";
            initview(sql);
        }

    }

    private void clean() {
        categoryID.setText(null);
        ItemName_cat.getEditor().setText("");
        categoryName.setText(null);
        categoryID.requestFocus();
        itemID = null;
        autoID();
    }

    private String prefareData(Row row) {
        try {
            itemID = String.valueOf(row.getCell(0).getNumericCellValue());
            if (itemID.equals("") || itemID.equals(" ")) {
                itemID = "0";
            }
        } catch (Exception e) {
            itemID = "0";
        }
        try {
            category_Name = row.getCell(1).getStringCellValue();
        } catch (Exception e) {
            category_Name = "";
        }
        return category_Name;
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        try {
            delete();
            view();
        } catch (Exception e) {

        }

    }

    @FXML
    private void Updatebtn(ActionEvent event) {

        try {
            update();
            view();
        } catch (Exception e) {

        }
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        try {
            insert();
            view();
        } catch (Exception e) {

        }
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
    private void SetItem(ActionEvent event) {
        try {
            itemID = itemlist.get(ItemName_cat.getSelectionModel().getSelectedIndex()).toString();
        } catch (Exception e) {
        }

    }

    @FXML
    private void ShowPressItem(KeyEvent event) {
        try {
            String sql = "SELECT * FROM `product_item` WHERE `item_name` LIKE '%" + ItemName_cat.getEditor().getText() + "%' LIMIT 100";
            itemlist = prepareQueryFunction.ShowArrayItemKeyReleased(sql, "item_name", "id", ItemName_cat, event, itemlist);
        } catch (Exception e) {
        }

    }

    @FXML
    private void shown(MouseEvent event) {
        veiwItem();
        ItemName_cat.show();
    }

    @FXML
    private void search_keyAction(KeyEvent event) {
        search();
    }

    @FXML
    private void TableClick(MouseEvent event) {
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
                    String sql = "SELECT product_category.id,product_category.category_name,product_item.item_name FROM product_category INNER JOIN product_item ON product_category.item_id=product_item.id WHERE product_category.id = '" + val + "' OR product_item.item_name = '" + val + "' OR product_category.category_name = '" + val + "'";
                    rs = prepareQueryFunction.getResult(sql);
                    if (rs.next()) {
                        categoryID.setText(rs.getString("id"));
                        ItemName_cat.getEditor().setText(rs.getString("item_name"));
                        categoryName.setText(rs.getString("category_name"));
                    }
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                prepareQueryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }

    }

    @FXML
    private void ReportAction(ActionEvent event) {
        String sql = "SELECT project_info.*,product_item.item_name,product_category.id,"
                + "product_category.category_name,product_category."
                + "category_name_bangla FROM product_category,project_info,product_item"
                + " WHERE product_category.item_id = product_item.id order by product_category.id asc";
        prepareQueryFunction.getImagePath(sql, "image");
        report.getReport("/fxsupershop/Product/Report/", ""
                + "Category_Report.jrxml", sql);
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
                String sql = "INSERT INTO `product_category`(`id`,`item_id`,category_name,admin_id,created_at) VALUES(?,?,?,?,?)";
                post = con.prepareStatement(sql);

                FileInputStream fileIn = new FileInputStream(new File(Path));
                XSSFWorkbook wb = new XSSFWorkbook(fileIn);

                XSSFSheet sheet = wb.getSheetAt(0);
                Row row;
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    try {
                        presentID = prepareQueryFunction.AutoJFXID("product_category");
                        category_Name = prefareData(row);
                        if (!category_Name.equals("") && !category_Name.equals(" ")
                                && !itemID.equals("0")) {
                            post.setString(1, String.valueOf(presentID));
                            post.setString(2, itemID);
                            post.setString(3, category_Name);
                            post.setString(4, String.valueOf(user));
                            post.setString(5, prepareQueryFunction.service.getDateTime());
                            post.execute();
                        }
                    } catch (Exception e) {
                    }
                }
                fileIn.close();
                post.close();

            } catch (Exception e) {
                prepareQueryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
            }
        }
    }

    @FXML
    private void exportAction(ActionEvent event) {
        String sql = "SELECT * FROM `product_category`";
        report.ExportReport("/fxsupershop/Product/Report/", "category_format_data.jrxml", sql);
    }

    @FXML
    private void exportFormat(ActionEvent event) {
        String sql = "SELECT * FROM `product_category`";
        report.ExportReport("/fxsupershop/Product/Report/", "category_format.jrxml", sql);
    }

    @FXML
    private void moreAction(ActionEvent event) {
        JFXPopup popup = new JFXPopup();
        prepareQueryFunction.service.PopUPRight(action_pane, morebtn, popup, 0, 0);
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}

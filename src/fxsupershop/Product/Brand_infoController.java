package fxsupershop.Product;

import com.jfoenix.controls.*;
import fxsupershop.Connection.connection_Sql;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.BrandView;
import java.awt.HeadlessException;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.*;
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
public class Brand_infoController implements Initializable {

//    @FXML
//    private ToggleGroup search;
    @FXML
    private TableView<BrandView> tableview;
    @FXML
    private JFXTextField brandName;
    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs;
    @FXML
    private JFXTextField brand_ID;
    ObservableList<BrandView> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn<BrandView, String> col_brandname;
    @FXML
    private TableColumn<BrandView, Integer> col_brandid;
    @FXML
    private JFXRadioButton id_filter;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    GeneralService service = new GeneralService();
    PrepareQueryFunction prepareQueryFunction = new PrepareQueryFunction();
    Report report = new Report();
    Message msg = new Message();
    @FXML
    private JFXButton reportbtn;
    Product_infoController product_infoController = new Product_infoController();
//    @FXML
//    private AnchorPane itempane;
    int presentID;
    @FXML
    private Pane action_pane;
    int user;
    LoginMultiFormController lmfc = new LoginMultiFormController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service1.start();
        service1.setOnSucceeded((event) -> {
            service1.cancel();
        });
    }

    Service service1 = new Service() {
        @Override
        protected Task createTask() {
            return new Task() {
                @Override
                protected Void call() {
                    initSource();
                    return null;
                }
            };
        }
    };

    public void initSource() {
        con = connection_Sql.ConnectDb();
        view();
        autoID();
        user = lmfc.User();
    }

    private void autoID() {
        presentID = prepareQueryFunction.AutoJFXID("product_brand");
        brand_ID.setText(String.valueOf(presentID));
    }

    private void insert() {
        if (brandName.getText().equals("")) {
            msg.ConditionalMessage("Enter Brand Name");
            return;
        }
        if (brand_ID.getText().equals("")) {
            msg.ConditionalMessage("Empty Brand ID");
            return;
        }

        String sql = "INSERT INTO `product_brand` (`brand_name`,"
                + "`id`)VALUES('" + brandName.getText().trim() + "','" + brand_ID.getText() + "')";
        prepareQueryFunction.Insert(sql);
        clean();

    }

    private void update() {
        if (brandName.getText().equals("")) {
            msg.ConditionalMessage("Enter Brand Name");
            return;
        }
        if (brand_ID.getText().equals("")) {
            msg.ConditionalMessage("Empty Brand ID");
            return;
        }

        String sql = "UPDATE product_brand SET brand_name='"
                + "" + brandName.getText().trim() + "' WHERE id= '" + brand_ID.getText() + "'";
        prepareQueryFunction.Update(sql);
        clean();
    }

    private void delete() {

        String sql = "delete from  product_brand WHERE id= '" + brand_ID.getText() + "'";
        prepareQueryFunction.Delete(sql);
        clean();
    }

    private void initView(String query) {
        try {
            String sql = query;
            rs = prepareQueryFunction.getResult(sql);
            while (rs.next()) {

                data.add(new BrandView(rs.getString("brand_name"), rs.getString("id")
                ));

            }
            tableview.setItems(data);
        } catch (Exception e) {
            msg.ErrorMessage("Unsucccessful", "Error", "Have a Problem.\n" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void view() {
        data.clear();

        col_brandname.setCellValueFactory(new PropertyValueFactory<>("brandname"));
        col_brandid.setCellValueFactory(new PropertyValueFactory<>("id"));

        String sql = "SELECT * FROM product_brand order by id asc";
        initView(sql);
    }

    private void search() {
        if (id_filter.isSelected()) {
            data.clear();

            String sql = "SELECT product_brand."
                    + "brand_name,product_brand.id FROM product_brand WHERE product_brand.id='" + search_filed.getText() + "' order by id asc";
            initView(sql);
        } else if (name_filter.isSelected()) {
            data.clear();

            String sql = "SELECT product_brand.brand_name,product_brand.id FROM product_brand "
                    + "WHERE product_brand.brand_name LIKE '%" + search_filed.getText() + "%' "
                    + "OR product_brand.id LIKE '%" + search_filed.getText() + "%'";
            initView(sql);
        }
    }

    private void clean() {
        brandName.setText("");
        brand_ID.setText("");
        brandName.requestFocus();
        autoID();
    }

    private String prefareData(Row row) {
        String name;
        try {
            name = row.getCell(0).getStringCellValue();
        } catch (Exception e) {
            name = "";
        }
        return name;
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
    private void Search(KeyEvent event) {
        search();
    }

    @FXML
    private void TableView(MouseEvent event) {
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

                if (col == 0 || col == 1 || col == 2 || col == 3) {
                    String sql = "SELECT * FROM product_brand WHERE product_brand.id = '" + val + "'";

                    rs = prepareQueryFunction.getResult(sql);
                    if (rs.next()) {
                        brand_ID.setText(rs.getString("id"));
                        brandName.setText(rs.getString("brand_name"));
                    }
                }
            }
        } catch (SQLException | HeadlessException e) {

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }

    }

    @FXML
    private void ReportAction(ActionEvent event) {
        String sql = "SELECT * FROM product_brand,project_info";
        prepareQueryFunction.getImagePath(sql, "image");
        report.getReport("/fxsupershop/Product/Report/", "Brand_Report.jrxml", sql);
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
                String sql = "INSERT INTO `product_brand`(`id`,`brand_name`,admin_id,created_at) VALUES(?,?,?,?)";
                post = con.prepareStatement(sql);

                FileInputStream fileIn = new FileInputStream(new File(Path));
                XSSFWorkbook wb = new XSSFWorkbook(fileIn);

                XSSFSheet sheet = wb.getSheetAt(0);
                Row row;
                String name = null;
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    try {
                        presentID = prepareQueryFunction.AutoJFXID("product_brand");
                        name = prefareData(row);
                        if (!name.equals("") && !name.equals(" ")) {
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

            } catch (Exception e) {
                prepareQueryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
            }
        }
    }

    @FXML
    private void exportAction(ActionEvent event) {
        String sql = "SELECT * FROM `product_brand` order by product_brand.id asc";
        report.ExportReport("/fxsupershop/Product/Report/", "brand_format_data.jrxml", sql);
    }

    @FXML
    private void exportFormat(ActionEvent event) {
        String sql = "SELECT * FROM `product_brand`";
        report.ExportReport("/fxsupershop/Product/Report/", "brand_format.jrxml", sql);
    }

    @FXML
    private void moreAction(ActionEvent event) {
        JFXPopup popup = new JFXPopup();
        prepareQueryFunction.service.PopUPRight(action_pane, reportbtn, popup, 0, 0);
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}

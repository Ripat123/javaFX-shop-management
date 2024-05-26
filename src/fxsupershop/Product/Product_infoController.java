package fxsupershop.Product;

import com.jfoenix.controls.*;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.ProductView;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class Product_infoController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
    @FXML
    private JFXTextField proName;
    @FXML
    private JFXTextField purches_price;
    @FXML
    private JFXTextField Sale_price;
    @FXML
    private JFXTextArea details;
    @FXML
    private JFXTextField barcode;
    @FXML
    private JFXRadioButton id_filter;
//    @FXML
//    private ToggleGroup search;
    @FXML
    private JFXRadioButton name_filter;
    @FXML
    private JFXTextField search_filed;
    @FXML
    private TableView<ProductView> tableview;
//    private TableColumn<ProductView, String> itemname;
    @FXML
    private JFXDialogLayout dialogpane;
    @FXML
    private JFXDialog dialog;
//    private StackPane stackpane;
    @FXML
    private JFXButton closedialog;
    @FXML
    private Pane view_pane;
    @FXML
    private JFXComboBox textitemcomboname;
    @FXML
    private JFXComboBox textcategorycomboname;
    @FXML
    private JFXComboBox textbrandcomboname;
    @FXML
    private JFXComboBox textcombomeasurment;
    Connection con = null;
    PreparedStatement post = null;
    ResultSet rs;
    long len;
    static FileInputStream fin = null;
    @FXML
    private TableColumn<ProductView, String> proid;
    @FXML
    private TableColumn<ProductView, String> proname;
    @FXML
    private TableColumn<ProductView, Double> sale_price;
    ObservableList<ProductView> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn<ProductView, Double> PurchesPrice;
    PrepareQueryFunction prepareQueryFunction = new PrepareQueryFunction();
    Report report = new Report();
    Message msg = new Message();

    GeneralService generalService = new GeneralService();
    @FXML
    private JFXButton new_item_btn;
//    @FXML
//    private JFXButton new_category_btn;
//    @FXML
//    private JFXButton new_brand_btn;
//    @FXML
//    private JFXButton new_measurement_btn;
    @FXML
    private Pane imagePane;
    @FXML
    private ImageView fullimage;
    @FXML
    private AnchorPane impane;
    @FXML
    private Pane report_popPane;
    @FXML
    private JFXButton probtnid;
    private ImageView image;
    @FXML
    private TableColumn<?, ?> image_col;
//    private Image imagex = null;
    ProductPresenter pp = new ProductPresenter();
    LoginMultiFormController lmfc = new LoginMultiFormController();
    private int userid;
    private String autoID;
    @FXML
    private JFXTextField shelf;
    @FXML
    private JFXTextField shortage;
    @FXML
    private JFXTextField over;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Rectangle img_shape;
    private ImagePattern pattern;
//    private Image image1;
    @FXML
    private JFXButton search_btn;
    @FXML
    private Pane search_pane;
    @FXML
    private JFXComboBox search_combo;
    ProductPresenter p = new ProductPresenter();
    private String itemID, brandID, categoryID, meseasurementID, image_name, suspensionID;
    @FXML
    private JFXButton Exbtn;
    @FXML
    private JFXButton Repbtn;
    @FXML
    private JFXButton barbtn;
    @FXML
    private JFXButton setPbtn;
    @FXML
    private JFXButton setPNbtn;
    @FXML
    private JFXButton Spath;
    @FXML
    private StackPane main_stack;
    @FXML
    private JFXComboBox suspension;
    @FXML
    private JFXButton format_btn;
    @FXML
    private JFXButton format_data_btn;
    @FXML
    private JFXButton import_btn;
    @FXML
    private JFXButton setting_btn;
    ObservableList itemlist = FXCollections.observableArrayList();
    ObservableList categorylist = FXCollections.observableArrayList();
    ObservableList brandlist = FXCollections.observableArrayList();
    ObservableList measurementlist = FXCollections.observableArrayList();
    ObservableList searchlist = FXCollections.observableArrayList();
    ObservableList suspensionList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        task.run();
        task.setOnSucceeded((event) -> {
            task.cancel();
        });
    }
    Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            initSource();
            return null;
        }
    };

    public void initSource() {
        con = prepareQueryFunction.getConnect();
        userid = lmfc.User();
        itemview();
        suspensionView();
        categoryview();
        measurmentview();
        brandveiw();
        view();
        searchview();
        JFXScrollPane.smoothScrolling(scroll);
        autoID = p.autoid();
    }

    private void searchview() {
        String sql = "SELECT * FROM product_productinfo LIMIT 100";
        searchlist = prepareQueryFunction.ViewArrayJFXComboBox(sql, "pdt_name_en", "pdt_id", search_combo, searchlist);
    }

    private void itemview() {

        String sql = "SELECT * FROM `pdt_item` LIMIT 100";
        itemlist = prepareQueryFunction.ViewArrayJFXComboBox(sql, "item_name_en", "item_id", textitemcomboname, itemlist);

    }

    private void categoryview() {

        String sql = "SELECT * FROM `pdt_category` LIMIT 100";
        categorylist = prepareQueryFunction.ViewArrayJFXComboBox(sql, "cat_name_en", "cat_id", textcategorycomboname, categorylist);

    }

    private void brandveiw() {

        String sql = "SELECT * FROM `pdt_brand` LIMIT 100";
        brandlist = prepareQueryFunction.ViewArrayJFXComboBox(sql, "brand_name_en", "brand_id", textbrandcomboname, brandlist);

    }

    private void measurmentview() {

        String sql = "SELECT * FROM `measurement_unit` LIMIT 100";
        measurementlist = prepareQueryFunction.ViewArrayJFXComboBox(sql, "measurement_unit", "measurement_id", textcombomeasurment, measurementlist);

    }

    private void prefareField() {
        try {
            if (purches_price.getText().equals("")) {
                purches_price.setText("0");
            }
        } catch (Exception e) {
            purches_price.setText("0");
        }
        try {
            if (Sale_price.getText().equals("")) {
                Sale_price.setText("0");
            }
        } catch (Exception e) {
            Sale_price.setText("0");
        }
        try {
            if (shortage.getText().equals("")) {
                shortage.setText("5");
            }
        } catch (Exception e) {
            shortage.setText("5");
        }
        try {
            if (over.getText().equals("")) {
                over.setText("100");
            }
        } catch (Exception e) {
            over.setText("100");
        }
        try {
//            autoID = p.autoid();
            if (barcode.getText().equals("")) {
                barcode.setText(autoID);
            }
        } catch (Exception e) {
            barcode.setText(autoID);
        }
        try {
            if (details.getText().equals("")) {
                details.setText(" ");
            }
        } catch (Exception e) {
            details.setText(" ");
        }
        try {
            if (shelf.getText().equals("")) {
                shelf.setText("");
            }
        } catch (Exception e) {
            shelf.setText("");
        }
        try {
            if (suspension.getValue().equals("")) {
                suspension.setValue("");
                suspensionID = "";
            }
        } catch (Exception e) {
            suspension.setValue("");
            suspensionID = "";
        }
    }

    private void insert() {
        try {
            autoID = p.autoid();
            prefareField();
            if (proName.getText().equals("")) {
                msg.ConditionalMessage("Enter Product Name");
                return;
            }
            if (purches_price.getText().equals("") || purches_price.getText().equals("0")) {
                msg.ConditionalMessage("Enter Purchase Price");
                return;
            }
            if (Sale_price.getText().equals("") || Sale_price.getText().equals("0")) {
                msg.ConditionalMessage("Enter Sale Price");
                return;
            }

            if (itemID == null) {
                msg.ConditionalMessage("Item not found\nSelect Item Name");
                return;
            }
            if (categoryID == null) {
                msg.ConditionalMessage("Category not found\nSelect Category Name");
                return;
            }
            if (brandID == null) {
                msg.ConditionalMessage("Brand not found\nSelect Brand Name");
                return;
            }
            if (meseasurementID == null) {
                msg.ConditionalMessage("Measurement Unit not found\nSelect Measurment Name");
                return;
            }
            if (suspensionID == null && suspension.getValue().toString().equals("")) {
                msg.ConditionalMessage("Suspension not found\nSelect Suspension Name");
                return;
            }
            if (shortage.getText().equals("") || shortage.getText() == null) {
                shortage.setText("5");
            }
            if (over.getText().equals("") || over.getText() == null) {
                over.setText("100");
            }

            if (barcode.getText() == null && barcode.getText().equals("")) {
                barcode.setText(autoID);
            }
            
            String sql = "INSERT INTO product_productinfo (`pdt_id`,`pdt_item_id`,`pdt_cat_id`,`pdt_brand_id`,"
                    + "`pdt_name_en`,`pdt_measurement`,`pdt_purchase_price`,`pdt_sale_price`,`pdt_details`,"
                    + "`pdt_barcode`,pdt_admin_id,created_at,pdt_shelf_no,pdt_shortage,pdt_over_stock,pdt_suspension) "
                    + "VALUES (?,'" + itemID + "','" + categoryID + "','" + brandID + "',?,'" + meseasurementID + "',?,?,?,?,?,?,?,?,?,?)";

            post = con.prepareStatement(sql);
            post.setString(1, autoID);
            post.setString(2, proName.getText().trim());
            post.setString(3, purches_price.getText().trim());
            post.setString(4, Sale_price.getText().trim());
            post.setString(5, details.getText().trim());
            post.setString(6, barcode.getText().trim());
            post.setString(7, String.valueOf(userid));
            post.setString(8, generalService.getDateTime());
            post.setString(9, shelf.getText());
            post.setString(10, shortage.getText());
            post.setString(11, over.getText());
//            if ((len <= 0) == false) {
//                post.setString(12, autoID);
//                String sql2 = "SELECT * FROM project_info";
//                rs = prepareQueryFunction.getResult(sql2);
//                if (rs.next()) {
//                    if (rs.getString("image_path").equals("")) {
//                        prepareQueryFunction.service.msg.WarningMessage("Unsuccessful", "Image didn't upload", "Folder path not found\nPlease Select Folder and update image");
//                    } else {
//                        prepareQueryFunction.CreateImage(rs.getString("image_path"), autoID, fin);
//                    }
//                }
//
//            } else {
//                post.setString(12, null);
//            }
            post.setString(12, suspensionID);
            post.execute();

            msg.InformationMessage("Successful", "Information", "Insert Successful");
            clean();
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Insert Unsuccessful\n" + e);
            e.printStackTrace();

        } finally {
            try {
                post.close();

            } catch (Exception e) {

            }
        }
    }

    private void update() {
        try {
            prefareField();
            if (barcode.getText().equals("")) {
                barcode.setText(autoID);
            }
            String sql = "update product_productinfo set pdt_item_id='" + itemID + "',pdt_cat_id='" + categoryID + "',"
                    + "pdt_brand_id='" + brandID + "',"
                    + "pdt_name_en='" + proName.getText().trim() + "',pdt_measurement='" + meseasurementID + "',pdt_purchase_price='" + purches_price.getText().trim() + "',"
                    + "pdt_sale_price='" + Sale_price.getText().trim() + "',pdt_details="
                    + "'" + details.getText().trim() + "',pdt_barcode='" + barcode.getText().trim() + "',"
                    + "pdt_admin_id = '" + userid + "',updated_at = '" + generalService.getDateTime() + "',"
                    + "pdt_shelf_no = '" + shelf.getText() + "',pdt_shortage = '" + shortage.getText() + "',"
                    + "pdt_over_stock = '" + over.getText() + "',pdt_suspension = '" + suspensionID + "' WHERE "
                    + "pdt_id= '" + autoID + "'";
            System.out.println(sql);
            prepareQueryFunction.Update(sql);
            if ((len <= 0) == false) {
                String sql2 = "SELECT * FROM project_info";
                rs = prepareQueryFunction.getResult(sql2);
                if (rs.next()) {
                    prepareQueryFunction.CreateImage(rs.getString("image_path"), autoID, fin);
                }
            }
            clean();

        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Update Unsuccessful\n" + e);
        }
    }

    private void delete() {

        String sql = "delete from  product_productinfo WHERE pdt_id= '" + autoID + "'";
        prepareQueryFunction.Delete(sql);
        cleanAll();
    }

    private void initveiw(String query) {
        try {
            String sql = query;

            rs = prepareQueryFunction.getResult(sql);
            while (rs.next()) {
//                image = new ImageView();
//                image_name = rs.getString("image_name");
//                if (image_name != null) {
//                    viewImageintabel(image_name, image);
//                } else {
//                    image.setImage(null);
//                }
                data.add(new ProductView(rs.getString("pdt_id"),
                        rs.getString("pdt_name_en"), rs.getString("pdt_purchase_price"), rs.getString("pdt_sale_price"),
                        image
                ));
            }
            tableview.setItems(data);
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "View Unsuccessful\n" + e);

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
        proid.setCellValueFactory(new PropertyValueFactory<>("id"));
        proname.setCellValueFactory(new PropertyValueFactory<>("proname"));
        PurchesPrice.setCellValueFactory(new PropertyValueFactory<>("purchase_price"));
        sale_price.setCellValueFactory(new PropertyValueFactory<>("sale_price"));
        image_col.setCellValueFactory(new PropertyValueFactory<>("image"));

        String sql = "SELECT product_productinfo.pdt_image,product_productinfo.pdt_id,product_productinfo.pdt_name_en,"
                + "product_productinfo.pdt_purchase_price,product_productinfo.pdt_image,product_productinfo.pdt_sale_price,"
                + "pdt_item.item_name_en FROM product_productinfo "
                + "INNER JOIN pdt_item ON product_productinfo.pdt_item_id=pdt_item.item_id ORDER BY product_productinfo.pdt_id DESC LIMIT 100";
        initveiw(sql);

    }

    private void search() {
        try {
            if (id_filter.isSelected()) {
                data.clear();

                String sql = "SELECT product_productinfo.pdt_image,product_productinfo.pdt_id,product_productinfo.pdt_name_en,"
                        + "product_productinfo.pdt_purchase_price,product_productinfo.pdt_sale_price,product_productinfo.pdt_image,"
                        + "pdt_item.item_name_en FROM product_productinfo "
                        + "INNER JOIN pdt_item ON product_productinfo.pdt_item_id=pdt_item.item_id "
                        + "WHERE product_productinfo.pdt_id LIKE '%" + search_filed.getText() + "%' LIMIT 100";

                initveiw(sql);
            } else if (name_filter.isSelected()) {
                data.clear();

                String sql = "SELECT product_productinfo.pdt_image,product_productinfo.pdt_id,product_productinfo.pdt_name_en,"
                        + "product_productinfo.pdt_purchase_price,product_productinfo.pdt_sale_price,product_productinfo.pdt_image,"
                        + "pdt_item.item_name_en FROM product_productinfo "
                        + "INNER JOIN pdt_item ON product_productinfo.pdt_item_id=pdt_item.item_id "
                        + " WHERE product_productinfo.pdt_id LIKE '%" + search_filed.getText() + "%' "
                        + "OR product_productinfo.pdt_name_en LIKE '%" + search_filed.getText() + "%' LIMIT 100";

                initveiw(sql);
            }
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Search Unsuccessful\n" + e);
        }
    }

    private void ImageUpload() {
        try {
            File file = generalService.ImageUpload();
            fin = new FileInputStream(file);
            len = file.length();
            if (len <= 0) {
                msg.WarningMessage("Unsuccessful", "Warning", "Not a image");
            } else {
                img_shape.setFill(generalService.getImagepattern(file));
            }
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem" + e);
        }
    }

    private void viewimage(String image_name) {
        try {
            String sql2 = "SELECT * FROM project_info";
            ResultSet rss = prepareQueryFunction.getResult(sql2);
            if (rss.next()) {
                String path = rss.getString("image_path") + "\\" + image_name + ".jpg";
                len = new File(path).length();
                if ((len <= 0) == false) {
                    pattern = generalService.getImagepattern(new File(path));
                    img_shape.setFill(pattern);
                } else {
                    img_shape.setFill(null);
                }
            }

        } catch (Exception e) {
        }
    }

    private void viewImageintabel(String image_name, ImageView img) {
        try {
            String sql2 = "SELECT * FROM project_info";
            ResultSet rss = prepareQueryFunction.getResult(sql2);
            if (rss.next()) {
                pp.getImage(rss.getString("image_path"), image_name, img);
            }
        } catch (Exception e) {
        }

    }

    private void cleanAll() {
        autoID = null;
        proName.setText("");
        purches_price.setText("");
        Sale_price.setText("");
        details.setText("");
        barcode.setText("");
        textitemcomboname.getEditor().setText("");
        textcategorycomboname.getEditor().setText("");
        textbrandcomboname.getEditor().setText("");
        textcombomeasurment.getEditor().setText("");
        img_shape.setFill(null);
        shelf.setText(null);
        shortage.setText(null);
        over.setText(null);
        proName.requestFocus();
        fin = null;
        len = 0;
        suspension.setValue(null);
    }

    private void clean() {
        autoID = null;
        proName.setText("");
        purches_price.setText("");
        Sale_price.setText("");
        barcode.setText("");
        proName.requestFocus();
        img_shape.setFill(null);
        fin = null;
        len = 0;
    }

    private void suspensionView() {
        String sql = "SELECT * FROM `suspension` LIMIT 100";
        suspensionList = prepareQueryFunction.ViewArrayJFXComboBox(sql, "sus_name", "id", suspension, suspensionList);
    }

    @FXML
    private void Deletebtn(ActionEvent event) {

        delete();
        view();

    }

    @FXML
    private void Updatebtn(ActionEvent event) {

        update();
        view();

    }

    @FXML
    private void Addbtn(ActionEvent event) {

        insert();
        view();

    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        cleanAll();

    }

    @FXML
    private void Viiewbtn(ActionEvent event) {
        dialogpane.setVisible(true);
        dialogpane.setHeading(view_pane);
        dialog = new JFXDialog(main_stack, dialogpane, JFXDialog.DialogTransition.RIGHT);
        closedialog.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                dialog.setOnDialogClosed((e) -> {
                    dialogpane.setVisible(false);
                });
            }
        });
        dialog.show();
        view();
    }

    @FXML
    private void prolistbtn(ActionEvent event) {
        String sql = "SELECT product_productinfo.`pdt_id`,"
                + "product_productinfo.`pdt_name_en`,"
                + "product_productinfo.`product_name_bangla`,"
                + "product_productinfo.`pdt_measurement`,"
                + "product_productinfo.`pdt_purchase_price`,"
                + "product_productinfo.`pdt_sale_price`,"
                + "product_productinfo.`pdt_details`,"
                + "pdt_brand.`brand_name_en`,project_info.`Shop_name`,"
                + "project_info.`address`,"
                + "project_info.`phone`,"
                + "project_info.`email`,"
                + "project_info.`logo_path`,project_info.`image`"
                + " FROM project_info,product_productinfo INNER JOIN "
                + "pdt_brand ON product_productinfo.pdt_brand_id = pdt_brand.brand_id "
                + "order by product_productinfo.pdt_id asc";
        prepareQueryFunction.getImagePath(sql, "image");
        report.getReport("/fxsupershop/Product/Report/", "Product_Report.jrxml", sql);

    }

    @FXML
    private void TableSearch(KeyEvent event) {
        search();
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
//`pdt_id`, `pdt_item_id`, `pdt_cat_id`, `pdt_brand_id`, `pdt_name_en`,pdt_image, `pdt_measurement`,pdt_barcode 
//    `pdt_purchase_price`, `pdt_sale_price`,pdt_details, `pdt_suspension`, `pdt_shelf_no`, `pdt_shortage`,
//    `pdt_over_stock`, `pdt_admin_id`, `created_at `product_productinfo`
                    String sql = "SELECT product_productinfo.pdt_image,product_productinfo.pdt_id,product_productinfo.pdt_name_en,product_productinfo."
                            + "pdt_shelf_no,product_productinfo.pdt_shortage,product_productinfo.pdt_over_stock,"
                            + "product_productinfo.pdt_purchase_price,product_productinfo.pdt_sale_price,product_productinfo.pdt_image,suspension.sus_name,"
                            + "product_productinfo.product_details,product_productinfo.barcode,product_measurement.measurement_type, "
                            + "product_item.item_name,product_brand.brand_name,product_category.category_name FROM product_productinfo "
                            + "INNER JOIN suspension ON product_productinfo.suspension=suspension.id "
                            + "INNER JOIN product_item ON product_productinfo.item_id=product_item.id "
                            + "INNER JOIN product_brand ON product_productinfo.brand_id=product_brand.id "
                            + "INNER JOIN product_category ON product_productinfo.category_id=product_category."
                            + "id INNER JOIN product_measurement ON product_productinfo.measurement_type=product_measurement.id"
                            + " WHERE product_productinfo.id = '" + val + "' OR report_pathreport_pathreport_pathreport_pathreport_pathproduct_productinfo.product_name = '" + val + "'";

                    rs = prepareQueryFunction.getResult(sql);
                    if (rs.next()) {
                        autoID = rs.getString("id");
                        image_name = rs.getString("image_name");
                        proName.setText(rs.getString("product_name"));
                        purches_price.setText(rs.getString("purchase_price"));
                        Sale_price.setText(rs.getString("sale_price"));
                        details.setText(rs.getString("product_details"));
                        barcode.setText(rs.getString("barcode"));
                        textitemcomboname.getEditor().setText(rs.getString("item_name"));
                        textcategorycomboname.getEditor().setText(rs.getString("category_name"));
                        textbrandcomboname.getEditor().setText(rs.getString("brand_name"));
                        textcombomeasurment.getEditor().setText(rs.getString("product_measurement.measurement_type"));
                        shelf.setText(rs.getString("shelf_no"));
                        shortage.setText(rs.getString("shortage_list"));
                        over.setText(rs.getString("over_stock"));
                        suspension.setValue(rs.getString("sus_name"));
                        if (image_name != null) {
                            viewimage(image_name);
                        } else {
                            img_shape.setFill(null);
                        }

                        dialog.close();
                    }

                }
            }
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    @FXML
    private void uploadbtn(ActionEvent event) throws FileNotFoundException {
        ImageUpload();
    }

    @FXML
    private void itemaction(ActionEvent event) {
        try {
            itemID = itemlist.get(textitemcomboname.getSelectionModel().getSelectedIndex()).toString();
        } catch (Exception e) {
        }
    }

    @FXML
    private void categoryaction(ActionEvent event) {
        try {
            categoryID = categorylist.get(textcategorycomboname.getSelectionModel().getSelectedIndex()).toString();
        } catch (Exception e) {
        }
    }

    @FXML
    private void brandaction(ActionEvent event) {
        try {
            brandID = brandlist.get(textbrandcomboname.getSelectionModel().getSelectedIndex()).toString();
        } catch (Exception e) {
        }
    }

    @FXML
    private void measurementaction(ActionEvent event) {
        try {
            meseasurementID = measurementlist.get(textcombomeasurment.getSelectionModel().getSelectedIndex()).toString();
        } catch (Exception e) {
        }
    }

    @FXML
    private void newItemAction(ActionEvent event) throws IOException {
        JFXPopup pop = new JFXPopup();
        Node node = (StackPane) FXMLLoader.load(getClass().getResource("/fxsupershop/Product/Item_info.fxml"));
        node.setStyle("-fx-background-radius: 20; -fx-pref-height: 600; -fx-pref-width: 700;");
        generalService.PopUPRight(node, new_item_btn, pop, 120, -95);
        pop.setOnHidden(e -> {
            itemview();
        });
    }

    @FXML
    private void newIcategoryAction(ActionEvent event) throws IOException {
        JFXPopup pop = new JFXPopup();
        Node node = (StackPane) FXMLLoader.load(getClass().getResource("/fxsupershop/Product/Category_info.fxml"));
        node.setStyle("-fx-background-radius: 20; -fx-pref-height: 600; -fx-pref-width: 700;");
        generalService.PopUPRight(node, new_item_btn, pop, 120, -95);
        pop.setOnHidden(e -> {
            categoryview();
        });
    }

    @FXML
    private void newbrandAction(ActionEvent event) throws IOException {
        JFXPopup pop = new JFXPopup();
        Node node = (StackPane) FXMLLoader.load(getClass().getResource("/fxsupershop/Product/Brand_info.fxml"));
        node.setStyle("-fx-background-radius: 20; -fx-pref-height: 600; -fx-pref-width: 700;");
        generalService.PopUPRight(node, new_item_btn, pop, 120, -95);
        pop.setOnHidden(e -> {
            brandveiw();
        });
    }

    @FXML
    private void newmeasurementAction(ActionEvent event) throws IOException {
        JFXPopup pop = new JFXPopup();
        Node node = (StackPane) FXMLLoader.load(getClass().getResource("/fxsupershop/Measurment/Mesurement_Info.fxml"));
        node.setStyle("-fx-background-radius: 20; -fx-pref-height: 600; -fx-pref-width: 700;");
        generalService.PopUPLeft(node, new_item_btn, pop, -95, -95);
        pop.setOnHidden(e -> {
            measurmentview();
        });
    }

    @FXML
    private void OpenPopupImage(MouseEvent event) {
        try {
            JFXPopup popup = new JFXPopup();
            ProductPresenter p = new ProductPresenter();
            fullimage.setImage(pattern.getImage());
            p.PopUP(imagePane, impane, popup, 0, 0);
        } catch (Exception e) {

        }

    }

    @FXML
    private void Exportpro(ActionEvent event) {
        String sql = "SELECT product_productinfo.`id`,"
                + "product_productinfo.`product_name`,"
                + "product_productinfo.`product_name_bangla`,"
                + "product_productinfo.`measurement_type`,"
                + "product_productinfo.`purchase_price`,"
                + "product_productinfo.`sale_price`,"
                + "product_productinfo.`product_details`,"
                + "product_brand.`brand_name`,project_info.`Shop_name`,"
                + "project_info.`address`,"
                + "project_info.`phone`,"
                + "project_info.`email`,"
                + "project_info.`logo_path`,project_info.`image`"
                + " FROM project_info,product_productinfo INNER JOIN "
                + "product_brand ON product_productinfo.brand_id = product_brand.id "
                + "order by product_productinfo.id asc";
        prepareQueryFunction.getImagePath(sql, "image");
        report.ExportReport("/fxsupershop/Product/Report/", "Product_Report.jrxml", sql);
    }

    @FXML
    private void OpenProPop(ActionEvent event) {
        JFXPopup popup = new JFXPopup();
        setPbtn.setVisible(false);
        setPNbtn.setVisible(false);
        format_btn.setVisible(false);
        format_data_btn.setVisible(false);
        import_btn.setVisible(false);
        Repbtn.setVisible(true);
        Exbtn.setVisible(true);
        barbtn.setVisible(true);
        p.PopUP(report_popPane, probtnid, popup, 0, 0);
    }

    @FXML
    private void CleanImage(ActionEvent event) {
        img_shape.setFill(null);
        fin = null;
        len = 0;
    }

    @FXML
    private void SearchAction(ActionEvent event) {
        try {
            String sql = "SELECT product_productinfo.*,product_measurement.measurement_type, "
                    + "product_item.item_name,product_brand.brand_name,product_category.category_name FROM product_productinfo "
                    + "INNER JOIN product_item ON product_productinfo.item_id=product_item.id "
                    + "INNER JOIN product_brand ON product_productinfo.brand_id=product_brand.id "
                    + "INNER JOIN product_category ON product_productinfo.category_id=product_category."
                    + "id INNER JOIN product_measurement ON product_productinfo.measurement_type=product_measurement.id"
                    + " WHERE product_productinfo.id = '" + searchlist.get(search_combo.getSelectionModel().getSelectedIndex()) + "'";

            rs = prepareQueryFunction.getResult(sql);
            if (rs.next()) {
                autoID = rs.getString("id");
                proName.setText(rs.getString("product_name"));
                purches_price.setText(rs.getString("purchase_price"));
                Sale_price.setText(rs.getString("sale_price"));
                details.setText(rs.getString("product_details"));
//                barcode.setText(rs.getString("barcode"));
                textitemcomboname.getEditor().setText(rs.getString("item_name"));
                textcategorycomboname.getEditor().setText(rs.getString("category_name"));
                textbrandcomboname.getEditor().setText(rs.getString("brand_name"));
                textcombomeasurment.getEditor().setText(rs.getString("product_measurement.measurement_type"));
                shelf.setText(rs.getString("shelf_no"));
                shortage.setText(rs.getString("shortage_list"));
                over.setText(rs.getString("over_stock"));
                image_name = rs.getString("image_name");
                if (image_name != null) {
                    viewimage(image_name);
                } else {
                    img_shape.setFill(null);
                }
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void SearchbtnAction(ActionEvent event) {
        JFXPopup popup = new JFXPopup();
        generalService.PopUPLeft(search_pane, search_btn, popup, 0, 0);
    }

    @FXML
    private void searchReleased(KeyEvent event) {
        String sql = "SELECT * FROM `product_productinfo` WHERE `product_name` LIKE '%" + search_combo.getValue() + "%' LIMIT 100";
        searchlist = prepareQueryFunction.ShowArrayItemKeyReleased(sql, "product_name", "id", search_combo, event, searchlist);
    }

    @FXML
    private void ItemKeyReleased(KeyEvent event) {
        String sql = "SELECT * FROM product_item WHERE item_name LIKE '%" + textitemcomboname.getValue() + "%' LIMIT 100";
        itemlist = prepareQueryFunction.ShowArrayItemKeyReleased(sql, "item_name", "id", textitemcomboname, event, itemlist);
    }

    @FXML
    private void CategoryKeyReleased(KeyEvent event) {
        String sql = "SELECT * FROM product_category WHERE category_name LIKE '%" + textcategorycomboname.getValue() + "%' LIMIT 100";
        categorylist = prepareQueryFunction.ShowArrayItemKeyReleased(sql, "category_name", "id", textcategorycomboname, event, categorylist);
    }

    @FXML
    private void BrandKeyReleased(KeyEvent event) {
        String sql = "SELECT * FROM product_brand WHERE brand_name LIKE '%" + textbrandcomboname.getValue() + "%' LIMIT 100";
        brandlist = prepareQueryFunction.ShowArrayItemKeyReleased(sql, "brand_name", "id", textbrandcomboname, event, brandlist);
    }

    @FXML
    private void MeasurementKeyReleased(KeyEvent event) {
        String sql = "SELECT * FROM product_measurement WHERE measurement_type LIKE '%" + textcombomeasurment.getValue() + "%'";
        measurementlist = prepareQueryFunction.ShowArrayItemKeyReleased(sql, "measurement_type", "id", textcombomeasurment, event, measurementlist);
    }

    @FXML
    private void BarcodeGenarate(ActionEvent event) {
        String sql = "SELECT * FROM product_productinfo";
        report.getReport("/fxsupershop/Product/Report/", "Barcode.jrxml", sql);
    }

    @FXML
    private void SetPath(ActionEvent event) {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(new Stage());

            if (selectedDirectory != null) {
                String P = selectedDirectory.getAbsolutePath();
                String path = P.replace("\\", "\\\\");
                String sql = "UPDATE project_info SET image_path = '" + path + "'";
                prepareQueryFunction.UpdateMessageLess(sql);
            }
        } catch (Exception e) {
        }

    }

    @FXML
    private void SetPathNew(ActionEvent event) {
        try {
            String sql = null;
            FileChooser fc;
            fc = generalService.getSaveChooser("Select Location", "Product Image");
            String Path = generalService.getSaveDialogPath(fc);
            boolean dir = new File(Path).mkdirs();
            if (!dir) {
                generalService.msg.WarningMessage("Unsuccessful", "Warning", "Cannot create");
            } else if (dir) {
                sql = "UPDATE project_info SET image_path = '" + Path + "'";
            }
            prepareQueryFunction.UpdateMessageLess(sql);

        } catch (Exception e) {
        }
    }

    @FXML
    private void PathSelection(ActionEvent event) {
        JFXPopup popup = new JFXPopup();
        setPbtn.setVisible(true);
        setPNbtn.setVisible(true);
        Repbtn.setVisible(false);
        Exbtn.setVisible(false);
        barbtn.setVisible(false);
        format_btn.setVisible(false);
        format_data_btn.setVisible(false);
        import_btn.setVisible(false);
        p.PopUP(report_popPane, Spath, popup, 0, 0);
    }

    @FXML
    private void Suspension(ActionEvent event) {
        try {
            suspensionID = suspensionList.get(suspension.getSelectionModel().getSelectedIndex()).toString();
        } catch (Exception e) {
        }
    }

    @FXML
    private void refresh_sus(MouseEvent event) {
        suspensionView();
    }

    @FXML
    private void susKeyReleased(KeyEvent event) {
        String sql = "SELECT * FROM suspension WHERE sus_name LIKE '%" + suspension.getValue() + "%'";
        suspensionList = prepareQueryFunction.ShowArrayItemKeyReleased(sql, "sus_name", "id", suspension, event, suspensionList);
    }

    @FXML
    private void SettingAction(ActionEvent event) {
        JFXPopup popup = new JFXPopup();
        setPbtn.setVisible(false);
        setPNbtn.setVisible(false);
        Repbtn.setVisible(false);
        Exbtn.setVisible(false);
        barbtn.setVisible(false);
        format_btn.setVisible(true);
        format_data_btn.setVisible(true);
        import_btn.setVisible(true);
        p.PopUP(report_popPane, setting_btn, popup, 0, 0);
    }

    @FXML
    private void formatAction(ActionEvent event) {
        String sql = "SELECT * FROM product_productinfo";
        prepareQueryFunction.report.ExportReport("/fxsupershop/Product/Report/", "product_format.jrxml", sql);
    }

    @FXML
    private void formatDataAction(ActionEvent event) {
        String sql = "SELECT * FROM product_productinfo";
        prepareQueryFunction.report.ExportReport("/fxsupershop/Product/Report/", "product_format_data.jrxml", sql);
    }

    @FXML
    private void ImportAction(ActionEvent event) {
        p.DataImport();
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        //System.runFinalization();
    }
}

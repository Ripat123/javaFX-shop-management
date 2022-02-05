package fxsupershop.Sales;

import animatefx.animation.*;
import com.jfoenix.controls.*;
import fxsupershop.Connection.connection_Sql;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.SalesView;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
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

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class Sales_InfoController implements Initializable {

    @FXML
    private TableView<SalesView> tableView;
//    @FXML
//    private AnchorPane salesancorpane;
    ObservableList<SalesView> data = FXCollections.observableArrayList();
    Connection con;
    PreparedStatement post, post1, post2, post3;
    ResultSet rs, rs1, rs2;
//    @FXML
//    private AnchorPane sale_pane;
    @FXML
    private TextField text_invoice_id;
    @FXML
    private DatePicker text_invoice_date;
    @FXML
    private TextField text_customer_phone;
    @FXML
    private ComboBox combo_customer_name;
    @FXML
    private ComboBox combo_category_name;
    @FXML
    private ComboBox combo_brand_name;
    @FXML
    private ComboBox combo_product_name;
    @FXML
    private TextField text_barcoad;
    @FXML
    private TextField text_quantity;
    @FXML
    private TextField text_purchase_price;
    @FXML
    private TextField text_discount;
    @FXML
    private TextField text_total_price;
    @FXML
    private TextField text_sale_price;
    @FXML
    private ComboBox combo_unit;
    @FXML
    private TextField text_total_amount;
    @FXML
    private TextField text_vat;
    @FXML
    private TextField text_payable;
    @FXML
    private TextField text_paid_amount;
    @FXML
    private TextField text_due_amount;
//    @FXML
//    private Label text_total;
    @FXML
    private TextField text_total_sale_price;
    @FXML
    private TextField text_total_discount;

//    private TableColumn<SalesView, String> TPurchase_Price;
    @FXML
    private TableColumn<SalesView, String> TSale_Price;
    @FXML
    private TableColumn<SalesView, String> TQuantity;
    @FXML
    private TableColumn<SalesView, String> TDiscount;
    @FXML
    private TableColumn<SalesView, String> TTotal_Sale_Price;
    private String unit_id, proid;
    private String customer_id;
    private String totalitem;
    private double total_purchaseprice;
    private double total_saleprice;
    private double totaldiscount;
    @FXML
    private TableColumn<SalesView, String> cl_proid;
    @FXML
    private CheckBox S_invoice_check;
    @FXML
    private Pane add_customer_pane;
    @FXML
    private JFXTextArea text_customeraddress;
    @FXML
    private JFXTextField text_Customerphone;
    @FXML
    private JFXTextField text_customeremail;
    @FXML
    private JFXTextField text_customerName;
    @FXML
    private JFXTextField text_customerID;
    @FXML
    private JFXTextField text_Customerphone1;
    @FXML
    private JFXTextField text_customerName1;
    @FXML
    private JFXTextField text_customerID1;
    private int check;
    private String CategoryID, BrandID, stock_hintV;
    Message msg = new Message();
    private int auto_id;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    Report rep = new Report();

    GeneralService generalService = new GeneralService();
    private JFXButton btn;
    @FXML
    private TableColumn<?, ?> action_col;
    private int index = 0, Uid;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private ScrollPane scroll;
//    @FXML
//    private Button clearcart_id;
    @FXML
    private Label stock_hint;
    Sales_Presenter presenter = new Sales_Presenter();
    LoginMultiFormController lmfc = new LoginMultiFormController();
    SimpleSalesPresenter ssp = new SimpleSalesPresenter();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        initable();
//       loaddata();
//        BrandView();
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    Service service = new Service() {
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
        CategoryView();
        Customerview();
        UnitView();
        ProductView();
        autoClearList();
        view();
        autoId();
        JFXScrollPane.smoothScrolling(scroll);
        S_invoice_check.setSelected(true);
        BrandView();
        Uid = lmfc.User();
    }

    private void autoId() {
        int id = queryFunction.AutoJFXID("sale_ledger");
        String front_tag_ofID = "SI-";
        if (id <= 9) {
            text_invoice_id.setText(front_tag_ofID + "000000" + "" + Integer.toString(id));
            return;
        } else if (id <= 99) {
            text_invoice_id.setText(front_tag_ofID + "00000" + "" + Integer.toString(id));
            return;
        } else if (id <= 999) {
            text_invoice_id.setText(front_tag_ofID + "0000" + "" + Integer.toString(id));
            return;
        } else if (id <= 9999) {
            text_invoice_id.setText(front_tag_ofID + "000" + "" + Integer.toString(id));
            return;
        } else if (id <= 99999) {
            text_invoice_id.setText(front_tag_ofID + "00" + "" + Integer.toString(id));
            return;
        } else if (id <= 999999) {
            text_invoice_id.setText(front_tag_ofID + "0" + "" + Integer.toString(id));
            return;
        } else if (id <= 9999999) {
            text_invoice_id.setText(front_tag_ofID + "" + Integer.toString(id));
            return;
        }

        if (text_invoice_id.getText().equals("")) {
            text_invoice_id.setText(front_tag_ofID + "01");
        }
    }

    private void Customerview() {
        String sql = "SELECT customer_name FROM customer_info";
        queryFunction.ViewItemOnComboBox(sql, "customer_name", combo_customer_name);
    }

    private void CategoryView() {
        String sql = "SELECT category_name FROM product_category";
        queryFunction.ViewItemOnComboBox(sql, "category_name", combo_category_name);
    }

    private void BrandView() {
        combo_brand_name.getItems().clear();
        String sql = "SELECT brand_name FROM product_brand";
        queryFunction.ViewItemOnComboBox(sql, "brand_name", combo_brand_name);
    }

    private void ProductView() {
        String sql = "SELECT product_productinfo.product_name,stock_product.product_id FROM "
                + "stock_product INNER JOIN product_productinfo ON stock_product.product_id = product_productinfo.id";
        queryFunction.ViewItemOnComboBox(sql, "product_name", combo_product_name);
    }

    private void UnitView() {
        String sql = "SELECT sub_unit_name FROM product_measurement_subunit";
        queryFunction.ViewItemOnComboBox(sql, "sub_unit_name", combo_unit);
    }

    private void totalitem() {
        String sql = "SELECT COUNT(id) AS 'TotalItem' FROM sale_currentsale";
        totalitem = queryFunction.getSUMValue(sql, "TotalItem");
        auto_id = 0;
        auto_id = Integer.parseInt(totalitem);
        auto_id = auto_id + 1;
    }

    private void totalpurchaseprice() {
        String sql = "SELECT SUM(product_purchaseprice) AS 'TotalPurchasePrice' FROM sale_currentsale";
        total_purchaseprice = Double.parseDouble(queryFunction.getSUMValue(sql, "TotalPurchasePrice"));
    }

    private void totalSaleprice() {
        String sql = "SELECT SUM(product_saleprice) AS 'TotalSalePrice' FROM sale_currentsale";
        total_saleprice = Double.parseDouble(queryFunction.getSUMValue(sql, "TotalSalePrice"));
    }

    private void totaldiscount() {
        String sql = "SELECT SUM(product_discount) AS 'TotalDiscount' FROM sale_currentsale";
        totaldiscount = Double.parseDouble(queryFunction.getSUMValue(sql, "TotalDiscount"));
    }

    private void totalPrice() {
        String sql = "SELECT SUM(`product_totalsaleprice`) AS 'total' FROM `sale_currentsale`";
        String s = queryFunction.getSUMValue(sql, "total");
        text_total_amount.setText(s);
        double per = ssp.vat();
        double av = Double.parseDouble(s) / 100;
        double vat = per * av;
        String amt = String.valueOf(vat + Double.parseDouble(s));
        text_total_sale_price.setText(amt);
        text_vat.setText(String.valueOf(vat));
        text_total_discount.setText("0");
        text_payable.setText(amt);
        text_paid_amount.setText("0");
        text_due_amount.setText(amt);
    }

    private void autoClearList() {
        String sql = "DELETE FROM sale_currentsale";
        queryFunction.DeleteConditionLess(sql);
    }

    private void Current_sale_Add() {

        try {

            if (text_invoice_id.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter invoice No.", ButtonType.OK).showAndWait();
                return;
            }
            if (text_purchase_price.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty Pruchase price Field.", ButtonType.OK).showAndWait();
                return;
            }
            if (text_sale_price.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty Sale price Field.", ButtonType.OK).showAndWait();
                return;
            }

            if (text_quantity.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty Quantity Field.", ButtonType.OK).showAndWait();
                return;
            }
            if (combo_unit.getEditor().getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Select Unit.", ButtonType.OK).showAndWait();
                return;
            }

            if (text_total_price.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty Total price Field.", ButtonType.OK).showAndWait();
                return;
            }
            // for auto id.
            totalitem();
            double due_quantity;
            String sql1 = "SELECT quantity FROM stock_product WHERE product_id = '" + proid + "'";
            rs1 = queryFunction.getResult(sql1);
            if (rs1.next()) {
                double quan = Double.parseDouble(rs1.getString("quantity"));
                double text_quan = Double.parseDouble(text_quantity.getText());
                due_quantity = text_quan - quan;
                if (text_quan <= quan) {
                    String sql = "INSERT INTO `sale_currentsale`(`id`,`sub_unit_id`,`product_id`,`product_purchaseprice`,`product_saleprice`,"
                            + "`product_quantity`,`product_discount`,`product_totalsaleprice`,`session_id`)VALUES('" + auto_id + "','" + unit_id + "','" + proid + "',"
                            + "'" + text_purchase_price.getText() + "','" + text_sale_price.getText() + "','" + text_quantity.getText() + "',"
                            + "'" + text_discount.getText() + "','" + text_total_price.getText() + "','" + text_invoice_id.getText() + "')";
                    queryFunction.Insert(sql);
                    view();
                    totalPrice();
                    clear1();
                    stock_hint.setText(null);
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("You have not enough Product!");
                    alert.setContentText("Are you sure you want to Sales with Purchase?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.YES) {
                        double price = Double.parseDouble(text_purchase_price.getText());
                        double total_purchase = due_quantity * price;
                        presenter.SalesWithPurchaseData(String.valueOf(due_quantity), proid, unit_id,
                                text_purchase_price.getText(), String.valueOf(total_purchase), text_sale_price.getText());
                        presenter.salesWithPurchaseEntry();
                        presenter.salesWithPurchaseLadger();
                        presenter.salesWithPurchaseStock();

                        String sql = "INSERT INTO `sale_currentsale`(`id`,`sub_unit_id`,`product_id`,`product_purchaseprice`,`product_saleprice`,"
                                + "`product_quantity`,`product_discount`,`product_totalsaleprice`,`session_id`)VALUES('" + auto_id + "','" + unit_id + "','" + proid + "',"
                                + "'" + text_purchase_price.getText() + "','" + text_sale_price.getText() + "','" + text_quantity.getText() + "',"
                                + "'" + text_discount.getText() + "','" + text_total_price.getText() + "','" + text_invoice_id.getText() + "')";
                        queryFunction.Insert(sql);
                        view();
                        totalPrice();
                        clear1();
                        stock_hint.setText(null);
                    }
                }
            }
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Insert Unsuccessful\n" + e);
        }
    }

    private void Sales_Ledger() {
        LocalDate date = text_invoice_date.getValue();
        String sql = "INSERT INTO `sale_ledger`(`invoice_id`,`invoice_date`,`customer_id`,`total_item`,"
                + "`total_purchaseprice`,`totalsaleprice`,`totaldiscount`,`total_amount`,`paid`,`due`,created_at,admin_id) VALUES('" + text_invoice_id.getText() + "','" + date + "',"
                + "'" + customer_id + "','" + totalitem + "','" + total_purchaseprice + "','" + total_saleprice + "',"
                + "'" + text_total_discount.getText().trim() + "','" + text_total_amount.getText() + "',"
                + "'" + text_paid_amount.getText() + "','" + text_due_amount.getText() + "','" + queryFunction.service.getDateTime() + "')";

        queryFunction.InsertCustomise(sql, "Have a Problem in Ledger");
    }

    private void peymentStatement() {
        String sql = "INSERT INTO sale_payment_statement (invoice_no,"
                + "entry_date,total_ammount,vat,discount,payment,due,comment)VALUES ("
                + "'" + text_invoice_id.getText().trim() + "','" + text_invoice_date.getValue() + "','" + text_total_amount.getText().trim() + ""
                + "','" + text_vat.getText().trim() + "','" + text_total_discount.getText().trim() + "',"
                + "'" + text_paid_amount.getText().trim() + "','" + text_due_amount.getText().trim() + "','First Payment')";
        queryFunction.InsertCustomise(sql, "Have a problem in Peyment Statement.");
    }

    private void view() {
        data.clear();
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        cl_proid.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        TSale_Price.setCellValueFactory(new PropertyValueFactory<>("product_saleprice"));
        TQuantity.setCellValueFactory(new PropertyValueFactory<>("product_quantity"));
        TDiscount.setCellValueFactory(new PropertyValueFactory<>("product_discount"));
        TTotal_Sale_Price.setCellValueFactory(new PropertyValueFactory<>("product_totalsaleprice"));
        action_col.setCellValueFactory(new PropertyValueFactory<>("button"));

        try {
            String sql = "SELECT * FROM `sale_currentsale`,product_productinfo WHERE "
                    + "product_productinfo.id = sale_currentsale.product_id";
            rs = queryFunction.getResult(sql);

            while (rs.next()) {

                btn = new JFXButton("Delete");
                btn.setButtonType(JFXButton.ButtonType.RAISED);
                btn.setStyle("-fx-background-color: #039aff; -fx-background-radius: 10; "
                        + "-fx-text-fill: white; -fx-font-weight: bold;");
                btn.setId(String.valueOf(index));
                data.add(new SalesView(rs.getString("id"),
                        rs.getString("product_name"), rs.getString("product_saleprice"),
                        rs.getString("product_quantity"), rs.getString("product_discount"),
                        rs.getString("product_totalsaleprice"), btn
                ));
                btn.setOnAction((event) -> {
                    try {
                        String source = event.getSource().toString();
                        int row = Integer.parseInt(source.substring(13, source.indexOf(",")));
                        SalesView sv = tableView.getItems().get(row);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                        alert.setTitle("Delete Selected Data?");
                        alert.setContentText("Are you sure you want to delete?\nID : " + sv.getId());
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.YES) {
                            String sql1 = "delete from  sale_currentsale WHERE id= '" + sv.getId() + "'";
                            queryFunction.DeleteConditionLess(sql1);
                            String sql2 = "SELECT * FROM sale_currentsale";
                            rs = queryFunction.getResult(sql2);
                            int i = 1;
                            while (rs.next()) {
                                String sql3 = "UPDATE sale_currentsale SET id = '" + i + "' WHERE id = '" + rs.getString("id") + "'";
                                queryFunction.UpdateMessageLess(sql3);
                                i++;
                            }
                            view();
                        }
                    } catch (Exception e) {

                    } finally {
                        try {
                            post.close();
                            rs.close();
                        } catch (Exception e) {

                        }
                    }
                });
                index++;
            }
            tableView.setItems(data);
            index = 0;

        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "View Unsuccessful" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void SalesEntry() {
        try {
            String sql = "SELECT * FROM sale_currentsale WHERE session_id = '" + text_invoice_id.getText() + "'";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                String sql1 = "INSERT INTO sale_entry (invoice_id,product_id,sub_unit_id,"
                        + "product_quantity,product_purchaseprice,product_saleprice,"
                        + "product_discount,product_totalsaleprice) VALUES "
                        + "('" + rs.getString("session_id") + "',"
                        + "'" + rs.getString("product_id") + "',"
                        + "'" + rs.getString("sub_unit_id") + "',"
                        + "'" + rs.getString("product_quantity") + "',"
                        + "'" + rs.getString("product_purchaseprice") + "',"
                        + "'" + rs.getString("product_saleprice") + "',"
                        + "'" + rs.getString("product_discount") + "',"
                        + "'" + rs.getString("product_totalsaleprice") + "')";
                queryFunction.InsertCustomise(sql1, "Have a Problem in Entry.");
            }
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem in Seles Entry!" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void substraction_product() {
        try {
            String sql = "SELECT * FROM `sale_currentsale` WHERE session_id = '" + text_invoice_id.getText() + "'";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                String sql1 = "SELECT stock_product.quantity FROM stock_product WHERE "
                        + "stock_product.product_id = '" + rs.getString("product_id") + "'";
                rs1 = queryFunction.getResult(sql1);
                if (rs1.next()) {
                    double quan = Double.parseDouble(rs1.getString("quantity"));
                    double text_quan = Double.parseDouble(rs.getString("product_quantity"));
                    if (text_quan <= quan) {
                        String sql3 = "UPDATE stock_product SET quantity = quantity - '" + rs.getString(""
                                + "product_quantity") + "' WHERE product_id = '" + rs.getString("product_id") + "'";
                        queryFunction.UpdateMessageLess(sql3);
                    }
                }
            }

        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem in Substraction Product!" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
                rs1.close();
            } catch (Exception e) {

            }
        }
    }

//    private void initable(){
//        initcols();
//    }
//    private void initcols(){
//        
//        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
//        c2.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        
//        editablecols();
//    }
//    public void editablecols(){
//        c1.setCellFactory(TextFieldTableCell.forTableColumn());
//        c1.setOnEditCommit(e->{
//        e.getTableView().getItems().get(e.getTablePosition().getRow()).setId(e.getNewValue());
//         SalesView d = tableView.getItems().get(row);
//         ds1 = Double.parseDouble(d.getId());
//      
//          dd = tableView.getItems().get(row);
//         ds = Double.parseDouble(dd.getName());
//         value0 = ds1 * ds; 
//        dd.setName(String.valueOf(value0));
//        
////        c2.setCellValueFactory(new PropertyValueFactory<>(String.valueOf(value0)));
//        });
//        
//        c2.setCellFactory(TextFieldTableCell.forTableColumn());
//        c2.setOnEditCommit(e->{
//        e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
//         
////        double value = Double.parseDouble(String.valueOf(value1));
////        SalesView d = tableView.getItems().get(row+1);
////        double ds = Double.parseDouble(d.getName());
////        double value0 = ds1 * ds; 
////        d.setName(String.valueOf(value0));
//        });
//        
//        
//        tableView.setEditable(true);
//    }
//    private void loaddata(){
//        try{
//       String sql = "SELECT * FROM testtable";
//       post = con.prepareStatement(sql);
//       rs = post.executeQuery();
//       while(rs.next()){
//           data_table.add(new SalesView(rs.getString("Name"), rs.getString("Department")));
//       }
//       tableView.setItems(data_table);
//        }catch(Exception e){
//            
//        }
//    }
    private void Release_product(KeyEvent event) {
        String sql = "SELECT product_productinfo.product_name,stock_product.product_id FROM "
                + "stock_product INNER JOIN product_productinfo ON stock_product.product_id = product_productinfo.id WHERE product_productinfo.product_name LIKE '%" + combo_product_name.getEditor().getText() + "%'";
        queryFunction.ShowItemOnkeyReleased(sql, "product_name", combo_product_name, event);
    }

    private void Release_Category(KeyEvent event) {
        String sql = "SELECT * FROM `product_category` WHERE `category_name` LIKE '%" + combo_category_name.getEditor().getText() + "%'";
        queryFunction.ShowItemOnkeyReleased(sql, "category_name", combo_category_name, event);

    }

    private void Release_Brand(KeyEvent event) {
        String sql = "SELECT * FROM `product_brand` WHERE `brand_name` LIKE '%" + combo_brand_name.getEditor().getText() + "%'";
        queryFunction.ShowItemOnkeyReleased(sql, "brand_name", combo_brand_name, event);
    }

    private void Release_Customer(KeyEvent event) {
        String sql = "SELECT * FROM `customer_info` WHERE `customer_name` LIKE '%" + combo_customer_name.getEditor().getText() + "%'";
        queryFunction.ShowItemOnkeyReleased(sql, "customer_name", combo_customer_name, event);
    }

    private void insert() {
        if (text_customerID.getText().equals("")) {
            Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Customer ID", ButtonType.OK).showAndWait();
            return;
        }

        if (text_customerName.getText().equals("")) {
            Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Customer Name", ButtonType.OK).showAndWait();
            return;
        }
        if (text_Customerphone.getText().equals("")) {
            Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Customer Phone", ButtonType.OK).showAndWait();
            return;
        } else {
            String sql = "INSERT INTO `customer_info`(`id`,`customer_name`,`customer_email`,`customer_phone`,`customer_address`)VALUES('" + text_customerID.getText() + "','" + text_customerName.getText() + "','" + text_customeremail.getText() + "','" + text_Customerphone.getText() + "','" + text_customeraddress.getText() + "')";
            queryFunction.Insert(sql);
            clean();
            view();
        }

    }

    private void insert_general() {
        if (text_customerID1.getText().equals("")) {
            Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Customer ID", ButtonType.OK).showAndWait();
            return;
        }

        if (text_customerName1.getText().equals("")) {
            Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Customer Name", ButtonType.OK).showAndWait();
            return;
        }
        if (text_Customerphone1.getText().equals("")) {
            Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter Customer Phone", ButtonType.OK).showAndWait();
            return;
        } else {
            String sql = "INSERT INTO `customer_info`(`id`,`customer_name`,`customer_phone`)VALUES"
                    + "('" + text_customerID1.getText() + "','" + text_customerName1.getText() + "','" + text_Customerphone1.getText() + "')";
            queryFunction.Insert(sql);
            clean();
            view();
        }

    }

    private void clean_g() {
        text_customerID1.setText(null);
        text_customerName1.setText(null);
        text_Customerphone1.setText(null);
        ;

    }

    private void clean() {
        text_customerID.setText(null);
        text_customerName.setText(null);
        text_customeremail.setText(null);
        text_Customerphone.setText(null);
        text_customeraddress.setText(null);
        text_customerID.requestFocus();

    }

    private void clear1() {
        combo_category_name.setValue(null);
        combo_brand_name.setValue(null);
        combo_product_name.setValue(null);
        text_barcoad.setText(null);
        text_purchase_price.setText(null);
        text_sale_price.setText(null);
        text_quantity.setText(null);
        combo_unit.setValue(null);
        text_discount.setText(null);
        text_total_price.setText(null);
        combo_category_name.requestFocus();
    }

    private void clear2() {
        text_invoice_id.setText(null);
        text_invoice_date.setValue(null);
        combo_customer_name.setValue(null);
        text_customer_phone.setText(null);
        combo_category_name.setValue(null);
        combo_brand_name.setValue(null);
        combo_product_name.setValue(null);
        text_barcoad.setText(null);
        text_purchase_price.setText(null);
        text_sale_price.setText(null);
        text_quantity.setText(null);
        combo_unit.setValue(null);
        text_discount.setText(null);
        text_total_price.setText(null);
        text_total_amount.setText(null);
        text_vat.setText(null);
        text_total_sale_price.setText(null);
        text_total_discount.setText(null);
        text_payable.setText(null);
        text_paid_amount.setText(null);
        text_due_amount.setText(null);
        S_invoice_check.setSelected(false);
        text_invoice_id.requestFocus();
    }

    public void PosReport(String invoice) {
        String path = "/fxsupershop/Sales/";
        String sql = "SELECT sale_entry.*,vat_entry.total_vat,"
                + "customer_info.*,project_info.*,"
                + "sale_ledger.*,product_productinfo.product_name "
                + "FROM vat_entry,sale_entry,sale_ledger,product_productinfo,customer_info,project_info WHERE sale_ledger.customer_id"
                + " = customer_info.id AND "
                + "sale_entry.product_id = product_productinfo.id "
                + "AND sale_entry.invoice_id = sale_ledger.invoice_id AND "
                + "sale_ledger.invoice_id = '" + invoice + "' AND vat_entry.invoice_no = '" + invoice + "'";
        queryFunction.getImagePath(sql, "image");
        queryFunction.report.getReport(path, "SalesPOSReport.jrxml", sql);
    }

    public void report(String invoice) {
        String path = "/fxsupershop/SalesReport/";
        String sql = "SELECT sale_entry.*,vat_entry.total_vat,"
                + "customer_info.*,project_info.*,"
                + "sale_ledger.*,product_productinfo.product_name "
                + "FROM vat_entry,sale_entry,sale_ledger,product_productinfo,customer_info,project_info WHERE sale_ledger.customer_id"
                + " = customer_info.id AND "
                + "sale_entry.product_id = product_productinfo.id "
                + "AND sale_entry.invoice_id = sale_ledger.invoice_id AND "
                + "sale_ledger.invoice_id = '" + invoice + "' AND vat_entry.invoice_no = '" + invoice + "'";
        queryFunction.getImagePath(sql, "image");
        queryFunction.report.getReport(path, "SalesReport.jrxml", sql);
    }

    private void productAction(String query) {
        try {
            String sql = query;
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                text_purchase_price.setText(rs.getString("stock_product.purchase_price"));
                text_sale_price.setText(rs.getString("stock_product.sale_price"));
                text_total_price.setText(rs.getString("stock_product.sale_price"));
                text_quantity.setText("1");
                text_discount.setText("0");
                proid = null;
                proid = rs.getString("stock_product.product_id");
                combo_product_name.setValue(rs.getString("product_name"));
                combo_unit.setValue(rs.getString("sub_unit_name"));
                stock_hintV = rs.getString("stock_product.quantity");
                stock_hint.setText("Stocked Quantity : " + stock_hintV);
            }
        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Error!\n" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    @FXML
    private void clickedTableData(MouseEvent event) {
//        try {
//            if (event.getClickCount() == 1) {
//                @SuppressWarnings("rawtypes")
//                TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
//                row = pos.getRow();
//                int col = pos.getColumn();
//                @SuppressWarnings("rawtypes")
//                TableColumn column = pos.getTableColumn();
//                val = column.getCellData(row).toString();
//            }
//        } catch (Exception e) {
//
//        }

    }

    @FXML
    private void keyTableData(KeyEvent event) {
//        if(event.getCode() == KeyCode.ENTER){
//            dd = tableView.getItems().get(row);
//         ds = Double.parseDouble(dd.getName());
//         value0 = ds1 * ds; 
//        dd.setName(String.valueOf(value0));
//        }
    }

    @FXML
    private void add_customer_btn(ActionEvent event) {
        add_customer_pane.setVisible(true);
        check = 0;
        new ZoomIn(add_customer_pane).setSpeed(2).play();

    }

    @FXML
    private void add_single_btn(ActionEvent event) {
        Current_sale_Add();
    }

    @FXML
    private void add_multiple_btn(ActionEvent event) {

    }

    @FXML
    private void view_btn(ActionEvent event) {
        view();
        tableView.requestFocus();
    }

    @FXML
    private void submit_btn(ActionEvent event) {

        try {
            if (text_invoice_id.getText().equals("")) {
                msg.ConditionalMessage("Enter invoice No.");
                return;
            }
            if (text_invoice_date.getValue() == null) {
                msg.ConditionalMessage("Empty Invoice date.");
                return;
            }
            if (combo_customer_name.getValue().equals("") && text_customer_phone.getText().equals("")) {
                msg.ConditionalMessage("Empty Customer name and Phone.");
                return;
            }
            if (text_total_amount.getText().equals("") && text_paid_amount.getText().equals("")) {
                msg.ConditionalMessage("Empty Paid amount and total.");
                return;
            }

            totalitem();
            totalpurchaseprice();
            totalSaleprice();
            totaldiscount();
            Sales_Ledger();
            SalesEntry();
            substraction_product();
            peymentStatement();
            presenter.vatEntry(text_invoice_date.getValue().toString(), text_invoice_id.getText(),
                    "0", text_vat.getText(), String.valueOf(Uid));
            if (S_invoice_check.isSelected()) {
                report(text_invoice_id.getText());
            }
            autoClearList();
            view();
            clear2();
            autoId();
        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Submit Unsuccessful!\n" + e);
        }

    }

    @FXML
    private void CustomerAction(ActionEvent event) {
        try {
            String sql = "SELECT customer_phone,id FROM customer_info WHERE customer_name='" + combo_customer_name.getEditor().getText() + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                text_customer_phone.setText(rs.getString("customer_phone"));
                customer_id = rs.getString("id");
            }
        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Error!\n" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }

    }

    @FXML
    private void CustomerKeyRAction(KeyEvent event) {
        Release_Customer(event);

    }

    @FXML
    private void CategoryKeyRAction(KeyEvent event) {

        Release_Category(event);

    }

    @FXML
    private void BrandKeyRAction(KeyEvent event) {

        Release_Brand(event);

    }

    @FXML
    private void ProductAction(ActionEvent event) {
        String sql = "SELECT * FROM stock_product INNER JOIN product_productinfo"
                + " ON stock_product.product_id = product_productinfo.id "
                + "INNER JOIN product_measurement_subunit ON product_productinfo."
                + "measurement_type =product_measurement_subunit.id WHERE product_productinfo.product_name="
                + "'" + combo_product_name.getEditor().getText() + "'";
        productAction(sql);
    }

    @FXML
    private void ProductKeyRAction(KeyEvent event) {

        Release_product(event);

    }
    double amount;
    double total;

    @FXML
    private void QuantityKeyRAction(KeyEvent event) {
        try {

            double qun = Double.parseDouble(text_quantity.getText());

            double unitprice = Double.parseDouble(text_sale_price.getText());
            amount = 0;
            amount = qun * unitprice;
            text_total_price.setText(String.valueOf(amount));

        } catch (Exception e) {

        }
    }

    @FXML
    private void discountkeyAction(KeyEvent event) {
        try {

            double disc = Double.parseDouble(text_discount.getText());
            double price = Double.parseDouble(text_sale_price.getText());
            double quan = Double.parseDouble(text_quantity.getText());
            double unit_price = price - disc;
            double net = unit_price * quan;

            text_total_price.setText(String.valueOf(net));

        } catch (Exception e) {

        }
    }

    @FXML
    private void UnitAction(ActionEvent event) {
        String sql = "SELECT id FROM product_measurement_subunit WHERE sub_unit_name='" + combo_unit.getEditor().getText() + "'";
        unit_id = queryFunction.getDataInSVeriable(sql, "id");
    }

    @FXML
    private void UnitKeyRAction(KeyEvent event) {
    }

    @FXML
    private void VatKeyRAction(KeyEvent event) {
    }

    @FXML
    private void PaidKeyRAction(KeyEvent event) {
        try {

            double totalamount = Double.parseDouble(text_payable.getText());

            double paidamount = Double.parseDouble(text_paid_amount.getText());
            double total = 0;
            total = totalamount - paidamount;

            text_due_amount.setText(String.valueOf(total));

        } catch (Exception e) {

        }
    }

    @FXML
    private void Clear_Cart(ActionEvent event) {
        autoClearList();
        view();
    }

    @FXML
    private void DiscountKeyRAction(KeyEvent event) {
        try {

            double disamount = Double.parseDouble(text_total_discount.getText());
            double total = Double.parseDouble(text_total_sale_price.getText());
            Double finaldiscount = total - disamount;

            text_payable.setText(String.valueOf(finaldiscount));

        } catch (Exception e) {

        }
    }

    @FXML
    private void CleanField(ActionEvent event) {
        clear2();
        autoId();
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
    }

    @FXML
    private void CustomerPaneClose(ActionEvent event) {
        new ZoomOut(add_customer_pane).setSpeed(2).play();
        check = 0;
        check = 5;
    }

    @FXML
    private void EnteredSupplierClose(MouseEvent event) {
        if (check == 5) {
            add_customer_pane.setVisible(false);
            check = 0;
        }
    }

    @FXML
    private void CleanCus(ActionEvent event) {
        clean();
    }

    @FXML
    private void generalCus_add(ActionEvent event) {
        insert_general();
    }

    @FXML
    private void CleanCus_general(ActionEvent event) {
        clean_g();
    }

    @FXML
    private void CategoryAction(ActionEvent event) {
        try {
            String sql1 = "SELECT id FROM product_category WHERE category_name = '" + combo_category_name.getValue() + "'";
            CategoryID = queryFunction.getDataInSVeriable(sql1, "id");

        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Error!\n" + e);
        }
    }

    @FXML
    private void BrandAction(ActionEvent event) {
        try {
            String sql1 = "SELECT id FROM product_brand WHERE brand_name = '" + combo_brand_name.getValue() + "'";
            BrandID = queryFunction.getDataInSVeriable(sql1, "id");

            combo_product_name.getItems().clear();

            String sql = "SELECT product_productinfo.product_name,product_productinfo.brand_id,"
                    + "product_productinfo.category_id,stock_product.product_id FROM "
                    + "stock_product INNER JOIN product_productinfo ON stock_product.product_id = "
                    + "product_productinfo.id WHERE product_productinfo.brand_id = '" + BrandID + "'"
                    + " AND product_productinfo.category_id = '" + CategoryID + "'";
            queryFunction.ViewItemOnComboBox(sql, "product_name", combo_product_name);

        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Error!\n" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs2.close();
            } catch (Exception e) {

            }
        }
    }

    @FXML
    private void barcodeAction(KeyEvent event) {
        String sql = "SELECT * FROM stock_product INNER JOIN product_productinfo"
                + " ON stock_product.product_id = product_productinfo.id "
                + "INNER JOIN product_measurement_subunit ON product_productinfo."
                + "measurement_type =product_measurement_subunit.id WHERE product_productinfo.id="
                + "'" + text_barcoad.getText() + "'";
        productAction(sql);
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}

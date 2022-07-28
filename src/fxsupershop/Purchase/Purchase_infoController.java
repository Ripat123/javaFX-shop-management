package fxsupershop.Purchase;

import animatefx.animation.*;
import com.jfoenix.controls.*;
import fxsupershop.Connection.connection_Sql;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.PurchaseView;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class Purchase_infoController implements Initializable {

    @FXML
    private JFXTextField invoiceNo;
    @FXML
    private JFXTextField voucherNo;
    @FXML
    private JFXTextField SelectPhone;
    @FXML
    private JFXDatePicker voucherDate;
    @FXML
    private JFXComboBox SelectSupplier;
    @FXML
    private JFXComboBox selectProduct;
    @FXML
    private JFXTextField quantity;
    @FXML
    private JFXTextField price;
    @FXML
    private JFXTextField Discount;
    @FXML
    private JFXTextField totalPrice;
    @FXML
    private JFXTextField SalePrice;
    @FXML
    private JFXDatePicker ExpiredDate;
    @FXML
    private JFXTextField totalAmount;
    @FXML
    private JFXTextField paidAmount;
    @FXML
    private JFXTextField discount;
    @FXML
    private JFXTextField Due;
    @FXML
    private StackPane stackpane;
    @FXML
    private JFXDialogLayout dialoglayout;
    @FXML
    private JFXDialog dialog;
    @FXML
    private JFXButton closebtn;
    Connection con;
    PreparedStatement post, post1;
    ResultSet rs, rs1, rss, set;
    String Product_hiddenID;
    ObservableList<PurchaseView> data = FXCollections.observableArrayList();
//    @FXML
//    private JFXButton addbtn;
//
//    @FXML
//    private JFXButton viewbtn;
//    @FXML
//    private JFXButton submitbtn;
//    @FXML
//    private JFXButton clearbtn;
//    @FXML
//    private JFXButton cartbtn;
    @FXML
    public TableView<PurchaseView> tableView;
    @FXML
    private TableColumn<PurchaseView, String> proname_col;
    @FXML
    private TableColumn<PurchaseView, Double> guantity_col;
    @FXML
    private TableColumn<PurchaseView, Double> price_col;
    @FXML
    private TableColumn<PurchaseView, Double> discount_col;
    @FXML
    private TableColumn<PurchaseView, Double> totalPrice_col;
    @FXML
    private TableColumn<PurchaseView, Double> sale_col;
    @FXML
    private TableColumn<?, ?> action_col;
    @FXML
    private TableColumn<?, ?> id;
    private JFXButton btn;
    @FXML
    private Pane multiple_pane;
//    @FXML
//    private JFXButton addMulti_btn;
    @FXML
    private TableView<PurchaseView> dropdown_tableview;
    @FXML
    private TableColumn<?, ?> checkid;
    @FXML
    private TableColumn<PurchaseView, String> productnameid;
    JFXCheckBox check;
    ObservableList<PurchaseView> data1 = FXCollections.observableArrayList();
    @FXML
    private TableColumn<PurchaseView, String> quantityid;
    @FXML
    private TableColumn<PurchaseView, String> purchasepriceid;
    @FXML
    private TableColumn<PurchaseView, String> discountid;
    @FXML
    private JFXTextField multisearchfield;
    private int row1 = -1;
    private int col1 = -1;
    private double totalPrice_V;
    private double saleprice;
    private String proid = null;
    private String supp_id, unitID;
    private String totalitem;
    private double TemporaryTotal_p, quantity1, purchaseprice1, Tamount1,
            quantityv, totalquantity, purchaseprice, Tamount, totalpurchaseprice, average;
    @FXML
    private Pane add_supplier_pane;
    @FXML
    private JFXTextArea text_present_Address;
    @FXML
    private JFXTextField text_email;
    @FXML
    private JFXTextField text_comPhone;
    @FXML
    private JFXTextField text_supPhone;
    @FXML
    private JFXTextField text_name;
    private int Check, index = 0;
    Message msg = new Message();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    Report report = new Report();
    GeneralService service = new GeneralService();

    @FXML
    private JFXTextField netTotal;
    private int ID;
//    @FXML
//    private JFXScrollPane scroll;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private JFXComboBox unit;
//    @FXML
//    private AnchorPane p1;
    private int auto_id;
    @FXML
    private JFXCheckBox check_invoice;
    @FXML
    private TableView tableView1;
    @FXML
    private TableColumn<?, ?> proname_col1;
    @FXML
    private TableColumn<?, ?> guantity_col1;
    @FXML
    private TableColumn<?, ?> price_col1;
    @FXML
    private TableColumn<?, ?> totalPrice_col1;
//    @FXML
//    private TableColumn<?, ?> sale_col1;
//    @FXML
//    private TableColumn<?, ?> action_col1;
    double amount, Sdata;
    double total, sum_price, sum_salePrice;
    @FXML
    private JFXTextField barcode;
    String measurement_type = null;
    ObservableList productlist = FXCollections.observableArrayList();
    ObservableList suplierlist = FXCollections.observableArrayList();
    ObservableList unitlist = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
        service.start();
        service.setOnSucceeded((event) -> {service.cancel();
        });
    }

    public void initSource() {
        con = connection_Sql.ConnectDb();
        autoId();
        ID = LoginMultiFormController.User();
        supplier();
        product();
        multipleselectionView();
        initable();
        JFXScrollPane.smoothScrolling(scrollpane);
        ShoppingCartclear();
        Simpleview();
        view();
    }

    private void autoId() {
        int id = queryFunction.AutoJFXID("purchase_ledger");
        String prefix = "PI-";
        if (id <= 9) {
            invoiceNo.setText(prefix + "000000" + "" + Integer.toString(id));
            return;
        } else if (id <= 99) {
            invoiceNo.setText(prefix + "00000" + "" + Integer.toString(id));
            return;
        } else if (id <= 999) {
            invoiceNo.setText(prefix + "0000" + "" + Integer.toString(id));
            return;
        } else if (id <= 9999) {
            invoiceNo.setText(prefix + "000" + "" + Integer.toString(id));
            return;
        } else if (id <= 99999) {
            invoiceNo.setText(prefix + "00" + "" + Integer.toString(id));
            return;
        } else if (id <= 999999) {
            invoiceNo.setText(prefix + "0" + "" + Integer.toString(id));
            return;
        } else if (id <= 9999999) {
            invoiceNo.setText(prefix + "" + Integer.toString(id));
            return;
        }

        if (invoiceNo.getText().equals("")) {
            invoiceNo.setText(prefix + "0000001");
        }
    }

    private void supplier() {
        String sql = "SELECT * FROM `suplier_info`";
        suplierlist = queryFunction.ViewArrayJFXComboBox(sql, "company_name", "id", SelectSupplier, suplierlist);
    }

    private void product() {
        String sql = "SELECT * FROM `product_productinfo` LIMIT 100";
        productlist = queryFunction.ViewArrayJFXComboBox(sql, "product_name", "id", selectProduct, productlist);
    }

    private void press_supplier(KeyEvent event) {
        String sql = "SELECT * FROM `suplier_info` WHERE `company_name` LIKE '%" + SelectSupplier.getEditor().getText() + "%'";
        suplierlist = queryFunction.ShowArrayItemKeyReleased(sql, "company_name", "id", SelectSupplier, event, suplierlist);
    }

    private void press_product(KeyEvent event) {
        String sql = "SELECT * FROM `product_productinfo` WHERE `product_name` LIKE '%" + selectProduct.getEditor().getText() + "%' LIMIT 100";
        productlist = queryFunction.ShowArrayItemKeyReleased(sql, "product_name", "id", selectProduct, event, productlist);
    }

//    private void unitview(String measurID) {
//        String sql = "SELECT product_measurement_subunit.* FROM "
//                + "product_measurement_subunit WHERE product_measurement_subunit."
//                + "measurement_unit_id='" + measurID + "'";
//        unitlist = queryFunction.ViewArrayJFXComboBox(sql, "sub_unit_name", "id", unit, unitlist);
//    }
    private void totalPrice() {
        String sql = "SELECT SUM(`product_totalpurchaseprice`) AS 'total' FROM `purchase_currentpurchase`";
        String total_purchase;
        total_purchase = queryFunction.getSUMValue(sql, "total");
        totalAmount.setText(total_purchase);
        discount.setText("0");
        netTotal.setText(total_purchase);
        paidAmount.setText("0");
        Due.setText(total_purchase);
    }

    private void totalitem() {
        try {
            String sql = "SELECT COUNT(id) AS 'TotalItem' FROM purchase_currentpurchase";
            post = con.prepareStatement(sql);
            ResultSet rs = post.executeQuery();

            if (rs.next()) {

                totalitem = rs.getString("TotalItem");

            }

        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Error.\n" + e);
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void ShoppingCartclear() {
        String sql = "delete from  purchase_currentpurchase";
        queryFunction.DeleteConditionLess(sql);
    }

    private void IDgenerate() {
        try {
            String sql = "SELECT COUNT(id) AS 'TotalItem' FROM purchase_currentpurchase";
            post = con.prepareStatement(sql);
            ResultSet rs = post.executeQuery();

            if (rs.next()) {

                String totalitem = rs.getString("TotalItem");
                auto_id = 0;
                auto_id = Integer.parseInt(rs.getString("TotalItem"));
                auto_id = auto_id + 1;
            }

        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Error!\n" + e);
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void currentPurchase() {
        try {
            if (selectProduct.getValue() == null) {
                queryFunction.service.msg.ConditionalMessage("Empty Product");
                return;
            }
            if (ExpiredDate.getValue() == null) {
                queryFunction.service.msg.ConditionalMessage("Empty Expired Date");
                return;
            }
            if (quantity.getText().equals("") || quantity.getText().equals("0")) {
                queryFunction.service.msg.ConditionalMessage("Empty Quantity");
                return;
            }
            if (price.getText().equals("") || price.getText().equals("0")) {
                queryFunction.service.msg.ConditionalMessage("Empty Price");
                return;
            }
            if (SalePrice.getText().equals("") || SalePrice.getText().equals("0")) {
                queryFunction.service.msg.ConditionalMessage("Empty SalePrice");
                return;
            }
            if (totalPrice.getText().equals("") || totalPrice.getText().equals("0")) {
                queryFunction.service.msg.ConditionalMessage("Empty total price");
                return;
            }
            IDgenerate();
            String expired_date = null;
            if (ExpiredDate.getValue() != null) {
                String date = ExpiredDate.getValue().toString();
                expired_date = date.replace(date.substring(7, 10), "");
            }
            if (Discount.getText().equals("")) {
                Discount.setText("0");
            }
            int ch = 0;
            String sql3 = "SELECT * FROM purchase_currentpurchase WHERE product_id= '" + Product_hiddenID + "'";
            rss = queryFunction.getResult(sql3);
            while (rss.next()) {
                if (rss.getString("expired_date").equals(expired_date)) {
                    try {
                        double saleP = Double.parseDouble(rss.getString("product_totalpurchaseprice"));
                        double quant = Double.parseDouble(rss.getString("product_quantity"));
                        double Tsale = saleP + Double.parseDouble(totalPrice.getText());
                        double Tquant = quant + Double.parseDouble(quantity.getText());
                        double avgSale = Tsale / Tquant;
                        double dis = Double.parseDouble(rss.getString("product_discount_money"));
                        double avgDis = (dis + Double.parseDouble(Discount.getText())) / Tquant;
                        String sql = "UPDATE `purchase_currentpurchase` SET product_purchaseprice = '" + avgSale + "', product_quantity = "
                                + "'" + Tquant + "',product_discount_money = '" + avgDis + "',"
                                + "product_totalpurchaseprice = product_totalpurchaseprice + '" + totalPrice.getText() + "' "
                                + "WHERE product_id = '" + rss.getString("product_id") + "' AND expired_date='" + expired_date + "'";
                        queryFunction.UpdateMessageLess(sql);
                        ch = 2;
                        totalPrice();
                        view();
                        cleanSingle();
                    } catch (Exception e) {
                    }

                } else if (rss.wasNull()) {
                    currentInsrt(expired_date);
                    ch = 1;
                }
            }
            if (ch == 0) {
                currentInsrt(expired_date);
            }
        } catch (SQLException ex) {
            queryFunction.service.msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem in insert\n" + ex);
        } finally {
            try {
                rss.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private void currentInsrt(String expired_date) {
        String sql = "Insert into purchase_currentpurchase (id,`product_id`,sub_unit_id,`product_quantity`,"
                + "`product_purchaseprice`,`product_totalpurchaseprice`,`product_unitsaleprice`,"
                + "`product_discount_money`,expired_date,exp_date,`session_id`,`Admin`) "
                + "VALUES ('" + auto_id + "','" + Product_hiddenID + "','" + measurement_type + "','" + quantity.getText() + "',"
                + "'" + price.getText() + "','" + totalPrice.getText() + "',"
                + "'" + SalePrice.getText() + "','" + Discount.getText() + "',"
                + "'" + expired_date + "','" + ExpiredDate.getValue() + "','" + invoiceNo.getText() + "','" + ID + "')";
        queryFunction.Insert(sql);
        totalPrice();
        view();
        cleanSingle();
    }

    private void product_update(String id, String Pprice, String Sprice) {
        try {
            String sql = "UPDATE `product_productinfo` SET purchase_price = '" + Pprice + "', sale_price = '" + Sprice + "' WHERE id = '" + id + "'";
            queryFunction.UpdateMessageLess(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void purchaseEnty(ResultSet rs, String stid) {
        try {

//            String sql = "SELECT * FROM purchase_currentpurchase WHERE "
//                    + "purchase_currentpurchase.`session_id`='" + invoiceNo.getText() + "'";
//            rs = queryFunction.getResult(sql);
//            while (rs.next()) {
            String sqll = "INSERT INTO `purchase_entry`(`invoice_no`,"
                    + "`product_id`,sub_unit_id,`product_quantity`,`product_purchaseprice`,"
                    + "`product_totalpurchaseprice`,`product_unitsaleprice`,"
                    + "`expired_date`,exp_date,`product_discount_money`,`Session_ID`,stock_id,`admin`) VALUES "
                    + "('" + invoiceNo.getText() + "','" + rs.getString("product_id") + "'"
                    + ",'" + rs.getString("sub_unit_id") + "','" + rs.getString("product_quantity") + "',"
                    + "'" + rs.getString("product_purchaseprice") + "',"
                    + "'" + rs.getString("product_totalpurchaseprice") + "',"
                    + "'" + rs.getString("product_unitsaleprice") + "',"
                    + "'" + rs.getString("expired_date") + "','" + rs.getString("exp_date") + "','" + rs.getString("product_discount_money") + ""
                    + "','" + invoiceNo.getText() + "','" + stid + "','" + ID + "')";
            queryFunction.InsertCustomise(sqll, "Have a Problem in Entry.");

//            }
        } catch (SQLException | HeadlessException e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem in Purchase Entry!\n" + e);
        } finally {

            try {
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private void purchaseLadger() {
        if (discount.getText().equals("")) {
            discount.setText("0");
        }
        if (total == 0) {
            total = Double.parseDouble(totalAmount.getText());
        }
        String sql = "INSERT INTO `purchase_ledger` (`invoice_no`,`invoice_date`,`voucher_no`,`voucher_date`"
                + ",`suplier_id`,`total_item`,total_discount,`net_total_ammount`,`paid`,`due`,admin_id,created_at)"
                + "values('" + invoiceNo.getText().trim() + "','" + service.getnonFormatCurrentDate() + "','"
                + "" + voucherNo.getText().trim() + "','" + voucherDate.getValue() + "','" + supp_id + ""
                + "','" + totalitem + "','" + discount.getText().trim() + "','" + total + "','" + paidAmount.getText().trim() + ""
                + "','" + Due.getText().trim() + "','" + ID + "','" + service.getDateTime() + "')";
        queryFunction.InsertCustomise(sql, "Have a Problem in Purchase Ladger.");

    }

    private void stockProduct() {
        try {
            String sql = "select * FROM purchase_currentpurchase where purchase_currentpurchase.`session_id`='" + invoiceNo.getText() + "'";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                quantity1 = Double.parseDouble(rs.getString("product_quantity"));
                purchaseprice1 = Double.parseDouble(rs.getString("product_purchaseprice"));
                Tamount1 = quantity1 * purchaseprice1;
                String sql1 = "SELECT * FROM stock_product WHERE stock_product.product_id IN ('" + rs.getString("product_id") + "')";
                rs1 = queryFunction.getResult(sql1);
                if (rs1.next()) {
                    if (rs.getString("expired_date").equals(rs1.getString("expired_date"))) {
                        quantityv = Double.parseDouble(rs1.getString("quantity"));
                        totalquantity = quantity1 + quantityv;
                        purchaseprice = Double.parseDouble(rs1.getString("purchase_price"));
                        Tamount = quantityv * purchaseprice;
                        totalpurchaseprice = Tamount1 + Tamount;
                        average = totalpurchaseprice / totalquantity;
                        String sql2 = "UPDATE stock_product SET stock_product.quantity = stock_product.quantity + "
                                + "'" + rs.getString("product_quantity") + "',stock_product.purchase_price = "
                                + "'" + rs.getString("product_purchaseprice") + "',stock_product.sale_price = "
                                + "'" + rs.getString("product_unitsaleprice") + "',stock_product."
                                + "old_and_new_purchase_price_average = '" + average + "' WHERE stock_product."
                                + "product_id = '" + rs.getString("product_id") + "'";
                        queryFunction.UpdateMessageLess(sql2);
                    } else {
                        stockInsert(rs);
                    }
                } else {
                    stockInsert(rs);
                }
            }
        } catch (SQLException | HeadlessException e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem in Stock Product.\n" + e);

        } finally {
            try {
                queryFunction.post.close();
                rs.close();
                rs1.close();
            } catch (Exception e) {
            }
        }
    }

    private void stockInsert(ResultSet rs) {

        try {
            String query = "INSERT INTO `stock_product`(`stock_product`.`product_id`,`stock_product`.`quantity`,"
                    + "`stock_product`.`purchase_price`,`stock_product`.`sale_price`,`stock_product`."
                    + "`old_and_new_purchase_price_average`,`stock_product`.`expired_date`) VALUES "
                    + "('" + rs.getString("purchase_currentpurchase.product_id") + "','" + rs.getString("purchase_currentpurchase.product_quantity") + "','" + rs.getString("purchase_currentpurchase."
                    + "product_purchaseprice") + "','" + rs.getString("purchase_currentpurchase.product_unitsaleprice") + "',"
                    + "'" + rs.getString("purchase_currentpurchase.product_purchaseprice") + "','" + rs.getString("expired_date") + "')";
            queryFunction.InsertCustomise(query, "Have a Problem in stock else.");
            String sql = "SELECT MAX(id) AS 'id' FROM `stock_product`";
            set = queryFunction.getResult(sql);
            if (set.next()) {
                purchaseEnty(rs, set.getString("id"));
            }
        } catch (Exception e) {
        }
    }

    private void SupplierTransaction() {
        try {
            double balance = 0, netbalance = 0;
            String sql = "SELECT * FROM suplier_transaction WHERE suplier_id = '" + supp_id + "' ORDER BY id DESC";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                balance = Double.parseDouble(rs.getString("balance"));
            }
            String voucher = "";
            if (voucherNo.getText().equals("")) {
                voucher = "Purchase";
            } else {
                voucher = voucherNo.getText();
            }
            netbalance = balance + Double.parseDouble(Due.getText());
            String sql1 = "INSERT INTO suplier_transaction (suplier_id,transaction_date,"
                    + "invoice_id,voucher_no,transaction_type,debit,credit,balance)VALUES ("
                    + "'" + supp_id + "','" + queryFunction.service.getnonFormatCurrentDate() + "',"
                    + "'" + invoiceNo.getText() + "','" + voucher + "','Cash','" + Due.getText() + "',"
                    + "'" + paidAmount.getText() + "','" + netbalance + "')";
            queryFunction.InsertCustomise(sql1, "Have a Problem in Supplier Transaction");
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private void peymentStatement() {
        String sql = "INSERT INTO purchase_payment_statement (invoice_no,"
                + "entry_date,total_ammount,payment,due,comment)VALUES ("
                + "'" + invoiceNo.getText().trim() + "','" + service.getnonFormatCurrentDate() + "','" + netTotal.getText().trim() + ""
                + "','" + paidAmount.getText().trim() + "','" + Due.getText().trim() + "','First Payment')";
        queryFunction.InsertCustomise(sql, "Have a problem in Peyment Statement.");

    }

    private void IdRegenerate() {
        try {
            String sql2 = "SELECT * FROM purchase_currentpurchase";
            rs = queryFunction.getResult(sql2);
            int i = 1;
            while (rs.next()) {
                String sql3 = "UPDATE purchase_currentpurchase SET id = '" + i + "' WHERE id = '" + rs.getString("id") + "'";
                queryFunction.UpdateMessageLess(sql3);
                i++;
            }
        } catch (Exception e) {
        }
    }

    private void Simpleview() {
        proname_col1.setCellValueFactory(new PropertyValueFactory<>("productName"));
        guantity_col1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price_col1.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        totalPrice_col1.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        try {
            String sql = "SELECT product_productinfo."
                    + "product_name,purchase_currentpurchase.product_quantity,"
                    + "purchase_currentpurchase.product_purchaseprice,"
                    + "purchase_currentpurchase.product_totalpurchaseprice"
                    + " FROM "
                    + "purchase_currentpurchase INNER JOIN product_productinfo "
                    + "ON purchase_currentpurchase.product_id=product_productinfo.id";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                data.add(new PurchaseView(rs.getString("id"),
                        rs.getString("product_name"),
                        rs.getString("product_quantity"),
                        rs.getString("product_purchaseprice"),
                        rs.getString("product_discount_money"),
                        rs.getString("product_totalpurchaseprice"),
                        rs.getString("product_unitsaleprice"),
                        btn
                ));
            }
            tableView1.setItems(data);
        } catch (Exception e) {
        }

    }

    private void view() {
        try {
            data.clear();
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            proname_col.setCellValueFactory(new PropertyValueFactory<>("productName"));
            guantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            price_col.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
            discount_col.setCellValueFactory(new PropertyValueFactory<>("discount"));
            totalPrice_col.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            sale_col.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
            action_col.setCellValueFactory(new PropertyValueFactory<>("delete"));

            String sql = "SELECT purchase_currentpurchase.id,product_productinfo."
                    + "product_name,purchase_currentpurchase.product_quantity,"
                    + "purchase_currentpurchase.product_purchaseprice,"
                    + "purchase_currentpurchase.product_discount_money,"
                    + "purchase_currentpurchase.product_totalpurchaseprice,"
                    + "purchase_currentpurchase.product_unitsaleprice FROM "
                    + "purchase_currentpurchase INNER JOIN product_productinfo "
                    + "ON purchase_currentpurchase.product_id=product_productinfo.id";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                btn = new JFXButton("Delete");
                btn.setButtonType(JFXButton.ButtonType.RAISED);
                btn.setStyle("-fx-background-color: #039aff; -fx-background-radius: 10; "
                        + "-fx-text-fill: white; -fx-font-weight: bold;");
                btn.setId(String.valueOf(index));

                data.add(new PurchaseView(rs.getString("id"),
                        rs.getString("product_name"),
                        rs.getString("product_quantity"),
                        rs.getString("product_purchaseprice"),
                        rs.getString("product_discount_money"),
                        rs.getString("product_totalpurchaseprice"),
                        rs.getString("product_unitsaleprice"),
                        btn
                ));
                btn.setOnAction(e -> {
                    try {
                        String source = e.getSource().toString();
                        int row = Integer.parseInt(source.substring(13, source.indexOf(",")));
                        PurchaseView p = tableView.getItems().get(row);

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                        alert.setTitle("Delete Selected Data?");
                        alert.setContentText("Are you sure you want to delete?\nID : " + p.getId());
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.YES) {
                            String sql1 = "delete from  purchase_currentpurchase WHERE id= '" + p.getId() + "'";
                            queryFunction.DeleteConditionLess(sql1);
                            IdRegenerate();
                            view();
                            totalPrice();

                        }

                    } catch (Exception f) {
                        msg.WarningMessage("Unsuccessful", "Warning", "Delete Unsuccessful\n" + f);
                    }
                });
                index++;
            }
            tableView.setItems(data);
            index = 0;
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

    private void initable() {
        initcols();
    }

    private void initcols() {
        editablecols();
    }

    private void editablecols() {
        quantityid.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityid.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setQuantity_multi(e.getNewValue());
            try {
                PurchaseView p = dropdown_tableview.getItems().get(row1);
                double e_discount = Double.parseDouble(p.getDiscount_multi());
                double e_quantity = Double.parseDouble(p.getQuantity_multi());
                double e_purchaseprice;
                if (p.getPurchasePrice_multi() == null) {
                    e_purchaseprice = 0;
                } else {
                    e_purchaseprice = Double.parseDouble(p.getPurchasePrice_multi());
                }
                TemporaryTotal_p = e_quantity * e_purchaseprice;
                totalPrice_V = TemporaryTotal_p - e_discount;
                String sql1 = "UPDATE purchase_currentpurchase SET product_quantity = "
                        + "'" + p.getQuantity_multi() + "',product_totalpurchaseprice="
                        + "'" + totalPrice_V + "' WHERE product_id = '" + getProID(p.getProductName_multi()) + "'";

                queryFunction.UpdateMessageLess(sql1);
                view();
                totalPrice();

            } catch (Exception f) {
                msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + f);

            } finally {
                try {
                    queryFunction.post.close();
                } catch (Exception f) {

                }
            }
        });

        purchasepriceid.setCellFactory(TextFieldTableCell.forTableColumn());
        purchasepriceid.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPurchasePrice_multi(e.getNewValue());
            try {

                PurchaseView p = dropdown_tableview.getItems().get(row1);
                double e_discount = Double.parseDouble(p.getDiscount_multi());
                double e_quantity = Double.parseDouble(p.getQuantity_multi());
                double e_purchaseprice;
                if (p.getPurchasePrice_multi() == null) {
                    e_purchaseprice = 0;
                } else {
                    e_purchaseprice = Double.parseDouble(p.getPurchasePrice_multi());
                }
                TemporaryTotal_p = e_quantity * e_purchaseprice;
                totalPrice_V = TemporaryTotal_p - e_discount;

                String sql1 = "UPDATE purchase_currentpurchase SET product_purchaseprice = "
                        + "'" + p.getPurchasePrice_multi() + "',product_totalpurchaseprice="
                        + "'" + totalPrice_V + "' WHERE product_id = '" + getProID(p.getProductName_multi()) + "'";

                queryFunction.UpdateMessageLess(sql1);
                view();
                totalPrice();

            } catch (Exception f) {
                msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem.\n" + f);
            } finally {
                try {
                    queryFunction.post.close();
                } catch (Exception g) {

                }
            }
        });

        discountid.setCellFactory(TextFieldTableCell.forTableColumn());
        discountid.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDiscount_multi(e.getNewValue());
            try {

                PurchaseView p = dropdown_tableview.getItems().get(row1);
                double e_discount = Double.parseDouble(p.getDiscount_multi());
                double e_quantity = Double.parseDouble(p.getQuantity_multi());
                double e_purchaseprice;
                if (p.getPurchasePrice_multi() == null) {
                    e_purchaseprice = 0;
                } else {
                    e_purchaseprice = Double.parseDouble(p.getPurchasePrice_multi());
                }
                TemporaryTotal_p = e_quantity * e_purchaseprice;
                totalPrice_V = TemporaryTotal_p - e_discount;
                String sql1 = "UPDATE purchase_currentpurchase SET product_discount_money = "
                        + "'" + p.getDiscount_multi() + "',product_totalpurchaseprice="
                        + "'" + totalPrice_V + "' WHERE product_id = '" + getProID(p.getProductName_multi()) + "'";

                queryFunction.UpdateMessageLess(sql1);
                view();
                totalPrice();

            } catch (Exception f) {
                msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem.\n" + f);
            } finally {
                try {
                    queryFunction.post.close();
                } catch (Exception g) {

                }
            }

        });
    }

    private String getProID(String name) {
        String ID = null;
        try {
            String sql2 = "SELECT id FROM product_productinfo WHERE product_name='" + name + "'";
            rs = queryFunction.getResult(sql2);
            if (rs.next()) {
                ID = rs.getString("id");
            }
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
        return ID;
    }

    private void initmultiselection(String query) {
        try {
            String sql = query;
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                check = new JFXCheckBox();
                check.setId(String.valueOf(index));

                data1.add(new PurchaseView(check,
                        rs.getString("product_name"),
                        "1",
                        rs.getString("purchase_price"),
                        "0.0"
                ));
                check.setOnAction(e -> {
                    try {
                        String source = e.getSource().toString();
                        int row = Integer.parseInt(source.substring(15, source.indexOf(",")));
                        PurchaseView p = dropdown_tableview.getItems().get(row);

                        String sql2 = "SELECT * FROM product_productinfo WHERE "
                                + "product_name = '" + p.getProductName_multi() + "'";
                        rs = queryFunction.getResult(sql2);
                        if (rs.next()) {
                            if (row != row1) {
                                proid = rs.getString("id");
                                if (rs.getString("sale_price") == null) {
                                    saleprice = 0;
                                } else {
                                    saleprice = Double.parseDouble(rs.getString("sale_price"));
                                }
                                if (rs.getString("purchase_price") == null) {
                                    totalPrice_V = 0;
                                } else {
                                    totalPrice_V = Double.parseDouble(rs.getString("purchase_price"));
                                }
                            }
                        }
                        if (!invoiceNo.getText().equals("")) {
                            if (p.getCheckbox().isSelected()) {
                                IDgenerate();
                                double quan_m, pur_m, dis_m;
                                if (p.getQuantity_multi() == null) {
                                    quan_m = 0;
                                } else {
                                    quan_m = Double.parseDouble(p.getQuantity_multi());
                                }
                                if (p.getPurchasePrice_multi() == null) {
                                    pur_m = 0;
                                } else {
                                    pur_m = Double.parseDouble(p.getPurchasePrice_multi());
                                }
                                if (p.getDiscount_multi() == null) {
                                    dis_m = 0;
                                } else {
                                    dis_m = Double.parseDouble(p.getDiscount_multi());
                                }
                                String sql1 = "INSERT INTO purchase_currentpurchase (id,`product_id`,`product_quantity`,"
                                        + "`product_purchaseprice`,`product_totalpurchaseprice`,`product_unitsaleprice`,"
                                        + "`product_discount_money`,`session_id`,`Admin`)"
                                        + "VALUES ('" + auto_id + "','" + proid + "',"
                                        + "'" + quan_m + "',"
                                        + "'" + pur_m + "','" + totalPrice_V + "',"
                                        + "'" + saleprice + "','" + dis_m + "',"
                                        + "'" + invoiceNo.getText() + "','sbit')";

                                queryFunction.InsertCustomise(sql1, "Have a Problem in multi insert.");
                                view();
                                totalPrice();
                                proid = null;
                                saleprice = 0;
                                totalPrice_V = 0;
                            } else if (!p.getCheckbox().isSelected()) {
                                String sql1 = "DELETE FROM purchase_currentpurchase WHERE product_id = "
                                        + "(SELECT id FROM product_productinfo WHERE "
                                        + "product_name = '" + p.getProductName_multi() + "')";
                                queryFunction.DeleteConditionLess(sql1);
                                IdRegenerate();
                                view();
                                totalPrice();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Please Enter the Invioce Number.", ButtonType.CLOSE);
                            alert.show();
                        }

                    } catch (Exception f) {
                        msg.WarningMessage("Unsuccessful", "Warning", "Add Unsuccessfull.\n" + f);
                    }
                });
                index++;
            }
            dropdown_tableview.setItems(data1);
            index = 0;
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void multipleselectionView() {

        data1.clear();
        checkid.setCellValueFactory(new PropertyValueFactory<>("checkbox"));
        productnameid.setCellValueFactory(new PropertyValueFactory<>("productName_multi"));
        quantityid.setCellValueFactory(new PropertyValueFactory<>("quantity_multi"));
        purchasepriceid.setCellValueFactory(new PropertyValueFactory<>("purchasePrice_multi"));
        discountid.setCellValueFactory(new PropertyValueFactory<>("discount_multi"));

        String sql = "SELECT * FROM product_productinfo LIMIT 100";
        initmultiselection(sql);
    }

    private void search() {

        data1.clear();
        String sql = "SELECT * FROM product_productinfo WHERE product_name LIKE '%" + multisearchfield.getText() + "%' LIMIT 100";
        initmultiselection(sql);
    }

    private void clear() {
        invoiceNo.setText("");
        voucherNo.setText("");
        voucherDate.setValue(null);
        SelectSupplier.getEditor().setText("");
        SelectPhone.setText("");
        selectProduct.getEditor().setText("");
        price.setText("");
        Discount.setText("");
        totalPrice.setText("");
        ExpiredDate.setValue(null);
        SalePrice.setText("");
        quantity.setText("");
        totalAmount.setText("");
        paidAmount.setText("");
        discount.setText("");
        Due.setText("");
        netTotal.setText(null);
        unit.setValue(null);
        measurement_type = null;
        invoiceNo.requestFocus();
    }

    private void report() {

        String path = "/fxsupershop/Purchase_Report/";
        String sql = "SELECT purchase_entry.*,"
                + "suplier_info.company_name,suplier_info.company_phone,project_info.*,"
                + "purchase_ledger.invoice_date,purchase_ledger.total_discount,purchase_ledger.net_total_ammount,"
                + "purchase_ledger.paid,purchase_ledger.due,product_productinfo.product_name "
                + "FROM purchase_entry,purchase_ledger,product_productinfo,suplier_info,project_info WHERE purchase_ledger.suplier_id"
                + " = suplier_info.id AND "
                + "purchase_entry.product_id = product_productinfo.id "
                + "AND purchase_entry.invoice_no = purchase_ledger.invoice_no AND "
                + "purchase_ledger.invoice_no = '" + invoiceNo.getText() + "'";
        queryFunction.getImagePath(sql, "image");
        queryFunction.report.getReport(path, "PurchaseReport.jrxml", sql);
    }

    private void viewdialog() {
        stackpane.setVisible(true);
        view();
        dialog = new JFXDialog(stackpane, dialoglayout, JFXDialog.DialogTransition.BOTTOM);
//        dialogpane.setActions(closedialog);
        closebtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                dialog.close();
                stackpane.setVisible(false);
            }
        });
        dialog.show();
    }

    private void insert() {

        if (text_name.getText().equals("")) {
            msg.ConditionalMessage("Enter Supplier Name");
            return;
        }
        if (text_comPhone.getText().equals("")) {
            msg.ConditionalMessage("Enter Company Phone");
            return;
        }

        String sql = "INSERT INTO `suplier_info` (`company_name`,`suplier_phone`,"
                + "`company_phone`,`email`,`present_address`)VALUES("
                + "'" + text_name.getText() + "',"
                + "'" + text_supPhone.getText() + "','" + text_comPhone.getText() + "',"
                + "'" + text_email.getText() + "','" + text_present_Address.getText() + "')";
        queryFunction.Insert(sql);
        clean();
    }

    private void clean() {
        text_name.setText(null);
        text_supPhone.setText(null);
        text_comPhone.setText(null);
        text_email.setText(null);
        text_present_Address.setText(null);
        text_name.requestFocus();
    }

    private void cleanSingle() {
        selectProduct.setValue(null);
        ExpiredDate.setValue(null);
        quantity.setText(null);
        price.setText(null);
        Discount.setText(null);
        totalPrice.setText(null);
        SalePrice.setText(null);
        unit.setValue(null);
        barcode.setText(null);
        measurement_type = null;
//        selectProduct.requestFocus();
    }

    private void productAction(String query, int check) {
        ResultSet rss = null;
        try {
            String sqll = query;
            rss = queryFunction.getResult(sqll);
            if (rss.next()) {
                Product_hiddenID = "";
                Product_hiddenID = rss.getString("product_productinfo.id");
                if (check == 1) {
                    selectProduct.setValue(rss.getString("product_name"));
                }
                price.setText(rss.getString("purchase_price"));
                totalPrice.setText(rss.getString("purchase_price"));
                quantity.setText("1");
                Discount.setText("0");
                SalePrice.setText(rss.getString("sale_price"));
                if (price.getText() == null) {
                    amount = 0;
                } else {
                    amount = Double.parseDouble(price.getText());
                }
                if (rss.getString("purchase_price") == null) {
                    sum_price = 0;
                } else {
                    sum_price = Double.parseDouble(rss.getString("purchase_price"));
                }
                if (rss.getString("sale_price") == null) {
                    sum_salePrice = 0;
                } else {
                    sum_salePrice = Double.parseDouble(rss.getString("sale_price"));
                }

                measurement_type = rss.getString("measurement_type");
//                unitview(measurement_type);
//                unit.setValue(rss.getString("sub_unit_name"));
                if (check == 2) {
                    selectProduct.setValue(rss.getString("product_name"));
                }
//                double purchase = Sdata * sum_price;
//                price.setText(String.valueOf(purchase));
//                totalPrice.setText(String.valueOf(purchase));
//                double sale = Sdata * sum_salePrice;
//                SalePrice.setText(String.valueOf(sale));

                double purchase = 1 * sum_price;
                price.setText(String.valueOf(purchase));
                totalPrice.setText(String.valueOf(purchase));
                double sale = 1 * sum_salePrice;
                SalePrice.setText(String.valueOf(sale));

            }
        } catch (Exception e) {
        } finally {
            try {
                queryFunction.post.close();
                rss.close();
            } catch (Exception e) {

            }
        }
    }

    private String UnitAction(JFXComboBox box, String measurID) {
        unitID = null;
        try {
            String sql = "SELECT product_measurement_subunit.* FROM product_measurement_subunit WHERE id="
                    + "'" + unitlist.get(box.getSelectionModel().getSelectedIndex()) + "' AND measurement_unit_id='" + measurID + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                unitID = rs.getString("id");
                Sdata = Double.parseDouble(rs.getString("sub_unit_data"));
                double purchase = Sdata * sum_price;
                price.setText(String.valueOf(purchase));
                double totalp = purchase * Double.parseDouble(quantity.getText());
                totalPrice.setText(String.valueOf(totalp - (Double.parseDouble(Discount.getText()) * Double.parseDouble(quantity.getText()))));
                double sale = Sdata * sum_salePrice;
                SalePrice.setText(String.valueOf(sale));
            }
        } catch (Exception e) {
        }
        return unitID;
    }

    @FXML
    private void SelectSuppAction(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            try {
                                String sql = "SELECT * FROM suplier_info WHERE id = '" + suplierlist.get(SelectSupplier.getSelectionModel().getSelectedIndex()) + "'";
                                post = con.prepareStatement(sql);
                                ResultSet rs = post.executeQuery();
                                if (rs.next()) {
                                    SelectPhone.setText(rs.getString("company_phone"));
                                    supp_id = rs.getString("id");
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

                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void selectProAction(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            try {
                                String sql = "select product_productinfo.*,product_measurement_subunit.sub_unit_name from "
                                        + "product_productinfo,product_measurement_subunit where product_productinfo.id ='"
                                        + "" + productlist.get(selectProduct.getSelectionModel().getSelectedIndex()) + "' AND product_productinfo.measurement_type"
                                        + " = product_measurement_subunit.measurement_unit_id";
                                productAction(sql, 2);
                            } catch (Exception e) {
                            }
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });

    }

    @FXML
    private void quanSum(KeyEvent event) {
        try {

            double qun = Double.parseDouble(quantity.getText());

            double unitprice = Double.parseDouble(price.getText());
            amount = 0;
            amount = qun * unitprice;

            totalPrice.setText(String.valueOf(amount - (Double.parseDouble(Discount.getText()) * qun)));

        } catch (Exception e) {

        }
    }

    @FXML
    private void disSum(KeyEvent event) {
        try {
            double disc;
            if (Discount.getText() == null || Discount.getText().equals("")) {
                disc = 0;
            } else {
                disc = Double.parseDouble(Discount.getText());
            }
            double unitprice = Double.parseDouble(price.getText());
            double quan = Double.parseDouble(quantity.getText());
            double netprice = unitprice - disc;
            double net = netprice * quan;
            totalPrice.setText(String.valueOf(net));
        } catch (Exception e) {

        }
    }

    @FXML
    private void Addbtn(ActionEvent event) {
        product_update(Product_hiddenID, price.getText().trim(), SalePrice.getText().trim());
        currentPurchase();
    }

    @FXML
    private void Viewbtn(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            view();
                            viewdialog();
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void finalDisSum(KeyEvent event) {
        try {

            double totalamount = Double.parseDouble(totalAmount.getText());

            double discountmoney;
            if (discount.getText() == null || discount.getText().equals("")) {
                discountmoney = 0;
            } else {
                discountmoney = Double.parseDouble(discount.getText());
            }
            total = 0;
            total = totalamount - discountmoney;

            netTotal.setText(String.valueOf(total));

        } catch (Exception e) {

        }
    }

    @FXML
    private void paidAmountSum(KeyEvent event) {
        try {

            double paidamount;
            if (paidAmount.getText() == null || paidAmount.getText().equals("")) {
                paidamount = 0;
            } else {
                paidamount = Double.parseDouble(paidAmount.getText());
            }
            double nettotal = Double.parseDouble(netTotal.getText());
            Double finalamount = nettotal - paidamount;

            Due.setText(String.valueOf(finalamount));

        } catch (Exception e) {

        }
    }

    @FXML
    private void Submitbtn(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            try {
                                if (invoiceNo.getText().equals("")) {
                                    msg.ConditionalMessage("Empty Invoice");
                                    return;
                                }
                                if (SelectSupplier.getValue() == null || SelectSupplier.getValue().equals("") || supp_id.equals("")) {
                                    msg.ConditionalMessage("Empty Supplier");
                                    return;
                                }
                                if (totalAmount.getText() == null || netTotal.getText() == null
                                        || paidAmount.getText() == null || Due.getText() == null) {
                                    msg.ConditionalMessage("Empty Value");
                                    return;
                                }
                                if (totalAmount.getText().equals("") || netTotal.getText().equals("")
                                        || paidAmount.getText().equals("") || Due.getText().equals("")) {
                                    msg.ConditionalMessage("Empty Value");
                                    return;
                                }
                                if (discount.getText().equals("")) {
                                    discount.setText("0");
                                }
                                totalitem();
//                                purchaseEnty();
                                stockProduct();
                                purchaseLadger();
                                SupplierTransaction();
                                peymentStatement();
                                if (check_invoice.isSelected()) {
                                    report();
                                }
                                ShoppingCartclear();
                                view();
                                clear();
                                autoId();
                                msg.InformationMessage("Successfull", "Information", "Successful");
                            } catch (Exception e) {
                            }
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void Allclearbtn(ActionEvent event) {
        clear();
        autoId();

    }

    @FXML
    private void clearShopCardbtn(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            ShoppingCartclear();
                            totalPrice();
                            view();
                        });

                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });

    }

    @FXML
    private void closestack(MouseEvent event) {
        stackpane.setVisible(false);
    }

    @FXML
    private void showpress_supplier(KeyEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            press_supplier(event);
                        });

                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });

    }

    @FXML
    private void showpress_pro(KeyEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            press_product(event);
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void clicked_supplier(MouseEvent event) {
        SelectSupplier.show();
        supplier();
    }

    @FXML
    private void clicked_product(MouseEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            selectProduct.show();
//                            product();
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void getTableValue(MouseEvent event) {

    }

    @FXML
    private void OpenMultipanebtn(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            multiple_pane.setVisible(true);
                            new SlideInDown(multiple_pane).setSpeed(2).play();
                            multipleselectionView();
                        });

                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void CloseMultipanebtn(ActionEvent event) {
        new SlideOutUp(multiple_pane).setSpeed(2).play();
    }

    @FXML
    private void InvioceNoCheckTextfield(KeyEvent event) {
    }

    @FXML
    private void VoucherNoCheckTextfield(KeyEvent event) {
    }

    @FXML
    private void SupPhoneCheckTextfield(KeyEvent event) {
    }

    @FXML
    private void VoucherDateCheckTextfield(InputMethodEvent event) {
    }

    @FXML
    private void tablemouseclicked(MouseEvent event) {
        try {
            if (event.getClickCount() == 1) {
                @SuppressWarnings("rawtypes")
                TablePosition pos = dropdown_tableview.getSelectionModel().getSelectedCells().get(0);
                row1 = pos.getRow();
                col1 = pos.getColumn();
            }
            PurchaseView p = dropdown_tableview.getItems().get(row1);
            String sql = "SELECT * FROM product_productinfo WHERE product_name = '" + p.getProductName_multi() + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                saleprice = Double.parseDouble(rs.getString("sale_price"));
                totalPrice_V = Double.parseDouble(rs.getString("purchase_price"));
                proid = rs.getString("id");
            }

        } catch (Exception e) {

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    @FXML
    private void tablekeyreleased(KeyEvent event) {
    }

    @FXML
    private void dropviewbtn(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            multipleselectionView();
                            viewdialog();
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void MultisearchActionfield(KeyEvent event) {
        search();

    }

    @FXML
    private void supplierbtn(ActionEvent event) {
        add_supplier_pane.setVisible(true);
        new ZoomIn(add_supplier_pane).setSpeed(2).play();
    }

    @FXML
    private void supplierPaneClose(ActionEvent event) {
        ZoomOut zoom = new ZoomOut(add_supplier_pane);
        zoom.setSpeed(2).play();
        zoom.setOnFinished((e) -> {
            add_supplier_pane.setVisible(false);
        });
    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
        supplier();
    }

    @FXML
    private void CleanSupp(ActionEvent event) {
        clean();
    }

    @FXML
    private void barcodeAction(KeyEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            String sql = "select * from product_productinfo,product_measurement_subunit where product_productinfo.barcode='"
                                    + "" + barcode.getText() + "' AND product_productinfo.measurement_type"
                                    + " = product_measurement_subunit.id";
                            productAction(sql, 1);
                        });

                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void UnitAction(ActionEvent event) {
        try {
            unitID = UnitAction(unit, measurement_type);
        } catch (Exception e) {
        }
    }

    @FXML
    private void PurchesSum(KeyEvent event) {
        try {

            double qun = Double.parseDouble(quantity.getText());

            double unitprice = Double.parseDouble(price.getText());
            amount = 0;
            amount = qun * unitprice;

            totalPrice.setText(String.valueOf(amount));

        } catch (Exception e) {

        }
    }

    @FXML
    private void barcodeClick(MouseEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            product();
                        });

                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}

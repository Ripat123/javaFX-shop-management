package fxsupershop.Sales;

import com.jfoenix.controls.*;
import fxsupershop.Home.Presenter;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.PrepareQueryFunction;
import fxsupershop.TableView.SalesView;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class Simple_sales_formController implements Initializable {

//    @FXML
//    private JFXScrollPane scroll;
    @FXML
    private ScrollPane scrollpane;
//    @FXML
//    private AnchorPane p1;
    @FXML
    private TableView<SalesView> tableView;
    @FXML
    private TableColumn<?, ?> proname_col1;
    @FXML
    private TableColumn<?, ?> action_col1;
    @FXML
    private JFXComboBox Selectcus;
    @FXML
    private JFXTextField SelectPhone;
    @FXML
    private JFXTextField barcode;
    @FXML
    private JFXComboBox selectProduct;
    @FXML
    private JFXComboBox unit;
    @FXML
    private JFXTextField SalePrice;
    @FXML
    private JFXTextField quantity;
    @FXML
    private JFXTextField Discount_p;
    @FXML
    private JFXTextField Discount_T;
    @FXML
    private JFXTextField net_rate;
    @FXML
    private JFXTextField totalPrice;
//    @FXML
//    private JFXButton addbtn;
    @FXML
    private JFXCheckBox new_customer_check;
    @FXML
    private Label purchase_label;
    @FXML
    private JFXTextField totalAmount;
    @FXML
    private JFXTextField netTotal;
    @FXML
    private JFXTextField paidAmount;
    @FXML
    private JFXTextField Due;
//    @FXML
//    private JFXButton submitbtn;
//    @FXML
//    private JFXButton clearbtn;
//    @FXML
//    private JFXButton cartbtn;
    @FXML
    private JFXCheckBox check_invoice;
    SimpleSalesModel salesModel;
    SimpleSalesPresenter ssp = new SimpleSalesPresenter();
    private String customerID, proID, unitID;
    @FXML
    private Label stock_hint;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    String invoice, stock_id;
    ResultSet rs, rs1;
    Sales_Presenter presenter = new Sales_Presenter();
    @FXML
    private JFXTextField vat;
    JFXButton btn;
    ObservableList<SalesView> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> sale_col;
    @FXML
    private TableColumn<?, ?> total_sale_col;
    @FXML
    private TableColumn<?, ?> quantity_col1;
    int index, currentSale_id;
    double amount, net_amount, current_stock;
    private int uid;
    LoginMultiFormController lmfc = new LoginMultiFormController();
    @FXML
    private JFXTextField net_dis;
    @FXML
    private JFXCheckBox walking_customer;
    String measurement_type = null;
    IDModel iDModel;
    @FXML
    private JFXCheckBox check_invoice1;
    @FXML
    private Label stock_date;
    ObservableList list = FXCollections.observableArrayList();
    ObservableList Unitlist = FXCollections.observableArrayList();
    ObservableList customerlist = FXCollections.observableArrayList();
//    @FXML
//    private Label stock_date1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            customerview();
                            JFXScrollPane.smoothScrolling(scrollpane);
                            productView();
                            autoId();
                            autoClearList();
                            uid = lmfc.User();
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

    private void customerview() {
        customerlist = ssp.customerView(Selectcus);
    }

    private void productView() {
        list.clear();
        list = ssp.ProductView(selectProduct);
    }

    private void autoId() {
        int id = queryFunction.AutoJFXID("sale_ledger");
        String front_tag_ofID = "SI-";
        if (id <= 9) {
            invoice = (front_tag_ofID + "000000" + "" + Integer.toString(id));
            return;
        } else if (id <= 99) {
            invoice = (front_tag_ofID + "00000" + "" + Integer.toString(id));
            return;
        } else if (id <= 999) {
            invoice = (front_tag_ofID + "0000" + "" + Integer.toString(id));
            return;
        } else if (id <= 9999) {
            invoice = (front_tag_ofID + "000" + "" + Integer.toString(id));
            return;
        } else if (id <= 99999) {
            invoice = (front_tag_ofID + "00" + "" + Integer.toString(id));
            return;
        } else if (id <= 999999) {
            invoice = (front_tag_ofID + "0" + "" + Integer.toString(id));
            return;
        } else if (id <= 9999999) {
            invoice = (front_tag_ofID + "" + Integer.toString(id));
            return;
        }

        if (invoice.equals("")) {
            invoice = (front_tag_ofID + "01");
        }
    }

    private void Current_sale_Add() {
        try {
            if (invoice.equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Enter invoice No.", ButtonType.OK).showAndWait();
                return;
            }

            if (SalePrice.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty Sale price Field.", ButtonType.OK).showAndWait();
                return;
            }

            if (quantity.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty Quantity Field.", ButtonType.OK).showAndWait();
                return;
            }
            if (unit.getValue().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Select Unit.", ButtonType.OK).showAndWait();
                return;
            }

            if (totalPrice.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty Total price Field.", ButtonType.OK).showAndWait();
                return;
            }
            if (Discount_T.getText() == null || Discount_T.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty Discount Field.", ButtonType.OK).showAndWait();
                return;
            }
            if (Discount_p.getText() == null || Discount_p.getText().equals("")) {
                Optional<ButtonType> alert = new Alert(Alert.AlertType.WARNING, "Empty Discount Field.", ButtonType.OK).showAndWait();
                return;
            }
            // for auto id.
            int auto_id = ssp.totalitem();
            double due_quantity;
            String sql1 = "SELECT quantity FROM stock_product WHERE id = '" + stock_id + "'";

            rs1 = queryFunction.getResult(sql1);
            if (rs1.next()) {
                double quan = Double.parseDouble(rs1.getString("quantity")), sale_quan = 0;
                double text_quan = Double.parseDouble(quantity.getText());

                String sql2 = "SELECT product_id,sub_unit_id,product_quantity FROM sale_currentsale "
                        + "WHERE product_id='" + proID + "' AND expired_date = '" + stock_date.getText() + "'";
                rs = queryFunction.getResult(sql2);
                while (rs.next()) {
                    sale_quan = sale_quan + Double.parseDouble(rs.getString("product_quantity"));
                }
                text_quan = text_quan + sale_quan;
                due_quantity = text_quan - quan;
                if (text_quan <= quan) {
                    if (currentSale_id == 0) {
                        String sql3 = "SELECT * FROM sale_currentsale WHERE product_id= "
                                + "'" + proID + "' AND expired_date = '" + stock_date.getText() + "'";
                        rs = queryFunction.getResult(sql3);
                        if (rs.next()) {
                            double saleP = Double.parseDouble(rs.getString("product_totalsaleprice"));
                            double quant = Double.parseDouble(rs.getString("product_quantity"));
                            double Tsale = saleP + Double.parseDouble(totalPrice.getText());
                            double Tquant = quant + Double.parseDouble(quantity.getText());
                            double avgSale = Tsale / Tquant;
                            double dis = Double.parseDouble(rs.getString("product_discount"));
                            double avgDis = (dis + Double.parseDouble(Discount_T.getText())) / Tquant;
                            String sql = "UPDATE `sale_currentsale` SET product_saleprice = '" + avgSale + "', product_quantity = "
                                    + "'" + Tquant + "',product_discount = '" + avgDis + "',"
                                    + "product_totalsaleprice = product_totalsaleprice + '" + totalPrice.getText() + "' "
                                    + "WHERE product_id = '" + rs.getString("product_id") + "' AND expired_date = '" + rs.getString("expired_date") + "'";
                            queryFunction.UpdateMessageLess(sql);
                            view();
                            totalPrice();
                            clear1();
                        } else {
                            String sql = "INSERT INTO `sale_currentsale`(`id`,`sub_unit_id`,`product_id`,`product_purchaseprice`,`product_saleprice`,"
                                    + "`product_quantity`,`product_discount`,`product_totalsaleprice`,expired_date,`session_id`)VALUES('" + auto_id + "','" + unitID + "','" + proID + "',"
                                    + "'" + purchase_label.getText() + "','" + SalePrice.getText() + "','" + quantity.getText() + "',"
                                    + "'" + Discount_T.getText() + "','" + totalPrice.getText() + "','" + stock_date.getText() + "','" + invoice + "')";

                            queryFunction.Insert(sql);
                            view();
                            totalPrice();
                            clear1();
                        }
                    } else {
                        String sql = "UPDATE `sale_currentsale` SET `sub_unit_id`='" + unitID + "',"
                                + "`product_purchaseprice`='" + purchase_label.getText() + "',`product_saleprice`"
                                + "='" + SalePrice.getText() + "',`product_quantity`='" + quantity.getText() + "',"
                                + "`product_discount`='" + Discount_T.getText() + "',`product_totalsaleprice`"
                                + "='" + totalPrice.getText() + "' WHERE id = '" + currentSale_id + "'";
                        queryFunction.UpdateMessageLess(sql);
                        view();
                        totalPrice();
                        clear1();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("You have not enough Product!");
                    alert.setContentText("Are you sure you want to Sales with Purchase?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.YES) {
                        double price = Double.parseDouble(purchase_label.getText());
                        double total_purchase = due_quantity * price;
                        presenter.SalesWithPurchaseData(String.valueOf(due_quantity), proID, unitID,
                                purchase_label.getText(), String.valueOf(total_purchase), SalePrice.getText());
                        presenter.salesWithPurchaseEntry();
                        presenter.salesWithPurchaseLadger();
                        presenter.salesWithPurchaseStock();

                        if (currentSale_id == 0) {
                            String sql3 = "SELECT * FROM sale_currentsale WHERE product_id= "
                                    + "'" + proID + "' AND expired_date = '" + stock_date.getText() + "'";
                            rs = queryFunction.getResult(sql3);
                            if (rs.next()) {
                                double saleP = Double.parseDouble(rs.getString("product_totalsaleprice"));
                                double quant = Double.parseDouble(rs.getString("product_quantity"));
                                double Tsale = saleP + Double.parseDouble(totalPrice.getText());
                                double Tquant = quant + Double.parseDouble(quantity.getText());
                                double avgSale = Tsale / Tquant;
                                double dis = Double.parseDouble(rs.getString("product_discount"));
                                double avgDis = (dis + Double.parseDouble(Discount_T.getText())) / Tquant;
                                String sql = "UPDATE `sale_currentsale` SET product_saleprice = '" + avgSale + "', product_quantity = "
                                        + "'" + Tquant + "',product_discount = '" + avgDis + "',"
                                        + "product_totalsaleprice = product_totalsaleprice + '" + totalPrice.getText() + "' "
                                        + "WHERE product_id = '" + rs.getString("product_id") + "' AND expired_date = '" + rs.getString("expired_date") + "'";
                                queryFunction.UpdateMessageLess(sql);
                                view();
                                totalPrice();
                                clear1();
                            } else {
                                String sql = "INSERT INTO `sale_currentsale`(`id`,`sub_unit_id`,`product_id`,`product_purchaseprice`,`product_saleprice`,"
                                        + "`product_quantity`,`product_discount`,`product_totalsaleprice`,expired_date,`session_id`)VALUES('" + auto_id + "','" + unitID + "','" + proID + "',"
                                        + "'" + purchase_label.getText() + "','" + SalePrice.getText() + "','" + quantity.getText() + "',"
                                        + "'" + Discount_T.getText() + "','" + totalPrice.getText() + "','" + stock_date.getText() + "','" + invoice + "')";
                                queryFunction.Insert(sql);
                                view();
                                totalPrice();
                                clear1();
                            }
                        } else {
                            String sql = "UPDATE `sale_currentsale` SET `sub_unit_id`='" + unitID + "',"
                                    + "`product_purchaseprice`='" + purchase_label.getText() + "',`product_saleprice`"
                                    + "='" + SalePrice.getText() + "',`product_quantity`='" + quantity.getText() + "',"
                                    + "`product_discount`='" + Discount_T.getText() + "',`product_totalsaleprice`"
                                    + "='" + totalPrice.getText() + "' WHERE id = '" + currentSale_id + "'";
                            queryFunction.UpdateMessageLess(sql);
                            view();
                            totalPrice();
                            clear1();
                        }
                    }
                }
            }
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Insert Unsuccessful\n" + e);
        }
    }

    private void SalesEntry() {
        try {
            String sql = "SELECT * FROM sale_currentsale WHERE session_id = '" + invoice + "'";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                String sql1 = "INSERT INTO sale_entry (invoice_id,expired_date,product_id,sub_unit_id,"
                        + "product_quantity,product_purchaseprice,product_saleprice,product_discount,"
                        + "product_totalsaleprice) VALUES "
                        + "('" + rs.getString("session_id") + "','" + rs.getString("expired_date") + "','" + rs.getString("product_id") + "',"
                        + "'" + rs.getString("sub_unit_id") + "','" + rs.getString("product_quantity") + "',"
                        + "'" + rs.getString("product_purchaseprice") + "','" + rs.getString("product_saleprice") + "'"
                        + ",'" + rs.getString("product_discount") + "','" + rs.getString("product_totalsaleprice") + "')";
                queryFunction.InsertCustomise(sql1, "Have a Problem in Entry.");
            }
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem in Seles Entry!" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void Sales_Ledger(String cusID) {
        String totalitem = ssp.totali();
        double total_purchase = ssp.totalpurchaseprice();
        double total_saleprice = ssp.totalSaleprice();
//        double total_discount = ssp.totaldiscount();
        double total_discount = Double.parseDouble(net_dis.getText());
        customerID = ssp.customerAction(Selectcus.getValue().toString(), SelectPhone, new_customer_check);
        if (customerID == null) {
            customerID = cusID;
        }
        String sql = "INSERT INTO `sale_ledger`(`invoice_id`,`invoice_date`,`customer_id`,`total_item`,"
                + "`total_purchaseprice`,`totalsaleprice`,`totaldiscount`,`total_amount`,`paid`,`due`,created_at) VALUES('" + invoice + "','" + queryFunction.service.getnonFormatCurrentDate() + "',"
                + "'" + customerID + "','" + totalitem + "','" + total_purchase + "','" + total_saleprice + "',"
                + "'" + total_discount + "','" + totalAmount.getText() + "',"
                + "'" + paidAmount.getText() + "','" + Due.getText() + "','" + queryFunction.service.getDateTime() + "')";

        queryFunction.InsertCustomise(sql, "Have a Problem in Ledger");
    }

    private void peymentStatement() {
        double total_discount = Double.parseDouble(net_dis.getText());
        String sql = "INSERT INTO sale_payment_statement (invoice_no,"
                + "entry_date,total_ammount,vat,discount,payment,due,comment)VALUES ("
                + "'" + invoice + "','" + queryFunction.service.getnonFormatCurrentDate() + "','" + totalAmount.getText().trim() + ""
                + "','" + vat.getText().trim() + "','" + total_discount + "',"
                + "'" + paidAmount.getText().trim() + "','" + Due.getText().trim() + "','First Payment')";
        queryFunction.InsertCustomise(sql, "Have a problem in Peyment Statement.");
    }

    private void substraction_product() {
        try {
            String sql = "SELECT * FROM `sale_currentsale` WHERE session_id = '" + invoice + "'";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                String sql1 = "SELECT stock_product.quantity FROM stock_product WHERE "
                        + "stock_product.product_id = '" + rs.getString("product_id") + "' "
                        + "AND stock_product.expired_date = '" + rs.getString("expired_date") + "'";
                rs1 = queryFunction.getResult(sql1);
                if (rs1.next()) {
                    double quan = Double.parseDouble(rs1.getString("quantity"));
                    double text_quan = Double.parseDouble(rs.getString("product_quantity"));
                    if (text_quan < quan) {
                        String sql3 = "UPDATE stock_product SET quantity = quantity - '" + rs.getString(""
                                + "product_quantity") + "' WHERE product_id = '" + rs.getString("product_id") + "' "
                                + "AND expired_date = '" + rs.getString("expired_date") + "'";
                        queryFunction.UpdateMessageLess(sql3);
                    } else if (text_quan == quan) {
                        String sql4 = "UPDATE stock_product SET quantity = '0' WHERE product_id = "
                                + "'" + rs.getString("product_id") + "' AND expired_date = '" + rs.getString("expired_date") + "'";
                        queryFunction.UpdateMessageLess(sql4);
                    } else {
                        queryFunction.service.msg.WarningMessage("Unsuccessful", "Problem in Stock", "You haven't enough Product");
                    }
                }
            }
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem in Substraction Product!" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
                rs1.close();
            } catch (Exception e) {

            }
        }
    }

    private void CusotmerTransaction(String cusID) {
        try {
            double balance = 0, netbalance = 0;
            if (customerID == null) {
                customerID = cusID;
            }
            String sql = "SELECT * FROM customer_transaction WHERE customer_id = '" + customerID + "' ORDER BY id DESC";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                balance = Double.parseDouble(rs.getString("balance"));
            }
            netbalance = balance + Double.parseDouble(Due.getText());
            String sql1 = "INSERT INTO customer_transaction (customer_id,transaction_date,"
                    + "invoice_id,voucher_no,transaction_type,debit,credit,balance)VALUES ("
                    + "'" + customerID + "','" + queryFunction.service.getnonFormatCurrentDate() + "',"
                    + "'" + invoice + "','Sale','Cash','" + Due.getText() + "',"
                    + "'" + paidAmount.getText() + "','" + netbalance + "')";
            queryFunction.InsertCustomise(sql1, "Have a Problem in Customer Transaction");
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private void totalPrice() {
        String sql = "SELECT SUM(`product_totalsaleprice`) AS 'total' FROM `sale_currentsale`";
        String s = queryFunction.getSUMValue(sql, "total");
        totalAmount.setText(s);
        vat.setText("0");
        try {
            double Vat = ssp.vat();
            double sum = Double.parseDouble(s) / 100;
            double Amount = sum * Vat;
            vat.setText(String.valueOf(Amount));
            double net = Double.parseDouble(s) + Amount;
            netTotal.setText(String.valueOf(net));
            net_amount = net;
            net_dis.setText("0");
            paidAmount.setText("0");
            Due.setText(String.valueOf(net));
        } catch (Exception e) {
        }

    }

    private void CustomerKeyView(KeyEvent event) {
        String sql = "SELECT * FROM `customer_info` WHERE `customer_name` LIKE '%" + Selectcus.getValue() + "%'";
//        queryFunction.ShowItemOnkeyReleased(sql, "customer_name", Selectcus, event);
        customerlist = queryFunction.ShowArrayItemKeyReleased(sql, "customer_name", "id", Selectcus, event, customerlist);
    }

    private void clear1() {
        barcode.setText(null);
        selectProduct.setValue(null);
        purchase_label.setText(null);
        SalePrice.setText(null);
        quantity.setText(null);
        unit.setValue(null);
        Discount_T.setText(null);
        Discount_p.setText(null);
        totalPrice.setText(null);
        stock_hint.setText(null);
        stock_date.setText(null);
        net_rate.setText(null);
        barcode.requestFocus();
        currentSale_id = 0;
        measurement_type = null;
    }

    private void view() {
        data.clear();

        proname_col1.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        sale_col.setCellValueFactory(new PropertyValueFactory<>("product_saleprice"));
        quantity_col1.setCellValueFactory(new PropertyValueFactory<>("product_quantity"));
        total_sale_col.setCellValueFactory(new PropertyValueFactory<>("product_totalsaleprice"));
        action_col1.setCellValueFactory(new PropertyValueFactory<>("button"));

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
                            queryFunction.post.close();
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
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "View Unsuccessful" + e);

        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void autoClearList() {
        String sql = "DELETE FROM sale_currentsale";
        queryFunction.DeleteConditionLess(sql);
    }

    private void Notify() {
        ResultSet rss = null;
        try {
            String sql = "SELECT id FROM sale_currentsale";
            rss = queryFunction.getResult(sql);
            while (rss.next()) {
                Presenter pp = new Presenter(queryFunction);
                pp.productNotification(rss.getString("id"));
            }
        } catch (Exception e) {
        } finally {
            try {
                rss.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

//    private void report() {
//        String path = "/fxsupershop/Sales/";
//        String sql = "SELECT sale_currentsale.session_id,sale_currentsale.id,"
//                + "sale_currentsale.product_quantity,sale_currentsale.product_saleprice,"
//                + "sale_currentsale.product_totalsaleprice,sale_currentsale.product_discount,"
//                + "customer_info.customer_name,customer_info.customer_phone,customer_info.customer_address,"
//                + "sale_ledger.invoice_date,sale_ledger.totaldiscount,sale_ledger.total_amount,"
//                + "sale_ledger.paid,sale_ledger.due,product_productinfo.product_name "
//                + "FROM sale_currentsale,sale_ledger,product_productinfo,customer_info WHERE sale_ledger.customer_id"
//                + " = customer_info.id AND "
//                + "sale_currentsale.product_id = product_productinfo.id "
//                + "AND sale_currentsale.session_id = sale_ledger.invoice_id";
//        queryFunction.report.getReport(path, "SalesReport.jrxml", sql);
//    }
    private void clear2() {
        invoice = null;
        selectProduct.setValue(null);
        barcode.setText(null);
        purchase_label.setText(null);
        SalePrice.setText(null);
        quantity.setText(null);
        unit.setValue(null);
        Discount_p.setText(null);
        Discount_T.setText(null);
        totalPrice.setText(null);
        SelectPhone.setText(null);
        Selectcus.setValue(null);
        net_rate.setText(null);
        totalAmount.setText(null);
        vat.setText(null);
        netTotal.setText(null);
        paidAmount.setText(null);
        Due.setText(null);
        check_invoice.setSelected(false);
        Selectcus.requestFocus();
        unitID = null;
        customerID = null;
        proID = null;
        net_dis.setText(null);
        currentSale_id = 0;
        measurement_type = null;
        stock_date.setText(null);
        stock_hint.setText(null);
    }

    private void Release_product(KeyEvent event) {
        String sql = "SELECT product_productinfo.product_name,stock_product.* FROM "
                + "stock_product INNER JOIN product_productinfo ON stock_product.product_id = "
                + "product_productinfo.id WHERE product_productinfo.product_name LIKE '%" + selectProduct.getValue() + "%'";
        list = ssp.ShowArrayItemKeyReleased(sql, "product_name", "stock_product.id",
                "stock_product.expired_date", selectProduct, event, list);
    }

    @FXML
    private void getTableValue(MouseEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            tableView.setEditable(true);
                            try {
                                if (event.getClickCount() == 1) {
                                    @SuppressWarnings("rawtypes")
                                    TablePosition pos = tableView.getSelectionModel().getSelectedCells().get(0);
                                    int row = pos.getRow();
                                    SalesView sv = tableView.getItems().get(row);

                                    String sql = "SELECT * FROM `sale_currentsale`,product_productinfo,product_measurement_subunit WHERE "
                                            + "product_productinfo.id = sale_currentsale.product_id AND product_measurement_subunit.id"
                                            + "=sale_currentsale.sub_unit_id AND sale_currentsale.id = '" + sv.getId() + "'";
                                    rs = queryFunction.getResult(sql);
                                    if (rs.next()) {
                                        selectProduct.setValue(rs.getString("product_name"));
                                        unit.setValue(rs.getString("sub_unit_name"));
                                        SalePrice.setText(sv.getProduct_saleprice());
                                        quantity.setText(sv.getProduct_quantity());
                                        totalPrice.setText(sv.getProduct_totalsaleprice());
                                        Discount_T.setText(rs.getString("product_discount"));
                                        currentSale_id = Integer.parseInt(sv.getId());
                                        disSumT(null);
                                    }
                                }
                            } catch (Exception e) {
                            } finally {
                                try {
                                    rs.close();
                                    queryFunction.post.close();
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
    private void clicked_supplier(MouseEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            customerview();
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
    private void SelectCusAction(ActionEvent event) {
        try {
            customerID = ssp.customerAction(customerlist.get(Selectcus.getSelectionModel().
                    getSelectedIndex()).toString(), SelectPhone, walking_customer);
        } catch (Exception e) {
        }
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
                            CustomerKeyView(event);
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
    private void SupPhoneCheckTextfield(KeyEvent event) {
    }

    @FXML
    private void barcodeAction(KeyEvent event) {
        new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            try {
                                String sql = "SELECT stock_product.*,product_measurement_subunit.*,"
                                        + "product_productinfo.* FROM stock_product INNER JOIN product_productinfo"
                                        + " ON stock_product.product_id = product_productinfo.id "
                                        + "INNER JOIN product_measurement_subunit ON product_productinfo."
                                        + "measurement_type = product_measurement_subunit.measurement_unit_id WHERE product_productinfo.barcode = "
                                        + "'" + barcode.getText() + "'";

                                rs = queryFunction.getResult(sql);
                                if (rs.next()) {
                                    selectProduct.setValue(rs.getString("product_name"));
                                }
                            } catch (Exception e) {
                            } finally {
                                try {
                                    rs.close();
                                    queryFunction.post.close();
                                } catch (Exception e) {
                                }
                            }
                        });
                        return null;
                    }
                };
            }
        }.start();
    }

    @FXML
    private void clicked_product(MouseEvent event) {
        new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
//                            productView();
                        });
                        return null;
                    }
                };
            }
        }.start();
    }

    @FXML
    private void selectProAction(ActionEvent event) {
        new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            try {
                                String i = proID;
                                String sql = "SELECT stock_product.*,product_measurement_subunit.*,"
                                        + "product_productinfo.* FROM stock_product INNER JOIN product_productinfo"
                                        + " ON stock_product.product_id = product_productinfo.id "
                                        + "INNER JOIN product_measurement_subunit ON product_productinfo."
                                        + "measurement_type = product_measurement_subunit.measurement_unit_id WHERE stock_product.id="
                                        + "'" + list.get(selectProduct.getSelectionModel().getSelectedIndex()) + "' AND stock_product.`expired_date` >= SUBSTR(CURDATE(),1,7)";
                                stock_id = list.get(selectProduct.getSelectionModel().getSelectedIndex()).toString();
                                if (selectProduct.getValue() != null && !selectProduct.getValue().equals("")) {
                                    iDModel = ssp.productAction(sql, purchase_label, SalePrice, net_rate, totalPrice,
                                            quantity, Discount_p, Discount_T, selectProduct, unit, stock_hint, stock_date, 0);
                                    proID = iDModel.getUnitID();
                                    Unitlist = iDModel.getList();
                                    unitID = Unitlist.get(unit.getSelectionModel().getSelectedIndex()).toString();
                                    if (!i.equals(proID)) {
                                        currentSale_id = 0;
                                    }
                                }

                            } catch (Exception e) {
                            }
                        });
                        return null;
                    }
                };
            }
        }.start();

    }

    @FXML
    private void showpress_pro(KeyEvent event) {
        new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            Release_product(event);
                        });
                        return null;
                    }
                };
            }
        }.start();
    }

    @FXML
    private void quanSum(KeyEvent event) {
        try {
            double qun = Double.parseDouble(quantity.getText());

            double unitprice = Double.parseDouble(net_rate.getText());
            amount = 0;
            amount = qun * unitprice;
            totalPrice.setText(String.valueOf(amount));

        } catch (Exception e) {

        }
    }

    @FXML
    private void disSumP(KeyEvent event) {
        try {
            double price = Double.parseDouble(SalePrice.getText());
            double percent;
            if (Discount_p.getText() == null || Discount_p.getText().equals("")) {
                percent = 0;
            } else {
                percent = Double.parseDouble(Discount_p.getText());
            }
            double sum = price / 100;
            double net_price = sum * percent;
            Discount_T.setText(String.valueOf(net_price));
            double net_unit = price - net_price;
            net_rate.setText(String.valueOf(net_unit));
            totalPrice.setText(String.valueOf(Double.parseDouble(net_rate.getText()) * Double.parseDouble(quantity.getText())));
        } catch (Exception e) {
        }

    }

    @FXML
    private void disSumT(KeyEvent event) {
        try {
            double price = Double.parseDouble(SalePrice.getText());
            double taka;
            if (Discount_T.getText() == null || Discount_T.getText().equals("")) {
                taka = 0;
            } else {
                taka = Double.parseDouble(Discount_T.getText());
            }
            double sum = price / 100;
            double net_per = taka / sum;
            Discount_p.setText(String.valueOf(net_per));
            double net_unit = price - taka;
            net_rate.setText(String.valueOf(net_unit));
            totalPrice.setText(String.valueOf(Double.parseDouble(net_rate.getText()) * Double.parseDouble(quantity.getText())));
        } catch (Exception e) {
        }

    }

    @FXML
    private void Addbtn(ActionEvent event) {
        new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            Current_sale_Add();
                        });
                        return null;
                    }
                };
            }
        }.start();
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
            double total = 0;
            total = net_amount - paidamount;

            Due.setText(String.valueOf(total));

        } catch (Exception e) {
        }
    }

    @FXML
    private void Submitbtn(ActionEvent event) {
        new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            try {
                                if (net_dis.getText() == null || paidAmount.getText() == null
                                        || Due.getText() == null || netTotal.getText() == null
                                        || totalAmount.getText() == null || vat.getText() == null) {
                                    queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Empty value");
                                    return;
                                }
                                if (new_customer_check.isSelected()) {
                                    String sql = "INSERT INTO `customer_info`(`customer_name`,`customer_phone`"
                                            + ")VALUES('" + Selectcus.getValue() + "','" + SelectPhone.getText() + "')";
                                    queryFunction.InsertCustomise(sql, "Have a Problem in customer insert");
                                    customerID = queryFunction.getDataInSVeriable("SELECT MAX(id) AS 'id' FROM customer_info", "id");

                                } else if (walking_customer.isSelected()) {
                                    customerID = ssp.customer_gen("General Customer", SelectPhone, new_customer_check);
                                    if (customerID == null) {
                                        String sql = "INSERT INTO `customer_info`(`customer_name`"
                                                + ")VALUES('General Customer')";
                                        queryFunction.InsertCustomise(sql, "Have a Problem in customer insert");
                                        customerID = ssp.customer_gen("General Customer", SelectPhone, new_customer_check);
                                        Selectcus.setValue("General Customer");
                                    } else {
                                        Selectcus.setValue("General Customer");
                                    }
                                }

                            } catch (Exception e) {
                            }
                            SalesEntry();
                            Sales_Ledger(customerID);
                            substraction_product();
                            peymentStatement();
                            CusotmerTransaction(customerID);
                            presenter.vatEntry(queryFunction.service.getnonFormatCurrentDate(), invoice,
                                    "0", vat.getText(), String.valueOf(uid));
                            Notify();
                            Sales_InfoController si = new Sales_InfoController();
                            if (check_invoice.isSelected()) {
                                si.report(invoice);
                            }
                            if (check_invoice1.isSelected()) {
                                si.PosReport(invoice);
                            }
                            autoClearList();
                            view();
                            clear2();
                            autoId();
                        });
                        return null;
                    }
                };
            }
        }.start();
    }

    @FXML
    private void Allclearbtn(ActionEvent event) {
        new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            clear2();
                            autoId();
                        });
                        return null;
                    }
                };
            }
        }.start();
    }

    @FXML
    private void clearShopCardbtn(ActionEvent event) {
        new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            autoClearList();
                            view();
                        });
                        return null;
                    }
                };
            }
        }.start();
    }

    @FXML
    private void UnitAction(ActionEvent event) {
//        unitID = ssp.UnitAction(unit, measurement_type);
        try {
            unitID = Unitlist.get(unit.getSelectionModel().getSelectedIndex()).toString();
        } catch (Exception e) {
        }

    }

    @FXML
    private void netDIsReleased(KeyEvent event) {
        try {

            double totalamount = Double.parseDouble(netTotal.getText());
            double dis_amount;
            if (net_dis.getText() == null || net_dis.getText().equals("")) {
                dis_amount = 0;
            } else {
                dis_amount = Double.parseDouble(net_dis.getText());
            }

            net_amount = totalamount - dis_amount;
            paidAmount.setText("0");
            Due.setText(String.valueOf(net_amount));

        } catch (Exception e) {

        }
    }

    @FXML
    private void NewCheck(ActionEvent event) {
        walking_customer.setSelected(false);
        if (Selectcus.getValue() == "General Customer") {
            Selectcus.setValue(null);
        }
    }

    @FXML
    private void WalkCheck(ActionEvent event) {
        new_customer_check.setSelected(false);
        Selectcus.setValue("General Customer");
        SelectPhone.setText(null);
    }

    @FXML
    private void SaleSum(KeyEvent event) {
        try {
            double qun = Double.parseDouble(quantity.getText());

            double unitprice = Double.parseDouble(SalePrice.getText());
            amount = 0;
            amount = qun * unitprice;
            totalPrice.setText(String.valueOf(amount));
            net_rate.setText(String.valueOf(unitprice));

        } catch (Exception e) {

        }
    }

}

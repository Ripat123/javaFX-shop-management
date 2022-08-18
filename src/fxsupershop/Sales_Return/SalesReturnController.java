package fxsupershop.Sales_Return;

import com.jfoenix.controls.*;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.SalesReturnView;
import java.awt.HeadlessException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class SalesReturnController implements Initializable {

    @FXML
    private ScrollPane scrollID;
    @FXML
    private JFXTextField SelectPhone;
    @FXML
    private JFXComboBox Selectcustomer;
    @FXML
    private JFXTextField returnID;
    @FXML
    private JFXTextField QuantityID;
    @FXML
    private JFXComboBox unitID;
    @FXML
    private JFXComboBox proID;
    @FXML
    private JFXTextField barcodeID;
    @FXML
    private JFXTextField totalamountiD;
    @FXML
    private JFXTextField discountID;
    @FXML
    private JFXTextField net_totalID;
//    @FXML
//    private JFXButton submitbtn;
//    @FXML
//    private JFXButton cartbtn;
    @FXML
    private TableView<SalesReturnView> tableview;
    @FXML
    private JFXComboBox Select_type;
    GeneralService generalService = new GeneralService();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    Report report = new Report();
    Message msg = new Message();

    @FXML
    private JFXComboBox text_invoice_id;
    ResultSet rs, rss;
    private double price;
    private double quntity, purchase_price, totalAmount, per_picePrice;
    @FXML
    private JFXDatePicker currentDate;
    @FXML
    private TableColumn<?, ?> col_ReID;
    @FXML
    private TableColumn<?, ?> col_SInvoice;
    @FXML
    private TableColumn<?, ?> col_date;
    @FXML
    private TableColumn<?, ?> col_proName;
    @FXML
    private TableColumn<?, ?> col_quantity;
    @FXML
    private TableColumn<?, ?> col_total;
    @FXML
    private TableColumn<?, ?> col_type;
    @FXML
    private TableColumn<?, ?> col_dis;
    @FXML
    private TableColumn<?, ?> col_netTotal;
    private String pro_ID;
    private int unit_id;
    LoginMultiFormController loginMultiFormController = new LoginMultiFormController();
    private static int userId;
    ObservableList<SalesReturnView> data = FXCollections.observableArrayList();
    ObservableList customerList = FXCollections.observableArrayList();
    ObservableList Prolist = FXCollections.observableArrayList();
    String customerID;
    double perQuanVat, perQuanDis, total;
    @FXML
    private Label paid_label;
    @FXML
    private Label due_label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Select_type.getItems().addAll("Return", "Damage");
        customer();
        autoid();
        userId = loginMultiFormController.User();
        view();
        JFXScrollPane.smoothScrolling(scrollID);
    }

    private void customer_Rel(KeyEvent event) {
        String sql = "SELECT customer_info.customer_name,customer_info.id FROM "
                + "customer_info WHERE customer_name LIKE '%" + Selectcustomer.getValue() + "%'";
        customerList = queryFunction.ShowArrayItemKeyReleased(sql, "customer_name", "id", Selectcustomer, event, customerList);
    }

    private void customer() {
        String sql = "SELECT customer_info.customer_name,customer_info.id FROM customer_info";
        customerList = queryFunction.ViewArrayJFXComboBox(sql, "customer_name", "id", Selectcustomer, customerList);
    }

    private void customer_Ac() {
        try {
            customerID = customerList.get(Selectcustomer.getSelectionModel().getSelectedIndex()).toString();
            String sql = "SELECT customer_info.* FROM customer_info WHERE customer_info.id = '" + customerID + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                SelectPhone.setText(rs.getString("customer_phone"));
                salesInvoice();
            }
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private void autoid() {
        int id = queryFunction.AutoJFXID("sale_return","id");
        String front_tag_ofID = "SR-";
        if (id == 0) {
            id++;
            returnID.setText(front_tag_ofID + "0000" + "" + Integer.toString(id));
            return;
        } else if (id <= 9) {
            returnID.setText(front_tag_ofID + "0000" + "" + Integer.toString(id));
            return;
        } else if (id <= 99) {
            returnID.setText(front_tag_ofID + "000" + "" + Integer.toString(id));
            return;
        } else if (id <= 999) {
            returnID.setText(front_tag_ofID + "00" + "" + Integer.toString(id));
            return;
        } else if (id <= 9999) {
            returnID.setText(front_tag_ofID + "0" + "" + Integer.toString(id));
            return;
        } else if (id <= 99999) {
            returnID.setText(front_tag_ofID + "" + Integer.toString(id));
            return;
        }
        if (returnID.getText().equals("")) {
            returnID.setText(front_tag_ofID + "01");
        }
    }

    private void unit(int id) {
        String sql = "SELECT * FROM product_measurement_subunit WHERE id = '" + id + "'";
        queryFunction.ViewItemOnJFXComboBox(sql, "sub_unit_name", unitID);
    }

    private void salesInvoice() {
        String sql = "SELECT * FROM sale_ledger WHERE sale_ledger.customer_id = '" + customerID + "'";
        queryFunction.ViewItemOnJFXComboBox(sql, "invoice_id", text_invoice_id);
    }

    private void invoiceSelection() {
        double vat = 0, dis;
        int proquan = 0;
        try {
            String sql1 = "SELECT sale_entry.*,product_productinfo.*,sale_payment_statement.*"
                    + " FROM sale_entry,product_productinfo INNER JOIN sale_payment_statement"
                    + " ON sale_payment_statement.invoice_no = '" + text_invoice_id.getValue() + "' WHERE "
                    + "sale_entry.invoice_id='" + text_invoice_id.getValue() + "' AND "
                    + "sale_entry.product_id=product_productinfo.id";
            Prolist = queryFunction.ViewArrayJFXComboBox(sql1, "product_productinfo.product_name",
                    "product_productinfo.id", proID, Prolist);
            rs = queryFunction.getResult(sql1);
            if (rs.next()) {
                vat = Double.parseDouble(rs.getString("sale_payment_statement.vat"));
                dis = Double.parseDouble(rs.getString("sale_payment_statement.discount"));
                String invoice = rs.getString("sale_entry.invoice_id");
                String sql = "SELECT SUM(product_quantity) AS 'quan' FROM sale_entry"
                        + " WHERE invoice_id = '" + invoice + "'";
                proquan = queryFunction.getDataIniVeriable(sql, "quan");
                perQuanVat = vat / proquan;
                perQuanDis = dis / proquan;
                paid_label.setText(rs.getString("payment"));
                due_label.setText(rs.getString("due"));
            }
        } catch (Exception e) {
//            msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem.\n" + e);
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {

            }
        }
    }

    private void productSelection() {
        try {
            pro_ID = Prolist.get(proID.getSelectionModel().getSelectedIndex()).toString();
            String sql = "SELECT sale_entry.*,product_measurement_subunit.*,sale_payment_statement.* FROM "
                    + "sale_entry,product_measurement_subunit,sale_payment_statement "
                    + "WHERE sale_entry.invoice_id='" + text_invoice_id.getValue() + "' AND sale_payment_statement.invoice_no = '" + text_invoice_id.getValue() + "' "
                    + "AND sale_entry.product_id='" + pro_ID + "' AND sale_entry."
                    + "sub_unit_id=product_measurement_subunit.id";

            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                unit_id = Integer.parseInt(rs.getString("product_measurement_subunit.id"));
                QuantityID.setText(rs.getString("product_quantity"));
                quntity = Double.parseDouble(rs.getString("product_quantity"));
                totalAmount = Double.parseDouble(rs.getString("product_totalsaleprice"));
                total = totalAmount;
                totalAmount = totalAmount + (perQuanVat * quntity);
                totalAmount = totalAmount - (perQuanDis * quntity);
                totalamountiD.setText(String.valueOf(totalAmount));
                per_picePrice = totalAmount / quntity;
                price = Double.parseDouble(rs.getString("product_saleprice"));
                purchase_price = Double.parseDouble(rs.getString("product_purchaseprice"));
                unit(unit_id);
                unitID.setValue(rs.getString("sub_unit_name"));
            }

        } catch (Exception e) {
//            msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem.\n" + e);
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {

            }
        }
    }

    private double amountGenerate(double quan) {
        double to1, to2 = 0;
        if (quan == 1) {
            to1 = total / quntity;
            to2 = to1 + (perQuanVat * quan);
            to2 = to1 - (perQuanDis * quan);
        } else {
            to1 = total + (perQuanVat * quan);
            to1 = total - (perQuanDis * quan);
            to2 = to1 / quntity;
        }
        return to2;
    }

    private void clear() {
        returnID.setText(null);
        text_invoice_id.setValue(null);
        Selectcustomer.setValue(null);
        SelectPhone.setText(null);
        currentDate.setValue(null);
        barcodeID.setText(null);
        proID.setValue(null);
        proID.getItems().clear();
        unitID.setValue(null);
        QuantityID.setText(null);
        totalamountiD.setText(null);
        Select_type.setValue(null);
        discountID.setText(null);
        net_totalID.setText(null);
        Selectcustomer.requestFocus();
    }

    private void insert() {
        String sql = "INSERT INTO sale_return (Return_id,sale_invoice,cus_id,date,pro_id,"
                + "unit,type,qun,amount,discount,total_amount,admin_id) VALUES("
                + "'" + returnID.getText().trim() + "','" + text_invoice_id.getValue() + ""
                + "','" + customerID + "','" + currentDate.getValue() + "'"
                + ",'" + pro_ID + "','" + unit_id + "',"
                + "'" + Select_type.getValue() + "','" + QuantityID.getText().trim() + "',"
                + "'" + totalamountiD.getText().trim() + "','" + discountID.getText().trim() + ""
                + "','" + net_totalID.getText().trim() + "','" + userId + "')";
        queryFunction.InsertCustomise(sql, "Have a Problem.");
    }

    private void submit() {
        try {
            if (Select_type.getValue().equals("Return")) {

                String sql = "SELECT * FROM stock_product WHERE stock_product.product_id IN ('" + pro_ID + "')";
                rs = queryFunction.getResult(sql);
                if (rs.next()) {
                    String sql1 = "UPDATE stock_product SET stock_product.quantity = stock_product.quantity + "
                            + "'" + QuantityID.getText().trim() + "' WHERE stock_product.product_id='" + pro_ID + "'";
                    queryFunction.UpdateMessageLess(sql1);
                } else {
                    String query = "INSERT INTO `stock_product`(`stock_product`.`product_id`,`stock_product`.`quantity`,"
                            + "`stock_product`.`purchase_price`,`stock_product`.`sale_price`,`stock_product`."
                            + "`old_and_new_purchase_price_average`) VALUES ('" + pro_ID + "','"
                            + "" + QuantityID.getText().trim() + "','" + purchase_price + "','" + price + "','" + purchase_price + "')";
                    queryFunction.InsertCustomise(query, "Have a Problem in submit insert.");
                }
            } else if (Select_type.getValue().equals("Damage")) {
                String sql = "INSERT INTO damage_stock (fk_pro_id,unit,damage_qunt,"
                        + "date,fk_admin_id,cus_id) VALUES('" + pro_ID + "','" + unit_id + "',"
                        + "'" + QuantityID.getText().trim() + "','" + currentDate.getValue() + ""
                        + "','" + userId + "','" + customerID + "')";
                queryFunction.InsertCustomise(sql, "Have a Problem in damage stock insert.");
            }
        } catch (SQLException | HeadlessException e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem in Total Submit." + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    private void reduceProduct() {
        try {
            if (quntity == Double.parseDouble(QuantityID.getText())) {
                String sql = "DELETE FROM sale_entry WHERE sale_entry.invoice_id='" + text_invoice_id.getValue() + "' "
                        + "AND sale_entry.product_id='" + pro_ID + "'";
                queryFunction.DeleteConditionLess(sql);
            } else if (quntity > Double.parseDouble(QuantityID.getText())) {
                String sql = "UPDATE sale_entry SET product_quantity = product_quantity - '"
                        + "" + QuantityID.getText() + "',product_totalsaleprice = product_totalsaleprice -"
                        + " '" + totalamountiD.getText() + "',updated_at = '" + generalService.getDateTime() + "'"
                        + " WHERE sale_entry.invoice_id='" + text_invoice_id.getValue() + "' "
                        + "AND sale_entry.product_id='" + pro_ID + "'";
                queryFunction.UpdateMessageLess(sql);
            }
            String sql1 = "SELECT sale_entry.* FROM sale_entry WHERE sale_entry."
                    + "invoice_id='" + text_invoice_id.getValue() + "'";
            rs = queryFunction.getResult(sql1);
            if (rs.next()) {
                String sql = "UPDATE sale_ledger SET updated_at = '" + generalService.getDateTime() + "' "
                        + "WHERE sale_ledger.invoice_id = '" + text_invoice_id.getValue() + "'";
                queryFunction.UpdateMessageLess(sql);
            } else {
                String sql = "DELETE FROM sale_ledger WHERE invoice_id = '" + text_invoice_id.getValue() + "'";
                queryFunction.DeleteConditionLess(sql);
            }

        } catch (Exception e) {
        }
    }

    private void Transaction() {
        double returnA = Double.parseDouble(net_totalID.getText());
        double disA = Double.parseDouble(discountID.getText());
        double returnAmount = returnA + disA;
        double ret_am = returnAmount;
        try {
            if (returnAmount > 0) {
                String sql1 = "SELECT * FROM sale_payment_statement WHERE invoice_no = '" + text_invoice_id.getValue() + "'";
                rs = queryFunction.getResult(sql1);
                if (rs.next()) {
                    double due = Double.parseDouble(rs.getString("due"));
                    double netDue = due - returnAmount;
                    if (String.valueOf(netDue).contains("-")) {
                        String p = String.valueOf(netDue).replace("-", "");
                        double pay = returnAmount - Double.parseDouble(p);
                        returnAmount = Double.parseDouble(p);

                        String sql2 = "UPDATE sale_payment_statement SET total_ammount = total_ammount - '" + ret_am + "', payment = payment - '" + returnAmount + "',"
                                + "due = '0' WHERE invoice_no = '" + rs.getString("invoice_no") + "'";
                        queryFunction.UpdateMessageLess(sql2);
                    } else {
                        String sql2 = "UPDATE sale_payment_statement SET total_ammount = total_ammount - '" + ret_am + "', "
                                + "due = '" + netDue + "' WHERE invoice_no = '" + rs.getString("invoice_no") + "'";
                        queryFunction.UpdateMessageLess(sql2);
                    }
                }
                String sql = "SELECT customer_transaction.* FROM customer_transaction "
                        + "WHERE customer_id = '" + customerID + "' ORDER BY id DESC";
                rs = queryFunction.getResult(sql);
                if (rs.next()) {
                    double bal = Double.parseDouble(rs.getString("balance"));
                    if (bal > 0) {

                        if (bal <= ret_am) {
                            String sql2 = "INSERT INTO customer_transaction (customer_id,"
                                    + "transaction_date,transaction_type,balance)VALUES('" + customerID + "',"
                                    + "'" + queryFunction.service.getnonFormatCurrentDate() + "','Return','0')";
                            queryFunction.InsertCustomise(sql2, "Have a Problem in Transaction");
                        } else {
                            double due = bal - ret_am;
                            String sql2 = "INSERT INTO customer_transaction (customer_id,"
                                    + "transaction_date,transaction_type,balance)VALUES('" + customerID + "',"
                                    + "'" + queryFunction.service.getnonFormatCurrentDate() + "','Return','" + due + "')";
                            queryFunction.InsertCustomise(sql2, "Have a Problem in Transaction");
                        }
                    }
                }
                String sql3 = "SELECT sale_payment_statement.* FROM sale_payment_statement "
                        + "WHERE invoice_no = '" + text_invoice_id.getValue() + "' AND payment = '0' AND due = '0'";
                rs = queryFunction.getResult(sql3);
                if (rs.next()) {
                    String sql4 = "DELETE FROM sale_payment_statement WHERE invoice_no = '" + rs.getString("invoice_no") + "'";
                    queryFunction.DeleteConditionLess(sql4);
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
    }

    private void view() {
        try {
            data.clear();
            col_ReID.setCellValueFactory(new PropertyValueFactory<>("returnid"));
            col_SInvoice.setCellValueFactory(new PropertyValueFactory<>("invoiceNO"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_proName.setCellValueFactory(new PropertyValueFactory<>("pro_name"));
            col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
            col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
            col_dis.setCellValueFactory(new PropertyValueFactory<>("discount"));
            col_netTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));

            String sql = "SELECT * FROM sale_return,product_productinfo WHERE sale_return."
                    + "pro_id=product_productinfo.id";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                data.add(new SalesReturnView(rs.getString("Return_id"),
                        rs.getString("sale_invoice"),
                        rs.getString("date"),
                        rs.getString("product_name"),
                        rs.getString("qun"),
                        rs.getString("amount"),
                        rs.getString("type"),
                        rs.getString("discount"),
                        rs.getString("total_amount")
                ));
            }
            tableview.setItems(data);
        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem.\n" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    @FXML
    private void InvoiceAction(ActionEvent event) {
        invoiceSelection();
    }

    @FXML
    private void UnitAction(ActionEvent event) {
    }

    @FXML
    private void productAction(ActionEvent event) {
        productSelection();
    }

    @FXML
    private void Submitbtn(ActionEvent event) {
        try {
            insert();
            submit();
            reduceProduct();
            Transaction();
            clear();
            autoid();
            view();
        } catch (Exception e) {

        }
    }

    @FXML
    private void clearShopCardbtn(ActionEvent event) {
        clear();
        autoid();
    }

    @FXML
    private void TableClick(MouseEvent event) {
    }

    @FXML
    private void quantityReleased(KeyEvent event) {
        try {
            double Total, perT;
            double textquan = Double.parseDouble(QuantityID.getText());

            if (textquan <= quntity) {
                perT = amountGenerate(textquan);
                Total = textquan * perT;
                totalamountiD.setText(String.valueOf(Total));
            } else {
                msg.WarningMessage("Unsuccessful", "Warning", "Up to sales quantity.");
                textquan = quntity;
                perT = amountGenerate(textquan);
                QuantityID.setText(String.valueOf(quntity));
                Total = textquan * perT;
                totalamountiD.setText(String.valueOf(Total));
            }
        } catch (Exception e) {

        }
    }

    @FXML
    private void discountReleased(KeyEvent event) {
        try {
            double total = Double.parseDouble(totalamountiD.getText());
            double discount = Double.parseDouble(discountID.getText());
            double netTotal = total - discount;
            net_totalID.setText(String.valueOf(netTotal));
        } catch (Exception e) {

        }
    }

    @FXML
    private void invoiceReleased(KeyEvent event) {
    }

    @FXML
    private void BarcodeChanged(InputMethodEvent event) {
    }

    @FXML
    private void BarcodeChanged(KeyEvent event) {
    }

    @FXML
    private void SelectTypeAction(ActionEvent event) {
    }

    @FXML
    private void customerAction(ActionEvent event) {
        customer_Ac();
    }

    @FXML
    private void customerReleased(KeyEvent event) {
        customer_Rel(event);
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}

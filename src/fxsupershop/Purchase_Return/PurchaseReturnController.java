package fxsupershop.Purchase_Return;

import com.jfoenix.controls.*;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.PurchaseReturnView;
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
public class PurchaseReturnController implements Initializable {

    @FXML
    private JFXTextField SelectPhone;
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
    private ScrollPane scrollID;
    @FXML
    private TableView<PurchaseReturnView> tableview;
    @FXML
    private JFXDatePicker currentDate;
    @FXML
    private JFXComboBox Selectsupplier;
    @FXML
    private JFXComboBox<?> text_invoice_id;
    @FXML
    private JFXComboBox Select_type;
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
//    @FXML
//    private TableColumn<?, ?> col_total;
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
    ObservableList<PurchaseReturnView> data = FXCollections.observableArrayList();
    ResultSet rs, rss;
    private double price;
    private double quntity, purchase_price, totalAmount, per_picePrice;
    GeneralService generalService = new GeneralService();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    Report report = new Report();
    Message msg = new Message();
    ObservableList supplierList = FXCollections.observableArrayList();
    ObservableList Prolist = FXCollections.observableArrayList();
    String supplierID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Select_type.getItems().addAll("Return", "Damage");
//        salesInvoice();
//        unit();
        supplier();
        autoid();
        userId = loginMultiFormController.User();
        view();
        JFXScrollPane.smoothScrolling(scrollID);
    }

    private void supplier() {
        String sql = "SELECT suplier_info.company_name,suplier_info.id FROM suplier_info";
        supplierList = queryFunction.ViewArrayJFXComboBox(sql, "company_name", "id", Selectsupplier, supplierList);
    }

    private void supplier_Rel(KeyEvent event) {
        String sql = "SELECT suplier_info.company_name,suplier_info.id FROM "
                + "suplier_info WHERE company_name LIKE '%" + Selectsupplier.getValue() + "%'";
        supplierList = queryFunction.ShowArrayItemKeyReleased(sql, "company_name", "id", Selectsupplier, event, supplierList);
    }

    private void supplier_Ac() {
        try {
            supplierID = supplierList.get(Selectsupplier.getSelectionModel().getSelectedIndex()).toString();
            String sql = "SELECT suplier_info.* FROM suplier_info WHERE suplier_info.id = '" + supplierID + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                SelectPhone.setText(rs.getString("company_phone"));
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
        int id = queryFunction.AutoJFXID("purchase_return_entry","id");
        String front_tag_ofID = "PR-";
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
        String sql = "SELECT * FROM purchase_ledger WHERE purchase_ledger.suplier_id = '" + supplierID + "'";
        queryFunction.ViewItemOnJFXComboBox(sql, "invoice_no", text_invoice_id);
    }

    private void invoiceSelection() {
        try {
            String sql = "SELECT suplier_info.company_name,suplier_info.company_phone"
                    + ",suplier_info.id FROM suplier_info,purchase_ledger WHERE purchase_ledger."
                    + "invoice_no='" + text_invoice_id.getValue() + "' AND purchase_ledger."
                    + "suplier_id=suplier_info.id";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                String sql1 = "SELECT * FROM purchase_entry,product_productinfo WHERE "
                        + "purchase_entry.invoice_no='" + text_invoice_id.getValue() + "' AND "
                        + "purchase_entry.product_id=product_productinfo.id";
                Prolist = queryFunction.ViewArrayJFXComboBox(sql1, "product_productinfo.product_name",
                        "product_productinfo.id", proID, Prolist);

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
            String sql = "SELECT * FROM purchase_entry,product_measurement_subunit "
                    + "WHERE purchase_entry.invoice_no='" + text_invoice_id.getValue() + "' "
                    + "AND purchase_entry.product_id= '" + pro_ID + "' AND purchase_entry."
                    + "sub_unit_id=product_measurement_subunit.id";

            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                unit_id = Integer.parseInt(rs.getString("product_measurement_subunit.id"));
                QuantityID.setText(rs.getString("product_quantity"));
                quntity = Double.parseDouble(rs.getString("product_quantity"));
                totalamountiD.setText(rs.getString("product_totalpurchaseprice"));
                totalAmount = Double.parseDouble(rs.getString("product_totalpurchaseprice"));
                per_picePrice = totalAmount / quntity;
                price = Double.parseDouble(rs.getString("product_unitsaleprice"));
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

    private void clear() {
        returnID.setText(null);
        text_invoice_id.setValue(null);
        Selectsupplier.setValue(null);
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
        Selectsupplier.requestFocus();
    }

    private void insert() {
        String sql = "INSERT INTO purchase_return_entry (purchase_return_id,"
                + "invoice_no,fk_supplier_id,return_date,product_id,"
                + "purchase_sub_unit_id,type,product_quantity,discount,total_amount,admin_id) VALUES("
                + "'" + returnID.getText().trim() + "','" + text_invoice_id.getValue() + ""
                + "','" + supplierID + "','" + currentDate.getValue() + "'"
                + ",'" + pro_ID + "','" + unit_id + "',"
                + "'" + Select_type.getValue() + "','" + QuantityID.getText().trim() + "',"
                + "'" + discountID.getText().trim() + ""
                + "','" + net_totalID.getText().trim() + "','" + userId + "')";
        queryFunction.InsertCustomise(sql, "Have a Problem.");
    }

    private void submit() {
        try {

            String sql = "SELECT * FROM stock_product WHERE stock_product.product_id IN ('" + pro_ID + "')";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                double quntity = Double.parseDouble(rs.getString("quantity"));
                if (quntity > Double.parseDouble(QuantityID.getText())) {
                    String sql1 = "UPDATE stock_product SET stock_product.quantity = stock_product.quantity - "
                            + "'" + QuantityID.getText().trim() + "' WHERE stock_product.product_id='" + pro_ID + "'";
                    queryFunction.UpdateMessageLess(sql1);
                } else if (quntity == Double.parseDouble(QuantityID.getText())) {
                    String sql1 = "DELETE FROM stock_product WHERE stock_product.product_id='" + pro_ID + "'";
                    queryFunction.DeleteConditionLess(sql1);
                }
            } else {
                msg.WarningMessage("Unsuccessful", "Warning", "Don't have the product.");
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
                String sql = "DELETE FROM purchase_entry WHERE purchase_entry.invoice_no='" + text_invoice_id.getValue() + "' "
                        + "AND purchase_entry.product_id='" + pro_ID + "'";
                queryFunction.DeleteConditionLess(sql);
            } else if (quntity > Double.parseDouble(QuantityID.getText())) {
                String sql = "UPDATE purchase_entry SET product_quantity = product_quantity - '"
                        + "" + QuantityID.getText() + "',product_totalpurchaseprice = product_totalpurchaseprice -"
                        + " '" + totalamountiD.getText() + "',updated_at = '" + generalService.getDateTime() + "'"
                        + " WHERE purchase_entry.invoice_no='" + text_invoice_id.getValue() + "' "
                        + "AND purchase_entry.product_id='" + pro_ID + "'";
                queryFunction.UpdateMessageLess(sql);
            }
            String sql3 = "SELECT purchase_entry.* FROM purchase_entry WHERE purchase_entry."
                    + "invoice_no='" + text_invoice_id.getValue() + "'";
            rs = queryFunction.getResult(sql3);
            if (rs.next()) {
                String sql = "UPDATE purchase_ledger SET updated_at = '" + generalService.getDateTime() + "' "
                        + "WHERE purchase_ledger.invoice_no = '" + text_invoice_id.getValue() + "'";
                queryFunction.UpdateMessageLess(sql);
            } else {
                String sql = "DELETE FROM purchase_ledger WHERE invoice_no = '" + text_invoice_id.getValue() + "'";
                queryFunction.DeleteConditionLess(sql);
            }
        } catch (SQLException ex) {
        }
    }

    private void Transaction() {
        double returnA = Double.parseDouble(net_totalID.getText());
        double disA = Double.parseDouble(discountID.getText());
        double returnAmount = returnA + disA;
        double ret_am = returnAmount;
        try {
            if (returnAmount > 0) {
                String sql1 = "SELECT * FROM purchase_payment_statement WHERE invoice_no = '" + text_invoice_id.getValue() + "'";
                rs = queryFunction.getResult(sql1);
                if (rs.next()) {
                    double due = Double.parseDouble(rs.getString("due"));
                    double netDue = due - returnAmount;
                    if (String.valueOf(netDue).contains("-")) {
                        String p = String.valueOf(netDue).replace("-", "");
//                        double pay = returnAmount - Double.parseDouble(p);
                        returnAmount = Double.parseDouble(p);

                        String sql2 = "UPDATE purchase_payment_statement SET total_ammount = "
                                + "total_ammount - '" + ret_am + "', payment = payment - '" + returnAmount + "',"
                                + "due = '0' WHERE invoice_no = '" + rs.getString("invoice_no") + "'";
                        queryFunction.UpdateMessageLess(sql2);
                    } else {
                        String sql2 = "UPDATE purchase_payment_statement SET total_ammount = total_ammount - '" + ret_am + "',"
                                + "due = '" + netDue + "' WHERE invoice_no = '" + rs.getString("invoice_no") + "'";
                        queryFunction.UpdateMessageLess(sql2);
                    }
                }
                String sql = "SELECT suplier_transaction.* FROM suplier_transaction "
                        + "WHERE suplier_id = '" + supplierID + "' ORDER BY id DESC";
                rs = queryFunction.getResult(sql);
                if (rs.next()) {
                    double bal = Double.parseDouble(rs.getString("balance"));
                    if (bal > 0) {

                        if (bal <= ret_am) {
                            String sql2 = "INSERT INTO suplier_transaction (suplier_id,"
                                    + "transaction_date,transaction_type,balance)VALUES('" + supplierID + "',"
                                    + "'" + queryFunction.service.getnonFormatCurrentDate() + "','Return','0')";
                            queryFunction.InsertCustomise(sql2, "Have a Problem in Transaction");
                        } else {
                            double due = bal - ret_am;
                            String sql2 = "INSERT INTO suplier_transaction (suplier_id,"
                                    + "transaction_date,transaction_type,balance)VALUES('" + supplierID + "',"
                                    + "'" + queryFunction.service.getnonFormatCurrentDate() + "','Return','" + due + "')";
                            queryFunction.InsertCustomise(sql2, "Have a Problem in Transaction");
                        }
                    }
                }
                String sql3 = "SELECT purchase_payment_statement.* FROM purchase_payment_statement "
                        + "WHERE invoice_no = '" + text_invoice_id.getValue() + "' AND payment = '0' AND due = '0'";
                rs = queryFunction.getResult(sql3);
                if (rs.next()) {
                    String sql4 = "DELETE FROM purchase_payment_statement WHERE invoice_no = '" + rs.getString("invoice_no") + "'";
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
            col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
            col_dis.setCellValueFactory(new PropertyValueFactory<>("discount"));
            col_netTotal.setCellValueFactory(new PropertyValueFactory<>("netTotal"));

            String sql = "SELECT * FROM purchase_return_entry,product_productinfo WHERE purchase_return_entry."
                    + "product_id=product_productinfo.id";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                data.add(new PurchaseReturnView(rs.getString("purchase_return_id"),
                        rs.getString("invoice_no"),
                        rs.getString("return_date"),
                        rs.getString("product_name"),
                        rs.getString("product_quantity"),
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
    private void invoiceReleased(KeyEvent event) {
    }

    @FXML
    private void quantityReleased(KeyEvent event) {
        try {
            double total;
            double textquan = Double.parseDouble(QuantityID.getText());
            if (textquan <= quntity) {
                total = textquan * per_picePrice;
                totalamountiD.setText(String.valueOf(total));
            } else {
                msg.WarningMessage("Unsuccessful", "Warning", "Up to Purchase quantity.");
                textquan = quntity;
                QuantityID.setText(String.valueOf(quntity));
                total = textquan * per_picePrice;
                totalamountiD.setText(String.valueOf(total));
            }
        } catch (Exception e) {

        }
    }

    @FXML
    private void BarcodeChanged(InputMethodEvent event) {
    }

    @FXML
    private void BarcodeChanged(KeyEvent event) {
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
    private void SelectTypeAction(ActionEvent event) {
    }

    @FXML
    private void supplierAction(ActionEvent event) {
        supplier_Ac();
    }

    @FXML
    private void SupplierReleased(KeyEvent event) {
        supplier_Rel(event);
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }

}

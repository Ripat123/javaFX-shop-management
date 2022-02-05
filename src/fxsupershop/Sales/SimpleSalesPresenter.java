package fxsupershop.Sales;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import java.sql.ResultSet;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.input.*;

/**
 *
 * @author PC
 */
public class SimpleSalesPresenter {

    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    private String id, proid, unitID, invoice;
    private ResultSet rs, rs1;
    private int auto_id;
    ObservableList list = FXCollections.observableArrayList();
    ObservableList unitlist = FXCollections.observableArrayList();
    ObservableList customerlist = FXCollections.observableArrayList();

    public SimpleSalesPresenter() {
        autoId();
    }

    public ObservableList customerView(JFXComboBox box) {
        String sql = "SELECT customer_name,id FROM customer_info";
//        queryFunction.ViewItemOnComboBox(sql, "customer_name", box);
        customerlist = queryFunction.ViewArrayJFXComboBox(sql, "customer_name", "id", box, customerlist);
        return customerlist;
    }

    public String customer_gen(String box, JFXTextField textField, JFXCheckBox checkBox) {
        try {
            String sql = "SELECT customer_phone,id FROM customer_info WHERE customer_name='" + box + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                textField.setText(rs.getString("customer_phone"));
                id = rs.getString("id");
                if (checkBox.isSelected()) {
                    checkBox.setSelected(false);
                }
            }
        } catch (Exception e) {
            queryFunction.service.msg.ErrorMessage("Unsuccessful", "Error", "Have a Error!\n" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
        return id;
    }

    public String customerAction(String box, JFXTextField textField, JFXCheckBox checkBox) {
        try {
            String sql = "SELECT customer_phone,id FROM customer_info WHERE id='" + box + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                textField.setText(rs.getString("customer_phone"));
                id = rs.getString("id");
                if (checkBox.isSelected()) {
                    checkBox.setSelected(false);
                }
            }
        } catch (Exception e) {
            queryFunction.service.msg.ErrorMessage("Unsuccessful", "Error", "Have a Error!\n" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
        return id;
    }

    public IDModel productAction(String query, Label label, JFXTextField textField2,
            JFXTextField textField3, JFXTextField textField33, JFXTextField textField4, JFXTextField textField5,
            JFXTextField textField55, JFXComboBox box1, JFXComboBox box2, Label l, Label l1, int condition) {
        String product = null;
        try {
            String sql = query;
            rs1 = queryFunction.getResult(sql);
            if (rs1.next()) {
                label.setText(rs1.getString("stock_product.purchase_price"));
                textField2.setText(rs1.getString("stock_product.sale_price"));
                textField3.setText(rs1.getString("stock_product.sale_price"));
                textField33.setText(rs1.getString("stock_product.sale_price"));
                product = rs1.getString("stock_product.product_id");

                textField4.setText("1");
                textField5.setText("0");
                textField55.setText("0");
                if (condition == 1) {
                    box1.setValue(rs1.getString("product_name"));
                }
                try {
                    l.setText("Stocked Quantity : " + rs1.getDouble("stock_product.quantity"));
                } catch (Exception e) {
                }
                try {
                    unitlist = UnitView(box2, rs1.getString("product_productinfo.measurement_type"));
                } catch (Exception e) {
                }

                box2.setValue(rs1.getString("sub_unit_name"));
//                unitID = rs1.getString("product_measurement_subunit.id");
                l1.setText(rs1.getString("stock_product.expired_date"));
            } else {
                label.setText("");
                textField2.setText("");
                textField3.setText("");
                textField33.setText("");
                textField4.setText("");
                textField5.setText("");
                textField55.setText("");
                box1.setValue("");
                l.setText("");
                box2.setValue("");
                l1.setText("Date Expired!");
//                queryFunction.service.msg.ErrorMessage("Unsuccessful", "Expired", "Date Expired!");
            }
        } catch (Exception e) {
            queryFunction.service.msg.ErrorMessage("Unsuccessful", "Error", "Have a Error!\n" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs1.close();
            } catch (Exception e) {

            }
        }
        return new IDModel(proid, product, unitlist);
    }

    public ObservableList ProductView(JFXComboBox box) {
        try {
            list.clear();
            box.getItems().clear();
            String sql = "SELECT product_productinfo.product_name,stock_product.* FROM "
                    + "stock_product INNER JOIN product_productinfo ON stock_product.product_id = product_productinfo.id";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                list.addAll(rs.getString("stock_product.id"));
                box.getItems().addAll(rs.getString("product_name") + " (" + rs.getString("stock_product.expired_date") + ")");

            }
        } catch (Exception e) {
        }
        return list;
    }

    public ObservableList ShowArrayItemKeyReleased(String query, String query_column_name, String ID_column_name, String date, ComboBox combo_box, KeyEvent event, ObservableList list) {
        try {
            if (event.getCode() != KeyCode.UP && event.getCode() != KeyCode.DOWN
                    && event.getCode() != KeyCode.RIGHT && event.getCode()
                    != KeyCode.LEFT && event.getCode() != KeyCode.ENTER) {

                combo_box.show();
                combo_box.getItems().clear();
                list.clear();
                String sql = query;
                rs = queryFunction.getResult(sql);
                while (rs.next()) {
                    list.addAll(rs.getString(ID_column_name));
                    combo_box.getItems().addAll(rs.getString(query_column_name) + " (" + rs.getString(date) + ")");
                }
            }

        } catch (Exception e) {

            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem!\n" + e);

        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
        return list;
    }

    public ObservableList UnitView(JFXComboBox box, String measurID) {
        String sql = "SELECT product_measurement_subunit.* FROM "
                + "product_measurement_subunit WHERE product_measurement_subunit."
                + "measurement_unit_id='" + measurID + "'";
        unitlist = queryFunction.ViewArrayJFXComboBox(sql, "sub_unit_name", "id", box, unitlist);
        return unitlist;
    }

    public String UnitAction(JFXComboBox box, String measurID) {
        String sql = "SELECT id FROM product_measurement_subunit WHERE sub_unit_name="
                + "'" + box.getValue() + "' AND measurement_unit_id='" + measurID + "'";
        unitID = null;
        unitID = queryFunction.getDataInSVeriable(sql, "id");
        return unitID;
    }

    public int totalitem() {
        String sql = "SELECT COUNT(id) AS 'TotalItem' FROM sale_currentsale";
        String totalitem = queryFunction.getSUMValue(sql, "TotalItem");
        auto_id = 0;
        auto_id = Integer.parseInt(totalitem);
        auto_id = auto_id + 1;
        return auto_id;
    }

    public String totali() {
        String sql = "SELECT COUNT(id) AS 'TotalItem' FROM sale_currentsale";
        String totalitem = queryFunction.getSUMValue(sql, "TotalItem");
        return totalitem;
    }

    public double totalpurchaseprice() {
        String sql = "SELECT SUM(product_purchaseprice) AS 'TotalPurchasePrice' FROM sale_currentsale";
        double total_purchaseprice = Double.parseDouble(queryFunction.getSUMValue(sql, "TotalPurchasePrice"));
        return total_purchaseprice;
    }

    public double totalSaleprice() {
        String sql = "SELECT SUM(product_saleprice) AS 'TotalSalePrice' FROM sale_currentsale";
        double total_saleprice = Double.parseDouble(queryFunction.getSUMValue(sql, "TotalSalePrice"));
        return total_saleprice;
    }

    public double totaldiscount() {
        String sql = "SELECT SUM(product_discount) AS 'TotalDiscount' FROM sale_currentsale";
        double totaldiscount = Double.parseDouble(queryFunction.getSUMValue(sql, "TotalDiscount"));
        return totaldiscount;
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

    public double vat() {
        double vat = 0;
        try {
            String sql = "SELECT * FROM vat order by id desc";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                vat = Double.parseDouble(rs.getString("vat_parcentage"));
            }
        } catch (Exception e) {
        }

        return vat;
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}

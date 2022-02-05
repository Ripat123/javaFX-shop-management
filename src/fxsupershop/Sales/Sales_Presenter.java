package fxsupershop.Sales;

import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.PrepareQueryFunction;
import java.sql.*;

/**
 *
 * @author Ripat Rabbi
 */
public class Sales_Presenter {

    private static String quantity, proID, invoice_id, sub_unit_Id, purchase_price, total_purchase_price, sale_price;
    LoginMultiFormController lmfc = new LoginMultiFormController();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    private int ID;
    ResultSet rs;

    public Sales_Presenter() {
        ID = lmfc.User();
        autoId();
    }

    public static void SalesWithPurchaseData(String quantity, String proID,
            String sub_unit_Id, String purchase_price, String total_purchase_price, String sale_price) {
        Sales_Presenter.proID = proID;
        Sales_Presenter.quantity = quantity;
        Sales_Presenter.sub_unit_Id = sub_unit_Id;
        Sales_Presenter.purchase_price = purchase_price;
        Sales_Presenter.total_purchase_price = total_purchase_price;
        Sales_Presenter.sale_price = sale_price;

    }

    public void autoId() {
        int id = queryFunction.AutoJFXID("purchase_ledger");
        String prefix = "PI-";
        if (id <= 9) {
            invoice_id = prefix + "000000" + "" + Integer.toString(id);
            return;
        } else if (id <= 99) {
            invoice_id = prefix + "00000" + "" + Integer.toString(id);
            return;
        } else if (id <= 999) {
            invoice_id = prefix + "0000" + "" + Integer.toString(id);
            return;
        } else if (id <= 9999) {
            invoice_id = prefix + "000" + "" + Integer.toString(id);
            return;
        } else if (id <= 99999) {
            invoice_id = prefix + "00" + "" + Integer.toString(id);
            return;
        } else if (id <= 999999) {
            invoice_id = prefix + "0" + "" + Integer.toString(id);
            return;
        } else if (id <= 9999999) {
            invoice_id = prefix + "" + Integer.toString(id);
            return;
        }

        if (invoice_id.equals("")) {
            invoice_id = prefix + "0000001";
        }
    }

    public void salesWithPurchaseEntry() {
        autoId();
        try {
            String sqll = "INSERT INTO `purchase_entry`(`invoice_no`,"
                    + "`product_id`,sub_unit_id,`product_quantity`,`product_purchaseprice`,"
                    + "`product_totalpurchaseprice`,`product_unitsaleprice`,"
                    + "`Session_ID`,`admin`) VALUES "
                    + "('" + invoice_id + "','" + proID + "'"
                    + ",'" + sub_unit_Id + "','" + quantity + "',"
                    + "'" + purchase_price + "',"
                    + "'" + total_purchase_price + "',"
                    + "'" + sale_price + "',"
                    + "'" + invoice_id + "','" + ID + "')";
            queryFunction.InsertCustomise(sqll, "Have a Problem in Entry.");
        } catch (Exception e) {
        } finally {
            try {
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private String SupID() {
        String id = null;
        try {
            String sql = "SELECT id FROM suplier_info WHERE company_name = 'Urgent Suplier'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                id = rs.getString("id");
            }
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
        return id;
    }

    public void salesWithPurchaseLadger() {
        String supid = SupID();
        if (supid == null) {
            String sql = "INSERT INTO suplier_info (company_name,admin_id,created_at)VALUES ("
                    + "'Urgent Suplier','" + ID + "','" + queryFunction.service.getDateTime() + "')";
            queryFunction.InsertCustomise(sql, "Have a Problem in Suplier insert.");
            supid = SupID();
        }
        String sql = "INSERT INTO `purchase_ledger` (`invoice_no`,`invoice_date`,suplier_id,"
                + "`total_item`,`net_total_ammount`,`paid`,`due`,admin_id,created_at)"
                + "values('" + invoice_id + "','" + queryFunction.service.getnonFormatCurrentDate() + "',"
                + "'" + supid + "','1','" + total_purchase_price + "','" + total_purchase_price + ""
                + "','0','" + ID + "','" + queryFunction.service.getDateTime() + "')";
        queryFunction.InsertCustomise(sql, "Have a Problem in Purchase Ladger.");
    }

    public void salesWithPurchaseStock() {
        try {
            String sql1 = "SELECT * FROM stock_product WHERE stock_product.product_id IN ('" + proID + "')";
            rs = queryFunction.getResult(sql1);
            if (rs.next()) {
                String sql2 = "UPDATE stock_product SET stock_product.quantity = stock_product.quantity + "
                        + "'" + quantity + "',stock_product.purchase_price = "
                        + "'" + purchase_price + "',stock_product.sale_price = "
                        + "'" + sale_price + "',stock_product."
                        + "old_and_new_purchase_price_average = '" + purchase_price + "' WHERE stock_product."
                        + "product_id = '" + proID + "'";
                queryFunction.UpdateMessageLess(sql2);
            } else {
                String query = "INSERT INTO `stock_product`(`stock_product`.`product_id`,`stock_product`.`quantity`,"
                        + "`stock_product`.`purchase_price`,`stock_product`.`sale_price`,`stock_product`."
                        + "`old_and_new_purchase_price_average`) VALUES "
                        + "('" + proID + "','" + quantity + "','" + purchase_price + "'"
                        + ",'" + sale_price + "',"
                        + "'" + purchase_price + "')";
                queryFunction.InsertCustomise(query, "Have a Problem in stock else.");
            }
            salesWithPurchasePayment();
        } catch (Exception e) {
        }
    }

    public void salesWithPurchasePayment() {
        String sql = "INSERT INTO purchase_payment_statement (invoice_no,"
                + "entry_date,total_ammount,payment,due,comment)VALUES ("
                + "'" + invoice_id + "','" + queryFunction.service.getnonFormatCurrentDate() + "','" + total_purchase_price + ""
                + "','" + total_purchase_price + "','0','First Payment')";
        queryFunction.InsertCustomise(sql, "Have a problem in Peyment Statement.");
    }

    public void vatEntry(String date, String invoice_no, String vat_P, String vat_t, String admin) {
        String sql = "INSERT INTO vat_entry (date,invoice_no,vat_parcentage,total_vat,"
                + "admin_id) VALUES ('" + date + "','" + invoice_no + "','" + vat_P + "','" + vat_t + "','" + admin + "')";
        queryFunction.InsertCustomise(sql, "Have a Problem in Vat Entry");
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }

}

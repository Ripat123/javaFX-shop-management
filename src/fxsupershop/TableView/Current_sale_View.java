
package fxsupershop.TableView;


public class Current_sale_View {

    public static void setItems(Object data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    String session_id,product_purchaseprice,product_saleprice,product_quantity,product_discount,product_totalsaleprice;

    public Current_sale_View(String session_id, String product_purchaseprice, String product_saleprice, String product_quantity, String product_discount, String product_totalsaleprice) {
        this.session_id = session_id;
        this.product_purchaseprice = product_purchaseprice;
        this.product_saleprice = product_saleprice;
        this.product_quantity = product_quantity;
        this.product_discount = product_discount;
        this.product_totalsaleprice = product_totalsaleprice;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getProduct_purchaseprice() {
        return product_purchaseprice;
    }

    public void setProduct_purchaseprice(String product_purchaseprice) {
        this.product_purchaseprice = product_purchaseprice;
    }

    public String getProduct_saleprice() {
        return product_saleprice;
    }

    public void setProduct_saleprice(String product_saleprice) {
        this.product_saleprice = product_saleprice;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }

    public String getProduct_totalsaleprice() {
        return product_totalsaleprice;
    }

    public void setProduct_totalsaleprice(String product_totalsaleprice) {
        this.product_totalsaleprice = product_totalsaleprice;
    }
    
}

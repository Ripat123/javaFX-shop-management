
package fxsupershop.TableView;

import com.jfoenix.controls.JFXButton;

/**
 *
 * @author Ripat Rabbi
 */
public class SalesView {
    private String id,product_id,product_saleprice,product_quantity,product_discount,product_totalsaleprice;
    private JFXButton button;
    public SalesView(String id,String product_id, String product_saleprice, String product_quantity, String product_discount, String product_totalsaleprice, JFXButton button) {
        this.id = id;
        this.product_id = product_id;
        this.product_saleprice = product_saleprice;
        this.product_quantity = product_quantity;
        this.product_discount = product_discount;
        this.product_totalsaleprice = product_totalsaleprice;
        this.button = button;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JFXButton getButton() {
        return button;
    }

    public void setButton(JFXButton button) {
        this.button = button;
    }
    
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

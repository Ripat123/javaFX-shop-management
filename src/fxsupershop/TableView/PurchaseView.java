
package fxsupershop.TableView;



import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.control.Button;


/**
 *
 * @author PC
 */
public class PurchaseView {
    String id,productName, quantity, purchasePrice, discount, totalPrice, salePrice;
    JFXButton delete;
    
    
    JFXCheckBox checkbox;
    String productName_multi, quantity_multi, purchasePrice_multi,discount_multi;
    public PurchaseView(JFXCheckBox checkbox, String productName_multi, String quantity_multi, String purchasePrice_multi, String discount_multi) {
        this.checkbox = checkbox;
        this.productName_multi = productName_multi;
        this.quantity_multi = quantity_multi;
        this.purchasePrice_multi = purchasePrice_multi;
        this.discount_multi = discount_multi;
    }

    public String getQuantity_multi() {
        return quantity_multi;
    }

    public void setQuantity_multi(String quantity_multi) {
        this.quantity_multi = quantity_multi;
    }

    public String getPurchasePrice_multi() {
        return purchasePrice_multi;
    }

    public void setPurchasePrice_multi(String purchasePrice_multi) {
        this.purchasePrice_multi = purchasePrice_multi;
    }

    public String getDiscount_multi() {
        return discount_multi;
    }

    public void setDiscount_multi(String discount_multi) {
        this.discount_multi = discount_multi;
    }

    public JFXCheckBox getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(JFXCheckBox checkbox) {
        this.checkbox = checkbox;
    }

    public String getProductName_multi() {
        return productName_multi;
    }

    public void setProductName_multi(String productName_multi) {
        this.productName_multi = productName_multi;
    }
    
    
    
    
    public PurchaseView(String id,String productName, String quantity, String purchasePrice, String discount, String totalPrice, String salePrice, JFXButton delete) {
        this.productName = productName;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.salePrice = salePrice;
        this.id = id;
        this.delete = delete;

        
    }

    public JFXButton getDelete() {
        return delete;
    }

    public void setDelete(JFXButton delete) {
        this.delete = delete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }
    
}

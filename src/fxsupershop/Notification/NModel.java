
package fxsupershop.Notification;

/**
 *
 * @author Rifat Rabbi
 */
public class NModel {
    String productName,quantity,listquan;

    public NModel(String productName, String quantity, String listquan) {
        this.productName = productName;
        this.quantity = quantity;
        this.listquan = listquan;
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

    public String getListquan() {
        return listquan;
    }

    public void setListquan(String listquan) {
        this.listquan = listquan;
    }
}


package fxsupershop.Sales;

/**
 *
 * @author PC
 */
public class SimpleSalesModel {
    String customer,cusPhone,barcode,product,unit,perchase,salePrice,quantity,disPer,disTk,rate,totalPrice;

    public SimpleSalesModel(String customer, String cusPhone, String barcode, String product, String unit,String perchase, String salePrice, String quantity, String disPer, String disTk, String rate, String totalPrice) {
        this.customer = customer;
        this.cusPhone = cusPhone;
        this.barcode = barcode;
        this.product = product;
        this.unit = unit;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.disPer = disPer;
        this.disTk = disTk;
        this.rate = rate;
        this.totalPrice = totalPrice;
        this.perchase = perchase;
    }

    public String getPerchase() {
        return perchase;
    }

    public void setPerchase(String perchase) {
        this.perchase = perchase;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDisPer() {
        return disPer;
    }

    public void setDisPer(String disPer) {
        this.disPer = disPer;
    }

    public String getDisTk() {
        return disTk;
    }

    public void setDisTk(String disTk) {
        this.disTk = disTk;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
    
}


package fxsupershop.TableView;

/**
 *
 * @author PC
 */
public class SalesReturnView {
    String returnid,invoiceNO,date,pro_name,quantity,total,type,discount,netTotal;

    public SalesReturnView(String returnid, String invoiceNO, String date, String pro_name, String quantity, String total, String type, String discount, String netTotal) {
        this.returnid = returnid;
        this.invoiceNO = invoiceNO;
        this.date = date;
        this.pro_name = pro_name;
        this.quantity = quantity;
        this.total = total;
        this.type = type;
        this.discount = discount;
        this.netTotal = netTotal;
    }

    public String getReturnid() {
        return returnid;
    }

    public void setReturnid(String returnid) {
        this.returnid = returnid;
    }

    public String getInvoiceNO() {
        return invoiceNO;
    }

    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(String netTotal) {
        this.netTotal = netTotal;
    }
    
}


package fxsupershop.TableView;

/**
 *
 * @author PC
 */
public class CurrentStockView {
     String proName, quan, purch_price, sale_price;
    public CurrentStockView(String proName, String quan,String purch_price, String sale_price){
        this.sale_price=sale_price;
        this.proName=proName;
        this.quan=quan;
        this.purch_price=purch_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getPurch_price() {
        return purch_price;
    }

    public void setPurch_price(String purch_price) {
        this.purch_price = purch_price;
    }

}


package fxsupershop.TableView;

import javafx.scene.image.ImageView;

/**
 *
 * @author Ripat Rabbi
 */
public class ProductView {
    String id, proname, purchase_price, sale_price;
    ImageView image;
    public ProductView(String id, String proname, String purchase_price, String sale_price, ImageView image){
        this.id = id;
        this.proname = proname;
        this.purchase_price = purchase_price;
        this.sale_price = sale_price;
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }


    
}

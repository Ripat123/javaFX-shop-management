
package fxsupershop.TableView;

/**
 *
 * @author Ripat Rabbi
 */
public class BrandView {
    String brandname,id;
    public BrandView(String brandname,String id){
        
        this.brandname=brandname;
        this.id=id;
    }


    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

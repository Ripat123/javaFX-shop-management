
package fxsupershop.TableView;

/**
 *
 * @author Ripat Rabbi
 */
public class CategoryView {
    String id, itemname, catagoryname;
    public CategoryView(String id, String itemname, String categoryname){
        this.id=id;
        this.catagoryname=categoryname;
        this.itemname=itemname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getCatagoryname() {
        return catagoryname;
    }

    public void setCatagoryname(String catagoryname) {
        this.catagoryname = catagoryname;
    }
    
}

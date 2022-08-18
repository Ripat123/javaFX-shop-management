
package fxsupershop.TableView;



/**
 *
 * @author Ripat Rabbi
 */
public class ItemView {
   

   String id, itemname;

    public ItemView(String id, String itemname) {
       this.id=id;
       this.itemname=itemname;
    }

    public ItemView(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
   
    
}



package fxsupershop.Home;

/**
 *
 * @author Rifat Rabbi
 */
public class todaySalesView {
    String cus_name,item,paid,due;

    public todaySalesView(String cus_name, String item, String paid, String due) {
        this.cus_name = cus_name;
        this.item = item;
        this.paid = paid;
        this.due = due;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }
}

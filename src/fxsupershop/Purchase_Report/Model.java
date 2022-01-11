
package fxsupershop.Purchase_Report;

/**
 *
 * @author Rifat Rabbi
 */
public class Model {
    String invoice,date,suplier,net,paid,due;

    public Model(String invoice, String date, String suplier, String net, String paid, String due) {
        this.invoice = invoice;
        this.date = date;
        this.suplier = suplier;
        this.net = net;
        this.paid = paid;
        this.due = due;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSuplier() {
        return suplier;
    }

    public void setSuplier(String suplier) {
        this.suplier = suplier;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
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

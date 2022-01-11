
package fxsupershop.SalesReport;

/**
 *
 * @author Rifat Rabbi
 */
public class Sales_Model {
    
    String invoice,date,company,net,paid,due;

    public Sales_Model(String invoice, String date, String company, String net, String paid, String due) {
        this.invoice = invoice;
        this.date = date;
        this.company = company;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

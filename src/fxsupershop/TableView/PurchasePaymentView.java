
package fxsupershop.TableView;

/**
 *
 * @author PC
 */
public class PurchasePaymentView {
    String invoice,entrydate,total_amount,paid_amount,due;

    public PurchasePaymentView(String invoice, String entrydate, String total_amount, String paid_amount, String due) {
        this.invoice = invoice;
        this.entrydate = entrydate;
        this.total_amount = total_amount;
        this.paid_amount = paid_amount;
        this.due = due;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }
}

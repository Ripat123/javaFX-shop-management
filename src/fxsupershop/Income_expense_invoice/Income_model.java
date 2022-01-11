
package fxsupershop.Income_expense_invoice;

/**
 *
 * @author Rifat Rabbi
 */
public class Income_model {
    
    String invoice, date,title,amount,comment;

    public Income_model(String invoice, String date, String title, String amount, String comment) {
        this.invoice = invoice;
        this.date = date;
        this.title = title;
        this.amount = amount;
        this.comment = comment;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

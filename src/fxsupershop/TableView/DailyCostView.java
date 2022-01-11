
package fxsupershop.TableView;

/**
 *
 * @author PC
 */
public class DailyCostView {
    String invoiceNO, expenseSource, voucherNO, amount;

    public String getInvoiceNO() {
        return invoiceNO;
    }

    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }

    public String getExpenseSource() {
        return expenseSource;
    }

    public void setExpenseSource(String expenseSource) {
        this.expenseSource = expenseSource;
    }

    public String getVoucherNO() {
        return voucherNO;
    }

    public void setVoucherNO(String voucherNO) {
        this.voucherNO = voucherNO;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public DailyCostView(String invoiceNO, String expenseSource, String voucherNO, String amount) {
        this.invoiceNO = invoiceNO;
        this.expenseSource = expenseSource;
        this.voucherNO = voucherNO;
        this.amount = amount;
    }
}

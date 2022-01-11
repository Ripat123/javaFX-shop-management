
package fxsupershop.TableView;

/**
 *
 * @author PC
 */
public class Transaction {
    String id, ac_no, transaction_type, voucherNo, amount, date;

    public Transaction(String id, String ac_no, String transaction_type, String voucherNo, String amount, String date) {
        this.id = id;
        this.ac_no = ac_no;
        this.transaction_type = transaction_type;
        this.voucherNo = voucherNo;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAc_no() {
        return ac_no;
    }

    public void setAc_no(String ac_no) {
        this.ac_no = ac_no;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}

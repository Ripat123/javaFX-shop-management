
package fxsupershop.Income_Expense_Entry;

/**
 *
 * @author Rifat Rabbi
 */
public class Model {
    String id,date,type,amount;

    public Model(String id, String date, String type, String amount) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

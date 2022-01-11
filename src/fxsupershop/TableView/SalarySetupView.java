
package fxsupershop.TableView;

/**
 *
 * @author PC
 */
public class SalarySetupView {
    
    private String name, amount, date,status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SalarySetupView(String name, String amount, String date, String status) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }
    

    
}

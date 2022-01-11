
package fxsupershop.Vat;

/**
 *
 * @author PC
 */
public class VatView {
    String date,percent;

    public VatView(String date, String percent) {
        this.date = date;
        this.percent = percent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
    
}

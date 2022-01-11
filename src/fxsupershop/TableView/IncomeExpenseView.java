
package fxsupershop.TableView;

/**
 *
 * @author PC
 */
public class IncomeExpenseView {
    String id, source_type, source_title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public String getSource_title() {
        return source_title;
    }

    public void setSource_title(String source_title) {
        this.source_title = source_title;
    }

    public IncomeExpenseView(String id, String source_type, String source_title) {
        this.id = id;
        this.source_type = source_type;
        this.source_title = source_title;
    }
}

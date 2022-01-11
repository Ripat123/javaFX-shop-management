
package fxsupershop.Services.View_by_date_pane;

/**
 *
 * @author PC
 */
public class Model {
    String tableName, column;

    public Model(String tableName, String column) {
        this.tableName = tableName;
        this.column = column;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}

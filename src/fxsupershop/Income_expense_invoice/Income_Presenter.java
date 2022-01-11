package fxsupershop.Income_expense_invoice;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import java.sql.ResultSet;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Rifat Rabbi
 */
public class Income_Presenter {

    ResultSet rs;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    ObservableList data = FXCollections.observableArrayList();
    String date_sql = null;

    private void ThisInitview(String query, TableView tableView) {
        try {
            rs = queryFunction.getResult(query);
            while (rs.next()) {
                data.add(new Income_model(rs.getString("id"),
                        rs.getString("date"),
                        rs.getString("source_title"),
                        rs.getString("ammount"),
                        rs.getString("comments")
                ));
            }
            tableView.setItems(data);
        } catch (Exception e) {
            queryFunction.service.msg.ErrorMessage("Unsucccessful", "Error", "Have a Problem.\n" + e);
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    public void view(String query, TableView tableView, TableColumn invoice_col, TableColumn date_col,
            TableColumn title_col, TableColumn amount_col, TableColumn comment_col) {
        data.clear();
        invoice_col.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));
        comment_col.setCellValueFactory(new PropertyValueFactory<>("comment"));
        ThisInitview(query, tableView);
    }

    public void DateSearch(JFXDatePicker date1, JFXDatePicker date2, TableView tableView) {
        data.clear();
        date_sql = "SELECT income_expense_source.source_title,incomeinfo.* FROM incomeinfo "
                + "INNER JOIN income_expense_source ON incomeinfo.fk_title_id = "
                + "income_expense_source.id WHERE incomeinfo.date BETWEEN '" + date1.getValue() + "' AND '" + date2.getValue() + "'";
        ThisInitview(date_sql, tableView);
    }

    public void sumFunction(String query, JFXTextField field, String clmn) {
        try {
            rs = queryFunction.getResult(query);
            if (rs.next()) {
                field.setText(rs.getString(clmn));
            }
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem\n" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public void Report(String sql) {
        if (sql == null) {
            reportGenerator(repQuery(date_sql));
        } else {
            reportGenerator(sql);
        }
    }

    private void reportGenerator(String query) {
        queryFunction.getImagePath(query, "image");
        queryFunction.report.getReport("/fxsupershop/Income_Expense_Entry/Report/", "Income_report.jrxml", query);
    }

    public String repQuery(String sql) {
        String repQuery = sql.substring(sql.indexOf("SELECT"), sql.indexOf(" FROM incomeinfo")) + ",project_info.* FROM project_info,";
        String query = sql.replace(sql.substring(sql.indexOf("SELECT"), sql.indexOf("incomeinfo INNER ")), repQuery);
        return query;
    }
}

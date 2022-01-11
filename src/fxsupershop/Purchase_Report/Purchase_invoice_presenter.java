package fxsupershop.Purchase_Report;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import java.sql.ResultSet;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Rifat Rabbi
 */
public class Purchase_invoice_presenter {

    ResultSet rs;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    ObservableList data = FXCollections.observableArrayList();

    private void initview(String query, TableView tableview) {
        try {
            rs = queryFunction.getResult(query);
            while (rs.next()) {

                data.add(new Model(rs.getString("invoice_no"),
                        rs.getString("invoice_date"),
                        rs.getString("company_name"),
                        rs.getString("purchase_payment_statement.total_ammount"),
                        rs.getString("purchase_payment_statement.payment"),
                        rs.getString("purchase_payment_statement.due")
                ));

            }
            tableview.setItems(data);
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

    private void ThisInitview(String query, TableView tableview) {
        try {

            rs = queryFunction.getResult(query);
            while (rs.next()) {
                data.add(new Model(rs.getString("invoice_no"),
                        rs.getString("invoice_date"),
                        rs.getString("company_name"),
                        rs.getString("purchase_payment_statement.total_ammount"),
                        rs.getString("purchase_payment_statement.payment"),
                        rs.getString("purchase_payment_statement.due")
                ));

            }
            tableview.setItems(data);
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
            TableColumn suplier_col, TableColumn net_col, TableColumn paid_col, TableColumn due_col) {
        data.clear();
        invoice_col.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        suplier_col.setCellValueFactory(new PropertyValueFactory<>("suplier"));
        net_col.setCellValueFactory(new PropertyValueFactory<>("net"));
        paid_col.setCellValueFactory(new PropertyValueFactory<>("paid"));
        due_col.setCellValueFactory(new PropertyValueFactory<>("due"));
        ThisInitview(query, tableView);
    }

    public void Allview(String query, TableView tableView) {
        data.clear();
        initview(query, tableView);
    }

    public void clicked(TableView tableView, MouseEvent event) {
        try {
            if (event.getClickCount() == 1) {
                @SuppressWarnings("rawtypes")
                TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                int col = pos.getColumn();
                @SuppressWarnings("rawtypes")
                TableColumn column = pos.getTableColumn();
                String val = column.getCellData(row).toString();
                if (col == 0) {
                    String path = "/fxsupershop/Purchase_Report/";
                    String sql = "SELECT purchase_entry.*,"
                            + "suplier_info.company_name,suplier_info.company_phone,project_info.*,"
                            + "purchase_ledger.invoice_date,purchase_ledger.total_discount,purchase_ledger.net_total_ammount,"
                            + "purchase_ledger.paid,purchase_ledger.due,product_productinfo.product_name "
                            + "FROM purchase_entry,purchase_ledger,product_productinfo,suplier_info,project_info WHERE purchase_ledger.suplier_id"
                            + " = suplier_info.id AND "
                            + "purchase_entry.product_id = product_productinfo.id "
                            + "AND purchase_entry.invoice_no = purchase_ledger.invoice_no AND purchase_ledger.invoice_no = '" + val + "'";
                    queryFunction.getImagePath(sql, "image");
                    queryFunction.report.getReport(path, "PurchaseReport.jrxml", sql);
                }
            }
        } catch (Exception e) {
        }

    }

    public void DateSearch(JFXDatePicker date1, JFXDatePicker date2, TableView tableview) {
        data.clear();
        String sql = "SELECT purchase_ledger.*,suplier_info.*,purchase_payment_statement.* FROM purchase_ledger "
                + "INNER JOIN suplier_info ON purchase_ledger.suplier_id = suplier_info."
                + "id INNER JOIN purchase_payment_statement ON purchase_ledger.invoice_no = purchase_payment_statement.invoice_no WHERE purchase_ledger.invoice_date BETWEEN '"
                + "" + date1.getValue() + "' AND '" + date2.getValue() + "'";
        initview(sql, tableview);
    }

    public void ThisSearch(String query, TableView tableView) {
        data.clear();
        ThisInitview(query, tableView);
    }

    public void allSearch(String query, TableView tableView) {
        data.clear();
        initview(query, tableView);
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
}

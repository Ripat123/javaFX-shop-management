package fxsupershop.SalesReport;

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
public class Sales_invoice_Presenter {

    ResultSet rs;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    ObservableList data = FXCollections.observableArrayList();

    private void initview(String query, TableView tableview) {
        try {
            rs = queryFunction.getResult(query);
            while (rs.next()) {
                data.add(new Sales_Model(rs.getString("invoice_id"),
                        rs.getString("invoice_date"),
                        rs.getString("customer_name"),
                        rs.getString("sale_payment_statement.total_ammount"),
                        rs.getString("sale_payment_statement.payment"),
                        rs.getString("sale_payment_statement.due")
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
            TableColumn company_col, TableColumn net_col, TableColumn paid_col, TableColumn due_col) {
        data.clear();
        invoice_col.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        company_col.setCellValueFactory(new PropertyValueFactory<>("company"));
        net_col.setCellValueFactory(new PropertyValueFactory<>("net"));
        paid_col.setCellValueFactory(new PropertyValueFactory<>("paid"));
        due_col.setCellValueFactory(new PropertyValueFactory<>("due"));
        initview(query, tableView);
    }

    public void Allview(String query, TableView tableView) {
        data.clear();
        initview(query, tableView);
    }

    public void clicked(TableView tableView, MouseEvent event, JFXCheckBox checkBox, JFXCheckBox checkBox1, JFXTextField field) {
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
                    if (checkBox.isSelected()) {
                        String path = "/fxsupershop/SalesReport/";
                        String sql = "SELECT sale_entry.*,vat_entry.total_vat,"
                                + "customer_info.*,project_info.*,"
                                + "sale_ledger.*,product_productinfo.product_name "
                                + "FROM sale_entry,sale_ledger,product_productinfo,customer_info,project_info"
                                + " INNER JOIN vat_entry ON vat_entry.invoice_no = '" + val + "'"
                                + " WHERE sale_ledger.customer_id = customer_info.id AND "
                                + "sale_entry.product_id = product_productinfo.id "
                                + "AND sale_entry.invoice_id = sale_ledger.invoice_id AND sale_ledger.invoice_id = '" + val + "'";
                        queryFunction.getImagePath(sql, "image");
                        queryFunction.report.getReport(path, "SalesReport.jrxml", sql);
                    }
                    if (checkBox1.isSelected()) {
                        String path = "/fxsupershop/Sales/";
                        String sql = "SELECT sale_entry.*,vat_entry.total_vat,"
                                + "customer_info.*,project_info.*,"
                                + "sale_ledger.*,product_productinfo.product_name "
                                + "FROM sale_entry,sale_ledger,product_productinfo,customer_info,project_info"
                                + " INNER JOIN vat_entry ON vat_entry.invoice_no = '" + val + "'"
                                + " WHERE sale_ledger.customer_id = customer_info.id AND "
                                + "sale_entry.product_id = product_productinfo.id "
                                + "AND sale_entry.invoice_id = sale_ledger.invoice_id AND sale_ledger.invoice_id = '" + val + "'";
                        queryFunction.getImagePath(sql, "image");
                        queryFunction.report.getReport(path, "SalesPOSReport.jrxml", sql);
                    }
                    field.setText(String.valueOf(profit(val)));
                }
            }
        } catch (Exception e) {
        }

    }

    private double profit(String val) {
        double purchasePrice, totalPrice, quantity, salePrice, singleProfit, profit = 0;
        try {
            String sql = "SELECT * FROM sale_entry WHERE sale_entry.invoice_id = '" + val + "'";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                purchasePrice = Double.parseDouble(rs.getString("product_purchaseprice"));
                totalPrice = Double.parseDouble(rs.getString("product_totalsaleprice"));
                quantity = Double.parseDouble(rs.getString("product_quantity"));
                salePrice = totalPrice / quantity;
                singleProfit = (quantity * (salePrice - purchasePrice));
                profit = (profit + singleProfit);
            }
            String sql1 = "SELECT * FROM sale_ledger WHERE sale_ledger.invoice_id = '" + val + "'";
            rs = queryFunction.getResult(sql1);
            if (rs.next()) {
                double discount = Double.parseDouble(rs.getString("totaldiscount"));
                profit = profit - discount;
            }
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
        return profit;
    }

    public void profitGenerator(String query, JFXTextField field) {
        String val;
        ResultSet rss = null;
        double singleProfit, totalProfit = 0;
        try {
            rss = queryFunction.getResult(query);
            while (rss.next()) {
                val = rss.getString("invoice_id");
                singleProfit = profit(val);
                totalProfit = totalProfit + singleProfit;
            }
            field.setText(String.valueOf(totalProfit));
        } catch (Exception e) {
        } finally {
            try {
                rss.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    public void DateSearch(JFXDatePicker date1, JFXDatePicker date2, TableView tableview) {
        data.clear();
        String sql = "SELECT sale_ledger.*,customer_info.*,sale_payment_statement.* FROM sale_ledger "
                + "INNER JOIN customer_info ON sale_ledger.customer_id = customer_info."
                + "id INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no WHERE sale_ledger.invoice_date BETWEEN '"
                + "" + date1.getValue() + "' AND '" + date2.getValue() + "'";
        initview(sql, tableview);
    }

    public void ThisSearch(String query, TableView tableView) {
        data.clear();
        initview(query, tableView);
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

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}


package fxsupershop.Income_Expense_Entry;

import com.jfoenix.controls.*;
import fxsupershop.Services.PrepareQueryFunction;
import java.sql.ResultSet;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Ripat Rabbi
 */
public class Presenter {
    
    ResultSet rs;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    ObservableList data = FXCollections.observableArrayList();
    
    public Model ClickedData(TableView tableview,MouseEvent event,String id,JFXComboBox 
            type,JFXTextArea desc,JFXTextField amount,String titleID,JFXDatePicker date,
            JFXComboBox tran, JFXRadioButton r1, String query){
        tableview.setEditable(true);
        Model m=null;
        try {
            if (event.getClickCount() == 1) {
                @SuppressWarnings("rawtypes")
                TablePosition pos = (TablePosition) tableview.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                int col = pos.getColumn();
                @SuppressWarnings("rawtypes")
                TableColumn column = pos.getTableColumn();
                String val = column.getCellData(row).toString();

                if (col == 0) {
                    String sql = query+"'" + val + "'";
                    rs = queryFunction.getResult(sql);
                    if (rs.next()) {
                        id = rs.getString("id");
                        if(r1.isSelected())
                            tran.setValue("Income");
                        else
                            tran.setValue("Expense");
                        type.setValue(rs.getString("source_title"));
                        desc.setText(rs.getString("comments"));
                        amount.setText(rs.getString("ammount"));
                        titleID = rs.getString("fk_title_id");
                        date.setValue(rs.getDate("date").toLocalDate());
                        m = new Model(id, titleID, null, null);
                    }
                }
            }
        } catch (Exception e) {

        }
        finally{
            try{
                rs.close();
                queryFunction.post.close();
            }
            catch(Exception e){
                
            }
        }
        return m;
    }
    
    private void initview(TableView tableview, String query){
        try {
            rs = queryFunction.getResult(query);
            while (rs.next()) {

                data.add(new Model(rs.getString("id"),
                        rs.getString("date"), 
                        rs.getString("source_title"),
                        rs.getString("ammount")
                ));

            }
            tableview.setItems(data);
        } catch (Exception e) {
            queryFunction.service.msg.ErrorMessage("Unsucccessful", "Error", "Have a Problem.\n" + e);
        }finally{
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }
    
    public void view(TableView tableview, TableColumn col_id, TableColumn col_date,
            TableColumn col_type,TableColumn col_amount,String query){
        data.clear();
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        String sql = query;
        initview(tableview, sql);
    }
    
    public void Search(TableView tableview, TableColumn col_id, TableColumn col_date,
            TableColumn col_type,TableColumn col_amount,JFXTextField search,String query){
        data.clear();
        String sql = query;
        initview(tableview, sql);
    }
    
    public void Report(){
        String sql ="SELECT expenseinfo.*,income_expense_source.*,project_info.* FROM "
                + "expenseinfo,income_expense_source,project_info WHERE expenseinfo.fk_title_id = income_expense_source.id";
        queryFunction.getImagePath(sql, "image");
        queryFunction.report.getReport("/fxsupershop/Income_Expense_Entry/Report/", "Expense_report.jrxml", sql);
    }
}

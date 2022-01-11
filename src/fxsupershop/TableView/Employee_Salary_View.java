
package fxsupershop.TableView;

public class Employee_Salary_View {
    String invoice_no,date,select_month,payment_type,amount,employee_name,phone;


    public Employee_Salary_View(String invoice_no, String date, String select_month, String payment_type, String amount, String employee_name, String phone, String comments ) {
        this.invoice_no = invoice_no;
        this.date = date;
        this.select_month = select_month;
        this.payment_type = payment_type;
        this.amount = amount;
        this.employee_name = employee_name;
        this.phone = phone;

    }

    public Employee_Salary_View(String string, String string0, String string1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSelect_month() {
        return select_month;
    }

    public void setSelect_month(String select_month) {
        this.select_month = select_month;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

   

    
}

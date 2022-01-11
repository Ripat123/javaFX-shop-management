
package fxsupershop.TableView;


public class SupplierView {
    String id,company_name,suplier_phone,company_phone,email,present_address,permanent_address;

    public SupplierView(String id, String company_name, String suplier_phone, String company_phone, String email, String present_address, String permanent_address) {
        this.id = id;
        this.company_name = company_name;
        this.suplier_phone = suplier_phone;
        this.company_phone = company_phone;
        this.email = email;
        this.present_address = present_address;
        this.permanent_address = permanent_address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getSuplier_phone() {
        return suplier_phone;
    }

    public void setSuplier_phone(String suplier_phone) {
        this.suplier_phone = suplier_phone;
    }

    public String getCompany_phone() {
        return company_phone;
    }

    public void setCompany_phone(String company_phone) {
        this.company_phone = company_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPresent_address() {
        return present_address;
    }

    public void setPresent_address(String present_address) {
        this.present_address = present_address;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }
    
}

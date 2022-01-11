
package fxsupershop.TableView;

/**
 *
 * @author PC
 */
public class BranchView {
    String id,company_name,name,mobileNo,email,officialNo,status;

    public BranchView(String id, String company_name, String name, String mobileNo, String email, String officialNo, String status) {
        this.id = id;
        this.company_name = company_name;
        this.name = name;
        this.mobileNo = mobileNo;
        this.email = email;
        this.officialNo = officialNo;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficialNo() {
        return officialNo;
    }

    public void setOfficialNo(String officialNo) {
        this.officialNo = officialNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


package fxsupershop.TableView;


public class CompanyView {
    String id,CompanyName,Status;

    public CompanyView(String id, String CompanyName, String Status) {
        this.id = id;
        this.CompanyName = CompanyName;
        this.Status = Status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
}

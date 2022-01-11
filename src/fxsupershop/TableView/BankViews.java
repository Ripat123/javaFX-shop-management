
package fxsupershop.TableView;

public class BankViews {
    String brance_name,bank_name,ac_no,type,add,mbl_no;

    public BankViews(String brance_name, String bank_name, String ac_no, String type, String add, String mbl_no) {
        this.brance_name = brance_name;
        this.bank_name = bank_name;
        this.ac_no = ac_no;
        this.type = type;
        this.add = add;
        this.mbl_no = mbl_no;
    }

    public BankViews(String string, String string0, String string1, String string2, String string3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getBrance_name() {
        return brance_name;
    }

    public void setBrance_name(String brance_name) {
        this.brance_name = brance_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAc_no() {
        return ac_no;
    }

    public void setAc_no(String ac_no) {
        this.ac_no = ac_no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getMbl_no() {
        return mbl_no;
    }

    public void setMbl_no(String mbl_no) {
        this.mbl_no = mbl_no;
    }
    
}

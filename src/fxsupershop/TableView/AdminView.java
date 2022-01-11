
package fxsupershop.TableView;

/**
 *
 * @author PC
 */
public class AdminView {
    private String fullname, phone, email, admintype, userstutas, gender, address;
    private String id, question;

    public AdminView(String id, String question) {
        this.id = id;
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AdminView(String fullname, String phone, String email, String admintype, String userstutas, String gender, String address) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.admintype = admintype;
        this.userstutas = userstutas;
        this.gender = gender;
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdmintype() {
        return admintype;
    }

    public void setAdmintype(String admintype) {
        this.admintype = admintype;
    }

    public String getUserstutas() {
        return userstutas;
    }

    public void setUserstutas(String userstutas) {
        this.userstutas = userstutas;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

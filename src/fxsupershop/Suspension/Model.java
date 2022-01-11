
package fxsupershop.Suspension;

/**
 *
 * @author Rifat Rabbi
 */
public class Model {
    String id, sus_name;

    public Model(String id, String sus_name) {
        this.id = id;
        this.sus_name = sus_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSus_name() {
        return sus_name;
    }

    public void setSus_name(String sus_name) {
        this.sus_name = sus_name;
    }
}

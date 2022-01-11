
package fxsupershop.TableView;

/**
 *
 * @author PC
 */
public class MesurementView {
    String id, mesurement_type;

    public MesurementView(String id, String mesurement_type) {
        this.id = id;
        this.mesurement_type = mesurement_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMesurement_type() {
        return mesurement_type;
    }

    public void setMesurement_type(String mesurement_type) {
        this.mesurement_type = mesurement_type;
    }
}

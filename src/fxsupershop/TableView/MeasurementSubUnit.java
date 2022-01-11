
package fxsupershop.TableView;

/**
 *
 * @author PC
 */
public class MeasurementSubUnit {
    
    String id, subUnitName, subUnitData;

    public MeasurementSubUnit(String id, String subUnitName, String subUnitData) {
        this.id = id;
        this.subUnitName = subUnitName;
        this.subUnitData = subUnitData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubUnitName() {
        return subUnitName;
    }

    public void setSubUnitName(String subUnitName) {
        this.subUnitName = subUnitName;
    }

    public String getSubUnitData() {
        return subUnitData;
    }

    public void setSubUnitData(String subUnitData) {
        this.subUnitData = subUnitData;
    }
    
}

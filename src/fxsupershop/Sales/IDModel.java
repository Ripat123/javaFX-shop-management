
package fxsupershop.Sales;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Rifat Rabbi
 */
public class IDModel {
    String proID, unitID;
    ObservableList list = FXCollections.observableArrayList();

    public IDModel(String proID, String unitID,ObservableList list) {
        this.proID = proID;
        this.unitID = unitID;
        this.list = list;
    }

    public ObservableList getList() {
        return list;
    }

    public void setList(ObservableList list) {
        this.list = list;
    }

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }
}

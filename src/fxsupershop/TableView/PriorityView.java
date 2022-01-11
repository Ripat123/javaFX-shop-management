
package fxsupershop.TableView;

import com.jfoenix.controls.JFXToggleButton;

/**
 *
 * @author PC
 */
public class PriorityView {
    private String formName;
    private JFXToggleButton button;

    public PriorityView(String formName, JFXToggleButton button) {
        this.formName = formName;
        this.button = button;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public JFXToggleButton getButton() {
        return button;
    }

    public void setButton(JFXToggleButton button) {
        this.button = button;
    }
}

package fxsupershop.Login;

import fxsupershop.Services.Message;
import javafx.scene.control.*;

/**
 *
 * @author Ripat Rabbi
 */
public class LoginPresenter {

    private String txt_pass, txt_promptPass;
    Message msg = new Message();

    public void CheckSelectedGetPass(CheckBox showpass_box, PasswordField pass_field) {
        if (showpass_box.isSelected()) {
            showpass_box.setSelected(false);
            pass_field.setText(pass_field.getPromptText());
        }
    }

    public void ShowPassToPasswordField(PasswordField pass, CheckBox show_pass_box) {
        try {
            txt_pass = pass.getText();
            if (show_pass_box.isSelected()) {
                pass.setText(null);
                pass.setPromptText(txt_pass);
            } else if (!show_pass_box.isSelected()) {
                txt_promptPass = pass.getPromptText();
                pass.setText(txt_promptPass);
            }
        } catch (Exception e) {
            msg.WarningMessage("Warning", "Warning", "Problem in Show pass.");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}

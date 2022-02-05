package fxsupershop.Project_info;

import com.jfoenix.controls.*;
import fxsupershop.Connection.connection_Sql;
import fxsupershop.Services.PrepareQueryFunction;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Project_detailsController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
    @FXML
    private JFXTextArea add;
    @FXML
    private JFXTextField shop_n;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField owner;
    @FXML
    private JFXTextField ow_phone;
    @FXML
    private JFXTextField service;
    @FXML
    private JFXDatePicker delivery_date;
    @FXML
    private JFXDatePicker expired_date;
    @FXML
    private Rectangle img_shape;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    ResultSet rs;
    @FXML
    private JFXButton addbtn;
    FileInputStream fin;
    int len;
    Connection con = null;
    PreparedStatement post = null;
    Image image1;
    ImagePattern pattern;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        con = connection_Sql.ConnectDb();
        view();
    }

    private void insert() {
        try {
            String sql = "INSERT INTO project_info (Shop_name,address,phone,email,owner_name,"
                    + "owner_phone,delivery_date,service_charge,expired_date,image,created_at) VALUES ("
                    + "'" + shop_n.getText().trim() + "','" + add.getText().trim() + "','" + phone.getText().trim() + "'"
                    + ",'" + email.getText().trim() + "','" + owner.getText().trim() + "','" + ow_phone.getText().trim() + "'"
                    + ",'" + delivery_date.getValue() + "','" + service.getText().trim() + "'"
                    + ",'" + expired_date.getValue() + "',?,'" + queryFunction.service.getDateTime() + "')";
            post = con.prepareStatement(sql);
            post.setBinaryStream(1, fin, len);
            post.execute();
            queryFunction.service.msg.InformationMessage("Successful", "Information", "Insert Successful");
            clean();
            view();
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Insert Unsuccessful\n" + e);
        }

    }

    private void update() {
        String sql = "UPDATE project_info SET Shop_name = '" + shop_n.getText().trim() + "',"
                + "address='" + add.getText().trim() + "',phone='" + phone.getText().trim() + "',"
                + "email='" + email.getText().trim() + "',owner_name='" + owner.getText().trim() + "',"
                + "owner_phone='" + ow_phone.getText().trim() + "',delivery_date='" + delivery_date.getValue() + "',"
                + "service_charge='" + service.getText().trim() + "',expired_date='" + expired_date.getValue() + "',"
                + "created_at='" + queryFunction.service.getDateTime() + "'";
        queryFunction.Update(sql);
        clean();
        view();
    }

    private void view() {
        try {
            String sql = "SELECT * FROM project_info";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                shop_n.setText(rs.getString("Shop_name"));
                add.setText(rs.getString("address"));
                phone.setText(rs.getString("phone"));
                email.setText(rs.getString("email"));
                owner.setText(rs.getString("owner_name"));
                ow_phone.setText(rs.getString("owner_phone"));
                delivery_date.setValue(rs.getDate("delivery_date").toLocalDate());
                service.setText(rs.getString("service_charge"));
                expired_date.setValue(rs.getDate("expired_date").toLocalDate());
                addbtn.setDisable(true);
                image1 = queryFunction.getImage(sql, "image");
//                        if (image1 != null) {
//                            pattern = new ImagePattern(image1);
//                            img_shape.setFill(pattern);
//                        }
            } else {
                addbtn.setDisable(false);
            }
        } catch (Exception e) {
            queryFunction.service.msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem" + e);

        }
    }

    private void delete() {
        String sql = "DELETE FROM project_info WHERE Shop_name = '" + shop_n.getText() + "'";
        queryFunction.Delete(sql);
        clean();
    }

    private void ImageUpload() {
        try {
            File file = queryFunction.service.ImageUpload();
            fin = new FileInputStream(file);
            len = (int) file.length();
            img_shape.setFill(queryFunction.service.getImagepattern(file));
            String sql = "UPDATE project_info SET image = ?";
            post = con.prepareStatement(sql);
            post.setBinaryStream(1, fin, len);
            post.execute();
            queryFunction.service.msg.InformationMessage("Successful", "Information", "Update Successful");
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem" + e);
        }
    }

    private void clean() {
        shop_n.setText(null);
        add.setText(null);
        phone.setText(null);
        email.setText(null);
        owner.setText(null);
        ow_phone.setText(null);
        delivery_date.setValue(null);
        service.setText(null);
        expired_date.setValue(null);
        shop_n.requestFocus();
    }

    @FXML
    private void Deletebtn(ActionEvent event) {
        delete();

    }

    @FXML
    private void Updatebtn(ActionEvent event) {
        update();

    }

    @FXML
    private void valueAdd(ActionEvent event) {
        insert();
    }

    @FXML
    private void Cleanbtn(ActionEvent event) {
        clean();
    }

    @FXML
    private void onViiew(ActionEvent event) {
        view();
    }

    @FXML
    private void uploadbtn(ActionEvent event) {
        ImageUpload();
    }

    @FXML
    private void OpenPopupImage(MouseEvent event) {
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }

}

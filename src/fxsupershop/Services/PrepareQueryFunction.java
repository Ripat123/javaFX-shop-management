package fxsupershop.Services;

import com.jfoenix.controls.*;
import java.io.*;
import java.sql.*;
import java.util.Optional;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;

/**
 *
 * @author Ripat Rabbi
 */
public class PrepareQueryFunction {

    static Connection con;
    public PreparedStatement post = null;
    ResultSet rs = null, rss = null;

    public Report report = new Report();
    public GeneralService service = new GeneralService();

    public static void getCon(Connection c) {
        con = c;
    }

    public Connection getConnect() {
        return con;
    }

    public int AutoJFXID(String table_name) {
        int ID = 0;
        try {
            String sql = "SELECT MAX(id) AS 'id' FROM " + table_name;
            rs = getResult(sql);
            if (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                id++;
                ID = id;
                if (ID == 0) {
                    ID = 1;
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {
            }
        }
        if (ID == 0) {
            ID = 1;
        }
        return ID;
    }

    public String AutoStringID(String table_name) {
        String ID = null;
        try {
            String sql = "SELECT MAX(id) AS 'id' FROM " + table_name;
            rs = getResult(sql);
            if (rs.next()) {
                String id = rs.getString("id");
                ID = id;
            }
        } catch (Exception e) {
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {
            }
        }
        return ID;
    }

    public void Insert(String query) {
        try {

            String sql = query;
            post = con.prepareStatement(sql);
            post.execute();
            service.msg.InformationMessage("Successful", "Information", "Insert Successful.");
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", "Insert Unsuccessful\n" + e);
        } finally {
            try {
                post.close();

            } catch (Exception e) {

            }
        }
    }

    public void InsertCustomise(String query, String contentText) {
        try {
            String sql = query;
            post = con.prepareStatement(sql);
            post.execute();
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", contentText + "\n" + e);

        } finally {
            try {
                post.close();
            } catch (Exception e) {

            }
        }
    }

    public void Delete(String query) {
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Delete Selected Data?");
            alert.setContentText("Are you sure you want to delete?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                String sql = query;
                post = con.prepareStatement(sql);
                post.execute();
                service.msg.InformationMessage("Successful", "Information", "Delete Successful.");
            }
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", "Delete Unsuccessful\n" + e);
        } finally {
            try {
                post.close();

            } catch (Exception e) {

            }
        }
    }

    public void DeleteConditionLess(String query) {
        try {
            String sql = query;
            post = con.prepareStatement(sql);
            post.execute();

        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", "Delete Unsuccessful\n" + e);
        } finally {
            try {
                post.close();

            } catch (Exception e) {

            }
        }
    }

    public void Update(String query) {
        try {
            String sql = query;
            post = con.prepareStatement(sql);
            post.execute();
            service.msg.InformationMessage("Successful", "Information", "Update Successful.");

        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", "Update Unsuccessful\n" + e);
        } finally {
            try {
                post.close();

            } catch (Exception e) {

            }
        }
    }

    public void UpdateMessageLess(String query) {
        try {
            String sql = query;
            post = con.prepareStatement(sql);
            post.execute();

        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", "Update Unsuccessful\n" + e);
        } finally {
            try {
                post.close();

            } catch (Exception e) {

            }
        }
    }

    public ResultSet getResult(String query) throws SQLException {
        String sql = query;
        post = con.prepareStatement(sql);
        rs = post.executeQuery();
        return rs;
    }

    public ObservableList ViewArrayJFXComboBox(String query, String query_column_name, String ID_column_name, JFXComboBox combo_box, ObservableList list) {
        try {
            combo_box.getItems().clear();
            list.clear();
            String sql = query;
            rs = getResult(sql);
            while (rs.next()) {
                list.addAll(rs.getString(ID_column_name));
                combo_box.getItems().addAll(rs.getString(query_column_name));

            }
        } catch (Exception e) {

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
        return list;
    }

    public void ViewItemOnJFXComboBox(String query, String query_column_name, JFXComboBox combo_box) {
        try {
            combo_box.getItems().clear();
            String sql = query;
            rs = getResult(sql);
            while (rs.next()) {

                combo_box.getItems().addAll(rs.getString(query_column_name));

            }
        } catch (Exception e) {

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public void ViewItemOnComboBox(String query, String query_column_name, ComboBox combo_box) {
        try {
            combo_box.getItems().clear();
            String sql = query;
            rss = getResult(sql);
            while (rss.next()) {

                combo_box.getItems().addAll(rss.getString(query_column_name));
            }
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful here", "Warning", "View Item Unsuccessful\n" + e);

        } finally {
            try {
                post.close();
                rss.close();
            } catch (Exception e) {

            }
        }
    }

    public void ViewSingleItemOnJFXTextfield(String query, String query_column_name, JFXTextField textField) {
        try {
            String sql = query;
            rs = getResult(sql);
            while (rs.next()) {

                textField.setText(rs.getString(query_column_name));

            }
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", "View Item Unsuccessful\n" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public void ViewDualItemOnJFXTextfield(String query, String query_column_name, String query_column_name1, JFXTextField textField, JFXTextField textField1) {
        try {
            String sql = query;
            rs = getResult(sql);
            while (rs.next()) {

                textField.setText(rs.getString(query_column_name));
                textField1.setText(rs.getString(query_column_name1));

            }
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", "View Item Unsuccessful\n" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public void ShowJFXItemOnkeyReleased(String query, String query_column_name, JFXComboBox combo_box, KeyEvent event) {
        try {
            if (event.getCode() != KeyCode.UP && event.getCode() != KeyCode.DOWN
                    && event.getCode() != KeyCode.RIGHT && event.getCode()
                    != KeyCode.LEFT && event.getCode() != KeyCode.ENTER) {

                combo_box.show();
                combo_box.getItems().clear();
                String sql = query;
                rs = getResult(sql);
                while (rs.next()) {

                    combo_box.getItems().addAll(rs.getString(query_column_name));
                }
            }

        } catch (Exception e) {

            service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem!\n" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public ObservableList ShowArrayItemKeyReleased(String query, String query_column_name, String ID_column_name, ComboBox combo_box, KeyEvent event, ObservableList list) {
        try {
            if (event.getCode() != KeyCode.UP && event.getCode() != KeyCode.DOWN
                    && event.getCode() != KeyCode.RIGHT && event.getCode()
                    != KeyCode.LEFT && event.getCode() != KeyCode.ENTER) {

                combo_box.show();
                combo_box.getItems().clear();
                list.clear();
                String sql = query;
                rs = getResult(sql);
                while (rs.next()) {
                    list.addAll(rs.getString(ID_column_name));
                    combo_box.getItems().addAll(rs.getString(query_column_name));
                }
            }

        } catch (Exception e) {

            service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem!\n" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
        return list;
    }

    public void ShowItemOnkeyReleased(String query, String query_column_name, ComboBox combo_box, KeyEvent event) {
        try {
            if (event.getCode() != KeyCode.UP && event.getCode() != KeyCode.DOWN
                    && event.getCode() != KeyCode.RIGHT && event.getCode()
                    != KeyCode.LEFT && event.getCode() != KeyCode.ENTER) {

                combo_box.show();
                combo_box.getItems().clear();
                String sql = query;
                rs = getResult(sql);
                while (rs.next()) {

                    combo_box.getItems().addAll(rs.getString(query_column_name));
                }
            }

        } catch (Exception e) {

            service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem!\n" + e);

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public int getDataIniVeriable(String query, String columnName) {
        int id = 0;
        try {
            String sql = query;
            rs = getResult(sql);
            if (rs.next()) {
                id = rs.getInt(columnName);
            }
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Get int Data", "Have a Problem!\n" + e);
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
        return id;
    }

    public String getDataInSVeriable(String query, String columnName) {
        String data_id = null;
        try {
            String sql = query;
            rs = getResult(sql);
            if (rs.next()) {
                data_id = rs.getString(columnName);
            }
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Get String Data", "Have a Problem!\n" + e);
        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
        return data_id;
    }

    public String getSUMValue(String query, String query_column) {
        String variable = null;
        try {
            String sql = query;
            ResultSet rs = getResult(sql);
            if (rs.next()) {
                variable = rs.getString(query_column);
            }
        } catch (Exception e) {

        } finally {
            try {
                post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
        return variable;
    }

    public Image getImage(String query, String image_col) {
        Image imagex = null;
        try {
            rs = getResult(query);
            if (rs.next()) {
                if (rs.getBinaryStream("image") != null && !rs.getString("image")
                        .isEmpty() && rs.getString("image").length() != 255) {
                    String sql = "SELECT * FROM project_info", realPath = null;
                    ResultSet rss = getResult(sql);
                    if (rss.next()) {
                        realPath = rss.getString("image_path");
                        System.out.println(realPath);
                    }
                    InputStream is = rs.getBinaryStream(image_col);
                    String path = realPath + "\\" + "profile.jpg";
                    OutputStream os = new FileOutputStream(new File(path));
                    byte[] content = new byte[1096];
                    int size = 0;
                    while ((size = is.read(content)) != -1) {
                        os.write(content, 0, size);
                    }
                    os.close();
                    is.close();
                    imagex = new Image("file:" + path, 300, 280, true, true);
                }
            }
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Get Image", "Have a Problem.\n" + e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {

            }
        }
        return imagex;
    }

    public void getImagePath(String query, String image_col) {
        String imagex, Tpath = null;
        try {
            String sql = "SELECT image_path FROM project_info";
            ResultSet rss = getResult(sql);
            if (rss.next()) {
                Tpath = rss.getString("image_path");
            }
            rs = getResult(query);
            if (rs.next()) {
                if (rs.getBinaryStream("image") != null && !rs.getString("image")
                        .isEmpty() && rs.getString("image").length() != 255) {
                    InputStream is = null;
                    OutputStream os = null;
                    String path = null;
                    try {
                        is = rs.getBinaryStream(image_col);

                        path = Tpath + "\\" + "report_logo.jpg";
                        os = new FileOutputStream(new File(path));
                        byte[] content = new byte[1096];
                        int size = 0;
                        while ((size = is.read(content)) != -1) {
                            os.write(content, 0, size);
                        }
                        os.close();
                        is.close();
                        imagex = path.replace("\\", "\\\\");
                        String sql2 = "UPDATE project_info SET logo_path = '" + imagex + "'";
                        UpdateMessageLess(sql2);
                    } catch (Exception e) {
                        createFolder(Tpath);
                        os.close();
                        is.close();
                        getImagePath(query, image_col);
                    }

                }
            }
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Get Image", "Have a Problem.\n" + e);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public ImageView setImage(ResultSet rs, String image_col, ImageView imgV, double fitHeight, double fitwidth) {
        Image imagex = null;
        try {
            InputStream is = rs.getBinaryStream(image_col);
            String Tpath = service.getActualPath("/fxsupershop/Image/");
            String path = Tpath + "image.jpg";
            OutputStream os = new FileOutputStream(new File(path));
            byte[] content = new byte[1096];
            int size = 0;
            while ((size = is.read(content)) != -1) {
                os.write(content, 0, size);
            }
            os.close();
            is.close();
            imagex = new Image("file:" + path, 300, 280, true, true);
            imgV.setImage(imagex);
            imgV.setFitHeight(fitHeight);
            imgV.setFitWidth(fitwidth);
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Set Image", "Have a Problem.\n" + e);
        } finally {
            try {

            } catch (Exception e) {

            }
        }
        return imgV;
    }

    public void CreateImage(String path, String ID, FileInputStream fin) {
        try {
            String real_path = path + "\\" + ID + ".jpg";
            OutputStream os = new FileOutputStream(new File(real_path));
            byte[] content = new byte[1096];
            int size = 0;
            while ((size = fin.read(content)) != -1) {
                os.write(content, 0, size);
            }
            os.close();
            fin.close();
        } catch (Exception e) {
            newfile(path, ID, fin);
        }
    }

    public void newfile(String path, String ID, FileInputStream fin) {
        String Dpath;
        try {
            boolean dir = new File(path).mkdirs();
            if (!dir) {
                Dpath = System.getProperty("user.home") + "\\Documents\\Product Image";
                dir = new File(Dpath).mkdirs();
                String sql = "UPDATE project_info SET image_path = '" + Dpath + "'";
                UpdateMessageLess(sql);
                CreateImage(Dpath, ID, fin);
                if (!dir) {
                    service.msg.WarningMessage("Unsuccessful", "Warning", "Folder Cannot Create");
                }
            } else {
                CreateImage(path, ID, fin);
            }
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem in Create Image\n" + e);
        }
    }

    public void createFolder(String Path) {
        String Dpath;
        try {
            boolean dir = new File(Path).mkdirs();
            if (!dir) {
                Dpath = System.getProperty("user.home") + "\\Documents\\Product Image";
                dir = new File(Dpath).mkdirs();
                String sql = "UPDATE project_info SET image_path = '" + Dpath + "'";
                UpdateMessageLess(sql);
                if (!dir) {
                    service.msg.WarningMessage("Unsuccessful", "Warning", "Folder Cannot Create");
                }
            }
        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem in Create Image\n" + e);
        }
    }

}

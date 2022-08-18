package fxsupershop.Services;

import com.jfoenix.controls.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.*;
import javax.crypto.Cipher;
import javax.crypto.spec.*;

/**
 *
 * @author Ripat Rabbi
 */
public class GeneralService {

    private String Path;
    public Message msg = new Message();

    public void prt(String text) {
        System.out.print(text);
    }

    public void prtln(String text) {
        System.out.println(text);
    }

    public void PopUPRight(Node popup_pane, Node nodeStage, JFXPopup popup, double initOffsetX, double initOffsetY) {
        popup.setPopupContent(null);
        popup.setPopupContent((Region) popup_pane);
        popup.show(nodeStage, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, initOffsetX, initOffsetY);

    }

    public void PopUPBottom(Node popup_pane, Node nodeStage, JFXPopup popup, double initOffsetX, double initOffsetY) {
        popup.setPopupContent(null);
        popup.setPopupContent((Region) popup_pane);
        popup.show(nodeStage, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.LEFT, initOffsetX, initOffsetY);

    }

    public void PopUPBlur(Pane quit_pane, AnchorPane stagePane, JFXPopup quit, double initOffsetX, double initOffsetY) {
        quit.setPopupContent(quit_pane);
        quit.show(stagePane, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, initOffsetX, initOffsetY);
        stagePane.setEffect(new GaussianBlur(22.0));
        quit.setOnHidden(e -> {
            stagePane.setEffect(null);
        });
    }

    public void PopUPLeft(Node popup_pane, Node nodeStage, JFXPopup popup, double initOffsetX, double initOffsetY) {
        popup.setPopupContent(null);
        popup.setPopupContent((Region) popup_pane);
        popup.show(nodeStage, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, initOffsetX, initOffsetY);
    }

    public File ImageUpload(ImageView imageview) {
        File selectedfile = null;
        try {
            FileChooser filechoose = new FileChooser();
            filechoose.setTitle("Select image");
            filechoose.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
            selectedfile = filechoose.showOpenDialog(new Stage());
            if (selectedfile != null) {
                String imageFile = selectedfile.toURI().toURL().toString();
                Image image = new Image(imageFile);
                imageview.setImage(image);

            }
        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Warning", "Have a Problem!\n" + e);

        }
        return selectedfile;
    }

    public File ImageUpload() {
        File selectedfile = null;
        try {
            FileChooser filechoose = new FileChooser();
            filechoose.setTitle("Select image");
            filechoose.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
            selectedfile = filechoose.showOpenDialog(new Stage());

        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Warning", "Have a Problem!\n" + e);

        }
        return selectedfile;
    }

    public String getActualPath(String path) {
        String actualPath = null;
        try {
            String Tpath = (getClass().getResource(path).getPath());
            String str[] = Tpath.split("file:/");
            String reduce = (str[1]);
            String deletePart = reduce.substring(reduce.indexOf("dist"), reduce.indexOf("/fxsupershop"));
            String removeSpace = reduce.replace(deletePart, "src");
            actualPath = removeSpace.replace("%20", " ");
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Not found" + e);
        }
        return actualPath;
    }

    public ImagePattern getImagepattern(File selectedfile) {
        ImagePattern image = null;
        try {
            if (selectedfile != null) {
                String imageFile = selectedfile.toURI().toURL().toString();
                Image Image = new Image(imageFile);
                image = new ImagePattern(Image);
            }
        } catch (Exception e) {
            msg.ErrorMessage("Unsuccessful", "Warning", "Have a Problem!\n" + e);
        }

        return image;
    }

    public FileChooser getSaveChooser(String Title, String Initial_fileName) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(Title);
        chooser.setInitialFileName(Initial_fileName);
        return chooser;

    }

    public FileChooser getOpenChooser(String Title) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(Title);
        return chooser;

    }

    public String getOpenDialogPath(FileChooser chooser) {
        try {
            File selectedfile = chooser.showOpenDialog(new Stage());
            if (selectedfile != null) {
                File dir = selectedfile.getParentFile();
                chooser.setInitialDirectory(dir);
                Path = selectedfile.getAbsolutePath();
                Path = Path.replace('\\', '/');
//            Path = Path.replace(" ", "%20");
            }
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem!\n" + e);
        }
        return Path;

    }

    public String getSaveDialogPath(FileChooser chooser) {
        try {
            File selectedfile = chooser.showSaveDialog(new Stage());
            if (selectedfile != null) {
                File dir = selectedfile.getParentFile();
                chooser.setInitialDirectory(dir);
                Path = selectedfile.getAbsolutePath();
                Path = Path.replace('\\', '/');
//            Path = Path.replace(" ", "%20");
            }
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem!\n" + e);
        }
        return Path;

    }

    public String getDateTime() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String Date = formatter.format(date);
        return Date;
    }

    public String getCurrentDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String Date = formatter.format(date);
        return Date;
    }

    public String getnonFormatCurrentDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String Date = formatter.format(date);
        return Date;
    }

    public String getYearMonth() {
        String Cdate = LocalDate.now().toString();
        String date = Cdate.substring(0, 7);
        return date;
    }

    public String getYear() {
        String Cdate = LocalDate.now().toString();
        String date = Cdate.substring(0, 4);
        return date;
    }

    public String DateFormate(String Date) {
        LocalDate date = LocalDate.parse(Date);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String ds = dateFormatter.format(date);
        return ds;
    }

    public String getJFXDate(JFXDatePicker Date) {
        LocalDate date = Date.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fxdate = formatter.format(date);
        return fxdate;
    }

    public String getJDate(DatePicker Date) {
        LocalDate date = Date.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String jdate = formatter.format(date);
        return jdate;
    }

    public String encrypt(String value) {
        try {
            String key, initVector;
            key = "Jar12345Jar12345";
            initVector = "RandomInitVector";
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(1, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
//            encodeBase64String(encrypted);
        } catch (Exception ex) {
            Optional<ButtonType> alert = new Alert(Alert.AlertType.ERROR, "Have a Problem\n"
                    + ex, ButtonType.CLOSE).showAndWait();
        }
        return null;
    }

    public String decrypt(String encrypted) {
        try {
            String key, initVector;
            key = "Jar12345Jar12345";
            initVector = "RandomInitVector";
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Input length must be multiple of 16", ButtonType.CANCEL);
            a.show();
        }
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
    }
}

package fxsupershop.Report;

import com.jfoenix.controls.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.print.*;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class Page_setupController implements Initializable {

    @FXML
    private JFXComboBox p_size;
    @FXML
    private JFXComboBox p_orien;
    @FXML
    private JFXTextField M_left;
    @FXML
    private JFXTextField M_top;
    @FXML
    private JFXTextField M_right;
    @FXML
    private JFXTextField M_bottom;
    private Paper paper = Paper.A4;
    private PageOrientation paper_orientaion = PageOrientation.PORTRAIT;
    private double margin_left = 0.5;
    private double margin_right = 0.5;
    private double margin_top = 0.75;
    private double margin_bottom = 0.75;
    Localhost_reportController controller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        p_size.getItems().addAll("A0", "A1", "A2", "A3", "A4", "A5", "A6", "C", "DESIGNATED_LONG",
                "EXECUTIVE", "JAPANESE_POSTCARD", "JIS_B4", "JIS_B5", "JIS_B6", "LEGAL",
                "MONARCH_ENVELOPE", "NA_8X10", "NA_LETTER", "NA_NUMBER_10_ENVELOPE",
                "TABLOID");
        p_size.setValue("A4");
        p_orien.getItems().addAll("PORTRAIT", "LANDSCAPE", "REVERSE_LANDSCAPE", "REVERSE_PORTRAIT");
        p_orien.setValue("PORTRAIT");
        M_left.setText("0.5");
        M_right.setText("0.5");
        M_top.setText("0.75");
        M_bottom.setText("0.75");

    }

    public PageLayout PageSetup(PrinterJob job, Paper p, PageOrientation pg, double ml, double mr, double mt, double mb) {
//        setup();
        ml = ml * 72;
        mr = mr * 72;
        mt = mt * 72;
        mb = mb * 72;
        Printer printer = job.getPrinter();
        PageLayout pageLayout = printer.createPageLayout(p, pg,
                ml, mr, mt, mb);
        return pageLayout;

    }

    public void setup() {
        String size = p_size.getValue().toString();
        String orien = p_orien.getValue().toString();
        switch (size) {
            case "A0":
                paper = Paper.A0;
                break;
            case "A1":
                paper = Paper.A1;
                break;
            case "A2":
                paper = Paper.A2;
                break;
            case "A3":
                paper = Paper.A3;
                break;
            case "A4":
                paper = Paper.A4;
                break;
            case "A5":
                paper = Paper.A5;
                break;
            case "A6":
                paper = Paper.A6;
                break;
            case "C":
                paper = Paper.C;
                break;
            case "DESIGNATED_LONG":
                paper = Paper.DESIGNATED_LONG;
                break;
            case "EXECUTIVE":
                paper = Paper.EXECUTIVE;
                break;
            case "JAPANESE_POSTCARD":
                paper = Paper.JAPANESE_POSTCARD;
                break;
            case "JIS_B4":
                paper = Paper.JIS_B4;
                break;
            case "JIS_B5":
                paper = Paper.JIS_B5;
                break;
            case "JIS_B6":
                paper = Paper.JIS_B6;
                break;
            case "LEGAL":
                paper = Paper.LEGAL;
                break;
            case "MONARCH_ENVELOPE":
                paper = Paper.MONARCH_ENVELOPE;
                break;
            case "NA_8X10":
                paper = Paper.NA_8X10;
                break;
            case "NA_LETTER":
                paper = Paper.NA_LETTER;
                break;
            case "NA_NUMBER_10_ENVELOPE":
                paper = Paper.NA_NUMBER_10_ENVELOPE;
                break;
            case "TABLOID":
                paper = Paper.TABLOID;
                break;
        }
        switch (orien) {
            case "PORTRAIT":
                paper_orientaion = PageOrientation.PORTRAIT;
                break;
            case "LANDSCAPE":
                paper_orientaion = PageOrientation.LANDSCAPE;
                break;
            case "REVERSE_LANDSCAPE":
                paper_orientaion = PageOrientation.REVERSE_LANDSCAPE;
                break;
            case "REVERSE_PORTRAIT":
                paper_orientaion = PageOrientation.REVERSE_PORTRAIT;
                break;
        }
        margin_left = Double.parseDouble(M_left.getText());
        margin_right = Double.parseDouble(M_right.getText());
        margin_top = Double.parseDouble(M_top.getText());
        margin_bottom = Double.parseDouble(M_bottom.getText());
    }

    @FXML
    private void okAction(ActionEvent event) {
        setup();
        Localhost_reportController lc = new Localhost_reportController();
        lc.paper(paper, paper_orientaion, margin_left, margin_right, margin_top, margin_bottom);

        JFXPopup p = controller.hide();
        p.hide();
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        JFXPopup p = controller.hide();
        p.hide();
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }

}

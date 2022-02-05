package fxsupershop.Report;

import com.jfoenix.controls.JFXPopup;
import fxsupershop.Services.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.print.*;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.scene.web.*;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class Localhost_reportController implements Initializable {

    @FXML
    private WebView webid;
    GeneralService generalService = new GeneralService();
    @FXML
    private Pane moreID;
    static JFXPopup pop = new JFXPopup();
    PrinterJob job;
    static PageLayout pageLayout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        final WebEngine webEngine = webid.getEngine();
        webEngine.load("http://Localhost");

    }

    Paper paper;
    PageOrientation orientation;
    double m_left, m_right, m_top, m_bottom;

    public void paper(Paper p, PageOrientation po, double ml, double mr, double mt, double mb) {
        this.paper = p;
        this.orientation = po;
        this.m_left = ml;
        this.m_right = mr;
        this.m_top = mt;
        this.m_bottom = mb;
        job = PrinterJob.createPrinterJob();
        Page_setupController pc = new Page_setupController();
        pageLayout = pc.PageSetup(job, paper, orientation, m_left, m_right, m_top, m_bottom);
    }

    private void print(final WebView node) {
        job = null;
        job = PrinterJob.createPrinterJob();
        if (pageLayout == null) {
            Printer printer = job.getPrinter();
            pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

        }
        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {

            double width = node.getBoundsInParent().getWidth();
            double height = node.getBoundsInParent().getHeight();
            PrintResolution resolution = job.getJobSettings().getPrintResolution();
            width /= resolution.getFeedResolution();
            height /= resolution.getCrossFeedResolution();
            double scaleX = pageLayout.getPrintableWidth() / width / 600;
            double scaleY = pageLayout.getPrintableHeight() / height / 600;
            Scale scale = new Scale(scaleX + 0.012, scaleY);
            node.getTransforms().add(scale);
            boolean success = job.printPage(pageLayout, node);
            if (success) {
                job.endJob();
            }
            node.getTransforms().remove(scale);
        }

    }

    public static JFXPopup hide() {
        JFXPopup p = pop;
        return p;
    }

    @FXML
    private void loadaction(MouseEvent event) {
        print(webid);
    }

    @FXML
    private void more(MouseEvent event) throws IOException {

        Node node = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxsupershop/Report/page_setup.fxml"));
        generalService.PopUPRight(node, moreID, pop, 0, 0);

    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }

}

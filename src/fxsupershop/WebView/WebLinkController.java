
package fxsupershop.WebView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import win.zqxu.jrviewer.JRViewerFX;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class WebLinkController implements Initializable {

    @FXML
    private WebView webid;
    @FXML
    private Button webload;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final WebEngine webEngine = webid.getEngine();
         webEngine.load("http://www.google.com");
         webEngine.setJavaScriptEnabled(true);
         
    }    
public void print(final Node node) {
    Printer printer = Printer.getDefaultPrinter();
    PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
//    double scalex = node.getBoundsInParent().getWidth();
//    double scaley = node.getBoundsInParent().getHeight();
    double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
    double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
    node.getTransforms().add(new Scale(scaleX, scaleY));

    PrinterJob job = PrinterJob.createPrinterJob();
    if (job != null) {
        boolean success = job.printPage(node);
       
      
        if (success) {
            job.endJob();
        }
    }
}
    @FXML
    private void loadaction(ActionEvent event) {
        print(webid);
    }
    
}
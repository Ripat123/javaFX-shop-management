package fxsupershop.Services;

import java.io.File;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.stage.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import win.zqxu.jrviewer.JRViewerFX;

/**
 *
 * @author Ripat Rabbi
 */
public class Report {

    static Connection con;
    static String report_path, report_name, query;
    GeneralService service = new GeneralService();

    public static void GetCon(Connection c) {
        con = c;
    }

    public void getReport(String report_path, String report_name, String query) {
        this.report_path = report_path;
        this.report_name = report_name;
        this.query = query;
        BackgroundService service = new BackgroundService(this, "Report");
        service.start();
    }

    public void Report() {
        try {
            String path = service.getActualPath(report_path);
            String report = path + report_name;
            JasperDesign jd = JRXmlLoader.load(report);
            
            String sql = query;

            JRDesignQuery deq = new JRDesignQuery();

            deq.setText(sql);

            jd.setQuery(deq);

            JasperReport jr = JasperCompileManager.compileReport(jd);

            JasperPrint pp = JasperFillManager.fillReport(jr, null, con);
            Platform.runLater(() -> {
                JRViewerFX.preview(new Stage(), Modality.APPLICATION_MODAL, pp);
            });

        } catch (Exception ex) {ex.printStackTrace();
             Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            //service.msg.ErrorMessage("Unsuccessful", "Getting Report", "Have a Problem\n" + ex);

        }
    }

    public void ExportReport(String report_path, String report_name, String query) {
        this.report_path = report_path;
        this.report_name = report_name;
        this.query = query;
        BackgroundService service = new BackgroundService(this, "Export");
        service.start();
    }

    public void Export() {
        Platform.runLater(() -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select Location");
            chooser.setInitialFileName("New Export File");
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Document Files", "*.xlsx", "*.xls", "*.xlsm"),
                    new FileChooser.ExtensionFilter("Word Document Files", "*.docx")
            );
            File selectedfile = chooser.showSaveDialog(new Stage());

            String path = service.getActualPath(report_path);
            String report = path + report_name;

            try {
                if (selectedfile != null) {
                    JasperDesign jd = JRXmlLoader.load(report);

                    String sql = query;

                    JRDesignQuery deq = new JRDesignQuery();

                    deq.setText(sql);

                    jd.setQuery(deq);

                    JasperReport jr = JasperCompileManager.compileReport(jd);

                    JasperPrint pp = JasperFillManager.fillReport(jr, null, con);

                    File dir = selectedfile.getParentFile();
                    chooser.setInitialDirectory(dir);
                    String Path = selectedfile.getAbsolutePath();
                    Path = Path.replace('\\', '/');

                    JRViewerFX.export(pp, Path);
                }
            } catch (JRException ex) {
                // Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
                service.msg.ErrorMessage("Unsuccessful", "Export Report", "Have a Problem\n" + ex);

            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}

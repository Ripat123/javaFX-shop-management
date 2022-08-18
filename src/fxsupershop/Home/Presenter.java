package fxsupershop.Home;

import animatefx.animation.ZoomIn;
import com.jfoenix.controls.*;
import fxsupershop.Connection.connection_Sql;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.CurrentStockView;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.PopupWindow;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Ripat Rabbi
 */
public class Presenter {

    private int userid;
    ResultSet rs;
    private Tab t;
    GeneralService service = new GeneralService();
    PrepareQueryFunction queryFunction;
    LoginMultiFormController lmfc = new LoginMultiFormController();

//    Brand_infoController brand_infoController = new Brand_infoController();
//    Product_infoController product_infoController = new Product_infoController();
//    Employee_SalaryController employee_SalaryController = new Employee_SalaryController();
//    Sales_InfoController sales_InfoController = new Sales_InfoController();
//    Purchase_infoController purchase_infoController = new Purchase_infoController();
//    Salary_SetupController salary_SetupController = new Salary_SetupController();
//    User_adminController user_adminController = new User_adminController();
    public Presenter(PrepareQueryFunction queryFunction) {
        this.queryFunction = queryFunction;
        userid = lmfc.User();
    }

    public void getTab(String FXML_Path, String Tab_Name, String TabID, JFXTabPane tabpane) {
        Service<Void> tab_service = new Service<Void>() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            try {
                                int tb = tabpane.getTabs().size();
                                if (tb == 0) {
                                    newTab(FXML_Path, Tab_Name, TabID, tabpane);
                                } else {
                                    for (int i = 0; i <= tb; i++) {
                                        if (i < tb) {
                                            String s = tabpane.getTabs().get(i).getId();
                                            if (s.equals(TabID)) {
                                                tabpane.getSelectionModel().select(i);
                                                break;
                                            }
                                        } else {
                                            newTab(FXML_Path, Tab_Name, TabID, tabpane);
                                        }
                                    }
                                }
//                                t.setOnSelectionChanged((event) -> {
//                                    try {
////                    RefreshSource();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                        service.msg.WarningMessage("Uncompleted", "Warning", "Have a Problem.\n" + e);
//                                    }
//                                });
                                t.setOnClosed((event) -> {
                                    if (tabpane.getTabs().isEmpty()) {
                                        tabpane.setVisible(false);
//                                        System.gc();
//                                        System.runFinalization();
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                                service.msg.WarningMessage("Uncompleted", "Warning", "Have a Problem.\n" + e);
                            }
                        });
                        return null;
                    }
                };
            }
        };
        tab_service.start();
        tab_service.setOnSucceeded((event) -> {
            tab_service.cancel();
        });
    }

    private void newTab(String FXML_Path, String Tab_Name, String TabID, JFXTabPane tabpane) throws IOException {
        Node node = (StackPane) FXMLLoader.load(getClass().getResource(FXML_Path));
        tabpane.setVisible(true);
        new ZoomIn(node).setSpeed(2).play();
        t = new Tab(Tab_Name, node);
        tabpane.getTabs().add(t);
        tabpane.getSelectionModel().select(t);
        t.setId(TabID);
    }

    public void PopUP(Pane quit_pane, AnchorPane stagePane, JFXPopup quit) {
        quit.setPopupContent(null);
        quit.setPopupContent(quit_pane);
        quit.show(stagePane, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, 450, 200);
        stagePane.setEffect(new GaussianBlur(22.0));
        quit.setOnHidden(e -> {
            stagePane.setEffect(null);
        });
    }

//    private void RefreshSource(){
//        
//        brand_infoController.initSource();
//        product_infoController.initSource();
//        employee_SalaryController.initSource();
//        sales_InfoController.initSource();
//        purchase_infoController.initSource();
//        salary_SetupController.initSource();
//        user_adminController.initSource();
//    }
    public void PriorityRender(JFXButton admin_btn_id, JFXButton sales_btn_id, MenuItem sales_bar_id, JFXButton purchase_btn_id,
            MenuItem purchase_bar_id, JFXButton product_btn_id, JFXButton stock_btn_id, JFXButton company_btn_id, JFXButton employee_btn_id,
            JFXButton salary_btn_id, JFXButton supplier_btn_id, JFXButton bank_btn_id, JFXButton transaction_btn_id,
            JFXButton daily_btn_id, MenuItem admin_bar_id, MenuItem priority_bar_id, MenuItem customer_bar_id,
            MenuItem branch_bar_id, MenuItem company_bar_id, MenuItem income_ex_bar_id, MenuItem measurement_bar_id,
            MenuItem employee_bar_id, MenuItem salary_bar_id, MenuItem Ssetup_bar_id,
            JFXButton customer_btn_id, MenuItem report_bar_id, MenuItem product_bar_id,
            MenuItem item_bar_id, MenuItem brand_bar_id, MenuItem category_bar_id, MenuItem damage_bar_id, MenuItem Sreturn_bar_id,
            MenuItem Preturn_bar_id, MenuItem Spayment_bar_id, MenuItem Ppayment_bar_id, MenuItem backup_bar_id,
            MenuItem restore_bar_id, MenuItem project_bar_id, MenuItem Qsales_bar_id) {
        try {
            String sql = "SELECT Admin_Type FROM createadmin WHERE id='" + userid + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                int admin_type = Integer.parseInt(rs.getString("Admin_Type")), swh;
                if (admin_type == 0) {
                    String form_name;
                    admin_btn_id.setDisable(true);
                    admin_bar_id.setDisable(true);
                    priority_bar_id.setDisable(true);

                    String sql1 = "SELECT * FROM access_priority WHERE admin_id = '" + userid + "'";
                    rs = queryFunction.getResult(sql1);
                    while (rs.next()) {
                        form_name = rs.getString("form_name");
                        swh = Integer.parseInt(rs.getString("switch"));
                        switch (form_name) {
                            case "Sales":
                                if (swh == 0) {
                                    sales_btn_id.setDisable(true);
                                    sales_bar_id.setDisable(true);
                                    Qsales_bar_id.setDisable(true);
                                }
                                break;
                            case "Purchase":
                                if (swh == 0) {
                                    purchase_btn_id.setDisable(true);
                                    purchase_bar_id.setDisable(true);
                                }
                                break;
                            case "Product Information":
                                if (swh == 0) {
                                    product_btn_id.setDisable(true);
                                    product_bar_id.setDisable(true);
                                    item_bar_id.setDisable(true);
                                    brand_bar_id.setDisable(true);
                                    category_bar_id.setDisable(true);
                                }
                                break;
                            case "Stock":
                                if (swh == 0) {
                                    stock_btn_id.setDisable(true);
                                    damage_bar_id.setDisable(true);
                                }
                                break;
                            case "Company Information":
                                if (swh == 0) {
                                    company_btn_id.setDisable(true);
                                    company_bar_id.setDisable(true);
                                }
                                break;
                            case "Employee Information":
                                if (swh == 0) {
                                    employee_btn_id.setDisable(true);
                                    employee_bar_id.setDisable(true);
                                }
                                break;
                            case "Employee Salary":
                                if (swh == 0) {
                                    salary_btn_id.setDisable(true);
                                    salary_bar_id.setDisable(true);
                                }
                                break;
                            case "Supplier Information":
                                if (swh == 0) {
                                    supplier_btn_id.setDisable(true);
                                }
                                break;
                            case "Bank Information":
                                if (swh == 0) {
                                    bank_btn_id.setDisable(true);
                                }
                                break;
                            case "Bank Transaction":
                                if (swh == 0) {
                                    transaction_btn_id.setDisable(true);
                                }
                                break;
                            case "Daily Cost":
                                if (swh == 0) {
                                    daily_btn_id.setDisable(true);
                                }
                                break;
                            case "Customer Information":
                                if (swh == 0) {
                                    customer_bar_id.setDisable(true);
                                    customer_btn_id.setDisable(true);
                                }
                                break;
                            case "Branch Information":
                                if (swh == 0) {
                                    branch_bar_id.setDisable(true);
                                }
                                break;
                            case "Income/expense":
                                if (swh == 0) {
                                    income_ex_bar_id.setDisable(true);
                                }
                                break;
                            case "Measurement info":
                                if (swh == 0) {
                                    measurement_bar_id.setDisable(true);
                                }
                                break;
                            case "Employee Salary Setup":
                                if (swh == 0) {
                                    Ssetup_bar_id.setDisable(true);
                                }
                                break;
                            case "Report View":
                                if (swh == 0) {
                                    report_bar_id.setDisable(true);
                                }
                                break;
                            case "Sales Return":
                                if (swh == 0) {
                                    Sreturn_bar_id.setDisable(true);
                                }
                                break;
                            case "Purchase Return":
                                if (swh == 0) {
                                    Preturn_bar_id.setDisable(true);
                                }
                                break;
                            case "Sales Payment Statement":
                                if (swh == 0) {
                                    Spayment_bar_id.setDisable(true);
                                }
                                break;
                            case "Purchase Payment Statement":
                                if (swh == 0) {
                                    Ppayment_bar_id.setDisable(true);
                                }
                                break;
                            case "Database Backup":
                                if (swh == 0) {
                                    backup_bar_id.setDisable(true);
                                }
                                break;
                            case "Database Restore":
                                if (swh == 0) {
                                    restore_bar_id.setDisable(true);
                                }
                                break;
                            case "Project Information":
                                if (swh == 0) {
                                    project_bar_id.setDisable(true);
                                }
                        }
                    }
                }
            }

        } catch (Exception e) {
            service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
        }
    }

    public void ShowAvatar(ImagePattern pattern1, Circle circle) {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            Image imagex;
                            ImagePattern pattern = pattern1;
                            try {
                                String sql = "SELECT * FROM createadmin,project_info WHERE createadmin.id = '" + userid + "'";
                                rs = queryFunction.getResult(sql);
                                if (rs.next()) {
                                    try {
                                        InputStream is = rs.getBinaryStream("createadmin.image");
                                        String Tpath = rs.getString("project_info.image_path");
                                        String path = Tpath + "\\" + "avatar.jpg";
                                        OutputStream os = new FileOutputStream(new File(path));
                                        byte[] content = new byte[1096];
                                        int size = 0;
                                        while ((size = is.read(content)) != -1) {
                                            try {
                                                os.write(content, 0, size);
                                            } catch (IOException e) {
                                            }
                                        }
                                        os.close();
                                        is.close();
                                        imagex = new Image("file:" + path, 200, 180, true, true);
                                        try {
                                            pattern = new ImagePattern(imagex);
                                            circle.setFill(pattern);
                                        } catch (Exception e) {
                                        }
                                    } catch (IOException e) {
                                    }

                                }

                            } catch (Exception e) {
                            } finally {
                                try {
                                    rs.close();
                                    queryFunction.post.close();
                                } catch (Exception e) {
                                }
                            }
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((event) -> {
            service.cancel();
        });
    }

    public void Salesinitview(String query, TableView tableView, ObservableList data) {
        try {
            String sql = query;
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                data.add(new todaySalesView(rs.getString("customer_name"),
                        rs.getString("total_item"), rs.getString("sale_payment_statement.payment"),
                        rs.getString("sale_payment_statement.due")
                ));

            }
            tableView.setItems(data);
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "View Unsuccessful\n" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public void initview(String query, TableView tableView, ObservableList data) {
        try {
            String sql = query;
            rs = queryFunction.getResult(sql);
            while (rs.next()) {

                data.add(new CurrentStockView(rs.getString("product_name"),
                        rs.getString("quantity"), rs.getString("sub_unit_name"), rs.getString("sale_price")
                ));

            }
            tableView.setItems(data);
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "View Unsuccessful\n" + e);
        } finally {
            try {
                queryFunction.post.close();
                rs.close();
            } catch (Exception e) {

            }
        }
    }

    public void backup(String Path, Label pathselection_msg_print) {
        String dbName = connection_Sql.dbName;
        String dbUser = connection_Sql.dbUser;
        String dbPass = connection_Sql.dbPass;
        Process p = null;
        try {
            if (Path != null) {
                if (!Path.contains(" ")) {
                    String executeCmd = "C:/xampp/mysql/bin/mysqldump -u" + dbUser + " -p" + dbPass + " --add-drop-database -B " + dbName + " -r" + Path;
                    p = Runtime.getRuntime().exec(executeCmd);
                    int processComplete = p.waitFor();
                    if (processComplete == 0) {
                        pathselection_msg_print.setText("Backup Created Successful.");
                    } else {
                        pathselection_msg_print.setText("Can't Create Backup.");
                    }
                } else {
                    queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a blank space in this path.");
                }

            } else {
                queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "At first you should Browse Path.");
            }
        } catch (Exception e) {
            queryFunction.service.msg.ErrorMessage("Unsuccessful", "Error", "Have a Error!\n" + e);
        }
    }

    public void Restore(String Path, Label pathselection_msg_print1) {
        String dbUserName = connection_Sql.dbUser;
        String dbName = connection_Sql.dbName;
        String dbPassword = connection_Sql.dbPass;
        Process runtimeProcess;
        try {
            String[] restoreCmd = new String[]{"C:/xampp/mysql/bin/mysql", dbName, "-u" + dbUserName, "-p" + dbPassword, "-e", " source " + Path};
            runtimeProcess = Runtime.getRuntime().exec(restoreCmd);
            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                pathselection_msg_print1.setText("Restored successfully!");

            } else {
                pathselection_msg_print1.setText("Could not restore the backup!");
            }

        } catch (Exception ex) {
            queryFunction.service.msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem!" + ex);
        }
    }

    public void clickedData(TableView tableView, Pane popup_node, Label pop_level, AnchorPane ap, MouseEvent event) {
        try {
            if (event.getClickCount() == 1) {
                @SuppressWarnings("rawtypes")
                TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                int col = pos.getColumn();
                @SuppressWarnings("rawtypes")
                TableColumn column = pos.getTableColumn();
                String val = column.getCellData(row).toString();
                if (col == 0) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        int size = val.length();
                        int width = size * 10;
                        popup_node.setPrefWidth(width);
                        if (!pop_level.isVisible()) {
                            pop_level.setVisible(true);
                        }
                        pop_level.setText(val);
                        OpenPopup(popup_node, ap);
                    }

                }
            }
        } catch (Exception e) {
        }
    }

    public void OpenPopup(Pane popup_node, AnchorPane ap) {
        JFXPopup p = new JFXPopup();
        p.setPopupContent(popup_node);
        p.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_TOP_LEFT);
        p.show(ap);
    }

    public void Notify(String title) {
//        FontAwesomeIconView awesomeIconView = new FontAwesomeIconView(FontAwesomeIcon.BATTERY_4);
        Notifications notificationBuilder = Notifications.create()
                .title("Digital Shop: Product Short")
                .text(title).position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(20)).
                onAction((event) -> {

                });
        notificationBuilder.showInformation();

    }

    public void showTrayMessage(String title, String message) {
        try {
            Notify(message);
            if (SystemTray.isSupported()) {
                SystemTray tray = SystemTray.getSystemTray();
                BufferedImage image = ImageIO.read(getClass().getResource("/fxsupershop/Image/icon.png"));
                TrayIcon trayIcon = new TrayIcon(image, "Digital Shop");
                trayIcon.addActionListener((e) -> {
                    System.exit(1);
                });
                trayIcon.setImageAutoSize(true);
                trayIcon.setToolTip("Digital Shop");
                tray.add(trayIcon);
                trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
                tray.remove(trayIcon);
            } else {
                Notify(message);
            }
        } catch (Exception exp) {
            queryFunction.service.msg.ErrorMessage("Unsuccessful", "Error", "Have a Problem!\n" + exp);
        }
    }

    public void productNotify() {
        double stockQuan, pro_short_Quan, pro_over_Quan, proQuan = 0, pro_over = 0;
        try {
            String sql = "SELECT stock_product.*,product_productinfo.* FROM stock_product "
                    + "INNER JOIN product_productinfo ON stock_product.product_id = "
                    + "product_productinfo.id";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                if (rs.getString("stock_product.quantity") == null) {
                    stockQuan = 0;
                } else {
                    stockQuan = Double.parseDouble(rs.getString("stock_product.quantity"));
                }
                if (rs.getString("product_productinfo.shortage_list") == null) {
                    pro_short_Quan = 0;
                } else {
                    pro_short_Quan = Double.parseDouble(rs.getString("product_productinfo.shortage_list"));
                }
                if (rs.getString("product_productinfo.over_stock") == null) {
                    pro_over_Quan = 0;
                } else {
                    pro_over_Quan = Double.parseDouble(rs.getString("product_productinfo.over_stock"));
                }
                if (stockQuan <= pro_short_Quan) {
                    proQuan++;
                } else if (stockQuan > pro_over_Quan) {
                    pro_over++;
                }
            }
            if (proQuan > 0) {
                showTrayMessage("Digital Shop: Product Quantity Information", proQuan + " Products less than decided quantity.");
            }
            if (pro_over > 0) {
                Notify(pro_over + " Products have over stock.");
            }
        } catch (Exception e) {
        }
    }

    public void productNotification(String col) {
        double stockQuan, pro_short_Quan, pro_over_Quan, proQuan = 0, pro_over = 0;
        String shortname = null, overname = null;
        try {
            String sql = "SELECT stock_product.*,product_productinfo.* FROM stock_product "
                    + "INNER JOIN product_productinfo ON stock_product.product_id = "
                    + "product_productinfo.id WHERE stock_product.product_id = '" + col + "'";
            rs = queryFunction.getResult(sql);
            if (rs.next()) {
                if (rs.getString("stock_product.quantity") == null) {
                    stockQuan = 0;
                } else {
                    stockQuan = Double.parseDouble(rs.getString("stock_product.quantity"));
                }
                if (rs.getString("product_productinfo.shortage_list") == null) {
                    pro_short_Quan = 0;
                } else {
                    pro_short_Quan = Double.parseDouble(rs.getString("product_productinfo.shortage_list"));
                }
                if (rs.getString("product_productinfo.over_stock") == null) {
                    pro_over_Quan = 0;
                } else {
                    pro_over_Quan = Double.parseDouble(rs.getString("product_productinfo.over_stock"));
                }
                if (stockQuan <= pro_short_Quan) {
                    proQuan++;
                    shortname = rs.getString("product_name");
                } else if (stockQuan > pro_over_Quan) {
                    pro_over++;
                    overname = rs.getString("product_name");
                }
            }
            if (proQuan > 0) {
                showTrayMessage("Digital Shop: Product Quantity Information", shortname + " Products less than decided quantity.");
            }
            if (pro_over > 0) {
                Notify(overname + " Products have over stock.");
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        //System.runFinalization();
    }

}

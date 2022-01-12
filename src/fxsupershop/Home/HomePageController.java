package fxsupershop.Home;

import animatefx.animation.*;
import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.*;
import fxsupershop.FxSuperShop;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import fxsupershop.TableView.CurrentStockView;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class HomePageController implements Initializable {

    @FXML
    public JFXTabPane tb11;
    ResultSet rs;
    @FXML
    private TableColumn<CurrentStockView, String> proname_col;
    @FXML
    private TableColumn<CurrentStockView, String> quanName_col;
    @FXML
    private TableColumn<CurrentStockView, String> purchesPrice_col;
    ObservableList<CurrentStockView> data = FXCollections.observableArrayList();
    ObservableList<todaySalesView> todayData = FXCollections.observableArrayList();
    @FXML
    private Pane backup_pane;
    @FXML
    private TextField path_textfield;
    @FXML
    private Label pathselection_msg_print;
    private String Path = null;
    String filename;
//    @FXML
//    private Label pathlabel;
//    @FXML
//    private JFXButton pathbtn;
//    @FXML
//    private JFXButton backupbtn;
    int check = 0;
    @FXML
    private AnchorPane manubtnpane;
    @FXML
    private AnchorPane stagePane;

//    @FXML
//    private MenuItem webmenu;
    @FXML
    private Button tabopenID;
    @FXML
    private JFXTextField searchfield_id;
    @FXML
    private TableView<CurrentStockView> tableview;
    @FXML
    private Tab backupID;
    @FXML
    private Tab restoreID;
    @FXML
    private TextField path_textfield1;
//    @FXML
//    private Label pathlabel1;
//    @FXML
//    private JFXButton pathbtn1;
    @FXML
    private Label pathselection_msg_print1;
    @FXML
    private JFXTabPane backup_tab_pane;
//    @FXML
//    private JFXButton rstorebtn;
    Message msg = new Message();
    @FXML
    private Pane popup_node;
    @FXML
    private Label pop_level;
    @FXML
    private AnchorPane current_stock_pane;
    @FXML
    private Pane quit_pane;
    private JFXPopup quit = new JFXPopup();
    private ActionEvent e;
//    @FXML
//    private MenuItem home_menu;
    GeneralService generalService = new GeneralService();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    private Presenter Tab = new Presenter(queryFunction);
    @FXML
    private ImageView ref_img;
    @FXML
    private MenuItem admin_bar_id;
    @FXML
    private MenuItem customer_bar_id;
    @FXML
    private MenuItem branch_bar_id;
    @FXML
    private MenuItem company_bar_id;
    @FXML
    private MenuItem income_ex_bar_id;
    @FXML
    private Menu measurement_bar_id;
    @FXML
    private MenuItem employee_bar_id;
    @FXML
    private MenuItem report_bar_id;
    @FXML
    private MenuItem product_bar_id;
    @FXML
    private MenuItem item_bar_id;
    @FXML
    private MenuItem brand_bar_id;
    @FXML
    private MenuItem category_bar_id;
    @FXML
    private MenuItem damage_bar_id;
    @FXML
    private MenuItem Sreturn_bar_id;
    @FXML
    private MenuItem Preturn_bar_id;
    @FXML
    private MenuItem Spayment_bar_id;
    @FXML
    private MenuItem Ppayment_bar_id;
    @FXML
    private MenuItem backup_bar_id;
    @FXML
    private MenuItem restore_bar_id;
    @FXML
    private MenuItem priority_bar_id;
    @FXML
    private JFXButton admin_btn_id;
    @FXML
    private JFXButton sales_btn_id;
    @FXML
    private JFXButton purchase_btn_id;
    @FXML
    private JFXButton product_btn_id;
    @FXML
    private JFXButton stock_btn_id;
    @FXML
    private JFXButton company_btn_id;
    @FXML
    private JFXButton employee_btn_id;
    @FXML
    private JFXButton salary_btn_id;
    @FXML
    private JFXButton supplier_btn_id;
    @FXML
    private JFXButton bank_btn_id;
    @FXML
    private JFXButton transaction_btn_id;
    @FXML
    private JFXButton daily_btn_id;
    LoginMultiFormController loginMultiFormController = new LoginMultiFormController();
    @FXML
    private JFXButton log_out_btn_id;
    @FXML
    private JFXButton customer_btn_id;
    @FXML
    private MenuItem Ssetup_bar_id;
    @FXML
    private MenuItem salary_bar_id;
//    @FXML
//    private Circle circle;
//    @FXML
//    private JFXButton profile_btn;
//    private static int userid;
//    private ImagePattern pattern;
    @FXML
    private Label request_label;
//    @FXML
//    private MenuItem Spayment_bar_id1;
//    @FXML
//    private MenuItem Preturn_bar_id1;
    @FXML
    private MenuItem project_bar_id;
    @FXML
    private MenuItem sales_bar_id;
    @FXML
    private MenuItem purchase_bar_id;
    @FXML
    private MenuItem Qsales_bar_id;
//    @FXML
//    private MenuItem vat_bar_id;
    @FXML
    private Circle noti_shape;
//    @FXML
//    private JFXButton notification_btn;
    @FXML
    private Label noti_lavel;
//    @FXML
//    private MenuItem Pinvoice_bar_id;
//    @FXML
//    private MenuItem Sinvoice_list;
    @FXML
    private HBox sc_menu_vox;
    @FXML
    private FontAwesomeIconView menu_icon;
//    @FXML
//    private JFXButton BTN1;
//    @FXML
//    private JFXButton BTN2;
//    @FXML
//    private JFXButton BTN3;
//    @FXML
//    private JFXButton BTN4;
//    @FXML
//    private JFXButton BTN5;
//    @FXML
//    private JFXButton BTN6;
//    @FXML
//    private JFXButton BTN7;
//    @FXML
//    private VBox home_stack;
//    @FXML
//    private MenuItem suspension_bar_id;
    @FXML
    private TableColumn<?, ?> customer_col;
    @FXML
    private TableColumn<?, ?> item_col;
    @FXML
    private TableColumn<?, ?> paid_col;
    @FXML
    private TableColumn<?, ?> due_col;
    @FXML
    private TableView<?> today_sales_view;
//    @FXML
//    private MenuItem Ppayment_bar_id_sup;
//    @FXML
//    private MenuItem Spayment_bar_idCus;
//    @FXML
//    private MenuItem merchandised_bar_id;
    int notification_check = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new FadeInUpBig(manubtnpane).play();
//        scheduledService.setDelay(Duration.seconds(1));
        scheduledService.start();
        scheduledService.setOnSucceeded((event) -> {
            scheduledService.cancel();
        });
    }

    Service<Void> scheduledService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() {
                    initResource();
                    return null;
                }
            };
        }
    };

    private void initResource() {
//        userid = loginMultiFormController.User();
        Platform.runLater(() -> {
            Tab.PriorityRender(admin_btn_id, sales_btn_id, sales_bar_id, purchase_btn_id, purchase_bar_id, product_btn_id, stock_btn_id,
                    company_btn_id, employee_btn_id, salary_btn_id, supplier_btn_id, bank_btn_id, transaction_btn_id,
                    daily_btn_id, admin_bar_id, priority_bar_id, customer_bar_id, branch_bar_id, company_bar_id,
                    income_ex_bar_id, measurement_bar_id, employee_bar_id, salary_bar_id, Ssetup_bar_id,
                    customer_btn_id, report_bar_id, product_bar_id, item_bar_id, brand_bar_id, category_bar_id,
                    damage_bar_id, Sreturn_bar_id, Preturn_bar_id, Spayment_bar_id, Ppayment_bar_id, backup_bar_id,
                    restore_bar_id, project_bar_id, Qsales_bar_id);
//            showAvatar();
            tb11.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
            view();
            todaySales();
            tabopenID.setDisable(true);
//            ss.setDelay(Duration.minutes(5));
//            ss.start();
//            ss.setPeriod(Duration.minutes(5));
//            ProductNotification.setDelay(Duration.hours(2));
//            ProductNotification.start();
//            ProductNotification.setPeriod(Duration.hours(2));
            popMenu();
            Tab.productNotify();
            notification_check++;
            noti_shape.setVisible(true);
            noti_lavel.setText(String.valueOf(notification_check));
        });

    }

//    ScheduledService<Void> ss = new ScheduledService<Void>() {
//        @Override
//        protected Task<Void> createTask() {
//            return new Task<Void>() {
//                @Override
//                protected Void call() {
//                    Platform.runLater(() -> {
//                        view();
//                        todaySales();
//                        showAvatar();
//                    });
//                    return null;
//                }
//            };
//        }
//    };

//    ScheduledService<Void> ProductNotification = new ScheduledService<Void>() {
//        @Override
//        protected Task<Void> createTask() {
//            return new Task<Void>() {
//                @Override
//                protected Void call() {
//                    Platform.runLater(() -> {
//                        Tab.productNotify();
//                        notification_check++;
//                        noti_shape.setVisible(true);
//                        noti_lavel.setText(String.valueOf(notification_check));
//                    });
//                    return null;
//                }
//            };
//        }
//    };

//    Stage window1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
//    window1.setOnHiding(new EventHandler<WindowEvent>() {
//    @Override
//    public void handle(WindowEvent event) {
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                msg.InformationMessage("sample", "sample", "sample");
//            }
//        });
//    }
//});
    public void showAvatar() {
//        Tab.ShowAvatar(pattern, circle);
    }

    private void initview(String query) {
        Tab.initview(query, tableview, data);
    }

    private void view() {
        data.clear();
        proname_col.setCellValueFactory(new PropertyValueFactory<>("proName"));
        quanName_col.setCellValueFactory(new PropertyValueFactory<>("quan"));
        purchesPrice_col.setCellValueFactory(new PropertyValueFactory<>("purch_price"));

        String sql = "SELECT product_measurement_subunit.sub_unit_name,stock_product.id,stock_product.quantity,stock_product."
                + "purchase_price,stock_product.sale_price,product_productinfo.product_name,product_productinfo.measurement_type FROM stock_product\n"
                + "INNER JOIN product_productinfo  ON stock_product.product_id=product_productinfo.id "
                + "INNER JOIN `product_measurement_subunit` ON product_productinfo.measurement_type="
                + "product_measurement_subunit.id order by stock_product.id asc";
        initview(sql);
    }

    private void todaySales() {
        todayData.clear();
        customer_col.setCellValueFactory(new PropertyValueFactory<>("cus_name"));
        item_col.setCellValueFactory(new PropertyValueFactory<>("item"));
        paid_col.setCellValueFactory(new PropertyValueFactory<>("paid"));
        due_col.setCellValueFactory(new PropertyValueFactory<>("due"));
        String sql = "SELECT customer_info.customer_name,sale_ledger.*,"
                + "sale_payment_statement.payment,sale_payment_statement.due FROM sale_ledger "
                + "INNER JOIN customer_info ON sale_ledger.customer_id = customer_info.id "
                + "INNER JOIN sale_payment_statement ON sale_ledger.invoice_id = sale_payment_statement.invoice_no"
                + " WHERE sale_ledger.invoice_date = '" + queryFunction.service.getnonFormatCurrentDate() + "'";
        Tab.Salesinitview(sql, today_sales_view, todayData);
    }

    private void search() {
        data.clear();

        String sql = "SELECT product_measurement_subunit.sub_unit_name,stock_product.id,stock_product.quantity,stock_product.purchase_price,"
                + "stock_product.sale_price,product_productinfo.product_name,product_productinfo.measurement_type FROM stock_product "
                + "INNER JOIN product_productinfo ON stock_product.product_id=product_productinfo.id INNER JOIN `product_measurement_subunit` ON product_productinfo.measurement_type="
                + "product_measurement_subunit.id WHERE "
                + "stock_product.id LIKE '%" + searchfield_id.getText() + "%' OR product_productinfo.product_name LIKE '%" + searchfield_id.getText() + "%' order by stock_product.id asc";
        initview(sql);
    }

    private static void getWinCalculator() {
        try {
            Runtime.getRuntime().exec("calc");
        } catch (final IOException e) {
            System.out.println("[dan]: stacktrace = " + e.getMessage().toString());
        }

    }

    private void QuickMenu() {
        try {
            if (!sc_menu_vox.isVisible()) {
                sc_menu_vox.setVisible(true);
                SlideInDown down = new SlideInDown(sc_menu_vox);
                down.setSpeed(2).play();
                down.setOnFinished((e) -> {
                    menu_icon.setIcon(FontAwesomeIcon.ARROW_CIRCLE_UP);
                });
            } else if (sc_menu_vox.isVisible()) {
                SlideOutUp up = new SlideOutUp(sc_menu_vox);
                up.setSpeed(2).play();
                up.setOnFinished((e1) -> {
                    menu_icon.setIcon(FontAwesomeIcon.ARROW_CIRCLE_DOWN);
                    sc_menu_vox.setVisible(false);
                });
            }
        } catch (Exception e) {
        }
    }

    private ContextMenu Menu() {
        ContextMenu cm = new ContextMenu();
        MenuItem item = new MenuItem("Quick Sales");
        cm.getItems().add(item);
        item.setOnAction((event) -> {
            QuickSales(event);
        });
        return cm;
    }

    private void popMenu() {
        stagePane.setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                tb11.setContextMenu(Menu());
            }
        });
    }

    @FXML
    private void Sales_Info(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Sales/Sales_Info.fxml", "Sales Information", "01", tb11);
    }

    @FXML
    private void ItemInfo(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Product/Item_info.fxml", "Item Information", "02", tb11);
    }

    @FXML
    private void Admininfo(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Admin/user_admin.fxml", "Admin Information", "03", tb11);
    }

    @FXML
    private void Search(ActionEvent event) {
        popup_node.setPrefWidth(200);
        pop_level.setVisible(false);
        if (!searchfield_id.isVisible()) {
            searchfield_id.setVisible(true);
        }
        Tab.OpenPopup(popup_node, current_stock_pane);
    }

    @FXML
    private void BrandInfo(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Product/Brand_info.fxml", "Brand Information", "04", tb11);
    }

    @FXML
    private void CategoryInfo(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Product/Category_info.fxml", "Category Information", "05", tb11);
    }

    @FXML
    private void PurchaseInfo(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Purchase/Purchase_info.fxml", "Purchase Information", "06", tb11);
    }

    @FXML
    private void ProductInfo(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Product/Product_info.fxml", "Product Information", "07", tb11);
    }

    @FXML
    private void StockInfo(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Stock/Stock_Info.fxml", "Stock Information", "08", tb11);
    }

    @FXML
    private void EmployeeInfo(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Employee_info/Employee_Info.fxml", "Employee Information", "09", tb11);
    }

    @FXML
    private void EmployeeSalaryinfo(ActionEvent event) throws IOException {
        String path = "/fxsupershop/Employee_Salary/Employee_Salary.fxml";
        Tab.getTab(path, "Employee Salary", "23", tb11);
    }

    @FXML
    private void SupplierInfo(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Supplier/Supplier_info.fxml", "Supplier Information", "10", tb11);
    }

    @FXML
    private void BankInfo(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Bank_Info/Bank_Info.fxml", "Bank Information", "11", tb11);
    }

    @FXML
    private void Banktransaction(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Bank_Transaction/Bank_Transaction.fxml", "Bank Transaction", "12", tb11);
    }

    @FXML
    private void Logoutbtn(ActionEvent event) {
        check = 2;
        request_label.setText("Are you sure you want to LogOut?");
        Tab.PopUP(quit_pane, stagePane, quit);
        e = event;
    }

    @FXML
    private void companyinfo_btn(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Company/company.fxml", "Company Information", "14", tb11);

    }

    @FXML
    private void customer_btn(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Customer/customer_info.fxml", "Customer Information", "15", tb11);
    }

    @FXML
    private void Brance_info(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Company/Brance_Info.fxml", "Brance Information", "16", tb11);
    }

    @FXML
    private void income_expense_btn(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Income_Expense/income_expense.fxml", "Income Expense Source", "17", tb11);
    }

    @FXML
    private void Measurment_btn(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Measurment/Mesurement_Info.fxml", "Measurement Information", "18", tb11);
    }

    @FXML
    private void close_tab(ActionEvent event) {
        tb11.getTabs().clear();
        tb11.setVisible(false);
        viewallbtn(event);
    }

    @FXML
    private void BackUp(ActionEvent event) {
        backup_pane.setVisible(true);
        new ZoomIn(backup_pane).setSpeed(2).play();
        backup_tab_pane.getSelectionModel().select(backupID);
    }

    @FXML
    private void backupPaneClose(ActionEvent event) {
        ZoomOut zoom = new ZoomOut(backup_pane);
        zoom.setOnFinished((e) -> {
            backup_pane.setVisible(false);
        });
        zoom.setSpeed(2).play();
    }

    @FXML
    private void PathSelectionbtn(ActionEvent event) {
        FileChooser fc;
        Date date = Date.valueOf(LocalDate.now());
        fc = generalService.getSaveChooser("Select Location", date + "_SQL_Database");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SQL File", "*.sql"));

        Path = generalService.getSaveDialogPath(fc);
        path_textfield.setText(Path);
        Path = path_textfield.getText();
    }

    @FXML
    private void Backupbtn(ActionEvent event) {
        Tab.backup(Path, pathselection_msg_print);
    }

    @FXML
    private void webbtn(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/WebView/WebLink.fxml", "Web Browse", "19", tb11);
    }

    @FXML
    private void WebReport(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Report/Localhost_report.fxml", "Report View", "20", tb11);
    }

    @FXML
    private void HomeAction(ActionEvent event) {
        tb11.setVisible(false);
        if (tb11.getTabs().isEmpty() == false) {
            tabopenID.setDisable(false);
            tabopenID.requestFocus();
        } else {
            tabopenID.setDisable(true);
        }
        viewallbtn(event);
    }

    @FXML
    private void viewallbtn(ActionEvent event) {
        view();
        todaySales();
        showAvatar();
    }

    @FXML
    private void TabOpen(ActionEvent event) {
        if (tb11.getTabs().isEmpty() == false) {
            tb11.setVisible(true);
            tabopenID.setDisable(true);
        }
    }

    @FXML
    private void SearchKeyR(KeyEvent event) {
        search();
    }

    @FXML
    private void ClosePlatform(ActionEvent event) {
        check = 1;
        request_label.setText("Are you sure you want to Quit?");
        Tab.PopUP(quit_pane, stagePane, quit);

    }

    @FXML
    private void Restore(ActionEvent event) {
        backup_pane.setVisible(true);
        new ZoomIn(backup_pane).setSpeed(2).play();
        backup_tab_pane.getSelectionModel().select(restoreID);
    }

    @FXML
    private void Restoreaction(ActionEvent event) {
        Tab.Restore(Path, pathselection_msg_print1);
    }

    @FXML
    private void SalesReturnAction(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Sales_Return/SalesReturn.fxml", "Sales Return", "21", tb11);
    }

    @FXML
    private void PurchaseReturnAction(ActionEvent event) throws IOException {

        Tab.getTab("/fxsupershop/Purchase_Return/PurchaseReturn.fxml", "Purchase Return", "22", tb11);
    }

    @FXML
    private void getData(MouseEvent event) {
        searchfield_id.setVisible(false);
        Tab.clickedData(tableview, popup_node, pop_level, current_stock_pane, event);
    }

    @FXML
    private void QuitNoAction(ActionEvent event) {
        try {
            quit.hide();
        } catch (Exception e) {
        }
    }

    @FXML
    private void QuitYesAction(ActionEvent event) {
        if (check == 1) {
            Platform.exit();
        }
        if (check == 2) {
            try {
                Stage window1 = (Stage) ((Node) e.getSource()).getScene().getWindow();
                window1.close();
                FxSuperShop b = new FxSuperShop();
                b.start(new Stage());

            } catch (Exception f) {

            }
        }

    }

    @FXML
    private void CalculatorAction(ActionEvent event) {
        getWinCalculator();
    }

    @FXML
    private void SalarySetup(ActionEvent event) {
        String path = "/fxsupershop/Employee_salarySetup/Salary_Setup.fxml";
        Tab.getTab(path, "Employer Salary Setup", "24", tb11);
    }

    @FXML
    private void PathSelectionRbtn(ActionEvent event) {
        FileChooser fc;
        Date date = Date.valueOf(LocalDate.now());
        fc = generalService.getOpenChooser("Select Location");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SQL File", "*.sql"));

        Path = generalService.getOpenDialogPath(fc);
        path_textfield1.setText(Path);
        Path = path_textfield1.getText();
    }

    @FXML
    private void EditorAction(ActionEvent event) {
        String path = "/fxsupershop/Editor/Text_edit.fxml";
        Tab.getTab(path, "Editor", "25", tb11);
    }

    @FXML
    private void sales_payment(ActionEvent event) {
        String path = "/fxsupershop/Sales/Sales_payment.fxml";
        Tab.getTab(path, "Sales Payment", "28", tb11);
    }

    @FXML
    private void purchase_payment(ActionEvent event) {
        String path = "/fxsupershop/Purchase/Purchase_payment.fxml";
        Tab.getTab(path, "Purchase Payment", "26", tb11);
    }

    @FXML
    private void SubunitAction(ActionEvent event) {
        String path = "/fxsupershop/Measurment/Mesurement_subunit.fxml";
        Tab.getTab(path, "Measurement Subunit", "27", tb11);
    }

    @FXML
    private void ref_hover(MouseEvent event) {
        new RotateIn(ref_img).play();
    }

    @FXML
    private void damage_stock(ActionEvent event) {
        String path = "/fxsupershop/Stock/damage_stock.fxml";
        Tab.getTab(path, "Damage Stock", "29", tb11);
    }

    @FXML
    private void priorityAction(ActionEvent event) {
        String path = "/fxsupershop/AdminPriority/Priority_list.fxml";
        Tab.getTab(path, "Priority Set", "30", tb11);
    }

    @FXML
    private void LogOutFire(ActionEvent event) {
        log_out_btn_id.fire();
    }

    @FXML
    private void Profile(ActionEvent event) {
        String path = "/fxsupershop/Admin/Profile.fxml";
        Tab.getTab(path, "My Information", "31", tb11);
    }

    @FXML
    private void ProfilebtnAction(ActionEvent event) {
        String path = "/fxsupershop/Admin/Profile.fxml";
        Tab.getTab(path, "My Information", "31", tb11);
    }

    @FXML
    private void Project_info(ActionEvent event) {
        String path = "/fxsupershop/Project_info/Project_details.fxml";
        Tab.getTab(path, "Project Information", "32", tb11);
    }

    @FXML
    private void QuickSales(ActionEvent event) {
        String path = "/fxsupershop/Sales/Simple_sales_form.fxml";
        Tab.getTab(path, "Quick Sales", "33", tb11);
    }

    @FXML
    private void Vat(ActionEvent event) {
        String path = "/fxsupershop/Vat/vat.fxml";
        Tab.getTab(path, "Vat", "34", tb11);
    }

    @FXML
    private void ExpenseEntry(ActionEvent event) {
        String path = "/fxsupershop/Income_Expense_Entry/Income_Expense.fxml";
        Tab.getTab(path, "Daily Transaction", "35", tb11);
    }

    @FXML
    private void IncomeEntry(ActionEvent event) {
        ExpenseEntry(event);
    }

    @FXML
    private void purchase_invoice(ActionEvent event) {
        String path = "/fxsupershop/Purchase_Report/Purchase_invoice.fxml";
        Tab.getTab(path, "Purchase Invoice", "37", tb11);
    }

    @FXML
    private void SalesInvoice(ActionEvent event) {
        String path = "/fxsupershop/SalesReport/SalesInvoice.fxml";
        Tab.getTab(path, "Sales Invoice", "38", tb11);
    }

    @FXML
    private void IncomeInvoice(ActionEvent event) {
        String path = "/fxsupershop/Income_expense_invoice/income_invoice.fxml";
        Tab.getTab(path, "Income Invoice", "39", tb11);
    }

    @FXML
    private void ExpenseInovice(ActionEvent event) {
        String path = "/fxsupershop/Income_expense_invoice/Expense.fxml";
        Tab.getTab(path, "Expense Invoice", "40", tb11);
    }

    @FXML
    private void Suspension(ActionEvent event) {
        String path = "/fxsupershop/Suspension/suspension.fxml";
        Tab.getTab(path, "Suspension", "41", tb11);
    }

    @FXML
    private void sc_menu(ActionEvent event) {
        QuickMenu();
    }

    @FXML
    private void quickMenuAction(SwipeEvent event) {
        QuickMenu();
    }

    @FXML
    private void purchase_payment_supplier(ActionEvent event) {
        String path = "/fxsupershop/Purchase/Supplier_wise_payment.fxml";
        Tab.getTab(path, "Supplier wise Payment", "42", tb11);
    }

    @FXML
    private void sales_CUSpayment(ActionEvent event) {
        String path = "/fxsupershop/Sales_cus_pay/Customer_payment.fxml";
        Tab.getTab(path, "Customer wise Payment", "43", tb11);
    }

    @FXML
    private void MerchandisedAction(ActionEvent event) {
        String path = "/fxsupershop/Most_saleProduct/Most_productList.fxml";
        Tab.getTab(path, "Most Merchandised", "44", tb11);
    }

    public void Notification() {
        String path = "/fxsupershop/Notification/notification.fxml";
        Tab.getTab(path, "NOTIFICATION PANEL", "45", tb11);
        notification_check = 0;
        noti_lavel.setText(null);
        noti_shape.setVisible(false);
    }

    @FXML
    private void notification(ActionEvent event) {
        Notification();
    }

    @FXML
    private void sales_ladger(ActionEvent event) {
    }

    @FXML
    private void purchaseLadger(ActionEvent event) {
    }

}

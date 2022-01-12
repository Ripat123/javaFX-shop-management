package fxsupershop.Notification;

import animatefx.animation.*;
import fxsupershop.Services.PrepareQueryFunction;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

/**
 * FXML Controller class
 *
 * @author Rifat Rabbi
 */
public class NotificationController implements Initializable {

//    @FXML
//    private AnchorPane itempane;
//    @FXML
//    private JFXButton shortID;
//    @FXML
//    private JFXButton overID;
    @FXML
    private AnchorPane short_card;
    @FXML
    private TableView<?> tableview;
    @FXML
    private TableColumn<?, ?> PRO_COL;
    @FXML
    private TableColumn<?, ?> QUAN_COL;
//    @FXML
//    private JFXButton refbtn;
//    @FXML
//    private JFXButton printbtn;
//    @FXML
//    private JFXButton back_short_btn;
    @FXML
    private AnchorPane over_card;
    @FXML
    private TableView<?> tableview1;
    @FXML
    private TableColumn<?, ?> PRO_COL1;
    @FXML
    private TableColumn<?, ?> QUAN_COL1;
//    @FXML
//    private JFXButton refbtn1;
//    @FXML
//    private JFXButton printbtn1;
//    @FXML
//    private JFXButton back_over_btn;
//    @FXML
//    private BorderPane mainID;
    ObservableList shortdata = FXCollections.observableArrayList();
    ObservableList overdata = FXCollections.observableArrayList();
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    ResultSet rs;
    @FXML
    private TableColumn<?, ?> SH_QUAN_COL2;
    @FXML
    private TableColumn<?, ?> OV_QUAN_COL;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private void initveiw(String query, ObservableList data, String col, int cond) {
        try {
            double stockQuan, pro_short_Quan;
            String sql = query;
            data.clear();
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                if (rs.getString("stock_product.quantity") == null) {
                    stockQuan = 0;
                } else {
                    stockQuan = Double.parseDouble(rs.getString("stock_product.quantity"));
                }
                if (rs.getString(col) == null) {
                    pro_short_Quan = 0;
                } else {
                    pro_short_Quan = Double.parseDouble(rs.getString(col));
                }
                if (cond == 1) {
                    if (stockQuan <= pro_short_Quan) {
                        data.add(new NModel(rs.getString("product_name"), rs.getString("stock_product.quantity"),
                                rs.getString(col)));
                    }
                } else {
                    if (stockQuan > pro_short_Quan) {
                        data.add(new NModel(rs.getString("product_name"), rs.getString("stock_product.quantity"),
                                rs.getString(col)));
                    }
                }

            }
            if (cond == 1) {
                tableview.setItems(data);
            } else {
                tableview1.setItems(data);
            }
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

    private void shortview() {
        PRO_COL.setCellValueFactory(new PropertyValueFactory<>("productName"));
        QUAN_COL.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        SH_QUAN_COL2.setCellValueFactory(new PropertyValueFactory<>("listquan"));

        String sql = "SELECT stock_product.*,product_productinfo.* FROM stock_product "
                + "INNER JOIN product_productinfo ON stock_product.product_id = "
                + "product_productinfo.id";
        initveiw(sql, shortdata, "product_productinfo.shortage_list", 1);
    }

    private void overView() {
        PRO_COL1.setCellValueFactory(new PropertyValueFactory<>("productName"));
        QUAN_COL1.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        OV_QUAN_COL.setCellValueFactory(new PropertyValueFactory<>("listquan"));

        String sql = "SELECT stock_product.*,product_productinfo.* FROM stock_product "
                + "INNER JOIN product_productinfo ON stock_product.product_id = "
                + "product_productinfo.id";
        initveiw(sql, overdata, "product_productinfo.over_stock", 2);
    }

    @FXML
    private void Clicked(MouseEvent event) {
    }

    @FXML
    private void Refresh(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        shortview();
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void Print(ActionEvent event) {
        
    }

    @FXML
    private void ShortAction(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        SlideInLeft left = new SlideInLeft();
                        left.setNode(short_card);
                        short_card.setVisible(true);
                        left.setSpeed(2);
                        left.play();
                        shortview();
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void OverAction(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        SlideInRight right = new SlideInRight();
                        right.setNode(over_card);
                        over_card.setVisible(true);
                        right.setSpeed(2);
                        right.play();
                        overView();
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void BackShortAction(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        SlideOutRight right = new SlideOutRight();
                        right.setNode(short_card);
                        right.setSpeed(2);
                        right.play();
                        right.setOnFinished((e) -> {
                            short_card.setVisible(false);
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void BackOverAction(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        SlideOutLeft left = new SlideOutLeft();
                        left.setNode(over_card);
                        left.setSpeed(2);
                        left.play();
                        left.setOnFinished((e) -> {
                            over_card.setVisible(false);
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void RefreshOver(ActionEvent event) {
        Service service = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() {
                        overView();
                        return null;
                    }
                };
            }
        };
        service.start();
        service.setOnSucceeded((e) -> {
            service.cancel();
        });
    }

    @FXML
    private void PrintOver(ActionEvent event) {
    }

}

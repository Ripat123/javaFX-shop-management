package fxsupershop.AdminPriority;

import animatefx.animation.*;
import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.*;
import fxsupershop.Services.*;
import fxsupershop.TableView.PriorityView;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author Ripat Rabbi
 */
public class Priority_listController implements Initializable {

    @FXML
    private JFXListView<?> list;
    ResultSet rs;
    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    ObservableList data = FXCollections.observableArrayList();
    ObservableList<PriorityView> data1 = FXCollections.observableArrayList();
    Message msg = new Message();
    @FXML
    private Pane prio_pane;
    @FXML
    private JFXScrollPane scroll;
    @FXML
    private TableView table;
    @FXML
    private TableColumn<?, ?> name_id;
    @FXML
    private TableColumn<?, ?> action_id;
    JFXButton button = new JFXButton();
    private int userID;
    JFXToggleButton btn = new JFXToggleButton();
    private int index, user_id;
    private String user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initview();
//        service.start();
//        service.setOnSucceeded((event) -> {
//            service.cancel();
//        });
    }

//    Service<Void> service = new Service<Void>() {
//        @Override
//        protected Task<Void> createTask() {
//            return new Task<Void>() {
//                @Override
//                protected Void call() {
//                    initview();
//                    return null;
//                }
//            };
//        }
//    };

    private void initview() {
        listView();
        scroll.setContent(table);
        JFXScrollPane.smoothScrolling((ScrollPane) scroll.getChildren().get(0));
        button.setRipplerFill(Paint.valueOf("white"));
        button.setStyle("-fx-background-radius: 23;");
        FontAwesomeIconView awesomeIconView = new FontAwesomeIconView(FontAwesomeIcon.ARROW_LEFT);
        awesomeIconView.setFill(Paint.valueOf("WHITE"));
        awesomeIconView.setSize("30");
        button.setGraphic(awesomeIconView);
        Label title = new Label("SubAdmin Priority");
        AnchorPane p = new AnchorPane();
        title.setPadding(new Insets(0, 120, 0, 0));
        p.setRightAnchor(title, Double.MIN_NORMAL);
        p.getChildren().addAll(button, title);
        title.setStyle("-fx-text-fill:WHITE; -fx-font-size: 35;");
        scroll.getBottomBar().getChildren().add(p);
        StackPane.setAlignment(p, Pos.CENTER_LEFT);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ZoomOut zoom = new ZoomOut(prio_pane);
                zoom.setOnFinished((e) -> {
                    prio_pane.setVisible(false);
                });
                zoom.setSpeed(2).play();
            }
        });
    }

    private void listView() {
        try {
            String sql = "SELECT name FROM createadmin WHERE Admin_Type = '0'";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                data.add(rs.getString("name"));
                list.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        prio_pane.setVisible(true);
                        new ZoomIn(prio_pane).setSpeed(2).play();
                        user = list.getSelectionModel().getSelectedItem().toString();
                        AccesControlView();
                    }
                });
            }
            list.setItems(data);
        } catch (Exception e) {
        }

    }

    private void AccesControlView() {
        try {
            data1.clear();
            name_id.setCellValueFactory(new PropertyValueFactory<>("formName"));
            action_id.setCellValueFactory(new PropertyValueFactory<>("button"));

            String sql = "SELECT * FROM access_priority WHERE admin_id = (SELECT "
                    + "id FROM createadmin WHERE name = '" + user + "')";
            rs = queryFunction.getResult(sql);
            while (rs.next()) {
                user_id = Integer.parseInt(rs.getString("admin_id"));
                int sw = Integer.parseInt(rs.getString("switch"));
                btn = new JFXToggleButton();
                btn.setId(String.valueOf(index));
                if (sw == 1) {
                    btn.setSelected(true);
                } else if (sw == 0) {
                    btn.setSelected(false);
                }
                data1.add(new PriorityView(rs.getString("form_name"), btn));
                btn.setOnAction((ActionEvent event) -> {
                    SwitchAction(event);
                });
                index++;
            }
            table.setItems(data1);
            index = 0;
        } catch (SQLException ex) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + ex);
        } finally {
            try {
                rs.close();
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    private void SwitchAction(ActionEvent event) {
        try {
            String source = event.getSource().toString();
            int row = Integer.parseInt(source.substring(19, source.indexOf(",")));
            PriorityView pv = (PriorityView) table.getItems().get(row);
            int swh = 0;
            if (pv.getButton().isSelected() == false) {
                swh = 0;
            } else if (pv.getButton().isSelected()) {
                swh = 1;
            }
            String sql = "UPDATE access_priority SET switch "
                    + "= '" + swh + "' WHERE form_name = '" + pv.getFormName() + "' AND admin_id = '" + user_id + "'";
            queryFunction.UpdateMessageLess(sql);
            AccesControlView();
        } catch (Exception e) {
            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
        } finally {
            try {
                queryFunction.post.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }

}

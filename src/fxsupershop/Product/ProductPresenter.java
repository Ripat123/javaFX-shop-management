package fxsupershop.Product;

import com.jfoenix.controls.JFXPopup;
import fxsupershop.Login.LoginMultiFormController;
import fxsupershop.Services.*;
import java.io.*;
import java.sql.*;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

/**
 *
 * @author Ripat Rabbi
 */
public class ProductPresenter {

    PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    LoginMultiFormController lmfc = new LoginMultiFormController();
    static int UId;
    ResultSet rs;
    String autoID = null;
    static String itemID, brandID, categoryID, shortage, suspension,
            purchase, sale, barcode, shelf, over, product_name, measurement;

    public void PopUP(Node popup_pane, Node nodeStage, JFXPopup popup, double initOffsetX, double initOffsetY) {
        popup.setPopupContent(null);
        popup.setPopupContent((Region) popup_pane);
        popup.show(nodeStage, JFXPopup.PopupVPosition.BOTTOM, JFXPopup.PopupHPosition.RIGHT, initOffsetX, initOffsetY);

    }

    public ImageView getImage(String Path, String image_name, ImageView imgV) {
        Image imagex = null;
        try {
            String path = Path + "/" + image_name + ".jpg";
            imagex = new Image("file:" + path, 300, 280, true, true, true);
            imgV.setImage(imagex);
            imgV.setFitHeight(100);
            imgV.setFitWidth(120);
        } catch (Exception e) {
//            msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem.\n" + e);
        } finally {
            try {

            } catch (Exception e) {

            }
        }
        return imgV;
    }

    public String autoid() {
        String prefix = "P-", presentID;
        int id = 0;
        try {
            presentID = queryFunction.AutoStringID("product_productinfo");
            String str[] = presentID.split("-");
            id = Integer.parseInt(str[1]) + 1;

            if (id <= 9) {
                autoID = (prefix + "000" + "" + Integer.toString(id));
                return autoID;
            } else if (id <= 99) {
                autoID = (prefix + "00" + "" + Integer.toString(id));
                return autoID;
            } else if (id <= 999) {
                autoID = (prefix + "0" + "" + Integer.toString(id));
                return autoID;
            } else if (id <= 9999) {
                autoID = (prefix + "" + "" + Integer.toString(id));
                return autoID;
            }else {
                autoID = (prefix + id);
            }

            if (autoID == null && autoID.equals("")) {
                autoID = (prefix + "0001");
            }
        } catch (Exception e) {e.printStackTrace();
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem\n" + e);
        }
        return autoID;
    }

    public void DataImport() {
        Connection con = queryFunction.getConnect();
        PreparedStatement post = null;
        UId = lmfc.User();
        FileChooser fc;
        fc = queryFunction.service.getOpenChooser("Select Location");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel File", "*.xls", "*.xlsx", "*.xlsm"));

        String Path = queryFunction.service.getOpenDialogPath(fc);
        if (Path != null) {
            try {
                String sql = "INSERT INTO product_productinfo (`id`,`item_id`,`category_id`,`brand_id`,shortage_list,"
                        + "`product_name`,suspension,`measurement_type`,`purchase_price`,`sale_price`,"
                        + "`barcode`,shelf_no,over_stock,admin_id,created_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                post = con.prepareStatement(sql);

                FileInputStream fileIn = new FileInputStream(new File(Path));
                XSSFWorkbook wb = new XSSFWorkbook(fileIn);
                XSSFSheet sheet = wb.getSheetAt(0);
                Row row;
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    autoID = autoid();
                    try {
                        DataPrepare(row);
                        if (!itemID.equals("0") && !brandID.equals("0") && !categoryID.equals("0")
                                && (!product_name.equals("") && !product_name.equals(" ")) && !measurement.equals("0")) {
                            post.setString(1, autoID);
                            post.setString(2, itemID);
                            post.setString(3, brandID);
                            post.setString(4, categoryID);
                            post.setString(5, shortage);
                            post.setString(6, product_name);
                            post.setString(7, suspension);
                            post.setString(8, measurement);
                            post.setString(9, purchase);
                            post.setString(10, sale);
                            post.setString(11, barcode);
                            post.setString(12, shelf);
                            post.setString(13, over);
                            post.setString(14, String.valueOf(UId));
                            post.setString(15, queryFunction.service.getDateTime());
                            post.execute();
                        }
                    } catch (Exception e) {
                    }
                }
                fileIn.close();
                post.close();

            } catch (Exception e) {
                queryFunction.service.msg.WarningMessage("Unsuccessful", "Warning", "Have a Problem in Data Import.\n" + e);
            }
        }
    }

    private void DataPrepare(Row row) {
        try {
            itemID = String.valueOf(row.getCell(0).getNumericCellValue());
            if(itemID.equals("") || itemID.equals(" "))
                itemID = "0";
        } catch (Exception e) {
            itemID = "0";
        }
        try {
            brandID = String.valueOf(row.getCell(1).getNumericCellValue());
            if(brandID.equals("") || brandID.equals(" "))
                brandID = "0";
        } catch (Exception e) {
            brandID = "0";
        }
        try {
            categoryID = String.valueOf(row.getCell(2).getNumericCellValue());
            if(categoryID.equals("") || categoryID.equals(" "))
                categoryID = "0";
        } catch (Exception e) {
            categoryID = "0";
        }
        try {
            product_name = row.getCell(4).getStringCellValue();
        } catch (Exception e) {
            product_name = "";
        }
        try {
            measurement = String.valueOf(row.getCell(6).getNumericCellValue());
        } catch (Exception e) {
            measurement = "0";
        }
        try {
            shortage = String.valueOf(row.getCell(3).getNumericCellValue());
            if (shortage.equals("0")) {
                shortage = "5";
            }
        } catch (Exception e) {
            shortage = "5";
        }
        try {
            suspension = row.getCell(5).getStringCellValue();
        } catch (Exception e) {
            suspension = " ";
        }
        try {
            purchase = String.valueOf(row.getCell(7).getNumericCellValue());
        } catch (Exception e) {
            purchase = "0";
        }
        try {
            sale = String.valueOf(row.getCell(8).getNumericCellValue());
        } catch (Exception e) {
            sale = "0";
        }
        try {
            barcode = row.getCell(9).getStringCellValue();
            if (barcode.equals("")) {
                barcode = autoID;
            }
        } catch (Exception e) {
            barcode = autoID;
        }
        try {
            shelf = row.getCell(10).getStringCellValue();
        } catch (Exception e) {
            try {
                shelf = String.valueOf(row.getCell(10).getNumericCellValue());
            } catch (Exception f) {
                shelf = " ";
            }
        }
        try {
            over = String.valueOf(row.getCell(11).getNumericCellValue());
            if (over.equals("0")) {
                over = "100";
            }
        } catch (Exception e) {
            over = "100";
        }
    }
}

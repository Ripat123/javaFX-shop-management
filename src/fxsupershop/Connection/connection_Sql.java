package fxsupershop.Connection;

import fxsupershop.Services.PrepareQueryFunction;
import java.io.*;
import java.sql.*;

/**
 *
 * @author Ripat Rabbi
 */
public class connection_Sql {

    static Connection con = null;
    static Process process;
    static String execCmd, user, password, dbname;
    static ConnectionPresenter cp = new ConnectionPresenter();
    static PrepareQueryFunction queryFunction = new PrepareQueryFunction();
    static int check = 0;
//    public static String dbName = "sbitcom_medicine";
//    public static String dbUser = "sbitcom_medicine_admin"; 
//    public static String dbPass = "UNxd-Yepgi2&";
    public static String dbName = "medicine";
    public static String dbUser = "root";
    public static String dbPass = "12345";

    public static Connection newConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, dbUser, dbPass);

            return con;
        } catch (Exception e) {
            connectFromFile();
            return con;
        }
    }

    public static Connection DynamicConnection(String dbname, String user, String password) {
        dbName = dbname;
        dbUser = user;
        dbPass = password;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbname, user, password);

            return con;
        } catch (Exception e) {
            queryFunction.service.msg.WarningMessage("Unsuccessful", "Have a Problem", "Connection Failed\n" + e);
            return null;
        }
    }

    public static Connection ConnectDb() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, dbUser, dbPass);
            return con;

//            con = DriverManager.getConnection("jdbc:mysql://www.sbit.com.bd:3306/"+dbName, dbUser, dbPass);
        } catch (Exception e) {
            return null;
        }
    }

    private static String read(String name) {
        String TI = null;
        String path = System.getProperty("user.home") + "\\Documents\\DigitalShop dbUserFile\\";
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path + name)))) {
            String line, text = null;
            while ((line = reader.readLine()) != null) {
                text = line;
                TI = queryFunction.service.decrypt(text);
            }
        } catch (Exception e) {
            cp.setupConnection(e);
            check = 1;
        }
        return TI;
    }

    private static Connection connectFromFile() {
        try {
            dbname = read("dbName.txt");
            if (check != 1) {
                user = read("UInfo.txt");
                password = read("PInfo.txt");
                con = DynamicConnection(dbname, user, password);
            }
            return con;
        } catch (Exception e) {
            cp.setupConnection(e);
            return con;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.gc();
        System.runFinalization();
        super.finalize();
    }
}

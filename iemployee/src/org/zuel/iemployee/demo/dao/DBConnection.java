package org.zuel.iemployee.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * create time: 2021/3/8 20:54
 * author: XinChen1899
 */

public class DBConnection {

    private static final String driverName="com.mysql.jdbc.Driver";

    private static final String url="jdbc:mysql://127.0.0.1:3306/iemployee?useSSL=false";

    private static final String user="root";

    private static final String password="3093104a8b27";

    public static Connection getConnection() {
        Connection conn=null;
        try{
            Class.forName(driverName);
            conn= DriverManager.getConnection(url,user,password);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

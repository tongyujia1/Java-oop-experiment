package com.dataprocessing;

import java.sql.SQLException;

public class SQLconnection {
    static String DriverName="com.mysql.jdbc.Driver";
    static String Url="jdbc:mysql://localhost:3306/sys?useSSL=false&serverTimezone=UTC";
    static String User="root";
    static String Password="tyj";
    public static void Connect() throws ClassNotFoundException, SQLException {
        DataProcessing.connectToDatabase(DriverName, Url, User, Password);
    }

    // 断开数据库函数
    public static void Disconnect() throws SQLException {
        DataProcessing.disconnectFromDatabase();
    }

}

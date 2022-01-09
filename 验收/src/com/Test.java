package com;

import com.GUI.LoginFrame;
import com.dataprocessing.DataProcessing;
import com.dataprocessing.SQLconnection;
import com.user.Administrator;
import com.user.Browser;
import com.user.Operator;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame frame = new LoginFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}




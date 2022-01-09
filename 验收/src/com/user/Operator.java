package com.user;

import com.dataprocessing.DataProcessing;
import com.dataprocessing.Doc;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

//档案录入员类
public class Operator extends User {
    public Operator(String name, String password, String role) {
        super(name, password, role);
    }

}

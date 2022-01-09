package com.user;

import com.dataprocessing.DataProcessing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

//系统管理员类
public class Administrator extends User{
    public Administrator(String name, String password, String role) {
        super(name, password, role);
    }
}

package com.user;

import com.dataprocessing.DataProcessing;
import com.dataprocessing.Doc;


import javax.swing.*;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;


public class User {
    private String name;
    private String password;
    private String role;

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }


    public void setRole(String role) {
        this.role = role;
    }

    //public  abstract void showMenu() throws SQLException, IOException;

    public boolean downloadFile(String ID) throws IOException, SQLException {
        String filePath="C:\\Users\\1.1\\IdeaProjects\\oop-experiment07\\a.txt";
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(filePath));
        Doc doc=DataProcessing.searchDoc(ID);
        out.writeObject(doc);
        out.close();
        return true;
    }
    public boolean uploadFile(String fileID, String filepath, String filedescription) {

        // 输入文件对象
        File input_file = new File(filepath);
        // 获取文件名
        String filename = input_file.getName();
        // 获取当前时间
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try {

            if (DataProcessing.insertDoc(fileID, this.getName(), timestamp, filedescription, filename)) {

                // 输入过滤器流,建立在文件流上
                BufferedInputStream input = new BufferedInputStream(new FileInputStream(input_file));

                // 输出文件对象
                File output_file = new File("C:\\Users\\1.1\\IdeaProjects\\oop-experiment07\\" + input_file.getName());
                // 创建文件
                output_file.createNewFile();
                // 输出过滤器流,建立在文件流上
                BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(output_file));

                // 用字节数组存取数据
                byte[] bytes = new byte[1024];
                // 文件写入操作
                int length = 0;
                while ((length = input.read(bytes)) != -1) {
                    output.write(bytes, 0, length);
                }

                // 关闭流
                input.close();
                output.close();

                return true;
            } else
                return false;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        return false;
    }

    public void showFileList(String ID) throws SQLException{
        Doc doc=DataProcessing.searchDoc(ID);
        System.out.println("[  ID:"+doc.getID()+"   creator:"+doc.getCreator()+"   description:"+doc.getDescription()+
                "   filename:"+doc.getFilename()+"]");
        System.out.println("显示成功");
    }
    public  boolean changeSelfInfo(String password) throws SQLException {
        if (DataProcessing.updateUser(name, password, role)){
            this.password=password;
            System.out.println("修改成功");
            return true;
        }else
            return false;
    }
    public void exitSystem(){
        System.out.println("退出成功");
        System.exit(0);
    }
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String  getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "["+getName()+"   "+getPassword()+"    "+getRole()+"]";
    }


}

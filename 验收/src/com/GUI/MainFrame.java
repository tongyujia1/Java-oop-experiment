package com.GUI;

import CS.Client;
import com.dataprocessing.SQLconnection;
import com.user.User;
import sun.awt.image.ToolkitImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings({"all"})
public class MainFrame extends JFrame {
    private JPanel contentPane;

    private JMenuBar menuBar;


    private JMenu UserManager_Menu;
    private JMenu FileManager_Menu;
    private JMenu SelfInfo_Menu;
    private JMenu Others_Menu;

    private JButton AddUser_Button;
    private JButton DelUser_Button;
    private JButton UpdateUser_Button;
    private JButton UploadFile_Button;
    private JButton DownloadFile_Button;
    private JButton ChangeSelfInfo_Button;
    private JButton Exit_Button;
    ImageIcon background;

    public MainFrame(User user) {
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(user.getRole());
        this.setBounds(100,100,600,400);

        contentPane=new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));

        background = new ImageIcon("C:\\Users\\1.1\\IdeaProjects\\oop-experiment07\\src\\com\\GUI\\h.jpeg");	//创建一个背景图片
        JLabel label = new JLabel(background);		//把背景图片添加到标签里
        label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());	//把标签设置为和图片等高等宽
        contentPane = (JPanel)this.getContentPane();		//把我的面板设置为内容面板
        contentPane.setOpaque(false);					//把我的面板设置为不可视
        contentPane.setLayout(new FlowLayout());		//把我的面板设置为流动布局
        this.getLayeredPane().setLayout(null);		//把分层面板的布局置空

        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

        this.setContentPane(contentPane);
        contentPane.setLayout(null);

        AddUser_Button=new JButton("添加用户");
        AddUser_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUserActionPerformed(user,e);
            }
        });

        DelUser_Button=new JButton("删除用户");
        DelUser_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DelUserActionPerformed(user,e);
            }
        });
        UpdateUser_Button=new JButton("修改用户");
        UpdateUser_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateUserActionPerformed(user,e);
            }
        });

        UploadFile_Button=new JButton("上传档案");
        UploadFile_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UploadFileActionPerformed(user,e);
            }
        });

        DownloadFile_Button=new JButton("下载档案");
        DownloadFile_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DownloadFileActionPerformed(user,e);
            }
        });

        ChangeSelfInfo_Button=new JButton("修改个人信息");
        ChangeSelfInfo_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeSelfActionPerformed(user,e);
            }
        });

        Exit_Button=new JButton("退出系统");
        Exit_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExitActionPerformed(e);
            }
        });

        menuBar=new JMenuBar();
        menuBar.setBounds(0, 0, 1160, 30);
        contentPane.add(menuBar);

        UserManager_Menu=new JMenu("用户管理");
        menuBar.add(UserManager_Menu);
        UserManager_Menu.add(AddUser_Button);
        UserManager_Menu.add(DelUser_Button);
        UserManager_Menu.add(UpdateUser_Button);


        FileManager_Menu=new JMenu("档案管理");
        menuBar.add(FileManager_Menu);
        FileManager_Menu.add(UploadFile_Button);
        FileManager_Menu.add(DownloadFile_Button);

        SelfInfo_Menu=new JMenu("个人信息管理");
        menuBar.add(SelfInfo_Menu);
        SelfInfo_Menu.add(ChangeSelfInfo_Button);

        Others_Menu=new JMenu("其他");
        menuBar.add(Others_Menu);
        Others_Menu.add(Exit_Button);

        this.SetRights(user.getRole());

    }
    //监听事件方法
    private void AddUserActionPerformed(User user, ActionEvent evt){
        UserFrame userframe = new UserFrame(user, 0);
        userframe.setVisible(true);
    }
    private void DelUserActionPerformed(User user, ActionEvent evt){
        UserFrame userframe = new UserFrame(user, 1);
        userframe.setVisible(true);
    }
    private void UpdateUserActionPerformed(User user, ActionEvent evt){
        UserFrame userframe = new UserFrame(user, 2);
        userframe.setVisible(true);
    }
    private void UploadFileActionPerformed(User user, ActionEvent evt){
        FileFrame fileframe = new FileFrame(user, 0);
        fileframe.setVisible(true);
    }
    private void DownloadFileActionPerformed(User user, ActionEvent evt){
        FileFrame fileframe = new FileFrame(user, 1);
        fileframe.setVisible(true);
    }
    private void ChangeSelfActionPerformed(User user, ActionEvent evt){
        SelfInforFrame selfframe = new SelfInforFrame(user);
        selfframe.setVisible(true);
    }
    private void ExitActionPerformed(ActionEvent evt) {
        // 断开数据库连接
        try {
            SQLconnection.Disconnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        // 发送登出消息
        try {
            Client.SendMessage("登出");
            // 最后再接收服务器登出消息
            Client.ReceiveMessage();
            Client.CloseConnection();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            return;
        } finally {
            // 在finally里返回登录界面，无论是否断开服务器连接都能返回
            this.dispose();
            LoginFrame loginframe = new LoginFrame();
            loginframe.setVisible(true);
        }

    }

//设置标题
    private void SetTitle(String role) {
        if (role.equalsIgnoreCase("administrator")) {
            setTitle("档案管理员界面");
        } else if (role.equalsIgnoreCase("browser")) {
            setTitle("档案浏览员界面");
        } else if (role.equalsIgnoreCase("operator")) {
            setTitle("档案录入员界面");
        }
    }
//设置权限，不区分大小写
    private void SetRights(String role) {

        if (role.equals("administrator")) {

        AddUser_Button.setEnabled(true);
        DelUser_Button.setEnabled(true);
        UpdateUser_Button.setEnabled(true);
        DownloadFile_Button.setEnabled(true);
        UploadFile_Button.setEnabled(false);
        ChangeSelfInfo_Button.setEnabled(true);
        Exit_Button.setEnabled(true);

        } else if (role.equals("browser")) {

        AddUser_Button.setEnabled(false);
        DelUser_Button.setEnabled(false);
        UpdateUser_Button.setEnabled(false);
        DownloadFile_Button.setEnabled(true);
        UploadFile_Button.setEnabled(false);
        ChangeSelfInfo_Button.setEnabled(true);
        Exit_Button.setEnabled(true);

        } else if (role.equals("operator")) {

        AddUser_Button.setEnabled(false);
        DelUser_Button.setEnabled(false);
        UpdateUser_Button.setEnabled(false);
        DownloadFile_Button.setEnabled(true);
        UploadFile_Button.setEnabled(true);
        ChangeSelfInfo_Button.setEnabled(true);
        Exit_Button.setEnabled(true);
        }


    }


}




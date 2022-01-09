package com.GUI;

import CS.Client;
import com.dataprocessing.DataProcessing;
import com.dataprocessing.SQLconnection;
import com.sun.deploy.util.StringUtils;
import com.user.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

@SuppressWarnings({"all"})
public class LoginFrame extends JFrame {
    private JPanel contentPane;
    private JLabel Username_Label;
    private JLabel Password_Label;
    private JTextField Username_Text;
    private JPasswordField Password_Text;
    private JButton Confirm_Button;
    private JButton Cancel_Button;
    public LoginFrame(){
        //界面框架

        this.setTitle("登入界面");
        this.setBounds(200,200,550,350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);


        contentPane=new JPanel();
        //contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.setBackground(Color.LIGHT_GRAY);
        this.setContentPane(contentPane);

        Username_Label=new JLabel("用户名：");
        Username_Label.setBounds(120,120,60,20);
        Password_Label=new JLabel("密码：");
        Password_Label.setBounds(120,150,60,20);

        Username_Text=new JTextField();
        Username_Text.setBounds(200,120,160,20);
        Password_Text=new JPasswordField();
        Password_Text.setBounds(200,150,160,20);

        Confirm_Button=new JButton("确定");
        Confirm_Button.setBounds(130,180,60,20);
        Cancel_Button=new JButton("取消");
        Cancel_Button.setBounds(240,180,60,20);


        contentPane.setLayout(null);
        contentPane.add(Username_Label);
        contentPane.add(Username_Text);
        contentPane.add(Password_Label);
        contentPane.add(Password_Text);
        contentPane.add(Confirm_Button);
        contentPane.add(Cancel_Button);

        //连接数据库
        try {
            SQLconnection.Connect();
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }



    //监听事件
        Confirm_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginInActionPerformed(e);
            }
        });

        Cancel_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResetValueActionPerformed(e);
            }
        });
    }
    private void ResetValueActionPerformed(ActionEvent e) {
        // 设置为空
        this.Username_Text.setText("");
        this.Password_Text.setText("");
    }

    private void LoginInActionPerformed(ActionEvent e){
        String username = this.Username_Text.getText();
        String password = new String(this.Password_Text.getPassword());

        if(StringUtil.isEmpty(username)){
            JOptionPane.showMessageDialog(null,"未输入用户名！");
            return;
        }
        if(StringUtil.isEmpty(password)){
            JOptionPane.showMessageDialog(null,"未输入密码！");
            return;
        }
        try{
            if(DataProcessing.searchUser(username,password)==null){
                JOptionPane.showMessageDialog(null,"用户名或密码错误！");
                return;
            }else {
                //与服务端联结
                Client.ConnectToServer();
                Client.GetStreams();
                Client.SendMessage(username + "已登录");
                Client.ReceiveMessage();

                User user=DataProcessing.searchUser(username,password);
                this.dispose();
                MainFrame mainFrame=new MainFrame(user);
                mainFrame.setVisible(true);
            }
        }catch (Exception e1){
            JOptionPane.showMessageDialog(null,e1);
            return;
        }
    }

}

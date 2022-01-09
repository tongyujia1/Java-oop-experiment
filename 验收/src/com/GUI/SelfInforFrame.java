package com.GUI;

import CS.Client;
import com.dataprocessing.DataProcessing;
import com.user.User;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


@SuppressWarnings({"all"})
public class SelfInforFrame extends JFrame {

    private JPanel contentPane;
    private JLabel Username_Label;
    private JLabel OldPassword_Label;
    private JLabel NewPassword_Label;
    private JLabel ConfirmPassword_Label;
    private JLabel Role_Label;

    private JTextField Username_Txt;
    private JPasswordField OldPassword_Txt;
    private JPasswordField NewPassword_Txt;
    private JPasswordField ConfirmPassword_Txt;
    private JTextField Role_Txt;

    private JButton Confirm_Button;
    private JButton Return_Button;


    public SelfInforFrame(User user) {
        setResizable(false);
        setTitle("用户信息管理");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 385);

        // 中间容器
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 用户名标签
        Username_Label = new JLabel("用户名:");
        Username_Label.setBounds(100, 51, 72, 30);
        contentPane.add(Username_Label);

        // 旧密码标签
        OldPassword_Label = new JLabel("旧密码:");
        OldPassword_Label.setBounds(100, 93, 72, 30);
        contentPane.add(OldPassword_Label);

        // 新密码标签
        NewPassword_Label = new JLabel("新密码:");
        NewPassword_Label.setBounds(100, 135, 72, 30);
        contentPane.add(NewPassword_Label);

        // 确认密码标签
        ConfirmPassword_Label = new JLabel("确认新密码:");
        ConfirmPassword_Label.setBounds(63, 178, 109, 30);
        contentPane.add(ConfirmPassword_Label);

        // 角色标签
        Role_Label = new JLabel("角色:");
        Role_Label.setBounds(120, 220, 60, 30);
        contentPane.add(Role_Label);

        // 用户名文本域
        Username_Txt = new JTextField();
        // 自动设置文本为用户名
        Username_Txt.setText(user.getName());
        Username_Txt.setEditable(false);
        Username_Txt.setBounds(186, 56, 154, 24);
        contentPane.add(Username_Txt);
        Username_Txt.setColumns(10);

        // 旧密码文本域
        OldPassword_Txt = new JPasswordField();
        OldPassword_Txt.setBounds(186, 98, 154, 24);
        contentPane.add(OldPassword_Txt);

        // 新密码文本域
        NewPassword_Txt = new JPasswordField();
        NewPassword_Txt.setBounds(186, 140, 154, 24);
        contentPane.add(NewPassword_Txt);

        // 确认密码文本域
        ConfirmPassword_Txt = new JPasswordField();
        ConfirmPassword_Txt.setBounds(186, 183, 154, 24);
        contentPane.add(ConfirmPassword_Txt);

        // 角色文本域
        Role_Txt = new JTextField();
        // 自动设置用户身份
        Role_Txt.setText(user.getRole());
        Role_Txt.setEditable(false);
        Role_Txt.setColumns(10);
        Role_Txt.setBounds(186, 226, 154, 24);
        contentPane.add(Role_Txt);

        // 确认按钮
        Confirm_Button = new JButton("确认");
        Confirm_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 修改密码事件
                ChangeSelfInfoActionPerformed(user, e);
            }
        });
        Confirm_Button.setBounds(118, 288, 113, 27);
        contentPane.add(Confirm_Button);

        // 返回按钮
        Return_Button = new JButton("返回");
        Return_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 返回事件
                ReturnActionPerformed(e);
            }
        });
        Return_Button.setBounds(278, 288, 113, 27);
        contentPane.add(Return_Button);
    }

    // 修改密码
    private void ChangeSelfInfoActionPerformed(User user, ActionEvent evt) {

        String oldpassword = new String(OldPassword_Txt.getPassword());
        String newpassword = new String(NewPassword_Txt.getPassword());
        String confirmpassword = new String(ConfirmPassword_Txt.getPassword());

        // 检查是否为空
        if (StringUtil.isEmpty(oldpassword)) {
            JOptionPane.showMessageDialog(null, "未输入旧密码！");
            return;
        }
        if (StringUtil.isEmpty(newpassword)) {
            JOptionPane.showMessageDialog(null, "未输入新密码！");
            return;
        }
        if (StringUtil.isEmpty(confirmpassword)) {
            JOptionPane.showMessageDialog(null, "请输入确认密码！");
            return;
        }

        // 密码匹配
        try {

            if (DataProcessing.searchUser(user.getName(), oldpassword) == null) {
                JOptionPane.showMessageDialog(null, "用户名与原密码不匹配！");
                return;
            }
            if (!newpassword.equals(confirmpassword)) {
                JOptionPane.showMessageDialog(null, "两次输入的新密码不相同！");
                return;
            }

            // 修改密码
            if (user.changeSelfInfo(newpassword)) {

                // 发送修改密码消息
                try {
                    Client.SendMessage("修改密码");
                    Client.ReceiveMessage();
                } catch (IOException | ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                    return;
                }


                // 清空
                this.OldPassword_Txt.setText("");
                this.NewPassword_Txt.setText("");
                this.ConfirmPassword_Txt.setText("");

                JOptionPane.showMessageDialog(null, "修改成功!");
                return;

            } else {
                JOptionPane.showMessageDialog(null, "修改失败!");
                return;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }

    // 返回
    private void ReturnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

}


package com.GUI;

import CS.Client;
import com.dataprocessing.DataProcessing;
import com.user.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

@SuppressWarnings({"all"})
public class UserFrame extends JFrame {
    private JPanel contentPane;
    // 多页面容器
    private JTabbedPane tabbedPane;

    // 增添用户页面及组件
    private JPanel AddUser_Panel;
    private JLabel Username_Label1;
    private JLabel Password_Label1;
    private JLabel Role_Label1;
    private JTextField Username_Txt1;
    private JPasswordField Password_Txt1;
    private JComboBox Role_ComboBox1;
    private JButton Confirm_Button1;
    private JButton Return_Button1;

    // 删除用户页面及组件
    private JPanel DelUser_Panel;
    private JScrollPane scrollPane;
    private JTable Users_table;
    private JButton Confirm_Button2;
    private JButton Return_Button2;

    // 修改用户页面及组件
    private JPanel UpdateUser_Panel;
    private JLabel Username_Label2;
    private JLabel Password_Label2;
    private JLabel Role_Label2;
    private JTextField Username_Txt2;
    private JPasswordField Password_Txt2;
    private JComboBox Role_ComboBox2;
    private JButton Confirm_Button3;
    private JButton Return_Button3;



    // 传入用户及页面选项: 0 增添用户 1 删除用户 2 修改用户
    public UserFrame(User user, int i) {

        this.setTitle("用户管理界面");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(100,100,600,500);


        contentPane=new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(contentPane);
        contentPane.setLayout(null);

        tabbedPane=new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(15,50,550,310);
        contentPane.add(tabbedPane);

        AddUser_Panel = new JPanel();
        // 增添选项卡
        tabbedPane.addTab("添加用户", null, AddUser_Panel, null);
        AddUser_Panel.setLayout(null);

        // 用户名标签
        Username_Label1 = new JLabel("用户名");
        Username_Label1.setBounds(114, 38, 61, 32);
        AddUser_Panel.add(Username_Label1);

        // 密码标签
        Password_Label1 = new JLabel("密码");
        Password_Label1.setBounds(132, 93, 43, 32);
        AddUser_Panel.add(Password_Label1);

        // 角色标签
        Role_Label1 = new JLabel("角色");
        Role_Label1.setBounds(132, 150, 43, 32);
        AddUser_Panel.add(Role_Label1);

        // 用户名文本域
        Username_Txt1 = new JTextField();
        Username_Txt1.setBounds(197, 44, 181, 24);
        Username_Txt1.setColumns(10);
        AddUser_Panel.add(Username_Txt1);

        // 密码文本域
        Password_Txt1 = new JPasswordField();
        Password_Txt1.setBounds(197, 99, 181, 24);
        AddUser_Panel.add(Password_Txt1);

        Role_ComboBox1=new JComboBox();
        Role_ComboBox1.setEditable(true);
        Role_ComboBox1.setModel(new DefaultComboBoxModel(new String[] {"Administrator", "Browser", "Operator"}));
        Role_ComboBox1.setBounds(197, 156, 181, 24);
        AddUser_Panel.add(Role_ComboBox1);

        Confirm_Button1 = new JButton("确定");
        Confirm_Button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 增添用户事件
               AddUserActionPerformed(user, e);
            }
        });

        Confirm_Button1.setBounds(132, 222, 113, 27);
        AddUser_Panel.add(Confirm_Button1);

        // 返回按钮
        Return_Button1 = new JButton("返回");
        Return_Button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 返回事件
                ReturnActionPerformed(e);
            }
        });
        Return_Button1.setBounds(329, 222, 113, 27);
        AddUser_Panel.add(Return_Button1);



        //删除界面
        DelUser_Panel = new JPanel();
        tabbedPane.addTab("删除用户", null, DelUser_Panel, null);
        DelUser_Panel.setLayout(null);

        // 删除按钮
        Confirm_Button2 = new JButton("删除");
        Confirm_Button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 删除事件
                DelUserActionPerformed(user, e);
            }
        });
        Confirm_Button2.setBounds(132, 222, 113, 27);
        DelUser_Panel.add(Confirm_Button2);

        // 返回按钮
        Return_Button2 = new JButton("返回");
        Return_Button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 返回事件
                ReturnActionPerformed(e);
            }
        });
        Return_Button2.setBounds(329, 222, 113, 27);
        DelUser_Panel.add(Return_Button2);

        // 可下拉容器域
        scrollPane = new JScrollPane();
        scrollPane.setBounds(62, 37, 432, 159);
        DelUser_Panel.add(scrollPane);

        // 用户列表
        Users_table = new JTable();
        // 构造表格
        ConstructUserTable();
        // 加入可下拉区域
        scrollPane.setViewportView(Users_table);


        UpdateUser_Panel = new JPanel();
        UpdateUser_Panel.setLayout(null);
        tabbedPane.addTab("修改用户", null, UpdateUser_Panel, null);

        // 用户名标签
        Username_Label2 = new JLabel("用户名");
        Username_Label2.setBounds(114, 38, 61, 32);
        UpdateUser_Panel.add(Username_Label2);

        // 密码标签
        Password_Label2 = new JLabel("密码");
        Password_Label2.setBounds(132, 93, 43, 32);
        UpdateUser_Panel.add(Password_Label2);

        // 角色标签
        Role_Label2 = new JLabel("角色");
        Role_Label2.setBounds(132, 150, 43, 32);
        UpdateUser_Panel.add(Role_Label2);

        // 用户名文本域
        Username_Txt2 = new JTextField();
        Username_Txt2.setColumns(10);
        Username_Txt2.setBounds(197, 44, 181, 24);
        UpdateUser_Panel.add(Username_Txt2);

        // 密码文本域
        Password_Txt2 = new JPasswordField();
        Password_Txt2.setBounds(197, 99, 181, 24);
        UpdateUser_Panel.add(Password_Txt2);

        // 角色选项栏
        Role_ComboBox2 = new JComboBox();
        Role_ComboBox2.setModel(new DefaultComboBoxModel(new String[] { "", "Administrator", "Browser", "Operator" }));
        Role_ComboBox2.setEditable(true);
        Role_ComboBox2.setBounds(197, 156, 181, 24);
        UpdateUser_Panel.add(Role_ComboBox2);

        // 修改按钮
        Confirm_Button3 = new JButton("修改");
        Confirm_Button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 修改用户事件
                UpdateUserActionPerformed(user, e);
            }
        });
        Confirm_Button3.setBounds(132, 222, 113, 27);
        UpdateUser_Panel.add(Confirm_Button3);

        // 返回按钮
        Return_Button3 = new JButton("返回");
        Return_Button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 返回事件
                ReturnActionPerformed(e);
            }
        });
        Return_Button3.setBounds(329, 222, 113, 27);
        UpdateUser_Panel.add(Return_Button3);

        SetPane(i);
    }



    //方法
        private void ConstructUserTable() {
        // 表头数据
            String[] columnNames = { "用户名", "密码", "角色" };
        // 表格数据
            String[][] rowData = new String[20][3];

            Enumeration<User> u;
            try {
            // 获取哈希表信息
            u = DataProcessing.getAllUser();
            // 行数
            int row = 0;
            // 将哈希表信息导入至表格数据
                    while (u.hasMoreElements()) {
                    User user = u.nextElement();
                    rowData[row][0] = user.getName();
                    rowData[row][1] = user.getPassword();
                    rowData[row][2] = user.getRole();
                    row++;
                }

            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
            }


            // 构造表格
            Users_table.setModel(new DefaultTableModel(rowData, columnNames) {

                boolean[] columnEditables = new boolean[] { false, false, false };

                public boolean isCellEditable(int row, int column) {
                    return columnEditables[column];
                }

            });


    }

    // 增添
    private synchronized void AddUserActionPerformed(User user, ActionEvent evt) {

        // 获取输入内容
        String username = this.Username_Txt1.getText();
        String password = new String(this.Password_Txt1.getPassword());
        String role = (String) this.Role_ComboBox1.getSelectedItem();

        if (StringUtil.isEmpty(username)) {
            JOptionPane.showMessageDialog(null, "未输入用户名！");
            return;
        }
        if (StringUtil.isEmpty(password)) {
            JOptionPane.showMessageDialog(null, "未输入密码！");
            return;
        }
        if (StringUtil.isEmpty(role)) {
            JOptionPane.showMessageDialog(null, "未选择身份！");
            return;
        }

        try {
            if (DataProcessing.insertUser(username, password, role)) {

                // 发送增添用户的消息
                try {
                    Client.SendMessage("增添用户");
                    Client.ReceiveMessage();
                } catch (IOException | ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                    return;
                }

                // 更新表格数据
                ConstructUserTable();
                JOptionPane.showMessageDialog(null, "添加成功！");
                return;

            } else {
                JOptionPane.showMessageDialog(null, "添加失败！用户名已存在！");
                return;
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

    }

    // 删除
    private  synchronized void DelUserActionPerformed(User user, ActionEvent evt) {

        // 获取所选行序号,若未选择其值为-1
        int selectedrow = Users_table.getSelectedRow();

        // 未选择用户的情况
        if (selectedrow == -1) {
            JOptionPane.showMessageDialog(null, "未选择用户！");
            return;
        } else {

            // 获取所选行的用户名
            String username = (String) Users_table.getValueAt(selectedrow, 0);
            // 若选择空行
            if (StringUtil.isEmpty(username)) {
                return;
            }
            // 选择自身用户的情况
            if (username.equals(user.getName())) {
                JOptionPane.showMessageDialog(null, "不能删除自身用户！");
                return;
            }

            // 显示确认界面: 信息, 标题, 选项个数
            int value = JOptionPane.showConfirmDialog(null, "确定要删除用户吗？", "用户删除确认界面", 2);
            // Yes=0 No=1
            if (value == 0) {
                try {
                    if (DataProcessing.deleteUser(username)) {

                        // 发送删除用户消息
                        try {
                            Client.SendMessage("删除用户");
                            Client.ReceiveMessage();
                        } catch (IOException | ClassNotFoundException e) {
                            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                            return;
                        }

                        // 更新表格数据
                        ConstructUserTable();
                        JOptionPane.showMessageDialog(null, "删除成功！");
                        return;

                    } else {
                        JOptionPane.showMessageDialog(null, "删除失败！");
                        return;
                    }

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                }

            } else if (value == 1) {
                return;
            }
        }
    }

    // 修改
    private synchronized void UpdateUserActionPerformed(User user, ActionEvent evt) {

        String username = this.Username_Txt2.getText();
        String password = new String(this.Password_Txt2.getPassword());
        String role = (String) this.Role_ComboBox2.getSelectedItem();

        if (StringUtil.isEmpty(username)) {
            JOptionPane.showMessageDialog(null, "未输入用户名！");
            return;
        }
        if (StringUtil.isEmpty(password)) {
            JOptionPane.showMessageDialog(null, "未输入密码！");
            return;
        }
        if (StringUtil.isEmpty(role)) {
            JOptionPane.showMessageDialog(null, "未选择身份！");
            return;
        }

        try {

            if (DataProcessing.searchUser(username, password) == null) {
                JOptionPane.showMessageDialog(null, "用户名与密码不匹配！");
                return;
            } else {
                // 显示确认界面：信息，标题，选项个数
                int value = JOptionPane.showConfirmDialog(null, "确定要修改信息吗？", "信息修改确认界面", 2);
                // Yes=0 No=1
                if (value == 0) {
                    if (DataProcessing.updateUser(username, password, role)) {

                        // 发送修改用户信息
                        try {
                            Client.SendMessage("修改用户");
                            Client.ReceiveMessage();
                        } catch (IOException | ClassNotFoundException e) {
                            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                            return;
                        }

                        // 更新表格数据
                        ConstructUserTable();
                        JOptionPane.showMessageDialog(null, "修改成功！");
                        return;

                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败！");
                        return;
                    }

                } else if (value == 1) {
                    return;
                }
            }

        } catch (HeadlessException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }

    // 设置页面
    private void SetPane(int value) {
        if (value == 0) {
            tabbedPane.setSelectedComponent(AddUser_Panel);
        } else if (value == 1) {
            tabbedPane.setSelectedComponent(DelUser_Panel);
        } else if (value == 2) {
            tabbedPane.setSelectedComponent(UpdateUser_Panel);
        }
    }

    // 返回
    private void ReturnActionPerformed(ActionEvent evt) {
        this.dispose();
    }


}

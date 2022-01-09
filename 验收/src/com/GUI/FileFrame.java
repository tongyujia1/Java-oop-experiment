package com.GUI;

import CS.Client;
import com.dataprocessing.DataProcessing;
import com.dataprocessing.Doc;
import com.user.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

@SuppressWarnings({"all"})
public class FileFrame extends JFrame {
    // 中间容器
    private JPanel contentPane;
    // 多页面容器
    private JTabbedPane tabbedPane;

    // 上传文件页面及组件
    private JPanel Upload_Panel;
    private JLabel FileID_Label;
    private JLabel Filedescription_Label;
    private JLabel Filename_Label;
    private JTextField FileID_Txt;
    private JTextArea Filedescription_Txt;
    private JTextField Filepath_Txt;
    private JButton Upload_Button;
    private JButton OpenFile_Button;
    private JButton Return_Button1;

    // 下载文件页面及组件
    private JPanel Download_Panel;
    private JButton Download_Button;
    private JButton Return_Button2;
    private JScrollPane scrollPane;
    private JTable Files_table;

    public FileFrame(User user, int i) {

        this.setTitle("文件管理界面");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 800, 590);
        this.setBackground(Color.CYAN);

        // 中间容器
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 多页面容器
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(40, 35, 710, 470);
        contentPane.add(tabbedPane);

        // 上传页面
        Upload_Panel = new JPanel();
        tabbedPane.addTab("上传文档", null, Upload_Panel, null);
        Upload_Panel.setLayout(null);

        // 档案号标签
        FileID_Label = new JLabel("档案号");
        FileID_Label.setBounds(125, 35, 60, 35);
        Upload_Panel.add(FileID_Label);

        // 文件描述标签
        Filedescription_Label = new JLabel("文件描述");
        Filedescription_Label.setBounds(105, 90, 80, 35);
        Upload_Panel.add(Filedescription_Label);

        // 文件名标签
        Filename_Label = new JLabel("文件名称");
        Filename_Label.setBounds(85, 315, 100, 35);
        Upload_Panel.add(Filename_Label);

        // 档案号文本域
        FileID_Txt = new JTextField();
        FileID_Txt.setBounds(215, 40, 270, 30);
        Upload_Panel.add(FileID_Txt);
        FileID_Txt.setColumns(10);

        // 文件描述文本域
        Filedescription_Txt = new JTextArea();
        Filedescription_Txt.setBounds(215, 95, 270, 200);
        Upload_Panel.add(Filedescription_Txt);
        Filedescription_Txt.setColumns(10);

        // 文件名文本域
        Filepath_Txt = new JTextField();
        Filepath_Txt.setColumns(10);
        Filepath_Txt.setBounds(215, 320, 270, 30);
        Upload_Panel.add(Filepath_Txt);

        // 上传按钮
        Upload_Button = new JButton("上传");
        Upload_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 上传文件事件
                UploadActionPerformed(user, e);
            }
        });
        Upload_Button.setBounds(215, 380, 95, 30);
        Upload_Panel.add(Upload_Button);

        // 返回按钮
        Return_Button1 = new JButton("返回");
        Return_Button1.setBounds(395, 380, 95, 30);
        Return_Button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 返回事件
                ReturnActionPerformed(e);
            }
        });

        // 打开按钮
        OpenFile_Button = new JButton("打开");
        OpenFile_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 打开文件事件
                OpenFileActionPerformed(e);
            }
        });
        OpenFile_Button.setBounds(530, 320, 95, 30);
        Upload_Panel.add(OpenFile_Button);
        Upload_Panel.add(Return_Button1);

        // 下载页面
        Download_Panel = new JPanel();
        tabbedPane.addTab("下载文档", null, Download_Panel, null);
        tabbedPane.setEnabledAt(1, true);
        Download_Panel.setLayout(null);

        // 下载按钮
        Download_Button = new JButton("下载");
        Download_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 下载文件事件
                try {
                    DownloadActionPerformed(user, e);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        Download_Button.setBounds(215, 380, 95, 30);
        Download_Panel.add(Download_Button);

        // 返回按钮
        Return_Button2 = new JButton("返回");
        Return_Button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 返回事件
                ReturnActionPerformed(e);
            }
        });
        Return_Button2.setBounds(395, 380, 95, 30);
        Download_Panel.add(Return_Button2);

        // 可下拉容器
        scrollPane = new JScrollPane();
        scrollPane.setBounds(35, 30, 640, 320);
        Download_Panel.add(scrollPane);

        // 下载文件列表
        Files_table = new JTable();
        // 构造表格
        ConstructFileTable();
        // 加入可下拉区域
        scrollPane.setViewportView(Files_table);

        // 设置权限及页面
        setPane(user, i);
    }
    private void ConstructFileTable() {

        // 表头数据
        String[] columnNames = { "档案号", "用户名", "时间", "文件名", "文件描述" };
        // 表格数据
        String[][] rowData = new String[20][5];

        Enumeration<Doc> f;
        try {
            // 获取哈希表信息
            f = DataProcessing.getAllDocs();

            // 行数
            int row = 0;
            // 将哈希表信息导入至表格
            while (f.hasMoreElements()) {
                Doc doc = f.nextElement();
                rowData[row][0] = doc.getID();
                rowData[row][1] = doc.getCreator();
                rowData[row][2] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(doc.getTimestamp()); // Time转String
                rowData[row][3] = doc.getFilename();
                rowData[row][4] = doc.getDescription();
                row++;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }

        // 构造表格
        Files_table.setModel(new DefaultTableModel(rowData, columnNames) {

            boolean[] columnEditables = new boolean[] { false, false, false, false, false };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }

        });

    }

    // 打开文件
    private void OpenFileActionPerformed(ActionEvent evt) {

        // 弹出文件选择框
        FileDialog OpenFileDialog = new FileDialog(this, "选择上传文件");
        OpenFileDialog.setVisible(true);

        // 获取文件路径
        String filepath = OpenFileDialog.getDirectory() + OpenFileDialog.getFile();
        Filepath_Txt.setText(filepath);

    }


    // 上传文件
    private synchronized void UploadActionPerformed(User user, ActionEvent evt) {

        String filepath = Filepath_Txt.getText();
        String fileID = FileID_Txt.getText();
        String filedescription = Filedescription_Txt.getText();

        if (StringUtil.isEmpty(filepath)) {
            JOptionPane.showMessageDialog(null, "未选择文件！");
            return;
        }
        if (StringUtil.isEmpty(fileID)) {
            JOptionPane.showMessageDialog(null, "未输入档案号！");
            return;
        }
        if (StringUtil.isEmpty(filedescription)) {
            JOptionPane.showMessageDialog(null, "未输入文件描述！");
            return;
        }

        if (user.uploadFile(fileID, filepath, filedescription)) {

            // 发送上传文件消息
            try {
                Client.SendMessage("上传文件");
                Client.ReceiveMessage();
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                return;
            }

            // 更新表格数据
            ConstructFileTable();
            JOptionPane.showMessageDialog(null, "上传成功！");
            return;

        } else {
            JOptionPane.showMessageDialog(null, "上传失败！");
            return;
        }

    }

    // 下载文件
    private synchronized void DownloadActionPerformed(User user, ActionEvent evt) throws SQLException, IOException {

        // 获取所选行序号, 若未选择其值为-1
        int selectedrow = Files_table.getSelectedRow();

        // 未选择文件的情况
        if (selectedrow == -1) {
            JOptionPane.showMessageDialog(null, "未选择文件！");
            return;
        } else {

            // 获取档案号
            String fileID = (String) Files_table.getValueAt(selectedrow, 0);
            // 若选择空行
            if (StringUtil.isEmpty(fileID)) {
                return;
            }

            // 显示确认界面: 信息, 标题, 选项个数
            int value = JOptionPane.showConfirmDialog(null, "确定要下载文件吗？", "文件下载确认界面", 2);
            // Yes=0 No=1
            if (value == 0) {
                if (user.downloadFile(fileID)) {

                    // 发送下载文件消息
                    try {
                        Client.SendMessage("下载文件");
                        Client.ReceiveMessage();
                    } catch (IOException | ClassNotFoundException e) {
                        JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                        return;
                    }

                    JOptionPane.showMessageDialog(null, "下载成功！");
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "下载失败！");
                    return;
                }

            } else if (value == 1) {
                return;
            }
        }
    }


    // 设置页面
    private void setPane(User user, int choice) {

        if (!user.getRole().equalsIgnoreCase("operator")) {
            FileID_Txt.setEditable(false);
            Filedescription_Txt.setEditable(false);
            Filepath_Txt.setEditable(false);
            Upload_Button.setEnabled(false);
            OpenFile_Button.setEnabled(false);
        }

        if (choice == 0) {
            tabbedPane.setSelectedComponent(Upload_Panel);
        } else if (choice == 1) {
            tabbedPane.setSelectedComponent(Download_Panel);
        }

    }

    // 返回
    private void ReturnActionPerformed(ActionEvent evt) {
        this.dispose();
    }




}

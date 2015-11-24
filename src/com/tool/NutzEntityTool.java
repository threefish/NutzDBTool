package com.tool;

import com.bean.DB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 黄川
 * Date Time: 2015/11/21 002120:11
 */

public class NutzEntityTool {
    private JTextField driverClassText;
    private JTextField urlText;
    private JTextField usernameText;
    private JTextField passordText;
    private JButton connBtn;
    private JPanel rootPanel;
    private JButton createEntity;
    private JTextField javaPackge;
    private JTextField savePath;
    private JButton choseButton;
    private JTextField parentClass;
    private JList tableList;
    private JCheckBox entityNameReg;
    private JTextArea sqlserverComMicrosoftSqlserverTextArea;
    private DefaultListModel listModel = new DefaultListModel();
    private Connection dbConn = null;
    private DB db;

    /**
     * 获取所有表
     *
     * @param conn
     * @return
     */
    public void loadTableNames(Connection conn, DB db) {
        List<String> tabls = DbTool.getTableNames(conn, db);
        loadList(tabls);
    }

    /**
     * 加载jslit数据
     *
     * @param listbean
     */
    public void loadList(List<String> listbean) {
        listModel.removeAllElements();
        for (int i = 0; i < listbean.size(); i++) {
            String str = listbean.get(i);
            listModel.add(i, str);
        }
        tableList.setModel(listModel);
    }

    /**
     * 提示错误信息
     *
     * @param e1
     */
    private void showErrorMsg(Exception e1) {
        e1.printStackTrace();
        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE, null);
        connBtn.setEnabled(true);
        choseButton.setEnabled(false);
        createEntity.setEnabled(false);
        connBtn.setText("连接");
    }

    /**
     * 重新定义实体类名
     *
     * @param className
     * @return
     */
    private String enToUpperCase(String className) {
        className = className.trim().toLowerCase();
        String index = className.substring(0, 1);
        String temp = className.substring(1, className.length());
        className = index.toUpperCase() + temp;
        return className;
    }

    /**
     * 判断数据库类型
     *
     * @param driverClass
     * @return
     */
    private DB CheckDB(String driverClass) {
        if (driverClass.indexOf("microsoft.sqlserver") > -1) {
            return DB.sqlserver;
        }
        if (driverClass.indexOf("mysql.jdbc") > -1) {
            return DB.mysql;
        } else {
            return DB.unknow;
        }
    }

    public NutzEntityTool() {
        /**
         * 连接数据库并加载表
         */
        connBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    db= CheckDB(driverClassText.getText());
                    if (db!= DB.unknow) {
                        Class.forName(driverClassText.getText());
                        dbConn = DriverManager.getConnection(urlText.getText(), usernameText.getText(), passordText.getText());
                        connBtn.setEnabled(false);
                        connBtn.setText("连接成功！");
                        choseButton.setEnabled(true);
                        createEntity.setEnabled(true);
                        loadTableNames(dbConn, CheckDB(driverClassText.getText()));
                    } else {
                        JOptionPane.showMessageDialog(null, "暂时不支持该数据库类型！", "错误提示", JOptionPane.ERROR_MESSAGE, null);
                    }
                } catch (ClassNotFoundException e1) {
                    showErrorMsg(e1);
                } catch (SQLException e1) {
                    showErrorMsg(e1);
                }
            }
        });
        /**
         * 生成实体类
         */
        createEntity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> lists = tableList.getSelectedValuesList();
                if (lists.size() != 0) {
                    if (javaPackge.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "请输入包名", "错误提示", JOptionPane.ERROR_MESSAGE, null);
                    } else if (savePath.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "请选择保存路径", "错误提示", JOptionPane.ERROR_MESSAGE, null);
                    } else {
                        for (int i = 0; i < lists.size(); i++) {
                            String tableName = String.valueOf(lists.get(i));
                            String className = tableName;
                            if (entityNameReg.isSelected()) {
                                className = enToUpperCase(className);
                            }
                            if (parentClass.getText().trim().isEmpty()) {
                                CodeCreateTool.CreateEntiy(tableName, javaPackge.getText().trim(), savePath.getText(), dbConn, null, className, db);
                            } else {
                                CodeCreateTool.CreateEntiy(tableName, javaPackge.getText().trim(), savePath.getText(), dbConn, parentClass.getText().trim(), className, db);
                            }
                        }
                        JOptionPane.showMessageDialog(null, "操作完成！", "提示", JOptionPane.PLAIN_MESSAGE, null);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请至少选择一张表！", "提示", JOptionPane.PLAIN_MESSAGE, null);
                }
            }
        });
        choseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(1);// 设定只能选择到文件夹
                int returnVal = chooser.showOpenDialog(rootPanel);// 此句是打开文件选择器界面的触发语句
                if (returnVal == 1) {
                    return;
                } else {
                    File f = chooser.getSelectedFile();// f为选择到的目录
                    System.out.println(f.getAbsolutePath());
                    String[] s = f.getAbsolutePath().toString().split("\\\\");
                    String realPath = "";
                    for (int i = 0; i < s.length; i++) {
                        if (i == 0) {
                            realPath = s[i];
                        } else {
                            realPath += "\\\\" + s[i];
                        }
                    }
                    savePath.setText(realPath);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainFrom");
        frame.setTitle("Nutz 数据库反向生成代码工具");
        frame.setResizable(false);
        frame.setContentPane(new NutzEntityTool().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}

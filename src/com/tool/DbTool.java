package com.tool;

import com.bean.DB;
import com.bean.TableFileds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 黄川
 * Date Time: 2015/11/22 002220:33
 */

public class DbTool {

    /**
     * 获取所有表
     *
     * @param conn
     * @param db
     * @return
     */
    public static List<String> getTableNames(Connection conn, DB db) {
        List<String> list = new ArrayList<String>();
        switch (db) {
            case mysql:
                list = getMysqlTableNames(conn);
                break;
            case sqlserver:
                list = getSqlServerTableNames(conn);
                break;
        }
        return list;
    }

    /**
     * 获取表所有字段信息
     *
     * @param conn
     * @param tableName
     * @param db
     * @return
     */
    public static List<TableFileds> getTableFileds(Connection conn, String tableName, DB db) {
        List<TableFileds> list = new ArrayList<TableFileds>();
        switch (db) {
            case mysql:
                list = getMysqlTableFileds(conn, tableName);
                break;
            case sqlserver:
                list = getSqlserverTableFileds(conn, tableName);
                break;
        }
        return list;
    }


    private static List<String> getSqlServerTableNames(Connection conn) {
        List<String> tabls = new ArrayList<String>();
        try {
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM sys.tables");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tabls.add(String.valueOf(rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabls;
    }

    private static List<String> getMysqlTableNames(Connection conn) {
        List<String> tabls = new ArrayList<String>();
        try {
            String tableName = conn.getMetaData().getURL();
            if(tableName.indexOf("?")>-1){
                tableName = tableName.substring(tableName.lastIndexOf("/") + 1, tableName.indexOf("?"));
            }else{
                tableName = tableName.substring(tableName.lastIndexOf("/") + 1, tableName.length());
            }
            PreparedStatement pst = conn.prepareStatement("select TABLE_NAME from information_schema.`TABLES` WHERE TABLE_SCHEMA=?");
            pst.setString(1, tableName);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tabls.add(String.valueOf(rs.getString("TABLE_NAME")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabls;
    }


    private static List<TableFileds> getMysqlTableFileds(Connection conn, String tableName) {
        List<TableFileds> tabls = new ArrayList<TableFileds>();
        try {
            String sql = "select COLUMN_NAME,ORDINAL_POSITION,IS_NULLABLE,DATA_TYPE,COLUMN_COMMENT from information_schema.`columns` where table_name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tableName);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                TableFileds tb = new TableFileds();
                tb.setFieldName(rs.getString("COLUMN_NAME"));
                tb.setColid(rs.getInt("ORDINAL_POSITION"));
                tb.setNullable(rs.getString("IS_NULLABLE").equals("NO"));
                tb.setTypeName(rs.getString("DATA_TYPE"));
                tb.setComment(rs.getString("COLUMN_COMMENT"));
                tabls.add(tb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabls;
    }


    private static List<TableFileds> getSqlserverTableFileds(Connection conn, String tableName) {
        List<TableFileds> tabls = new ArrayList<TableFileds>();
        try {
            String sql = "SELECT SC.name fieldName,SC.colid colid,ST.name typeName,SC.length,SC.isnullable FROM sysobjects SO,syscolumns   SC,systypes ST ";
            sql += " WHERE SO.id = SC.id  AND   SO.xtype = 'U'  ";
            sql += " AND   SO.status >= 0 ";
            sql += " AND   SC.xtype = ST.xusertype ";
            sql += " AND   SO.name = ? ";
            sql += " ORDER BY  SO.name, SC.colorder ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tableName);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                TableFileds tb = new TableFileds();
                tb.setFieldName(rs.getString("fieldName"));
                tb.setColid(rs.getInt("colid"));
                tb.setLength(rs.getInt("length"));
                tb.setNullable(rs.getInt("isnullable") != 0);
                tb.setTypeName(rs.getString("typeName"));
                tabls.add(tb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabls;
    }

}

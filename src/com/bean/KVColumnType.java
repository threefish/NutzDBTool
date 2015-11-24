package com.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 黄川
 * Date Time: 2015/11/23 002313:15
 */

public class KVColumnType {

    private Map<String, String> columns = new HashMap<String, String>();
    public KVColumnType(DB db) {
        if (db == DB.mysql) {
            setMysqlColumns();
        } else if (db == DB.sqlserver) {
            setSqlserverColumns();
        } else {
            System.out.println("暂时不支持当前类型数据库");
        }
    }
    /**
     * 设置mysql字段类型映射关系
     */
    private void setMysqlColumns() {
        columns.clear();
        columns.put("int", "int");
        columns.put("varchar", "String");
        columns.put("char", "String");
        columns.put("blob", "byte[]");
        columns.put("integer", "long");
        columns.put("tinyint", "long");
        columns.put("smallint", "long");
        columns.put("mediumint", "long");
        columns.put("bit", "Boolean");
        columns.put("bigint", "java.math.BigInteger");
        columns.put("float", "float");
        columns.put("double", "double");
        columns.put("decimal", "java.math.BigDecimal");
        columns.put("tinyint", "java.lang.Integer");
        columns.put("date", "java.sql.Date");
        columns.put("time", "java.sql.Time");
        columns.put("datetime", "java.sql.Timestamp");
        columns.put("timestamp", "java.sql.Timestamp");
        columns.put("year", "java.sql.Date");
    }


    /**
     * 设置Sqlserver字段类型映射关系
     */
    private void setSqlserverColumns(){
        columns.clear();
        columns.put("numeric","java.math.BigDecimal");
        columns.put("bigint","long");
        columns.put("int","int");
        columns.put("binary","byte[]");
        columns.put("char","String");
        columns.put("decimal","java.math.BigDecimal");
        columns.put("float","double");
        columns.put("money","java.math.BigDecimal");
        columns.put("nchar","String");
        columns.put("ntext","String");
        columns.put("varchar","String");
        columns.put("nvarchar","String");
        columns.put("real","float");
        columns.put("smalldatetime","java.sql.Timestamp");
        columns.put("smallint","java.lang.Integer");
        columns.put("smallmoney","java.math.BigDecimal");
        columns.put("sql_variant","String");
        columns.put("text","String");
        columns.put("timestamp","byte[]");
        columns.put("tinyint","java.lang.Integer");
        columns.put("uniqueidentifier","String");
        columns.put("varbinary","byte[]");
        columns.put("bit","Boolean");
        columns.put("date","Date");
        columns.put("datetime","java.sql.Timestamp");
        columns.put("timestamp","java.sql.Timestamp");
    }

    /**
     * 取值
     * @param key
     * @return
     */
    public String getJavaType(String key) {
        return columns.get(key);
    }

}

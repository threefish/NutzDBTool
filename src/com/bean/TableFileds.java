package com.bean;

/**
 * Created with IntelliJ IDEA.
 * User: 黄川
 * Date Time: 2015/11/2114:35
 * To change this template use File | Settings | File Templates.
 */

public class TableFileds {

    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 索引
     */
    private int colid;
    /**
     * 字段类型
     */
    private String typeName;
    /**
     * 字段长度
     */
    private int length;

    /**
     * 是否允许NULL
     */
    private boolean nullable;

    private String comment;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getColid() {
        return colid;
    }

    public void setColid(int colid) {
        this.colid = colid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}

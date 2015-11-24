package com.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 实体类
 */
public class Entity {
    //表名
    private String tableName;
    // 实体所在的包名
    private String javaPackage;
    // 实体类名
    private String className;
    // 父类名
    private String superclass;
    //时间
    private String createDate;
    // 属性集合
    List<Property> properties;
    // 是否有构造函数
    private boolean constructors;

    public String getJavaPackage() {
        return javaPackage;
    }

    public String getCreateDate() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSuperclass() {
        return superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public boolean isConstructors() {
        return constructors;
    }

    public void setConstructors(boolean constructors) {
        this.constructors = constructors;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

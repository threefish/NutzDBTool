package com.tool;

import com.bean.*;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.net.URLDecoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 黄川
 * Date Time: 2015/11/2114:50
 * To change this template use File | Settings | File Templates.
 */

public class CodeCreateTool {
    private static File javaFile = null;
    private static String tempPath;
    private static Configuration cfg = new Configuration();

    static {
        try {
            String jarPath= CodeCreateTool.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            tempPath= jarPath.substring(0,jarPath.lastIndexOf("/")+1)+File.separator+"template";
            tempPath = URLDecoder.decode(tempPath, "UTF-8");
            // 指定 模板文件从何处加载的数据源，这里设置一个文件目录
            cfg.setDirectoryForTemplateLoading(new File(tempPath));
            cfg.setObjectWrapper(new DefaultObjectWrapper());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建数据模型
     *
     * @return
     */
    private static Map<String, Object> createDataModel(String tableName, String packageName, Connection conn, String toPath, String parentClass, String className, DB db) {
        KVColumnType kvColumnType=new KVColumnType(DB.sqlserver);
        Map<String, Object> root = new HashMap<String, Object>();
        Entity entity = new Entity();
        entity.setJavaPackage(packageName); // 创建包名
        entity.setClassName(className);  // 创建类名
        entity.setConstructors(true); // 是否创建构造函数
        entity.setTableName(tableName);
        if (parentClass != null) {
            entity.setSuperclass(parentClass);//父类
        }
        List<Property> propertyList = new ArrayList<Property>();
        List<TableFileds> tableF = DbTool.getTableFileds(conn, tableName,db);
        for (TableFileds tf : tableF) {
            Property property = new Property();
            property.setPropertyName(tf.getFieldName().toLowerCase());
            property.setColumName(tf.getFieldName().toLowerCase());
            String typeName = kvColumnType.getJavaType(tf.getTypeName());
            if (!typeName.equals("")) {
                property.setJavaType(typeName);
                property.setDesc(tf.getComment());
            }else {
                property.setJavaType("String");
                property.setDesc("我暂时没有找到合适的类型来进行转换！！！");
            }
            propertyList.add(property);
        }
        // 将属性集合添加到实体对象中
        entity.setProperties(propertyList);
        // 创建.java类文件
        File outDirFile = new File(toPath);
        if (!outDirFile.exists()) {
            outDirFile.mkdir();
        }
        javaFile = toJavaFilename(outDirFile, entity.getJavaPackage(), entity.getClassName());
        root.put("entity", entity);
        return root;
    }

    /**
     * 创建.java文件所在路径 和 返回.java文件File对象
     *
     * @param outDirFile    生成文件路径
     * @param javaPackage   java包名
     * @param javaClassName java类名
     * @return
     */
    private static File toJavaFilename(File outDirFile, String javaPackage, String javaClassName) {
        String packageSubPath = javaPackage.replace('.', '/');
        File packagePath = new File(outDirFile, packageSubPath);
        File file = new File(packagePath, javaClassName + ".java");
        if (!packagePath.exists()) {
            packagePath.mkdirs();
        }
        return file;
    }

    public static boolean CreateEntiy(String tableName, String packageName, String toPath, Connection conn, String parentClass, String className, DB db) {
       boolean flag=false;
        String repath="";
        try {
            // 获取 模板文件
            Template template = cfg.getTemplate("entity.ftl");

            Map<String, Object> root = createDataModel(tableName, packageName, conn, toPath, parentClass, className,db);
            //  合并 模板 和 数据模型
            // 创建.java类文件
            if (javaFile != null) {
                Writer javaWriter = new FileWriter(javaFile);
                template.process(root, javaWriter);
                javaWriter.flush();
                javaWriter.close();
            }
            repath=javaFile.getCanonicalPath();
            flag=true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println((flag ? "成功:" : "失败:") + repath);
        }
        return flag;
    }
}

package ${entity.javaPackage};
import java.io.Serializable;
import org.nutz.dao.entity.annotation.*;
/**
 * 通过 DBCodeCreateTool 代码自动生成
 * @author 黄川
 * @time ${entity.createDate}
 */
@Table("${entity.tableName}")
public class ${entity.className}<#if entity.superclass?has_content> extends ${entity.superclass} </#if> implements Serializable{
    private static final long serialVersionUID = 1L;
    /********** 属性 ***********/
<#list entity.properties as property>
    <#if property.desc?has_content>/****${property.desc} ****/</#if>
    @Column("${property.columName}")
    private ${property.javaType} ${property.propertyName};
</#list>
<#if entity.constructors>
    public ${entity.className}() {}
    public ${entity.className}(<#list entity.properties as property>${property.javaType} ${property.propertyName}<#if property_has_next>, </#if></#list>) {
    <#list entity.properties as property>
        this.${property.propertyName} = ${property.propertyName};
    </#list>
    }
</#if>
    /********** get/set ***********/
<#list entity.properties as property>
    public ${property.javaType} get${property.propertyName?cap_first}() {
        return ${property.propertyName};
    }
    public void set${property.propertyName?cap_first}(${property.javaType} ${property.propertyName}) {
        this.${property.propertyName} = ${property.propertyName};
    }
</#list>
}

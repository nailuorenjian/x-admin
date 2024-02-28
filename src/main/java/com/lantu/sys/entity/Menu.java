package com.lantu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author kkk
 * @since 2024-01-07
 */
@TableName("x_menu")
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    private String component;

    private String path;

    private String redirect;

    private String name;

    private String title;

    private String icon;

    private Integer parentId;

    private String isLeaf;

    private Boolean hidden;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Menu> children;

    @TableField(exist = false)
    private Map<String,Object> meta;
    public Map<String,Object> getMeta(){
        meta = new HashMap<>();
        meta.put("title",title);
        meta.put("icon",icon);
        return meta;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }
    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "menuId=" + menuId +
            ", component=" + component +
            ", path=" + path +
            ", redirect=" + redirect +
            ", name=" + name +
            ", title=" + title +
            ", icon=" + icon +
            ", parentId=" + parentId +
            ", isLeaf=" + isLeaf +
            ", hidden=" + hidden +
        "}";
    }
}

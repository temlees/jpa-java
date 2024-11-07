package com.ohgiraffers.section03.projection;


import jakarta.persistence.*;

import java.util.List;

@Entity(name = "bidirection_category")
@Table(name ="tbl_category")
public class BIDirectionCategory {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    @OneToMany(mappedBy = "category")
    private List<BIDirectionMenu> menuList;

    public BIDirectionCategory() {
    }

    public BIDirectionCategory(int categoryCode, String categoryName, Integer refCategoryCode, List<BIDirectionMenu> menuList) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
        this.menuList = menuList;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }

    public List<BIDirectionMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<BIDirectionMenu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "BIDirectionCategory{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                '}';
    }
}

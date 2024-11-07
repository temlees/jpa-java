package com.ohgiraffers.section03.projection;


import jakarta.persistence.*;

@Entity(name ="bidirection_menu")
@Table(name ="tbl_menu")
public class BIDirectionMenu {

    @Id
    @Column(name = "menu_code")
    private String menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private BIDirectionCategory category;

    @Column(name = "orderable_status")
    private String orderableStatus;

    public BIDirectionMenu() {
    }

    public BIDirectionMenu(String menuCode, String menuName, int menuPrice, BIDirectionCategory category, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        this.orderableStatus = orderableStatus;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public BIDirectionCategory getCategory() {
        return category;
    }

    public void setCategory(BIDirectionCategory category) {
        this.category = category;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "BIDirectionMenu{" +
                "menuCode='" + menuCode + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", category=" + category +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}

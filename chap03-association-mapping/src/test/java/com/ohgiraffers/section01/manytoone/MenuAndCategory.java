package com.ohgiraffers.section01.manytoone;


/*
*   @JoinColumn 은 다대일 연관 관계에서 사요오디는 어노테이션이다.
*   다음과 같은 속성들을 사용할 수 있다
*
* name : 참조하는 테이블의 컬럼명을 지정한다
* referencedColumnName : 참조되는 컬럼명를 지정한다
* nullable : 참조하는 테이블의 컬럼에 null 값을 허용할지 여부 지정한다
* unique : 참좌는 테이블의 컬럼에 유일성 제약조건을 추가할지 여부 지정한다
* insertable: 새로운 엔티티가 저장될때 이 참조 컬럼이 sql insert 문에 포함될지 지정한다
* updatable: 엔티티가 업데이트 될때 이 참조 컬럼이 update 문에 포함될지 지정한다
* table: 참조하는 테이블의 이름 지정 지정한다
* foreignKey: 참조하는 테이블에 생성될 외래키에 대한 추가 정보를 지정한다
*
* @ManyToOne 은 디테일 연관관계에서 사용되는 어노테이션이다
* 다음과 같은 속성을 사용할 수 있다
* cascade : 연관된 엔티티에 대한 영속성 전이를 설정
* fetch : 연관된 엔티티를 로딩하는 전략을 설정
* optional: 연관된 엔티티가 필수 인지 선택인지 설정
* */

import jakarta.persistence.*;

@Entity(name = "menu_and_category")
@Table(name = "tbl_menu")
public class MenuAndCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_price")
    private int menuPrice;  // 수정: `menu_price`로 컬럼명 변경

    /*
    *   CascadeType
    * -Persist : 자식의 값이 저장될때 연관관계를 가지고 있는 부모의 값도 함께 저장됨
    * - REMOVE : 엔티티를 제거할 떄 연관된 엔티티도 모두 제거한다
    * - Merge : 엔티티 상태를 병합할때 연관된 하위 엔티티도 모두 병합한다
    * - DETACH : 엔티티를 DETACH()하면  연관 엔티티도 DETACH 상태가 된다.
    * */
    @JoinColumn(name = "category_code")
  //  @ManyToOne(cascade = CascadeType.PERSIST)
    //@ManyToOne(cascade = CascadeType.REMOVE)
  //  @ManyToOne(cascade = CascadeType.MERGE)
    @ManyToOne(cascade = CascadeType.DETACH)
    private Category category;

    @Column(name = "menu_name")
    private String menuName;


    @Column(name = "orderable_status")
    private String orderableStatus;


    public MenuAndCategory() {
    }

    public MenuAndCategory(int menuCode, int menuPrice, Category category, String menuName, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuPrice = menuPrice;
        this.category = category;
        this.menuName = menuName;
        this.orderableStatus = orderableStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public String toString() {
        return "MenuAndCategory{" +
                "menuCode=" + menuCode +
                ", menuPrice=" + menuPrice +
                ", category=" + category +
                ", menuName='" + menuName + '\'' +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
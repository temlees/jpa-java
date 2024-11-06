package com.ohgiraffers.section.problem;

import com.ohgiraffers.section01.problem.Category;
import com.ohgiraffers.section01.problem.Menu;
import com.ohgiraffers.section01.problem.MenuAndCategory;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemOfUsingDirectSQLTests {


/*
*  junit = 단위 테스트를 위한 프레임 워크이다
* 코드의 품질을 개선하고 뮤지보수겅을 높이기 위해 사용한다 메소드 단위로 테스트를 수행하며
* 예상한 결과와 실제 결과를 비교하여 테스트를 수행한다
* 이를통해 개발자들은 코드의 문제점을 발견하고 수정할 수 있게 된다.
* */
    private Connection con;

    @BeforeEach // 테스트 메소드가 동작하기전에 실행
    void setConnection() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/menudb";
        String user = "gangnam";
        String password = "gangnam";

        Class.forName(driver);
        con = DriverManager.getConnection(url,user,password);
        con.setAutoCommit(false);
    }

    @AfterEach //테스트 메소드가 실행된후 진행  test라서 실제 db에 반영되지 말라는 뜻
    void closeConnection() throws SQLException {
        con.rollback();
        con.close();
    }

    /*
    *   JDBC API를 이용해 직접sql을 다룰떄 발생할 수 있는 문제점
    * 1. 데이터 변환 sql작성 jdbc api 코드등의 중복 작성 (개발시간 증가, 유지보수성 저하)
    * 2. sql에 의존하여 개발
    * 3. 패러다임 불일치
    * 4. 동일성 보장문제
    * */

    // 1. 데이터 변환 sql작성 jdbc api 코드등의 중복 작성 (개발시간 증가, 유지보수성 저하)
    @DisplayName("직접 sql을 작성하여 메누를 조회할때 문제확인")
    @Test
    void testDirectSelectSql() throws SQLException {
        String query = "SELECT MENU_CODE , MENU_NAME,MENU_PRICE,CATEGORY_CODE," +
                "ORDERABLE_STATUS FROM TBL_MENU";

        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        List<Menu> menuList = new ArrayList<>();

        while(rset.next()) {
            Menu menu = new Menu();
            menu.setMenuCode(rset.getInt("MENU_CODE"));
            menu.setMenuName(rset.getString("MENU_NAME"));
            menu.setMenuPrice(rset.getInt("MENU_PRICE"));
            menu.setCategoryCode(rset.getInt("CATEGORY_CODE"));
            menu.setOrderableStatus(rset.getString("ORDERABLE_STATUS"));
            menuList.add(menu);
        }

        Assertions.assertNotNull(menuList);
        for (Menu menu : menuList) {
            System.out.println(menu);
        }
        rset.close();
        stmt.close();


    }


    //2. sql에 의존하여 개발
    /*
    *   요구사항 변경에따라 어플리케이션 수정이 sql 의 수정으로 이어진다
    * 이러한 수정에 영향을 미치는 것은 오류 발생시킬 가능성도 잇지만 유지보수성에도 악영향을 미친다.
    * 또한 객체를 사용할 때 sql 에 의존하면 객체에 값이 무엇이 들었는지 확인하기 위해 sql 을 매번 살펴야 한다.
    * */

    @DisplayName("조회 항목 변경에 따른 의존성 확인")
    @Test
    void testChangeSelectColumn() throws SQLException {
        String query = "SELECT MENU_CODE,MENU_NAME FROM TBL_MENU";

        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        List<Menu> menuList = new ArrayList<>();
        while(rset.next()) {
            Menu menu = new Menu();
            menu.setMenuCode(rset.getInt("MENU_CODE"));
            menu.setMenuName(rset.getString("MENU_NAME"));
            menuList.add(menu);
        }
        rset.close();
        stmt.close();

    }
// 연관된 객체 문제
    /*
    *  연관된 객체가 잇는 경우 , 객체 간의 관계를 수동으로 매핑해야하기 때문에 코드가 복잡해지고
    * 유지 보수가 어려워지는 문제가 존재..
    *
    *
    * */

    @DisplayName("연관된 객체 문제 확인")
    @Test
    void testAssociatedObject() throws SQLException {
        String query = "SELECT A.MENU_CODE,A.MENU_NAME,A.MENU_PRICE,B.CATEGORY_CODE,B.CATEGORY_NAME,A.ORDERABLE_STATUS " +
                "FROM TBL_MENU A JOIN TBL_CATEGORY B ON(A.CATEGORY_CODE = B.CATEGORY_CODE)";


        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        List<MenuAndCategory>menuAndCategories = new ArrayList<>();
        while(rset.next()) {
            MenuAndCategory menuAndCategory = new MenuAndCategory();

            menuAndCategory.setMenuCode(rset.getInt("MENU_CODE"));
            menuAndCategory.setMenuName(rset.getString("MENU_NAME"));
            menuAndCategory.setMenuPrice(rset.getInt("MENU_PRICE"));
            menuAndCategory.setCategory(new Category(rset.getInt("Category_code"),rset.getString("Category_name")));
            menuAndCategory.setOrderableStatus(rset.getString("ORDERABLE_STATUS"));
            menuAndCategories.add(menuAndCategory);

        }

        Assertions.assertNotNull(menuAndCategories);
        for (MenuAndCategory menu : menuAndCategories) {
            System.out.println(menu);
        }
        rset.close();
        stmt.close();

    }

    //동일성 보장 문제
    @DisplayName("조회한 두개의 행을 담은 객체의 동일성 비교 테스트")
    @Test
    void testEquals() throws SQLException {
        String query = "SELECT MENU_CODE,MENU_NAME FROM TBL_MENU WHERE MENU_CODE = 12";


        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        Menu menu1 = null;
        while (rset.next()) {
            menu1 = new Menu();
            menu1.setMenuCode(rset.getInt("MENU_CODE"));
            menu1.setMenuName(rset.getString("MENU_NAME"));

        }
        Statement stmt2 = con.createStatement();
        ResultSet rset2 = stmt2.executeQuery(query);

        Menu menu2 = null;
        while (rset2.next()) {
            menu2 = new Menu();
            menu2.setMenuCode(rset2.getInt("MENU_CODE"));
            menu2.setMenuName(rset2.getString("MENU_NAME"));

        }
        rset.close();
        rset2.close();
        stmt2.close();
        stmt.close();

        Assertions.assertFalse(menu1 == menu2);


    }

}

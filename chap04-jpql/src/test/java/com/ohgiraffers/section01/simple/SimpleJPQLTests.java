package com.ohgiraffers.section01.simple;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class SimpleJPQLTests {
    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory(){
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager(){
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void closeManager(){
        entityManager.close();
    }

    @AfterAll
    public static void closeFactory(){
        entityManagerFactory.close();
    }

    /*
    * jpql(java Persistence query language)
    * jpql 은 엔티티 객체를 중심으로 개발할  수 있는 객체 지향 쿼리이다.
    * sql 보다 간결하며 특정 dbms에 맞는 sql문을 실행하게 된다
    * jpql 은 find()메소드를 통한 조회와 다르게 항상 데이터베이스에 sql문을 실행시켜 결과조회
    * */

    /*
    * jpql의 기본 문법
    * select update, delete 등의 키워드는 sql과 동일하다
    * insert는 persist 를 사용한다
    * 키워드는 대소문자를 구분하지 않지만 엔티티와속성은 대소문자를 구분함에 유의한다..
    * 별칭을 사용하는 것이 권장 된다.
    *
    * jpql 사용법
    * 1. 작성한 jpql을 createQuery()를 통해 쿼리 객체로 만든다
    * 2. 쿼리 객체는 TypeQuery , Query 두 가지가 잇다
    * -TypeQuery : 반환할 타입을 명확하게 지정하는 방식일때 사용하며 쿼리 객체의 메소드 실행 결과로
    * 지정된 타입이 반환 된다...
    * -query :  반환할 타입을 명확하게 지정할 수 없을 때 사용하며 쿼리 객체 메소드의 실행결과로
    * object 또는 object[]이 반환된다...
    *
    * 3. 쿼리 객체에서 제공하는 메소드 getSingleResult() 또는 getResultList()를 호출해
    * 쿼리를 실행 하고 db를 조회한다...
    * - getSingleResult() : 결과가 정확히 한 행일 경우 사용
    * -  getResultList() : 결과가 2행 이상일 경우 사용하며 "컬렉션"을 반환한다.. 결과가 없으면 빈컬렉션
    * */
//테이블 이름 대신 엔티티 name 으로 쓴다
    @Test
    void Query_이용한_단일메뉴(){
        String jpql = "select m.menuName from menu_section01 AS m WHERE m.menuCode =7";
        Query query = entityManager.createQuery(jpql);

        Object resultMenuName = query.getSingleResult();
        Assertions.assertTrue((resultMenuName instanceof String));
        Assertions.assertEquals("민트미역국", resultMenuName);
    }

    @Test
    void Query를_이용한_다중행_조회_테스트(){
        String jpql = "select m.menuName from menu_section01 AS m";
        Query query = entityManager.createQuery(jpql);

        List<Object> foundMenuList = query.getResultList();
        Assertions.assertNotNull(foundMenuList);
        for (Object menu : foundMenuList) {
            System.out.println(menu);
        }


    }


    @Test
    void TypeQuery이용한_단일행_조회(){
        String jpql = "SELECT m FROM menu_section01 AS m WHERE m.menuCode =7";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);

        Menu foundMenu = query.getSingleResult();

        Assertions.assertEquals(7, foundMenu.getMenuCode());
        System.out.println(foundMenu);
    }

    @Test
    void TypeQuery_이용한_다중행_조회_테스트(){
        String jpql = "SELECT m From menu_section01 AS m ";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);

        List<Menu> foundMenuList = query.getResultList();
        Assertions.assertNotNull(foundMenuList);
        for (Menu menu : foundMenuList) {
            System.out.println(menu);
        }

    }

    @Test
    void distinct_활용한중복제거_여러행_조회(){
        String jpql = "select distinct m.categoryCode from menu_section01 as m";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        List<Integer> foundCategoryList = query.getResultList();

        Assertions.assertNotNull(foundCategoryList);
        for (Integer category : foundCategoryList) {
            System.out.println(category);
        }
    }

    //카테고리 코드가 6,10번인 메뉴 조회
    @Test
    void 카테고리코드610(){
        String jpql = "select m.categoryCode from menu_section01 as m where m.categoryCode =6 or m.categoryCode =10";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> foundMenuList = query.getResultList();
        Assertions.assertNotNull(foundMenuList);
        for (Menu menu : foundMenuList) {
            System.out.println(menu);
        }
    }

    //메뉴 이름에 마늘이 포함된 메뉴
    @Test
    void 마늘(){
        String jpql =  "select m from menu_section01 as m where m.menuName Like '%마늘%'";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> foundMenuList = query.getResultList();
        Assertions.assertNotNull(foundMenuList);
        for (Menu menu : foundMenuList) {
            System.out.println(menu);
        }
    }

    /*
    * mybatis 는 복잡한 sql 쿼리와  데이터 베이스 성능 최적화 측면에서.
    * jpa 는 객체 지향적인 데이터 접근과 자동화된 상태 관리 측면에서 주로 사용된다
    *
    * jpa 는 객체 지향적인 패러다임을 통해 db와 의 연동을 자동화 하지만
    * 복잡한 sql 쿼리나 대량의 데이터 처리에서는 성능에 제한이 잇다
    * 이런 경우 mybatis 는 명시적이고 복잡한 쿼리를 작성하는 데 적합하며 최적화에 유리하다
    *
    * Mybatis 는 복잡한 조인 , 서브쿼리,다양한 함수등을 처리할 때 더 유연하고,
    * 쿼리의 복잡성을 완전히 개발자가 컨트롤 할 수 있다.
    * sql 을 직접작성하므로 객체 변환없이 순수 sql 을 실행 할수있다
    * jpa 는 orm 을 통해 객체로 변환하는 과정을 거쳐야하고 복잡해질 수록 성능적인 문제와
    * 추가적인 시간이 소모된다.
    * */
}

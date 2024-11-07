package com.ohgiraffers.section02.parameter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Scanner;

public class ParameterBindingTest {

    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }


    /*
     *   파라미터를 바인딩하는 방법
     *  1. 이름 기준 파라미터 ':' 다음에 이름 기준 파라미터를 지정한다
     * 2. 위치 기준 파라미터 '?' 다음에 값을 주고 위치값은 1부터 시작.
     *
     *
     *  */

    @Test
    void 이름_기준_파라미터_바인딩_메뉴_조회_테스트() {
        String menuNameParameter = "한우딸기국밥";

        String jpql = "SELECT m from menu_section02 m where m.menuName = :menuName";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).setParameter("menuName", menuNameParameter).getResultList();

        Assertions.assertNotNull(menuList);
        for (Menu menu : menuList) {
            System.out.println(menu);
        }

    }

    @Test
    void 위치_기준_파라미터_바인딩_메뉴_목록_조회_테스트() {

        String menuNameParameter = "한우딸기국밥";
        String jpql = "SELECT m from menu_section02 m where m.menuName = ?1";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).setParameter(1, menuNameParameter).getResultList();


        Assertions.assertNotNull(menuList);
        for (Menu menu : menuList) {
            System.out.println(menu);
        }
    }

    //메뉴 이름 입력시 입력한 값이 포함된 메뉴 조회
    @Test
    void 입력_조회() {
        String menuNameParameter = "한우딸기국밥";
        String jpql = "select m from menu_section02 m where m.menuName LIKE :menuName ";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).setParameter("menuName","%"+ menuNameParameter+"%").getResultList();


        Assertions.assertNotNull(menuList);
        for (Menu menu : menuList) {
            System.out.println(menu);
        }
    }
}
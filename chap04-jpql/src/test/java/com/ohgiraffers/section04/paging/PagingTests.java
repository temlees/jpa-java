package com.ohgiraffers.section04.paging;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class PagingTests {


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
    * 페이징 처리용 sql 문법은 각각 dbms 마다 다르다는 문제점이 있다.
    * jpa는 이러한 페이징을 api를 통해 추상화해서 간단하게 처리할 수 있도록 제공한다
    *
    * -페이징(데이터베이스에서 많은 양의 데이터를 효과적으로 조회하기 위한 기술)
    * : 데이터의 일부만을 한 번에 조회하여 성능을 개선하고
    * 사용자 인터페이스 (ui)에서 데이터를 보다  쉽게 표현할 수 있다
    * 일반적으로 대량의 데이터를 한 번에 조회하는 경우 성능 저하 및 메모리 소모가 발생해
    * 페이징을 통해 필요한 데이터만을 가져오는 것
    * */

    @Test
    void 페이징_api이용한_조회(){
        int offset =10;  //10행부터 조회
        int limit = 5; // 5개 행가져오기

        String jpql = "select m from menu_section04 m order by m.menuCode Desc";

        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
                .setFirstResult(offset) //시작위치
                .setMaxResults(limit) //조회할 갯수
        .getResultList();

        Assertions.assertNotNull(menuList);
        for (Menu menu : menuList) {
            System.out.println(menu);
        }

    }

}

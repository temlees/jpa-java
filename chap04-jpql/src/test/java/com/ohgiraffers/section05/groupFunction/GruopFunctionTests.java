package com.ohgiraffers.section05.groupFunction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class GruopFunctionTests {

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
    * JPQL 의 그룹함수는 COUNT , MAX, SUM ,AVG 로 SQL의 그룹함수와
    * 별반 차이가 없다
    * 단 몇가지 주의사항이 있다
    * 1. 그룹함수의 반환 타입은 결과 값이 정수면 Long 실수면 DOUBLE 로 반환된다
    * 2. 값이 없는 상태에서 COUNT 를 제외한 그룹함수는 NULL이 되고 COUNT만 0이 된다.
    * 따라서 반환값을 담기 위해선언하는
    * 변수 타입을 기본자료형으로 하게 되면 , 조회결과를 언박싱할때 npe가 발생
    * 3. 그룹함수의 반환 자료형은 Long or Double 형 이기 때문에
    * Having 절에서 그룹 함수 결과 값을 비교하기 위한 파라미터 타입은
    * Long or double 로 해야한다
    * */

    @Test
    void 특정_카테고리에_등록된_메뉴수_조회(){
        int categoryCodeParameter = 4;
        String jpql = "select count(m.menuPrice) from menu_section05 m " +
                "where m.categoryCode = :categoryCode";
        long countOfMenu = entityManager.createQuery(jpql,long.class)
                .setParameter("categoryCode",categoryCodeParameter)
                .getSingleResult();

        Assertions.assertTrue(countOfMenu >=0);
        System.out.println(countOfMenu);


        }
    @Test
    void count_제외_결과없을시_테스트(){
        int categoryCode= 2;
        String jpql = "select count(m.menuPrice) from menu_section05 m " +
                "where m.categoryCode = :categoryCode";
        Long sumOfPrice = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode",categoryCode).getSingleResult();

        System.out.println(sumOfPrice);

    }

    @Test
    void groupby_조회(){
        //having 절에서의 파라미터는 Long 을 사용한다.
        //그룹함수의 반환 타입이 Long 이므로
        long minPrice = 50000L;

        String jpql = "select m.categoryCode, SUM(m.menuPrice) from menu_section05 m";
    }
}

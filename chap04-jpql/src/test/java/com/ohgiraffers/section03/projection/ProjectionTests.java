package com.ohgiraffers.section03.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.List;

public class ProjectionTests {

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
    * 프로젝션(projection)
    * select 절에 조회할 대상을 지정하는 것을 프로젝션이라고 한다..
    *
    * selct 프로젝션 대상 from
    *
    * 프로젝션은 4가지 방식이 있다
    *
    * 1. 엔티티 프로젝션
    * -원하는 객체를 바로 조회할 수 있다.
    * -조회된 엔티티는 영속성 컨텍스트가 관리한다.
    *
    * 2. 임베디드 타입 프로젝션
    * -엔티티와 거의 비슷하게 사용되며 조회의 시작점이 될 수 없다.
    * -임베디드 타입은 영속성 컨텍스트에서 관리되지 않는다.
    *
    * 3. 스칼라 타입 프로젝션
    * -숫자 , 문자, 날짜 같은 기본 데이터 타입이다. (주로 db 기본 타입)
    * -스칼라 타입은 영속성 컨텍스트에서 관리하지 않는다.
    * (자바의 프로미티브 타입과 비슷한 개념)
    *
    * 4. new 명령어를 활용한 프로젝션
    * -다양한 종류의 단순 값들을 dto 로 바로 조회하는 방식으로 new 패키지명.dto명
    * 을 쓰면 해당 dto로 바로 반환 받을 수 잇다
    * new 명령어를 사용한 클래스의 객체는 엔티티가 아니므로 컨텍스트에서 관리 x
    * */

    //1. 엔티티 프로젝션
    @Test
    void 단일_엔티티_프로젝션_테스트(){
// 엔티티타입이면 영속성 컨텍스트가 관리되고있다
        String jpql = "select m from menu_section03 m ";
       List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

       Assertions.assertNotNull(menuList);
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        menuList.get(1).setMenuName("메뉴이름 변경");
        entityTransaction.commit();

    }

    @Test
    void 양방향_연관관계_엔티티_프로젝션(){
        int menuCodeParameter = 3;

        String jpql = "select m.category from bidirection_menu m where m.menuCode = :menuCode";

        BIDirectionCategory category= entityManager.createQuery(jpql, BIDirectionCategory.class)
                .setParameter("menuCode", menuCodeParameter).getSingleResult();

        Assertions.assertNotNull(category);
        System.out.println(category);

    }

    @Test
    void 임베디드(){
    String jpql = "select m.menuInfo FROM embedded_menu m";
    List<MenuInfo> menuList = entityManager.createQuery(jpql, MenuInfo.class).getResultList();

    Assertions.assertNotNull(menuList);
    for (MenuInfo menuInfo : menuList) {
        System.out.println(menuInfo);
    }
    /*
    *  @Embeddable 은 데이터베이스에서 중복을 방지하는 역할을 하지 않으며
    * 중복을 방지하려면 테이블에서 유니크 제약조건을 추가해야한다.
    *
    * @Embeddable 은 복합키를 표현할때 사용하며, 해당 필드나 조합이 유일해야 하는
    * 요구사항은 데이터 베이스의 제약조건을 통해 해결해야 한다.
    * */


    }

    //3 . 스칼라 타입 프로젝션

    @Test
    void TypdQuery_이용한스칼라타입(){
        String jpql = "select c.categoryName from category_section03  c";
        List<String> categoryNameList = entityManager.createQuery(jpql, String.class).getResultList();
    Assertions.assertNotNull(categoryNameList);
    for (String categoryName : categoryNameList) {
        System.out.println(categoryName);
    }

    }

    /*
    *  조회하려는 컬럼 값이 2개 이상인 경우 typedquery로 반환타입을 지정하지 못한다
    *  그때 query를 사용하여 object[]로 받을 수 있다.
    *
    * 카테고리 코드 , 카테고리 네임 반환받아서 출력해보기
    * */

    @Test
    void query_이용스칼라(){
        String jpql = "select c.categoryCode , c.categoryName from category_section03  c";
        List<Object[]> list = entityManager.createQuery(jpql).getResultList();
        Assertions.assertNotNull(list);
        for (int i = 0; i < list.size(); i++) {
            Object[] result = list.get(i);
            Object categoryCode = result[0];
            Object categoryName = result[1];
            System.out.println( categoryCode+ "" +categoryName);
        }
    }

}

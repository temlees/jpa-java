package com.ohgiraffers.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class ManyToOneAssociation {

    /*
    *   Association Mapping 은 Entity 클래스간의 관계를 매핑하는 것을 의미한다
    * 이를 통해 객체를 이용해 데이터 베이스 테이블 간의 관계를 매핑할 수 있다.
    *
    * 다중성에 의한 분류
    * 연관 관계가 잇는 객체 관계에서는 실제로 견관을 가지는 객체의 수에 따라 부류된다
    *
    * -n:1  1:n  1:1 관계
    *
    * jpa에서 연관관계를 잘못설정하면 성능과 데이터 일관성에 큰 영향을 줄 수 잇다.
    * 각 연관관계 유형에 따라 jpa가 db 상호작요한느 방식이 달라진다
    * 매핑 잘해야함
    *
    * 테이블 연관관계는 외래키를 이용하여 양방향 연관관계의 특징을 가진다
    * 참조에 의한 객체의 연관관계는  단방향이다
    * 객체간의 연관관계를 양방향으로 만들고 싶은 경우 반대쪽에서 필드를 추가해
    * 참조를 보관하면된다
    * 하지만 엄밀하게는 이는 양뱡향 관계가 아니라 단방향 관계 2개로 볼 수 있다
    *
    * */

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
    *   ManyToOne은 다수의 엔티티가 하나의 엔티티를 참조하는 상황에서 사용된다
    * 예를 들어 하나의 카테고리가 여러개의 메누를 가질수 있는 상황에서
    * 메뉴 엔티티가 카테고리를 참조하는것이다
    * 이 때 메뉴 엔티티가 many 카테고리 엔티티가 One이 된다
    *
    * */


    @Test
    void 다대일_테스트(){
        int menuCode = 15;

        MenuAndCategory foundMenu = entityManager.find(MenuAndCategory.class, menuCode);
        Category menuCategory = foundMenu.getCategory();

        Assertions.assertNotNull(menuCategory);
        System.out.println("menuCategory = " + menuCategory);
    }

    @Test
    void 다대일_연관관계_객체지향쿼리(){
        String jpql = "SELECT c.categoryName From menu_and_category m JOIN m.category c WHERE m.menuCode=15";

        String category = entityManager.createQuery(jpql, String.class).getSingleResult();

        Assertions.assertNotNull(category);
        System.out.println("category = " + category);
    }

    @Test
    void 다대일_연관관계_객체_삽입_테스트(){
        MenuAndCategory menuAndCategory = new MenuAndCategory();
        menuAndCategory.setMenuCode(99999);
        menuAndCategory.setMenuName("양마마구이");
        menuAndCategory.setMenuPrice(100000);

        Category category = new Category();
        category.setCategoryCode(33333);
        category.setCategoryName("신규 카테고리");
        category.setRefCategoryCode(null);

        menuAndCategory.setCategory(category);
        menuAndCategory.setOrderableStatus("Y");

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(menuAndCategory);
        entityTransaction.commit();

        MenuAndCategory foundMenuAndCategory = entityManager.find(MenuAndCategory.class, 99999);
        Assertions.assertEquals(99999, foundMenuAndCategory.getMenuCode());
        Assertions.assertEquals(33333, foundMenuAndCategory.getCategory().getCategoryCode());


    }

    @Test
    void   영속성_삭제_테스트(){
        MenuAndCategory menuAndCategory = entityManager.find(MenuAndCategory.class,99999);
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.remove(menuAndCategory);
        entityTransaction.commit();
        MenuAndCategory deleteMenu  =entityManager.find(MenuAndCategory.class,99999);
        Category category = entityManager.find(Category.class,33333);
        System.out.println(deleteMenu);
        System.out.println(category);
    }

    @Test
    void Merge_insert_test(){
        MenuAndCategory menuAndCategory = new  MenuAndCategory();
        menuAndCategory.setMenuPrice(15000);
        menuAndCategory.setMenuName("merge insert 메뉴");
        menuAndCategory.setOrderableStatus("Y");
        Category category = new Category();
        category.setCategoryName("merge 카테고리");
        menuAndCategory.setCategory(category);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        //새로운 값 MERMERGE 하면 INSERT 있으면 바뀜
        //CASCADE 넣어주면 둘다 적용
        MenuAndCategory mergedMenu = entityManager.merge(menuAndCategory);

        entityTransaction.commit();
        System.out.println(mergedMenu);

    }

    @Test
    void detach_테스트(){
        /*
        *   Detach의 경우 해당 엔티티들 영속성 컨텍스트에서 관리하지 않겠다고 하는것이다
        * (준영속화)
        * 그러나 해당 관계를 맺고 있는 엔티티의 수정이 생기는 경우 해당 엔티티는 관리중이기때문에
        * 함께 관계를 가지고 간다
        * 이러한 문제를 해결하기 위해 cascadeType을 detach로 설정하면
        * 관계요소도 함께 영속성에서 관리하지 않겠다는뜻
        *
        *
        * */

        MenuAndCategory menuAndCategory = entityManager.find(MenuAndCategory.class, 99999);
        menuAndCategory.setMenuName("변경함");
        Category category = menuAndCategory.getCategory();
        category.setCategoryName("detach카테고리 수정함");
        menuAndCategory.setCategory(category);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.detach(menuAndCategory);
        entityTransaction.commit();

        Category category1 = entityManager.find(Category.class,33333);
        System.out.println("category1 = " + category1);
    }
}

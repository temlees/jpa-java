package com.ohgiraffers.section06.compositeKey.section01.embedded;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class EmbeddedKeyTests {

    private  static EntityManagerFactory entityManagerFactory;

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
    *  jpa에서 복합키를 매핑하는 방법에는 두가지가 있다.
    * 1. @EmbeddedId 어노테이션
    * - 이방법은 복합키를 구성하는 필드들을 하나씩 클래스로 묶은 뒤 해당 클래스를
    *   어노테이션을 사용하여 매핑하는 방법이다
    * 복합키의 일부 필ㄷ만을 매핑할 수 있기 때문에, 필드 수가 많은 경우에는 유연한 매핑이
    * 가능하다는 장점이 있다.
    *
    * 2. @IdClass 어노테이션
    * -이 방법은 복합키를 구성하는 필드들을 별도의 클래스로 분리한뒤 해당 클래스를 @IdClass 어노테이션의
    * 값으로 지정해주는 것이다 이 방법은 별도의매핑클래스를 사용하지 않기 때문에 코드가 간결하다.
    *
    * 복합키의 매핑에서는 복합키 클래스의 Equal 와 hashCode 메소드를 구현해야 한다는 점에 주의 한다.
    * 이는 jpa에서 엔터티와 객체의 동일성 판단위해 필요
    *  */

    @Test
    public void 임베디드_아이디사용한복합키_매핑(){
        Member member = new Member();
        member.setMemberPK(new MemberPK(1,"user01"));
        member.setPhone("010-1111-1111");
        member.setAddress("서울시 서초구");

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(member);
        entityTransaction.commit();

        Member foundMember = entityManager.find(Member.class, member.getMemberPK());
        Assertions.assertEquals(member.getMemberPK(), foundMember.getMemberPK());
    }
}

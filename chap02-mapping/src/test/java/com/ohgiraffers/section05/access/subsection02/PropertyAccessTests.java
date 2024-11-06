package com.ohgiraffers.section05.access.subsection02;

import com.ohgiraffers.section05.access.subsection01.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class PropertyAccessTests {

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

    @Test
    void 프로퍼티접근테스트(){
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("password01");
        member.setNickName("홍길동");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(member);
        transaction.commit();

        String jpql = "SELECT memberId FROM member_section05_subsection02 WHERE memberNo =1";
        String registedNickName = entityManager.createQuery(jpql, String.class).getSingleResult();
        System.out.println(registedNickName);
        Assertions.assertEquals("user01", registedNickName);


    }
}

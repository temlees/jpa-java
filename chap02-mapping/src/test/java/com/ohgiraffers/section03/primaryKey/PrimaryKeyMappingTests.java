package com.ohgiraffers.section03.primaryKey;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class PrimaryKeyMappingTests {

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
    *   Primary Key 에는 @Id 어노테이션과 @GeneratedValue 어노테이션을 사용한다.
    * @Id 어노테이션은 엔티팉 클래스에서 PrimaryKey 역할을 하는 필드를 지정할 때 사용한다
    * 데이터 베이스마다 기본 키를 생성하는 방식이 서로 다르다.
    * @GeneratedValue 는 다음과 같은 속성을 가진다
    *
    * -strategy : 자동 생성 전략을 지정
    * -GenerationType.INDENTITY : 기본 키 생성을 데이터 베이스에 위임(Mysql의 AutoIncrement)
    * -GenerationType.SEQUENCE : 데이터 베이스 시퀀스 객체 사용(ORACLE 의 SEQUENCE)
    * -GenerationType.TABLE : 키 생성 테이블 사용
    * -GenerationType.AUTO : 자동선택
    * */

    @Test
    public void 식별자_매핑_테스트(){
        Member member = new Member();
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickName("홍길동");
        member.setPhone("010-1234-5678");

        Member member2 = new Member();
        member2.setMemberId("user02");
        member2.setMemberPwd("pass02");
        member2.setNickName("유관순");
        member2.setPhone("010-1111-2222");

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(member);
        entityManager.persist(member2);
        entityTransaction.commit();

        //엔티티 명으로 쿼리날림
        //db 에따라 달라지지 않는다..
        String jpql = "SELECT A.memberNo FROM member_section03 A";

        List<Integer> memberNoList = entityManager.createQuery(jpql, Integer.class).getResultList();
        System.out.println(memberNoList);
    }
}

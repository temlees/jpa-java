package com.ohgiraffers.section02.column;


/*
* 컬럼 매핑시 @column 어노테이션에 사용 가능한 속성들
*
* name : 매핑할 테이블의 컬럼 이름
* insertable : 엔티티 저장 시 필드 저장 여부(default : true)
* updatable : 엔티티 수정시 필드 저장여부 (default : true)
* table : 엔티티와 매핑될 테이블 이름 하나의 엔티티를 두개 이상의 테이블에 매핑할 때 사용
* nullable : null 값 허용 여부 설정,  not null 제약조건에 해당함 (default : true)
* unique : 컬럼 유일성 제약조건
* length : 문자열 길이 , String type에서만 사용(default: 255)
* columnDefinition :직접 컬럼의 ddl 조정
*
* */


import jakarta.persistence.*;

@Entity(name = "member_section02")
@Table(name = "tbl_memeber_section02")
public class Member {

    @Id
    @Column(name = "member_no")
    private int member_no;

    @Column(name = "member_id")
    private String member_id;

    @Column(name = "member_pwd")
    private String member_pwd;

    @Column(name = "nickName")
    @Transient // 테이블 생성 시 무시 . 자바에서는 필요한값이지만 db에 넣지 않을때
    private String nickName;

    @Column(name = "phone",columnDefinition = "varchar(200) default '010-0000-0000'")
    private String phone;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "address" , nullable = false)
    private String address;

    public Member() {
    }

    public Member(int member_no, String member_id, String member_pwd, String nickName, String phone, String email, String address) {
        this.member_no = member_no;
        this.member_id = member_id;
        this.member_pwd = member_pwd;
        this.nickName = nickName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_pwd() {
        return member_pwd;
    }

    public void setMember_pwd(String member_pwd) {
        this.member_pwd = member_pwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Member{" +
                "member_no=" + member_no +
                ", member_id='" + member_id + '\'' +
                ", member_pwd='" + member_pwd + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

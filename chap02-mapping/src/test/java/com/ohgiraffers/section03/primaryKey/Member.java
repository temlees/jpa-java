package com.ohgiraffers.section03.primaryKey;

import jakarta.persistence.*;


@Entity(name = "member_section03")
@Table(name = "tbl_member_section03")
public class Member {

        @Id
        @Column(name = "member_no")
        @GeneratedValue(strategy = GenerationType.IDENTITY) //오토인크리먼트 설정
        private int memberNo;

        @Column(name = "member_id")
        private String memberId;

        @Column(name = "member_pwd")
        private String memberPwd;

        @Column(name = "nickName")
        private String nickName;

        @Column(name = "phone")
        private String phone;

        public Member() {
        }

        public Member(int memberNo, String memberId, String memberPwd, String nickName, String phone) {
            this.memberNo = memberNo;
            this.memberId = memberId;
            this.memberPwd = memberPwd;
            this.nickName = nickName;
            this.phone = phone;
        }

        public int getMemberNo() {
            return memberNo;
        }

        public void setMemberNo(int memberNo) {
            this.memberNo = memberNo;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getMemberPwd() {
            return memberPwd;
        }

        public void setMemberPwd(String memberPwd) {
            this.memberPwd = memberPwd;
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

        @Override
        public String toString() {
            return "Member{" +
                    "memberNo=" + memberNo +
                    ", memberId='" + memberId + '\'' +
                    ", memberPwd='" + memberPwd + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
}

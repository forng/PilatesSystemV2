package com.pilates.model;

import java.time.LocalDateTime;

public class Member {
	 private int id;
	    private String memberNo;
	    private String name;
	    private String phone;
	    private String email;
	    private String status;
	    private int pointBalance;
	    private LocalDateTime createdAt;
	    private String password;
		public Member() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Member(String memberNo, String name, String phone, String email, String password) {
			super();
			this.memberNo = memberNo;
			this.name = name;
			this.phone = phone;
			this.email = email;
			this.status = "ACTIVE"; //新會員一定是ACTIVE
			this.pointBalance = 0;
			this.password = password;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getMemberNo() {
			return memberNo;
		}
		public void setMemberNo(String memberNo) {
			this.memberNo = memberNo;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public int getPointBalance() {
			return pointBalance;
		}
		public void setPointBalance(int pointBalance) {
			this.pointBalance = pointBalance;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

}

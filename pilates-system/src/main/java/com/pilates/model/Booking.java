package com.pilates.model;

import java.time.LocalDateTime;

public class Booking {
	private int id;
    private int memberId;
    private int scheduleId;
    private String status;
    private LocalDateTime bookedAt;
	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}
	 public Booking(int id, int memberId, int scheduleId,
             String status, LocalDateTime bookedAt) {
		 this.id = id;
		 this.memberId = memberId;
		 this.scheduleId = scheduleId;
		 this.status = status;
		 this.bookedAt = bookedAt;
	 }
	 public int getId() {
		 return id;
	 }
	 public void setId(int id) {
		 this.id = id;
	 }
	 public int getMemberId() {
		 return memberId;
	 }
	 public void setMemberId(int memberId) {
		 this.memberId = memberId;
	 }
	 public int getScheduleId() {
		 return scheduleId;
	 }
	 public void setScheduleId(int scheduleId) {
		 this.scheduleId = scheduleId;
	 }
	 public String getStatus() {
		 return status;
	 }
	 public void setStatus(String status) {
		 this.status = status;
	 }
	 public LocalDateTime getBookedAt() {
		 return bookedAt;
	 }
	 public void setBookedAt(LocalDateTime bookedAt) {
		 this.bookedAt = bookedAt;
	 }
}

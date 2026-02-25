package com.pilates.model;

import java.time.LocalDateTime;

public class BookingView {
	 private String templateName;
	    private String coachName;
	    private LocalDateTime startTime;
	    private int pointCost;
	    private LocalDateTime bookingTime;
	    private int bookingId;

	    public int getBookingId() {
			return bookingId;
		}

		public void setBookingId(int bookingId) {
			this.bookingId = bookingId;
		}

		public String getTemplateName() {
	        return templateName;
	    }

	    public void setTemplateName(String templateName) {
	        this.templateName = templateName;
	    }

	    public String getCoachName() {
	        return coachName;
	    }

	    public void setCoachName(String coachName) {
	        this.coachName = coachName;
	    }

	    public LocalDateTime getStartTime() {
	        return startTime;
	    }

	    public void setStartTime(LocalDateTime startTime) {
	        this.startTime = startTime;
	    }

	    public int getPointCost() {
	        return pointCost;
	    }

	    public void setPointCost(int pointCost) {
	        this.pointCost = pointCost;
	    }

	    public LocalDateTime getBookingTime() {
	        return bookingTime;
	    }

	    public void setBookingTime(LocalDateTime bookingTime) {
	        this.bookingTime = bookingTime;
	    }
}

package com.pilates.model;

import java.time.LocalDateTime;

public class ClassSchedule {
	 private int id;
	    private int templateId;
	    private int coachId;
	    private LocalDateTime startTime;
	    private LocalDateTime endTime;
	    private int maxCapacity;
	    private int currentCapacity;
	    private int pointCost;
	    private int cancelDeadlineHours;
	    private String templateName; // 課程名稱
	    private boolean booked;
	    public boolean isBooked() {
			return booked;
		}

		public void setBooked(boolean booked) {
			this.booked = booked;
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

		private String coachName;    // 教練名稱
	    
	    public ClassSchedule() {
	    }

	    public ClassSchedule(int id, int templateId, int coachId,
	                         LocalDateTime startTime, LocalDateTime endTime,
	                         int maxCapacity, int currentCapacity,
	                         int pointCost, int cancelDeadlineHours) {

	        this.id = id;
	        this.templateId = templateId;
	        this.coachId = coachId;
	        this.startTime = startTime;
	        this.endTime = endTime;
	        this.maxCapacity = maxCapacity;
	        this.currentCapacity = currentCapacity;
	        this.pointCost = pointCost;
	        this.cancelDeadlineHours = cancelDeadlineHours;
	    }

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getTemplateId() {
			return templateId;
		}

		public void setTemplateId(int templateId) {
			this.templateId = templateId;
		}

		public int getCoachId() {
			return coachId;
		}

		public void setCoachId(int coachId) {
			this.coachId = coachId;
		}

		public LocalDateTime getStartTime() {
			return startTime;
		}

		public void setStartTime(LocalDateTime startTime) {
			this.startTime = startTime;
		}

		public LocalDateTime getEndTime() {
			return endTime;
		}

		public void setEndTime(LocalDateTime endTime) {
			this.endTime = endTime;
		}

		public int getMaxCapacity() {
			return maxCapacity;
		}

		public void setMaxCapacity(int maxCapacity) {
			this.maxCapacity = maxCapacity;
		}

		public int getCurrentCapacity() {
			return currentCapacity;
		}

		public void setCurrentCapacity(int currentCapacity) {
			this.currentCapacity = currentCapacity;
		}

		public int getPointCost() {
			return pointCost;
		}

		public void setPointCost(int pointCost) {
			this.pointCost = pointCost;
		}

		public int getCancelDeadlineHours() {
			return cancelDeadlineHours;
		}

		public void setCancelDeadlineHours(int cancelDeadlineHours) {
			this.cancelDeadlineHours = cancelDeadlineHours;
		}

}

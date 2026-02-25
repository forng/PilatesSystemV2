package com.pilates.model;
import java.time.LocalDateTime;
public class AdminScheduleView {
	 private String templateName;
	    private String coachName;
	    private LocalDateTime startTime;
	    private int currentCapacity;
	    private int maxCapacity;
	    private String status;

	    public String getTemplateName() { return templateName; }
	    public void setTemplateName(String templateName) { this.templateName = templateName; }

	    public String getCoachName() { return coachName; }
	    public void setCoachName(String coachName) { this.coachName = coachName; }

	    public LocalDateTime getStartTime() { return startTime; }
	    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

	    public int getCurrentCapacity() { return currentCapacity; }
	    public void setCurrentCapacity(int currentCapacity) { this.currentCapacity = currentCapacity; }

	    public int getMaxCapacity() { return maxCapacity; }
	    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

	    public String getStatus() { return status; }
	    public void setStatus(String status) { this.status = status; }
	}
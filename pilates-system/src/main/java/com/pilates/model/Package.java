package com.pilates.model;

public class Package {

    private int id;
    private String name;
    private int totalPoints;
    private int validDays;
    private int price;
	public Package() {
		super();
		// TODO Auto-generated constructor stub
	}
	 public Package(int id, String name, int totalPoints, int validDays, int price) {
	        this.id = id;
	        this.name = name;
	        this.totalPoints = totalPoints;
	        this.validDays = validDays;
	        this.price = price;
	}
	 public int getId() {
		 return id;
	 }
	 public void setId(int id) {
		 this.id = id;
	 }
	 public String getName() {
		 return name;
	 }
	 public void setName(String name) {
		 this.name = name;
	 }
	 public int getTotalPoints() {
		 return totalPoints;
	 }
	 public void setTotalPoints(int totalPoints) {
		 this.totalPoints = totalPoints;
	 }
	 public int getValidDays() {
		 return validDays;
	 }
	 public void setValidDays(int validDays) {
		 this.validDays = validDays;
	 }
	 public int getPrice() {
		 return price;
	 }
	 public void setPrice(int price) {
		 this.price = price;
	 }
	 @Override
	 public String toString() {
	     return name + " (" + totalPoints + "點 / $" + price + ")";
	 }

   
}
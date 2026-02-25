package com.pilates.model;

public class RankingView {
	 private String name;
	    private int total;

	    public RankingView() {}

	    public RankingView(String name, int total) {
	        this.name = name;
	        this.total = total;
	    }

	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public int getTotal() { return total; }
	    public void setTotal(int total) { this.total = total; }
	}
package com.example.demo.model.enums;

public enum Rating {
	
	LOW("Disastisifed",1), DECENT("Satisfied",5), HIGH("Highly Satisfied",10);

	private String satisfaction;
	private int satisfactionRate;
	
	public String getSatisfaction() {
		return satisfaction;
	}


	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}


	public int getSatisfactionRate() {
		return satisfactionRate;
	}


	public void setSatisfactionRate(int satisfactionRate) {
		this.satisfactionRate = satisfactionRate;
	}


	Rating(String satisfaction, int satisfactionRate) {
		this.satisfaction = satisfaction;
		this.satisfactionRate = satisfactionRate;
	}
	
}

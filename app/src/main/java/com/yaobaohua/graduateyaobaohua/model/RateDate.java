package com.yaobaohua.graduateyaobaohua.model;

public class RateDate {

	private float rate;
	private String dateString;

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public RateDate(float rate, String dateString) {
		super();
		this.rate = rate;
		this.dateString = dateString;
	}

	@Override
	public String toString() {
		return "RateDate [rate=" + rate + ", dateString="
				+ dateString + "]";
	}
	

}

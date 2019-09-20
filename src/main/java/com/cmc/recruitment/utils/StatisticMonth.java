package com.cmc.recruitment.utils;

public class StatisticMonth {
	private int month;
	private int numberOfNew;
	private int numberOfTT;
	
	
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getNumberOfNew() {
		return numberOfNew;
	}
	public void setNumberOfNew(int numberOfNew) {
		this.numberOfNew = numberOfNew;
	}
	public int getNumberOfTT() {
		return numberOfTT;
	}
	public void setNumberOfTT(int numberOfTT) {
		this.numberOfTT = numberOfTT;
	}
	/**
	 * @param month
	 * @param numberOfNew
	 * @param numberOfTT
	 */
	public StatisticMonth(int month, int numberOfNew, int numberOfTT) {
		super();
		this.month = month;
		this.numberOfNew = numberOfNew;
		this.numberOfTT = numberOfTT;
	}
	/**
	 * 
	 */
	public StatisticMonth() {
		super();
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}

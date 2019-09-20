package com.cmc.recruitment.utils;

import java.util.List;

public class ReportByGroup {
	private String group;
	private String department;
	private List<StatisticMonth> statisticMonth;
	private Long total;
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public List<StatisticMonth> getStatisticMonth() {
		return statisticMonth;
	}
	public void setStatisticMonth(List<StatisticMonth> statisticMonth) {
		this.statisticMonth = statisticMonth;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal() {
		Long total=0L;
		for(StatisticMonth item : statisticMonth) {
			total += item.getNumberOfNew() + item.getNumberOfTT();
		}
		this.total = total;
	}
	/**
	 * @param group
	 * @param department
	 * @param statisticMonth
	 * @param total
	 */
	public ReportByGroup(String group, String department, List<StatisticMonth> statisticMonth, Long total) {
		super();
		this.group = group;
		this.department = department;
		this.statisticMonth = statisticMonth;
		this.total = total;
	}
	/**
	 * 
	 */
	public ReportByGroup() {
		super();
	}
	
}

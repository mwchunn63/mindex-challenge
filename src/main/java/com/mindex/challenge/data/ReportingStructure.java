package com.mindex.challenge.data;

public class ReportingStructure {
	
    private String employeeId;
    private int numberOfReports;
    
    public ReportingStructure() {
    }
    
    public ReportingStructure(String emp, int nbr) {
    	this.employeeId = emp;
    	this.numberOfReports = nbr;
    }
    
	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * @return the numberOfReports
	 */
	public int getNumberOfReports() {
		return numberOfReports;
	}
	/**
	 * @param numberOfReports the numberOfReports to set
	 */
	public void setNumberOfReports(int numberOfReports) {
		this.numberOfReports = numberOfReports;
	}
	
	@Override
	public String toString() {
		return "ReportingStructure [employeeId=" + employeeId + ", numberOfReports=" + numberOfReports + "]";
	}

}

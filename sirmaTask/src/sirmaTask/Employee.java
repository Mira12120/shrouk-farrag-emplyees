package sirmaTask;

import java.util.Date;

public class Employee {
	private int empID;
	private int projectID;
	private Date dateFrom;
	private Date dateTo;
	private long dyasWorked;
	
	public Employee() {

	}
	public Employee(int empID, int projectID, Date dateFrom, Date dateTo ) {
		super();
		this.empID = empID;
		this.projectID = projectID;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	public int getEmpID() {
		return empID;
	}
	public void setEmpID(int empID) {
		this.empID = empID;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public long getDyasWorked() {
		return dyasWorked;
	}
	public void setDyasWorked(long dyasWorked) {
		this.dyasWorked = dyasWorked;
	}
	
	
	

}

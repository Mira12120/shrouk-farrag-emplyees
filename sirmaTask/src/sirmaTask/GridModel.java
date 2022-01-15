package sirmaTask;

public class GridModel {
	
	private String empolyeeIDOne;
	private String employeeIDTwo;
	private String projectID;
	private String workedDays;
	
	
	
	public GridModel() {
	
		// TODO Auto-generated constructor stub
	}
	
	
	public GridModel(String empolyeeIDOne, String employeeIDTwo, String projectID, String workedDays) {
		super();
		this.empolyeeIDOne = empolyeeIDOne;
		this.employeeIDTwo = employeeIDTwo;
		this.projectID = projectID;
		this.workedDays = workedDays;
	}


	public String getEmpolyeeIDOne() {
		return empolyeeIDOne;
	}
	public void setEmpolyeeIDOne(String empolyeeIDOne) {
		this.empolyeeIDOne = empolyeeIDOne;
	}
	public String getEmployeeIDTwo() {
		return employeeIDTwo;
	}
	public void setEmployeeIDTwo(String employeeIDTwo) {
		this.employeeIDTwo = employeeIDTwo;
	}
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getWorkedDays() {
		return workedDays;
	}
	public void setWorkedDays(String workedDays) {
		this.workedDays = workedDays;
	}
	
	

}

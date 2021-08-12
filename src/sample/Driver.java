package sample;

public class Driver {
	private String driverId;
	private String fullname;
	
	public Driver() {
		// TODO Auto-generated constructor stub
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Driver(String driverId, String fullname) {
		super();
		this.driverId = driverId;
		this.fullname = fullname;
	}
	
	
}

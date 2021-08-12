package sample;

public class Passenger {
	private String passengerId;
	private String fullname;
	
	public Passenger() {
		// TODO Auto-generated constructor stub
	}

	public Passenger(String passengerId, String fullname) {
		super();
		this.passengerId = passengerId;
		this.fullname = fullname;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	
}

package model;

public class Guest {
	
	private int gId;
	private int rId;
	private String name;
	private String address;
	private String email;
	private boolean paymentReceived;
	private String arrivalInfo;
	
	public Guest(int gId, int rId, String name, String addr, String email, boolean payment, String arrivalInfo){
		this.gId = gId;
		this.name = name;
		this.address = addr;
		this.email = email;
		this.paymentReceived = payment;
		this.arrivalInfo = arrivalInfo;
	}

	public int getgId() {
		return gId;
	}

	public void setgId(int gId) {
		this.gId = gId;
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPaymentReceived() {
		return paymentReceived;
	}

	public void setPaymentReceived(boolean paymentReceived) {
		this.paymentReceived = paymentReceived;
	}

	public String getArrivalInfo() {
		return arrivalInfo;
	}

	public void setArrivalInfo(String arrivalInfo) {
		this.arrivalInfo = arrivalInfo;
	}

}

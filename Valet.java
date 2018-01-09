//package project;

public class Valet 
{
	private String car;
	private int gID;
	private boolean requested;
	private int parkingNum;
	
	public Valet(String car, int gID, boolean requested, int parkingNum)
	{
		this.car = car;
		this.gID = gID;
		this.requested = requested;
		this.parkingNum = parkingNum;
	}
	
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	public int getgID() {
		return gID;
	}
	public void setgID(int gID) {
		this.gID = gID;
	}
	public boolean isRequested() {
		return requested;
	}
	public void setRequested(boolean requested) {
		this.requested = requested;
	}
	public int getParkingNum() {
		return parkingNum;
	}
	public void setParkingNum(int parkingNum) {
		this.parkingNum = parkingNum;
	}
	
	

}

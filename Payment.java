//package project;


public class Payment {
	private int pId;
	private int reservationNum;
	private int gId;
	private String name;
	private String cardNumber;
	private String expiration;
	private int cvc;
	private double amountDue;


	public Payment(int pId, int reservationNum, int gId, String name, String cardNumber, String expiration, int cvc, double amountDue){
		this.pId = pId;
		this.reservationNum = reservationNum;
		this.gId = gId;
		this.name =name;
		this.cardNumber = cardNumber;
		this.expiration = expiration;
		this.cvc = cvc;
		this.amountDue = amountDue;
	}
			
	public int getpId() {
		return pId;
	}


	public void setpId(int pId) {
		this.pId = pId;
	}


	public int getReservationNum() {
		return reservationNum;
	}


	public void setReservationNum(int reservationNum) {
		this.reservationNum = reservationNum;
	}


	public int getgId() {
		return gId;
	}


	public void setgId(int gId) {
		this.gId = gId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCardNumber() {
		return cardNumber;
	}


	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}


	public String getExpiration() {
		return expiration;
	}


	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}


	public int getCvc() {
		return cvc;
	}


	public void setCvc(int cvc) {
		this.cvc = cvc;
	}


	public double getAmountDue() {
		return amountDue;
	}


	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
	}


	

}

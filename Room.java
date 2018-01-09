//package project;

public class Room {

			
	private int rId;
	private int roomNum;
	private String type;
	private double price;
	private boolean clean;
	private boolean smoke;
	
	public Room(int rId, int roomNum, String type, double price, boolean clean, boolean smoke){
		this.rId = rId;
		this.roomNum = roomNum;
		this.type = type;
		this.price = price;
		this.clean = clean;
		this.smoke = smoke;
	}
	
	public Room(int rId)
	{
		this.rId = rId;
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isClean() {
		return clean;
	}

	public void setClean(boolean clean) {
		this.clean = clean;
	}

	public boolean isSmoke() {
		return smoke;
	}

	public void setSmoke(boolean smoke) {
		this.smoke = smoke;
	}

}

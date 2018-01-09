//package project;

public class WakeUpCall 
{
	private int gID;
	private String time;
	
	public WakeUpCall(int gID, String time)
	{
		this.gID = gID;
		this.time = time;
	}

	public int getgID() {
		return gID;
	}

	public void setgID(int gID) {
		this.gID = gID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}

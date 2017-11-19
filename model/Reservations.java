package model;
import java.util.*;
import java.io.*;


public class Reservations {
	private int reservationNumber;
	private int gId; //guest id
	private int rId; //room id
	private int roomNumber;
	private String name;
	private int partyCount;
	private Date checkIn;
	private Date checkOut;
	private String comment;
	private Date updatedAt;
	
	public Reservations(int rNum, int gId, int rId, int roomNumber, String name, int partyCount, Date checkIn, Date checkOut, String comment, Date updatedAt){
		this.reservationNumber = rNum;
		this.gId = gId;
		this.rId = rId;
		this.roomNumber = roomNumber;
		this.name = name;
		this.partyCount = partyCount;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.comment = comment;			
		this.updatedAt = updatedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(int reservationNumber) {
		this.reservationNumber = reservationNumber;
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

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPartyCount() {
		return partyCount;
	}

	public void setPartyCount(int partyCount) {
		this.partyCount = partyCount;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	

	
	
}

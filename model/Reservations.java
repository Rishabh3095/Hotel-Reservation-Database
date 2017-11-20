package model;
import java.util.*;
import java.io.*;

import java.sql.Date;

public class Reservations {
	private int reservationNumber;
	private int gId; //guest id
	private int roomNumber;
	private String name;
	private int partyCount;
	private Date checkIn;
	private Date checkOut;
	private boolean checkedIn;
	private boolean checkedOut;
	private boolean payment;
	private String comment;
	private Date updatedAt;
	
	public Reservations(int rNum, int gId, int roomNumber, String name, int partyCount, Date checkIn, Date checkOut, boolean checkedIn, boolean checkedOut, boolean payment, Date updatedAt){
		this.reservationNumber = rNum;
		this.gId = gId;
		this.roomNumber = roomNumber;
		this.name = name;
		this.partyCount = partyCount;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.checkedIn = checkedIn;
		this.checkedOut = checkedOut;
		this.payment = payment;
		this.updatedAt = updatedAt;
	}

	public boolean isCheckedIn() {
		return checkedIn;
	}

	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	public boolean isCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	public boolean isPayment() {
		return payment;
	}

	public void setPayment(boolean payment) {
		this.payment = payment;
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

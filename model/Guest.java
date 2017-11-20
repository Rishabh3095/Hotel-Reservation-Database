package model;

import java.sql.Date;

public class Guest {

  private int gId;
  private int rId;
  private String name;
  private String address;
  private String email;
  private Date checkIn;
  private Date checkOut;

  public Guest(
      int gId, int rId, String name, String addr, String email, Date checkIn, Date checkOut) {
    this.gId = gId;
    this.rId = rId;
    this.name = name;
    this.address = addr;
    this.email = email;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
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
}

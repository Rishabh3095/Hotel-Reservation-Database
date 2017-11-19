SET sql_mode='';

DROP SCHEMA IF EXISTS Hotel;
CREATE SCHEMA Hotel;
USE Hotel; 

DROP TABLE IF EXISTS EMPLOYEE;
CREATE TABLE EMPLOYEE
(eID int NOT NULL AUTO_INCREMENT,
Name VARCHAR(40),
Admin BOOLEAN ,
Password CHAR(20),
onDuty BOOLEAN ,
PRIMARY KEY (eID));


DROP TABLE IF EXISTS ROOM;

CREATE TABLE ROOM
(rID int NOT NULL AUTO_INCREMENT,
roomNum int UNIQUE,
Type VARCHAR(20),
Price float,
Cleaned BOOLEAN ,
Smoke BOOLEAN ,
PRIMARY KEY (rID)
);


DROP TABLE IF EXISTS GUEST;

CREATE TABLE GUEST
(gID int NOT NULL AUTO_INCREMENT,
rid int,
NAME VARCHAR(40),
Address VARCHAR(80),
Email VARCHAR(30),
checkIn datetime,
checkOut datetime,
FOREIGN KEY (rid) REFERENCES Room(rID) on delete cascade,
PRIMARY KEY (gID)
);


DROP TABLE IF EXISTS Reservations;
CREATE TABLE Reservations
(RNum int NOT NULL AUTO_INCREMENT,
gID int,
roomNum int,
NAME VARCHAR(40),
PartyCount int,
checkIn datetime,
checkOut datetime,
checkedIn BOOLEAN,
checkedOut BOOLEAN ,
paymentReceived BOOLEAN DEFAULT FALSE,
updatedAt timestamp not null on update current_timestamp,
FOREIGN KEY (roomNum) REFERENCES Room(roomNum) on delete cascade,
FOREIGN KEY (gID) REFERENCES Guest(gId) on delete cascade,
PRIMARY KEY (RNum,gId,roomNum)
);


DROP TABLE IF EXISTS Payment;

CREATE TABLE Payment
(pID int NOT NULL AUTO_INCREMENT,
RNum int,
gId int,
NAME VARCHAR(40),
CardNumber VARCHAR(20),
Expiration VARCHAR(5),
CVC int,
AmountDue Float,
FOREIGN KEY (gId) REFERENCES Guest(gId) on delete cascade,
FOREIGN KEY (RNum) REFERENCES Reservations(RNum) on delete cascade,
PRIMARY KEY (pID, gId, RNum)
);

#update payment status in Reservations table after insert in payment table
CREATE Trigger updatePaymentStatus
After Insert on Payment
for each row 
update Reservations set paymentReceived = TRUE where new.gid = gid;



DROP TABLE IF EXISTS Archive;

CREATE TABLE Archive
(RNum int NOT NULL,
gID int,
rid int,
roomNum int,
NAME VARCHAR(40),
PartyCount int,
CheckIN datetime,
CheckOut datetime,
Comment VARCHAR(40),
FOREIGN KEY (rid) REFERENCES Room(rID) on delete cascade,
FOREIGN KEY (gID) REFERENCES Guest(gId)on delete cascade,
PRIMARY KEY (RNum,gId,roomNum)
);


DROP TABLE IF EXISTS WakeupCall;

CREATE TABLE WakeupCall
(gID INT,
Time varchar(5),
FOREIGN KEY (gId) REFERENCES Guest(gId) on delete cascade,
PRIMARY KEY(gID)
);


DROP TABLE IF EXISTS Valet;

CREATE TABLE VALET
(gID INT,
Car CHAR(15),
Requested boolean ,
parkingNum INT AUTO_INCREMENT,
FOREIGN KEY (gId) REFERENCES Guest(gId) on delete cascade,
PRIMARY KEY(parkingNum, gID)
);

LOAD DATA LOCAL INFILE '/Users/gurpreet/Documents/workspace/HotelReservationDb/src/data/employees.txt' INTO TABLE EMPLOYEE;
LOAD DATA LOCAL INFILE '/Users/gurpreet/Documents/workspace/HotelReservationDb/src/data/room.txt' INTO TABLE ROOM;


SET sql_mode='';

DROP SCHEMA IF EXISTS Hotel;
CREATE SCHEMA Hotel;
USE Hotel; 

DROP TABLE IF EXISTS EMPLOYEE;
CREATE TABLE EMPLOYEE
(eID int NOT NULL AUTO_INCREMENT,
Name VARCHAR(40),
Admin BOOLEAN,
Password CHAR(20),
onDuty BOOLEAN,
PRIMARY KEY (eID));


DROP TABLE IF EXISTS ROOM;
CREATE TABLE ROOM
(rID int NOT NULL AUTO_INCREMENT,
RoomNum int,
Type VARCHAR(20),
Price float,
Cleaned BOOLEAN,
View VARCHAR(20),
Smoke BOOLEAN,
PRIMARY KEY (rID)
);


DROP TABLE IF EXISTS GUEST;

CREATE TABLE GUEST
(gID int NOT NULL AUTO_INCREMENT,
rid int,
NAME VARCHAR(40),
Address VARCHAR(80),
Email VARCHAR(30),
PaymentReceived BOOLEAN,
ArrivalInfo VARCHAR(40),
FOREIGN KEY (rid) REFERENCES Room(rID) on delete cascade,
PRIMARY KEY (gID)
);



DROP TABLE IF EXISTS Reservations;

CREATE TABLE Reservations
(RNum int NOT NULL AUTO_INCREMENT,
gID int,
rid int,
roomNum int,
NAME VARCHAR(40),
PartyCount int,
CheckIN datetime,
CheckOut datetime,
Comment VARCHAR(40),
updatedAt datetime,
FOREIGN KEY (rid) REFERENCES Room(rID) on delete cascade,
FOREIGN KEY (gID) REFERENCES Guest(gId)on delete cascade,
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
PRIMARY KEY (pID, RNum)
);

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
(
	gID INT,
	Time varchar(5),
	FOREIGN KEY (gId) REFERENCES Guest(gId) on delete cascade,
	PRIMARY KEY(gID)
);


DROP TABLE IF EXISTS Valet;

CREATE TABLE VALET
(
gID INT,
Car CHAR(15),
Requested boolean,
parkingNum INT AUTO_INCREMENT,
FOREIGN KEY (gId) REFERENCES Guest(gId) on delete cascade,
PRIMARY KEY(parkingNum, gID)
);


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


DROP TABLE IF EXISTS UNROOM;

CREATE TABLE UNROOM
(rID int NOT NULL AUTO_INCREMENT,
PRIMARY KEY (rID)
);


DROP TABLE IF EXISTS GUEST;

CREATE TABLE GUEST
(gID int NOT NULL AUTO_INCREMENT,
rid int,
NAME VARCHAR(40),
Address VARCHAR(80),
Email VARCHAR(30),
checkIn Date,
checkOut Date,
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
checkIn Date,
checkOut Date ,
checkedIn BOOLEAN,
checkedOut BOOLEAN ,
paymentReceived BOOLEAN DEFAULT FALSE,
updatedAt timestamp not null DEFAULT CURRENT_TIMESTAMP  on update current_timestamp,
FOREIGN KEY (roomNum) REFERENCES Room(roomNum) on delete cascade,
FOREIGN KEY (gID) REFERENCES Guest(gId) on delete cascade,
PRIMARY KEY (RNum,gId,roomNum)
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
updatedAt timestamp,
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
FOREIGN KEY (RNum) REFERENCES Reservations(RNum) on delete cascade,
PRIMARY KEY (pID, gId, RNum)
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


#update payment status in Reservations table after insert in payment table
CREATE Trigger updatePaymentStatus
After Insert on Payment
for each row 
update Reservations set paymentReceived = TRUE where new.gid = gid;

#delete guest from guest table on cancel booking
CREATE Trigger deleteGuest
After Delete on Reservations 
for each row
delete from Guest where old.gId = gID;

#remove payment information on cancelling a booking 
CREATE Trigger deletePayment
After Delete on Reservations 
for each row
delete from Payment where old.gId = gId;

#Procedure to archive data from Reservations table
DROP PROCEDURE IF EXISTS archiveReservations;

DELIMITER //
CREATE PROCEDURE archiveReservations(IN lastUpdated timestamp) 
BEGIN 
       INSERT INTO Archive (RNum,gID,rid,roomNum,NAME,PartyCount,CheckIN,CheckOut,updatedAt)
       Select RNum,gID,rid,roomNum,NAME,PartyCount,CheckIN,CheckOut,updatedAt from Reservations where updatedAt <= lastUpdated;
       Delete from Reservations where updatedAt <= lastUpdated;
END//



LOAD DATA LOCAL INFILE 'C:\\My Stuff\\Software Projects\\Java\\javaSpace\\HotelReservationDB\\src\\data\\employees.txt' INTO TABLE EMPLOYEE;
LOAD DATA LOCAL INFILE 'C:\\My Stuff\\Software Projects\\Java\\javaSpace\\HotelReservationDB\\src\\data\\room.txt' INTO TABLE ROOM;


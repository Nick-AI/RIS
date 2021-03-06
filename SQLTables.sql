###### SQL Database
# NAME: RISystem
#TABLES: 
#  - Login
#  - patientInfo
#  - billing
#  - PACS


create table if not exists login(
 username varchar(10),
 name text NOT NULL,
 level int,
 password varchar(10));
 
# level defines access

 Insert into table login
 
 create table if not exists patientInfo(
 fname varchar(20),
 lname varchar(25),
 ID int,
 DOB date,
 gender varchar(3)
 );
 
 create table if not exists PACS(
 ID int,
 imageDate date,
 image varBinary(Max));
 
 create table if not exists billing(
  ID int PRIMARY KEY,
  totalAmount long);
  
 
 

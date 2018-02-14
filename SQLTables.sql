###### SQL Database
# NAME: RISystem
#TABLES: 
#  - Login
#  - patientInfo
#  - billing
#  - PACS


create table if not exists login(
 username varchar(10),
 password varchar(10),
 admin boolean);
 
 Insert into table login
 
 create table if not exists patientInfo(
 fname varchar(20),
 lname varchar(25),
 ID int,
 DOB date);
 
 create table if not exists PACS(
 ID int,
 imageDate date,
 image varBinary(Max));
 
 create table if not exists billing(
  ID int PRIMARY KEY,
  totalAmount long,
  dueDate date);
  
 
 

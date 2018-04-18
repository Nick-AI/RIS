-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 11, 2018 at 08:22 PM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `irsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `billing`
--

CREATE TABLE `billing` (
  `billID` int(10) NOT NULL,
  `patientID` int(10) NOT NULL,
  `procedureList` text NOT NULL,
  `inProgress` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `imageID` int(10) NOT NULL,
  `description` tinytext NOT NULL,
  `modality` varchar(255) NOT NULL,
  `dateTaken` date NOT NULL,
  `image` longblob NOT NULL,
  `reportID` int(10) NOT NULL,
  `staffID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `username` varchar(255) NOT NULL,
  `password` varchar(512) NOT NULL,
  `level` int(11) NOT NULL,
  `staffID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `patient`
--

CREATE TABLE `patient` (
  `patientID` int(10) NOT NULL,
  `fName` varchar(255) NOT NULL,
  `lName` varchar(255) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `gender` varchar(255) NOT NULL,
  `phoneNum` varchar(14) NOT NULL,
  `addressOne` varchar(255) NOT NULL,
  `addressTwo` varchar(255) NOT NULL,
  `addressCity` varchar(255) NOT NULL,
  `addressState` varchar(2) NOT NULL,
  `addressZip` varchar(5) NOT NULL,
  `refPhysicianID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `patient`
--

INSERT INTO `patient` (`patientID`, `fName`, `lName`, `dateOfBirth`, `gender`, `phoneNum`, `addressOne`, `addressTwo`, `addressCity`, `addressState`, `addressZip`, `refPhysicianID`) VALUES
(1, 'Bill', 'Doe', '1999-01-01', 'Male', '100-100-1010', '1 Street', 'Box 1', 'City', 'AL', '10100', 23445),
(1000000000, 'Jane', 'Doe', '1999-12-12', 'Female', '100-200-3000', '1 Road', 'Apartment 1', 'City', 'AL', '11111', 23445);

-- --------------------------------------------------------

--
-- Table structure for table `presentstaff`
--

CREATE TABLE `presentstaff` (
  `staffID` int(10) NOT NULL,
  `checkIN` datetime NOT NULL,
  `clockedOut` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `radorder`
--

CREATE TABLE `radorder` (
  `refPhysicianID` int(10) NOT NULL,
  `modality` tinytext NOT NULL,
  `specification` text NOT NULL,
  `patientID` int(10) NOT NULL,
  `patientFName` varchar(255) NOT NULL,
  `patientLName` varchar(255) NOT NULL,
  `orderID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `radorder`
--

INSERT INTO `radorder` (`refPhysicianID`, `modality`, `specification`, `patientID`, `patientFName`, `patientLName`, `orderID`) VALUES
(23445, 'XRAY', 'Something something shoulder something something', 1, 'Bill', 'Doe', 1);

-- --------------------------------------------------------

--
-- Table structure for table `refphysician`
--

CREATE TABLE `refphysician` (
  `lName` varchar(255) NOT NULL,
  `refPhysicianID` int(10) NOT NULL,
  `fName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `refphysician`
--

INSERT INTO `refphysician` (`lName`, `refPhysicianID`, `fName`) VALUES
('Man', 1, 'Mister'),
('Woman', 1000000000, 'Misses');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `reportID` int(10) NOT NULL,
  `reportBody` longtext NOT NULL,
  `beingModified` tinyint(1) NOT NULL,
  `lastModifiedDate` date NOT NULL,
  `lastModifiedID` int(10) NOT NULL,
  `patientID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `schedule`
--

CREATE TABLE `schedule` (
  `procType` text NOT NULL,
  `procTime` varchar(13) NOT NULL,
  `modality` tinytext NOT NULL,
  `patientID` int(10) NOT NULL,
  `procID` int(11) NOT NULL,
  `orderID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `schedule`
--

INSERT INTO `schedule` (`procType`, `procTime`, `modality`, `patientID`, `procID`, `orderID`) VALUES
('blabla shoulder blabla', '04/04/2011-12', 'XRAY', 1, 1, 1),
('bla bla ankle', '05/05/2020-12', 'MRI', 1000000000, 2, 1),
('blabla ribs', '10/10/2019', 'MRI', 1000000000, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staffID` int(10) NOT NULL,
  `staffType` varchar(255) NOT NULL,
  `salary` decimal(10,0) NOT NULL,
  `fName` varchar(255) NOT NULL,
  `lName` varchar(255) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `SSN` int(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staffID`, `staffType`, `salary`, `fName`, `lName`, `dateOfBirth`, `SSN`) VALUES
(23445, 'Referring Physician', '0', 'Ref', 'Phys', '0000-00-00', 0),
(300000000, 'Technician', '50', 'Tech', 'McTechface', '1987-06-05', 111111111),
(400000000, 'Reception', '30', 'Desk', 'Front', '1991-01-09', 999999999),
(1000000000, 'Nurse', '35', 'Nurse', 'McNurseface', '1900-01-01', 123121234),
(2000000000, 'Radiologist', '50', 'Rad', 'Iologist', '1919-01-09', 321214321);

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `nextStaffID` int(10) NOT NULL,
  `currentlyWaiting` tinyint(1) NOT NULL,
  `waitingSince` time NOT NULL,
  `patientID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `vitals`
--

CREATE TABLE `vitals` (
  `timeTaken` datetime NOT NULL,
  `pulseRate` tinytext NOT NULL,
  `bloodPressure` tinytext NOT NULL,
  `byStaffID` int(10) NOT NULL,
  `patientID` int(10) NOT NULL,
  `preExConditions` text NOT NULL,
  `vitalsID` int(10) NOT NULL,
  `reportID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `billing`
--
ALTER TABLE `billing`
  ADD PRIMARY KEY (`billID`),
  ADD KEY `patientID` (`patientID`),
  ADD KEY `billID` (`billID`);

--
-- Indexes for table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`imageID`),
  ADD UNIQUE KEY `imageID` (`imageID`),
  ADD KEY `imageID_2` (`imageID`),
  ADD KEY `reportID` (`reportID`),
  ADD KEY `staffID` (`staffID`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`username`),
  ADD KEY `staffID` (`staffID`);

--
-- Indexes for table `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`patientID`),
  ADD UNIQUE KEY `patientID` (`patientID`),
  ADD KEY `patientID_2` (`patientID`),
  ADD KEY `refPhysicianID` (`refPhysicianID`);

--
-- Indexes for table `presentstaff`
--
ALTER TABLE `presentstaff`
  ADD PRIMARY KEY (`staffID`),
  ADD KEY `checkIN` (`checkIN`);

--
-- Indexes for table `radorder`
--
ALTER TABLE `radorder`
  ADD PRIMARY KEY (`orderID`),
  ADD KEY `patientID` (`patientID`),
  ADD KEY `refPhysicianID` (`refPhysicianID`);

--
-- Indexes for table `refphysician`
--
ALTER TABLE `refphysician`
  ADD PRIMARY KEY (`refPhysicianID`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`reportID`),
  ADD UNIQUE KEY `reportID` (`reportID`),
  ADD KEY `lastModifiedID` (`lastModifiedID`),
  ADD KEY `fileID` (`patientID`),
  ADD KEY `reportID_2` (`reportID`),
  ADD KEY `lastModifiedID_2` (`lastModifiedID`),
  ADD KEY `fileID_2` (`patientID`);

--
-- Indexes for table `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`procID`),
  ADD KEY `patientID` (`patientID`),
  ADD KEY `orderID` (`orderID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staffID`),
  ADD UNIQUE KEY `staffID` (`staffID`),
  ADD KEY `staffID_2` (`staffID`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`patientID`),
  ADD UNIQUE KEY `patientID` (`patientID`),
  ADD KEY `nextStaffID` (`nextStaffID`),
  ADD KEY `patientID_2` (`patientID`);

--
-- Indexes for table `vitals`
--
ALTER TABLE `vitals`
  ADD PRIMARY KEY (`vitalsID`),
  ADD KEY `vitalsID` (`vitalsID`),
  ADD KEY `byStaffID` (`byStaffID`),
  ADD KEY `patientID` (`patientID`),
  ADD KEY `reportID` (`reportID`),
  ADD KEY `vitalsID_2` (`vitalsID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `billing`
--
ALTER TABLE `billing`
  ADD CONSTRAINT `billing_ibfk_1` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON UPDATE CASCADE;

--
-- Constraints for table `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `images_ibfk_1` FOREIGN KEY (`reportID`) REFERENCES `report` (`reportID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `images_ibfk_2` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`) ON UPDATE CASCADE;

--
-- Constraints for table `login`
--
ALTER TABLE `login`
  ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`) ON UPDATE CASCADE;

--
-- Constraints for table `patient`
--
ALTER TABLE `patient`
  ADD CONSTRAINT `patient_ibfk_1` FOREIGN KEY (`refPhysicianID`) REFERENCES `staff` (`staffID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `presentstaff`
--
ALTER TABLE `presentstaff`
  ADD CONSTRAINT `presentstaff_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`) ON UPDATE CASCADE;

--
-- Constraints for table `radorder`
--
ALTER TABLE `radorder`
  ADD CONSTRAINT `radorder_ibfk_1` FOREIGN KEY (`refPhysicianID`) REFERENCES `staff` (`staffID`) ON UPDATE CASCADE;

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_ibfk_2` FOREIGN KEY (`lastModifiedID`) REFERENCES `staff` (`staffID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `report_ibfk_3` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON UPDATE CASCADE;

--
-- Constraints for table `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`orderID`) REFERENCES `radorder` (`orderID`) ON UPDATE CASCADE;

--
-- Constraints for table `status`
--
ALTER TABLE `status`
  ADD CONSTRAINT `status_ibfk_1` FOREIGN KEY (`nextStaffID`) REFERENCES `staff` (`staffID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `status_ibfk_2` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON UPDATE CASCADE;

--
-- Constraints for table `vitals`
--
ALTER TABLE `vitals`
  ADD CONSTRAINT `vitals_ibfk_1` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `vitals_ibfk_2` FOREIGN KEY (`byStaffID`) REFERENCES `staff` (`staffID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `vitals_ibfk_3` FOREIGN KEY (`reportID`) REFERENCES `report` (`reportID`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

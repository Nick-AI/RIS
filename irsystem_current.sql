-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 25. Apr 2018 um 04:37
-- Server-Version: 10.1.30-MariaDB
-- PHP-Version: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `irsystem`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `billing`
--

CREATE TABLE `billing` (
  `billID` int(10) NOT NULL,
  `patientID` int(10) NOT NULL,
  `procedureList` text NOT NULL,
  `inProgress` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `images`
--

CREATE TABLE `images` (
  `imageID` int(10) NOT NULL,
  `description` tinytext NOT NULL,
  `modality` varchar(255) NOT NULL,
  `dateTaken` date NOT NULL,
  `image` varchar(255) NOT NULL,
  `reportID` int(10) NOT NULL,
  `staffID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `images`
--

INSERT INTO `images` (`imageID`, `description`, `modality`, `dateTaken`, `image`, `reportID`, `staffID`) VALUES
(2, 'Spoopy head things', 'MRI', '2018-04-10', 'C:\\Users\\yanni\\Desktop\\RIS-master\\ImageDB\\MRI_Head.jpg', 1, 300000000);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `login`
--

CREATE TABLE `login` (
  `username` varchar(255) NOT NULL,
  `password` varchar(512) NOT NULL,
  `level` int(11) NOT NULL,
  `staffID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `login`
--

INSERT INTO `login` (`username`, `password`, `level`, `staffID`) VALUES
('admin', 'admin', 1, 5555);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `patient`
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
-- Daten für Tabelle `patient`
--

INSERT INTO `patient` (`patientID`, `fName`, `lName`, `dateOfBirth`, `gender`, `phoneNum`, `addressOne`, `addressTwo`, `addressCity`, `addressState`, `addressZip`, `refPhysicianID`) VALUES
(1, 'Bob', 'Pat', '2010-10-10', 'Male', '12312312314', 'Address 1', 'Address 2', 'City', 'ST', '12345', 12345),
(1000000000, 'Jane', 'Doe', '1999-12-12', 'Female', '100-200-3000', '1 Road', 'Apartment 1', 'City', 'AL', '11111', 12345);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `presentstaff`
--

CREATE TABLE `presentstaff` (
  `staffID` int(10) NOT NULL,
  `checkIN` datetime NOT NULL,
  `clockedOut` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `presentstaff`
--

INSERT INTO `presentstaff` (`staffID`, `checkIN`, `clockedOut`) VALUES
(300000000, '2018-04-11 00:00:00', 0),
(2000000000, '2018-04-25 06:02:09', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `radorder`
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
-- Daten für Tabelle `radorder`
--

INSERT INTO `radorder` (`refPhysicianID`, `modality`, `specification`, `patientID`, `patientFName`, `patientLName`, `orderID`) VALUES
(12345, 'XRAY', 'Something something shoulder something something', 1, 'Bill', 'Doe', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `refphysician`
--

CREATE TABLE `refphysician` (
  `lName` varchar(255) NOT NULL,
  `refPhysicianID` int(10) NOT NULL,
  `fName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `refphysician`
--

INSERT INTO `refphysician` (`lName`, `refPhysicianID`, `fName`) VALUES
('Phys', 1, 'Bob'),
('Phys', 2, 'Jane'),
('Dude', 12345, 'Some');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `report`
--

CREATE TABLE `report` (
  `reportID` int(10) NOT NULL,
  `reportBody` longtext NOT NULL,
  `beingModified` tinyint(1) NOT NULL,
  `lastModifiedDate` date NOT NULL,
  `lastModifiedID` int(10) NOT NULL,
  `patientID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `report`
--

INSERT INTO `report` (`reportID`, `reportBody`, `beingModified`, `lastModifiedDate`, `lastModifiedID`, `patientID`) VALUES
(1, 'Hello.', 0, '2018-04-23', 2000000000, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `schedule`
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
-- Daten für Tabelle `schedule`
--

INSERT INTO `schedule` (`procType`, `procTime`, `modality`, `patientID`, `procID`, `orderID`) VALUES
('Hip sucks', '04/12/2018-3', 'XRAY', 1000000000, 108, 1),
('Knees', '04/12/2018-3', 'FMRI', 1000000000, 109, 1),
('Important Shit', '04/12/2018-1', 'CAT', 1, 110, 1),
('Medium Important Shit', '04/12/2018-2', 'CAT', 1, 111, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `staff`
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
-- Daten für Tabelle `staff`
--

INSERT INTO `staff` (`staffID`, `staffType`, `salary`, `fName`, `lName`, `dateOfBirth`, `SSN`) VALUES
(5555, 'Administrator', '10', 'Admin', 'Adminface', '2010-11-11', 123123322),
(12345, 'Referring Physician', '0', 'Ref', 'Phys', '0000-00-00', 0),
(300000000, 'Technician', '50', 'Tech', 'McTechface', '1987-06-05', 111111111),
(400000000, 'Reception', '30', 'Desk', 'Front', '1991-01-09', 999999999),
(1000000000, 'Nurse', '35', 'Nurse', 'McNurseface', '1900-01-01', 123121234),
(2000000000, 'Radiologist', '50', 'Rad', 'Iologist', '1919-01-09', 321214321);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `status`
--

CREATE TABLE `status` (
  `nextStaffID` int(10) NOT NULL,
  `currentlyWaiting` tinyint(1) NOT NULL,
  `waitingSince` time NOT NULL,
  `patientID` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `status`
--

INSERT INTO `status` (`nextStaffID`, `currentlyWaiting`, `waitingSince`, `patientID`) VALUES
(2000000000, 0, '19:03:09', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `vitals`
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
-- Daten für Tabelle `vitals`
--

INSERT INTO `vitals` (`timeTaken`, `pulseRate`, `bloodPressure`, `byStaffID`, `patientID`, `preExConditions`, `vitalsID`, `reportID`) VALUES
('2018-04-12 08:09:53', '100', '120/80', 1000000000, 1, 'I don\'t know, probs some allergies.', 1, 1);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `billing`
--
ALTER TABLE `billing`
  ADD PRIMARY KEY (`billID`),
  ADD KEY `patientID` (`patientID`),
  ADD KEY `billID` (`billID`);

--
-- Indizes für die Tabelle `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`imageID`),
  ADD UNIQUE KEY `imageID` (`imageID`),
  ADD KEY `imageID_2` (`imageID`),
  ADD KEY `reportID` (`reportID`),
  ADD KEY `staffID` (`staffID`);

--
-- Indizes für die Tabelle `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`username`),
  ADD KEY `staffID` (`staffID`);

--
-- Indizes für die Tabelle `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`patientID`),
  ADD UNIQUE KEY `patientID` (`patientID`),
  ADD KEY `patientID_2` (`patientID`),
  ADD KEY `refPhysicianID` (`refPhysicianID`);

--
-- Indizes für die Tabelle `presentstaff`
--
ALTER TABLE `presentstaff`
  ADD PRIMARY KEY (`staffID`),
  ADD KEY `checkIN` (`checkIN`);

--
-- Indizes für die Tabelle `radorder`
--
ALTER TABLE `radorder`
  ADD PRIMARY KEY (`orderID`),
  ADD KEY `patientID` (`patientID`),
  ADD KEY `refPhysicianID` (`refPhysicianID`);

--
-- Indizes für die Tabelle `refphysician`
--
ALTER TABLE `refphysician`
  ADD PRIMARY KEY (`refPhysicianID`);

--
-- Indizes für die Tabelle `report`
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
-- Indizes für die Tabelle `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`procID`),
  ADD KEY `patientID` (`patientID`),
  ADD KEY `orderID` (`orderID`);

--
-- Indizes für die Tabelle `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staffID`),
  ADD UNIQUE KEY `staffID` (`staffID`),
  ADD KEY `staffID_2` (`staffID`);

--
-- Indizes für die Tabelle `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`patientID`),
  ADD UNIQUE KEY `patientID` (`patientID`),
  ADD KEY `nextStaffID` (`nextStaffID`),
  ADD KEY `patientID_2` (`patientID`);

--
-- Indizes für die Tabelle `vitals`
--
ALTER TABLE `vitals`
  ADD PRIMARY KEY (`vitalsID`),
  ADD KEY `vitalsID` (`vitalsID`),
  ADD KEY `byStaffID` (`byStaffID`),
  ADD KEY `patientID` (`patientID`),
  ADD KEY `reportID` (`reportID`),
  ADD KEY `vitalsID_2` (`vitalsID`);

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `billing`
--
ALTER TABLE `billing`
  ADD CONSTRAINT `billing_ibfk_1` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON UPDATE CASCADE;

--
-- Constraints der Tabelle `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `images_ibfk_1` FOREIGN KEY (`reportID`) REFERENCES `report` (`reportID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `images_ibfk_2` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`) ON UPDATE CASCADE;

--
-- Constraints der Tabelle `login`
--
ALTER TABLE `login`
  ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`) ON UPDATE CASCADE;

--
-- Constraints der Tabelle `patient`
--
ALTER TABLE `patient`
  ADD CONSTRAINT `patient_ibfk_1` FOREIGN KEY (`refPhysicianID`) REFERENCES `staff` (`staffID`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints der Tabelle `presentstaff`
--
ALTER TABLE `presentstaff`
  ADD CONSTRAINT `presentstaff_ibfk_1` FOREIGN KEY (`staffID`) REFERENCES `staff` (`staffID`) ON UPDATE CASCADE;

--
-- Constraints der Tabelle `radorder`
--
ALTER TABLE `radorder`
  ADD CONSTRAINT `radorder_ibfk_1` FOREIGN KEY (`refPhysicianID`) REFERENCES `staff` (`staffID`) ON UPDATE CASCADE;

--
-- Constraints der Tabelle `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_ibfk_2` FOREIGN KEY (`lastModifiedID`) REFERENCES `staff` (`staffID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `report_ibfk_3` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON UPDATE CASCADE;

--
-- Constraints der Tabelle `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`orderID`) REFERENCES `radorder` (`orderID`) ON UPDATE CASCADE;

--
-- Constraints der Tabelle `status`
--
ALTER TABLE `status`
  ADD CONSTRAINT `status_ibfk_1` FOREIGN KEY (`nextStaffID`) REFERENCES `staff` (`staffID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `status_ibfk_2` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON UPDATE CASCADE;

--
-- Constraints der Tabelle `vitals`
--
ALTER TABLE `vitals`
  ADD CONSTRAINT `vitals_ibfk_1` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `vitals_ibfk_2` FOREIGN KEY (`byStaffID`) REFERENCES `staff` (`staffID`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `vitals_ibfk_3` FOREIGN KEY (`reportID`) REFERENCES `report` (`reportID`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

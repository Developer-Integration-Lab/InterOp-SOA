SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `aggregator` ;
CREATE SCHEMA IF NOT EXISTS `aggregator` DEFAULT CHARACTER SET latin1 ;
DROP SCHEMA IF EXISTS `assigningauthoritydb` ;
CREATE SCHEMA IF NOT EXISTS `assigningauthoritydb` DEFAULT CHARACTER SET latin1 ;
DROP SCHEMA IF EXISTS `asyncmsgs` ;
CREATE SCHEMA IF NOT EXISTS `asyncmsgs` DEFAULT CHARACTER SET latin1 ;
DROP SCHEMA IF EXISTS `auditrepo` ;
CREATE SCHEMA IF NOT EXISTS `auditrepo` DEFAULT CHARACTER SET latin1 ;
DROP SCHEMA IF EXISTS `docrepository` ;
CREATE SCHEMA IF NOT EXISTS `docrepository` DEFAULT CHARACTER SET latin1 ;
DROP SCHEMA IF EXISTS `patientcorrelationdb` ;
CREATE SCHEMA IF NOT EXISTS `patientcorrelationdb` DEFAULT CHARACTER SET latin1 ;
DROP SCHEMA IF EXISTS `patientdb` ;
CREATE SCHEMA IF NOT EXISTS `patientdb` DEFAULT CHARACTER SET latin1 ;
DROP SCHEMA IF EXISTS `perfrepo` ;
CREATE SCHEMA IF NOT EXISTS `perfrepo` DEFAULT CHARACTER SET latin1 ;
DROP SCHEMA IF EXISTS `subscriptionrepository` ;
CREATE SCHEMA IF NOT EXISTS `subscriptionrepository` DEFAULT CHARACTER SET latin1 ;
USE `aggregator` ;

-- -----------------------------------------------------
-- Table `aggregator`.`agg_message_results`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `aggregator`.`agg_message_results` (
  `MessageId` VARCHAR(32) NOT NULL COMMENT 'This will be a UUID.' ,
  `TransactionId` VARCHAR(32) NOT NULL COMMENT 'This will be a UUID. - Foreign Key to the agg_transaction table.' ,
  `MessageKey` VARCHAR(1000) NOT NULL COMMENT 'This is the key used to tie the response to the original request.' ,
  `MessageOutTime` DATETIME NULL DEFAULT NULL COMMENT 'This is the date/time when the outbound request was recorded.  Format of YYYYMMDDHHMMSS' ,
  `ResponseReceivedTime` DATETIME NULL DEFAULT NULL COMMENT 'This is the date/time when the response was recorded.  Format of YYYYMMDDHHMMSS' ,
  `ResponseMessageType` VARCHAR(100) NULL DEFAULT NULL COMMENT 'This is the name of the outer layer JAXB class for the response message.' ,
  `ResponseMessage` LONGTEXT NULL DEFAULT NULL COMMENT 'The response message in XML - Based on marshalling using JAXB' ,
  PRIMARY KEY (`MessageId`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `aggregator`.`agg_transaction`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `aggregator`.`agg_transaction` (
  `TransactionId` VARCHAR(32) NOT NULL COMMENT 'This will be a UUID' ,
  `ServiceType` VARCHAR(64) NOT NULL ,
  `TransactionStartTime` DATETIME NULL DEFAULT NULL COMMENT 'Format of YYYYMMDDHHMMSS' ,
  PRIMARY KEY (`TransactionId`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

USE `assigningauthoritydb` ;

-- -----------------------------------------------------
-- Table `assigningauthoritydb`.`aa_to_home_community_mapping`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `assigningauthoritydb`.`aa_to_home_community_mapping` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `assigningauthorityid` VARCHAR(45) NOT NULL ,
  `homecommunityid` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`, `assigningauthorityid`) )
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;

USE `asyncmsgs` ;

-- -----------------------------------------------------
-- Table `asyncmsgs`.`asyncmsgrepo`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `asyncmsgs`.`asyncmsgrepo` (
  `Id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `MessageId` VARCHAR(100) NOT NULL ,
  `CreationTime` DATETIME NOT NULL ,
  `ServiceName` VARCHAR(100) NULL DEFAULT NULL ,
  `MsgData` LONGBLOB NULL DEFAULT NULL ,
  PRIMARY KEY (`Id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

USE `auditrepo` ;

-- -----------------------------------------------------
-- Table `auditrepo`.`auditrepository`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `auditrepo`.`auditrepository` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `audit_timestamp` DATETIME NULL DEFAULT NULL ,
  `eventId` BIGINT(20) NOT NULL ,
  `userId` VARCHAR(100) NULL DEFAULT NULL ,
  `participationTypeCode` SMALLINT(6) NULL DEFAULT NULL ,
  `participationTypeCodeRole` SMALLINT(6) NULL DEFAULT NULL ,
  `participationIDTypeCode` VARCHAR(100) NULL DEFAULT NULL ,
  `receiverPatientId` VARCHAR(1024) NULL DEFAULT NULL ,
  `senderPatientId` VARCHAR(100) NULL DEFAULT NULL ,
  `communityId` VARCHAR(255) NULL DEFAULT NULL ,
  `messageType` VARCHAR(100) NOT NULL ,
  `message` TEXT NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `UQ_eventlog_id` (`id` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 5763
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `auditrepo`.`auditextension`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `auditrepo`.`auditextension` (
  `auditextensionId` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `auditrepositoryId` BIGINT(20) NOT NULL ,
  `eventOutcomeIndicator` VARCHAR(10) NULL DEFAULT NULL ,
  `requestMessage` LONGTEXT NULL DEFAULT NULL ,
  `responseMessage` LONGTEXT NULL DEFAULT NULL ,
  PRIMARY KEY (`auditextensionId`) ,
  UNIQUE INDEX `auditextensionId_UNIQUE` (`auditextensionId` ASC) ,
  INDEX `fk_auditextension_auditrepository` (`auditrepositoryId` ASC) ,
  CONSTRAINT `fk_auditextension_auditrepository`
    FOREIGN KEY (`auditrepositoryId` )
    REFERENCES `auditrepo`.`auditrepository` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5551
DEFAULT CHARACTER SET = latin1;

USE `docrepository` ;

-- -----------------------------------------------------
-- Table `docrepository`.`confidentialitycode`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `docrepository`.`confidentialitycode` (
  `confidentialityCodeId` INT(11) NOT NULL ,
  `documentid` INT(11) NOT NULL COMMENT 'Foriegn key to document table' ,
  `confidentialityCode` VARCHAR(64) NULL DEFAULT NULL ,
  `confidentialityCodeScheme` VARCHAR(64) NULL DEFAULT NULL ,
  `confidentialityCodeDisplayName` VARCHAR(64) NULL DEFAULT NULL ,
  PRIMARY KEY (`confidentialityCodeId`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
ROW_FORMAT = COMPACT;


-- -----------------------------------------------------
-- Table `docrepository`.`document`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `docrepository`.`document` (
  `documentid` INT(11) NOT NULL ,
  `DocumentUniqueId` VARCHAR(64) NOT NULL ,
  `DocumentTitle` VARCHAR(128) NULL DEFAULT NULL ,
  `authorPerson` VARCHAR(64) NULL DEFAULT NULL ,
  `authorInstitution` VARCHAR(64) NULL DEFAULT NULL ,
  `authorRole` VARCHAR(64) NULL DEFAULT NULL ,
  `authorSpecialty` VARCHAR(64) NULL DEFAULT NULL ,
  `AvailabilityStatus` VARCHAR(64) NULL DEFAULT NULL ,
  `ClassCode` VARCHAR(64) NULL DEFAULT NULL ,
  `ClassCodeScheme` VARCHAR(64) NULL DEFAULT NULL ,
  `ClassCodeDisplayName` VARCHAR(64) NULL DEFAULT NULL ,
  `ConfidentialityCode` VARCHAR(64) NULL DEFAULT NULL ,
  `ConfidentialityCodeScheme` VARCHAR(64) NULL DEFAULT NULL ,
  `ConfidentialityCodeDisplayName` VARCHAR(64) NULL DEFAULT NULL ,
  `CreationTime` DATETIME NULL DEFAULT NULL COMMENT 'Date format expected: MM/dd/yyyy.HH:mm:ss' ,
  `FormatCode` VARCHAR(64) NULL DEFAULT NULL ,
  `FormatCodeScheme` VARCHAR(64) NULL DEFAULT NULL ,
  `FormatCodeDisplayName` VARCHAR(64) NULL DEFAULT NULL ,
  `PatientId` VARCHAR(64) NULL DEFAULT NULL COMMENT 'Format of HL7 2.x CX' ,
  `ServiceStartTime` DATETIME NULL DEFAULT NULL COMMENT 'Format of YYYYMMDDHHMMSS' ,
  `ServiceStopTime` DATETIME NULL DEFAULT NULL COMMENT 'Format of YYYYMMDDHHMMSS' ,
  `Status` VARCHAR(64) NULL DEFAULT NULL ,
  `Comments` VARCHAR(256) NULL DEFAULT NULL ,
  `Hash` VARCHAR(1028) NULL DEFAULT NULL COMMENT 'Might be better to derive' ,
  `FacilityCode` VARCHAR(64) NULL DEFAULT NULL ,
  `FacilityCodeScheme` VARCHAR(64) NULL DEFAULT NULL ,
  `FacilityCodeDisplayName` VARCHAR(64) NULL DEFAULT NULL ,
  `IntendedRecipientPerson` VARCHAR(128) NULL DEFAULT NULL COMMENT 'Format of HL7 2.x XCN' ,
  `IntendedRecipientOrganization` VARCHAR(128) NULL DEFAULT NULL COMMENT 'Format of HL7 2.x XON' ,
  `LanguageCode` VARCHAR(64) NULL DEFAULT NULL ,
  `LegalAuthenticator` VARCHAR(128) NULL DEFAULT NULL COMMENT 'Format of HL7 2.x XCN' ,
  `MimeType` VARCHAR(32) NULL DEFAULT NULL ,
  `ParentDocumentId` VARCHAR(64) NULL DEFAULT NULL ,
  `ParentDocumentRelationship` VARCHAR(64) NULL DEFAULT NULL ,
  `PracticeSetting` VARCHAR(64) NULL DEFAULT NULL ,
  `PracticeSettingScheme` VARCHAR(64) NULL DEFAULT NULL ,
  `PracticeSettingDisplayName` VARCHAR(64) NULL DEFAULT NULL ,
  `DocumentSize` INT(11) NULL DEFAULT NULL ,
  `SourcePatientId` VARCHAR(128) NULL DEFAULT NULL COMMENT 'Format of HL7 2.x CX' ,
  `Pid3` VARCHAR(128) NULL DEFAULT NULL ,
  `Pid5` VARCHAR(128) NULL DEFAULT NULL ,
  `Pid7` VARCHAR(128) NULL DEFAULT NULL ,
  `Pid8` VARCHAR(128) NULL DEFAULT NULL ,
  `Pid11` VARCHAR(128) NULL DEFAULT NULL ,
  `TypeCode` VARCHAR(64) NULL DEFAULT NULL ,
  `TypeCodeScheme` VARCHAR(64) NULL DEFAULT NULL ,
  `TypeCodeDisplayName` VARCHAR(64) NULL DEFAULT NULL ,
  `DocumentUri` VARCHAR(128) NULL DEFAULT NULL COMMENT 'May derive this value' ,
  `RawData` LONGTEXT NULL DEFAULT NULL ,
  `Persistent` INT(11) NOT NULL ,
  PRIMARY KEY (`documentid`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `docrepository`.`eventcode`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `docrepository`.`eventcode` (
  `eventcodeid` INT(11) NOT NULL ,
  `documentid` INT(11) NOT NULL COMMENT 'Foriegn key to document table' ,
  `EventCode` VARCHAR(64) NULL DEFAULT NULL ,
  `EventCodeScheme` VARCHAR(64) NULL DEFAULT NULL ,
  `EventCodeDisplayName` VARCHAR(64) NULL DEFAULT NULL ,
  PRIMARY KEY (`eventcodeid`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

USE `patientcorrelationdb` ;

-- -----------------------------------------------------
-- Table `patientcorrelationdb`.`correlatedidentifiers`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `patientcorrelationdb`.`correlatedidentifiers` (
  `correlationId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `PatientAssigningAuthorityId` VARCHAR(45) NOT NULL ,
  `PatientId` VARCHAR(45) NOT NULL ,
  `CorrelatedPatientAssignAuthId` VARCHAR(45) NOT NULL ,
  `CorrelatedPatientId` VARCHAR(45) NOT NULL ,
  `CorrelationExpirationDate` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`correlationId`) )
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = latin1;

USE `patientdb` ;

-- -----------------------------------------------------
-- Table `patientdb`.`patient`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `patientdb`.`patient` (
  `patientId` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `dateOfBirth` DATE NULL DEFAULT NULL ,
  `gender` CHAR(2) NULL DEFAULT NULL ,
  `ssn` CHAR(9) NULL DEFAULT NULL ,
  PRIMARY KEY (`patientId`) ,
  UNIQUE INDEX `patientId_UNIQUE` (`patientId` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Patient Repository';


-- -----------------------------------------------------
-- Table `patientdb`.`address`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `patientdb`.`address` (
  `addressId` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `patientId` BIGINT(20) NOT NULL ,
  `street1` VARCHAR(128) NULL DEFAULT NULL ,
  `street2` VARCHAR(128) NULL DEFAULT NULL ,
  `city` VARCHAR(128) NULL DEFAULT NULL ,
  `state` VARCHAR(128) NULL DEFAULT NULL ,
  `postal` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`addressId`) ,
  UNIQUE INDEX `addressId_UNIQUE` (`addressId` ASC) ,
  INDEX `fk_address_patient` (`patientId` ASC) ,
  CONSTRAINT `fk_address_patient`
    FOREIGN KEY (`patientId` )
    REFERENCES `patientdb`.`patient` (`patientId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Addresses';


-- -----------------------------------------------------
-- Table `patientdb`.`identifier`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `patientdb`.`identifier` (
  `identifierId` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `patientId` BIGINT(20) NOT NULL ,
  `id` VARCHAR(64) NULL DEFAULT NULL ,
  `organizationId` VARCHAR(64) NULL DEFAULT NULL ,
  PRIMARY KEY (`identifierId`) ,
  UNIQUE INDEX `identifierrId_UNIQUE` (`identifierId` ASC) ,
  INDEX `fk_identifier_patient` (`patientId` ASC) ,
  CONSTRAINT `fk_identifier_patient`
    FOREIGN KEY (`patientId` )
    REFERENCES `patientdb`.`patient` (`patientId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Identifier definitions';


-- -----------------------------------------------------
-- Table `patientdb`.`personname`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `patientdb`.`personname` (
  `personnameId` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `patientId` BIGINT(20) NOT NULL ,
  `prefix` VARCHAR(64) NULL DEFAULT NULL ,
  `firstName` VARCHAR(64) NULL DEFAULT NULL ,
  `middleName` VARCHAR(64) NULL DEFAULT NULL ,
  `lastName` VARCHAR(64) NULL DEFAULT NULL ,
  `suffix` VARCHAR(64) NULL DEFAULT NULL ,
  PRIMARY KEY (`personnameId`) ,
  UNIQUE INDEX `personnameId_UNIQUE` (`personnameId` ASC) ,
  INDEX `fk_personname_patient` (`patientId` ASC) ,
  CONSTRAINT `fk_personname_patient`
    FOREIGN KEY (`patientId` )
    REFERENCES `patientdb`.`patient` (`patientId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Person Names';


-- -----------------------------------------------------
-- Table `patientdb`.`phonenumber`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `patientdb`.`phonenumber` (
  `phonenumberId` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `patientId` BIGINT(20) NOT NULL ,
  `value` VARCHAR(64) NULL DEFAULT NULL ,
  PRIMARY KEY (`phonenumberId`) ,
  UNIQUE INDEX `phonenumberId_UNIQUE` (`phonenumberId` ASC) ,
  INDEX `fk_phonenumber_patient` (`patientId` ASC) ,
  CONSTRAINT `fk_phonenumber_patient`
    FOREIGN KEY (`patientId` )
    REFERENCES `patientdb`.`patient` (`patientId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Phone Numbers';

USE `perfrepo` ;

-- -----------------------------------------------------
-- Table `perfrepo`.`perfrepository`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `perfrepo`.`perfrepository` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `starttime` TIMESTAMP NULL DEFAULT NULL ,
  `stoptime` TIMESTAMP NULL DEFAULT NULL ,
  `duration` BIGINT(20) NULL DEFAULT NULL ,
  `servicetype` VARCHAR(45) NULL DEFAULT NULL ,
  `messagetype` VARCHAR(10) NULL DEFAULT NULL ,
  `direction` VARCHAR(10) NULL DEFAULT NULL ,
  `communityid` VARCHAR(255) NULL DEFAULT NULL ,
  `status` INT(11) NULL DEFAULT '0' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 2695
DEFAULT CHARACTER SET = latin1
COMMENT = 'Performance Monitor Repository';

USE `subscriptionrepository` ;

-- -----------------------------------------------------
-- Table `subscriptionrepository`.`subscription`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `subscriptionrepository`.`subscription` (
  `id` VARCHAR(128) NOT NULL COMMENT 'Database generated UUID' ,
  `Subscriptionid` VARCHAR(128) NULL DEFAULT NULL COMMENT 'Unique identifier for a CONNECT generated subscription' ,
  `SubscribeXML` LONGTEXT NULL DEFAULT NULL COMMENT 'Full subscribe message as an XML string' ,
  `SubscriptionReferenceXML` LONGTEXT NULL DEFAULT NULL COMMENT 'Full subscription reference as an XML string' ,
  `RootTopic` LONGTEXT NULL DEFAULT NULL COMMENT 'Root topic of the subscription record' ,
  `ParentSubscriptionId` VARCHAR(128) NULL DEFAULT NULL COMMENT 'Subscription id for a parent record provided for fast searching' ,
  `ParentSubscriptionReferenceXML` LONGTEXT NULL DEFAULT NULL COMMENT 'Full subscription reference for a parent record as an XML string' ,
  `Consumer` VARCHAR(128) NULL DEFAULT NULL COMMENT 'Notification consumer system' ,
  `Producer` VARCHAR(128) NULL DEFAULT NULL COMMENT 'Notification producer system' ,
  `PatientId` VARCHAR(128) NULL DEFAULT NULL COMMENT 'Local system patient identifier' ,
  `PatientAssigningAuthority` VARCHAR(128) NULL DEFAULT NULL COMMENT 'Assigning authority for the local patient identifier' ,
  `Targets` LONGTEXT NULL DEFAULT NULL COMMENT 'Full target system as an XML string' ,
  `CreationDate` DATETIME NULL DEFAULT NULL COMMENT 'Format of YYYYMMDDHHMMSS' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

-- -----------------------------------------------------
-- Table `serviceset`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `serviceset` (
  `setId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Test Service Set Id - auto incremented number' ,
  `setName` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'Test Service Set Name' ,
  `initiatorAllowedInd` VARCHAR(45) NOT NULL DEFAULT 'N' COMMENT 'Initiator Allowed Indicator - \'Y\' Yes, \'N\' No' ,
  `responderAllowedInd` VARCHAR(45) NOT NULL DEFAULT 'N' COMMENT 'Responder Allowed Indicator - \'Y\' Yes, \'N\' No' ,
  `ssnHandlingInd` VARCHAR(45) NOT NULL DEFAULT 'N' ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'ACTIVE' COMMENT 'ACTIVE, DISABLED' ,
  `labAccessFilter` BIGINT(20) NULL DEFAULT '0' ,
  `createtime` DATETIME NULL DEFAULT NULL COMMENT 'Datetime row created' ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'User name created by' ,
  `changedtime` DATETIME NULL DEFAULT NULL COMMENT 'Datetime row changed' ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'User name changed by' ,
  PRIMARY KEY (`setId`) )
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Test Service Set';


-- -----------------------------------------------------
-- Table `scenario`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `scenario` (
  `scenarioId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `setId` INT(10) UNSIGNED NOT NULL ,
  `scenarioName` VARCHAR(45) NOT NULL ,
  `quickName` VARCHAR(255) NOT NULL ,
  `description` VARCHAR(1000) NOT NULL DEFAULT '' COMMENT 'Scenario Description' ,
  `conditionDescription` VARCHAR(1000) NULL DEFAULT NULL COMMENT 'Pre-condition description' ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'ACTIVE' COMMENT 'ACTIVE, DISABLED' ,
  `initiatorInd` VARCHAR(45) NOT NULL DEFAULT 'N' ,
  `responderInd` VARCHAR(45) NOT NULL DEFAULT 'N' ,
  `ssnHandlingInd` VARCHAR(45) NOT NULL DEFAULT 'N' ,
  `createtime` DATETIME NULL DEFAULT NULL ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' ,
  `changedtime` DATETIME NULL DEFAULT NULL ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' ,
  PRIMARY KEY (`scenarioId`) ,
  INDEX `fk_scenario_serviceset` (`setId` ASC) ,
  CONSTRAINT `fk_scenario_serviceset`
    FOREIGN KEY (`setId` )
    REFERENCES `serviceset` (`setId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Test Scenario';


-- -----------------------------------------------------
-- Table `testcase`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `testcase` (
  `caseId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(1000) NOT NULL DEFAULT '' ,
  `messageType` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'Raw audit message type' ,
  `messageName` VARCHAR(255) NOT NULL DEFAULT '' ,
  `testType` VARCHAR(45) NOT NULL DEFAULT 'I' COMMENT '			' ,
  `executeType` VARCHAR(45) NOT NULL DEFAULT 'R' ,
  `initiatorInd` VARCHAR(45) NOT NULL DEFAULT 'N' ,
  `responderInd` VARCHAR(45) NOT NULL DEFAULT 'N' ,
  `ssnHandlingInd` VARCHAR(45) NOT NULL DEFAULT 'N' ,
  `expectedTestResults` VARCHAR(1000) NOT NULL DEFAULT '' ,
  `createtime` DATETIME NULL DEFAULT NULL ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' ,
  `changedtime` DATETIME NULL DEFAULT NULL ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' ,
  `nhinSpecHtml` LONGTEXT NULL DEFAULT NULL ,
  PRIMARY KEY (`caseId`) )
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Test Case';


-- -----------------------------------------------------
-- Table `scenariocase`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `scenariocase` (
  `scenarioId` INT(10) UNSIGNED NOT NULL ,
  `caseId` INT(10) UNSIGNED NOT NULL ,
  `userName` VARCHAR(255) NULL DEFAULT NULL ,
  `patientId` VARCHAR(64) NOT NULL ,
  `documentIds` VARCHAR(255) NULL DEFAULT NULL ,
  `participatingRIs` VARCHAR(45) NOT NULL ,
  `successCriteria` VARCHAR(255) NULL DEFAULT NULL ,
  `pdSuccessCriteria` VARCHAR(500) NULL DEFAULT NULL ,
  `pdSuccessCriteriaDesc` VARCHAR(500) NULL DEFAULT NULL ,
  `autoAttachInd` VARCHAR(1) NOT NULL DEFAULT 'N' ,
  `dependentScenarioId` INT(11) NULL DEFAULT NULL ,
  `dependentCaseId` INT(11) NULL DEFAULT NULL ,
  `config` LONGBLOB NULL DEFAULT NULL COMMENT 'Holds configuration XML' ,
  PRIMARY KEY (`scenarioId`, `caseId`) ,
  INDEX `fk_scenariocase_testcase` (`caseId` ASC) ,
  INDEX `fk_scenariocase_scenario` (`scenarioId` ASC) ,
  CONSTRAINT `fk_scenariocase_scenario`
    FOREIGN KEY (`scenarioId` )
    REFERENCES `scenario` (`scenarioId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_scenariocase_testcase`
    FOREIGN KEY (`caseId` )
    REFERENCES `testcase` (`caseId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Test Scenario Case';


-- -----------------------------------------------------
-- Table `altscenariocase`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `altscenariocase` (
  `altscenariocaseId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `scenarioId` INT(10) UNSIGNED NOT NULL ,
  `caseId` INT(10) UNSIGNED NOT NULL ,
  `alternateName` VARCHAR(45) NULL DEFAULT NULL ,
  `description` VARCHAR(255) NULL DEFAULT NULL ,
  `alternateCriteria` VARCHAR(255) NULL DEFAULT NULL ,
  `config` LONGBLOB NULL DEFAULT NULL ,
  PRIMARY KEY (`altscenariocaseId`) ,
  INDEX `fk_altscenariocase_scenariocase` (`scenarioId` ASC, `caseId` ASC) ,
  CONSTRAINT `fk_altscenariocase_scenariocase`
    FOREIGN KEY (`scenarioId` , `caseId` )
    REFERENCES `scenariocase` (`scenarioId` , `caseId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Alternate Scenario Case Configuration';


-- -----------------------------------------------------
-- Table `applicationproperties`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `applicationproperties` (
  `propertyKey` VARCHAR(64) NOT NULL ,
  `propertyValue` VARCHAR(200) NOT NULL ,
  `description` VARCHAR(200) NULL DEFAULT NULL ,
  PRIMARY KEY (`propertyKey`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `attachment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `attachment` (
  `attachmentId` INT(11) NOT NULL AUTO_INCREMENT ,
  `resultId` INT(10) UNSIGNED NULL DEFAULT NULL ,
  `filename` VARCHAR(255) NOT NULL DEFAULT '' ,
  `description` VARCHAR(2000) NULL DEFAULT NULL ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'ACTIVE' COMMENT 'ACTIVE, EXPIRED, IGNORE' ,
  `contentType` VARCHAR(45) NOT NULL DEFAULT '' ,
  `createtime` DATETIME NULL DEFAULT NULL ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' ,
  `changedtime` DATETIME NULL DEFAULT NULL ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' ,
  `content` LONGBLOB NULL DEFAULT NULL ,
  `setExecutionId` INT(10) UNSIGNED NULL DEFAULT NULL ,
  PRIMARY KEY (`attachmentId`) ,
  UNIQUE INDEX `attachmentId_UNIQUE` (`attachmentId` ASC) ,
  INDEX `fk_attachment_caseresult` (`resultId` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Validation Evidence for Manually Checked Results';


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `user` (
  `userId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'User Id - auto-incremented number' ,
  `username` VARCHAR(45) NOT NULL COMMENT 'User Name - Unique log in name' ,
  `password` VARCHAR(45) NOT NULL COMMENT 'Password - current stored non-encrypted' ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'A' COMMENT 'User Status - \'A\' Active, \'L\' Locked, \'T\' Terminated' ,
  `expirationTime` DATETIME NULL DEFAULT NULL COMMENT 'Expiration Datetime of this user' ,
  `invalidAttempts` INT(11) NULL DEFAULT '0' ,
  `securityReminder` VARCHAR(1000) NULL DEFAULT NULL ,
  `comments` VARCHAR(2000) NULL DEFAULT NULL COMMENT 'User Comments' ,
  `createtime` DATETIME NULL DEFAULT NULL COMMENT 'Datetime row created' ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'User name created by' ,
  `changedtime` DATETIME NULL DEFAULT NULL COMMENT 'Datetime row changed' ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'User name changed by' ,
  PRIMARY KEY (`userId`) ,
  UNIQUE INDEX `unq_user_username` (`username` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Security User';


-- -----------------------------------------------------
-- Table `participant`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `participant` (
  `participantId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Participant Id - auto-incremented' ,
  `participantName` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'Participant Name' ,
  `userId` INT(10) UNSIGNED NOT NULL COMMENT 'Participant security user id' ,
  `communityId` VARCHAR(255) NOT NULL COMMENT 'Participant Community Id (OID)' ,
  `assigningAuthorityId` VARCHAR(255) NOT NULL ,
  `ipAddress` VARCHAR(45) NOT NULL COMMENT 'Participant IP Address' ,
  `version` VARCHAR(45) NOT NULL DEFAULT 'Connect_2.4.8' COMMENT 'Gateway version of the participant system.' ,
  `nhinRepId` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT 'NHIN Representative Id - person who registered this participant' ,
  `contactName` VARCHAR(255) NOT NULL COMMENT 'Participant Contact Name - initially entered by NHIN Rep during registration' ,
  `contactPhone` VARCHAR(45) NOT NULL COMMENT 'Participant Contact Phone - initially entered by NHIN Rep during registration' ,
  `contactEmail` VARCHAR(255) NOT NULL COMMENT 'Participant Contact Email - initially entered by NHIN Rep during registration' ,
  `initiatorInd` VARCHAR(45) NOT NULL DEFAULT 'N' COMMENT 'Initiator Indicator - \'Y\' Yes, \'N\' No' ,
  `responderInd` VARCHAR(45) NOT NULL DEFAULT 'N' COMMENT 'Respoder Indicator - \'Y\' Yes, \'N\' No' ,
  `ssnHandlingInd` VARCHAR(45) NOT NULL DEFAULT 'N' COMMENT 'SSN Handling Indicator - \'Y\' Yes, \'N\' No' ,
  `dynamicContentInd` VARCHAR(45) NOT NULL DEFAULT 'M' COMMENT 'Dynamic Document Content Indicator - \'M\' Multiple, \'S\' Single' ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'A' COMMENT 'NEW, ACTIVE, INACTIVE' ,
  `commVerifyStatus` VARCHAR(45) NULL DEFAULT 'NOT VERIFIED' COMMENT 'VERIFIED, FAIL, NOT VERIFIED' ,
  `commVerifyTimestamp` DATETIME NULL DEFAULT NULL ,
  `labAccessFilter` BIGINT(20) NULL DEFAULT '3' ,
  `createtime` DATETIME NULL DEFAULT NULL COMMENT 'Datetime row created' ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'User name created by' ,
  `changedtime` DATETIME NULL DEFAULT NULL COMMENT 'Datetime row changed' ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'User name changed by' ,
  `resouceIdSendInd` VARCHAR(45) NOT NULL DEFAULT 'Y' COMMENT 'Whether the participant send resource id as part of SAML assertion',
  PRIMARY KEY (`participantId`) ,
  INDEX `fk_participant_user` (`userId` ASC) ,
  CONSTRAINT `fk_participant_user`
    FOREIGN KEY (`userId` )
    REFERENCES `user` (`userId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Participant User';


-- -----------------------------------------------------
-- Table `scenarioexecution`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `scenarioexecution` (
  `scenarioExecutionId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `participantId` INT(10) UNSIGNED NOT NULL ,
  `scenarioId` INT(10) UNSIGNED NOT NULL ,
  `executionUniqueId` VARCHAR(45) NOT NULL ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'NEW' COMMENT 'NEW, ACTIVE, CLOSED, PENDING, CERTIFIED' ,
  `beginTime` DATETIME NULL DEFAULT NULL ,
  `endTime` DATETIME NULL DEFAULT NULL ,
  `submitComments` VARCHAR(2000) NULL DEFAULT NULL ,
  `validationComments` VARCHAR(2000) NULL DEFAULT NULL ,
  PRIMARY KEY (`scenarioExecutionId`) ,
  INDEX `fk_scenarioexecution_participant` (`participantId` ASC) ,
  INDEX `fk_scenarioexecution_scenario` (`scenarioId` ASC) ,
  CONSTRAINT `fk_scenarioexecution_participant`
    FOREIGN KEY (`participantId` )
    REFERENCES `participant` (`participantId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_scenarioexecution_scenario`
    FOREIGN KEY (`scenarioId` )
    REFERENCES `scenario` (`scenarioId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Participant Scenario Execution';


-- -----------------------------------------------------
-- Table `caseexecution`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `caseexecution` (
  `caseExecutionId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `scenarioExecutionId` INT(10) UNSIGNED NOT NULL ,
  `caseId` INT(10) UNSIGNED NOT NULL ,
  `userName` VARCHAR(255) NULL DEFAULT NULL ,
  `patientId` VARCHAR(64) NOT NULL ,
  `participantPatientId` VARCHAR(64) NULL DEFAULT NULL ,
  `documentIds` VARCHAR(1000) NULL DEFAULT NULL ,
  `participatingRIs` VARCHAR(45) NOT NULL DEFAULT '1' ,
  `successCriteria` VARCHAR(255) NULL DEFAULT NULL ,
  `pdSuccessCriteria` VARCHAR(500) NULL DEFAULT NULL ,
  `pdSuccessCriteriaDesc` VARCHAR(500) NULL DEFAULT NULL ,
  `autoAttachInd` VARCHAR(1) NOT NULL DEFAULT 'N' ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'NEW' COMMENT 'NEW, ACTIVE, CLOSED, PENDING, CERTIFIED' ,
  `version` VARCHAR(45) NOT NULL DEFAULT 'Connect_2.4.8' ,
  `beginTime` DATETIME NULL DEFAULT NULL ,
  `messageType` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'Raw audit message type' ,
  `dependentScenarioId` INT(11) NULL DEFAULT NULL ,
  `dependentCaseId` INT(11) NULL DEFAULT NULL ,
  `startTimer` DATETIME NULL DEFAULT NULL ,
  `endTimer` DATETIME NULL DEFAULT NULL ,
  `resouceIdSendInd` VARCHAR(45) NOT NULL COMMENT 'To indicate whether the test case is executed with out resouce id or not',
  PRIMARY KEY (`caseExecutionId`) ,
  INDEX `fk_caseexecution_scenarioexecution` (`scenarioExecutionId` ASC) ,
  INDEX `fk_caseexecution_case` (`caseId` ASC) ,
  CONSTRAINT `fk_caseexecution_case`
    FOREIGN KEY (`caseId` )
    REFERENCES `testcase` (`caseId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_caseexecution_scenarioexecution`
    FOREIGN KEY (`scenarioExecutionId` )
    REFERENCES `scenarioexecution` (`scenarioExecutionId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Participant Scenario Case Execution';


-- -----------------------------------------------------
-- Table `caseresult`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `caseresult` (
  `resultId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `caseExecutionId` INT(10) UNSIGNED NOT NULL ,
  `executeTime` DATETIME NULL DEFAULT NULL ,
  `labInd` VARCHAR(2) NULL DEFAULT NULL COMMENT 'Y or NULL' ,
  `result` VARCHAR(45) NOT NULL DEFAULT 'FAIL' COMMENT 'PASS, FAIL, PROGRESS' ,
  `resultInfo` VARCHAR(255) NULL DEFAULT NULL ,
  `documentIds` VARCHAR(1000) NULL DEFAULT NULL ,
  `labDocumentIds` VARCHAR(1000) NULL DEFAULT NULL ,
  `submissionInd` VARCHAR(45) NOT NULL DEFAULT 'N' COMMENT 'Submission for certification; \'Y\' Yes, \'N\' No' ,
  `message` VARCHAR(4000) NULL DEFAULT '' ,
  `errorCode` VARCHAR(100) NULL ,
  `stacktrace` LONGTEXT NULL ,
  `processedRIsCount` INT(11) NULL DEFAULT NULL ,
  `createdAt` DATETIME NULL DEFAULT NULL ,
  `createdBy` VARCHAR(50) NULL DEFAULT NULL ,
  `updatedAt` DATETIME NULL DEFAULT NULL ,
  `updatedBy` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`resultId`) ,
  INDEX `fk_caseresult_caseexecution` (`caseExecutionId` ASC) ,
  CONSTRAINT `fk_caseresult_caseexecution`
    FOREIGN KEY (`caseExecutionId` )
    REFERENCES `caseexecution` (`caseExecutionId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab - Participant Scenario Case Result';


-- -----------------------------------------------------
-- Table `testharnessri`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `testharnessri` (
  `testharnessId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `participatingId` INT(10) NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `version` VARCHAR(45) NOT NULL DEFAULT '' ,
  `communityId` VARCHAR(128) NOT NULL ,
  `assigningAuthorityId` VARCHAR(255) NOT NULL ,
  `ipAddress` VARCHAR(45) NOT NULL ,
  `lastAuditRepositoryId` BIGINT(20) NULL DEFAULT NULL ,
  `createtime` DATETIME NULL DEFAULT NULL ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' ,
  `changedtime` DATETIME NULL DEFAULT NULL ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' ,
  PRIMARY KEY (`testharnessId`) )
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Test Harness RI';


-- -----------------------------------------------------
-- Table `auditsummary`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `auditsummary` (
  `summaryId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `repositoryId` INT(11) NOT NULL ,
  `testharnessId` INT(10) UNSIGNED NOT NULL ,
  `resultId` INT(10) UNSIGNED NOT NULL ,
  `type` VARCHAR(45) NOT NULL ,
  `typeCode` VARCHAR(45) NOT NULL ,
  `outcomeIndicator` INT(11) NOT NULL ,
  `userId` VARCHAR(100) NULL DEFAULT NULL ,
  `userName` VARCHAR(200) NULL DEFAULT NULL ,
  `messageType` VARCHAR(100) NULL DEFAULT NULL ,
  `actionCode` VARCHAR(20) NULL DEFAULT NULL ,
  `networkAccessPointId` VARCHAR(100) NULL DEFAULT NULL ,
  `patientId` VARCHAR(64) NOT NULL ,
  `enterpriseSourceSite` VARCHAR(100) NULL DEFAULT NULL ,
  `enterpriseSourceId` VARCHAR(255) NULL DEFAULT NULL ,
  `executeTime` DATETIME NULL DEFAULT NULL COMMENT 'Execution time from audit log' ,
  `createtime` DATETIME NULL DEFAULT NULL ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' ,
  `changedtime` DATETIME NULL DEFAULT NULL ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' ,
  PRIMARY KEY (`summaryId`) ,
  INDEX `fk_auditsummary_testharnessri` (`testharnessId` ASC) ,
  INDEX `fk_auditsummary_caseresult` (`resultId` ASC) ,
  CONSTRAINT `fk_auditsummary_caseresult`
    FOREIGN KEY (`resultId` )
    REFERENCES `caseresult` (`resultId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auditsummary_testharnessri`
    FOREIGN KEY (`testharnessId` )
    REFERENCES `testharnessri` (`testharnessId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab RI Audit Summary';


-- -----------------------------------------------------
-- Table `participantaudithistory`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `participantaudithistory` (
  `auditId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Participant Audit History Id - auto incremented number' ,
  `participantId` INT(10) UNSIGNED NOT NULL COMMENT 'Participant Id' ,
  `participantName` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'Participant Name' ,
  `userId` INT(10) UNSIGNED NOT NULL COMMENT 'Participant security user id' ,
  `communityId` VARCHAR(255) NOT NULL COMMENT 'Participant Community Id (OID)' ,
  `assigningAuthorityId` VARCHAR(255) NOT NULL ,
  `ipAddress` VARCHAR(45) NOT NULL COMMENT 'Participant IP Address' ,
  `nhinRepId` INT(11) NOT NULL DEFAULT '0' COMMENT 'NHIN Representative Id - person who registered this participant' ,
  `contactName` VARCHAR(255) NOT NULL COMMENT 'Participant Contact Name - initially entered by NHIN Rep during registration' ,
  `contactPhone` VARCHAR(45) NOT NULL COMMENT 'Participant Contact Phone - initially entered by NHIN Rep during registration' ,
  `contactEmail` VARCHAR(255) NOT NULL COMMENT 'Participant Contact Email - initially entered by NHIN Rep during registration' ,
  `initiatorInd` VARCHAR(45) NOT NULL COMMENT 'Dynamic Document Support Indicator - \'Y\' Yes, \'N\' No' ,
  `reposnderInd` VARCHAR(45) NOT NULL COMMENT 'Deferred Document Support Indicator - \'Y\' Yes, \'N\' No' ,
  `ssnHandlingInd` VARCHAR(45) NOT NULL COMMENT 'SSN Handling Indicator - \'Y\' Yes, \'N\' No' ,
  `status` VARCHAR(45) NOT NULL ,
  `commVerifyStatus` VARCHAR(45) NULL DEFAULT NULL ,
  `commVerifyTimestamp` DATETIME NULL DEFAULT NULL ,
  `labAccessFilter` BIGINT(20) NULL DEFAULT '0' ,
  `auditcreatetime` DATETIME NULL DEFAULT NULL COMMENT 'Audit Datetime row created' ,
  `auditcreateuser` VARCHAR(45) NOT NULL COMMENT 'Audit User name created by' ,
  `auditchangedtime` DATETIME NULL DEFAULT NULL COMMENT 'Audit Datetime row changed' ,
  `auditchangeduser` VARCHAR(45) NOT NULL COMMENT 'Audit User name changed by' ,
  `createtime` DATETIME NULL DEFAULT NULL COMMENT 'Datetime row created' ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'User name created by' ,
  PRIMARY KEY (`auditId`) ,
  INDEX `fk_participantaudithistory_participant` (`participantId` ASC) ,
  CONSTRAINT `fk_participantaudithistory_participant`
    FOREIGN KEY (`participantId` )
    REFERENCES `participant` (`participantId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Participant Audit History';


-- -----------------------------------------------------
-- Table `participantendpoint`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `participantendpoint` (
  `participantendpointId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `participantId` INT(10) UNSIGNED NOT NULL ,
  `messageType` VARCHAR(45) NOT NULL ,
  `endpoint` VARCHAR(500) NULL DEFAULT NULL ,
  PRIMARY KEY (`participantendpointId`) ,
  INDEX `fk_participantendpoint_participant` (`participantId` ASC) ,
  CONSTRAINT `fk_participantendpoint_participant`
    FOREIGN KEY (`participantId` )
    REFERENCES `participant` (`participantId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Test Participant Service Endpoint';


-- -----------------------------------------------------
-- Table `patient`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `patient` (
  `patientId` VARCHAR(64) NOT NULL ,
  `prefix` VARCHAR(45) NULL DEFAULT NULL ,
  `firstName` VARCHAR(45) NULL DEFAULT NULL ,
  `middleName` VARCHAR(45) NULL DEFAULT NULL ,
  `lastName` VARCHAR(45) NULL DEFAULT NULL ,
  `suffix` VARCHAR(45) NULL DEFAULT NULL ,
  `addressLine1` VARCHAR(45) NULL DEFAULT NULL ,
  `addressLine2` VARCHAR(45) NULL DEFAULT NULL ,
  `city` VARCHAR(45) NULL DEFAULT NULL ,
  `state` VARCHAR(45) NULL DEFAULT NULL ,
  `zipCode` VARCHAR(45) NULL DEFAULT NULL ,
  `homePhone` VARCHAR(45) NULL DEFAULT NULL ,
  `workPhone` VARCHAR(45) NULL DEFAULT NULL ,
  `dateOfBirth` DATETIME NULL DEFAULT NULL ,
  `ssn` VARCHAR(9) NULL DEFAULT NULL ,
  `gender` VARCHAR(45) NULL DEFAULT NULL ,
  `status` VARCHAR(45) NULL DEFAULT NULL COMMENT 'ASSIGNED, OPEN' ,
  `labAccessFilter` BIGINT(20) NULL DEFAULT '0' ,
  PRIMARY KEY (`patientId`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Patient Master';


-- -----------------------------------------------------
-- Table `participantpatientmap`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `participantpatientmap` (
  `participantPatientMapId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `participantId` INT(10) UNSIGNED NOT NULL ,
  `patientId` VARCHAR(64) NOT NULL ,
  `participantPatientId` VARCHAR(64) NULL DEFAULT NULL ,
  `createtime` DATETIME NULL DEFAULT NULL ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' ,
  `changedtime` DATETIME NULL DEFAULT NULL ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' ,
  PRIMARY KEY (`participantPatientMapId`) ,
  UNIQUE INDEX `unq_participantpatientmap_participant_patient` (`participantId` ASC, `patientId` ASC) ,
  INDEX `fk_participantpatientmap_participant` (`participantId` ASC) ,
  INDEX `fk_participantpatientmap_patient` (`patientId` ASC) ,
  CONSTRAINT `fk_participantpatientmap_participant`
    FOREIGN KEY (`participantId` )
    REFERENCES `participant` (`participantId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_participantpatientmap_patient`
    FOREIGN KEY (`patientId` )
    REFERENCES `patient` (`patientId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Participant Patient Mapping';


-- -----------------------------------------------------
-- Table `caseresultparameters`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `caseresultparameters` (
  `caseresultParamsId` INT(11) NOT NULL AUTO_INCREMENT ,
  `resultId` INT(10) UNSIGNED NOT NULL ,
  `paramName` VARCHAR(250) NOT NULL ,
  `displayParamName` VARCHAR(250) NULL DEFAULT NULL ,
  `paramValue` VARCHAR(250) NULL DEFAULT NULL ,
  `required` CHAR(1) NULL DEFAULT 'N' ,
  PRIMARY KEY (`caseresultParamsId`) ,
  INDEX `fk_caseresultparameters_caseresult` (`resultId` ASC) ,
  CONSTRAINT `fk_caseresultparameters_caseresult`
    FOREIGN KEY (`resultId` )
    REFERENCES `caseresult` (`resultId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Store each test case actual parameters';


-- -----------------------------------------------------
-- Table `caseschedule`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `caseschedule` (
  `caseScheduleId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `caseExecutionId` INT(10) UNSIGNED NOT NULL ,
  `executed` VARCHAR(45) NOT NULL DEFAULT 'N' COMMENT 'Executed flag - \'Y\' Yes, \'N\' No' ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'NEW' COMMENT 'NEW, RUNNING, COMPLETED' ,
  `scheduledTime` DATETIME NULL DEFAULT NULL ,
  `executedTime` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`caseScheduleId`) ,
  INDEX `fk_caseschedule_caseexecution` (`caseExecutionId` ASC) ,
  CONSTRAINT `fk_caseschedule_caseexecution`
    FOREIGN KEY (`caseExecutionId` )
    REFERENCES `caseexecution` (`caseExecutionId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Test Case Scheduled Job';


-- -----------------------------------------------------
-- Table `codetype`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `codetype` (
  `type` VARCHAR(64) NOT NULL ,
  `description` VARCHAR(255) NOT NULL DEFAULT '' ,
  PRIMARY KEY (`type`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Code Type Master - defines valid code sets';


-- -----------------------------------------------------
-- Table `code`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `code` (
  `type` VARCHAR(64) NOT NULL ,
  `value` VARCHAR(45) NOT NULL ,
  `intvalue` BIGINT(20) NULL DEFAULT '0' ,
  `description` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`type`, `value`) ,
  INDEX `fk_code_codetype` (`type` ASC) ,
  CONSTRAINT `fk_code_codetype`
    FOREIGN KEY (`type` )
    REFERENCES `codetype` (`type` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Code Master - code sets and config settings';


-- -----------------------------------------------------
-- Table `nhinrep`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `nhinrep` (
  `nhinRepId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '		' ,
  `name` VARCHAR(45) NOT NULL ,
  `userId` INT(10) UNSIGNED NOT NULL ,
  `contactName` VARCHAR(255) NOT NULL DEFAULT '' ,
  `contactPhone` VARCHAR(45) NOT NULL DEFAULT '' ,
  `contactEmail` VARCHAR(255) NOT NULL DEFAULT '' ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'ACTIVE' ,
  `createtime` DATETIME NULL DEFAULT NULL ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' ,
  `changedtime` DATETIME NULL DEFAULT NULL ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' ,
  PRIMARY KEY (`nhinRepId`) ,
  INDEX `fk_nhinrep_user` (`userId` ASC) ,
  CONSTRAINT `fk_nhinrep_user`
    FOREIGN KEY (`userId` )
    REFERENCES `user` (`userId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab NHIN Representative';


-- -----------------------------------------------------
-- Table `resultdocument`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `resultdocument` (
  `resultDocumentId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `resultId` INT(10) UNSIGNED NOT NULL ,
  `documentUniqueId` VARCHAR(128) NULL DEFAULT NULL ,
  `classCode` VARCHAR(45) NULL DEFAULT NULL ,
  `documentHash` VARCHAR(1028) NULL DEFAULT NULL ,
  `documentSize` int(11) NULL DEFAULT NULL ,
  `repositoryId` VARCHAR(45) NULL DEFAULT NULL ,
  `communityId` VARCHAR(128) NULL DEFAULT NULL ,
  `rawData` LONGBLOB NULL DEFAULT NULL ,
  PRIMARY KEY (`resultDocumentId`) ,
  INDEX `fk_resultdocument_caseresult` (`resultId` ASC) ,
  CONSTRAINT `fk_resultdocument_caseresult`
    FOREIGN KEY (`resultId` )
    REFERENCES `caseresult` (`resultId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Case Result Document';


-- -----------------------------------------------------
-- Table `role`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `role` (
  `roleId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Role Id - auto-incremented number' ,
  `rolename` VARCHAR(255) NOT NULL COMMENT 'Role Name - Unique description of role' ,
  `labAccessFilter` BIGINT(20) NULL DEFAULT '0' ,
  `createtime` DATETIME NULL DEFAULT NULL COMMENT 'Datetime row created' ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'User name created by' ,
  `changedtime` DATETIME NULL DEFAULT NULL COMMENT 'Datetime row changed' ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' COMMENT 'User name changed by' ,
  PRIMARY KEY (`roleId`) )
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Security Role';


-- -----------------------------------------------------
-- Table `scenariocaseparameters`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `scenariocaseparameters` (
  `scenariocaseparametersId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `scenarioId` INT(10) UNSIGNED NOT NULL ,
  `caseId` INT(10) UNSIGNED NOT NULL ,
  `paramName` VARCHAR(250) NULL DEFAULT NULL ,
  `displayParamName` VARCHAR(250) NULL DEFAULT NULL ,
  `paramValue` VARCHAR(250) NULL DEFAULT NULL ,
  `required` VARCHAR(1) NULL DEFAULT 'N' ,
  PRIMARY KEY (`scenariocaseparametersId`) ,
  INDEX `fk_scenariocaseparameters_scenariocase` (`scenarioId` ASC, `caseId` ASC) ,
  CONSTRAINT `fk_scenariocaseparameters_scenariocase`
    FOREIGN KEY (`scenarioId` , `caseId` )
    REFERENCES `scenariocase` (`scenarioId` , `caseId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `servicesetexecution`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `servicesetexecution` (
  `setExecutionId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `participantId` INT(10) UNSIGNED NOT NULL ,
  `setId` INT(10) UNSIGNED NOT NULL ,
  `executionUniqueId` VARCHAR(45) NOT NULL ,
  `initiatorInd` VARCHAR(45) NOT NULL DEFAULT 'N' COMMENT 'Initiator Indicator - \'Y\' Yes, \'N\' No' ,
  `responderInd` VARCHAR(45) NOT NULL DEFAULT 'N' COMMENT 'Responder Indicator - \'Y\' Yes, \'N\' No' ,
  `ssnHandlingInd` VARCHAR(45) NOT NULL DEFAULT 'N' ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'NEW' COMMENT 'NEW, ACTIVE, CLOSED, PENDING, CERTIFIED' ,
  `beginTime` DATETIME NULL DEFAULT NULL ,
  `endTime` DATETIME NULL DEFAULT NULL ,
  `version` VARCHAR(45) NOT NULL  DEFAULT 'Connect_2.4.8' ,
  `submitComments` VARCHAR(2000) NULL DEFAULT NULL ,
  `validationComments` VARCHAR(2000) NULL DEFAULT NULL ,
  PRIMARY KEY (`setExecutionId`) ,
  INDEX `fk_servicesetexecution_participant` (`participantId` ASC) ,
  INDEX `fk_servicesetexecution_serviceset` (`setId` ASC) ,
  CONSTRAINT `fk_servicesetexecution_participant`
    FOREIGN KEY (`participantId` )
    REFERENCES `participant` (`participantId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_servicesetexecution_serviceset`
    FOREIGN KEY (`setId` )
    REFERENCES `serviceset` (`setId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Participant Test Service Set Execution';


-- -----------------------------------------------------
-- Table `testharnessendpoint`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `testharnessendpoint` (
  `testharnessendpointId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `testharnessId` INT(10) UNSIGNED NOT NULL ,
  `messageType` VARCHAR(45) NOT NULL ,
  `endpoint` VARCHAR(500) NULL DEFAULT NULL ,
  PRIMARY KEY (`testharnessendpointId`) ,
  INDEX `fk_testharnessendpoint_testharnessri` (`testharnessId` ASC) ,
  CONSTRAINT `fk_testharnessendpoint_testharnessri`
    FOREIGN KEY (`testharnessId` )
    REFERENCES `testharnessri` (`testharnessId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Test Harness RI Service End Point';


-- -----------------------------------------------------
-- Table `userrole`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `userrole` (
  `userId` INT(10) UNSIGNED NOT NULL ,
  `roleId` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`userId`, `roleId`) ,
  INDEX `fk_userrole_user` (`userId` ASC) ,
  INDEX `fk_userrole_role` (`roleId` ASC) ,
  CONSTRAINT `fk_userrole_role`
    FOREIGN KEY (`roleId` )
    REFERENCES `role` (`roleId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_userrole_user`
    FOREIGN KEY (`userId` )
    REFERENCES `user` (`userId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Security User Role Relationship';


-- -----------------------------------------------------
-- Table `validation`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `validation` (
  `validationId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `setExecutionId` INT(10) UNSIGNED NOT NULL ,
  `nhinRepId` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT 'From Service Set Execution' ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'SUBMITTED' COMMENT 'SUBMITTED, REVIEW, APPROVED, DENIED' ,
  `decision` VARCHAR(45) NULL DEFAULT NULL COMMENT 'ACCEPT, REJECT' ,
  `decisionReason` VARCHAR(2000) NULL DEFAULT NULL ,
  `comments` VARCHAR(2000) NULL DEFAULT NULL ,
  `createtime` DATETIME NULL DEFAULT NULL ,
  `createuser` VARCHAR(45) NOT NULL DEFAULT '' ,
  `changedtime` DATETIME NULL DEFAULT NULL ,
  `changeduser` VARCHAR(45) NOT NULL DEFAULT '' ,
  PRIMARY KEY (`validationId`) ,
  INDEX `fk_validation_servicesetexecution` (`setExecutionId` ASC) ,
  CONSTRAINT `fk_validation_servicesetexecution`
    FOREIGN KEY (`setExecutionId` )
    REFERENCES `servicesetexecution` (`setExecutionId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Lab Participant Validation';


-- -----------------------------------------------------
-- Table `questionnaire`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `questionnaire` (
  `questionId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `setId` INT(10) UNSIGNED NOT NULL COMMENT 'foreign key for serviceset.setId' ,
  `name` VARCHAR(250) NOT NULL ,
  `serviceType` VARCHAR(45) NOT NULL ,
  `description` VARCHAR(2000) NOT NULL ,
  `answer` VARCHAR(45) NOT NULL DEFAULT 'Y' ,
  `initiatorInd` VARCHAR(45) NOT NULL DEFAULT 'N' COMMENT '\'\'Y\'\' Yes, \'\'N\'\' No\'' ,
  `responderInd` VARCHAR(45) NOT NULL DEFAULT 'N' COMMENT '\'\'Y\'\' Yes, \'\'N\'\' No\'' ,
  `displayOrder` INT(10) NULL DEFAULT NULL ,
  `uiDisplay` VARCHAR(45) NOT NULL DEFAULT 'Y' COMMENT '\'\'Y\'\' Yes, \'\'N\'\' No\'' ,
  PRIMARY KEY (`questionId`) ,
  INDEX `fk_Questionnaire_serviceset` (`setId` ASC) ,
  CONSTRAINT `fk_Questionnaire_serviceset0`
    FOREIGN KEY (`setId` )
    REFERENCES `serviceset` (`setId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'Questionnaire table to hold questions for each service set.';


-- -----------------------------------------------------
-- Table `questionnairecase`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `questionnairecase` (
  `questionId` INT(10) UNSIGNED NOT NULL DEFAULT '0' ,
  `caseId` INT(10) UNSIGNED NOT NULL DEFAULT '0' ,
  PRIMARY KEY (`questionId`, `caseId`) ,
  INDEX `fk_questionnairecase_questionnaire` (`questionId` ASC) ,
  INDEX `fk_questionnairecase_testcase` (`caseId` ASC) ,
  CONSTRAINT `fk_questionnairecase_questionnaire0`
    FOREIGN KEY (`questionId` )
    REFERENCES `questionnaire` (`questionId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_questionnairecase_testcase0`
    FOREIGN KEY (`caseId` )
    REFERENCES `testcase` (`caseId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COMMENT = 'questionnaire test case mapping table';


-- -----------------------------------------------------
-- Table `questionnaireresponse`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `questionnaireresponse` (
  `questionnaireresponseId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `setExecutionId` INT(10) UNSIGNED NOT NULL ,
  `questionId` INT(10) UNSIGNED NOT NULL ,
  `participantId` INT(10) UNSIGNED NOT NULL ,
  `executionUniqueId` VARCHAR(45) NOT NULL ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'NEW' COMMENT '\'NEW, ACTIVE, CLOSED, PENDING, CERTIFIED\'' ,
  `answer` VARCHAR(45) NOT NULL ,
  `beginTime` DATETIME NULL DEFAULT NULL ,
  `endTime` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`questionnaireresponseId`) ,
  INDEX `participantId` (`participantId` ASC) ,
  INDEX `questionId` (`questionId` ASC) ,
  INDEX `fk_questionnaireresponse_servicesetexecution` (`setExecutionId` ASC) ,
  CONSTRAINT `fk_questionnaireresponse_participant0`
    FOREIGN KEY (`participantId` )
    REFERENCES `participant` (`participantId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_questionnaireresponse_questionnaire0`
    FOREIGN KEY (`questionId` )
    REFERENCES `questionnaire` (`questionId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_questionnaireresponse_servicesetexecution0`
    FOREIGN KEY (`setExecutionId` )
    REFERENCES `servicesetexecution` (`setExecutionId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'To store each participant executed questionnaires';


-- -----------------------------------------------------
-- Table `questionnairetestplan`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `questionnairetestplan` (
  `questionnairetestplanId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT ,
  `questionnaireresponseId` INT(10) UNSIGNED NOT NULL ,
  `caseId` INT(10) UNSIGNED NOT NULL ,
  `status` VARCHAR(45) NOT NULL DEFAULT 'NEW' ,
  PRIMARY KEY (`questionnairetestplanId`) ,
  INDEX `fk_questionnairetestplan_questionnaireresponse` (`questionnaireresponseId` ASC) ,
  INDEX `fk_questionnairetestplan_case` (`caseId` ASC) ,
  CONSTRAINT `fk_questionnairetestplan_case0`
    FOREIGN KEY (`caseId` )
    REFERENCES `testcase` (`caseId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_questionnairetestplan_questionnaireresponse0`
    FOREIGN KEY (`questionnaireresponseId` )
    REFERENCES `questionnaireresponse` (`questionnaireresponseId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1
COMMENT = 'list of test cases executed under each questionnaire ';

-- -----------------------------------------------------
-- Table `transaction`
-- -----------------------------------------------------

 CREATE TABLE `transaction` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `statuscode` int(11) DEFAULT NULL,
   `msgtype` varchar(64) DEFAULT NULL,
   `time` datetime DEFAULT NULL,
   `rule` varchar(255) DEFAULT NULL,
   `method` varchar(50) DEFAULT NULL,
   `path` varchar(1000) DEFAULT NULL,
   `client` varchar(255) DEFAULT NULL,
   `server` varchar(255) DEFAULT NULL,
   `sender` VARCHAR(255) NULL ,
   `receiver` VARCHAR(255) NULL ,
   `contenttype` varchar(100) DEFAULT NULL,
   `contentlength` int(11) DEFAULT NULL,
   `duration` int(11) DEFAULT NULL,
   `msgfilepath` varchar(255) DEFAULT NULL,
   `msgheader` blob,
   `msg` longblob,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=latin1 ;
 
 
-- -----------------------------------------------------
-- Table `resultsummary`
-- -----------------------------------------------------

 CREATE TABLE `resultsummary` (
	`resultsummaryId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`resultId` INT(10) UNSIGNED NOT NULL,
	`messageId` VARCHAR(200) NULL DEFAULT NULL,
	`relatesTo` VARCHAR(200) NULL DEFAULT NULL,
	`req_transactionId` INT(11) NULL DEFAULT NULL,
	`res_transactionId` INT(11) NULL DEFAULT NULL,
	`req_sender_hcid` VARCHAR(255) NULL DEFAULT NULL,
	`req_receiver_hcid` VARCHAR(255) NULL DEFAULT NULL,
	`res_sender_hcid` VARCHAR(255) NULL DEFAULT NULL,
	`res_receiver_hcid` VARCHAR(255) NULL DEFAULT NULL,
	PRIMARY KEY (`resultsummaryId`),
	INDEX `fk_resultsummary_caseresult` (`resultId`),
	INDEX `fk1_resultsummary_transaction` (`req_transactionId`),
	INDEX `fk2_resultsummary_transaction` (`res_transactionId`),
	CONSTRAINT `fk_resultsummary_caseresult` FOREIGN KEY (`resultId`) REFERENCES `caseresult` (`resultId`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT = 1 ;
 
 
-- -----------------------------------------------------
-- View `vw_gateway`
-- -----------------------------------------------------
 
create view vw_gateway as
select  distinct communityId HCID, lower(ipAddress) gatewayAddress, participantName hostedBy, 0 labNode
from participant
union 
select distinct communityId HCID, lower(ipAddress) gatewayAddress, name hostedBy, 1 labNode
from testharnessri;

commit;
 
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

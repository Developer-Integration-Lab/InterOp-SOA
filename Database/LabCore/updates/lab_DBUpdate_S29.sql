-- -----------------------------------------------------
-- Sprint 26
-- -----------------------------------------------------
-- ILT-388 - Start ------------------------------------

SET AUTOCOMMIT=0;

ALTER TABLE `resultsummary` ADD COLUMN `responseFlag` VARCHAR(45) NULL  AFTER `relatesTo`;

-- ILT-388 - End ------------------------------------



-- ILT-423 - Start ------------------------------------

INSERT INTO `applicationproperties` (`propertyKey`, `propertyValue`,`description`) VALUES ('transaction.days', '5','Specify "N" to display data from last number of day(s) where N is an number.');

INSERT INTO `applicationproperties` (`propertyKey`, `propertyValue`,`description`) VALUES ('transaction.recordsperpage', '50','Specify "N" to display records per page where N is an number.');


-- ILT-423 - End ------------------------------------

-- ILT-421 - Start ----------------------------------

SET AUTOCOMMIT=0;

drop table IF EXISTS `patientcorrelation` ; 

CREATE TABLE `patientcorrelation` (
	`correlationId` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`resultId` INT(10) UNSIGNED NOT NULL,
	`patientId` VARCHAR(45) NULL DEFAULT NULL,
	`patientAssigningAuthorityId` VARCHAR(45) NULL DEFAULT NULL,
	`patientHomeCommunityId` VARCHAR(45) NULL DEFAULT NULL,
	`correlatedPatientId` VARCHAR(45) NULL DEFAULT NULL,
	`correlatedPatientAssignAuthId` VARCHAR(45) NULL DEFAULT NULL,
	`correlatedPatientHomeCommunityId` VARCHAR(45) NULL DEFAULT NULL,
	`correlated` CHAR(1) NULL DEFAULT NULL,
	`error` CHAR(1) NULL DEFAULT NULL,
	`status` VARCHAR(45) NULL DEFAULT NULL,
	`message` VARCHAR(2000) NULL DEFAULT NULL,
	`createdAt` DATETIME NULL DEFAULT NULL,
	`createdBy` VARCHAR(50) NULL DEFAULT NULL,
	`updatedAt` DATETIME NULL DEFAULT NULL,
	`updatedBy` VARCHAR(50) NULL DEFAULT NULL,
	PRIMARY KEY (`correlationId`),
	INDEX `fk_patientcorrelation_caseresult` (`resultId`),
	CONSTRAINT `fk_patientcorrelation_caseresult` FOREIGN KEY (`resultId`) REFERENCES `caseresult` (`resultId`) ON UPDATE NO ACTION ON DELETE NO ACTION
)
COMMENT='Lab patient correlation'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

COMMIT;
-- ILT-421 - End -----------------------------------


-- ILT-424 - Start ----------------------------------

ALTER TABLE `resultsummary` 
  ADD CONSTRAINT `fk_req_tran_id`
  FOREIGN KEY (`req_transactionId` )
  REFERENCES `transaction` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `fk_res_tran_id`
  FOREIGN KEY (`res_transactionId` )
  REFERENCES `transaction` (`id` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
, ADD INDEX `fk_req_tran_id` (`req_transactionId` ASC) 
, ADD INDEX `fk_res_tran_id` (`res_transactionId` ASC) ;

-- ILT-424 - End ----------------------------------

-- -----------------------------------------------------
-- Sprint 26
-- -----------------------------------------------------
-- ILT-365 - Start ------------------------------------

SET AUTOCOMMIT=0;

ALTER TABLE `transaction` ADD COLUMN `sender` VARCHAR(255) NULL  AFTER `server` , ADD COLUMN `receiver` VARCHAR(255) NULL  AFTER `sender` ;

drop view vw_gateway;

create view vw_gateway as
select  distinct communityId HCID, lower(ipAddress) gatewayAddress, participantName hostedBy, 0 labNode
from participant
union 
select distinct communityId HCID, lower(ipAddress) gatewayAddress, name hostedBy, 1 labNode
from testharnessri;

commit;

 -- ILT-315 - End ------------------------------------

 
 -- ILT-335 - Start ------------------------------------
 
 SET AUTOCOMMIT=0;
 -- DDL 
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
AUTO_INCREMENT=123;

-- DML 
ALTER TABLE `caseresult`
	ADD COLUMN `processedRIsCount` INT(11) NULL DEFAULT NULL AFTER `stacktrace`,	
	ADD COLUMN `createdAt` DATETIME NULL DEFAULT NULL AFTER `processedRIsCount`,
	ADD COLUMN `createdBy` VARCHAR(50) NULL DEFAULT NULL AFTER `createdAt`,
	ADD COLUMN `updatedAt` DATETIME NULL DEFAULT NULL AFTER `createdBy`,
	ADD COLUMN `updatedBy` VARCHAR(50) NULL DEFAULT NULL AFTER `updatedAt`;
	
commit;

 -- ILT-315 - End ------------------------------------

-- -----------------------------------------------------
-- Sprint 31
-- -----------------------------------------------------
-- ILT-389/446 (RD)- Start -------------------------------------

SET AUTOCOMMIT=0;

ALTER TABLE `resultdocument`	
	ADD COLUMN `messageType` VARCHAR(45) NULL DEFAULT NULL AFTER `rawData`,
	ADD COLUMN `status` VARCHAR(45) NULL DEFAULT NULL AFTER `messageType`,	
	ADD COLUMN `message` VARCHAR(2000) NULL DEFAULT NULL AFTER `status`,
	ADD COLUMN `createdAt` DATETIME NULL DEFAULT NULL AFTER `message`,
	ADD COLUMN `createdBy` VARCHAR(50) NULL DEFAULT NULL AFTER `createdAt`,
	ADD COLUMN `updatedAt` DATETIME NULL DEFAULT NULL AFTER `createdBy`,
	ADD COLUMN `updatedBy` VARCHAR(50) NULL DEFAULT NULL AFTER `updatedAt`;
	
COMMIT;

SET AUTOCOMMIT=0;
UPDATE `scenariocase` SET `dependentScenarioId`=1002, `dependentCaseId`=27 WHERE `scenarioId`='1003' and`caseId`='54';
COMMIT;

-- ILT-389/446 (RD) - End ---------------------------------------
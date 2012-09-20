-- -----------------------------------------------------
-- Sprint 30
-- -----------------------------------------------------
-- ILT-440 - Start -------------------------------------

SET AUTOCOMMIT=0;

ALTER TABLE `scenariocase`
	ADD COLUMN `displayByDependentCaseStatus` VARCHAR(1) NULL DEFAULT NULL AFTER `dependentCaseId`;

ALTER TABLE `caseexecution`
	ADD COLUMN `displayByDependentCaseStatus` VARCHAR(1) NULL DEFAULT NULL AFTER `dependentCaseId`;

-- Scenario 30 --
update `scenariocase` set dependentScenarioId=5 , dependentCaseId=72 , displayByDependentCaseStatus='Y' where scenarioId=30 and caseId=74;
	
COMMIT;

-- ILT-440 - End ---------------------------------------
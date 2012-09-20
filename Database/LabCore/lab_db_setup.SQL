DROP SCHEMA IF EXISTS `lab` ;
CREATE SCHEMA IF NOT EXISTS `lab` DEFAULT CHARACTER SET latin1 ;

USE `lab`;

source ./lab_DDL.sql;
source ./lab_DML.sql; -- Last synchronized at the end of Sprint 28.
source ./updates/lab_DBUpdate_S29.sql;
source ./updates/lab_DBUpdate_S30.sql;
source ./updates/lab_DBUpdate_S31.sql;
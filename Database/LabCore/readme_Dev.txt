Lab database setup instructions:
********************************
1) Open the lab_DML.sql file and set the properties if its different from default values.

***********************************************************************************************
user_userid			   - Variable to set lab application userid.
user_password 		   - Variable to set lab application password.

name_participant       - Variable to set participant name to be displayed in the application.
hostname_participant   - Variable to set participant hostname.
hcid_participant       - Variable to set participant home community id.
aaid_participant       - Variable to set participant assigning authority id.	
***********************************************************************************************

set @user_userid := 'test1'
set @user_password := 'test1'

set @name_participant := 'Test Participant One Inc'
set @hostname_participant := 'local.example.com'
set @hcid_participant := '2.16.840.1.113883.0.200'
set @aaid_participant := '2.16.840.1.113883.0.200'
	
set @hostname_RI1 := 'ri1.example.com';
set @hcid_RI1 := '2.16.840.1.113883.0.101';
set @aaid_RI1 := '2.16.840.1.113883.0.101';

set @hostname_RI2 := 'ri2..example.com';
set @hcid_RI2 := '2.16.840.1.113883.0.102';
set @aaid_RI2 := '2.16.840.1.113883.0.102';

set @hostname_RI3 := 'ri3.example.com';
set @hcid_RI3 := '2.16.840.1.113883.0.103';
set @aaid_RI3 := '2.16.840.1.113883.0.103';

set @hostname_RI1_Connect32 := 'ri1c32.example.com';
set @hcid_RI1_Connect32 := '2.16.840.1.113883.3.609.001';
set @aaid_RI1_Connect32 := '2.16.840.1.113883.3.609.001';

2) The default document id in lab_DML.sql file has "CAN.200." if you want to have user specific document id replace all the "CAN.200." with first three characters of your first name and last three digits of your HCID(ex: if name:JOHN , HCID:2.16.840.1.113883.0.207 = JOH207).

3) The default patient id in lab_DML.sql file has "CAN200" if you want to have user specific patient id replace all the "CAN200" with first three characters of your first name and last three digits of your HCID(ex: if name:JOHN , HCID:2.16.840.1.113883.0.207 = JOH207).

4) Open command prompt and issue the following commands (Note that since the MySQL install has made changes to the path environment variable, it will be necessary to open a new command window.):

	cd <NwHIN_software_install_dir>\Database\labcore

	mysql -v -uroot -pNHIE-Gateway < lab_data_setup.sql

Participant Docrepository - Load patient documents:
***************************************************
1) The default document id in Participant_patient_docs.sql file has "CAN.200." if you want to have user specific document id replace all the "CAN.200." with first three characters of your first name and last three digits of your HCID(ex: if name:JOHN , HCID:2.16.840.1.113883.0.207 = JOH207).

2) Open command prompt and issue the following commands (Note that since the MySQL install has made changes to the path environment variable, it will be necessary to open a new command window.):

	cd <NwHIN_software_install_dir>\Database

	mysql -v -uroot -pPASSWORD < Participant_patient_docs.sql
 
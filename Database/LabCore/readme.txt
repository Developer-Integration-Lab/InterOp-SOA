Lab database setup instructions:
********************************
1) Open the lab_DML.sql file and set the properties given below:

************************************************************************************************************************
user_userid			   - Variable to set lab application userid.
user_password 		   - Variable to set lab application password.

name_participant       - Variable to set participant name to be displayed in the application.
hostname_participant   - Variable to set participant hostname.
hcid_participant       - Variable to set participant home community id.
aaid_participant       - Variable to set participant assigning authority id.
	
hostname_RI1    	   - Variable to set hostname of refrence implementation 1 (Connect version - 2.4.8)	
hcid_RI1        	   - Variable to set home community id of refrence implementation 1 (Connect version - 2.4.8)
aaid_RI1       		   - Variable to set assigning authority Id of refrence implementation 1 (Connect version - 2.4.8)

hostname_RI2    	   - Variable to set hostname of refrence implementation 2 (Connect version - 2.4.8)	
hcid_RI2        	   - Variable to set home community id of refrence implementation 2 (Connect version - 2.4.8)
aaid_RI2       		   - Variable to set assigning authority Id of refrence implementation 2 (Connect version - 2.4.8)

hostname_RI3    	   - Variable to set hostname of refrence implementation 3 (Connect version - 2.4.8)	
hcid_RI3        	   - Variable to set home community id of refrence implementation 3 (Connect version - 2.4.8)
aaid_RI3       		   - Variable to set assigning authority Id of refrence implementation 3 (Connect version - 2.4.8)

hostname_RI1_Connect32 - Variable to set hostname of refrence implementation 1 (Connect version - 3.2)
hcid_RI1_Connect32 	   - Variable to set home community id of refrence implementation 1 (Connect version - 3.2)
aaid_RI1_Connect32 	   - Variable to set assigning authority Id of refrence implementation 1 (Connect version - 3.2)
*************************************************************************************************************************

set @user_userid := 'test1';
set @user_password := 'test1';
set @name_participant := 'Test Participant One Inc';
set @hostname_participant := 'local.example.com' ;
set @hcid_participant := 'hcid_participant';
set @aaid_participant := 'aaid_participant';

set @hostname_RI1 := 'ri1.example.com';
set @hcid_RI1 := 'hcid_RI1';
set @aaid_RI1 := 'aaid_RI1';

set @hostname_RI2 := 'ri2.example.com';
set @hcid_RI2 := 'hcid_RI2';
set @aaid_RI2 := 'aaid_RI2';

set @hostname_RI3 := 'ri3.example.com';
set @hcid_RI3 := 'hcid_RI3';
set @aaid_RI3 := 'aaid_RI3';

set @hostname_RI1_Connect32 := 'ri1c32.example.com';
set @hcid_RI1_Connect32 := 'hcid_RI1_Connect32';
set @aaid_RI1_Connect32 := 'aaid_RI1_Connect32';

2) Open command prompt and issue the following commands (Note that since the MySQL install has made changes to the path environment variable, it will be necessary to open a new command window.):

	cd <NwHIN_software_install_dir>\Database\labcore
	
	mysql -v -uroot -pPASSWORD < lab_data_setup.sql


 
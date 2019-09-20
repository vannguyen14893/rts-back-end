# CMC_RTS
		Require: 	java >=8
					mysql > 5.6
					tomcat 8 
					using post man test api
# run script SQL
	Don't forget . You must import newest_base_by_Quy.sql and run script to mysql.	
	
# Take Note 
	Currently with all file unit test doesn't work.
	You must comment all file test first run build project.
			
# C1: Import and install project with Eclipe
	Step 1: Import and Install
	Step 2: Clone project 
				http://101.99.14.196:3030/ldthien/recruitment-tracking-2.git
	Step 3: Import maven project. Using eclipse. You should use spring tool suite.
	Step 4: Change username and pasword mysql in file application.properties.
	Step 5: Install project using maven.
	Step 6: Get file WAR in folder Target
# C2: Install project with Command line
	Step 1 : Clone project
		http://101.99.14.196:3030/ldthien/recruitment-tracking-2.git
	Step 2: 
		mvn clean install
	Step : Get file WAR in folder Target	
# Deploy to Server tomcat
	
	Step 1: Copy file war get from folder Target to folder webapps of Tomcat
	Step 2: Reconfig file server.xml in folder config.
			
			In tag <Host> </Host> we will insert and define path resources for images and files.
			  <!-- Config public resource folder -->
  			<Context docBase="../bin/src/main/resources/public" 				path="/CMC_Recruitment-0.0.1-SNAPSHOT/public" />

	
# Testing			
	Step 3: Testing --> Using PostMan
			Get token
			localhost:8082/api/oauth/token?username=admin&password=12345678&grant_type=password  --> Method get
			variable: grant_type=password , username=admin, password=12345678
			Basic Auth: Username = myclientid , Password=secret.
			
# Some api 
	Logout funtion: http://localhost:8082/api/oauth/logout 
		Method: Post
		Header: Authorization: Bearer value of AcsessToken.
		
	Refresh Token: localhost:8082/api/oauth/token
		Method: Post
		Basic Auth: Auth: Username = myclientid , Password=secret.
		Body: grant_type=refresh_token , refresh_token= value of refresh_token
		
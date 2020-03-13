This is a password validation application

Download the project folder

Open terminal
1) cd to the directory, validator
2) command to launch the app --> ./gradlew bootRun
3) Application launches on default port 8080


POSTMAN collection url:https://www.getpostman.com/collections/005a9a54aac41caf1c0d


Use the postman collection shared in this folder or follow these steps

1) Note that my application accepts only encrypted password. So, use this endpoint
   to generated an encrypted text http://localhost:8080/encrypt_password/youpassword

2) Grab the encrypted password from step (1) and use it to hit the following GET endpoint
	a. http://localhost:8080/validate/password
	b. Request body json format
	{
	"password" : "TGrboaZYZWP9V/hrCRml+g=="
        }

# Server_App_MVP
Server Application for MVP Vivt
---
Start: create an android application that can: generate a QR-code, display any news from the site (small, they can be opened in the application).
Registration is as follows: download the application, everything works, if the user needs to transfer his account, etc., then he can set a password, email, phone

What are my thoughts:
Make up shell with no-code tools in android sutdio,
then write a server with the following logic:
* All work on http - https: // site / api
* /api/news - here data not ambiguous json / json with link

* /api/registration - here - it will return the token, for further work with the server, it will need to be saved on the client,
* /api/setPersonDate - password set, nickname change, etc.
* /api/resetPassword - "GET token" for reste password
* /api/resetPassword - "GET email" send email on accounts
* /api/getStatusToken - check status token
* /api/qrCode - get qr code on token

the file with the settings for mail has been sent to .gitignore,
since it is not safe, in any case, I will say, it worked through mail ru,
to send a notification you have to set up your mail a little, mail.properties looks like the following

````
username=eamil@email.com
password=pass
````

The settings for the tests were also placed in a separate file, I don't want to receive spam
test.properties

````
user.test.Token=bdToken
user.test.Email=BdEmail@test.com
user.test.password=passTest
````

There is a database in the project files, you only need to import it

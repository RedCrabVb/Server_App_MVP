# Server_App_MVP
Server Application for MVP Vivt

Start: create an android application that can: generate a QR-code, display any news from the site (small, they can be opened in the application).
Registration is as follows: download the application, everything works, if the user needs to transfer his account, etc., then he can set a password, email, phone

What are my thoughts:
Make up shell with no-code tools in android sutdio,
then write a server with the following logic:
* All work on http - https: // site / api
* add api / reg here - it will return the token, for further work with the server, it will need to be saved on the client,
* api / new - here is json (header, body, img), then you still need to make the qr code, it will be constant, you will need to take it only once from the database (from the customer, I don’t know why once)
* api / lk - password set, nickname change, etc.
* well, changing the password, ease of registration - we will somehow fix the security problems


the file with the settings for mail has been sent to .gitignore, 
since it is not safe, in any case, I will say, it worked through mail ru, 
to send a notification you have to set up your mail a little, mail.properties looks like the following
---
username=eamil@email.com
password=pass
---

The settings for the tests were also placed in a separate file, I don't want to receive spam
test.properties
---
user.test.Token=bdToken
user.test.Email=BdEmail@test.com
user.test.password=passTest
---

There is a database in the project files, you only need to import it

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
# Server_App_MVP
Server Application for MVP Vivt
---
Веб-сервер для организации интерактивных квестов. 
Приложение в данные момент может:
* регистрировать и авторизовывать пользователей
* создавать тест, управлять ими
* просматривать результаты тестов
##api
* REST api - https: // site / api
* GET /api/news - вернет ссылку на новостной источник

* POST /api/registration () - здесь вернет токен, для дальнейшей работы с сервером его нужно будет сохранить на клиенте
* POST /api/authorization (email, password) - получить данные об аккунте
* POST /api/setPersonDate (token, password, email, username)- установка пароля, смена ника и т. д.
* POST /api/resetPassword/email  (email) - "GET token" для сброса пароля
* GET /api/resetPassword/token (token) - "GET email" отправит сообщение
* GET /api/qrCode (token) - получить QR-код по токену
* GET /api/getStatusToken (token) - получить статус токен (true/false)

* GET /api/testAll - return json array test
* GET /api/test (id) - return json test
* GET /api/getHashAnswer (question, answer) - получить хеш вопроса
* GET /api/saveResultTest (map(token, idTest, time, countRightAnswer, jsonAnswer))

##Install
Для работы приложения нужна java 17. БД работает с базой данных postgres, база данных генерируеться по коду.

Для изменения параметров приложения во время запуска, без перкомпляции достаточно указать значение в args
```shell
    java -jar server.jar --server-port=8082 --mail.properties=mail.properties
```
Данные для подключения к smtp серверу нужно указать данные для подключения отдельно. Пример файла конфиграции в ресурсах (mail.properties).


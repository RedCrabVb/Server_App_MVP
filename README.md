# Server_App_MVP
Server Application for MVP Vivt
---
тарт: создать андроид-приложение, которое умеет: генерировать QR-код, отображать любые новости с сайта (маленькие, их можно открывать в приложении).
Регистрация происходит следующим образом: скачать приложение, все работает, если пользователю нужно перенести аккаунт и т. Д., То он может установить пароль, почту, телефон.
Что я думаю:
Создайте оболочку с инструментами без кода в студии Android,
затем напишите сервер со следующей логикой:
##old api
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

web:
* /testCreator - страниц для создания теста, валидность данных проверяться с помощью токена
* /testAdd - GET запрос, которые добавляет данные в БД


Файл с настройками для почты отправлен на .gitignore,
так как это небезопасно, в любом случае скажу, через mail ru работало,
чтобы отправить уведомление, вам нужно немного настроить свою почту, mail.properties выглядит следующим образом

````
username=eamil@email.com
password=pass
````

hibernate.properties - файлы для базы данных

```
hibernate.connection.driver_class=com.mysql.jdbc.Driver
hibernate.connection.url=jdbc:mysql://localhost:3306/MvpDB?useSSL=false&serverTimezone=UTC
hibernate.connection.username=username
hibernate.connection.password=password
hibernate.c3p0.min_size=5
hibernate.c3p0.max_size=20
hibernate.c3p0.timeout=1800
hibernate.c3p0.max_statements=50
hibernate.dialect=org.hibernate.dialect.MySQLDialect
hibernate.show_sql=false

```

Сам файл с БД лежит в src/main/resource/MvpDB.sql

Запуск сервера, mysql должен быть запущен
```
java -jar -Dserver.port=8082  
-Dmail.properties=mail.properties 
-Dhibernate.properties=hibernate.properties server-*-SNAPSHOT.jar
```
Стандартный токен администратора `abc`, может быть изменен при запуске, следующим параметром `-Dadmin.token=rrr`

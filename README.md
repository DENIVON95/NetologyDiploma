# <p align="center"> Дипломный проект профессии «Тестировщик ПО» </p>
### <p align="left"> Дипломный проект представляет собой автоматизацию тестирования [комплексного сервиса](https://github.com/netology-code/qa-diploma), взаимодействующего с СУБД и API Банка. </p>
### <p align="left"> База данных хранит информацию о заказах, платежах, статусах карт, способах оплаты. Приложение предлагает купить тур по определённой цене с помощью двух способов: </p>
* Обычная оплата по дебетовой карте
* Уникальная технология: выдача кредита по данным банковской карты
### Данные по картам обрабатываются отдельными сервисами:
* Сервис платежей - Payment Gate
* Кредитный сервис - Credit Gate
##  <p align="center"> Инструменты, которые были использованы </p>
| IntelliJ IDEA | Gradle | Java | Junit5 | Selenide | Docker | Allure Report |
|:------:|:----:|:----:|:------:|:------:|:--------:|:-------------:|
| <img src="images/Intellij.svg" width="40" height="40"> | <img src="images/Gradle.svg" width="40" height="40"> | <img src="images/Java.svg" width="40" height="40"> | <img src="images/Junit5.svg" width="40" height="40"> | <img src="images/Selenide.svg" width="40" height="40"> | <img src="images/docker.svg" width="40" height="40"> | <img src="images/Jenkins.svg" width="40" height="40"> | <img src="images/Allure Report.svg" width="40" height="40"> |
---
## <p align="center"> Подготовка и запуск приложения </p>
1. Для начала работы приложения необходимо запустить одну из доступных баз данных, параметры для запуска которых хранятся в файле **docker-compose.yml**. 
- Для запуска необходимо ввести в терминале команду: **docker-compose up**

2. В новой вкладке терминала ввести следующую команду в зависимости от базы данных:
- **java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar** - для субд MySQL
- **java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar** - для субд PostgreSQL
  
<br> **Приложение должно запуститься по адресу http://localhost:8080/** </br> 
## <p align="center"> Запуск тестов </p>
В новой вкладке терминала ввести команду в зависимости от запущенной ранее субд:
- **gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app** - для MySQL
- **gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app** - для PostgreSQL

## <p align="center"> Формирование Allure отчета </p>
Для формирования отчета по результатам прохожедния тестов необходимо в новой вкладке терминала ввести команду: 
- **gradlew allureServe**
## <p align="center">Проектная документация</p>
- [План автоматизации тестирования веб-сервиса](documentation/Plan.md)
- [Отчет о проведенном тестировании](documentation/Report.md)
- [Отчет по итогам автоматизации](documentation/Summary.md)

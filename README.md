#  CountryIndicators_2015_Project  #
Итоговый проект по Java, вариант 5
***
#  Ход работы  #

Проанализировал CSV файл и создал сущность для хранения его данных (класс CountryIndicators)

Распарсил CSV файл(класс Parser) как массив из CountryIndicators, не было пустых полей 

Создал БД в SQLite и подключил её к проекту с помощью библиотеки sqlite-jdbc-3.36.0.3

Создал в БД таблицу CountryIndicators, которая соответствует третьей нормальной форме(есть метод в DbHandler)

Создал класс DbHandler для взаимодействий с БД, с его помощью заполнил БД данными, полученными из парсера, также есть метод для извлечение записей из таблицы CountryIndicators, которая находится в БД

Создал класс TaskHandler, который имеет 3 метода для решения задач, каждый метод которого принимает объект DBHandler. Также есть приватные методы, но они нужны для создания представлений, из которых мы потом и вытягиваем определенные данные

Задание 1
![alt text](https://github.com/sheka00/CountryIndicators_2015_Project/blob/main/images/1.JPG)
Задание 3 и 2
![alt text](https://github.com/sheka00/CountryIndicators_2015_Project/blob/main/images/3%2C2.JPG)


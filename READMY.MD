

**insert**
Команда для вставки одной строки в заданную таблицу
    **Формат**: insert | tableName  затем 
            insertVelues| column1 | value1 | column2 | value2 | ... | columnN | valueN
    `где:`
    tableName - имя таблицы
    column1 - имя первого столбца записи
    value1 - значение первого столбца записи
    column2 - имя второго столбца записи
    value2 - значение второго столбца записи
    columnN - имя n-го столбца записи
    valueN - значение n-го столбца записи
    
    **UserSTORY Вставка**
    1. Я как Юзер хочу выбрать таблицу(получить уведомление что таблица выбрана 
    успешно и она существует) и получить внешний вид таблицы.
     Затем ввести команду insertVelues|column1|value1|column2|value2| ... | columnN | valueN
         **_Use Cases:_**
       1.1 Я как User ввожу команду Insert|tableName(tableName -существует и ввод коректен)-> 
           -> получаю уведомление что ввод коректен и "карту таблицы"(т.е. название полей и порядок их вставки в последующую команду)
           -> ввожу insertVelues|column1|value1|column2|value2| ... | columnN | valueN->
           -> получаю уведомление что вставка прошла успешно.
        
    2...

Создать базу данных: CREATE DATABASE [databasename];
 
Вывести список всех баз данных: SHOW DATABASES;
 
Выбрать определённую базу данных для работы: USE [db name];
 
Вывести все таблицы в выбранной базе: SHOW TABLES;
 
Вывести формат таблицы: DESCRIBE [table name];
 
Удалить базу: DROP DATABASE [database name];
 
Удалить таблицу из базы: DROP TABLE [table name];
 
Вывести всю таблицу: SELECT * FROM [table name];
 
Вывести столбцы с их описанием: SHOW COLUMNS FROM [table name];
 
Отобразить строки в определенной таблице, содержащие “toyota“: SELECT * FROM [table name] WHERE [field name] = “toyota”;
 
Отобразить все записи в таблице, содержащие “BMW” и тип авто мобиля "pikap": SELECT * FROM [table name] WHERE car_model = “BMW” AND car_type = ‘pikap‘;
 
Отобразить все записи, НЕ содержащие модель “BMW” и тип авто “pikap“, отсортированные по полю car_type: SELECT * FROM [table name] WHERE car_model != “BMW” AND car_type = ‘pikap‘ order by car_type;
 
Показать все записи, начинающиеся с букв ‘fo” и типа “sedan” : SELECT * FROM [table name] WHERE car_model like “fo%” AND car_type = ‘sedan‘;
 
Показать все записи, начинающиеся с букв ‘fo” и типа “sedan“, ограничиваясь записями с 1-ой до 5-ой: SELECT * FROM [table name] WHERE car_model like “fo%” AND car_type = ‘sedan‘ limit 0,5;
 
У автомобилей марки LADA сменить поле состояния на bad: UPDATE [table name] SET car_condition='bad' WHERE car_model='LADA';
 
Удалить все записи мотоциклов из таблицы transports: DELETE FROM transports WHERE transport_type='motorcycle';
 
Вставить в таблицу cars в соответствующие столбцы значения: INSERT INTO cars (car_model,car_color,car_price) values ('Opel','Yellow',4430,21), ('BMW','Black',95000);
 
Выбрать из таблицы transport технику с ценой от 10000 до 25000: SELECT * FROM transport WHERE tran_price BETWEEN 10000 AND 25000;
 
Выбрать из таблицы cars автомобили, входящие в множкство BMW и Mazda: SELECT * FROM cars WHERE car_model IN ('BMW', 'Mazda');
 
У всех Chevrolet Orlando понизить цену на 1000 и установить состояние 'Achtung' :
UPDATE cars SET car_condition = 'Achtung' , car_price=car_price-1000 WHERE car_model = 'Chevrolet Orlando';
Показать код, создавший таблицу: SHOW CREATE TABLE <tablename>;
 
Показать последние предупреждения: SHOW WARNINGS;
 
Добавляем столбец, устанавливаем его первичным ключом таблицы: 
ALTER TABLE cars ADD COLUMN car_id INT NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY(car_id);
Переименовать таблицу cars в cars_list: ALTER TABLE cars RENAME TO cars_list;
 
Взять первые 3 символа модели авто и записать их в столбец car_key: UPDATE cars SET car_key = LEFT(car_model,3);
 
Вывести описание автомобилей до третий запятой: SELECT SUBSTRING(car_description, ',' ,3) FROM cars;
 
Заполнить столбцы страны в соответствии с маркой авто: 
UPDATE cars SET country = 
CASE
WHEN BMW = 'Y' THEN 'Германия'
WHEN LADA = 'Y' THEN 'Россия'
WHEN Toyota = 'Y' THEN 'Япония'
ELSE 'неизвестно'
END;
 
Создать таблицу с первичным, внешним ключами и ограничением: 
CREATE TABLE description_car (
desc_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
description VARCHAR(50) NOT NULL, car_id_fk INT NOT NULL,
CONSTRAINT car_car_id_fk 
FOREIGN KEY (car_id_fk) REFERENCES car (car_id)
);
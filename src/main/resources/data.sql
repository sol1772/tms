INSERT INTO User_
SELECT 1,
       'Администратор',
       'admin@mail.com',
       '$2a$10$K6KD7OjZK8HCeVPRBA2m6OjNy8VLdXz71HMvyoK.GJOiPJztGoSb.',
       'ADMIN',
       now()
WHERE NOT EXISTS(SELECT id FROM User_ WHERE id = 1);
INSERT INTO User_
SELECT 2,
       'Загитова Алина',
       'info@alinazagitova.ru',
       '$2a$10$ApBtGXv16XEsHStHoGJvAOAocxfKoEP.ZXCVmW9DGqrc0vsehlU/i',
       'USER',
       now()
WHERE NOT EXISTS(SELECT id FROM User_ WHERE id = 2);
INSERT INTO User_
SELECT 3,
       'Петров Дмитрий',
       'dpetrov@mail.com',
       '$2a$10$wZ9I5cYgfMKWBU6EONB3hOvmudJe/Z97lTv9ciIZW.BJZCbDmVaFy',
       'USER',
       now()
WHERE NOT EXISTS(SELECT id FROM User_ WHERE id = 3);
INSERT INTO User_
SELECT 4, 'System admin', 'sa@mail.com', '$2a$10$caaKF/MP3VOIB7lEMD5mc.jzruQZhkOocBzJhr3y0MKxCL/qqSKbi', 'ADMIN', now()
WHERE NOT EXISTS(SELECT id FROM User_ WHERE id = 4);
INSERT INTO User_
SELECT 5,
       'Java разработчик',
       'javadev@mail.com',
       '$2a$10$zzDFK2wVpupa4/MzNqc/z.m.OuEumA77l18HBJjmTB7sg7BYu7W8u',
       'USER',
       now()
WHERE NOT EXISTS(SELECT id FROM User_ WHERE id = 5);
INSERT INTO User_
SELECT 6, 'Tms user', 'tms@mail.com', '$2a$10$Wjoy5oGqME3K5sHXDz3VEeGjA3A.xCpBgtn8vcG9Q9dZaejF6ZY6K', 'USER', now()
WHERE NOT EXISTS(SELECT id FROM User_ WHERE id = 6);
INSERT INTO User_
SELECT 7,
       'Тестовый пользователь',
       'test@mail.com',
       '$2a$10$LUkFY2QQ2J8zNw4TU5pmyuxyozJMld/aYSIf8dbYMmo1G48jDSMHS',
       'USER',
       now()
WHERE NOT EXISTS(SELECT id FROM User_ WHERE id = 7);

INSERT INTO Task
SELECT 1,
       'Тестовая задача',
       'Проверить систему управления задачами',
       'PENDING',
       'HIGH',
       1,
       6,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 1);
INSERT INTO Task
SELECT 2,
       'Провести занятие',
       'Провести занятие по java в группе',
       'PENDING',
       'MID',
       1,
       5,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 2);
INSERT INTO Task
SELECT 3,
       'Мастер-класс',
       'Провести мастер-класс',
       'PENDING',
       'MID',
       2,
       2,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 3);
INSERT INTO Task
SELECT 4,
       'Обучение',
       'Провести лекцию в группе',
       'PENDING',
       'MID',
       3,
       3,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 4);
INSERT INTO Task
SELECT 5,
       'Тестовая задача',
       'Тестовая задача',
       'PENDING',
       'LOW',
       6,
       7,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 5);
INSERT INTO Task
SELECT 6,
       'Postgresql',
       'Установить Postgresql',
       'PENDING',
       'MID',
       1,
       4,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 6);
INSERT INTO Task
SELECT 7,
       'Аутентификация',
       'Сервис должен поддерживать аутентификацию и авторизацию пользователей по email и паролю',
       'PENDING',
       'HIGH',
       5,
       5,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 7);
INSERT INTO Task
SELECT 8,
       'JWT',
       'Доступ к API должен быть аутентифицировать с помощью JWT токена',
       'PENDING',
       'MID',
       5,
       5,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 8);
INSERT INTO Task
SELECT 9,
       'Управление задачами в TMS',
       'Пользователи могут управлять своими задачами: создавать новые, редактировать существующие, просматривать и удалять, менять статус и назначать исполнителей задачи',
       'PENDING',
       'MID',
       5,
       5,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 9);
INSERT INTO Task
SELECT 10,
       'Статусы задач',
       'Пользователи могут просматривать задачи других пользователей, а исполнители задачи могут менять статус своих задач',
       'PENDING',
       'MID',
       5,
       5,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 10);
INSERT INTO Task
SELECT 11,
       'Комментарии к задачам',
       'К задачам можно оставлять комментарии',
       'PENDING',
       'MID',
       5,
       5,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 11);
INSERT INTO Task
SELECT 12,
       'Фильтр и пагинация',
       'API должно позволять получать задачи конкретного автора или исполнителя, а также все комментарии к ним. Необходимо обеспечить фильтрацию и пагинацию вывода',
       'PENDING',
       'MID',
       5,
       5,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 12);
INSERT INTO Task
SELECT 13,
       'Обработка ошибок и валидация',
       'Сервис должен корректно обрабатывать ошибки и возвращать понятные сообщения, а также валидировать входящие данные',
       'PENDING',
       'LOW',
       5,
       5,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 13);
INSERT INTO Task
SELECT 14,
       'Документация Open API и Swagger',
       'Сервис должен быть хорошо задокументирован. API должнен быть описан с помощью Open API и Swagger. В сервисе должен быть настроен Swagger UI. Необходимо написать README с инструкциями для локального запуска проекта. Дев среду нужно поднимать с помощью docker compose',
       'PENDING',
       'MID',
       5,
       5,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 14);
INSERT INTO Task
SELECT 15,
       'Unit-тесты',
       'Напишите несколько базовых тестов для проверки основных функций вашей системы',
       'PENDING',
       'LOW',
       5,
       5,
       now()
WHERE NOT EXISTS(SELECT id FROM Task WHERE id = 15);

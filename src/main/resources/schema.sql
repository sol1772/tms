DO
'
DECLARE
BEGIN
IF NOT EXISTS(SELECT 1 FROM pg_type WHERE typname = ''role'') THEN
    CREATE TYPE role AS ENUM (''ADMIN'', ''USER'');
    END IF;
IF NOT EXISTS(SELECT 1 FROM pg_type WHERE typname = ''stage'') THEN
    CREATE TYPE stage AS ENUM (''PENDING'', ''PROGRESS'', ''COMPLETED'');
    END IF;
IF NOT EXISTS(SELECT 1 FROM pg_type WHERE typname = ''priority'') THEN
    CREATE TYPE priority AS ENUM (''HIGH'', ''MID'', ''LOW'');
    END IF;
END;
';

CREATE TABLE IF NOT EXISTS User_
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(50) NOT NULL,
    email        VARCHAR(50) NOT NULL UNIQUE,
    passwordHash VARCHAR(128),
    role         role,
    registeredAt TIMESTAMP
);
CREATE TABLE IF NOT EXISTS Task
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    descr       VARCHAR(512),
    stage       stage        NOT NULL,
    priority    priority,
    authorId    INT          NOT NULL,
    performerId INT,
    createdAt   TIMESTAMP
);
CREATE TABLE IF NOT EXISTS Comment_
(
    id         SERIAL PRIMARY KEY,
    txtContent VARCHAR(512) NOT NULL,
    userId     INT          NOT NULL,
    taskId     INT          NOT NULL
);
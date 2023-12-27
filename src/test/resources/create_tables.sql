CREATE TABLE IF NOT EXISTS User_
(
    id           INT AUTO_INCREMENT,
    username     VARCHAR(50) NOT NULL,
    email        VARCHAR(50) NOT NULL UNIQUE,
    passwordHash VARCHAR(128),
    registeredAt TIMESTAMP,
    role         ENUM ('ADMIN', 'USER'),
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS Task
(
    id          INT AUTO_INCREMENT,
    title       VARCHAR(100) NOT NULL,
    descr       VARCHAR(512),
    stage       ENUM ('PENDING', 'PROGRESS', 'COMPLETED'),
    priority    ENUM ('HIGH', 'MID', 'LOW'),
    authorId    INT          NOT NULL,
    performerId INT,
    createdAt   TIMESTAMP,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS Comment_
(
    id         INT AUTO_INCREMENT,
    txtContent VARCHAR(512) NOT NULL,
    userId     INT          NOT NULL,
    taskId     INT          NOT NULL,
    PRIMARY KEY (id)
);
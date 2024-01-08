DROP DATABASE IF EXISTS `JDBC_AM`;
CREATE DATABASE `JDBC_AM`;
USE `JDBC_AM`;

CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

SHOW TABLES;

SELECT *
FROM article
ORDER BY id DESC;


SELECT *
FROM article
ORDER BY id DESC;

INSERT INTO article SET regDate = NOW(),updateDate = NOW(),title = CONCAT('제목', RAND()),`body` = CONCAT('내용', RAND());

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목', RAND()),
`body` = CONCAT('내용', RAND());

UPDATE article
SET updateDate = NOW(),
title = 'abc'
WHERE id = 6;
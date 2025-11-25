DROP TABLE IF EXISTS message;

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id         integer PRIMARY KEY,
    username   varchar(64)  NOT NULL UNIQUE,
    email      varchar(255) NOT NULL UNIQUE,
    first_name varchar(64),
    last_name  varchar(64),
    password   varchar(255),
    age        integer      NOT NULL
);

CREATE TABLE IF NOT EXISTS message
(
    id           integer PRIMARY KEY,
    id_sender    integer REFERENCES users (id),
    id_recipient integer REFERENCES users (id),
    message      text NOT NULL,
    created_at   timestamp DEFAULT NOW()
);


INSERT INTO users(id, username, email, first_name, last_name, password, age)
VALUES (1, 'user1', 'klepeshkin@mail.ru', 'Konstantin', 'Lepeshkin', 'qwerty', 18),
       (2, 'user2', 'nuskov@mail.ru', 'Nikita', 'Uskov', 'qwerty', 25),
       (3, 'user3', 'cherepok@mail.ru', 'Oleg', 'Cherpanov', 'qwerty', 23);

INSERT INTO message(id, id_sender, id_recipient, message)
VALUES (1, 1, 2, 'Привет, друг! Как твои делишки?'),
       (2, 2, 1, 'Привет, отлично! Как ты сам?'),
       (3, 1, 2, 'Да так развлекаюсь, Паша интересные задачки подкинул, вот сижу тут развлекаюсь!'),
       (4, 2, 1, 'Понял тебя, держись там.'),
       (5, 3, 1, 'Привет, а вы про меня совсем забыли?');

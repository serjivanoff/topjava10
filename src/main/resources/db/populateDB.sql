DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO meals(user_id, description, calories,date_time) VALUES (100000,'завтрак',1500,'2017-04-19 09:21:00');
INSERT INTO meals(user_id, description, calories,date_time) VALUES (100001,'обед',1500,'2017-04-19 09:22:00');
INSERT INTO meals(user_id, description, calories,date_time) VALUES (100001,'ужин',100,'2017-04-19 00:59:00');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

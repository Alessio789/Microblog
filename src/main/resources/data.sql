INSERT INTO user (username, email, password, roles) VALUES
('Alessio',	'alessio.trentin3@gmail.com',	'$2a$10$oAa/xcO/vODrrRKjy4uIK.Bn77XgPTZKNnbxOe/EjZtBqGO3Lz8um',	'ADMIN'),
('TestUser', 'test@user.com', '$2a$10$IBfz7SemInEeUZ77S72vPuIancOmckRiUMqBHnGTmjar5HKY9OFFC', 'USER');

INSERT INTO post (id, body, date_hour, title, user_username) VALUES
(1,	'Hi, this is a test post',	'2020-04-25 12:45:12.859', 'Test', 'Alessio'),
(2,	'This is a Post',	'2020-04-25 13:45:12.859', 'Post', 'Alessio');

INSERT INTO comment (id, body, date_hour, post_id, user_username) VALUES
(1,	'Hi, this is a test comment', '2020-04-25 13:07:00.61',	1, 'TestUser'),
(2,	'Hi', '2020-04-25 14:07:00.61',	1, 'TestUser');


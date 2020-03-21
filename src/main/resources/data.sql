
INSERT INTO user (username, email, password, role, salt) VALUES
('Alessio', 'alessio.trentin3@gmail.com', '03da8881bf08561c3e0d3f88bb86920956faa69ed84fe81093b6ef5592cbbc88', 'ADMIN', 'c/vZylHXCG1VardmjmLMhw=='),
('TestUser', 'prova@example.com', '25bf926124fd43cf8a12ecf955d535781531c5617e06086ddd3305f390d9a944', 'USER', '9UDjgg8kZqoZXyiSczYq0w==');

INSERT INTO post (id, body, date_hour, title, user_username) VALUES
(1, 'This is a test post', '2020-03-19 15:17:08.981', 'Test', 'Alessio');


INSERT INTO comment (id, body, date_hour, post_id, user_username) VALUES
('1', 'Hi, this is a test comment', '2020-03-19 18:07:14.09', '1', 'TestUser');

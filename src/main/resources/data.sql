
INSERT INTO utente (username, email, password, ruolo, salt) VALUES
('Alessio', 'alessio.trentin3@gmail.com', '03da8881bf08561c3e0d3f88bb86920956faa69ed84fe81093b6ef5592cbbc88', 'ADMIN', 'c/vZylHXCG1VardmjmLMhw=='),
('UtenteProva', 'prova@example.com', '25bf926124fd43cf8a12ecf955d535781531c5617e06086ddd3305f390d9a944', 'USER', '9UDjgg8kZqoZXyiSczYq0w==');

INSERT INTO post (id, contenuto, data_ora, titolo, utente_id) VALUES
(1, 'Questo è un post di prova', '2020-03-19 15:17:08.981', 'Prova', 'Alessio');


INSERT INTO commento (id, contenuto, data_ora, post_id, utente_id) VALUES
('1', 'Ciao, questo è un commento di prova', '2020-03-19 18:07:14.09', '1', 'UtenteProva');

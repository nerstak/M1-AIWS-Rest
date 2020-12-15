INSERT INTO city (id_city, name_city) VALUES 
	(1,'Paris'),
	(2,'Lyon'),
	(3,'Marseille'),
	(4,'Aix-en-Provence'),
	(5,'Les Sables'),
	(6,'Bordeaux');
	
INSERT INTO theater (id_theater, id_city, name_theater) VALUES
	(1,1,'MK2 Bibliothèque'),
	(2,1,'UGC Les Halles'),
	(3,1,'Pathé Les Gobelins'),
	(4,2,'Pathé Bellecour'),
	(5,2,'UGC Cité Internationale'),
	(6,2,'UGC Lyon Part Dieu'),
	(7,3,'Le Prado'),
	(8,3,'Pathé Madeleine'),
	(9,4,'Le Cézanne'),
	-- We let Les Sables empty, just to see
	(10,6,'Mégarama'),
	(11,6,'CGR Le Français');
	
INSERT INTO movie (id_movie, title, duration, min_age, director) VALUES 
	(1,'Joker', '2h02',12,'Todd Phillips'),
	(2,'Tenet', '2h30',0,'Christopher Nolan'),
	(3,'Twilight','2h10',12,'Catherine Hardwicke'),
	(4,'GoodFellas','2h21',16,'Martin Scorsese'),
	(5,'Alien','1h57',12,'Ridley Scott'),
	(6,'Knives Out','2h10',0,'Rian Johnson');
	
	
INSERT INTO actor (id_actor, name_actor) VALUES
	(1, 'Joaquin Phoenix'),
	(2, 'Robert De Niro'),
	(3, 'Zazie Beetz'),
	(4, 'John David Washington'),
	(5, 'Robert Pattinson'),
	(6, 'Elizabeth Debicki'),
	(7, 'Kristen Stewart'),
	(8, 'Ray Liotta'),
	(9, 'Sigourney Weaver'),
	(10, 'Daniel Craig'),
	(11, 'Chris Evans'),
	(12, 'Ana de Armas'),
	(13, 'Jamie Lee Curtis');
	
INSERT INTO actors_playing (id_movie, id_actor) VALUES 
	(1,1),
	(1,2),
	(1,3),
	(2,4),
	(2,5),
	(2,6),
	(3,5),
	(3,7),
	(4,2),
	(4,8),
	(5,9),
	(6,10),
	(6,11),
	(6,12),
	(6,13);
	
INSERT INTO movie_display(id_movie, id_theater, lang, start_date, end_date) VALUES
	(1,1,'VOSTF',to_date('16-12-2020', 'DD-MM-YYYY'), to_date('20-01-2021', 'DD-MM-YYYY')),
	(1,2,'VF',to_date('16-12-2020', 'DD-MM-YYYY'), to_date('27-01-2021', 'DD-MM-YYYY')),
	(1,4,'VOSTF',to_date('16-12-2020', 'DD-MM-YYYY'), to_date('20-01-2021', 'DD-MM-YYYY')),
	(1,8,'VF',to_date('16-12-2020', 'DD-MM-YYYY'), to_date('02-01-2021', 'DD-MM-YYYY')),
	(1,10,'VF',to_date('16-12-2020', 'DD-MM-YYYY'), to_date('03-02-2021', 'DD-MM-YYYY')),
	(2,1,'VOSTF',to_date('09-12-2020', 'DD-MM-YYYY'), to_date('20-01-2021', 'DD-MM-YYYY')),
	(2,3,'VF',to_date('09-12-2020', 'DD-MM-YYYY'), to_date('02-01-2021', 'DD-MM-YYYY')),
	(2,4,'VOSTF',to_date('09-12-2020', 'DD-MM-YYYY'), to_date('13-01-2021', 'DD-MM-YYYY')),
	(2,7,'VF',to_date('09-12-2020', 'DD-MM-YYYY'), to_date('20-01-2021', 'DD-MM-YYYY')),
	(2,8,'VF',to_date('09-12-2020', 'DD-MM-YYYY'), to_date('27-01-2021', 'DD-MM-YYYY')),
	(2,9,'VOSTF',to_date('09-12-2020', 'DD-MM-YYYY'), to_date('20-01-2021', 'DD-MM-YYYY')),
	(2,11,'VOSTF',to_date('09-12-2020', 'DD-MM-YYYY'), to_date('20-01-2021', 'DD-MM-YYYY')),
	(3,6,'VF',to_date('23-12-2020', 'DD-MM-YYYY'), to_date('30-12-2020', 'DD-MM-YYYY')),
	(4,2,'VOSTF',to_date('09-12-2020', 'DD-MM-YYYY'), to_date('20-01-2021', 'DD-MM-YYYY')),
	(4,5,'VOSTF',to_date('09-12-2020', 'DD-MM-YYYY'), to_date('27-01-2021', 'DD-MM-YYYY')),
	(4,6,'VOSTF',to_date('09-12-2020', 'DD-MM-YYYY'), to_date('03-02-2021', 'DD-MM-YYYY')),
	(5,1,'VOSTF',to_date('30-12-2020', 'DD-MM-YYYY'), to_date('03-02-2021', 'DD-MM-YYYY')),
	(5,3,'VOSTF',to_date('30-12-2020', 'DD-MM-YYYY'), to_date('10-02-2021', 'DD-MM-YYYY')),
	(5,7,'VOSTF',to_date('30-12-2020', 'DD-MM-YYYY'), to_date('10-02-2021', 'DD-MM-YYYY')),
	(5,8,'VOSTF',to_date('30-12-2020', 'DD-MM-YYYY'), to_date('03-02-2021', 'DD-MM-YYYY')),
	(5,10,'VOSTF',to_date('30-12-2020', 'DD-MM-YYYY'), to_date('17-02-2021', 'DD-MM-YYYY')),
	(5,11,'VOSTF',to_date('30-12-2020', 'DD-MM-YYYY'), to_date('03-02-2021', 'DD-MM-YYYY')),
	(6,3,'VOSTF',to_date('20-01-2021', 'DD-MM-YYYY'), to_date('24-02-2021', 'DD-MM-YYYY')),
	(6,4,'VOSTF',to_date('20-01-2021', 'DD-MM-YYYY'), to_date('24-02-2021', 'DD-MM-YYYY')),
	(6,5,'VOSTF',to_date('20-01-2021', 'DD-MM-YYYY'), to_date('17-02-2021', 'DD-MM-YYYY')),
	(6,7,'VOSTF',to_date('20-01-2021', 'DD-MM-YYYY'), to_date('24-02-2021', 'DD-MM-YYYY'));
	




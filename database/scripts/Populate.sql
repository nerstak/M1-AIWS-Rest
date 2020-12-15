INSERT INTO city (id_city, name_city) VALUES 
	(1,'Paris'),
	(2,'Lyon'),
	(3,'Marseille'),
	(4,'Aix-en-Provence'),
	(5,'Les Sables'),
	(6,'Bordeaux');
	
INSERT INTO theater (id_city, name_theater) VALUES
	(1,'MK2 Bibliothèque'),
	(1,'UGC Les Halles'),
	(1,'Pathé Les Gobelins'),
	(2,'Pathé Bellecour'),
	(2,'UGC Cité Internationale'),
	(2,'UGC Lyon Part Dieu'),
	(3,'Le Prado'),
	(3,'Pathé Madeleine'),
	(4,'Le Cézanne'),
	-- We let Les Sables empty, just to see
	(6,'Mégarama'),
	(6,'CGR Le Français');
	
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
	
	
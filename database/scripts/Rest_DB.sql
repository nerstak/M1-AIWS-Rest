-- You need to be connected to aiws_db to execute this part
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE city
(
	id_city SERIAL PRIMARY KEY,
	name_city VARCHAR(100) NOT NULL
);

CREATE TABLE theater
(
	id_theater SERIAL PRIMARY KEY,
	id_city INTEGER NOT NULL,
	name_theater VARCHAR(100) NOT NULL
);

CREATE TABLE manager
(
	id_manager SERIAL PRIMARY KEY,
	id_theater INTEGER NOT NULL,
	username VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL
);

CREATE TABLE movie 
(
	id_movie SERIAL PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	duration VARCHAR(10) NOT NULL,
	min_age INT NOT NULL,
	director VARCHAR(100)
);

CREATE TABLE movie_display
(
	id_movie INTEGER NOT NULL,
	id_theater INTEGER NOT NULL,
	lang VARCHAR(20) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	PRIMARY KEY (id_movie, id_theater)
);

CREATE TABLE schedule
(
	id_schedule SERIAL PRIMARY KEY,
	id_movie INTEGER NOT NULL,
	id_theater INTEGER NOT NULL,
	time_day TIME NOT NULL,
	-- The day of the week (0 - 6; Sunday is 0) 
	day_of_week INTEGER NOT NULL--,
	--PRIMARY KEY (id_movie, id_theater)
);

CREATE TABLE actor 
(
	id_actor SERIAL PRIMARY KEY,
	name_actor VARCHAR(100) NOT NULL
);

CREATE TABLE actors_playing
(
	id_actor INTEGER NOT NULL,
	id_movie INTEGER NOT NULL,
	PRIMARY KEY (id_movie, id_actor)
);


ALTER TABLE theater
	ADD FOREIGN KEY(id_city) REFERENCES city(id_city)
	ON DELETE CASCADE;
	
ALTER TABLE manager
	ADD FOREIGN KEY(id_theater) REFERENCES theater(id_theater)
	ON DELETE CASCADE;
	
ALTER TABLE movie_display
	ADD FOREIGN KEY(id_theater) REFERENCES theater(id_theater)
	ON DELETE CASCADE;
	
ALTER TABLE movie_display
	ADD FOREIGN KEY(id_movie) REFERENCES movie(id_movie)
	ON DELETE CASCADE;
	
ALTER TABLE schedule
	ADD FOREIGN KEY(id_theater) REFERENCES theater(id_theater)
	ON DELETE CASCADE;
	
ALTER TABLE schedule
	ADD FOREIGN KEY(id_movie) REFERENCES movie(id_movie)
	ON DELETE CASCADE;
	
ALTER TABLE actors_playing
	ADD FOREIGN KEY(id_movie) REFERENCES movie(id_movie)
	ON DELETE CASCADE;
	
ALTER TABLE actors_playing
	ADD FOREIGN KEY(id_actor) REFERENCES actor(id_actor)
	ON DELETE CASCADE;
	
	

GRANT ALL PRIVILEGES ON DATABASE aiws_db TO admin_rest;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public to admin_rest;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public to admin_rest;


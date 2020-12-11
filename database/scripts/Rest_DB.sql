-- You need to be connected to aiws_db to execute this part
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE city
(
	id_city SERIAL PRIMARY KEY,
	name_ciy VARCHAR(100) NOT NULL
);

CREATE TABLE theater
(
	id_theater SERIAL PRIMARY KEY,
	id_city INTEGER NOT NULL,
	name_theater VARCHAR(100) NOT NULL
);

CREATE TABLE movie 
(
	id_movie SERIAL PRIMARY KEY,
	title VARCHAR(100) NOT NULL,
	duration VARCHAR(10) NOT NULL,
	min_age INT NOT NULL,
	director VARCHAR(100)
)

CREATE TABLE movie_display
(
	id_movie INTEGER NOT NULL,
	id_theater INTEGER NOT NULL,
	lang VARCHAR(20) NOT NULL,
	start_date TIME NOT NULL,
	end_date TIME NOT NULL,
	PRIMARY KEY (id_movie, id_theater)
);

CREATE TABLE schedule
(
	id_movie INTEGER NOT NULL,
	id_theater INTEGER NOT NULL,
	time_day TIME NOT NULL,
	day_of_week DAY NOT NULL,
	PRIMARY KEY (id_movie, id_theater)
)

CREATE TABLE actor 
(
	id_actor SERIAL PRIMARY KEY,
	name_actor VARCHAR(100) NOT NULL
);

CREATE TABLE actors_playing
(
	id_actor INTEGER NOT NULL,
	id_movie INTEGER NOT NULL,
	PRIMARY KEY (id_movie, actor)
)


ALTER TABLE theater
	ADD FOREIGN KEY(id_city) REFERENCES city(id_city);
	
ALTER TABLE movie_display
	ADD FOREIGN KEY(id_theater) REFERENCES theater(id_theater);
	
ALTER TABLE movie_display
	ADD FOREIGN KEY(id_movie) REFERENCES movie(id_movie);
	
ALTER TABLE schedule
	ADD FOREIGN KEY(id_theater) REFERENCES theater(id_theater);
	
ALTER TABLE schedule
	ADD FOREIGN KEY(id_movie) REFERENCES movie(id_movie);
	
ALTER TABLE actors_playing
	ADD FOREIGN KEY(id_movie) REFERENCES movie(id_movie);
	
ALTER TABLE actors_playing
	ADD FOREIGN KEY(id_actor) REFERENCES actor(id_actor);
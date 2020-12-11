-- You need to be connected to postgres (the DB) to execute this part
drop database IF EXISTS aiws_db ; -- No need to put everything in capital, it is not case sensitive here
CREATE DATABASE aiws_db OWNER admin_rest ENCODING DEFAULT CONNECTION LIMIT -1;
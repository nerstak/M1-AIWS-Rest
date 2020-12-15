#!/bin/sh
DB_USER=postgres
DB_PWD=admin
DB_SERVER=localhost
DB_NAME=postgres

psql "postgresql://$DB_USER:$DB_PWD@$DB_SERVER/$DB_NAME" -f User_Setup.sql
psql "postgresql://$DB_USER:$DB_PWD@$DB_SERVER/$DB_NAME" -f DB_create.sql

DB_NAME=aiws_db
psql "postgresql://$DB_USER:$DB_PWD@$DB_SERVER/$DB_NAME" -f Rest_DB.sql
psql "postgresql://$DB_USER:$DB_PWD@$DB_SERVER/$DB_NAME" -f Populate.sql
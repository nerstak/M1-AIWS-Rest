# M1-AIWS-Project - Information Provider on Cinema (I2S)

## Table of contents

- [Informations](#Informations)

- [Features](#Features)

- [Installation](#Installation)

- [Tests](#Tests)

- [Use of the website](#use_of_the_website)

## Informations

### Description

REST application to allow users to access data, associated with a front interface.

### Technologies/framework used

Built with

- Java
- Jax-RS, Jersey, Jackson, Json Web Tokens
- ELM

IDE 

- IntelliJ IDEA  

Database

- PostgreSQL

Web container

- Tomcat 9

But also...

- Maven

- Docker

## Features

This project enables any users to access distant resources. It is possible to:

- see, add, delete the list of movies (with actors, duration, etc), and each of them individually

- see, add, delete the list of cities (with theaters) and each of them individually

- see, add, delete the list of theaters and each of them individually

- see, add, delete the schedules for a movie in a theater

- authenticate as a manager of a theater, using tokens

## Installation

TO COMPLETE

## Endpoints

- `/rest/movies` Actions related to movies

- `/rest/movies/{idMovie}` Actions related to a movie

- `/rest/movies/{idMovie}/cities` Actions related to cities playing a movie

- `/rest/movies/{idMovie}/cities/{idCity}` Actions related to a city playing a movie

- `/rest/movies/{idMovie}/cities/{idCity}/theaters` Actions related to theaters in a city playing a movie

- `/rest/movies/{idMovie}/cities/{idCity}/theaters/{idTheater}` Actions related to a theater in a city playing a movie

- `/rest/movies/{idMovie}/cities/{idCity}/theaters/{idTheater}/schedules` Actions related to schedules of a theater in a city playing a movie

- `/rest/movies/{idMovie}/cities/{idCity}/theaters/{idTheater}/schedules/{idSchedule}` Actions related to a schedule of a theater in a city playing a movie

- `/rest/cities` Actions related to cities

- `/rest/cities/{idCity}` Actions related to a city

- `/rest/cities/{idCity}/theaters` Actions related to theaters in a city

- `/rest/cities/{idCity}/theaters/{idTheater}` Actions related to a theater in a city
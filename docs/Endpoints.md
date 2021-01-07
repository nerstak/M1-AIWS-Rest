# Endpoints documentation

## /movies

**Produces:** XML or JSON of list of movies

**Methods**:

- GET: Public
  
  - Response:
    
    - ```xml
      <movies>
          <movie idMovie="ID_OF_MOVIE">
              <duration>Duration of movie</duration>
              <direction>Name of director</direction>
              <minimumAge>Minimum age</minimumAge>
              <title>Title of movie</title>
              <actors>
                  <actor id="ID_OF_ACTOR">Actor name</actor>
              </actors>
         </movie>
      </movies>
      ```

- POST: Authencation required
  
  - Header: "Authorization" (Bearer JWT)
  
  - Body:
    
    - ```xml
      <movie>
          <duration>Duration of movie</duration>
          <direction>Name of director</direction>
          <minimumAge>Minimum age</minimumAge>
          <title>Title of movie</title>
          <actors>
              <actor>Actor name</actor>
          </actors>
      </movie>
      ```
    
    - ```json
      {
          "direction": "Name of director",
          "duration": "Duration of movie",
          "minimumAge": "Minimum age",
          "title": "Title of movie",
          "actors": [
              {"actor": "Actor name"}
          ]
      }
      ```

## /movies/{idMovie}

**Produces:** XML or JSON of informations on the movie

**Methods**:

- GET: Public
  - Response:
    - ```xml
      <movie idMovie="ID_OF_MOVIE">
          <duration>Duration of movie</duration>
          <direction>Name of director</direction>
          <minimumAge>Minimum age</minimumAge>
          <title>Title of movie</title>
          <actors>
              <actor id="ID_OF_ACTOR">Actor name</actor>
          </actors>
      </movie>
      ```
- DELETE: Authencation required
  - Header: "Authorization" (Bearer JWT)

## /cities

## /movies/{idMovie}/cities

**Produces:** XML or JSON of list of cities

**Methods**: 

- GET: Public
  
  - Response:
    
    - ```xml
      <cities>
          <city idCity="ID_OF_CITY">
              <name>Name of city</name>
              <theaters>
                  <theater id="ID_OF_THEATER" idCity="ID_OF_CITY">
                      <name>Name of theater</name>
                  </theater>
              </theaters>
          </city>
      </cities>
      ```

- POST: Public
  
  - Body: 
    
    - ```xml
        <city>
            <name>Name of new City</name>
        </city>
      ```
    - ```json
        {
            "name": "Name of new City"
        }
      ```

## /cities/{idCity}

## /movies/{idMovie}/cities/{idCity}

**Produces:** XML or JSON of informations on the city

**Methods**:

- GET: Public
  - Response:
    - ```xml
      <city idCity="ID_OF_CITY">
          <name>Name of city</name>
          <theaters>
                  <theater id="ID_OF_THEATER" idCity="ID_OF_CITY">
                      <name>Name of theater</name>
                  </theater>
          </theaters>  
      </city>
      ```
- DELETE: Authencation required
  - Header: "Authorization" (Bearer JWT)

## /cities/{idCity}/theaters

## /movies/{idMovie}/cities/{idCity}/theaters

**Produces:** XML or JSON of informations on theaters in the city

**Methods**:

- GET: Public
  
  - Response:
    
    - ```xml
      <theaters> 
          <theater id="ID_OF_THEATER" idCity="ID_OF_CITY">
              <name>Name of theater</name>
          </theater>
      </theaters>
      ```

- POST: Public
  
  - Body:
    
    - ```xml
      <newTheater>
          <theater idCity="ID_OF_CITY">
              <name>Name of new Theater</name>
          </theater>    
          <manager>
              <username>Username</username>
              <password>Password</password>
          </manager>
      </newTheater>
      ```
    
    - ```json
      {
          "theater": {
              "idCity": "ID_OF_CITY",
              "name": "Name of new Theater"
          },
          "manager": {
              "username": "Username",
      "password": "Password"
          }
      }
      ```

## /cities/{idCity}/theaters/{idTheater}

## /movies/{idMovie}/cities/{idCity}/theaters/{idTheater}

**Produces:** XML or JSON of informations on the theater

**Methods**:

- GET: Public
  - Reponse:
    - ```xml
      <theater id="ID_OF_THEATER" idCity="ID_OF_CITY">
          <name>Name of theater</name>
      </theater>
      ```
- DELETE: Authencation required
  - Header: "Authorization" (Bearer JWT)

## /movies/{idMovie}/cities/{idCity}/theaters/{idTheater}/schedules

**Produces:** XML or JSON of informations on the schedules

**Methods**:

- GET: Public

## /auth

**Produces:** XML or JSON of JWT

**Methods**:

- POST: Public
  
  - Body:
    
    - ```xml
      <manager>
          <username>Username</username>
          <password>Password</password>
      </manager>
      ```
    
    - ```json
      {
          "username": "Username",
          "password": "Password"
      }
      ```
  
  - Response:
    
    - ```xml
      <authResponse>
          <token>tokenPart1.tokenPart2.tokenPart3</token>
      </authResponse>
      ```
    
    - ```json
      {
        "token": "tokenPart1.tokenPart2.tokenPart3"
      }
      ```

 
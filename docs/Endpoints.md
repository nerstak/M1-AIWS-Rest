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
    - ```json
      [
          {
              "idMovie":ID_OF_MOVIE,
              "duration": "Duration of movie",
              "direction": "Name of director",
              "minimumAge": Minimum age,
              "title": "Title of movie",
              "actor": [
                  {
                      "value": "Actor name",
                      "id": ID_OF_ACTOR
                  }
              ]
          }
      ]
      ```

- POST: Authentication required
  
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
          "actor": [
              {"value": "Actor name"}
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
    
    - ```json
      {
          "idMovie":ID_OF_MOVIE,
          "duration": "Duration of movie",
          "direction": "Name of director",
          "minimumAge": Minimum age,
          "title": "Title of movie",
          "actor": [
              {
                  "value": "Actor name",
                  "id": ID_OF_ACTOR
              }
          ]
      }
      ```
      
      - DELETE: Authentication required
  
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
    - ```json
      [
          {
          "idCity": 1,
          "name": "Name of city",
          "theater": [
              {
                  "id": ID_OF_THEATER,
                  "name": "Name of theater",
                  "idCity": ID_OF_CITY
              }]
          }
      ]
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
    - ```json
      {
          "idCity": 1,
          "name": "Name of city",
          "theater": [
              {
                  "id": ID_OF_THEATER,
                  "name": "Name of theater",
                  "idCity": ID_OF_CITY
              }]
       }
      ```
- DELETE: Authentication required
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
    - ```json
      [
          {
              "id": ID_OF_THEATER,
              "name": "Name of theater",
              "idCity": ID_OF_CITY
          }
      ]
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
    
    - ```json
      {
          "id":ID_OF_THEATER,
          "name": "Name of theater",
          "idCity": ID_OF_CITY
      }
      ```

- DELETE: Authentication required
  
  - Header: "Authorization" (Bearer JWT)

## /movies/{idMovie}/cities/{idCity}/theaters/{idTheater}/schedules

**Produces:** XML or JSON of informations on the schedules

**Methods**:

- GET: Public
  - Response
    - ```xml
      <schedules>
          <schedule>
          <time>HH:mm:SS</time>
          <dayOfWeek>Day of week</dayOfWeek>
          </schedule>
      </schedules>
      ```
    - ```json
      [
          {
              "idSchedule": ID_OF_SCHEDULE,
              "time": "HH:mm:SS",
              "dayOfWeek": "Day of week"
          }
      ]
      ```

## /movies/{idMovie}/cities/{idCity}/theaters/{idTheater}/schedules/{idSchedule}

**Produces:** XML or JSON of informations on a schedule

**Methods**:

- GET: Public
  
  - Response
    
    - ```xml
      <schedule>
          <time>HH:mm:SS</time>
          <dayOfWeek>Day of week</dayOfWeek>
      </schedule>
      ```
    
    - ```json
      {
          "idSchedule": ID_OF_SCHEDULE,
          "time": "HH:mm:SS",
          "dayOfWeek": "Day of week"
      }
      ```

## /movies/{idMovie}/cities/{idCity}/theaters/{idTheater}/display

**Produces:** XML or JSON of informations on a display

**Methods**:

- GET: Public
  
  - Response
    - ```xml
      <display idMovie="ID_OF_MOVIE" idTheater="ID_OF_THEATER">
          <language>Langage of movie display</language>
          <startDate>YYYY-MM-DD</startDate>
          <endDate>YYYY-MM-DD</endDate>
      </display>
      ```
    - ```json
      {
          "idMovie": ID_OF_MOVIE,
          "idTheater": ID_OF_THEATER,
          "language": "Langage of movie display",
          "startDate": "YYYY-MM-DD",
          "endDate": "YYYY-MM-DD"
      }
      ```

- DELETE: Authentication required
  
  - Header: "Authorization" (Bearer JWT)

- POST: Authentication required
  
  - Header: "Authorization" (Bearer JWT)
  
  - Body:
    
    - ```xml
      <display>
          <language>Langage of movie display</language>
          <startDate>YYYY-MM-DD</startDate>
          <endDate>YYYY-MM-DD</endDate>
      </display>
      ```
    
    - ```json
      {
          "language": "Langage of movie display",
          "startDate": "YYYY-MM-DD",
          "endDate": "YYYY-MM-DD"
      }
      ```

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

## Error

Errors will either return a classic webpage, or a json object:

```json
{
    "statusCode": Code,
    "reason": "Reason of error",
    "details": "Details on error"
}
```

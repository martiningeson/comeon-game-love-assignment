# Game Love Service

Solution for [comeon-gamlove-assignment](https://github.com/comeon-group/comeon-gamelove-assignment).

The service consists of three APIs:
* /gamelove
* /players
* /games

H2 is used as in-memory database.

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)


## Overview
Service which provides functionality for Players to love and unlove Games. Players and Games are modelled as separate
entities using a many-to-many relationship. The gamelove-API is used as a facade to provide a simpler interface to
the Player- and Game-resources (to love and unlove games).

### Testing
Unittest - Much is covered but some cases are left out.

### Error handling 
A template for error handling is provided with 
**src/main/java/se/main/gameloveservice/exception/CustomExceptionHandler.java**. More work needs to be done in order
to make the service robust and user-friendly.

## Running the application locally

```shell
./mvnw spring-boot:run
```

## Usage
During startup the application will preload one Player and multiple Games using the CommandLineRunner-interface. 
See **src/main/java/se/main/gameloveservice/GameLoveServiceConfig.java** for details (database tables are generated 
automatically).

The embedded in-memory database can be accessed using [h2-console](http://localhost:8080/h2-console).

### APIs

#### Game Love
POST - add new entry
```
curl --location --request POST 'http://localhost:8080/gamelove' \
--header 'Content-Type: application/json' \
--data-raw '{
    "player": {
        "name": "Player"
    },
    "game": {
        "name": "GameToLove"
    }
}'

```

DELETE - delete entry
```
curl --location --request DELETE 'http://localhost:8080/gamelove' \
--header 'Content-Type: application/json' \
--data-raw '{
    "player": {
        "name": "Player"
    },
    "game": {
        "name": "GameToUnlove"
    }
}'
```

#### Players

GET - player
```
curl --location --request GET 'http://localhost:8080/players/{playerId}'
```

GET - all games loved by a player
```
curl --location --request GET 'http://localhost:8080/players/{playerId}/games'
```

GET - player by name
```
curl --location --request GET 'http://localhost:8080/players?name={playerName}'
```

#### Games
GET - top loved games
```
curl --location --request GET 'http://localhost:8080/games/toplovedgames?nr={nrOfElements}'
```

Start the application and access [swagger-ui](http://localhost:8080/swagger-ui/index.html) for more info.

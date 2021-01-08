module API exposing (..)
import RemoteData exposing (WebData)
import Http
import Json.Decode.Pipeline exposing (required, optional, custom)
import Json.Decode as Decode exposing (Decoder)
import Json.Encode as Encode

-- GET

-- movies

getMovies : (WebData Movies -> msg) -> Cmd msg
getMovies toMsg=
    Http.request
        { method = "GET"
        , headers = [ Http.header "Accept" "application/json"]
        , url = "http://localhost:8080/Project/rest/movies"
        , body = Http.emptyBody
        , expect = Http.expectJson (RemoteData.fromResult >> toMsg) moviesDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

-- cities

getCities : (WebData Cities -> msg) -> Cmd msg
getCities toMsg =
    Http.request
        { method = "GET"
        , headers = [ Http.header "Accept" "application/json"]
        , url = "http://localhost:8080/Project/rest/cities"
        , body = Http.emptyBody
        , expect = Http.expectJson (RemoteData.fromResult >> toMsg) citiesDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

-- cities with idMovie

getCitiesIdMovie : Int -> (WebData Cities -> msg) -> Cmd msg
getCitiesIdMovie idMovie toMsg =
    Http.request
        { method = "GET"
        , headers = [ Http.header "Accept" "application/json" ]
        , url = "http://localhost:8080/Project/rest/movies/"
            ++ String.fromInt idMovie
            ++ "/cities"
        , body = Http.emptyBody
        , expect = Http.expectJson (RemoteData.fromResult >> toMsg) citiesDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

-- theaters

getTheaters : Int -> (WebData Theaters -> msg) -> Cmd msg
getTheaters idCity toMsg =
    Http.request
        { method = "GET"
        , headers = [ Http.header "Accept" "application/json" ]
        , url = "http://localhost:8080/Project/rest/cities/"
            ++ String.fromInt idCity
            ++ "/theaters"
        , body = Http.emptyBody
        , expect = Http.expectJson (RemoteData.fromResult >> toMsg) theatersDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

-- theaters with idMovie

getTheatersIdMovie : Int -> Int -> (WebData Theaters -> msg) -> Cmd msg
getTheatersIdMovie idMovie idCity toMsg =
    Http.request
        { method = "GET"
        , headers = [ Http.header "Accept" "application/json" ]
        , url = "http://localhost:8080/Project/rest/movies/"
            ++ String.fromInt idMovie
            ++ "/cities/"
            ++ String.fromInt idCity
            ++ "/theaters"
        , body = Http.emptyBody
        , expect = Http.expectJson (RemoteData.fromResult >> toMsg) theatersDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

-- schedules

getSchedules : Int -> Int -> Int -> (WebData Schedules -> msg) -> Cmd msg
getSchedules idMovie idCity idTheater toMsg =
    Http.request
        { method = "GET"
        , headers = [ Http.header "Accept" "application/json" ]
        , url = "http://localhost:8080/Project/rest/movies/"
            ++ String.fromInt idMovie
            ++ "/cities/"
            ++ String.fromInt idCity
            ++ "/theaters/"
            ++ String.fromInt idTheater
            ++ "/schedules"
        , body = Http.emptyBody
        , expect = Http.expectJson (RemoteData.fromResult >> toMsg) schedulesDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

-- display

getDisplay : Int -> Int -> Int -> (WebData Display -> msg) -> Cmd msg
getDisplay idMovie idCity idTheater toMsg =
    Http.request
        { method = "GET"
        , headers = [ Http.header "Accept" "application/json" ]
        , url = "http://localhost:8080/Project/rest/movies/"
            ++ String.fromInt idMovie
            ++ "/cities/"
            ++ String.fromInt idCity
            ++ "/theaters/"
            ++ String.fromInt idTheater
            ++ "/display"
        , body = Http.emptyBody
        , expect = Http.expectJson (RemoteData.fromResult >> toMsg) displayDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

-- POST

postAuth : String -> String -> (WebData Token -> msg) -> Cmd msg
postAuth username password toMsg =
    Http.request
        { method = "POST"
        , headers = [ Http.header "Accept" "application/json" ]
        , url = "http://localhost:8080/Project/rest/auth"
        , body = Http.jsonBody <|
            Encode.object
                [ ( "username", Encode.string username )
                , ( "password", Encode.string password )
                ]
        , expect = Http.expectJson (RemoteData.fromResult >> toMsg) tokenDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

-- DECODERS

-- movies

type alias Movies =
    List Movie

moviesDecoder : Decoder Movies
moviesDecoder =
    Decode.list movieDecoder

-- movie

type alias Movie =
    { id : Int
    , duration : String
    , direction : String
    , minimumAge : Int
    , title : String
    , actors : List Actor
    }

movieDecoder : Decoder Movie
movieDecoder =
    Decode.succeed Movie
        |> required "idMovie" Decode.int
        |> required "duration" Decode.string
        |> required "direction" Decode.string
        |> required "minimumAge" Decode.int
        |> required "title" Decode.string
        |> required "actor" (Decode.list actorDecoder)

-- actor

type alias Actor =
    { id : Int
    , name : String
    }

actorDecoder : Decoder Actor
actorDecoder =
    Decode.succeed Actor
        |> required "id" Decode.int
        |> required "value" Decode.string

-- cities

type alias Cities =
    List City

citiesDecoder : Decoder Cities
citiesDecoder =
    Decode.list cityDecoder

-- city

type alias City =
    { id : Int
    , name : String
    , theaters : List Theater
    }

cityDecoder : Decoder City
cityDecoder =
    Decode.succeed City
        |> required "idCity" Decode.int
        |> required "name" Decode.string
        |> required "theater" (Decode.list theaterDecoder)

-- theaters

type alias Theaters =
    List Theater

theatersDecoder : Decoder Theaters
theatersDecoder =
    Decode.list theaterDecoder

-- theater

type alias Theater =
    { id : Int
    , name : String
    }

theaterDecoder : Decoder Theater
theaterDecoder =
    Decode.succeed Theater
        |> required "id" Decode.int
        |> required "name" Decode.string

-- schedules

type alias Schedules =
    List Schedule


schedulesDecoder : Decoder Schedules
schedulesDecoder =
    Decode.list scheduleDecoder

-- schedule

type alias Schedule =
    { time : String
    , dayOfWeek : String
    }


scheduleDecoder : Decoder Schedule
scheduleDecoder =
    Decode.succeed Schedule
        |> required "time" Decode.string
        |> required "dayOfWeek" Decode.string

-- display

type alias Display =
    { language : String
    , startDate : String
    , endDate : String
    }

displayDecoder : Decoder Display
displayDecoder =
    Decode.succeed Display
        |> required "language" Decode.string
        |> required "startDate" Decode.string
        |> required "endDate" Decode.string

-- token

type alias Token =
    String

tokenDecoder : Decoder Token
tokenDecoder =
    Decode.field "token" Decode.string
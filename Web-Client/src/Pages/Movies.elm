module Pages.Movies exposing (Params, Model, Msg, page)

import Element.Font as Font
import Shared
import Spa.Document exposing (Document)
import Spa.Page as Page exposing (Page)
import Spa.Url as Url exposing (Url)
import Http
import RemoteData exposing (RemoteData(..), WebData)
import Element exposing (..)
import Json.Decode.Pipeline exposing (required, optional, custom)
import Json.Decode as Decode exposing (Decoder)
import Spa.Generated.Route as Route


page : Page Params Model Msg
page =
    Page.application
        { init = init
        , update = update
        , subscriptions = subscriptions
        , view = view
        , save = save
        , load = load
        }



-- INIT


type alias Params =
    ()


type alias Model =
    { body : List UrlInfo
    }

type alias UrlInfo =
    { url : String
    , label : Element Shared.Msg
    }

init : Shared.Model -> Url Params -> ( Model, Cmd Msg )
init shared { params } =
    ( { body = [] }, getMovies )



-- UPDATE


type Msg
    = GotMovies (WebData Movies)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GotMovies data ->
            updateGotMovies data model

updateGotMovies : WebData Movies -> Model -> ( Model, Cmd Msg )
updateGotMovies data model =
    case data of
        Success movies ->
            updateSuccess movies model
        Failure error ->
            ({ model | body =
                [ { url = Route.toString Route.Movies
                  , label = text <| httpErrorToString error
                  } ]
             }, Cmd.none)
        _ ->
            (model, Cmd.none)
        
updateSuccess : Movies -> Model -> ( Model, Cmd Msg )
updateSuccess movies model =
    ( { model | body = List.map movieToUrlInfo movies }, Cmd.none )

movieToUrlInfo : Movie -> UrlInfo
movieToUrlInfo movie =
    { url = Route.toString <| Route.Movies__IdMovie_Int__Cities { idMovie = movie.id }
    , label = column [ height fill, width fill]
        [   movieTitle movie
        ,   movieSubTitle movie
        ,   movieActors movie
        ]
    }

movieTitle : Movie -> Element msg
movieTitle movie =
   row [width fill] [
    link [ width fill, Font.center, Font.bold]
              { url = ""
              , label = text ""
              },
    link [ width fill, Font.center, Font.bold]
           { url = ""
           , label = text (String.toUpper movie.title)
           },
    link [ width fill, Font.alignRight, Font.color cgBlue]
               { url = ""
               , label = text ("+" ++ (String.fromInt movie.minimumAge))
               }
   ]

movieSubTitle : Movie -> Element msg
movieSubTitle movie =
     link [ width fill, Font.center, Font.italic, Font.color grey]
            { url = ""
            , label = text <|
                                "\n" ++ movie.duration ++
                                " | " ++ movie.direction
            }


movieActors : Movie -> Element msg
movieActors movie =
     link [ width fill, Font.center, Font.color orange]
            { url = ""
            , label = text <|
                              (List.foldl (++) "" <| List.map actorToString movie.actors)
            }


actorToString : Actor -> String
actorToString actor =
    "\n" ++ actor.name

httpErrorToString : Http.Error -> String
httpErrorToString error =
    (case error of
        Http.BadUrl _ ->
            "The provided url is not valid"
        Http.Timeout ->
            "The request timed out"
        Http.NetworkError ->
            "There was a network error"
        Http.BadStatus code ->
            "Error " ++ (String.fromInt code) ++ " - An error occured"
        Http.BadBody info ->
            "The body of the response was not valid: \n" ++ info)
    ++ "\nClick to retry!"

save : Model -> Shared.Model -> Shared.Model
save model shared =
    { shared | body = model.body }


load : Shared.Model -> Model -> ( Model, Cmd Msg )
load shared model =
    ( model, Cmd.none )


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none



-- VIEW


view : Model -> Document Msg
view model =
    { title = "Movies"
    , body = []
    }

-- HTTP

getMovies : Cmd Msg
getMovies =
    Http.request
        { method = "GET"
        , headers = [ Http.header "Accept" "application/json"]
        , url = "http://localhost:8080/Project/rest/movies"
        , body = Http.emptyBody
        , expect = Http.expectJson (RemoteData.fromResult >> GotMovies) moviesDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

type alias Movies =
    List Movie

moviesDecoder : Decoder Movies
moviesDecoder =
    Decode.list movieDecoder

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

type alias Actor =
    { id : Int
    , name : String
    }

actorDecoder : Decoder Actor
actorDecoder =
    Decode.succeed Actor
        |> required "id" Decode.int
        |> required "value" Decode.string

grey : Color
grey =
    rgb255 240 235 216

shadowBlue : Color
shadowBlue =
    rgb255 116 140 171

orange : Color
orange =
    rgb255 255 130 0

cgBlue : Color
cgBlue =
    rgb255 173 241 210
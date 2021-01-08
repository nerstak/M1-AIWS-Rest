module Pages.Movies exposing (Params, Model, Msg, page)

import Colors
import Element.Font as Font
import Shared
import Spa.Document exposing (Document)
import Spa.Page as Page exposing (Page)
import Spa.Url as Url exposing (Url)
import Http
import RemoteData exposing (RemoteData(..), WebData)
import Element exposing (..)
import Spa.Generated.Route as Route
import API


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
    ( { body = [] }
    , API.getMovies GotMovies
    )



-- UPDATE


type Msg
    = GotMovies (WebData API.Movies)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GotMovies data ->
            updateGotMovies data model

updateGotMovies : WebData API.Movies -> Model -> ( Model, Cmd Msg )
updateGotMovies data model =
    case data of
        Success movies ->
            updateSuccess movies model
        Failure error ->
            updateFailure error model
        _ ->
            (model, Cmd.none)
        
updateSuccess : API.Movies -> Model -> ( Model, Cmd Msg )
updateSuccess movies model =
    ( { model | body = List.map movieToUrlInfo movies }, Cmd.none )

movieToUrlInfo : API.Movie -> UrlInfo
movieToUrlInfo movie =
    { url = Route.toString <| Route.Movies__IdMovie_Int__Cities { idMovie = movie.id }
    , label = column [ height fill, width fill]
        [
          image
          [ centerX
          , centerY
          , spacing 10
          , width
                (fill
                    |> maximum 200
                )
          ]
          { src = "/images/movie.png"
          , description = "logo"
          }
        , el [] <| text "\n"
        ,   movieTitle movie
        ,   movieSubTitle movie
        ,   movieActors movie
        ]
    }

movieTitle : API.Movie -> Element msg
movieTitle movie =
   row [width fill]
   [    el [ width fill, Font.center, Font.bold] <| text ""
   ,    el [ width fill, Font.center, Font.bold] <| text (String.toUpper movie.title)
   ,    el [ width fill, Font.center, Font.bold] <| text ("+" ++ (String.fromInt movie.minimumAge))
   ]

movieSubTitle : API.Movie -> Element msg
movieSubTitle movie =
     link [ width fill, Font.center, Font.italic, Font.color Colors.grey]
            { url = ""
            , label = text <|
                                "\n" ++ movie.duration ++
                                " | " ++ movie.direction
            }


movieActors : API.Movie -> Element msg
movieActors movie =
     link [ width fill, Font.center, Font.color Colors.orange]
            { url = ""
            , label = text <|
                              (List.foldl (++) "" <| List.map actorToString movie.actors)
            }


actorToString : API.Actor -> String
actorToString actor =
    "\n" ++ actor.name

updateFailure : Http.Error -> Model -> ( Model, Cmd Msg )
updateFailure error model =
    ({ model | body =
        [ { url = Route.toString Route.Movies
          , label = text <| httpErrorToString error
          } ]
     }, Cmd.none)

httpErrorToString : Http.Error -> String
httpErrorToString error =
    case error of
        Http.BadUrl _ ->
            "The provided url is not valid"
        Http.Timeout ->
            "The request timed out"
        Http.NetworkError ->
            "There was a network error"
        Http.BadStatus code ->
            "Error " ++ (String.fromInt code) ++ " - An error occured"
        Http.BadBody info ->
            "The body of the response was not valid: \n" ++ info

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



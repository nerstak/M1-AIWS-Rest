module Pages.Movies.IdMovie_Int.Cities.IdCity_Int.Theaters.IdTheater_Int.Schedule exposing (Params, Model, Msg, page)

import Shared
import Spa.Document exposing (Document)
import Spa.Page as Page exposing (Page)
import Spa.Url as Url exposing (Url)
import Http
import RemoteData exposing (WebData, RemoteData(..))
import Element exposing (..)
import Element.Font as Font
import Json.Decode.Pipeline exposing (required, optional, custom)
import Json.Decode as Decode exposing (Decoder)
import Spa.Generated.Route as Route
import Html exposing (param)


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
    { idMovie : Int, idCity : Int, idTheater : Int }


type alias Model =
    { idMovie : Int
    , idCity : Int
    , idTheater : Int
    , body : List UrlInfo
    }

type alias UrlInfo =
    { url : String
    , label : Element Shared.Msg
    }

init : Shared.Model -> Url Params -> ( Model, Cmd Msg )
init shared { params } =
    (
        { idMovie = params.idMovie
        , idCity = params.idCity
        , idTheater = params.idTheater
        , body = [] 
        }
    , getSchedules params.idMovie params.idCity params.idTheater
    )



-- UPDATE


type Msg
    = GotSchedules (WebData Schedules)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GotSchedules data ->
            updateGotSchedules data model

updateGotSchedules : WebData Schedules -> Model -> ( Model, Cmd Msg )
updateGotSchedules data model =
    case data of
        Success schedules ->
            updateSuccess schedules model
        Failure error ->
            updateFailure error model
        _ ->
            (model, Cmd.none)

updateSuccess : Schedules -> Model -> ( Model, Cmd Msg )
updateSuccess schedules model =
    ( { model | body = List.map (scheduleToUrlInfo model.idMovie model.idCity model.idTheater) schedules.schedules}, Cmd.none )

scheduleToUrlInfo : Int -> Int -> Int -> Schedule -> UrlInfo
scheduleToUrlInfo idMovie idCity idTheater schedule =
    { url = Route.toString <| Route.Movies__IdMovie_Int__Cities__IdCity_Int__Theaters__IdTheater_Int__Schedule
        { idMovie = idMovie
        , idCity = idCity
        , idTheater = idTheater
        }
    , label = column [ height fill, width fill, spacing 15]
        [    image
                      [ centerX
                      , centerY
                      , spacing 10
                      , width
                            (fill
                                |> maximum 200
                            )
                      ]
                      { src = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn.icon-icons.com%2Ficons2%2F1310%2FPNG%2F512%2Fcity_86340.png&f=1&nofb=1"
                      , description = "logo"
                      }
        ,   schedulesTime schedule
        ,   schedulesDayOfWeek schedule
        ]
    }

schedulesTime : Schedule -> Element msg
schedulesTime schedule =
    el [width fill, Font.center] <| text (String.toUpper schedule.time)

schedulesDayOfWeek : Schedule -> Element msg
schedulesDayOfWeek schedule =
    el [width fill, Font.center] <| text (String.toUpper schedule.dayOfWeek)

updateFailure : Http.Error -> Model -> ( Model, Cmd Msg )
updateFailure error model =
    ({ model | body =
        [ { url = Route.toString Route.Movies
          , label = text <| httpErrorToString error
          } ]
     }, Cmd.none)

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
    { title = "Movies.IdMovie_Int.Cities.IdCity_Int.Theaters.IdTheater_Int.Schedule"
    , body = []
    }

-- HTTP

getSchedules : Int -> Int -> Int -> Cmd Msg
getSchedules idMovie idCity idTheater =
    Http.request
        { method = "GET"
        , headers = [ Http.header "Accept" "application/json"]
        , url = "http://localhost:8080/Project/rest/movies/"
            ++ String.fromInt idMovie
            ++ "/cities/"
            ++ String.fromInt idCity
            ++ "/theaters/"
            ++ String.fromInt idTheater
            ++ "/schedules"
        , body = Http.emptyBody
        , expect = Http.expectJson (RemoteData.fromResult >> GotSchedules) schedulesDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

type alias Schedules =
    { language : String
    , startDate : String
    , endDate : String
    , schedules : List Schedule
    }


schedulesDecoder : Decoder Schedules
schedulesDecoder =
    Decode.succeed Schedules
        |> required "language" Decode.string
        |> required "startDate" Decode.string
        |> required "endDate" Decode.string
        |> required "schedule" (Decode.list scheduleDecoder)

type alias Schedule =
    { time : String
    , dayOfWeek : String
    }

scheduleDecoder : Decoder Schedule
scheduleDecoder =
    Decode.succeed Schedule
        |> required "time" Decode.string
        |> required "dayOfWeek" Decode.string
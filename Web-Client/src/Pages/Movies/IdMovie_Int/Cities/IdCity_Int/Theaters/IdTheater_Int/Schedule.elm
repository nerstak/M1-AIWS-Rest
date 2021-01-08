module Pages.Movies.IdMovie_Int.Cities.IdCity_Int.Theaters.IdTheater_Int.Schedule exposing (Params, Model, Msg, page)

import Shared
import Spa.Document exposing (Document)
import Spa.Page as Page exposing (Page)
import Spa.Url as Url exposing (Url)
import Http
import RemoteData exposing (WebData, RemoteData(..))
import Element exposing (..)
import Element.Font as Font
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
    { idMovie : Int, idCity : Int, idTheater : Int }


type alias Model =
    { idMovie : Int
    , idCity : Int
    , idTheater : Int
    , display : List UrlInfo
    , schedules : List UrlInfo
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
        , display = []
        , schedules = [] 
        }
    , Cmd.batch
        [ API.getSchedules params.idMovie params.idCity params.idTheater GotSchedules
        , API.getDisplay params.idMovie params.idCity params.idTheater GotDisplay
        ]
    )



-- UPDATE


type Msg
    = GotSchedules (WebData API.Schedules)
    | GotDisplay (WebData API.Display)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GotSchedules data ->
            updateGotSchedules data model
        GotDisplay data ->
            updateGotDisplay data model

-- updateGotSchedules

updateGotSchedules : WebData API.Schedules -> Model -> ( Model, Cmd Msg )
updateGotSchedules data model =
    case data of
        Success schedules ->
            updateSuccessGotSchedules schedules model
        Failure error ->
            updateFailureGotSchedules error model
        _ ->
            (model, Cmd.none)

updateSuccessGotSchedules : API.Schedules -> Model -> ( Model, Cmd Msg )
updateSuccessGotSchedules schedules model =
    ( { model | schedules = List.map (scheduleToUrlInfo model.idMovie model.idCity model.idTheater) schedules}, Cmd.none )

scheduleToUrlInfo : Int -> Int -> Int -> API.Schedule -> UrlInfo
scheduleToUrlInfo idMovie idCity idTheater schedule =
    { url = Route.toString <| Route.Movies__IdMovie_Int__Cities__IdCity_Int__Theaters__IdTheater_Int__Schedule
        { idMovie = idMovie
        , idCity = idCity
        , idTheater = idTheater
        }
    , label = column [ height fill, width fill, spacing 15]
        [   schedulesDayOfWeek schedule
        ,   schedulesTime schedule
        ]
    }

schedulesTime : API.Schedule -> Element msg
schedulesTime schedule =
    el [width fill, Font.center, Font.size 15] <| text (String.toUpper schedule.time)

schedulesDayOfWeek : API.Schedule -> Element msg
schedulesDayOfWeek schedule =
    el [width fill, Font.center] <| text (String.toUpper schedule.dayOfWeek)

updateFailureGotSchedules : Http.Error -> Model -> ( Model, Cmd Msg )
updateFailureGotSchedules error model =
    ({ model | schedules =
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

-- updateGotDisplay

updateGotDisplay : WebData API.Display -> Model -> ( Model, Cmd Msg )
updateGotDisplay data model =
    case data of
        Success display ->
            updateSuccessGotDisplay display model
        Failure error ->
            updateFailureGotDisplay error model
        _ ->
            (model, Cmd.none)

updateSuccessGotDisplay : API.Display -> Model -> ( Model, Cmd Msg )
updateSuccessGotDisplay display model =
    ( { model | display = [ displayToUrlInfo model.idMovie model.idCity model.idTheater display ] }, Cmd.none )

displayToUrlInfo : Int -> Int -> Int -> API.Display -> UrlInfo
displayToUrlInfo idMovie idCity idTheater display =
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
                      { src = "/images/schedule.png"
                      , description = "logo"
                      }
        ,   displayDate display
        ,   displayLanguage display
        ]
    }

displayLanguage : API.Display -> Element msg
displayLanguage display =
    el [width fill, Font.center, Font.size 15] <| text (String.toUpper display.language)

displayDate : API.Display -> Element msg
displayDate display =
    el [width fill, Font.center] <| text (String.toUpper (display.endDate ++ " → " ++ display.endDate))

updateFailureGotDisplay : Http.Error -> Model -> ( Model, Cmd Msg )
updateFailureGotDisplay error model =
    ({ model | display =
        [ { url = Route.toString Route.Movies
          , label = text <| httpErrorToString error
          } ]
     }, Cmd.none)

-- SAVE

save : Model -> Shared.Model -> Shared.Model
save model shared =
    { shared | body = model.display ++ model.schedules }

-- LOAD

load : Shared.Model -> Model -> ( Model, Cmd Msg )
load shared model =
    ( model, Cmd.none )

-- SUBSCRIPTION

subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none



-- VIEW


view : Model -> Document Msg
view model =
    { title = "Movies.IdMovie_Int.Cities.IdCity_Int.Theaters.IdTheater_Int.Schedule"
    , body = []
    }

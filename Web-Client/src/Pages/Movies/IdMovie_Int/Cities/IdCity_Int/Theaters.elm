module Pages.Movies.IdMovie_Int.Cities.IdCity_Int.Theaters exposing (Params, Model, Msg, page)

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
    { idMovie : Int, idCity : Int }

type alias Model =
    { idMovie : Int
    , idCity : Int
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
        , body = [] 
        }
    , API.getTheatersIdMovie params.idMovie params.idCity GotTheaters
    )


-- UPDATE


type Msg
    = GotTheaters (WebData API.Theaters)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GotTheaters data ->
            updateGotTheaters data model

updateGotTheaters : WebData API.Theaters -> Model -> ( Model, Cmd Msg )
updateGotTheaters data model =
    case data of
        Success theaters ->
            updateSuccess theaters model
        Failure error ->
            updateFailure error model
        _ ->
            (model, Cmd.none)

updateSuccess : API.Theaters -> Model -> ( Model, Cmd Msg )
updateSuccess theaters model =
    ( { model | body = List.map (theaterToUrlInfo model.idMovie model.idCity) theaters}, Cmd.none )

theaterToUrlInfo : Int -> Int -> API.Theater -> UrlInfo
theaterToUrlInfo idMovie idCity theater =
    { url = Route.toString <| Route.Movies__IdMovie_Int__Cities__IdCity_Int__Theaters__IdTheater_Int__Schedule
        { idMovie = idMovie
        , idCity = idCity
        , idTheater = theater.id
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
                      { src = "/images/theater.png"
                      , description = "logo"
                      }
        ,   theaterName theater
        ]
    }

theaterName : API.Theater -> Element msg
theaterName theater =
    el [width fill, Font.center] <| text (String.toUpper theater.name)

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
    { title = "Movies.IdMovie_Int.Cities.IdCity_Int.Theaters"
    , body = []
    }

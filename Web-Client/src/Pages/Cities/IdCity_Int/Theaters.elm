module Pages.Cities.IdCity_Int.Theaters exposing (Params, Model, Msg, page)

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
    { idCity : Int }


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
    , API.getTheaters params.idCity GotTheaters
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
            ({ model | body =
                [ { url = Route.toString Route.Cities
                  , label = text <| httpErrorToString error
                  } ]
             }, Cmd.none)
        _ ->
            (model, Cmd.none)

updateSuccess : API.Theaters -> Model -> ( Model, Cmd Msg )
updateSuccess theaters model =
    ( { model | body = List.map cityToUrlInfo theaters }, Cmd.none )

cityToUrlInfo : API.Theater -> UrlInfo
cityToUrlInfo theater =
    { url = Route.toString <| Route.Cities__IdCity_Int__Theaters { idCity = theater.id }
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
    { title = "Cities.IdCity_Int.Theaters"
    , body = []
    }

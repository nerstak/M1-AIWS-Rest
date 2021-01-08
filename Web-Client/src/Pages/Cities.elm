module Pages.Cities exposing (Params, Model, Msg, page)

import Element.Font as Font
import Shared
import Spa.Document exposing (Document)
import Spa.Page as Page exposing (Page)
import Spa.Url as Url exposing (Url)
import Http
import RemoteData exposing (RemoteData(..), WebData)
import Element exposing (..)
import Spa.Generated.Route as Route
import Colors
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
    , API.getCities GotCities
    )



-- UPDATE


type Msg
    = GotCities (WebData API.Cities)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GotCities data ->
            updateGotCities data model

updateGotCities : WebData API.Cities -> Model -> ( Model, Cmd Msg )
updateGotCities data model =
    case data of
        Success cities ->
            updateSuccess cities model
        Failure error ->
            ({ model | body =
                [ { url = Route.toString Route.Cities
                  , label = text <| httpErrorToString error
                  } ]
             }, Cmd.none)
        _ ->
            (model, Cmd.none)

updateSuccess : API.Cities -> Model -> ( Model, Cmd Msg )
updateSuccess cities model =
    ( { model | body = List.map cityToUrlInfo cities }, Cmd.none )

cityToUrlInfo : API.City -> UrlInfo
cityToUrlInfo city =
    { url = Route.toString <| Route.Cities__IdCity_Int__Theaters { idCity = city.id }
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
                      { src = "/images/city.png"
                      , description = "logo"
                      }
        ,   cityName city
        ,   cityTheaters city
        ]
    }

cityName : API.City -> Element msg
cityName city =
    el [width fill, Font.center] <| text (String.toUpper city.name)

cityTheaters : API.City -> Element msg
cityTheaters city =
     el [ width fill, Font.center, Font.color Colors.orange]
             <| text (List.foldl (++) "" <| List.map theaterToString city.theaters)

theaterToString : API.Theater -> String
theaterToString theater =
    "\n" ++ theater.name

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
    { title = "Cities"
    , body = []
    }

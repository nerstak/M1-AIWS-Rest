module Pages.Cities exposing (Params, Model, Msg, page)

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
    ( { body = [] }, getCities )



-- UPDATE


type Msg
    = GotCities (WebData Cities)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GotCities data ->
            updateGotCities data model

updateGotCities : WebData Cities -> Model -> ( Model, Cmd Msg )
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

updateSuccess : Cities -> Model -> ( Model, Cmd Msg )
updateSuccess cities model =
    ( { model | body = List.map cityToUrlInfo cities }, Cmd.none )

cityToUrlInfo : City -> UrlInfo
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
                      { src = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn.icon-icons.com%2Ficons2%2F1310%2FPNG%2F512%2Fcity_86340.png&f=1&nofb=1"
                      , description = "logo"
                      }
        ,   cityName city
        ,   cityTheaters city
        ]
    }

cityName : City -> Element msg
cityName city =
    el [width fill, Font.center] <| text (String.toUpper city.name)

cityTheaters : City -> Element msg
cityTheaters city =
     el [ width fill, Font.center, Font.color orange]
             <| text (List.foldl (++) "" <| List.map theaterToString city.theater)

theaterToString : Theater -> String
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

-- HTTP

getCities : Cmd Msg
getCities =
    Http.request
        { method = "GET"
        , headers = [ Http.header "Accept" "application/json"]
        , url = "http://localhost:8080/Project/rest/cities"
        , body = Http.emptyBody
        , expect = Http.expectJson (RemoteData.fromResult >> GotCities) citiesDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

type alias Cities =
    List City

citiesDecoder : Decoder Cities
citiesDecoder =
    Decode.list cityDecoder

type alias City =
    { id : Int
    , name : String
    , theater : List Theater
    }

cityDecoder : Decoder City
cityDecoder =
    Decode.succeed City
        |> required "idCity" Decode.int
        |> required "name" Decode.string
        |> required "theater" (Decode.list theaterDecoder)

type alias Theater =
    { id : Int
    , name : String
    , idCity : Int
    }

theaterDecoder : Decoder Theater
theaterDecoder =
    Decode.succeed Theater
        |> required "id" Decode.int
        |> required "name" Decode.string
        |> required "idCity" Decode.int

orange : Color
orange =
    rgb255 255 130 0
module Pages.Cities.IdCity_Int.Theaters exposing (Params, Model, Msg, page)

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
    ( { body = [] }, getCities params.idCity)



-- UPDATE


type Msg
    = GotTheaters (WebData Theaters)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GotTheaters data ->
            updateGotTheaters data model

updateGotTheaters : WebData Theaters -> Model -> ( Model, Cmd Msg )
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

updateSuccess : Theaters -> Model -> ( Model, Cmd Msg )
updateSuccess theaters model =
    ( { model | body = List.map cityToUrlInfo theaters }, Cmd.none )

cityToUrlInfo : Theater -> UrlInfo
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
                      { src = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimg.icons8.com%2Fbubbles%2F2x%2Fcity.png&f=1&nofb=1"
                      , description = "logo"
                      }
        ,   text "bonjour"
        ]
    }

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

-- HTTP

getCities : Int -> Cmd Msg
getCities idCity =
    Http.request
        { method = "GET"
        , headers = [ Http.header "Accept" "application/json"]
        , url = "http://localhost:8080/Project/rest/cities/" ++ String.fromInt idCity
        , body = Http.emptyBody
        , expect = Http.expectJson (RemoteData.fromResult >> GotTheaters) theatersDecoder
        , timeout = Nothing
        , tracker = Nothing
        }

type alias Theaters =
    List Theater

theatersDecoder : Decoder Theaters
theatersDecoder =
    Decode.list theaterDecoder

type alias Theater =
    { id : Int
    , name : String
    , idCity : Int
    }

theaterDecoder : Decoder Theater
theaterDecoder =
    Decode.succeed Theater
        |> required "idCity" Decode.int
        |> required "name" Decode.string
        |> required "id" Decode.int

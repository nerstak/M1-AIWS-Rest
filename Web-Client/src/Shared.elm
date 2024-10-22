module Shared exposing
    ( Flags
    , Model
    , Msg
    , init
    , subscriptions
    , update
    , view
    )

import Browser.Navigation exposing (Key)
import Element exposing (..)
import Element.Font as Font
import Element.Border as Border
import Element.Background as Background
import Spa.Document exposing (Document)
import Spa.Generated.Route as Route
import Url exposing (Url)
import Element.Input exposing (button)
import API



-- INIT


type alias Flags =
    ()


type alias Model =
    { url : Url
    , key : Key
    , body : List { url : String, label : Element Msg }
    , token : API.Token
    }


init : Flags -> Url -> Key -> ( Model, Cmd Msg )
init flags url key =
    ( Model url key [] API.emptyToken
    , Cmd.none
    )



-- UPDATE


type Msg
    = ReplaceMe


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        ReplaceMe ->
            ( model, Cmd.none )


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none



-- VIEW


view :
    { page : Document msg, toMsg : Msg -> msg }
    -> Model
    -> Document msg
view { page, toMsg } model =
    { title = page.title
    , body =
        [ column [ padding 20, spacing 50, height fill, width fill]
            [ viewHeader model.token
            , column [ height fill, width fill] page.body
            , column [ height fill, width fill, spacing 10 ] <| List.map (mapBodyLinks toMsg) model.body
            ]
        ]
    }

viewHeader : API.Token -> Element msg
viewHeader token =
    row [ spacing 20 ]
        [ viewHeaderLinks { url = Route.toString Route.Top, label = text "Home" }
        , viewHeaderLinks { url = Route.toString Route.Cities, label = text "Cities" }
        , viewHeaderLinks { url = Route.toString Route.Movies, label = text "Movies" }
        , viewHeaderLinks { url = Route.toString Route.Login, label = text "Login" }
        , el [ alignRight ] <| text <| API.tokenToString token
        ]

viewHeaderLinks : { url : String, label : Element msg } -> Element msg
viewHeaderLinks =
    link
        [ padding 10
        , Background.color menuColor
        , Font.color textColor
        ]

contentLinks : { url : String, label : Element msg } -> Element msg
contentLinks =
    link
        [ padding 10
        , Border.rounded 5
        , Background.color elementColor
        , Font.color textColor
        , Font.center
        , centerX
        , width
            (fill
                |> maximum 400
            )
        ]

mapBodyLinks : (Msg -> msg) -> { url : String, label : Element Msg } -> Element msg
mapBodyLinks toMsg { url, label } =
    contentLinks
        { url = url
        , label =  map toMsg label
        }


backColor : Color
backColor =
    rgb255 11 19 43

textColor : Color
textColor =
    rgb255 255 255 255

elementColor : Color
elementColor =
    rgb255 58 80 107

menuColor : Color
menuColor =
    rgb255 91 192 190
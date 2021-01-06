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



-- INIT


type alias Flags =
    ()


type alias Model =
    { url : Url
    , key : Key
    , body : List { url : String, label : Element Msg }
    }


init : Flags -> Url -> Key -> ( Model, Cmd Msg )
init flags url key =
    ( Model url key []
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
        [ column [ padding 20, spacing 20, height fill ]
            [ viewHeader
            , column [ height fill, width fill ] page.body
            ]
        ]
    }

viewHeader : Element msg
viewHeader =
    row [ spacing 20 ]
        [ viewHeaderLinks { url = Route.toString Route.Top, label = text "Homepage" }
        , viewHeaderLinks { url = Route.toString Route.NotFound, label = text "Not found" }
        ]

viewHeaderLinks : { url : String, label : Element msg } -> Element msg
viewHeaderLinks =
    link
        [ padding 10
        , Border.width 1
        , Border.rounded 5
        , mouseOver 
            [ Background.color purple ]
        ]

purple : Color
purple =
    rgb255 238 20 238
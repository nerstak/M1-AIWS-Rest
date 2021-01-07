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
    ( Model url key 
        [ { url = Route.toString Route.Top, label =  text "Cities" }
        , { url = Route.toString Route.NotFound, label = text "Movies" }
        , { url = Route.toString Route.NotFound, label = text "Theaters" }
        ]
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
            [ viewHeader
            , image
                  [ centerX
                  , centerY
                  , width
                        (fill
                            |> maximum 200
                        )
                  ]
                  { src = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F000%2F660%2F143%2Foriginal%2Fvector-movie-theater-icons.jpg&f=1&nofb=1"
                  , description = "logo"
                  }
            , column [ height fill, width fill, spacing 10 ] <| List.map (mapBodyLinks toMsg) model.body
            ]
        ]
    }

viewHeader : Element msg
viewHeader =
    row [ spacing 20 ]
        [ viewHeaderLinks { url = Route.toString Route.Top, label = text "Home" }
        , viewHeaderLinks { url = Route.toString Route.Top, label = text "Cities" }
        , viewHeaderLinks { url = Route.toString Route.Movies, label = text "Movies" }
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
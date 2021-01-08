module Pages.Top exposing (Params, Model, Msg, page)

import Element exposing (Color, Element, centerX, centerY, column, el, explain, fill, height, image, link, maximum, padding, rgb255, spacing, text, width)
import Element.Background as Background
import Element.Font as Font
import Element.Input exposing (button)
import Html exposing (div)
import Shared
import Spa.Document exposing (Document)
import Spa.Generated.Route as Route
import Spa.Page as Page exposing (Page)
import Spa.Url as Url exposing (Url)


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
    {
    }


init : Shared.Model -> Url Params -> ( Model, Cmd Msg )
init shared { params } =
    ( {}, Cmd.none )



-- UPDATE


type Msg
    = Show


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        Show ->
            ( model, Cmd.none )


save : Model -> Shared.Model -> Shared.Model
save model shared =
    {shared | body = []}


load : Shared.Model -> Model -> ( Model, Cmd Msg )
load shared model =
    ( model, Cmd.none )


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none



-- VIEW


view : Model -> Document Msg
view model =
    { title = "Top"
    , body =
    [   column [height fill, width fill, spacing 30]
        [   image
            [ centerX
            , width
                (fill
                    |> maximum 500
                )
            ]
            {   src = "/images/top.png"
            ,   description = "logo"
            }
        , el [width fill, Font.center, Font.color backColor, Font.italic, Font.size 40] <| text "WELCOME !"
        , link [padding 10, Font.center, centerX, Background.color backColor, Font.color white, Font.size 17] { url = Route.toString Route.Movies, label = text "LET ME SEE !" }
        ]
    ]
    }

backColor : Color
backColor =
    rgb255 58 80 107

white : Color
white =
    rgb255 255 255 255
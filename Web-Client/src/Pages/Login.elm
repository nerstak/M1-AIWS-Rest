module Pages.Login exposing (Params, Model, Msg, page)

import Shared
import Spa.Document exposing (Document)
import Spa.Page as Page exposing (Page)
import Spa.Url as Url exposing (Url)
import Http
import Form exposing (Form)
import Form.View
import Element exposing (..)
import RemoteData exposing (WebData, RemoteData(..))
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
    { inputValues : Form.View.Model InputValues
    , token : Result String String
    }


init : Shared.Model -> Url Params -> ( Model, Cmd Msg )
init shared { params } =
    (
        { inputValues = Form.View.idle
            { username = ""
            , password = ""
            }
        , token = Err ""
        }
    , Cmd.none
    )



-- UPDATE


type Msg
    = FormChanged (Form.View.Model InputValues)
    | Login OutputValues
    | GotToken (WebData String)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        FormChanged newForm ->
            ( { model | inputValues = newForm }, Cmd.none )

        Login outputValues ->
            ( model, API.postAuth outputValues.username outputValues.password GotToken )

        GotToken data ->
            updateGotToken data model

updateGotToken : WebData API.Token -> Model -> ( Model, Cmd Msg )
updateGotToken data model =
    case data of
        Success token ->
            updateSuccess token model
        Failure error ->
            updateFailure error model
        _ ->
            (model, Cmd.none)

updateSuccess : String -> Model -> ( Model, Cmd Msg )    
updateSuccess token model =
    ( { model | token = Ok token }, Cmd.none )

updateFailure : Http.Error -> Model -> ( Model, Cmd Msg )
updateFailure error model =
    ({ model | token = Err <| httpErrorToString error }, Cmd.none)

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
    { shared | body = [] }


load : Shared.Model -> Model -> ( Model, Cmd Msg )
load shared model =
    ( model, Cmd.none )


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none



-- VIEW


view : Model -> Document Msg
view model =
    { title = "Login"
    , body =
        [ el [ centerX ] <| html <| Form.View.asHtml
            { onChange = FormChanged
            , action = "Log in"
            , loading = "Logging in..."
            , validation = Form.View.ValidateOnSubmit
            }
            (Form.map Login loginForm) model.inputValues
        , el [ centerX ] <| text <| Maybe.withDefault "" <| Result.toMaybe model.token
        ]
    }

-- FORM

type alias InputValues =
    { username : String
    , password : String
    }

-- usefull if output values are different, here it's more architectural than utilitarian
type alias OutputValues =
    { username : String
    , password : String
    }

loginForm : Form InputValues OutputValues
loginForm =
    Form.succeed OutputValues
        |> Form.append usernameField
        |> Form.append passwordField

usernameField : Form InputValues String
usernameField =
    Form.textField
        { parser = Ok
        , value = .username
        , update = \value values -> { values | username = value }
        , error = always Nothing
        , attributes =
            { label = "Username"
            , placeholder = "Username"
            }
        }

passwordField : Form InputValues String
passwordField =
    Form.textField
        { parser = Ok
        , value = .password
        , update = \value values -> { values | password = value }
        , error = always Nothing
        , attributes =
            { label = "Password"
            , placeholder = "Password"
            }
        }
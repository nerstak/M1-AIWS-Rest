module Pages.Movies exposing (Params, Model, Msg, page)

import Colors
import Element.Font as Font
import Form exposing (Form)
import Form.View
import Shared
import Spa.Document exposing (Document)
import Spa.Page as Page exposing (Page)
import Spa.Url as Url exposing (Url)
import Http
import RemoteData exposing (RemoteData(..), WebData)
import Element exposing (..)
import Spa.Generated.Route as Route
import API exposing (Actor)


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
    ,   inputValues : Form.View.Model InputValues
    ,   token : Result String String
    }

type alias UrlInfo =
    { url : String
    , label : Element Shared.Msg
    }

init : Shared.Model -> Url Params -> ( Model, Cmd Msg )
init shared { params } =
    ( { body = []
     , inputValues = Form.View.idle
                   { name = ""
                   , pegi = ""
                   , duration = ""
                   , realisator = ""
                   , actors = []
                   }
               , token = Err ""
     }
    , API.getMovies GotMovies
    )



-- UPDATE


type Msg
    = FormChanged (Form.View.Model InputValues)
    | AddMovie OutputValues
    | GotMovies (WebData API.Movies)
    | GotToken (WebData String)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        GotMovies data ->
            updateGotMovies data model

        FormChanged newForm ->
                    ( { model | inputValues = newForm }, Cmd.none )

        AddMovie outputValues ->
            ( model, API.postAuth outputValues.name outputValues.name GotToken )

        GotToken data ->
            updateGotToken data model

updateGotMovies : WebData API.Movies -> Model -> ( Model, Cmd Msg )
updateGotMovies data model =
    case data of
        Success movies ->
            updateSuccess movies model
        Failure error ->
            updateFailure error model
        _ ->
            (model, Cmd.none)

updateGotToken : WebData API.Token -> Model -> ( Model, Cmd Msg )
updateGotToken data model =
    case data of
        Success token ->
            updateSuccessToken token model
        Failure error ->
            updateFailure error model
        _ ->
            (model, Cmd.none)
        
updateSuccess : API.Movies -> Model -> ( Model, Cmd Msg )
updateSuccess movies model =
    ( { model | body = List.map movieToUrlInfo movies }, Cmd.none )

updateSuccessToken : String -> Model -> ( Model, Cmd Msg )
updateSuccessToken token model =
    ( { model | token = Ok token }, Cmd.none )

movieToUrlInfo : API.Movie -> UrlInfo
movieToUrlInfo movie =
    { url = Route.toString <| Route.Movies__IdMovie_Int__Cities { idMovie = movie.id }
    , label = column [ height fill, width fill]
        [
          image
          [ centerX
          , centerY
          , spacing 10
          , width
                (fill
                    |> maximum 200
                )
          ]
          { src = "/images/movie.png"
          , description = "logo"
          }
        , el [] <| text "\n"
        ,   movieTitle movie
        ,   movieSubTitle movie
        ,   movieActors movie
        ]
    }

movieTitle : API.Movie -> Element msg
movieTitle movie =
   row [width fill]
   [    el [ width fill, Font.center, Font.bold] <| text ""
   ,    el [ width fill, Font.center, Font.bold] <| text (String.toUpper movie.title)
   ,    el [ width fill, Font.center, Font.bold] <| text ("+" ++ (String.fromInt movie.minimumAge))
   ]

movieSubTitle : API.Movie -> Element msg
movieSubTitle movie =
     link [ width fill, Font.center, Font.italic, Font.color Colors.grey]
            { url = ""
            , label = text <|
                                "\n" ++ movie.duration ++
                                " | " ++ movie.direction
            }


movieActors : API.Movie -> Element msg
movieActors movie =
     link [ width fill, Font.center, Font.color Colors.orange]
            { url = ""
            , label = text <|
                              (List.foldl (++) "" <| List.map actorToString movie.actors)
            }


actorToString : API.Actor -> String
actorToString actor =
    "\n" ++ actor.name

updateFailure : Http.Error -> Model -> ( Model, Cmd Msg )
updateFailure error model =
    ({ model | body =
        [ { url = Route.toString Route.Movies
          , label = text <| httpErrorToString error
          } ]
     }, Cmd.none)

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
    { title = "Movies"
    , body =
        [ el [ centerX ] <| html <| Form.View.asHtml
            { onChange = FormChanged
            , action = "Post new movie"
            , loading = "Adding..."
            , validation = Form.View.ValidateOnSubmit
            }
            (Form.map AddMovie moviesForm) model.inputValues
        , el [ centerX ] <| text <| Maybe.withDefault "" <| Result.toMaybe model.token
        ]
    }


-- FORM

type alias InputValues =
    { name : String
    , pegi : String
    , duration : String
    , realisator : String
    , actors : List Actor
    }

-- usefull if output values are different, here it's more architectural than utilitarian
type alias OutputValues =
    { name : String
    , pegi : String
    , duration : String
    , realisator : String
    , actors : List Actor
    }

moviesForm : Form InputValues OutputValues
moviesForm =
    Form.succeed OutputValues
        |> Form.append nameField
        |> Form.append pegiField
        |> Form.append durationField
        |> Form.append realisatorField
        |> Form.append actorsForm


nameField : Form { r | name : String } String
nameField =
    Form.textField
        { parser = Ok
        , value = .name
        , update = \value values -> { values | name = value }
        , error = always Nothing
        , attributes =
            { label = "Name"
            , placeholder = "Name"
            }
        }

pegiField : Form { r | pegi : String } String
pegiField =
    Form.textField
        { parser = Ok
        , value = .pegi
        , update = \value values -> { values | pegi = value }
        , error = always Nothing
        , attributes =
            { label = "Minimum age"
            , placeholder = "Minimum age"
            }
        }

durationField : Form { r | duration : String } String
durationField =
    Form.textField
        { parser = Ok
        , value = .duration
        , update = \value values -> { values | duration = value }
        , error = always Nothing
        , attributes =
            { label = "Duration"
            , placeholder = "Duration"
            }
        }

realisatorField : Form { r | realisator : String } String
realisatorField =
    Form.textField
        { parser = Ok
        , value = .realisator
        , update = \value values -> { values | realisator = value }
        , error = always Nothing
        , attributes =
            { label = "Realisator"
            , placeholder = "Realisator"
            }
        }

type alias Actor =
    { name : String
    }

actorForm : Int -> Form Actor Actor
actorForm id =
    Form.succeed Actor
            |> Form.append actorNameField

actorsForm : Form { r | actors : List Actor } (List Actor)
actorsForm =
    Form.list
        { default =
            { name = ""
            }
        , value = .actors
        , update = \value values -> { values | actors = value }
        , attributes =
            { label = "Actors"
            , add = Just "Add actor"
            , delete = Just "-"
            }
        }
        actorForm

actorNameField : Form { r | name : String } String
actorNameField =
    Form.textField
        { parser = Ok
        , value = .name
        , update = \value values -> { values | name = value }
        , error = always Nothing
        , attributes =
            { label = "Actor Name"
            , placeholder = "Actor Name"
            }
        }
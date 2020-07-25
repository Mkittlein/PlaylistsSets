package Kittlein;


import java.io.IOException;
import java.net.URI;

import Kittlein.Wrapper.AuthWrapper;
import Kittlein.Wrapper.PlaylistsWrapper;
import Kittlein.Wrapper.UserWrapper;
import com.wrapper.spotify.*;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class App extends Application {
    private final static String clientID = "dde698aa3acb469097f4ae971ea8a419";
    private final static String clientSecret = "78fc3177ee9640afa0517df0d920b6b7";
    private final static URI redirectURI = SpotifyHttpManager.makeUri("http://localhost:8888/");
    private  static Stage stage;
    private SpotifyApi spotifyApi;
    private AuthWrapper authWrapper;
    private PlaylistsWrapper playlistsWrapper;
    private UserWrapper userWrapper;
    private Controller controller;



    public void iniciar(Controller main_controller) throws IOException {
        spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectURI)
            .build();
        this.controller=main_controller;
        authWrapper = new AuthWrapper(spotifyApi);
        authWrapper.LogIn();//Abre una ventana en el navegador por defecto del sistema y e intenta autorizarse con Spotify
        playlistsWrapper = new PlaylistsWrapper(spotifyApi);
        userWrapper = new UserWrapper(spotifyApi);
        controller.setPlaylistsWrapper(playlistsWrapper);
        controller.setUserWrapper(userWrapper);
        stage.close();
        FXMLLoader appLoader = new FXMLLoader(
                getClass().getResource(
                        "GUI/App.fxml"
                )
        );
        appLoader.setController(controller);
        this.stage=new Stage();
        stage.getIcons().add(new Image(App.class.getResourceAsStream("GUI/Logo.png")));
        stage.show();
        stage.setTitle("Playlists Sets - "+userWrapper.getName());
        Parent root = appLoader.load();

        stage.setScene(new Scene(root));

    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("GUI/Login.fxml"));
        primaryStage.setTitle("PlaylistSets - Login");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("GUI/Logo.png")));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

package Kittlein.POO;



import java.net.URI;

import Kittlein.POO.Wrapper.AuthWrapper;
import Kittlein.POO.Wrapper.PlaylistsWrapper;
import Kittlein.POO.Wrapper.UserWrapper;
import com.wrapper.spotify.*;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    private final static String clientID = "dde698aa3acb469097f4ae971ea8a419";
    private final static String clientSecret = "78fc3177ee9640afa0517df0d920b6b7";
    private final static URI redirectURI = SpotifyHttpManager.makeUri("http://localhost:8888/");




    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        primaryStage.setTitle("Trabajo POO Manuel Kittlein");
        primaryStage.setScene(new Scene(root, 300, 275));

        primaryStage.show();
    }


    public static void main(String[] args) {
        /*
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientID)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectURI)
                .build();

        AuthWrapper authWrapper = new AuthWrapper(spotifyApi);
        authWrapper.LogIn();//Abre una ventana en el navegador por defecto del sistema y e intenta autorizarse con Spotify
        PlaylistsWrapper playlistsWrapper = new PlaylistsWrapper(spotifyApi);
        UserWrapper userWrapper = new UserWrapper(spotifyApi);
*/




        launch(args);
    }
}

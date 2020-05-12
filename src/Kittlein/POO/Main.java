package Kittlein.POO;


import java.io.IOException;
import java.net.URI;

import com.wrapper.spotify.*;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private final static String clientID = "dde698aa3acb469097f4ae971ea8a419";
    private final static String clientSecret = "78fc3177ee9640afa0517df0d920b6b7";
    private final static URI redirectURI = SpotifyHttpManager.makeUri("http://localhost:8888/");
    private static String code = "";
    private static String playlistID = "73qtDn0QVXLXr1rLkVuvK1";
    private static SpotifyApi spotifyApi;
    private static GetPlaylistRequest getPlaylistRequest;




    private static Playlist getPlaylist_Sync(GetPlaylistRequest getPlaylistRequest) {
        try {
            return getPlaylistRequest.execute();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Trabajo POO Manuel Kittlein");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientID)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectURI)
                .build();
        AuthManager authManager = new AuthManager(spotifyApi);
        System.out.println("Ahora pedimos credenciales");
        authManager.clientCredentials_Sync();
        System.out.println("Ahora pedimos el auth code");
        authManager.authorizationCodeUri_Authorize();

        //authManager.authorizationCode_Sync();
        getPlaylistRequest = spotifyApi.getPlaylist(playlistID).build();
        System.out.println(getPlaylist_Sync(getPlaylistRequest).getName());
        launch(args);
    }
}

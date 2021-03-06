package PlaylistSets;


import PlaylistSets.Wrapper.AuthWrapper;
import PlaylistSets.Wrapper.PlaylistsWrapper;
import PlaylistSets.Wrapper.UserWrapper;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;


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



    public void iniciar() throws IOException { //inicia la App principal, con el App.fxml y el Controller principal
        spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientID)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectURI)
            .build();
        authWrapper = new AuthWrapper(spotifyApi);
        authWrapper.LogIn();//Abre una ventana en el navegador por defecto del sistema y e intenta autorizarse con Spotify
        playlistsWrapper = new PlaylistsWrapper(spotifyApi);
        userWrapper = new UserWrapper(spotifyApi);
        FXMLLoader appLoader = new FXMLLoader(
                getClass().getResource(
                        "GUI/App.fxml"
                )
        );
        Parent root = appLoader.load();
        this.controller=appLoader.getController();
        controller.setUserWrapper(userWrapper);
        controller.setPlaylistsWrapper(playlistsWrapper);
        controller.iniciar();
        stage.getIcons().add(new Image(App.class.getResourceAsStream("GUI/res/Logo.png")));
        stage.setTitle("Playlists Sets - "+userWrapper.getName());
        stage.setScene(new Scene(root));
        stage.setResizable(true);

    }


    @Override
    public void start(Stage primaryStage) throws Exception{ //Genera el dialogo del login
        this.stage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("GUI/Login.fxml"));
        primaryStage.setTitle("PlaylistSets - Login");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("GUI/res/Logo.png")));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(App.class,args);
    }
}

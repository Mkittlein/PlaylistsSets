package Kittlein;

import Kittlein.Sets.*;
import Kittlein.Wrapper.PlaylistsWrapper;
import Kittlein.Wrapper.UserWrapper;
import com.wrapper.spotify.SpotifyApi;
import javafx.event.ActionEvent;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


import java.awt.*;
import java.awt.TextField;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    public Button buttonAdd;
    private App app;
    private UserWrapper userWrapper;
    private PlaylistsWrapper playlistsWrapper;
    private List<Playlist> playlists;
    private List<String> operaciones=new ArrayList<>();
    private Playlist playlistOp=new PlaylistSimple(null,null,0,null,null);


    //Variables declaradas en el FXML
    public VBox LogScreen;
    public TextArea LogText;
    public Button LogIn;




    public LoginController() {
    }


    public void LogIn(ActionEvent actionEvent) {
        LogScreen.getChildren().remove(LogText);
        LogScreen.getChildren().remove(LogIn);
        LogScreen.getChildren().add(new ProgressIndicator() );
        LogScreen.fillWidthProperty().setValue(true);
        LogScreen.setAlignment(Pos.CENTER);
        app= new App();
        try {
            app.iniciar();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}

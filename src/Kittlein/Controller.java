package Kittlein;

import Kittlein.Sets.Playlist;
import Kittlein.Wrapper.PlaylistsWrapper;
import Kittlein.Wrapper.UserWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class Controller {
    public ListView PList;
    private App app;
    public Button LogIn;
    public TextArea LogText;
    public VBox LogScreen;
    private UserWrapper userWrapper;
    private PlaylistsWrapper playlistsWrapper;
    List<Playlist> playlists;

    public Controller() {
    }

    public void LogIn(ActionEvent actionEvent) {
        LogScreen.getChildren().remove(LogText);
        LogScreen.getChildren().remove(LogIn);
        ProgressIndicator P =new ProgressIndicator();
        LogScreen.getChildren().add(new ProgressIndicator() );
        LogScreen.fillWidthProperty().setValue(true);
        LogScreen.setAlignment(Pos.CENTER);
        app= new App();
        try {
            app.iniciar(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        playlists = playlistsWrapper.getListOfCurrentUsersPlaylists();
        System.out.println(playlists);
        updateUi();
    }

    private void updateUi(){
        for (Playlist p : playlists){
            PList.getItems().add(p.getName());
        }
        PList.refresh();
    }

    public void setUserWrapper(UserWrapper userWrapper) {
        this.userWrapper = userWrapper;
    }

    public void setPlaylistsWrapper(PlaylistsWrapper playlistsWrapper) {
        this.playlistsWrapper = playlistsWrapper;
    }
}

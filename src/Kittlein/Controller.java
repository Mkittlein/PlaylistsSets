package Kittlein;

import Kittlein.Wrapper.PlaylistsWrapper;
import Kittlein.Wrapper.UserWrapper;
import javafx.event.ActionEvent;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Controller {
    private App app;
    public Button LogIn;
    public TextArea LogText;
    public VBox LogScreen;
    private UserWrapper userWrapper;
    private PlaylistsWrapper playlistsWrapper;

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

    }

    public void setUserWrapper(UserWrapper userWrapper) {
        this.userWrapper = userWrapper;
    }

    public void setPlaylistsWrapper(PlaylistsWrapper playlistsWrapper) {
        this.playlistsWrapper = playlistsWrapper;
    }
}

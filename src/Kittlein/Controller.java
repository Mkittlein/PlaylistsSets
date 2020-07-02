package Kittlein;

import Kittlein.Sets.Playlist;
import Kittlein.Sets.PlaylistSimple;
import Kittlein.Wrapper.PlaylistsWrapper;
import Kittlein.Wrapper.UserWrapper;
import javafx.collections.ObservableList;
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
import java.awt.ScrollPane;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private App app;
    private UserWrapper userWrapper;
    private PlaylistsWrapper playlistsWrapper;
    private List<Playlist> playlists;
    private List<String> operaciones=new ArrayList<>();


    //Variables declaradas en el FXML
    public ListView PList;
    public Label PlaylistSize;
    public Hyperlink PlaylistLink;
    public ImageView PlaylistImage;
    public Button ButtonDiferencia;
    public Button ButtonResta;
    public Button ButtonInterseccion;
    public Button ButtonUnion;
    public Button LogIn;
    public VBox Contenido;
    public TextArea LogText;
    public VBox LogScreen;
    public Label PlaylistName;
    public ListView PlaylistTracks;
    public ListView OpList;


    public Controller() {
    }

    public void PlaylistSelected(){
        PlaylistSimple p = (PlaylistSimple) PList.getSelectionModel().getSelectedItem();
        PlaylistName.setText(p.getName());
        PlaylistSize.setText(Integer.toString(p.getSize()));
        PlaylistLink.setText(p.getId());
        PlaylistLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(p.getUrl());
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        PlaylistImage.setImage(p.getImage());
        PlaylistTracks.getItems().clear();
        PlaylistTracks.getItems().addAll(p.getCanciones());
        PlaylistTracks.refresh();
    }

    public void LogIn(ActionEvent actionEvent) {
        LogScreen.getChildren().remove(LogText);
        LogScreen.getChildren().remove(LogIn);
        LogScreen.getChildren().add(new ProgressIndicator() );
        LogScreen.fillWidthProperty().setValue(true);
        LogScreen.setAlignment(Pos.CENTER);
        app= new App();
        try {
            app.iniciar(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> aux =new ArrayList<String>();
        aux.add("");
        OpList.getItems().addAll(aux);//Dummy para que no tenga un fondo blanco hasta que se le carguen operaciones
        playlists = playlistsWrapper.getListOfCurrentUsersPlaylists();
        updateUi();
    }

    private void updateUi(){
        PList.getItems().addAll(playlists);
        PList.getSelectionModel().select(0);
        PList.refresh();
        OpList.getItems().addAll(operaciones);
        OpList.refresh();
        PlaylistSelected();
    }



    public void setUserWrapper(UserWrapper userWrapper) {
        this.userWrapper = userWrapper;
    }

    public void setPlaylistsWrapper(PlaylistsWrapper playlistsWrapper) {
        this.playlistsWrapper = playlistsWrapper;
    }

    public void addPlaylist(MouseEvent mouseEvent) {
            PlaylistSimple p = (PlaylistSimple) PList.getSelectionModel().getSelectedItem();
            operaciones.add(p.getName());
            OpList.getItems().clear();
            OpList.getItems().addAll(operaciones);
            OpList.refresh();
            ButtonUnion.setDisable(false);
            ButtonInterseccion.setDisable(false);
            ButtonResta.setDisable(false);
            ButtonDiferencia.setDisable(false);
    }
}

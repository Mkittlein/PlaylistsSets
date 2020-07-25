package Kittlein;

import Kittlein.Sets.*;
import Kittlein.Wrapper.PlaylistsWrapper;
import Kittlein.Wrapper.UserWrapper;
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

public class Controller {

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
    public ListView PList;
    public Label PlaylistSize;
    public Hyperlink PlaylistLink;
    public ImageView PlaylistImage;
    public Button ButtonDiferencia;
    public Button ButtonResta;
    public Button ButtonInterseccion;
    public Button ButtonUnion;
    public TextField textNombrePlaylist;
    public VBox Contenido;
    public Label PlaylistName;
    public ListView PlaylistTracks;
    public ListView OpList;
    public Button buttonGuardarPlaylist;



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

    public void addPlaylistSimple(MouseEvent mouseEvent) {
            PlaylistSimple p = (PlaylistSimple) PList.getSelectionModel().getSelectedItem();
            playlistOp.addPlaylist(p);
            addOperacion(p.getName());
            enableOperations();
    }

    private void enableOperations(){
        buttonAdd.setDisable(true);
        ButtonUnion.setDisable(false);
        ButtonInterseccion.setDisable(false);
        ButtonResta.setDisable(false);
        ButtonDiferencia.setDisable(false);
        buttonGuardarPlaylist.setDisable(false);
    }
    private void disableOperations(){
        buttonAdd.setDisable(false);
        ButtonUnion.setDisable(true);
        ButtonInterseccion.setDisable(true);
        ButtonResta.setDisable(true);
        ButtonDiferencia.setDisable(true);
        buttonGuardarPlaylist.setDisable(true);
    }

    private void addOperacion(String op){
        operaciones.add(op);
        OpList.getItems().clear();
        OpList.getItems().addAll(operaciones);
        OpList.refresh();
    }

    public void diferencia(MouseEvent mouseEvent) {
    disableOperations();
    PlaylistDiferencia p= new PlaylistDiferencia(playlistOp);
    playlistOp=p;
    addOperacion("△");

    }

    public void resta(MouseEvent mouseEvent) {
        disableOperations();
        PlaylistResta p= new PlaylistResta(playlistOp);
        playlistOp=p;
        addOperacion("-");
    }

    public void interseccion(MouseEvent mouseEvent) {
        disableOperations();
        PlaylistInterseccion p= new PlaylistInterseccion(playlistOp);
        playlistOp=p;
        addOperacion("∩");
    }

    public void union(MouseEvent mouseEvent) {
        disableOperations();
        PlaylistUnion p= new PlaylistUnion(playlistOp);
        playlistOp=p;
        addOperacion("∪");
    }

    public void guardarPlaylist(MouseEvent mouseEvent) {
        System.out.println("Guardada Playlist: "+playlistOp);
        System.out.println("Tamaño: "+playlistOp.getSize());
        System.out.println(playlistOp.getCanciones());
        String name=playlistOp.getName();
        /*
        if (textNombrePlaylist.getText().length()>0){
            name= textNombrePlaylist.getText();
        }*/
        PlaylistSimple p = new PlaylistSimple("",name,playlistOp.getSize(),"",playlistsWrapper,playlistOp.getCanciones());

        p.guardarPlaylist();
        //disableOperations();
    }
}

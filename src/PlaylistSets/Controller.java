package PlaylistSets;
import PlaylistSets.Sets.*;
import PlaylistSets.Wrapper.PlaylistsWrapper;
import PlaylistSets.Wrapper.UserWrapper;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Controller {
    private UserWrapper userWrapper;
    private PlaylistsWrapper playlistsWrapper;
    private List<Playlist> playlists;
    private List<String> operaciones;
    private Playlist playlistOp;
    private List<String> vacia;
    private Image defaultImage;


    //Variables declaradas en el FXML
   public Button buttonGuardarPlaylist;
    public Button buttonAdd;
    public ListView PList;
    public Label PlaylistSize;
    public Hyperlink PlaylistLink;
    public ImageView PlaylistImage;
    public Button ButtonDiferencia;
    public Button ButtonResta;
    public Button ButtonInterseccion;
    public Button ButtonUnion;
    public TextField TextNombrePlaylist;
    public VBox Contenido;
    public Label PlaylistName;
    public ListView PlaylistTracks;
    public ListView OpList;



    public Controller() {
        vacia=new ArrayList<>();
        vacia.add("");
       try {
           defaultImage = new Image(new FileInputStream(new File("./src/PlaylistSets/GUI/res/Logo.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void iniciar(){
        operaciones=new ArrayList<>();
        playlists= new ArrayList<Playlist>();
        List<String> aux =new ArrayList<String>();
        playlistOp=new PlaylistSimple(null, null, 0, null, playlistsWrapper, new HashSet<Cancion>());
        playlists = new ArrayList<Playlist>();
        playlists.clear();
        playlists = playlistsWrapper.getListOfCurrentUsersPlaylists();
        operaciones.clear();
        resetUi();
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
        if(p.getSize()>0)
            PlaylistTracks.getItems().addAll(p.getCanciones());
        else
            PlaylistTracks.getItems().addAll(vacia);
        PlaylistTracks.refresh();
    }

    private void resetUi(){
        disableOperations();

        PList.getItems().clear();
        OpList.getItems().clear();

        OpList.getItems().addAll(vacia);
        PList.getItems().addAll(playlists);

        PList.refresh();
        OpList.refresh();

        PList.getSelectionModel().select(0);
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

        if (TextNombrePlaylist.getText()!=null && TextNombrePlaylist.getText().length()>0){
            name= TextNombrePlaylist.getText();
        }
        PlaylistSimple p = new PlaylistSimple("",name,playlistOp.getSize(),"",playlistsWrapper,playlistOp.getCanciones());
        p.guardarPlaylist();
        iniciar();
    }

    public void MostrarTemas(MouseEvent mouseEvent) {
        PlaylistName.setText(playlistOp.getName());
        PlaylistSize.setText(Integer.toString(playlistOp.getSize()));
        PlaylistLink.setText("");
        PlaylistImage.setImage(defaultImage);
        PlaylistTracks.getItems().clear();
        if(playlistOp.getSize()>0)
            PlaylistTracks.getItems().addAll(playlistOp.getCanciones());
        else
            PlaylistTracks.getItems().addAll(vacia);
        PlaylistTracks.refresh();

    }
}

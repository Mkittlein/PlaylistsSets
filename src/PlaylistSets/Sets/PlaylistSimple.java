package PlaylistSets.Sets;

import PlaylistSets.Wrapper.PlaylistsWrapper;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class PlaylistSimple implements Playlist {
    private String id;
    private String name;
    private int size;
    private String url;
    private Image image;
    private boolean cached;
    private Set<Cancion> canciones = new HashSet<Cancion>();
    private PlaylistsWrapper playlistsWrapper;

    public PlaylistSimple(String id, String name, int size, String url, PlaylistsWrapper playlistsWrapper){
        this.id=id;
        this.name=name;
        this.size=size;
        this.cached=false;
        this.url=url;
        this.playlistsWrapper = playlistsWrapper;
    }

    public PlaylistSimple(String id, String name, int size, String url, PlaylistsWrapper playlistsWrapper,Set<Cancion> canciones){
        this.id=id;
        this.name=name;
        this.size=size;
        this.cached=true;
        this.url=url;
        this.playlistsWrapper = playlistsWrapper;
        this.canciones=canciones;
    }


    private void actualizarContenido(){
        URL url = null;
        try {
            if(playlistsWrapper.getImage(id)!= null){
                url = new URL(playlistsWrapper.getImage(id).getUrl());
                image = SwingFXUtils.toFXImage(ImageIO.read(url),null);}
            else{
                image =new Image(new FileInputStream(new File("./src/Kittlein/GUI/res/Logo.png")));

            }
            canciones.addAll(playlistsWrapper.getCanciones(id,size));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public javafx.scene.image.Image getImage() {
        if (!cached){
            actualizarContenido();
            cached=true;
        }
        return image;
    }

    public String toString(){return this.getName();}

    public Set<Cancion> getCanciones() {
        Set<Cancion> aux = new HashSet<Cancion>();
        if (cached){
            aux.addAll(canciones);
            return aux;
        }
        else{
            actualizarContenido();
            cached=true;
        }
        aux.addAll(canciones);
        return canciones;
    }

    public void addPlaylist(PlaylistSimple p){ //Necesario para poder implementar las operaciones con las playlist mediante la interfaz Playlist sin tener que preguntar la clase del objeto.
                                        // La idea es que si a una Playlist simple le agregamos una playlistsimple, generamos una copia y "pisamos" el contenido.
                                        // Esto es necesario solo para la primera playlist que uso en las operaciones, podría mejorarse creando otra clase que implemente playlist que sea solo para esa playlist inicial cuyo contenido no importa o complicando innecesariamente el codigo del controlador.
        this.id=((PlaylistSimple)p).id;
        this.name=((PlaylistSimple)p).name;
        this.size=((PlaylistSimple)p).size;
        this.url=((PlaylistSimple)p).url;
        this.image=((PlaylistSimple)p).image;
        this.cached=((PlaylistSimple)p).cached;
        this.canciones=((PlaylistSimple)p).canciones;
        this.playlistsWrapper=((PlaylistSimple)p).playlistsWrapper;
    }

    public void guardarPlaylist() {
        playlistsWrapper.createPlaylist(this);
    }

    public String getName(){
        return this.name;
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public URI getUrl() throws URISyntaxException {
        return new URI(url);
    }
}

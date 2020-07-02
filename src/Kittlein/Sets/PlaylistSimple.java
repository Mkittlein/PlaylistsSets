package Kittlein.Sets;

import Kittlein.Wrapper.PlaylistsWrapper;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class PlaylistSimple extends Playlist {
    private String id;
    private String name;
    private int size;
    private String url;
    private Image image;
    private boolean cached;
    private Set<Cancion> canciones = new HashSet<Cancion>();



    public PlaylistSimple(String id, String name, int size, String url, PlaylistsWrapper playlistsWrapper){
        this.id=id;
        this.name=name;
        this.size=size;
        this.cached=false;
        this.url=url;
        this.playlistsWrapper = playlistsWrapper;
    }

    private void actualizarContenido(){
        URL url = null;
        try {
            url = new URL(playlistsWrapper.getImage(id).getUrl());
        image = SwingFXUtils.toFXImage(ImageIO.read(url),null);
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

    public Set<Cancion> getCanciones() {
        if (cached)
            return canciones;
        else{
            actualizarContenido();
            cached=true;
        }
        return canciones;
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

package Kittlein.Sets;

import Kittlein.Wrapper.PlaylistsWrapper;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.text.Element;
import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class PlaylistSimple extends Playlist {
    private int size;
    private String url;
    private Image image;

    public PlaylistSimple(String id, String name){
        this.id=id;
        this.name=name;
        this.cached=false;
    }

    public PlaylistSimple(String id, String name, int size, String url, PlaylistsWrapper playlistsWrapper){
        this.id=id;
        this.name=name;
        this.size=size;
        this.cached=false;
        this.url=url;
        this.playlistsWrapper = playlistsWrapper;
    }

    private void requestContent(){

        URL url = null;
        try {
            url = new URL(playlistsWrapper.getImage(id).getUrl());
        image = SwingFXUtils.toFXImage(ImageIO.read(url),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public javafx.scene.image.Image getImage() {
        if (!cached){
            requestContent();
            cached=true;
        }
        return image;
    }

    public List<Cancion> getTracks() {
        if (cached)
            return tracks;
        else{
            requestContent();
            cached=true;
        }
        return tracks;
    }

    public int getSize() {
        return size;
    }

    public URI getUrl() throws URISyntaxException {
        return new URI(url);
    }
}

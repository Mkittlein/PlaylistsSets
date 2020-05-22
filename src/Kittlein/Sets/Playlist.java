package Kittlein.Sets;
import Kittlein.Wrapper.PlaylistsWrapper;

import java.util.List;

public abstract class Playlist {

protected String id;
protected String name;
protected boolean cached;
protected List<Cancion> tracks;
protected PlaylistsWrapper playlistsWrapper;

    public String toString(){
        return name;
    }

    public String getName(){
        return name;
    }

    public String getId() {
    return id;
}

    public abstract List<Cancion> getTracks();



    public abstract int getSize();





}



package Kittlein.Sets;
import Kittlein.Wrapper.PlaylistsWrapper;

import java.util.List;
import java.util.Set;

public interface Playlist {




    public abstract Set<Cancion> getCanciones();

    public abstract String getName();

    public abstract int getSize();

    public void add(Playlist p);


}



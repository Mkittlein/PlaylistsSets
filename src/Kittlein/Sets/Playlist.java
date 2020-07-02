package Kittlein.Sets;
import Kittlein.Wrapper.PlaylistsWrapper;

import java.util.List;
import java.util.Set;

public abstract class Playlist {




protected PlaylistsWrapper playlistsWrapper;




    public String toString(){return getName();}

    public abstract Set<Cancion> getCanciones();

    public abstract String getName();

    public abstract int getSize();





}



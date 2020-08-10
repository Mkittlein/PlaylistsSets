package PlaylistSets.Sets;

import java.util.Set;

public interface Playlist {




    public abstract Set<Cancion> getCanciones();

    public abstract String toString();

    public abstract String getName();

    public abstract int getSize();

    public abstract void addPlaylist(Playlist p);



}



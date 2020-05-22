package Kittlein.Sets;

import java.util.List;

public class PlaylistCompuesta extends Playlist {



    public List<Cancion> getTracks(){
        return tracks;
    }

    @Override
    public int getSize() {
        return 0;
    }
}

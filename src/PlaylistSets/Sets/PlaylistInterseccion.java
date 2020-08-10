package PlaylistSets.Sets;

import java.util.Set;

public class PlaylistInterseccion extends PlaylistCompuesta {


    public PlaylistInterseccion(Playlist p1, Playlist p2){
        playlist1=p1;
        playlist2=p2;
    }

    public PlaylistInterseccion(Playlist p1){
        this.playlist1=p1;
        playlist2=null;
    }

    @Override
    public Set<Cancion> getCanciones(){
        Set<Cancion> aux = playlist1.getCanciones();
        if(playlist2!=null)
            aux.retainAll(playlist2.getCanciones());
        return aux;
    }

    @Override
    public String getName() {
        if(playlist2!=null)
            return playlist1.getName() +" ∩ "+playlist2.getName();
        else return playlist1.getName();
    }

    @Override
    public int getSize() {
        Set<Cancion> aux = playlist1.getCanciones();
        if(playlist2!=null)
            aux.retainAll(playlist2.getCanciones());
        return aux.size();
    }
}

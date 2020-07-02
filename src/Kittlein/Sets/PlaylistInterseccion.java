package Kittlein.Sets;

import java.util.Set;

public class PlaylistInterseccion extends Playlist {

    private Playlist playlist1;

    private Playlist playlist2;

    public PlaylistInterseccion(Playlist p1, Playlist p2){
        playlist1=p1;
        playlist2=p2;
    }

    public PlaylistInterseccion(Playlist p1){
        this.playlist1=p1;
        playlist2=new PlaylistSimple("","",0,"",null);
    }

    public Set<Cancion> getCanciones(){
        Set<Cancion> aux = playlist1.getCanciones();
        aux.retainAll(playlist2.getCanciones());
        return aux;
    }

    public void add(Playlist p2){
        this.playlist2=p2;
    }


    @Override
    public String getName() {
        return playlist1.getName() +" ∩ "+playlist2.getName();
    }

    @Override
    public int getSize() {
        Set<Cancion> aux = playlist1.getCanciones();
        aux.retainAll(playlist2.getCanciones());
        return aux.size();
    }
}

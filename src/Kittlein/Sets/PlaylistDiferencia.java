package Kittlein.Sets;

import java.util.Set;

public class PlaylistDiferencia extends Playlist {
    private Playlist playlist1;

    private Playlist playlist2;

    public PlaylistDiferencia(Playlist p1, Playlist p2){
        playlist1=p1;
        playlist2=p2;
    }

    public Set<Cancion> getCanciones(){
        Set<Cancion> aux1= playlist1.getCanciones();
        Set<Cancion> aux2= playlist2.getCanciones();
        aux1.removeAll(aux2);
        aux2.retainAll(aux1);
        aux1.addAll(aux2);
        return aux1;
    }

    @Override
    public int getSize() {
        Set<Cancion> aux1= playlist1.getCanciones();
        Set<Cancion> aux2= playlist2.getCanciones();
        aux1.removeAll(aux2);
        aux2.retainAll(aux1);
        aux1.addAll(aux2);
        return aux1.size();
    }
    public String getName(){
        return playlist1.getName()+" △ "+playlist2.getName();
    }
}


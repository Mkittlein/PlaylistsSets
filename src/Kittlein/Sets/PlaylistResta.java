package Kittlein.Sets;

import java.util.Set;

public class PlaylistResta extends Playlist{
        private Playlist playlist1;

        private Playlist playlist2;

        public PlaylistResta(Playlist p1, Playlist p2){
            playlist1=p1;
            playlist2=p2;
        }

        public Set<Cancion> getCanciones(){
            Set<Cancion> aux = playlist1.getCanciones();
            aux.removeAll(playlist2.getCanciones());
            return aux;
        }

        @Override
        public int getSize() {
            Set<Cancion> aux = playlist1.getCanciones();
            aux.removeAll(playlist2.getCanciones());
            return aux.size();
        }

        public String getName(){
            return playlist1.getName()+" - "+playlist2.getName();
        }
}
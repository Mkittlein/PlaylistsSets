package Kittlein.Sets;

public abstract class PlaylistCompuesta implements Playlist {
    protected Playlist playlist1;
    protected  Playlist playlist2;

    public void add(Playlist p2){
        this.playlist2=p2;
    }
}
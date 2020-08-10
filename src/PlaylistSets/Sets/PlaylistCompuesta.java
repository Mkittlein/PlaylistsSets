package PlaylistSets.Sets;

public abstract class PlaylistCompuesta implements Playlist {
    protected Playlist playlist1;
    protected  Playlist playlist2;

    @Override
    public void addPlaylist(Playlist p2){
        this.playlist2=p2;
    }

    @Override
    public String toString(){return this.getName();}
}

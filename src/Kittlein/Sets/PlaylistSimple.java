package Kittlein.Sets;

public class PlaylistSimple extends Playlist {
    private int size;



    public PlaylistSimple(String id, String name){
        this.id=id;
        this.name=name;
        this.cached=false;
    }
    public PlaylistSimple(String id, String name,int size){
        this.id=id;
        this.name=name;
        this.size=size;
        this.cached=false;
    }
}

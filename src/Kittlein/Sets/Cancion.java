package Kittlein.Sets;

public class Cancion {
    private String id;
    private String name;
    private String url;
    private String artistname;
    private String artistid;


    @Override
    public boolean equals(Object o) {
        return id.equals(((Cancion) o).getId());
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
       return id;
    }

    public String getArtistname() {
        return artistname;
    }

    public String getArtistid() {
        return artistid;
    }
}
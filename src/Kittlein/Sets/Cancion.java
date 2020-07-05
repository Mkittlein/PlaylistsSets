package Kittlein.Sets;


import java.util.Objects;

public class Cancion {
    private String id;
    private String name;
    private String url;
    private String artistname;
    private String artistid;
    private String albumname;
    private String albumid;

    public Cancion(String name, String id, String url, String artistname, String artistid, String albumname, String albumid) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.artistname = artistname;
        this.artistid = artistid;
        this.albumname = albumname;
        this.albumid = albumid;
    }

    public String toString(){
        return name;
    }


    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this == o) {
            return true;
        }

        Cancion cancion = (Cancion) o;
        return id.equals(cancion.getId());//ID debería ser suficiente, si dos objetos cancion tienen ids iguales pero difieren en otros atributos hubo un problema cargando desde la API
    }

    public int hashCode() {
        return Objects.hash(id);
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

    public String getAlbumname() {
        return albumname;
    }

    public String getAlbumid() {
        return albumid;
    }
}
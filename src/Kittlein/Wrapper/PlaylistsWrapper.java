package Kittlein.Wrapper;

import Kittlein.Sets.Cancion;
import Kittlein.Sets.Playlist;
import Kittlein.Sets.PlaylistSimple;
import com.google.gson.JsonArray;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.playlists.*;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaylistsWrapper {
    private SpotifyApi spotifyApi;
    private   GetPlaylistsItemsRequest getPlaylistsItemsRequest;
    private GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest;
    private CreatePlaylistRequest createPlaylistRequest;
    private AddItemsToPlaylistRequest addItemsToPlaylistRequest;

    public List<Playlist> getListOfCurrentUsersPlaylists() {
        List<Playlist> PL = new ArrayList<Playlist>();
        try {
            Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();
            int total= playlistSimplifiedPaging.getTotal();

            int offset=0;
            while (total>0) {
                getListOfCurrentUsersPlaylistsRequest = spotifyApi
                        .getListOfCurrentUsersPlaylists()
                        .limit(50)
                        .offset(offset)
                        .build();
                for (PlaylistSimplified p : playlistSimplifiedPaging.getItems()) {
                    PL.add(new PlaylistSimple(p.getId(), p.getName(), p.getTracks().getTotal(),p.getUri(),this));
                }
                offset+=50;
                total-=50;
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        getListOfCurrentUsersPlaylistsRequest= spotifyApi
                .getListOfCurrentUsersPlaylists()
                .limit(50)
                .offset(0)
                .build();
        return PL;
    }

    public Image getImage(String id){
        GetPlaylistCoverImageRequest getPlaylistCoverImageRequest = spotifyApi
                .getPlaylistCoverImage(id)
                .build();
        try {
            Image[] images = getPlaylistCoverImageRequest.execute();
            if(images.length>=1)
                return images[0];
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public PlaylistsWrapper(SpotifyApi spotifyApi){
        getListOfCurrentUsersPlaylistsRequest = spotifyApi
                .getListOfCurrentUsersPlaylists()
                .limit(50)
                .offset(0)
                .build();
        this.spotifyApi=spotifyApi;
    }

    public Set<Cancion> getCanciones(String id, int size) {
        int total= size;
        int offset=0;
        Set<Cancion> tracks= new HashSet<Cancion>();
        while (total>0) {
            getPlaylistsItemsRequest = spotifyApi
                    .getPlaylistsItems(id)
//          .fields("description")
            .limit(50)
            .offset(offset)
//          .market(CountryCode.SE)
//          .additionalTypes("track,episode")
                    .build();
            offset+=50;
            total-=50;
            try {
                Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();
                for (PlaylistTrack i : playlistTrackPaging.getItems()){
                    Track t = (Track) i.getTrack();
                    if (t!=null && !t.getUri().contains("local")){//No los temas locales generan errores cuando quiero crear una playlist, ya que solo los tiene el creador de la playlist original, que generalmente no es el usuario
                            tracks.add(new Cancion(t.getName(), t.getId(), t.getPreviewUrl(), t.getArtists()[0].getName(), t.getArtists()[0].getId(), t.getAlbum().getName(), t.getAlbum().getId(),t.getUri()));
                    }
                }
            } catch (IOException | SpotifyWebApiException | ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return tracks;
    }


    public void createPlaylist(Playlist p){
        String id="";
        try {
            id=spotifyApi.getCurrentUsersProfile().build().execute().getId();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        createPlaylistRequest = spotifyApi.createPlaylist(id,p.getName())
          .collaborative(false)
          .public_(true)
          .description("🎶🎵🎶Creada con PlaylistSets🎶🎵🎶")
                .build();
        try {


            com.wrapper.spotify.model_objects.specification.Playlist playlist = createPlaylistRequest.execute();
            addTracks(playlist,p);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String[][] chunkArray(String[] array) {
        int numOfChunks = (int)Math.ceil((double)array.length / 100);
        String[][] output = new String[numOfChunks][];

        for(int i = 0; i < numOfChunks; ++i) {
            int start = i * 100;
            int length = Math.min(array.length - start, 100);

            String[] temp = new String[length];
            System.arraycopy(array, start, temp, 0, length);
            output[i] = temp;
        }
        return output;
    }


    public void addTracks(com.wrapper.spotify.model_objects.specification.Playlist spotifyPlaylist,Playlist playlist){

        List<String> aux= new ArrayList<>();

        for(Cancion c : playlist.getCanciones()){
         aux.add(c.getUri());
        }
        String[] uris= aux.toArray(new String[0]);
        String[][] urisArray=chunkArray(uris);
        System.out.println("URIS DE LA PLAYLIST: "+aux);

        for(int i=0;i<urisArray.length;i++) {
            try {
                JsonArray jsonArray= new JsonArray();
                for(int j=0;j<urisArray[i].length;j++)
                    jsonArray.add(urisArray[i][j]);
            addItemsToPlaylistRequest = spotifyApi.addItemsToPlaylist(spotifyPlaylist.getId(),jsonArray).build();

            addItemsToPlaylistRequest.execute();
        } catch (IOException | SpotifyWebApiException| ParseException e) {
                e.printStackTrace();
            }
        }

    }

}

package Kittlein.Wrapper;

import Kittlein.Sets.Cancion;
import Kittlein.Sets.Playlist;
import Kittlein.Sets.PlaylistSimple;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.playlists.*;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaylistsWrapper {
    private SpotifyApi spotifyApi;
    private   GetPlaylistsTracksRequest getPlaylistsTracksRequest;
    private GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest;
    private CreatePlaylistRequest createPlaylistRequest;
    private AddTracksToPlaylistRequest addTracksToPlaylistRequest;

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
        } catch (IOException | SpotifyWebApiException e) {
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
        } catch (IOException | SpotifyWebApiException e) {
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
            getPlaylistsTracksRequest = spotifyApi
                    .getPlaylistsTracks(id)
//          .fields("description")
            .limit(50)
            .offset(offset)
//          .market(CountryCode.SE)
//          .additionalTypes("track,episode")
                    .build();
            offset+=50;
            total-=50;
            try {
                Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsTracksRequest.execute();
                int i=0;
                for (PlaylistTrack t : playlistTrackPaging.getItems()){
                    if (t.getTrack()!=null) {
                        i++;
                        tracks.add(new Cancion(t.getTrack().getName(), t.getTrack().getId(), t.getTrack().getPreviewUrl(), t.getTrack().getArtists()[0].getName(), t.getTrack().getArtists()[0].getId(), t.getTrack().getAlbum().getName(), t.getTrack().getAlbum().getId(),t.getTrack().getUri()));
                    }
                }
            } catch (IOException | SpotifyWebApiException e) {
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
        }
        createPlaylistRequest = spotifyApi.createPlaylist(id,p.getName())
          .collaborative(false)
          .public_(true)
          .description("🎶🎵🎶Creada con PlaylistSets🎶🎵🎶")
                .build();
        try {


            com.wrapper.spotify.model_objects.specification.Playlist playlist = createPlaylistRequest.execute();
            addTracks(playlist,p);

        } catch (IOException | SpotifyWebApiException e) {
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
        try {
        for(int i=0;i<urisArray.length;i++) {

            System.out.println("i=" + i + "," + urisArray[i].length + "  canciones");
            for (String s : urisArray[i])
                System.out.println("\t    " + s);
            addTracksToPlaylistRequest = spotifyApi.addTracksToPlaylist(spotifyPlaylist.getId(), urisArray[i]).build();
            SnapshotResult snapshotResult = addTracksToPlaylistRequest.execute();
           // System.out.println(snapshotResult.getSnapshotId());
        }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SpotifyWebApiException e) {
                e.printStackTrace();
            }


    }

}

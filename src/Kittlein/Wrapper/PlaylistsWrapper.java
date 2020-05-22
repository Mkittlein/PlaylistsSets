package Kittlein.Wrapper;

import Kittlein.Sets.Playlist;
import Kittlein.Sets.PlaylistSimple;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Image;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistCoverImageRequest;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistsWrapper {
    private SpotifyApi spotifyApi;

    private GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest;

    public List<Playlist> getListOfCurrentUsersPlaylists() {
        List<Playlist> PL = new ArrayList<Playlist>();
        try {
            Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();
            int total= playlistSimplifiedPaging.getTotal();

            int offset=0;
            while (total>=0) {
                for (PlaylistSimplified p : playlistSimplifiedPaging.getItems()) {
                    PL.add(new PlaylistSimple(p.getId(), p.getName(), p.getTracks().getTotal(),p.getUri(),this));
                }
                offset+=50;
                total-=50;
                getListOfCurrentUsersPlaylistsRequest = spotifyApi
                        .getListOfCurrentUsersPlaylists()
                        .limit(50)
                        .offset(offset)
                        .build();
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
            return images[0];
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public PlaylistsWrapper(SpotifyApi spotifyApi){

        this.spotifyApi=spotifyApi;
        getListOfCurrentUsersPlaylistsRequest= spotifyApi
                .getListOfCurrentUsersPlaylists()
                  .limit(50)
                  .offset(0)
                .build();
    }
}

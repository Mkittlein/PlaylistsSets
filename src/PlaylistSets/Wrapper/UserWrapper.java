package PlaylistSets.Wrapper;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class UserWrapper {
    private SpotifyApi spotifyApi;
    private User user;

    public UserWrapper(SpotifyApi spotifyApi){
        this.spotifyApi=spotifyApi;
        GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
                .build();
        try {
            user = getCurrentUsersProfileRequest.execute();
            System.out.println("Logueado como: "+user.getDisplayName());
        } catch (IOException | SpotifyWebApiException |ParseException  e) {
            e.printStackTrace();
        } 
    }

    public String getName(){
        return  user.getDisplayName();
    }

    public void getPlaylists(){

    }
}

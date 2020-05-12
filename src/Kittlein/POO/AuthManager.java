package Kittlein.POO;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

public class AuthManager {

    SpotifyApi spotifyApi;
    private ClientCredentialsRequest clientCredentialsRequest;
    private String code="";
    private AuthorizationCodeRequest authorizationCodeRequest;
    private AuthorizationCodeUriRequest authorizationCodeUriRequest;
    private AuthorizationCodeCredentials authorizationCodeCredentials;

    public AuthManager(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
        clientCredentialsRequest = spotifyApi.clientCredentials().build();

        authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().scope("playlist-read-private playlist-read-collaborative playlist-modify-private playlist-modify-public").build();
        try {
            authorizationCodeCredentials= authorizationCodeRequest.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException |ParseException e) {
            e.printStackTrace();
        }
        authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        try {
            authorizationCodeRequest.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException|ParseException e) {
            e.printStackTrace();
        }
    }

    public  String authorizationCodeUri_Sync() {
        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    private String readAuthCode(){
        String code="";
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is started");
            //serverSocket.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    public void authorizationCodeUri_Authorize() {
        final URI uri = authorizationCodeUriRequest.execute();
        try {
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
       code= readAuthCode();
       /* if (code.length()>1){
            spotifyApi.setAccessToken();
        }*/


    }

    public void clientCredentials_Sync() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException |ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public  void authorizationCode_Sync() {


            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());

    }
}
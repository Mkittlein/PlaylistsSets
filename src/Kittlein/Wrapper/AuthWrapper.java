package Kittlein.Wrapper;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;


public class AuthWrapper {

    SpotifyApi spotifyApi;
    private ClientCredentialsRequest clientCredentialsRequest;
    private String code="";
    private AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest;
    private AuthorizationCodeRequest authorizationCodeRequest;
    private AuthorizationCodeUriRequest authorizationCodeUriRequest;
    private AuthorizationCodeCredentials authorizationCodeCredentials;

    public AuthWrapper(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
        clientCredentialsRequest = spotifyApi.clientCredentials().build();
        authorizationCodeUriRequest = spotifyApi.authorizationCodeUri().scope("playlist-read-private playlist-read-collaborative playlist-modify-private playlist-modify-public").build();
        clientCredentials_Sync();//
    }

    public void LogIn(){
        try {
            authorizationCodeUri_Authorize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        authorizationCode();
    }

    private String readAuthCode() throws IOException {
        String code="";
        boolean wait=true;
        ServerSocket server = new ServerSocket(8888);
        server.setSoTimeout(30000);
        System.out.println("Listening for connection on port 8888 ....");
            try (Socket socket = server.accept()) {
                wait=false;
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeBytes("HTTP/1.1 200 OK\r\n");
                out.writeBytes("Content-Type: text/html\r\n\r\n");
                out.writeBytes("<html><head></head><body><h1>Ya puede Cerrar la pagina</h1>\n" +
                        "<script>window.close();</script>" +//ESTO NO FUNCIONA EN FIREFOX POR MEDIDAS DE SEGURIDAD
                        "</body></html>");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String URL=in.readLine();
                in.close();
                code= (URL.substring(11,URL.length()-9));//Elimina formateo de la url y el sufijo de httpRequest
            } catch (SocketTimeoutException e){
                e.printStackTrace();
            }

        server.close();
        System.out.println("Succes! codigo="+code);
        return code;
    }

    public void authorizationCodeUri_Authorize() throws IOException {
        final URI uri = authorizationCodeUriRequest.execute();
        try {
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        code= readAuthCode();
        System.out.println("Solicitamos el Codigo de autorizacion para la cuenta a spotify");
        //authorizationCodeCredentials = spotifyApi.
        authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        //authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh().build();
    }

    private void clientCredentials_Sync() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void authorizationCodeRefresh() {
        try {
            authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();
            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error en authorizationCodeRefresh_Sync: " + e.getMessage());
        }
    }

    public void authorizationCode() {
        try {
            authorizationCodeCredentials = authorizationCodeRequest.execute();
            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            System.out.println("Codigo Pedido con éxito, expira en: " + authorizationCodeCredentials.getExpiresIn()+"s");
            System.out.println("Refresh Token: "+spotifyApi.getRefreshToken());
            authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh().build();
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error en authorizationCode_Sync: " + e.getMessage());
        }
    }
}
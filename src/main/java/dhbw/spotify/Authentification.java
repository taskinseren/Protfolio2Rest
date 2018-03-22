package dhbw.spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.netflix.config.DynamicPropertyFactory;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Base64;

/**
 * Diese Klasse dient ausschließlich der Authentifizierung gegenüber
 * der Spotify API.
 * Änderungen an dieser Klasse müssen/ sollten nicht vorgenommen werden.
 */

public class Authentification {

    private String publicKey;
    private String privateKey;
    private String authKey;
    private String base64Key;
    private LocalTime localTime;
    private static Authentification authentification = new Authentification();

    private Authentification() {
        this.privateKey = DynamicPropertyFactory.getInstance().getStringProperty("secretId","").get();;
        this.publicKey = DynamicPropertyFactory.getInstance().getStringProperty("clientId","").get();
        authKey = authenticatedAtSpotify();
        localTime = LocalTime.now();
    }

    public static Authentification getInstance(){
        return authentification;
    }

    private String authenticatedAtSpotify() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        String toEncode = this.getPublicKey() + ":" + this.getPrivateKey();
        byte[] encodedKeyInBytes =  Base64.getEncoder().encode((toEncode).getBytes());
        String encodedKey = new String(encodedKeyInBytes);
        String body = "grant_type=client_credentials";
        try {
            HttpResponse<AuthReponse> authReponseHttpResponse = Unirest.post("https://accounts.spotify.com/api/token")
                    .header("Authorization", "Basic " + encodedKey)
                    .header("Content-Type","application/x-www-form-urlencoded")
                    .body(body)
                    .asObject(AuthReponse.class);
            return authReponseHttpResponse.getBody().getAccess_token();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return "";
    }

    //Getter and Setter
    public String getPublicKey() {
        return publicKey;
    }

    private void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    private void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    private void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getBase64Key() {
        return base64Key;
    }

    private void setBase64Key(String base64Key) {
        this.base64Key = base64Key;
    }

    public String getAuthKey(){
        if (LocalTime.now().isAfter(this.localTime.plusMinutes(55))){
            authKey = this.authenticatedAtSpotify();
            this.localTime = LocalTime.now();
        }
        return this.authKey;
    }
}

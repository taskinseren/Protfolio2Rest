package dhbw.spotify;

/**
 * Created by Florian on 03.03.2018.
 *
 * POJO Klasse zur Speicherung der Authentifizerungsantworten der Spotify API.
 * Diese Klasse wird nur in der Authentifizerung verwendet und muss/ sollte ebenfalls nicht
 * angepasst werden.
 */
public class AuthReponse {

    private String access_token;
    private String token_type;
    private String expires_in;
    private String scope;

    public AuthReponse(String access_token, String token_type, String expires_in, String scope) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.scope = scope;
    }

    public AuthReponse(){

    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

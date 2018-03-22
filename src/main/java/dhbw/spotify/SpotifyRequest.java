package dhbw.spotify;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;
import java.util.Optional;


/**
 * Created by Florian on 04.03.2018.
 *
 * Diese Klasse dient zur Kommunikation mit Spotify.
 * Sie bietet verschiedene Methoden an, mit der verschiedene Parameter
 * an die Anfrage übergeben werden können.
 */

public class SpotifyRequest {
    private String baseUrl;
    private RequestType requestType;
    private Authentification authentification = Authentification.getInstance();

    /**
     * Konstruktor zur Erstellung einer SpotifyRequest Klasse.
     * @param requestType Gibt an ob es sich um eine Such- oder eine Detailanfrage an die Spotify API handelt
     */
    public SpotifyRequest(RequestType requestType) {
        if (requestType.equals(RequestType.SEARCH)){
            this.requestType = requestType;
            baseUrl = "https://api.spotify.com/v1/search?";
        }
        else if (requestType.equals(RequestType.DETAIL)){
            this.requestType = requestType;
            baseUrl = "https://api.spotify.com";
        }
    }

    public Optional<String> performeRequestSearch(RequestCategory requestCategory, String queue) throws WrongRequestTypeException {
        return performeRequestSearch(requestCategory,queue,20,null);
    }

    public Optional<String> performeRequestSearch(RequestCategory requestCategory, String queue, int limit) throws WrongRequestTypeException {
        return performeRequestSearch(requestCategory,queue,limit,null);
    }

    public Optional<String> performeRequestSearch(RequestCategory requestCategory, String queue, String market) throws WrongRequestTypeException {
        return performeRequestSearch(requestCategory,queue,20,market);
    }

    public Optional<String> performeRequestSearch(RequestCategory requestCategory, String queue, int limit, String market) throws WrongRequestTypeException {
        try {
            URIBuilder uriBuilder =  new URIBuilder(baseUrl);
            if (requestType == RequestType.SEARCH) {
                return this.buildUrlSearch(uriBuilder, requestCategory, queue, limit, market);
            } else{
                throw new WrongRequestTypeException("Search Anfrage kann nicht mit einem SpotifyRequest Objekt mit einem anderen Typ als Search durchgeführt werden.");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<String> performeRequestDetail(RequestCategory requestCategory, String id) throws WrongRequestTypeException {
        try {
            URIBuilder uriBuilder =  new URIBuilder(baseUrl);
            if (requestType == RequestType.DETAIL) {
                switch (requestCategory) {
                    case ALBUM:
                        uriBuilder.setPath("/v1/albums/" + id);
                        break;
                    case TRACK:
                        uriBuilder.setPath("/v1/tracks/" + id);
                        break;
                    case ARTIST:
                        uriBuilder.setPath("/v1/artists/" + id);
                        break;
                    default:
                        return Optional.empty();
                }

                return Optional.of(Unirest.get(uriBuilder.build().toString()).header("Authorization","Bearer " + authentification.getAuthKey()).asString().getBody());
            } else{
                throw new WrongRequestTypeException("Search Anfrage kann nicht mit einem SpotifyRequest Objekt mit einem anderen Typ als Search durchgeführt werden.");
            }
        } catch (URISyntaxException | UnirestException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    /**
     *
     * @param uriBuilder
     * @param requestCategory
     * @param queue
     * @param limit
     * @param market
     * @return Optional<String> Gibt das Resultat der Anfrage als JSON String zurück.
     */
    private Optional<String> buildUrlSearch(URIBuilder uriBuilder,RequestCategory requestCategory, String queue, int limit, String market) {
        switch (requestCategory) {
            case ALBUM:
                uriBuilder.addParameter("type","album");
                break;
            case TRACK:
                uriBuilder.addParameter("type","track");
                break;
            case ARTIST:
                uriBuilder.addParameter("type","artist");
                break;
            default:
                return Optional.empty();
        }
        uriBuilder.addParameter("q",queue);
        uriBuilder.addParameter("limit",String.valueOf(limit));
        if (market != null){
            uriBuilder.addParameter("market",market);
        }
        try {
            return Optional.of(Unirest.get(uriBuilder.build().toString()).header("Authorization","Bearer " + authentification.getAuthKey()).asString().getBody());
        } catch (UnirestException | URISyntaxException p) {
            p.printStackTrace();
            return Optional.empty();
        }
    }

}

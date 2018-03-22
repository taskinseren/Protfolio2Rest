package dhbw.spotify;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyRequestTest {

    @Test
    void performeRequestAll() {
        SpotifyRequest spotifyRequest = new SpotifyRequest(RequestType.SEARCH);
        Optional<String> stringOptional = Optional.empty();
        try {
            stringOptional = spotifyRequest.performeRequestSearch(RequestCategory.TRACK, "Spring", 5 , "DE");
        } catch (WrongRequestTypeException e) {
            e.printStackTrace();
        }
        assertTrue(stringOptional.isPresent());
    }

    @Test
    void performeRequestAllError() {
        SpotifyRequest spotifyRequest = new SpotifyRequest(RequestType.DETAIL);
        Optional<String> stringOptional = Optional.empty();
        try {
            stringOptional = spotifyRequest.performeRequestSearch(RequestCategory.TRACK, "Spring", 5 , "DE");
        } catch (WrongRequestTypeException e) {
            e.printStackTrace();
        }
        assertFalse(stringOptional.isPresent());
    }

    @Test
    void performeRequestDetail() {
        SpotifyRequest spotifyRequest = new SpotifyRequest(RequestType.DETAIL);
        Optional<String> stringOptional = Optional.empty();
        try {
            stringOptional = spotifyRequest.performeRequestDetail(RequestCategory.TRACK, "3JIxjvbbDrA9ztYlNcp3yL");
        } catch (WrongRequestTypeException e) {
            e.printStackTrace();
        }
        assertTrue(stringOptional.isPresent());
        System.out.println(stringOptional.get());
    }

}
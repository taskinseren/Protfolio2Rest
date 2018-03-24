package dhbw;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import dhbw.pojo.result.search.SearchResult;
import dhbw.pojo.result.search.SearchResultList;
import dhbw.pojo.search.album.SearchAlbum;
import dhbw.pojo.search.artist.Item;
import dhbw.pojo.search.artist.SearchArtist;
import dhbw.pojo.search.track.SearchTrack;
import dhbw.spotify.RequestCategory;
import dhbw.spotify.RequestType;
import dhbw.spotify.SpotifyRequest;
import dhbw.spotify.WrongRequestTypeException;
import java.io.IOException;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Seren
 */

@RestController
public class SearchWebservice {
    /**
     * @param type
     * @param query
     * @return 
     */
    
    @GetMapping("/search")
        public SearchResult search(@RequestParam RequestCategory type, @RequestParam String query) {
            SearchResult result = new SearchResult();
		List<SearchResultList> list = new ArrayList<>();
		result.setResults(list);
		SpotifyRequest sr = new SpotifyRequest(RequestType.SEARCH);
                try {
			Optional<String> o = sr.performeRequestSearch(type, query);
			if (o.isPresent()) {
				String json = o.get();
				ObjectMapper om = new ObjectMapper();
				switch (type) {
				case ALBUM:
					SearchAlbum sal = om.readValue(json, SearchAlbum.class);
					sal.getAlbums().getItems().forEach(i -> {
						String artists = "";
                                                artists = i.getArtists().stream().map((a) -> ", " + a.getName()).reduce(artists, String::concat);
						if (artists.length() > 2)
							artists = artists.substring(2);
						list.add(new SearchResultList(i.getId(), i.getName(), artists, i.getUri()));
					});
					break;
				case ARTIST:
					SearchArtist sar = om.readValue(json, SearchArtist.class);
					sar.getArtists().getItems().forEach((Item i) -> {
                                            String genres = "";
                                            genres = i.getGenres().stream().map((g) -> ", " + g.toString()).reduce(genres, String::concat);
                                            if (genres.length() > 2)
                                                genres = genres.substring(2);
                                            list.add(new SearchResultList(i.getId(), i.getName(), genres, i.getUri()));
                                });
					break;
				case TRACK:
					SearchTrack str = om.readValue(json, SearchTrack.class);
					str.getTracks().getItems().forEach(i -> {
						list.add(new SearchResultList(i.getId(), i.getName(), i.getAlbum().getName(), i.getUri()));
					});
					break;
				}
				result.setResults(list);
			}
		} catch (WrongRequestTypeException | IOException e) {
			e.printStackTrace(System.out);
		}
		return result;
	}
}

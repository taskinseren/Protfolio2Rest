/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw;

import com.fasterxml.jackson.databind.ObjectMapper;
import dhbw.pojo.detail.album.DetailsAlbum;
import dhbw.pojo.detail.artist.DetailsArtist;
import dhbw.pojo.detail.track.DetailsTrack;
import dhbw.pojo.result.detail.DetailResult;
import dhbw.spotify.RequestCategory;
import dhbw.spotify.RequestType;
import dhbw.spotify.SpotifyRequest;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Seren
 */


public class DetailsWebservice {
    @GetMapping("/detail/{id}")
	public DetailResult detail(@RequestParam RequestCategory type, @PathVariable String id) {
		SpotifyRequest sr = new SpotifyRequest(RequestType.DETAIL);
		try {
			Optional<String> o = sr.performeRequestDetail(type, id);
			if (o.isPresent()) {
				String json = o.get();
				ObjectMapper om = new ObjectMapper();
				switch (type) {
				case ALBUM:
					DetailsAlbum dal = om.readValue(json, DetailsAlbum.class);
					return new DetailResult(dal.getName(), "Release Date: " + dal.getReleaseDate());
				case ARTIST:
					DetailsArtist dar = om.readValue(json, DetailsArtist.class);
					String genres = "";
                                        genres = dar.getGenres().stream().map((s) -> ", " + s).reduce(genres, String::concat);
					if (genres.length() > 2)
						genres.substring(2);
					return new DetailResult(dar.getName(),
							"Genres: " + genres + "\nPopularity: " + dar.getPopularity() + "%");
				case TRACK:
					DetailsTrack dtr = om.readValue(json, DetailsTrack.class);
					String artists = "";
					for (dhbw.pojo.detail.track.Artist a : dtr.getArtists())
						artists += ", " + a.getName();
					if (artists.length() > 2)
						artists = artists.substring(2);
					return new DetailResult(dtr.getName(),
							"Artist: " + artists + "\nAlbum: " + dtr.getAlbum().getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DetailResult();
	}
}

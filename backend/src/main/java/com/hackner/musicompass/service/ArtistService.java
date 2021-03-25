package com.hackner.musicompass.service;
import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistService {
    private final DiscogsArtistApiService discogsArtistApiService;
    private final ArtistMongoDb artistMongoDb;

    @Autowired
    public ArtistService(DiscogsArtistApiService discogsArtistApiService, ArtistMongoDb artistMongoDb) {
        this.discogsArtistApiService = discogsArtistApiService;
        this.artistMongoDb = artistMongoDb;
    }

    public List<Artist> getArtistListBySearchTerm(String searchTerm) {
        DiscogsArtistSearchResults discogsArtistSearchResults = discogsArtistApiService.getDiscogsArtistByName(searchTerm);

        List<DiscogsArtist> discogsArtistList = discogsArtistSearchResults.getResults();

        if (discogsArtistList.size() > 5) {
            discogsArtistList = discogsArtistList.subList(0, 5);
        }

        List<Artist> artistList = discogsArtistList.stream()
                .map(discogsArtist -> {
                    Artist artist = new Artist().builder()
                            .artistName(discogsArtist.getArtistName())
                            .artistImageUrl(discogsArtist.getArtistImageUrl())
                            .discogsArtistId(discogsArtist.getDiscogsArtistId())
                            .discogsArtistUrl(discogsArtist.getDiscogsArtistUrl()).build();
                    return artist;
                })
                .collect(Collectors.toList());

        return artistList;

    }

    public Artist getArtistByName(String artistName) {

        DiscogsArtistSearchResults DiscogsArtistSearchResults = discogsArtistApiService.getDiscogsArtistByName(artistName);

        DiscogsArtist discogsArtist = DiscogsArtistSearchResults.getResults().get(0);

        Artist artist = new Artist().builder()
                .artistName(discogsArtist.getArtistName())
                .artistImageUrl(discogsArtist.getArtistImageUrl())
                .discogsArtistId(discogsArtist.getDiscogsArtistId())
                .discogsArtistUrl(discogsArtist.getDiscogsArtistUrl()).build();

        artistMongoDb.save(artist);

        return artist;
    }
}

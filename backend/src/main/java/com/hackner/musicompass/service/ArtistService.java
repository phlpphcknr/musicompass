package com.hackner.musicompass.service;
import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ArtistService {
    private final DiscogsArtistApiService discogsArtistApiService;
    private final ArtistMongoDb artistMongoDb;

    @Autowired
    public ArtistService(DiscogsArtistApiService discogsArtistApiService, ArtistMongoDb artistMongoDb) {
        this.discogsArtistApiService = discogsArtistApiService;
        this.artistMongoDb = artistMongoDb;
    }

    public Optional<Artist> getArtistBySearchTerm (String searchTerm) {
        DiscogsArtistSearchResults discogsArtistSearchResults = discogsArtistApiService.getDiscogsArtistByName(searchTerm);
        if (discogsArtistSearchResults.getResults().size() == 0){
            return Optional.empty();
        }

        ArrayList<DiscogsArtist> discogsArtistResult = discogsArtistSearchResults.getResults();

        DiscogsArtist discogsArtist = discogsArtistResult.get(0);

        return Optional.of(new Artist().builder()
                .artistName(discogsArtist.getArtistName())
                .artistImageUrl(discogsArtist.getArtistImageUrl())
                .discogsArtistId(discogsArtist.getDiscogsArtistId())
                .discogsArtistUrl(discogsArtist.getDiscogsArtistUrl()).build());
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

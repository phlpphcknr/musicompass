package com.hackner.musicompass.service;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Builder
public class ArtistService {
    private final DiscogsArtistApiService discogsArtistApiService;

    @Autowired
    public ArtistService(DiscogsArtistApiService discogsArtistApiService) {
        this.discogsArtistApiService = discogsArtistApiService;
    }

    public Optional<Artist> getArtistByArtistName (String artistName) {
        DiscogsArtistSearchResults DiscogsArtistSearchResults = discogsArtistApiService.getDiscogsArtistByArtistName(artistName);
        if (DiscogsArtistSearchResults.getResults().length == 0){
            return Optional.empty();
        }
        DiscogsArtist discogsArtist = DiscogsArtistSearchResults.getResults()[0];

        return Optional.of(new Artist().builder()
                .artistName(discogsArtist.getArtistName())
                .artistImageUrl(discogsArtist.getArtistImageUrl())
                .discogsId(discogsArtist.getDiscogsArtistId())
                .discogsArtistUrl(discogsArtist.getDiscogsArtistUrl()).build());
    }
}
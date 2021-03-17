package com.hackner.musicompass.service;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import com.hackner.musicompass.secret.DiscogsSecret;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
@Builder
public class ArtistService {
    private final DiscogsArtistApiService discogsArtistApiService;
    private final DiscogsSecret discogsSecret;

    @Autowired
    public ArtistService(DiscogsArtistApiService discogsArtistApiService, DiscogsSecret discogsSecret) {
        this.discogsArtistApiService = discogsArtistApiService;
        this.discogsSecret = discogsSecret;
    }

    public Optional<Artist> getArtistByArtistName (String artistName) {
        DiscogsArtistSearchResults DiscogsArtistSearchResults = discogsArtistApiService.getDiscogsArtistByArtistName(artistName, discogsSecret.getDiscogsToken());
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
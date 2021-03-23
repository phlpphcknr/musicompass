package com.hackner.musicompass.service;
import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Builder
public class ArtistService {
    private final DiscogsArtistApiService discogsArtistApiService;
    private final ArtistMongoDb artistMongoDb;

    @Autowired
    public ArtistService(DiscogsArtistApiService discogsArtistApiService, ArtistMongoDb artistMongoDb) {
        this.discogsArtistApiService = discogsArtistApiService;
        this.artistMongoDb = artistMongoDb;
    }

    public Optional<Artist> getArtistBySearchTerm (String searchTerm) {
        DiscogsArtistSearchResults DiscogsArtistSearchResults = discogsArtistApiService.getDiscogsArtistByArtistName(searchTerm);
        if (DiscogsArtistSearchResults.getResults().length == 0){
            return Optional.empty();
        }

        DiscogsArtist[] discogsArtistResultArray = DiscogsArtistSearchResults.getResults();

        //I want to cut the list down to the first 5 entries
        //Iwas not able to access the variable discogsArtistResultArrayCut outside the if-else-function
        /*if (discogsArtistResultArray.length > 5){
            DiscogsArtist[] discogsArtistResultArrayCut = Arrays.copyOfRange(discogsArtistResultArray, 0,4);
        }else{
            DiscogsArtist[] discogsArtistResultArrayCut = discogsArtistResultArray;
        }*/

        DiscogsArtist discogsArtist = discogsArtistResultArray[0];

        return Optional.of(new Artist().builder()
                .artistName(discogsArtist.getArtistName())
                .artistImageUrl(discogsArtist.getArtistImageUrl())
                .discogsId(discogsArtist.getDiscogsArtistId())
                .discogsArtistUrl(discogsArtist.getDiscogsArtistUrl()).build());
    }

    public Artist getArtistByArtistName(String artistName) {
        DiscogsArtistSearchResults DiscogsArtistSearchResults = discogsArtistApiService.getDiscogsArtistByArtistName(artistName);

        DiscogsArtist discogsArtist = DiscogsArtistSearchResults.getResults()[0];

        Artist artist = new Artist().builder()
                .artistName(discogsArtist.getArtistName())
                .artistImageUrl(discogsArtist.getArtistImageUrl())
                .discogsId(discogsArtist.getDiscogsArtistId())
                .discogsArtistUrl(discogsArtist.getDiscogsArtistUrl()).build();

        artistMongoDb.save(artist);

        return artist;
    }
}
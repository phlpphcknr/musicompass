package com.hackner.musicompass.service;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.ArtistInfo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArtistService {

    private final DiscogsArtistApiService discogsArtistApiService;
    private final ArtistMongoDb artistMongoDb;

    public ArtistService(DiscogsArtistApiService discogsArtistApiService, ArtistMongoDb artistMongoDb) {
        this.discogsArtistApiService = discogsArtistApiService;
        this.artistMongoDb = artistMongoDb;
    }

    public Artist getArtistByName(String artistName) {

        if(artistMongoDb.existsById(artistName)){
            return artistMongoDb.findById(artistName).get();
        }

        DiscogsArtistSearchResults DiscogsArtistSearchResults = discogsArtistApiService.getDiscogsArtistListBySearchTerm(artistName);

        DiscogsArtist discogsArtist = DiscogsArtistSearchResults.getResults().get(0);

        ArtistInfo artistInfo = new ArtistInfo().builder()
                .artistName(discogsArtist.getArtistName())
                .artistImageUrl(discogsArtist.getArtistImageUrl())
                .discogsArtistId(discogsArtist.getDiscogsArtistId())
                .discogsArtistUrl(discogsArtist.getDiscogsArtistUrl()).build();

        Artist artist = new Artist().builder()
                .artistName(discogsArtist.getArtistName())
                .artistInfo(artistInfo).build();

        artistMongoDb.save(artist);

        return artist;
    }

}

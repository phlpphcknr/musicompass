package com.hackner.musicompass.service;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.model.DiscogsMasterRelease;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.ArtistInfo;
import com.hackner.musicompass.model.ArtistRelease;
import com.hackner.musicompass.model.RecommendationTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Service
public class ArtistService {

    private final DiscogsArtistApiService discogsArtistApiService;
    private final ArtistReleaseService artistReleaseService;
    private final ArtistMongoDb artistMongoDb;
    private final TimeUtils timeUtils;

    @Autowired
    public ArtistService(DiscogsArtistApiService discogsArtistApiService, ArtistReleaseService artistReleaseService, ArtistMongoDb artistMongoDb, TimeUtils timeUtils) {
        this.discogsArtistApiService = discogsArtistApiService;
        this.artistReleaseService = artistReleaseService;
        this.artistMongoDb = artistMongoDb;
        this.timeUtils = timeUtils;
    }

    public Artist getArtistByName(String artistName) {

        if(artistMongoDb.existsById(artistName)){
            return artistMongoDb.findById(artistName).get();
        }

        DiscogsArtistSearchResults discogsArtistSearchResults = discogsArtistApiService.getDiscogsArtistListBySearchTerm(artistName);

        DiscogsArtist discogsArtist = discogsArtistSearchResults.getResults().get(0);

        ArtistInfo artistInfo = new ArtistInfo().builder()
                        .artistImageUrl(discogsArtist.getArtistImageUrl())
                        .discogsArtistId(discogsArtist.getDiscogsArtistId())
                        .discogsArtistUrl(discogsArtist.getDiscogsArtistUrl()).build();

        List<DiscogsMasterRelease> discogsMasterReleaseList = discogsArtistApiService.getDiscogsMasterReleaseListByArtistName(artistName);

        List<ArtistRelease> albumList = artistReleaseService.getSortedReleaseList("Album", discogsMasterReleaseList);
        List<ArtistRelease> singleList = artistReleaseService.getSortedReleaseList("Single/EP", discogsMasterReleaseList);

        RecommendationTags recommendationTags = RecommendationTags.builder()
                .recommended(false)
                .gender(List.of())
                .roles(List.of())
                .genres(List.of()).build();

        Artist artist = new Artist().builder()
                .artistName(discogsArtist.getArtistName())
                .saveDate(LocalDateTime.ofInstant(timeUtils.now(), ZoneOffset.UTC))
                .artistInfo(artistInfo)
                .artistAlbums(albumList)
                .artistSingles(singleList)
                .recommendationTags(recommendationTags).build();

        artistMongoDb.save(artist);

        return artist;
    }
}
package com.hackner.musicompass.service;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.model.DiscogsMasterRelease;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.ArtistAlbum;
import com.hackner.musicompass.model.ArtistInfo;
import com.hackner.musicompass.model.ArtistSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.Math;

@Service
public class ArtistService {

    private final DiscogsArtistApiService discogsArtistApiService;
    private final ArtistMongoDb artistMongoDb;
    private final TimeUtils timeUtils;

    @Autowired
    public ArtistService(DiscogsArtistApiService discogsArtistApiService, ArtistMongoDb artistMongoDb, TimeUtils timeUtils) {
        this.discogsArtistApiService = discogsArtistApiService;
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

        List<ArtistAlbum> albumList = discogsMasterReleaseList.stream()
                .filter(discogsMasterRelease -> discogsMasterRelease.getFormat().contains("Album"))
                .map(discogsMasterRelease -> new ArtistAlbum().builder()
                        .fullTitle(discogsMasterRelease.getFullAlbumTitle())
                        .discogsMasterReleaseId(discogsMasterRelease.getMasterId())
                        .format(discogsMasterRelease.getFormat())
                        .releaseYear(discogsMasterRelease.getYear())
                        .discogsWant(discogsMasterRelease.getReleaseStats().getNumberOfWants())
                        .discogsHave(discogsMasterRelease.getReleaseStats().getNumberOfHaves())
                        .globalRating(calculateGlobalRating(discogsMasterRelease.getReleaseStats().getNumberOfHaves(), discogsMasterRelease.getReleaseStats().getNumberOfWants())).build())
                .collect(Collectors.toList());

        List<ArtistSingle> singleList = discogsMasterReleaseList.stream()
                .filter(discogsMasterRelease -> discogsMasterRelease.getFormat().contains("Single"))
                .map(discogsMasterRelease -> new ArtistSingle().builder()
                        .fullTitle(discogsMasterRelease.getFullAlbumTitle())
                        .discogsMasterReleaseId(discogsMasterRelease.getMasterId())
                        .format(discogsMasterRelease.getFormat())
                        .releaseYear(discogsMasterRelease.getYear())
                        .discogsWant(discogsMasterRelease.getReleaseStats().getNumberOfWants())
                        .discogsHave(discogsMasterRelease.getReleaseStats().getNumberOfHaves())
                        .globalRating(calculateGlobalRating(discogsMasterRelease.getReleaseStats().getNumberOfHaves(), discogsMasterRelease.getReleaseStats().getNumberOfWants())).build())
                .collect(Collectors.toList());

        Artist artist = new Artist().builder()
                .artistName(discogsArtist.getArtistName())
                .saveDate(Date.from(timeUtils.now()))
                .artistInfo(artistInfo)
                .artistAlbums(albumList)
                .artistSingles(singleList).build();

        artistMongoDb.save(artist);

        return artist;
    }

    public double calculateGlobalRating ( int have, int want){
        return Math.sqrt((have*want)/(have+want));
    }
}

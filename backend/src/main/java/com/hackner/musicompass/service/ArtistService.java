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

import java.util.List;
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

    public Artist getArtistByName(String artistName) {

        if(artistMongoDb.existsById(artistName)){
            return artistMongoDb.findById(artistName).get();
        }

        DiscogsArtistSearchResults discogsArtistSearchResults = discogsArtistApiService.getDiscogsArtistListBySearchTerm(artistName);

        DiscogsArtist discogsArtist = discogsArtistSearchResults.getResults().get(0);

        ArtistInfo artistInfo = new ArtistInfo().builder()
                        .artistImageUrl(discogsArtist.getArtistImageUrl())
                        .discogsArtistId(discogsArtist.getDiscogsArtistId())
                        .discogsArtistUrl(discogsArtist.getDiscogsArtistUrl()).build());

        List<DiscogsMasterRelease> discogsMasterReleaseList = discogsArtistApiService.getDiscogsMasterReleaseListByArtistName(artistName);

        List<ArtistAlbum> albumList = discogsMasterReleaseList.stream()
                .filter(discogsMasterRelease -> discogsMasterRelease.getFormat().contains("Album"))
                .map(discogsMasterRelease -> new ArtistAlbum().builder()
                        .fullTitle(discogsMasterRelease.getFullAlbumTitle())
                        .discogsMasterReleaseId(discogsMasterRelease.getMasterId())
                        .format(discogsMasterRelease.getFormat())
                        .releaseYear(discogsMasterRelease.getYear())
                        .discogsWant(discogsMasterRelease.getReleaseStats().getNumberOfWants())
                        .discogsHave(discogsMasterRelease.getReleaseStats().getNumberOfHaves()).build())
                .collect(Collectors.toList());

        List<ArtistSingle> singleList = discogsMasterReleaseList.stream()
                .filter(discogsMasterRelease -> discogsMasterRelease.getFormat().contains("Single"))
                .map(discogsMasterRelease -> new ArtistSingle().builder()
                        .fullTitle(discogsMasterRelease.getFullAlbumTitle())
                        .discogsMasterReleaseId(discogsMasterRelease.getMasterId())
                        .format(discogsMasterRelease.getFormat())
                        .releaseYear(discogsMasterRelease.getYear())
                        .discogsWant(discogsMasterRelease.getReleaseStats().getNumberOfWants())
                        .discogsHave(discogsMasterRelease.getReleaseStats().getNumberOfHaves()).build())
                .collect(Collectors.toList());

        Artist artist = new Artist().builder()
                .artistName(discogsArtist.getArtistName())
                .artistInfo(artistInfo)
                .artistAlbums(albumList)
                .artistSingles(singleList).build();

        artistMongoDb.save(artist);

        return artist;
    }
}

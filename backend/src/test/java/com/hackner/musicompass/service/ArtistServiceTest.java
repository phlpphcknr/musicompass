package com.hackner.musicompass.service;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.ArtistInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

class ArtistServiceTest {

    private final DiscogsArtistApiService discogsArtistApiService = mock(DiscogsArtistApiService.class);
    private final ArtistMongoDb artistMongoDb = mock(ArtistMongoDb.class);
    private final TimeUtils timeUtils = mock(TimeUtils.class);
    private final ArtistReleaseService artistReleaseService = mock(ArtistReleaseService.class);
    private final ArtistService artistService = new ArtistService(discogsArtistApiService, artistReleaseService, artistMongoDb, timeUtils);

    @Test
    @DisplayName("Get Artist who is already saved in DB")
    public void getArtistByNameFromDb (){
        //GIVEN
        String artistName = "Hans Hammer";

        Artist artist = Artist.builder()
                .artistName(artistName)
                .artistInfo(new ArtistInfo().builder()
                        .artistImageUrl("HammerImageUrl")
                        .discogsArtistId("111")
                        .discogsArtistUrl("HammerUrl")
                        .build())
                .build();

        when(artistMongoDb.existsById(artistName)).thenReturn(true);
        when(artistMongoDb.findById(artistName)).thenReturn(Optional.of(artist));

        //WHEN
        Artist actual = artistService.getArtistByName(artistName);

        //THEN
        assertThat(actual, equalTo(artist));
    }

    @Test
    @DisplayName("Get Artist who is not saved in DB from Api and save in DB")
    public void getArtistByNameFromApi (){
        //GIVEN
        String artistName = "Hans Hammer";
        Instant now = Instant.ofEpochSecond(Instant.now().getEpochSecond());

        Artist artist = Artist.builder()
                .artistName(artistName)
                .saveDate(Date.from(now))
                .artistInfo(new ArtistInfo().builder()
                        .artistImageUrl("HammerImageUrl")
                        .discogsArtistId("111")
                        .discogsArtistUrl("HammerUrl")
                        .build())
                .artistReleases(Collections.emptyList())
                .artistSingles(Collections.emptyList())
                .build();

        DiscogsArtist discogsArtist = DiscogsArtist.builder()
                .discogsArtistId("111")
                .artistName("Hans Hammer")
                .artistImageUrl("HammerImageUrl")
                .discogsArtistUrl("HammerUrl").build();

        DiscogsArtistSearchResults discogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(Arrays.asList(discogsArtist)).build();

        when(artistMongoDb.existsById(artistName)).thenReturn(false);
        when(discogsArtistApiService.getDiscogsArtistListBySearchTerm(artistName))
                .thenReturn(discogsArtistSearchResults);
        when(timeUtils.now()).thenReturn(now);

        //WHEN
        Artist actual = artistService.getArtistByName(artistName);

        //THEN
        assertThat(actual, equalTo(artist));
        verify(artistMongoDb).save(artist);
    }
}


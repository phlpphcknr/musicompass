package com.hackner.musicompass.controller;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.model.*;
import com.hackner.musicompass.discogsapi.service.DiscogsApiEntityService;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.ArtistInfo;
import com.hackner.musicompass.model.ArtistRelease;
import com.hackner.musicompass.secret.DiscogsSecret;
import com.hackner.musicompass.service.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArtistControllerTest {

    @LocalServerPort
    private int port;

    private String getUrl() {return "http://localhost:" + port + "api/artist/";}

    @MockBean
    private RestTemplate restTemplate;

/*    @MockBean
    private DiscogsSecret discogsSecret;*/

    @Autowired
    private TimeUtils timeUtils;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ArtistMongoDb artistMongoDb;

    @Autowired
    private DiscogsApiEntityService discogsApiEntityService;

    @BeforeEach
    public void setup(){
        artistMongoDb.deleteAll();
    }

    @Test
    @DisplayName("Get Artist who already exists in DB")
    public void getArtistWhoIsSavedInDb (){
        //GIVEN
        String artistName = "Prince";
        Artist artist = getArtist(artistName);
        artistMongoDb.save(artist);
        DiscogsSecret discogsSecret = mock(DiscogsSecret.class);
        when(discogsSecret.getToken()).thenReturn("test");

        //WHEN
        ResponseEntity<Artist> response = testRestTemplate.exchange(getUrl() + artistName, HttpMethod.GET, null, Artist.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), equalToObject(artist));
    }

    @Test
    @DisplayName("Get Artist who does not exist in DB so far")
    public void getArtistFromApiAndSaveInDb (){
        //GIVEN
        String artistName = "Prince";
        String artistUrl = "https://api.discogs.com/database/search?type=artist&q=" + artistName;
        String releasesUrl = "https://api.discogs.com//database/search?type=master&artist=" + artistName + "&page=1&per-page_100";
        Artist artist = getArtist(artistName);

        DiscogsArtistSearchResults discogsArtistSearchResults = getDiscogsArtistSearchResults(artistName);
        ResponseEntity<DiscogsArtistSearchResults> artistResponseEntity = ResponseEntity.ok(discogsArtistSearchResults);

        DiscogsMasterReleaseSearchResults discogsMasterReleaseSearchResults = getDiscogsMasterReleaseSearchResults();
        ResponseEntity<DiscogsMasterReleaseSearchResults> releaseResponseEntity = ResponseEntity.ok(discogsMasterReleaseSearchResults);

        DiscogsSecret discogsSecret = mock(DiscogsSecret.class);
        when(discogsSecret.getToken()).thenReturn("test");

        HttpEntity<Void> testEntity = discogsApiEntityService.createEntity();

        when(restTemplate.exchange(artistUrl, HttpMethod.GET, discogsApiEntityService.createEntity(), DiscogsArtistSearchResults.class))
                .thenReturn(artistResponseEntity);
        when(restTemplate.exchange(releasesUrl, HttpMethod.GET, discogsApiEntityService.createEntity(), DiscogsMasterReleaseSearchResults.class))
                .thenReturn(releaseResponseEntity);

        //WHEN
        ResponseEntity<Artist> response = testRestTemplate.exchange(getUrl() + artistName, HttpMethod.GET, null, Artist.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), equalToObject(artist));
        assertTrue(artistMongoDb.existsById(artistName));
    }

    public Artist getArtist(String artistName){
        Date saveDate = Date.from(timeUtils.now());
        ArtistInfo artistInfo = ArtistInfo.builder()
                .discogsArtistId("111")
                .artistImageUrl("imageUrl")
                .discogsArtistUrl("artistUrl").build();
        ArtistRelease artistRelease1 = ArtistRelease.builder()
                .fullTitle("someTitle")
                .discogsMasterReleaseId("111")
                .format("Album")
                .releaseYear(1999)
                .discogsWant(32768)
                .discogsHave(65536)
                .globalRating(2.0)
                .build();
        ArtistRelease artistRelease2 = ArtistRelease.builder()
                .fullTitle("someOtherTitle")
                .discogsMasterReleaseId("222")
                .format("Single/EP")
                .releaseYear(1999)
                .discogsWant(32768)
                .discogsHave(65536)
                .globalRating(2.0)
                .build();
        List<ArtistRelease> artistAlbums = Arrays.asList(artistRelease1);
        List<ArtistRelease> artistSingles = Arrays.asList(artistRelease2);
        Artist artist = Artist.builder()
                .artistName(artistName)
                .saveDate(saveDate)
                .artistInfo(artistInfo)
                .artistAlbums(artistAlbums)
                .artistSingles(artistSingles).build();
        return artist;
    }

    public DiscogsMasterReleaseSearchResults getDiscogsMasterReleaseSearchResults(){

        DiscogsMasterRelease discogsMasterRelease1 = DiscogsMasterRelease.builder()
                .fullAlbumTitle("someTitle")
                .masterId("111")
                .format(Arrays.asList("Album","CD"))
                .year(1999)
                .releaseStats(Stats.builder().numberOfWants(32768).numberOfHaves(65536).build())
                .build();
        DiscogsMasterRelease discogsMasterRelease2 = DiscogsMasterRelease.builder()
                .fullAlbumTitle("someOtherTitle")
                .masterId("222")
                .format(Arrays.asList("Single","7\""))
                .year(1999)
                .releaseStats(Stats.builder().numberOfWants(32768).numberOfHaves(65536).build())
                .build();

        DiscogsPagination discogsPagination = new DiscogsPagination(2,1,1);

        DiscogsMasterReleaseSearchResults discogsMasterReleaseSearchResults = DiscogsMasterReleaseSearchResults.builder()
                .pagination(discogsPagination)
                .results(Arrays.asList(discogsMasterRelease1,discogsMasterRelease2)).build();

        return discogsMasterReleaseSearchResults;
    }

    public DiscogsArtistSearchResults getDiscogsArtistSearchResults (String artistName){

        String discogsArtistId = "111";
        String artistImageUrl = "imageUrl";
        String discogsArtistUrl = "artistUrl";

        DiscogsArtist discogsArtist = DiscogsArtist.builder()
                .discogsArtistId(discogsArtistId)
                .artistName(artistName)
                .artistImageUrl(artistImageUrl)
                .discogsArtistUrl(discogsArtistUrl).build();
        DiscogsArtistSearchResults discogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(Arrays.asList(discogsArtist)).build();

        return discogsArtistSearchResults;
    }


}
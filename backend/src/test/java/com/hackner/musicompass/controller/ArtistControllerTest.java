package com.hackner.musicompass.controller;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.model.*;
import com.hackner.musicompass.helper.TestData;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.ArtistInfo;
import com.hackner.musicompass.model.ArtistRelease;
import com.hackner.musicompass.model.RecommendationTags;
import com.hackner.musicompass.service.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties="discogs.token=test")
class ArtistControllerTest {

    @LocalServerPort
    private int port;

    private String getUrl() {return "http://localhost:" + port + "api/artist/";}

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TimeUtils timeUtils;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ArtistMongoDb artistMongoDb;

    @BeforeEach
    public void setup(){
        artistMongoDb.deleteAll();
    }

    @Test
    @DisplayName("Get Artist who already exists in DB")
    public void getArtistWhoIsSavedInDb (){
        //GIVEN
        String artistName = "Prince";
        Instant saveDate = timeUtils.now();
        Artist artist = TestData.createArtistIntegrationTest(artistName, saveDate);
        artistMongoDb.save(artist);

        //WHEN
        ResponseEntity<Artist> response = testRestTemplate.exchange(getUrl() + artistName, HttpMethod.GET, null, Artist.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(artist));
    }

    @Test
    @DisplayName("Get Artist who does not exist in DB so far")
    public void getArtistFromApiAndSaveInDb (){
        //GIVEN
        String artistName = "Prince";
        String artistUrl = "https://api.discogs.com/database/search?type=artist&q=" + artistName;
        String releasesUrl = "https://api.discogs.com/database/search?type=master&artist=" + artistName + "&page=1&per-page_100";
        Instant saveDate = timeUtils.now();
        Artist artist = TestData.createArtistIntegrationTest(artistName, saveDate);

        DiscogsArtistSearchResults discogsArtistSearchResults = TestData.createDiscogsArtistSearchResults2(artistName);
        ResponseEntity<DiscogsArtistSearchResults> artistResponseEntity = ResponseEntity.ok(discogsArtistSearchResults);

        DiscogsMasterReleaseSearchResults discogsMasterReleaseSearchResults = TestData.createDiscogsMasterReleaseSearchResults();
        ResponseEntity<DiscogsMasterReleaseSearchResults> releaseResponseEntity = ResponseEntity.ok(discogsMasterReleaseSearchResults);

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "MusiCompass/0.1");
        headers.add("Authorization", "Discogs token=test");
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        when(restTemplate.exchange(artistUrl, HttpMethod.GET, entity, DiscogsArtistSearchResults.class))
                .thenReturn(artistResponseEntity);
        when(restTemplate.exchange(releasesUrl, HttpMethod.GET, entity, DiscogsMasterReleaseSearchResults.class))
                .thenReturn(releaseResponseEntity);

        //WHEN
        ResponseEntity<Artist> response = testRestTemplate.exchange(getUrl() + artistName, HttpMethod.GET, null, Artist.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(artist));
        assertTrue(artistMongoDb.existsById(artistName));
    }
}

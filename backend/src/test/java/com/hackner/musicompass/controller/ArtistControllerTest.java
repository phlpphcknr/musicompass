package com.hackner.musicompass.controller;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.model.*;
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
        Date saveDate = Date.from(timeUtils.now());
        Artist artist = getArtist(artistName, saveDate);
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
        Date saveDate = Date.from(timeUtils.now());
        Artist artist = getArtist(artistName, saveDate);

        DiscogsArtistSearchResults discogsArtistSearchResults = getDiscogsArtistSearchResults(artistName);
        ResponseEntity<DiscogsArtistSearchResults> artistResponseEntity = ResponseEntity.ok(discogsArtistSearchResults);

        DiscogsMasterReleaseSearchResults discogsMasterReleaseSearchResults = getDiscogsMasterReleaseSearchResults();
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

    public Artist getArtist(String artistName, Date saveDate){
        ArtistInfo artistInfo = ArtistInfo.builder()
                .discogsArtistId("111")
                .artistImageUrl("imageUrl")
                .discogsArtistUrl("artistUrl").build();
        ArtistRelease artistRelease1 = ArtistRelease.builder()
                .fullTitle("someTitle")
                .discogsMasterReleaseId("111")
                .format("Album")
                .coverImageUrl("coverImageUrl")
                .releaseYear(1999)
                .discogsWant(32768)
                .discogsHave(65536)
                .globalRating(2.0)
                .build();
        ArtistRelease artistRelease2 = ArtistRelease.builder()
                .fullTitle("someOtherTitle")
                .discogsMasterReleaseId("222")
                .format("Single/EP")
                .coverImageUrl("someOtherCoverImageUrl")
                .releaseYear(1999)
                .discogsWant(32768)
                .discogsHave(65536)
                .globalRating(2.0)
                .build();
        List<ArtistRelease> artistAlbums = Arrays.asList(artistRelease1);
        List<ArtistRelease> artistSingles = Arrays.asList(artistRelease2);
        RecommendationTags recommendationTags = RecommendationTags.builder()
                .recommended(false)
                .gender("")
                .roles(List.of())
                .genres(List.of()).build();
        Artist artist = Artist.builder()
                .artistName(artistName)
                .saveDate(saveDate)
                .artistInfo(artistInfo)
                .artistAlbums(artistAlbums)
                .artistSingles(artistSingles)
                .recommendationTags(recommendationTags).build();
        return artist;
    }

    public DiscogsMasterReleaseSearchResults getDiscogsMasterReleaseSearchResults(){

        DiscogsMasterRelease discogsMasterRelease1 = DiscogsMasterRelease.builder()
                .fullAlbumTitle("someTitle")
                .masterId("111")
                .format(Arrays.asList("Album","CD"))
                .coverImageUrl("coverImageUrl")
                .year(1999)
                .releaseStats(Stats.builder().numberOfWants(32768).numberOfHaves(65536).build())
                .build();
        DiscogsMasterRelease discogsMasterRelease2 = DiscogsMasterRelease.builder()
                .fullAlbumTitle("someOtherTitle")
                .masterId("222")
                .format(Arrays.asList("Single","7\""))
                .coverImageUrl("someOtherCoverImageUrl")
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

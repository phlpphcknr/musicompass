package com.hackner.musicompass.controller;

import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.service.DiscogsApiEntityService;
import com.hackner.musicompass.model.Artist;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArtistControllerTest {

    @LocalServerPort
    private int port;

    private String getUrl() {
        return "http://localhost:" + port + "/api/artistsearch";
    }

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private DiscogsApiEntityService discogsApiEntityService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Searching for artist name should return artist object")
    public void findExistingArtist() {

        //GIVEN
        String artistName = "Prince";
        String discogsArtistId = "6666";
        String artistImageUrl = "https://img.discogs.com/wcD5QViPvggOaC3T_D5ql1rHY_E=/150x150/smart/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/A-12596-1565996731-5432.jpeg.jpg";
        String discogsArtistUrl = "https://api.discogs.com/artists/6666";
        String baseUrl = "https://api.discogs.com";
        String discogsApiUrl = baseUrl + "/database/search?type=artist&q=" + artistName;

        DiscogsArtist testDiscogsArtist = DiscogsArtist.builder()
                .discogsArtistId(discogsArtistId)
                .artistName(artistName)
                .artistImageUrl(artistImageUrl)
                .discogsArtistUrl(discogsArtistUrl).build();
        DiscogsArtistSearchResults testDiscogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(new DiscogsArtist[]{testDiscogsArtist}).build();
        ResponseEntity<DiscogsArtistSearchResults> mockResponseEntity = ResponseEntity.ok(testDiscogsArtistSearchResults);

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "MusiCompass/0.1");
        headers.add("Authorization", "Discogs token=testtest");
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        when(discogsApiEntityService.createEntity()).thenReturn(entity);

        when(restTemplate.exchange(discogsApiUrl, HttpMethod.GET, entity, DiscogsArtistSearchResults.class))
                .thenReturn(mockResponseEntity);

        //WHEN
        ResponseEntity<Artist> controllerResponse = testRestTemplate.getForEntity(getUrl() + "/" + artistName,Artist.class);

        //THEN
        assertThat(controllerResponse.getStatusCode(), is(HttpStatus.OK));
        assertThat(controllerResponse.getBody(), is(Artist.builder()
                .artistName(artistName)
                .artistImageUrl(artistImageUrl)
                .discogsArtistId(discogsArtistId)
                .discogsArtistUrl(discogsArtistUrl).build()));
    }

    @Test
    @DisplayName("Searching for non existing artist name should return 404 error")
    public void tryToFindNonExistingArtist(){
        //GIVEN
        String artistName = "King";
        String baseUrl = "https://api.discogs.com";
        String discogsApiUrl = baseUrl + "/database/search?type=artist&q=" + artistName;

        DiscogsArtistSearchResults testDiscogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(new DiscogsArtist[]{}).build();
        ResponseEntity<DiscogsArtistSearchResults> mockResponseEntity = ResponseEntity.ok(testDiscogsArtistSearchResults);

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "MusiCompass/0.1");
        headers.add("Authorization", "Discogs token=testtest");
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        when(discogsApiEntityService.createEntity()).thenReturn(entity);

        when(restTemplate.exchange(discogsApiUrl, HttpMethod.GET, entity, DiscogsArtistSearchResults.class))
                .thenReturn(mockResponseEntity);

        //WHEN
        ResponseEntity<Artist> controllerResponse = testRestTemplate.getForEntity(getUrl() + "/" + artistName,Artist.class);

        //THEN
        assertThat(controllerResponse.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }
}
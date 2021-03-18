package com.hackner.musicompass.discogsapi.service;

import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;

import com.hackner.musicompass.secret.DiscogsSecret;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

class DiscogsArtistApiServiceTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final DiscogsSecret discogsSecret = mock(DiscogsSecret.class);
    private DiscogsApiEntityService discogsApiEntityService = new DiscogsApiEntityService(discogsSecret);
    private final DiscogsArtistApiService discogsArtistApiService = new DiscogsArtistApiService(restTemplate, discogsApiEntityService);

    @Test
    @DisplayName("Get artist from API")
    public void returnsArtist(){
        //GIVEN
        String artistName = "hans%20hammer";
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

        when(discogsSecret.getDiscogsToken()).thenReturn("testtest");
        when(restTemplate.exchange(discogsApiUrl, HttpMethod.GET, discogsApiEntityService.getEntity(), DiscogsArtistSearchResults.class))
                .thenReturn(mockResponseEntity);

        //WHEN
        DiscogsArtistSearchResults actual = discogsArtistApiService.getDiscogsArtistByArtistName(artistName);

        //THEN
        assertThat(actual, equalTo(testDiscogsArtistSearchResults));
    }

    @Test
    @DisplayName("Get empty return after trying to find non-existing artist")
    public void returnsExceptionTryingToFindNonExistingArtist(){
        //GIVEN
        String artistName = "bliblablubb";
        String baseUrl = "https://api.discogs.com";
        String discogsApiUrl = baseUrl + "/database/search?type=artist&q=" + artistName;

        DiscogsArtistSearchResults testDiscogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(new DiscogsArtist[]{}).build();
        ResponseEntity<DiscogsArtistSearchResults> mockResponseEntity = ResponseEntity.ok(testDiscogsArtistSearchResults);

        when(discogsSecret.getDiscogsToken()).thenReturn("testtest");
        when(restTemplate.exchange(discogsApiUrl, HttpMethod.GET, discogsApiEntityService.getEntity(), DiscogsArtistSearchResults.class))
                .thenReturn(mockResponseEntity);

        //WHEN
        DiscogsArtistSearchResults actual = discogsArtistApiService.getDiscogsArtistByArtistName(artistName);

        //THEN
        assertThat(actual.getResults(), emptyArray());
    }
}


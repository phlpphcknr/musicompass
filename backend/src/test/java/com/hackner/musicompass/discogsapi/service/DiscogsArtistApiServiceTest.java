package com.hackner.musicompass.discogsapi.service;

import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

class DiscogsArtistApiServiceTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final DiscogsApiEntityService discogsApiEntityService = mock(DiscogsApiEntityService.class);
    private final DiscogsArtistApiService discogsArtistApiService =
            new DiscogsArtistApiService(restTemplate, discogsApiEntityService);

    @Test
    @DisplayName("Get artist from API")
    //THis test is not needed anymore because the function is not needed anymore; still save the in simplenote!!
    public void returnsArtist(){
        //GIVEN
        String artistName = "hans hammer";
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
                .results(Arrays.asList(testDiscogsArtist)).build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "MusiCompass/0.1");
        headers.add("Authorization", "Discogs token=testtest");
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        when(discogsApiEntityService.createEntity()).thenReturn(entity);

        ResponseEntity<DiscogsArtistSearchResults> mockResponseEntity = ResponseEntity.ok(testDiscogsArtistSearchResults);
        when(restTemplate.exchange(discogsApiUrl, HttpMethod.GET, entity, DiscogsArtistSearchResults.class))
                .thenReturn(mockResponseEntity);

        //WHEN .... cut and move to simple note
        DiscogsArtistSearchResults actual = discogsArtistApiService.getDiscogsArtistByName(artistName);

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
                .results(Collections.<DiscogsArtist>emptyList()).build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "MusiCompass/0.1");
        headers.add("Authorization", "Discogs token=testtest");
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        when(discogsApiEntityService.createEntity()).thenReturn(entity);

        ResponseEntity<DiscogsArtistSearchResults> mockResponseEntity = ResponseEntity.ok(testDiscogsArtistSearchResults);

        when(restTemplate.exchange(discogsApiUrl, HttpMethod.GET, entity, DiscogsArtistSearchResults.class))
                .thenReturn(mockResponseEntity);

        //WHEN
        DiscogsArtistSearchResults actual = discogsArtistApiService.getDiscogsArtistByName(artistName);

        //THEN
        //assertThat(actual.getResults(), emptyArray());
    }
}


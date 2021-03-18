package com.hackner.musicompass.discogsapi.service;

import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@Builder
public class DiscogsArtistApiService {

    private RestTemplate restTemplate;
    private final DiscogsApiEntityService discogsApiEntityService;
    private final String baseUrl = "https://api.discogs.com";

    @Autowired
    public DiscogsArtistApiService(RestTemplate restTemplate, DiscogsApiEntityService discogsApiEntityService) {
        this.restTemplate = restTemplate;
        this.discogsApiEntityService = discogsApiEntityService;
    }

    public DiscogsArtistSearchResults getDiscogsArtistByArtistName(String artistName) {

        String url = baseUrl + "/database/search?type=artist&q=" + artistName;

        try {
            ResponseEntity<DiscogsArtistSearchResults> response =
                    restTemplate.exchange(url, HttpMethod.GET, discogsApiEntityService.createEntity(), DiscogsArtistSearchResults.class);
            return response.getBody();
        } catch (Exception e){
            return DiscogsArtistSearchResults.builder().results(new DiscogsArtist[]{}).build();
        }
    }
}

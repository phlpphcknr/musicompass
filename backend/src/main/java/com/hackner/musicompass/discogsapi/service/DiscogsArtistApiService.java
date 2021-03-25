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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public DiscogsArtistSearchResults getDiscogsArtistListBySearchTerm(String searchTerm) {

        String url = baseUrl + "/database/search?type=artist&q=" + searchTerm
                ;

        try {
            ResponseEntity<DiscogsArtistSearchResults> response =
                    restTemplate.exchange(url, HttpMethod.GET, discogsApiEntityService.createEntity(), DiscogsArtistSearchResults.class);
            return response.getBody();
        } catch (Exception e){
            return DiscogsArtistSearchResults.builder().results(Collections.<DiscogsArtist>emptyList()).build();
        }
    }
}

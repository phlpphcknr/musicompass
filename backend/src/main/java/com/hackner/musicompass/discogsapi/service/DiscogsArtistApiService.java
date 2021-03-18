package com.hackner.musicompass.discogsapi.service;

import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class DiscogsArtistApiService {

    private RestTemplate restTemplate;
    private String baseUrl = "https://api.discogs.com";
    private final DiscogsApiEntityService discogsApiEntityService;

    @Autowired
    public DiscogsArtistApiService(RestTemplate restTemplate, DiscogsApiEntityService discogsApiEntityService) {
        this.restTemplate = restTemplate;
        this.discogsApiEntityService = discogsApiEntityService;
    }

    public DiscogsArtistSearchResults getDiscogsArtistByArtistName(String artistName) {

        String url = baseUrl + "/database/search?type=artist&q=" + artistName;

        //HttpHeaders headers = new HttpHeaders();
        //headers.add("User-Agent", "MusiCompass/0.1");
        //headers.add("Authorization", "Discogs token=" + accessToken);
        //HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<DiscogsArtistSearchResults> response = restTemplate.exchange(url, HttpMethod.GET, discogsApiEntityService.createEntity(), DiscogsArtistSearchResults.class);

        return response.getBody();
    }
}

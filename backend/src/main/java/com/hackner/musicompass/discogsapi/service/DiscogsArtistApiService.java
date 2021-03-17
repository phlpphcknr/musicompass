package com.hackner.musicompass.discogsapi.service;

import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
@Slf4j
public class DiscogsArtistApiService {

    private RestTemplate restTemplate;

    private String baseUrl = "https://api.discogs.com";

    @Autowired
    public DiscogsArtistApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public DiscogsArtistSearchResults getDiscogsArtistByArtistName(String artistName, String accessToken) {

        String url = baseUrl + "/database/search?type=artist&q=" + artistName;

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "MusiCompass/0.1");
        headers.add("Authorization", "Discogs token=" + accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<DiscogsArtistSearchResults> response = restTemplate.exchange(url, HttpMethod.GET, entity, DiscogsArtistSearchResults.class);

        return response.getBody();
    }
}

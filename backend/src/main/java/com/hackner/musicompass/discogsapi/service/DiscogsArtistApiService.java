package com.hackner.musicompass.discogsapi.service;

import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.model.DiscogsMasterRelease;
import com.hackner.musicompass.discogsapi.model.DiscogsMasterReleaseSearchResults;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

        String url = baseUrl + "/database/search?type=artist&q=" + searchTerm;

        try {
            ResponseEntity<DiscogsArtistSearchResults> response =
                    restTemplate.exchange(url, HttpMethod.GET, discogsApiEntityService.createEntity(), DiscogsArtistSearchResults.class);
            return response.getBody();
        } catch (Exception e){
            return DiscogsArtistSearchResults.builder().results(Collections.<DiscogsArtist>emptyList()).build();
        }
    }

    public List<DiscogsMasterRelease> getDiscogsMasterReleaseListByArtistName(String artistName) {

        try {
            DiscogsMasterReleaseSearchResults searchResults = getResponseBodyFromRestTemplate(artistName, 1);
            List<DiscogsMasterRelease> releaseList = searchResults.getResults();
            int numberOfPages = searchResults.getPagination().getPages();

            if (numberOfPages > 1) {
                for (int i = 2; i < numberOfPages + 1; i++) {
                    DiscogsMasterReleaseSearchResults nextResponse = getResponseBodyFromRestTemplate(artistName,i);
                    releaseList.addAll(nextResponse.getResults());
                }
            }
            return releaseList;

        } catch (Exception e) {
            return Collections.<DiscogsMasterRelease>emptyList();
        }
    }

    public String dynamicUrl(String artistName, int i){
        return baseUrl + "/database/search?type=master&artist=" + artistName + "&page=" + Integer.toString(i) + "&per-page_100";
    }

    public DiscogsMasterReleaseSearchResults getResponseBodyFromRestTemplate(String artistName, int page){
        ResponseEntity<DiscogsMasterReleaseSearchResults> response =
                restTemplate.exchange(dynamicUrl(artistName, page),
                        HttpMethod.GET,
                        discogsApiEntityService.createEntity(),
                        DiscogsMasterReleaseSearchResults.class);
        return response.getBody();
    }
}

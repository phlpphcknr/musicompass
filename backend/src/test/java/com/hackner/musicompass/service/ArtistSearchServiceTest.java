package com.hackner.musicompass.service;

import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.helper.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArtistSearchServiceTest {

    private final DiscogsArtistApiService discogsArtistApiService = mock(DiscogsArtistApiService.class);
    private final ArtistSearchService artistSearchService =  new ArtistSearchService(discogsArtistApiService);

    @Test
    @DisplayName("Search for artist by search term returns an empty list")
    public void searchForArtistBySearchTermEmptyList(){
        //GIVEN
        String searchTerm = "bliblablubb";

        DiscogsArtistSearchResults discogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(Collections.<DiscogsArtist>emptyList()).build();

        when(discogsArtistApiService.getDiscogsArtistListBySearchTerm(searchTerm))
                .thenReturn(discogsArtistSearchResults);

        //WHEN
        List<Artist> actual = artistSearchService.getArtistListBySearchTerm(searchTerm);

        //THEN
        assertThat(actual.isEmpty(), is(true));
    }

    @Test
    @DisplayName("Search for artist by search term returns a list with less than 5 entries")
    public void searchForArtistBySearchTermShortList(){
        //GIVEN
        String searchTerm ="Hans";
        when(discogsArtistApiService.getDiscogsArtistListBySearchTerm(searchTerm)).thenReturn(TestData.createDiscogsArtistsSearchResult(3));

        //WHEN
        List<Artist> actual = artistSearchService.getArtistListBySearchTerm(searchTerm);

        //THEN
        assertThat(actual, is(TestData.createBasicArtistList(3)));
    }

    @Test
    @DisplayName("Search for artist by search term returns a list with more than 5 entries")
    public void searchForArtistBySearchTermLongList(){
        //GIVEN
        String searchTerm ="Hans";
        when(discogsArtistApiService.getDiscogsArtistListBySearchTerm(searchTerm)).thenReturn(TestData.createDiscogsArtistsSearchResult(6));

        //WHEN
        List<Artist> actual = artistSearchService.getArtistListBySearchTerm(searchTerm);

        //THEN
        assertThat(actual, is(TestData.createBasicArtistList(5)));
        assertThat(actual.size(), is(5));
    }
}

package com.hackner.musicompass.service;

import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.ArtistInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
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
        when(discogsArtistApiService.getDiscogsArtistListBySearchTerm(searchTerm)).thenReturn(getDiscogsArtistsSearchResult(3));

        //WHEN
        List<Artist> actual = artistSearchService.getArtistListBySearchTerm(searchTerm);

        //THEN
        assertThat(actual, is(getArtistsList(3)));
    }

    @Test
    @DisplayName("Search for artist by search term returns a list with more than 5 entries")
    public void searchForArtistBySearchTermLongList(){
        //GIVEN
        String searchTerm ="Hans";
        when(discogsArtistApiService.getDiscogsArtistListBySearchTerm(searchTerm)).thenReturn(getDiscogsArtistsSearchResult(6));

        //WHEN
        List<Artist> actual = artistSearchService.getArtistListBySearchTerm(searchTerm);

        //THEN
        assertThat(actual, is(getArtistsList(5)));
        assertThat(actual.size(), is(5));
    }

    public DiscogsArtistSearchResults getDiscogsArtistsSearchResult(int listlength){

        DiscogsArtist discogsArtist1 = DiscogsArtist.builder()
                .discogsArtistId("111")
                .artistName("Hans Hammer")
                .artistImageUrl("HammerImageUrl")
                .discogsArtistUrl("HammerUrl").build();
        DiscogsArtist discogsArtist2 = DiscogsArtist.builder()
                .discogsArtistId("222")
                .artistName("Hans Sichel")
                .artistImageUrl("SichelImageUrl")
                .discogsArtistUrl("SichelUrl").build();
        DiscogsArtist discogsArtist3 = DiscogsArtist.builder()
                .discogsArtistId("333")
                .artistName("Hans Bohrer")
                .artistImageUrl("BohrerImageUrl")
                .discogsArtistUrl("BohrerUrl").build();
        DiscogsArtist discogsArtist4 = DiscogsArtist.builder()
                .discogsArtistId("444")
                .artistName("Hans Meisel")
                .artistImageUrl("MeiselImageUrl")
                .discogsArtistUrl("MeiselUrl").build();
        DiscogsArtist discogsArtist5 = DiscogsArtist.builder()
                .discogsArtistId("555")
                .artistName("Hans Nagel")
                .artistImageUrl("NagelImageUrl")
                .discogsArtistUrl("NagelUrl").build();
        DiscogsArtist discogsArtist6 = DiscogsArtist.builder()
                .discogsArtistId("666")
                .artistName("Hans Mutter")
                .artistImageUrl("MutterImageUrl")
                .discogsArtistUrl("MutterUrl").build();

        List<DiscogsArtist> results = Arrays.asList(discogsArtist1,discogsArtist2,discogsArtist3,discogsArtist4,discogsArtist5,discogsArtist6);

        return DiscogsArtistSearchResults.builder().results(results.subList(0,listlength)).build();
    }

    public List<Artist> getArtistsList(int listlength){

        Artist artist1 = Artist.builder()
                .artistName("Hans Hammer")
                .artistInfo(new ArtistInfo().builder()
                        .artistImageUrl("HammerImageUrl")
                        .discogsArtistId("111")
                        .discogsArtistUrl("HammerUrl")
                        .build())
                .build();
        Artist artist2 = Artist.builder()
                .artistName("Hans Sichel")
                .artistInfo(new ArtistInfo().builder()
                        .artistImageUrl("SichelImageUrl")
                        .discogsArtistId("222")
                        .discogsArtistUrl("SichelUrl")
                        .build())
                .build();
        Artist artist3 = Artist.builder()
                .artistName("Hans Bohrer")
                .artistInfo(new ArtistInfo().builder()
                        .artistImageUrl("BohrerImageUrl")
                        .discogsArtistId("333")
                        .discogsArtistUrl("BohrerUrl")
                        .build())
                .build();
        Artist artist4 = Artist.builder()
                .artistName("Hans Meisel")
                .artistInfo(new ArtistInfo().builder()
                        .artistImageUrl("MeiselImageUrl")
                        .discogsArtistId("444")
                        .discogsArtistUrl("MeiselUrl")
                        .build())
                .build();
        Artist artist5 = Artist.builder()
                .artistName("Hans Nagel")
                .artistInfo(new ArtistInfo().builder()
                        .artistImageUrl("NagelImageUrl")
                        .discogsArtistId("555")
                        .discogsArtistUrl("NagelUrl")
                        .build())
                .build();

        List<Artist> results = Arrays.asList(artist1,artist2,artist3,artist4,artist5);

        return results.subList(0,listlength);
    }
}

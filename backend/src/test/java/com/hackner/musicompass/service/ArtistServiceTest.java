package com.hackner.musicompass.service;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import com.hackner.musicompass.model.Artist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArtistServiceTest {

    private final DiscogsArtistApiService discogsArtistApiService = mock(DiscogsArtistApiService.class);
    private final ArtistMongoDb artistMongoDb = mock(ArtistMongoDb.class);
    private final ArtistService artistService =  new ArtistService(discogsArtistApiService, artistMongoDb);

    @Test
    @DisplayName("Search for artist by search term returns an empty list")
    public void searchForArtistBySearchTermEmptyList(){
        //GIVEN
        String searchTerm = "bliblablubb";

        DiscogsArtistSearchResults discogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(Collections.<DiscogsArtist>emptyList()).build();

        when(discogsArtistApiService.getDiscogsArtistByName(searchTerm))
                .thenReturn(discogsArtistSearchResults);

        //WHEN
        List<Artist> actual = artistService.getArtistListBySearchTerm(searchTerm);

        //THEN
        assertThat(actual.isEmpty(), is(true));
    }


    @Test
    @DisplayName("Search for artist by search term returns a list with less than 5 entries")
    public void searchForArtistBySearchTermShortList(){
        //GIVEN
        String searchTerm ="Hans";

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

        Artist artist1 = Artist.builder()
                .discogsArtistId("111")
                .artistName("Hans Hammer")
                .artistImageUrl("HammerImageUrl")
                .discogsArtistUrl("HammerUrl").build();
        Artist artist2 = Artist.builder()
                .discogsArtistId("222")
                .artistName("Hans Sichel")
                .artistImageUrl("SichelImageUrl")
                .discogsArtistUrl("SichelUrl").build();
        Artist artist3 = Artist.builder()
                .discogsArtistId("333")
                .artistName("Hans Bohrer")
                .artistImageUrl("BohrerImageUrl")
                .discogsArtistUrl("BohrerUrl").build();

        DiscogsArtistSearchResults discogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(Arrays.asList(discogsArtist1,discogsArtist2,discogsArtist3)).build();

        when(discogsArtistApiService.getDiscogsArtistByName(searchTerm)).thenReturn(discogsArtistSearchResults);

        //WHEN
        List<Artist> actual = artistService.getArtistListBySearchTerm(searchTerm);

        //THEN
        assertThat(actual, contains(artist1,artist2,artist3));
    }


    @Test
    @DisplayName("Search for artist by search term returns a list with more than 5 entries")
    public void searchForArtistBySearchTermLongList(){
        //GIVEN
        String searchTerm ="Hans";

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

        Artist artist1 = Artist.builder()
                .discogsArtistId("111")
                .artistName("Hans Hammer")
                .artistImageUrl("HammerImageUrl")
                .discogsArtistUrl("HammerUrl").build();
        Artist artist2 = Artist.builder()
                .discogsArtistId("222")
                .artistName("Hans Sichel")
                .artistImageUrl("SichelImageUrl")
                .discogsArtistUrl("SichelUrl").build();
        Artist artist3 = Artist.builder()
                .discogsArtistId("333")
                .artistName("Hans Bohrer")
                .artistImageUrl("BohrerImageUrl")
                .discogsArtistUrl("BohrerUrl").build();
        Artist artist4 = Artist.builder()
                .discogsArtistId("444")
                .artistName("Hans Meisel")
                .artistImageUrl("MeiselImageUrl")
                .discogsArtistUrl("MeiselUrl").build();
        Artist artist5 = Artist.builder()
                .discogsArtistId("555")
                .artistName("Hans Nagel")
                .artistImageUrl("NagelImageUrl")
                .discogsArtistUrl("NagelUrl").build();

        DiscogsArtistSearchResults discogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(Arrays.asList(discogsArtist1,discogsArtist2,discogsArtist3,discogsArtist4,discogsArtist5,discogsArtist6)).build();

        when(discogsArtistApiService.getDiscogsArtistByName(searchTerm)).thenReturn(discogsArtistSearchResults);

        //WHEN
        List<Artist> actual = artistService.getArtistListBySearchTerm(searchTerm);

        //THEN
        assertThat(actual, contains(artist1,artist2,artist3,artist4,artist5));
        assertThat(actual.size(), is(5));
    }
}

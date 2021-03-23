package com.hackner.musicompass.service;

import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import com.hackner.musicompass.model.Artist;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArtistServiceTest {

    private final DiscogsArtistApiService testDiscogsArtistApiService = mock(DiscogsArtistApiService.class);
    private final ArtistService testArtistService = ArtistService.builder()
            .discogsArtistApiService(testDiscogsArtistApiService)
            .build();


    @Test
    @DisplayName("TestArtistServiceForExistingArtist")
    public void getArtistFromArtistService(){
        //GIVEN
        String artistName = "hans hammer";
        String discogsArtistId = "6666";
        String artistImageUrl = "https://img.discogs.com/wcD5QViPvggOaC3T_D5ql1rHY_E=/150x150/smart/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/A-12596-1565996731-5432.jpeg.jpg";
        String discogsArtistUrl = "https://api.discogs.com/artists/6666";

        DiscogsArtist testDiscogsArtist = DiscogsArtist.builder()
                .discogsArtistId(discogsArtistId)
                .artistName(artistName)
                .artistImageUrl(artistImageUrl)
                .discogsArtistUrl(discogsArtistUrl).build();
        DiscogsArtistSearchResults testDiscogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(new DiscogsArtist[]{testDiscogsArtist}).build();

        when(testDiscogsArtistApiService.getDiscogsArtistByArtistName(artistName)).thenReturn(testDiscogsArtistSearchResults);

        //WHEN
        Optional<Artist> actual = testArtistService.getArtistBySearchTerm(artistName);

        //THEN
        assertThat(actual.get(), equalTo(Artist.builder()
                .artistName(artistName)
                .artistImageUrl(artistImageUrl)
                .discogsArtistId(discogsArtistId)
                .discogsArtistUrl(discogsArtistUrl).build()));
    }

    @Test
    @DisplayName("TestArtistServiceForNonExistingArtist")
    public void getExceptionFromArtistService(){
        //GIVEN
        String artistName = "bliblablubb";

        DiscogsArtistSearchResults testDiscogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(new DiscogsArtist[]{}).build();

        when(testDiscogsArtistApiService.getDiscogsArtistByArtistName(artistName))
                .thenReturn(testDiscogsArtistSearchResults);

        //WHEN
        Optional<Artist> actual = testArtistService.getArtistBySearchTerm(artistName);

        //THEN
        assertThat(actual, is(Optional.empty()));
    }
}

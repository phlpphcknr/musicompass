package com.hackner.musicompass.service;

import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.secret.DiscogsSecret;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArtistServiceTest {

    private final DiscogsArtistApiService testDiscogsArtistApiService = mock(DiscogsArtistApiService.class);
    //private final DiscogsSecret testDiscogsSecret = mock(DiscogsSecret.class);
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
        String testAccessToken = "testtest";

        Artist testArtist = Artist.builder()
                .artistName(artistName)
                .artistImageUrl(artistImageUrl)
                .discogsId(discogsArtistId)
                .discogsArtistUrl(discogsArtistUrl).build();

        DiscogsArtist testDiscogsArtist = DiscogsArtist.builder()
                .discogsArtistId(discogsArtistId)
                .artistName(artistName)
                .artistImageUrl(artistImageUrl)
                .discogsArtistUrl(discogsArtistUrl).build();

        DiscogsArtistSearchResults testDiscogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(new DiscogsArtist[]{testDiscogsArtist}).build();

        //when(testDiscogsSecret.getDiscogsToken()).thenReturn(testAccessToken);
        when(testDiscogsArtistApiService.getDiscogsArtistByArtistName(artistName)).thenReturn(testDiscogsArtistSearchResults);

        //WHEN
        Optional<Artist> actual = testArtistService.getArtistByArtistName(artistName);

        //THEN
        assertThat(actual.get(), equalTo(testArtist));
    }

    @Test
    @DisplayName("TestArtistServiceForNonExistingArtist")
    public void getExceptionFromArtistService(){
        //GIVEN
        String artistName = "bliblablubb";
        String discogsArtistId = "6666";
        String artistImageUrl = "https://img.discogs.com/wcD5QViPvggOaC3T_D5ql1rHY_E=/150x150/smart/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/A-12596-1565996731-5432.jpeg.jpg";
        String discogsArtistUrl = "https://api.discogs.com/artists/6666";
        String testAccessToken = "testtest";

        DiscogsArtistSearchResults testDiscogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(new DiscogsArtist[]{}).build();

        //when(testDiscogsSecret.getDiscogsToken()).thenReturn(testAccessToken);
        when(testDiscogsArtistApiService.getDiscogsArtistByArtistName(artistName))
                .thenReturn(testDiscogsArtistSearchResults);

        //WHEN
        Optional<Artist> actual = testArtistService.getArtistByArtistName(artistName);

        //THEN
        assertThat(actual, Matchers.is(Optional.empty()));
    }
}
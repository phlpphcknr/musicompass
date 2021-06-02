package com.hackner.musicompass.service;

import com.hackner.musicompass.discogsapi.model.DiscogsMasterRelease;
import com.hackner.musicompass.model.ArtistRelease;
import com.hackner.musicompass.helper.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ArtistReleaseServiceTest {

    private final ArtistReleaseService artistReleaseService = new ArtistReleaseService();

    @Test
    @DisplayName("Get albums list from discogsMasterReleaseList")
    public void getAlbumList (){
        //GIVEN
        List<DiscogsMasterRelease> discogsMasterReleaseList = TestData.createDiscogsMasterReleaseList();

        //WHEN
        List<ArtistRelease> actual = artistReleaseService.getSortedReleaseList("Album", discogsMasterReleaseList);

        //THEN
        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getGlobalRating() > actual.get(1).getGlobalRating(), is(true));
        assertThat(actual.get(1), equalTo(TestData.createArtistReleaseList().get(0)));
    }

    @Test
    @DisplayName("Get singles list from discogsMasterReleaseList")
    public void getSingleList (){
        //GIVEN
        List<DiscogsMasterRelease> discogsMasterReleaseList = TestData.createDiscogsMasterReleaseList();

        //WHEN
        List<ArtistRelease> actual = artistReleaseService.getSortedReleaseList("Album", discogsMasterReleaseList);

        //THEN
        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getGlobalRating() > actual.get(1).getGlobalRating(), is(true));
    }

    @Test
    @DisplayName("Test global rating calculation")
    public void getGlobalRatingValue(){
        //GIVEN
        int have = 1024;
        int want = 32768;

        //WHEN
        double actual = artistReleaseService.calculateGlobalRating(have, want);

        //THEN
        assertThat(actual, is(16.0));
    }
}

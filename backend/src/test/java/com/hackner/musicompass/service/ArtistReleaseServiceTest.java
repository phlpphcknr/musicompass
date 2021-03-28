package com.hackner.musicompass.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.discogsapi.model.DiscogsMasterRelease;
import com.hackner.musicompass.discogsapi.model.Stats;
import com.hackner.musicompass.model.ArtistRelease;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ArtistReleaseServiceTest {

    private final ArtistReleaseService artistReleaseService = new ArtistReleaseService();

    @Test
    @DisplayName("Get album list from discogsMasterReleaseList")
    public void getAlbumList (){
        //GIVEN
        List<DiscogsMasterRelease> discogsMasterReleaseList = getDiscogsMasterReleaseList();

        //WHEN
        List<ArtistRelease> actual = artistReleaseService.getSortedReleaseList("Album", discogsMasterReleaseList);

        //THEN
        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getGlobalRating() > actual.get(1).getGlobalRating(), is(true));
        assertThat(actual.get(1), equalTo(getArtistReleaseList().get(0)));
    }

    @Test
    @DisplayName("Get album list from discogsMasterReleaseList")
    public void getSingleList (){
        //GIVEN
        List<DiscogsMasterRelease> discogsMasterReleaseList = getDiscogsMasterReleaseList();

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
        int have = 256;
        int want = 128;

        //WHEN
        double actual = artistReleaseService.calculateGlobalRating(have, want);

        //THEN
        assertThat(actual, is(1.0));
    }

    public List<ArtistRelease> getArtistReleaseList(){

        ArtistRelease artistRelease1 = ArtistRelease.builder()
                .fullTitle("someTitle")
                .discogsMasterReleaseId("111")
                .format("Album")
                .releaseYear(1999)
                .discogsWant(32768)
                .discogsHave(65536)
                .globalRating(2.0)
                .build();

        List<ArtistRelease> results = Arrays.asList(artistRelease1);

        return results;
    }

    public List<DiscogsMasterRelease> getDiscogsMasterReleaseList(){

        Stats stats1 = Stats.builder().numberOfWants(32768).numberOfHaves(65536).build();
        DiscogsMasterRelease discogsMasterRelease1 = DiscogsMasterRelease.builder()
                .year(1999)
                .format(Arrays.asList("Album","Compilation","CD"))
                .masterId("111")
                .fullAlbumTitle("someTitle")
                .releaseStats(stats1)
                .build();
        Stats stats2 = Stats.builder().numberOfWants(110).numberOfHaves(88).build();
        DiscogsMasterRelease discogsMasterRelease2 = DiscogsMasterRelease.builder()
                .year(1999)
                .format(Arrays.asList("Album","LP"))
                .masterId("112")
                .fullAlbumTitle("anotherTitle")
                .releaseStats(stats2)
                .build();
        Stats stats3 = Stats.builder().numberOfWants(110).numberOfHaves(176).build();
        DiscogsMasterRelease discogsMasterRelease3 = DiscogsMasterRelease.builder()
                .year(1999)
                .format(Arrays.asList("Single","CD"))
                .masterId("113")
                .fullAlbumTitle("differentTitle")
                .releaseStats(stats3)
                .build();
        Stats stats4 = Stats.builder().numberOfWants(110).numberOfHaves(44).build();
        DiscogsMasterRelease discogsMasterRelease4 = DiscogsMasterRelease.builder()
                .year(1999)
                .format(Arrays.asList("EP","12\""))
                .masterId("114")
                .fullAlbumTitle("oneMoreTitle")
                .releaseStats(stats4)
                .build();

        List<DiscogsMasterRelease> results = Arrays.asList(discogsMasterRelease1,discogsMasterRelease2,discogsMasterRelease3,discogsMasterRelease4);

        return results;
    }


}
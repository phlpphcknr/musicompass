package com.hackner.musicompass.service;

import com.hackner.musicompass.model.ArtistRelease;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtistReleaseServiceTest {

    private final ArtistReleaseService artistReleaseService = new ArtistReleaseService();

    @Test
    @DisplayName("Get album list from discogsMasterReleaseList")
    public void getAlbumList (){
        //GIVEN


        //WHEN
        List<ArtistRelease> actual = artistReleaseService.getSortedReleaseList("Album", discogsM)

        //THEN
    }



}
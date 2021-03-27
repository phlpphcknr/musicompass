package com.hackner.musicompass.service;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ArtistServiceTest {

    private final DiscogsArtistApiService discogsArtistApiService = mock(DiscogsArtistApiService.class);
    private final ArtistMongoDb artistMongoDb = mock(ArtistMongoDb.class);
    private final ArtistService artistService = new ArtistService(discogsArtistApiService, artistMongoDb);

    @Test
    @DisplayName("")


}
package com.hackner.musicompass.controller;

import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("artistsearch/{searchTerm}")
    public Artist findArtist(@PathVariable String searchTerm){
        return artistService.getArtistBySearchTerm(searchTerm)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "artist name not found"));
    }

    @GetMapping("artist/{artistName}")
    public Artist getArtist(@PathVariable String artistName){
        return artistService.getArtistByName(artistName);
    }
}
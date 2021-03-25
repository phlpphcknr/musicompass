package com.hackner.musicompass.controller;

import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("artistsearch/{searchTerm}")
    public List<Artist> findArtist(@PathVariable String searchTerm){
        return artistService.getArtistListBySearchTerm(searchTerm);
    }

    @GetMapping("artist/{artistName}")
    public Artist getArtist(@PathVariable String artistName){
        return artistService.getArtistByName(artistName);
    }
}
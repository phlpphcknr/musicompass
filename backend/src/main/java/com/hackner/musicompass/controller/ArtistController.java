package com.hackner.musicompass.controller;

import com.hackner.musicompass.model.ArtistInfo;
import com.hackner.musicompass.service.ArtistInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/")
public class ArtistController {

    private final ArtistInfoService artistInfoService;

    @Autowired
    public ArtistController(ArtistInfoService artistInfoService) {
        this.artistInfoService = artistInfoService;
    }

    @GetMapping("artistsearch/{searchTerm}")
    public List<ArtistInfo> findArtist(@PathVariable String searchTerm){
        return artistInfoService.getArtistInfoListBySearchTerm(searchTerm);
    }

    @GetMapping("artist/{artistName}")
    public ArtistInfo getArtist(@PathVariable String artistName){
        return artistInfoService.getArtistByName(artistName);
    }
}
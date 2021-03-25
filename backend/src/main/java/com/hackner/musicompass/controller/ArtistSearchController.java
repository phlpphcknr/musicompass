package com.hackner.musicompass.controller;

import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.service.ArtistSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/artistsearch/")
public class ArtistSearchController {

    private final ArtistSearchService artistSearchService;

    @Autowired
    public ArtistSearchController(ArtistSearchService artistSearchService) {
        this.artistSearchService = artistSearchService;
    }

    @GetMapping("{searchTerm}")
    public List<Artist> getArtistInfoListBySearchTerm(@PathVariable String searchTerm){
        return artistSearchService.getArtistInfoListBySearchTerm(searchTerm);
    }
}
package com.hackner.musicompass.db;

import com.hackner.musicompass.model.Artist;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArtistMongoDb extends PagingAndSortingRepository<Artist, String> {

    List<Artist> findAll();

}

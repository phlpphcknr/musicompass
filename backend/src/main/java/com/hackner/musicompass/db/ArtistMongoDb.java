package com.hackner.musicompass.db;

import com.hackner.musicompass.model.ArtistInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArtistMongoDb extends PagingAndSortingRepository<ArtistInfo, String> {

    List<ArtistInfo> findAll();

}

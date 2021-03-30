package com.hackner.musicompass.db;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecommendationCategoryMongoDb extends PagingAndSortingRepository<String[], String> {

    List<String[]> findAll();

}

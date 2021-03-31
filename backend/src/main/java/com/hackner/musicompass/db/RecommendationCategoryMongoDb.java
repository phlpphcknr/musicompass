package com.hackner.musicompass.db;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecommendationCategoryMongoDb extends PagingAndSortingRepository<List<String>, String> {

    List<List<String>> findAll();

}

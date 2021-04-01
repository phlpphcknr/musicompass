package com.hackner.musicompass.db;

import com.hackner.musicompass.model.RecommendationCategory;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RecommendationCategoryMongoDb extends PagingAndSortingRepository<RecommendationCategory, String> {

    List<RecommendationCategory> findAll();

}

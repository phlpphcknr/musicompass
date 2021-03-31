package com.hackner.musicompass.service;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.model.RecommendationTags;
import com.hackner.musicompass.model.RecommendationTagsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RecommendationService {

    private final ArtistMongoDb artistMongoDb;
    private final TimeUtils timeUtils;

    @Autowired
    public RecommendationService(ArtistMongoDb artistMongoDb, TimeUtils timeUtils) {
        this.artistMongoDb = artistMongoDb;
        this.timeUtils = timeUtils;
    }

    public RecommendationTags changeRecommendationTags (RecommendationTagsDto recommendationTagsDto){
        RecommendationTags recommendationTags = RecommendationTags.builder()
                .recommended(true)
                .genres(recommendationTagsDto.getGenres())
                .roles(recommendationTagsDto.getRoles())
                .gender(recommendationTagsDto.getGender())
                .changeDate(Date.from(timeUtils.now())).build();



        return recommendationTags;
    }
}

package com.hackner.musicompass.service;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.db.RecommendationCategoryMongoDb;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.RecommendationTags;
import com.hackner.musicompass.model.RecommendationTagsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RecommendationService {

    private final ArtistMongoDb artistMongoDb;
    private final RecommendationCategoryMongoDb recommendationCategoryMongoDb;
    private final TimeUtils timeUtils;

    @Autowired
    public RecommendationService(ArtistMongoDb artistMongoDb, RecommendationCategoryMongoDb recommendationCategoryMongoDb, TimeUtils timeUtils) {
        this.artistMongoDb = artistMongoDb;
        this.recommendationCategoryMongoDb = recommendationCategoryMongoDb;
        this.timeUtils = timeUtils;
    }

    public RecommendationTags changeRecommendationTags (RecommendationTagsDto recommendationTagsDto){

        Artist artist = artistMongoDb.findById(recommendationTagsDto.getArtistName()).get();

        RecommendationTags recommendationTags = RecommendationTags.builder()
                .recommended(true)
                .genres(recommendationTagsDto.getGenres())
                .roles(recommendationTagsDto.getRoles())
                .gender(recommendationTagsDto.getGender())
                .changeDate(Date.from(timeUtils.now())).build();

        artist.setRecommendationTags(recommendationTags);

        artistMongoDb.save(artist);

        return recommendationTags;
    }

    public List<List<String>> getRecommendationCategoryValues(){
        return recommendationCategoryMongoDb.findAll();
    }

}

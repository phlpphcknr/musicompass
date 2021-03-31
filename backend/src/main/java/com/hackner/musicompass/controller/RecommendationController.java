package com.hackner.musicompass.controller;

import com.hackner.musicompass.model.RecommendationTagsDto;
import com.hackner.musicompass.model.RecommendationTags;
import com.hackner.musicompass.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("{artistName}")
    public RecommendationTags changeRecommendationTag(@RequestBody RecommendationTagsDto recommendationTagsDto){
        return this.recommendationService.changeRecommendationTags(RecommendationTagsDto recommendationTagsDto);
    }
}

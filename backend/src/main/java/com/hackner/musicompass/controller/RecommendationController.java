package com.hackner.musicompass.controller;

import com.hackner.musicompass.model.RecommendationTagsDto;
import com.hackner.musicompass.model.RecommendationTags;
import com.hackner.musicompass.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return this.recommendationService.changeRecommendationTags(recommendationTagsDto);
    }

    @GetMapping()
    public List<List<String>> getRecommendationCategoryValues(){
        return recommendationService.getRecommendationCategoryValues();
    }
}

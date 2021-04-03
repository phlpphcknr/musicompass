package com.hackner.musicompass.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecommendationRequestDto {

    @JsonProperty("genreTags")
    private List<String> genres;
    @JsonProperty("rolesTags")
    private List<String> roles;
    @JsonProperty("genderTag")
    private List<String> gender;
}

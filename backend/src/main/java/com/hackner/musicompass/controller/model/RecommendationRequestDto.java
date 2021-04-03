package com.hackner.musicompass.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationRequestDto {

    @JsonProperty("genderTag")
    private List<String> gender;
    @JsonProperty("genreTags")
    private List<String> genres;
    @JsonProperty("rolesTags")
    private List<String> roles;

}

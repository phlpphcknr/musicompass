package com.hackner.musicompass.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationTagsDto {

    private String artistName;
    private List<String> genres;
    private List<String> roles;
    private String gender;


}

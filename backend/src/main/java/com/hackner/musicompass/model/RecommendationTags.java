package com.hackner.musicompass.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RecommendationTags {

    private Boolean recommended;
    private List<String> genres;
    private List<String> roles;
    private String gender;
    private Date changeDate;
}

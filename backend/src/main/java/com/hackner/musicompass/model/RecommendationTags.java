package com.hackner.musicompass.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationTags {

    private Boolean recommended;
    private List<String> genres;
    private List<String> roles;
    private String gender;
    @EqualsAndHashCode.Exclude
    private Date changeDate;
}

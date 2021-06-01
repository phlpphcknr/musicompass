package com.hackner.musicompass.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationTags {

    private Boolean recommended;
    private List<String> genres;
    private List<String> roles;
    private List<String> gender;
    @EqualsAndHashCode.Exclude
    private LocalDateTime changeDate;
}

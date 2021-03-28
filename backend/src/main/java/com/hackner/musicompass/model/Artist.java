package com.hackner.musicompass.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    @Id
    private String artistName;
    private Date saveDate;
    private ArtistInfo artistInfo;
    private List<ArtistRelease> artistReleases;
    private List<ArtistRelease> artistSingles;
    private RecommendationTags recommendationTags;
}

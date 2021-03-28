package com.hackner.musicompass.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistRelease {

    private String fullTitle;
    private String discogsMasterReleaseId;
    private String format;
    private int releaseYear;
    private int discogsWant;
    private int discogsHave;
    private double globalRating;
}

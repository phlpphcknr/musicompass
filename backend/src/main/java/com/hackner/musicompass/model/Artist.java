package com.hackner.musicompass.model;

import lombok.*;
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
    @EqualsAndHashCode.Exclude
    private Date saveDate;
    private ArtistInfo artistInfo;
    private List<ArtistRelease> artistAlbums;
    private List<ArtistRelease> artistSingles;
    private RecommendationTags recommendationTags;

}

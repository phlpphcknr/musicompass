package com.hackner.musicompass.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    @Id
    private String artistName;
    private ArtistInfo artistInfo;
    private List<ArtistAlbum> artistAlbums;
    private List<ArtistSingle> artistSingles;
    private RecommendationTags recommendationTags;
}

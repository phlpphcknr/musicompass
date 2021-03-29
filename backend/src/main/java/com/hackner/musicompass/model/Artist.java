package com.hackner.musicompass.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Artist {


    @Id
    private String artistName;
    private Date saveDate;
    private ArtistInfo artistInfo;
    private List<ArtistRelease> artistAlbums;
    private List<ArtistRelease> artistSingles;
    private RecommendationTags recommendationTags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(artistName, artist.artistName) && Objects.equals(artistInfo, artist.artistInfo) && Objects.equals(artistAlbums, artist.artistAlbums) && Objects.equals(artistSingles, artist.artistSingles) && Objects.equals(recommendationTags, artist.recommendationTags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistName, artistInfo, artistAlbums, artistSingles, recommendationTags);
    }
}

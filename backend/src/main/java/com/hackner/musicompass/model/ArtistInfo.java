package com.hackner.musicompass.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistInfo {

    private String artistName;
    private String artistImageUrl;
    private String discogsArtistId;
    private String discogsArtistUrl;

}

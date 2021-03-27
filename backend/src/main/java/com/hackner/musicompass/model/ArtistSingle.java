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
public class ArtistSingle {

    private String fullTitle;
    private String discogsMasterReleaseId;
    private List<String> format;
    private int releaseYear;
    private int discogsWant;
    private int discogsHave;
}

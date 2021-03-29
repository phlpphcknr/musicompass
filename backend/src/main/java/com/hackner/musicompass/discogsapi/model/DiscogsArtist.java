package com.hackner.musicompass.discogsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscogsArtist {

    @JsonProperty("id")
    private String discogsArtistId;
    @JsonProperty("title")
    private String artistName;
    @JsonProperty("thumb")
    private String artistImageUrl;
    @JsonProperty("resource_url")
    private String discogsArtistUrl;
}



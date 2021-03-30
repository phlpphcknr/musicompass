package com.hackner.musicompass.discogsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stats {

    @JsonProperty("want")
    private int numberOfWants;
    @JsonProperty("have")
    private int numberOfHaves;
}

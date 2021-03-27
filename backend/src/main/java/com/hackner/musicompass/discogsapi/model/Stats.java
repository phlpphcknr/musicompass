package com.hackner.musicompass.discogsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Stats {

    @JsonProperty("want")
    private int numberOfWants;
    @JsonProperty("have")
    private int numberOfHaves;
}

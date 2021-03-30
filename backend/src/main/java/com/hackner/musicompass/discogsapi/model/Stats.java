package com.hackner.musicompass.discogsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stats {

    @JsonProperty("want")
    private int numberOfWants;
    @JsonProperty("have")
    private int numberOfHaves;
}

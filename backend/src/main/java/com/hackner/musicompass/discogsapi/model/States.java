package com.hackner.musicompass.discogsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class States {

    @JsonProperty("want")
    private int numberOfWants;
    @JsonProperty("have")
    private int numberOfHaves;


}

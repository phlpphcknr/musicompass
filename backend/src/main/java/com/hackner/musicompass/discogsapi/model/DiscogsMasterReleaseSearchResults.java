package com.hackner.musicompass.discogsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscogsMasterReleaseSearchResults {

    @JsonProperty("pagination")
    private DiscogsPagination pagination;
    @JsonProperty("results")
    private List<DiscogsMasterRelease> results;
}

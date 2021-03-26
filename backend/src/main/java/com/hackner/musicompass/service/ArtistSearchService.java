package com.hackner.musicompass.service;
import com.hackner.musicompass.discogsapi.model.DiscogsArtist;
import com.hackner.musicompass.discogsapi.model.DiscogsArtistSearchResults;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.ArtistInfo;
import com.hackner.musicompass.discogsapi.service.DiscogsArtistApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistSearchService {
    private final DiscogsArtistApiService discogsArtistApiService;

    @Autowired
    public ArtistSearchService(DiscogsArtistApiService discogsArtistApiService) {
        this.discogsArtistApiService = discogsArtistApiService;
    }

    public List<Artist> getArtistInfoListBySearchTerm(String searchTerm) {
        DiscogsArtistSearchResults discogsArtistSearchResults = discogsArtistApiService.getDiscogsArtistListBySearchTerm(searchTerm);

        List<DiscogsArtist> discogsArtistList = discogsArtistSearchResults.getResults();

        if (discogsArtistList.size() > 5) {
            discogsArtistList = discogsArtistList.subList(0, 5);
        }

        List<Artist> artistList = discogsArtistList.stream()
                .map(discogsArtist -> new Artist().builder()
                            .artistName(discogsArtist.getArtistName())
                            .artistInfo(new ArtistInfo().builder()
                                    .artistImageUrl(discogsArtist.getArtistImageUrl())
                                    .discogsArtistId(discogsArtist.getDiscogsArtistId())
                                    .discogsArtistUrl(discogsArtist.getDiscogsArtistUrl()).build())
                            .build())
                .collect(Collectors.toList());

        return artistList;
    }
}

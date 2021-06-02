package com.hackner.musicompass.helper;

import com.hackner.musicompass.controller.model.RecommendationTagsDto;
import com.hackner.musicompass.discogsapi.model.*;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.ArtistInfo;
import com.hackner.musicompass.model.ArtistRelease;
import com.hackner.musicompass.model.RecommendationTags;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TestData {

    public static List<ArtistRelease> createArtistReleaseList(){

        ArtistRelease artistRelease1 = ArtistRelease.builder()
                .fullTitle("someTitle")
                .discogsMasterReleaseId("111")
                .format("Album")
                .coverImageUrl("someOtherCoverImageUrl")
                .releaseYear(1999)
                .discogsWant(50000)
                .discogsHave(100000)
                .globalRating(5.0)
                .build();

        List<ArtistRelease> results = List.of(artistRelease1);

        return results;
    }

    public static List<DiscogsMasterRelease> createDiscogsMasterReleaseList(){

        Stats stats1 = Stats.builder().numberOfWants(50000).numberOfHaves(100000).build();
        DiscogsMasterRelease discogsMasterRelease1 = DiscogsMasterRelease.builder()
                .year(1999)
                .format(Arrays.asList("Album","Compilation","CD"))
                .masterId("111")
                .fullAlbumTitle("someTitle")
                .coverImageUrl("someOtherCoverImageUrl")
                .releaseStats(stats1)
                .build();
        Stats stats2 = Stats.builder().numberOfWants(1082).numberOfHaves(108).build();
        DiscogsMasterRelease discogsMasterRelease2 = DiscogsMasterRelease.builder()
                .year(1999)
                .format(Arrays.asList("Album","LP"))
                .masterId("112")
                .fullAlbumTitle("anotherTitle")
                .coverImageUrl("someOtherCoverImageUrl")
                .releaseStats(stats2)
                .build();
        Stats stats3 = Stats.builder().numberOfWants(110).numberOfHaves(176).build();
        DiscogsMasterRelease discogsMasterRelease3 = DiscogsMasterRelease.builder()
                .year(1999)
                .format(Arrays.asList("Single","CD"))
                .masterId("113")
                .fullAlbumTitle("differentTitle")
                .coverImageUrl("someOtherCoverImageUrl")
                .releaseStats(stats3)
                .build();
        Stats stats4 = Stats.builder().numberOfWants(110).numberOfHaves(44).build();
        DiscogsMasterRelease discogsMasterRelease4 = DiscogsMasterRelease.builder()
                .year(1999)
                .format(Arrays.asList("EP","12\""))
                .masterId("114")
                .fullAlbumTitle("oneMoreTitle")
                .coverImageUrl("someOtherCoverImageUrl")
                .releaseStats(stats4)
                .build();

        List<DiscogsMasterRelease> results = List.of(discogsMasterRelease1,discogsMasterRelease2,discogsMasterRelease3,discogsMasterRelease4);

        return results;
    }

    public static DiscogsArtistSearchResults createDiscogsArtistsSearchResult(int listlength){

        DiscogsArtist discogsArtist1 = DiscogsArtist.builder()
                .discogsArtistId("111")
                .artistName("Hans Hammer")
                .artistImageUrl("HammerImageUrl")
                .discogsArtistUrl("HammerUrl").build();
        DiscogsArtist discogsArtist2 = DiscogsArtist.builder()
                .discogsArtistId("222")
                .artistName("Hans Sichel")
                .artistImageUrl("SichelImageUrl")
                .discogsArtistUrl("SichelUrl").build();
        DiscogsArtist discogsArtist3 = DiscogsArtist.builder()
                .discogsArtistId("333")
                .artistName("Hans Bohrer")
                .artistImageUrl("BohrerImageUrl")
                .discogsArtistUrl("BohrerUrl").build();
        DiscogsArtist discogsArtist4 = DiscogsArtist.builder()
                .discogsArtistId("444")
                .artistName("Hans Meisel")
                .artistImageUrl("MeiselImageUrl")
                .discogsArtistUrl("MeiselUrl").build();
        DiscogsArtist discogsArtist5 = DiscogsArtist.builder()
                .discogsArtistId("555")
                .artistName("Hans Nagel")
                .artistImageUrl("NagelImageUrl")
                .discogsArtistUrl("NagelUrl").build();
        DiscogsArtist discogsArtist6 = DiscogsArtist.builder()
                .discogsArtistId("666")
                .artistName("Hans Mutter")
                .artistImageUrl("MutterImageUrl")
                .discogsArtistUrl("MutterUrl").build();

        List<DiscogsArtist> results = List.of(discogsArtist1,discogsArtist2,discogsArtist3,discogsArtist4,discogsArtist5,discogsArtist6);

        return DiscogsArtistSearchResults.builder().results(results.subList(0,listlength)).build();
    }

    public static DiscogsArtistSearchResults createDiscogsArtistSearchResults (String artistName){

        DiscogsArtist discogsArtist = DiscogsArtist.builder()
                .discogsArtistId("111")
                .artistName(artistName)
                .artistImageUrl("HammerImageUrl")
                .discogsArtistUrl("HammerUrl").build();

        DiscogsArtistSearchResults discogsArtistSearchResults = DiscogsArtistSearchResults.builder()
                .results(List.of(discogsArtist)).build();

        return discogsArtistSearchResults;
    }

    public static Artist createArtist(String artistName, Instant saveDate){
        Artist artist = createArtist(artistName);
        artist.setSaveDate(LocalDateTime.ofInstant(saveDate, ZoneOffset.UTC));

        return artist;
    }

    public static Artist createArtist(String artistName){

        Artist artist = createArtistList(1).get(0);
        artist.setArtistName(artistName);

        return artist;
    }

    public static List<Artist> createArtistList(int listlength){

        List<String> gender1 = List.of("Male");
        List<String> genres1 = List.of("Jazz", "Rock");
        List<String> roles1 = List.of("Singer");
        LocalDateTime date1 = LocalDateTime.of(2021, 4, 16, 14, 37);

        ArtistInfo artistInfo1 = new ArtistInfo().builder()
                .artistImageUrl("HammerImageUrl")
                .discogsArtistId("111")
                .discogsArtistUrl("HammerUrl")
                .build();
        ArtistRelease artistRelease1 = ArtistRelease.builder()
                .fullTitle("someTitle")
                .discogsMasterReleaseId("111")
                .format("Album")
                .coverImageUrl("coverImageUrl")
                .releaseYear(1999)
                .discogsWant(32768)
                .discogsHave(65536)
                .globalRating(4.595)
                .build();
        ArtistRelease artistRelease2 = ArtistRelease.builder()
                .fullTitle("someOtherTitle")
                .discogsMasterReleaseId("222")
                .format("Single/EP")
                .coverImageUrl("someOtherCoverImageUrl")
                .releaseYear(1999)
                .discogsWant(32768)
                .discogsHave(65536)
                .globalRating(4.595)
                .build();
        List<ArtistRelease> artistAlbums = List.of(artistRelease1);
        List<ArtistRelease> artistSingles = List.of(artistRelease2);
        /*RecommendationTags recommendationTags1 = RecommendationTags.builder()
                .recommended(true)
                .genres(genres1)
                .roles(roles1)
                .gender(gender1)
                .changeDate(date1).build();*/
        RecommendationTags recommendationTags1 = RecommendationTags.builder()
                .recommended(false)
                .genres(Collections.emptyList())
                .roles(Collections.emptyList())
                .gender(Collections.emptyList())
                .changeDate(null)
                .build();
        Artist artist1 = Artist.builder()
                .artistName("Hans Hammer")
                .artistInfo(artistInfo1)
                .artistAlbums(artistAlbums)
                .artistSingles(artistSingles)
                .recommendationTags(recommendationTags1)
                .build();

        List<String> gender2 = List.of("Non-binary");
        List<String> genres2 = List.of("Hip Hop","African");
        List<String> roles2 = List.of("Producer","Drummer/Percussionist");
        LocalDateTime date2 = LocalDateTime.of(2021, 2, 16, 14, 37);
        RecommendationTags recommendationTags2 = RecommendationTags.builder()
                .recommended(true)
                .genres(genres2)
                .roles(roles2)
                .gender(gender2)
                .changeDate(date2).build();
        ArtistInfo artistInfo2 = new ArtistInfo().builder()
                .artistImageUrl("SichelImageUrl")
                .discogsArtistId("222")
                .discogsArtistUrl("SichelUrl")
                .build();
        Artist artist2 = Artist.builder()
                .artistName("Hans Sichel")
                .artistInfo(artistInfo2)
                .recommendationTags(recommendationTags2)
                .build();

        List<String> gender3 = List.of("Female");
        List<String> genres3 = List.of("Latin");
        List<String> roles3 = List.of("Trumpeter");
        LocalDateTime date3 = LocalDateTime.of(2021, 3, 16, 14, 37);
        RecommendationTags recommendationTags3 = RecommendationTags.builder()
                .recommended(true)
                .genres(genres3)
                .roles(roles3)
                .gender(gender3)
                .changeDate(date3).build();
        ArtistInfo artistInfo3 = new ArtistInfo().builder()
                .artistImageUrl("BohrerImageUrl")
                .discogsArtistId("333")
                .discogsArtistUrl("BohrerUrl")
                .build();
        Artist artist3 = Artist.builder()
                .artistName("Hans Bohrer")
                .artistInfo(artistInfo3)
                .recommendationTags(recommendationTags3)
                .build();

        List<String> gender4 = List.of("Female");
        List<String> genres4 = List.of("Rock");
        List<String> roles4 = List.of("Producer");
        LocalDateTime date4 = LocalDateTime.of(2021, 5, 16, 14, 37);
        RecommendationTags recommendationTags4 = RecommendationTags.builder()
                .recommended(true)
                .genres(genres4)
                .roles(roles4)
                .gender(gender4)
                .changeDate(date4).build();
        ArtistInfo artistInfo4 = new ArtistInfo().builder()
                .artistImageUrl("MeiselImageUrl")
                .discogsArtistId("444")
                .discogsArtistUrl("MeiselUrl")
                .build();
        Artist artist4 = Artist.builder()
                .artistName("Hans Meisel")
                .artistInfo(artistInfo4)
                .recommendationTags(recommendationTags4)
                .build();

        List<String> gender5 = List.of("Male");
        List<String> genres5 = List.of("Folk");
        List<String> roles5 = List.of("Singer","Guitarist");
        LocalDateTime date5 = LocalDateTime.of(2021, 1, 16, 14, 37);
        RecommendationTags recommendationTags5 = RecommendationTags.builder()
                .recommended(true)
                .genres(genres5)
                .roles(roles5)
                .gender(gender5)
                .changeDate(date5).build();
        ArtistInfo artistInfo5 = new ArtistInfo().builder()
                .artistImageUrl("NagelImageUrl")
                .discogsArtistId("555")
                .discogsArtistUrl("NagelUrl")
                .build();
        Artist artist5 = Artist.builder()
                .artistName("Hans Nagel")
                .artistInfo(artistInfo5)
                .recommendationTags(recommendationTags5)
                .build();

        List<Artist> results = Arrays.asList(artist1,artist2,artist3,artist4,artist5);

        return results.subList(0,listlength);
    }

    public static RecommendationTagsDto createRecommendationTagsDto (String artistName) {

        List<String> genresAfter = List.of("Genre1", "Genre5");
        List<String> rolesAfter = List.of("Role0","Role2");
        List<String> genderAfter = List.of("differentGender");

        RecommendationTagsDto recommendationTagsDto = RecommendationTagsDto.builder()
                .artistName(artistName)
                .genres(genresAfter)
                .roles(rolesAfter)
                .gender(genderAfter).build();

        return recommendationTagsDto;
    }

    public static Artist createRecommendedArtist (String artistName, String state){
        Instant instant = Instant.ofEpochSecond(Instant.now().getEpochSecond());
        return createRecommendedArtist(artistName, state, instant);
    }

    public static Artist createRecommendedArtist (String artistName, String state, Instant instant) {

        List<String> genresBefore = List.of("Genre1", "Genre2");
        List<String> rolesBefore = List.of("Role1","Role2");
        List<String> genderBefore = List.of("Gender");

        RecommendationTagsDto recommendationTagsDto = createRecommendationTagsDto(artistName);

        List<String> genresAfter = recommendationTagsDto.getGenres();
        List<String> rolesAfter = recommendationTagsDto.getRoles();
        List<String> genderAfter = recommendationTagsDto.getGender();

        RecommendationTags recommendationTags = RecommendationTags.builder()
                .build();

        switch(state) {
            case "before":
                recommendationTags = RecommendationTags.builder()
                        .recommended(true)
                        .genres(genresBefore)
                        .roles(rolesBefore)
                        .changeDate(LocalDateTime.ofInstant(instant, ZoneOffset.UTC))
                        .gender(genderBefore).build();
                break;
            case "after":
                recommendationTags = RecommendationTags.builder()
                        .recommended(true)
                        .genres(genresAfter)
                        .roles(rolesAfter)
                        .changeDate(LocalDateTime.ofInstant(instant, ZoneOffset.UTC))
                        .gender(genderAfter).build();
                break;
            case "none":
                break;
        }

        Artist artist = Artist.builder()
                .artistName(artistName)
                .recommendationTags(recommendationTags).build();

        return artist;
    }

    public static DiscogsMasterReleaseSearchResults createDiscogsMasterReleaseSearchResults(){

        DiscogsMasterRelease discogsMasterRelease1 = DiscogsMasterRelease.builder()
                .fullAlbumTitle("someTitle")
                .masterId("111")
                .format(Arrays.asList("Album","CD"))
                .coverImageUrl("coverImageUrl")
                .year(1999)
                .releaseStats(Stats.builder().numberOfWants(32768).numberOfHaves(65536).build())
                .build();
        DiscogsMasterRelease discogsMasterRelease2 = DiscogsMasterRelease.builder()
                .fullAlbumTitle("someOtherTitle")
                .masterId("222")
                .format(Arrays.asList("Single","7\""))
                .coverImageUrl("someOtherCoverImageUrl")
                .year(1999)
                .releaseStats(Stats.builder().numberOfWants(32768).numberOfHaves(65536).build())
                .build();

        DiscogsPagination discogsPagination = new DiscogsPagination(2,1,1);

        DiscogsMasterReleaseSearchResults discogsMasterReleaseSearchResults = DiscogsMasterReleaseSearchResults.builder()
                .pagination(discogsPagination)
                .results(Arrays.asList(discogsMasterRelease1,discogsMasterRelease2)).build();

        return discogsMasterReleaseSearchResults;
    }

}

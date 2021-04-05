package com.hackner.musicompass.service;

import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.db.RecommendationCategoryMongoDb;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.RecommendationCategory;
import com.hackner.musicompass.model.RecommendationTags;
import com.hackner.musicompass.controller.model.RecommendationTagsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.time.Instant;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationServiceTest {

    private final ArtistMongoDb artistMongoDb = mock(ArtistMongoDb.class);
    private final RecommendationCategoryMongoDb recommendationCategoryMongoDb = mock(RecommendationCategoryMongoDb.class);
    private final TimeUtils timeUtils = mock(TimeUtils.class);
    private final RecommendationService recommendationService = new RecommendationService(artistMongoDb, recommendationCategoryMongoDb, timeUtils);

    @Test
    @DisplayName("Get recommendation category values")
    public void getRecommendationCategoryValues(){
        //GIVEN
        List<RecommendationCategory> categoryValues = Arrays.asList(
                RecommendationCategory.builder().categoryName("Genre").categoryValues(Arrays.asList("Genre1", "Genre2")).build(),
                RecommendationCategory.builder().categoryName("Role").categoryValues(Arrays.asList("Role1","Role2")).build(),
                RecommendationCategory.builder().categoryName("Gender").categoryValues(Arrays.asList("Gender")).build());

        when(recommendationCategoryMongoDb.findAll()).thenReturn(categoryValues);

        //WHEN
        List<RecommendationCategory> actual = recommendationService.getRecommendationCategoryValues();

        //THEN
        assertThat(actual, is(categoryValues));
        verify(recommendationCategoryMongoDb).findAll();
    }

    @Test
    @DisplayName("Change recommendation tags for already existing recommendation tags")
    public void changeExistingRecommendationTags(){
        //GIVEN
        String artistName = "artistName";
        List<String> genresBefore = List.of("Genre1", "Genre2");
        List<String> rolesBefore = List.of("Role1","Role2");
        List<String> genderBefore = List.of("Gender");
        List<String> genresAfter = List.of("Genre1", "Genre5");
        List<String> rolesAfter = List.of("Role0","Role2");
        List<String> genderAfter = List.of("differentGender");
        Instant before = Instant.ofEpochSecond(Instant.now().getEpochSecond());
        Instant after = Instant.ofEpochSecond(Instant.now().getEpochSecond()+5);

        RecommendationTagsDto recommendationTagsDto = RecommendationTagsDto.builder()
                .artistName(artistName)
                .genres(genresAfter)
                .roles(rolesAfter)
                .gender(genderAfter).build();

        RecommendationTags recommendationTagsBefore = RecommendationTags.builder()
                .recommended(true)
                .genres(genresBefore)
                .roles(rolesBefore)
                .changeDate(Date.from(before))
                .gender(genderBefore).build();

        RecommendationTags recommendationTagsAfter = RecommendationTags.builder()
                .recommended(true)
                .genres(genresAfter)
                .roles(rolesAfter)
                .changeDate(Date.from(after))
                .gender(genderAfter).build();

        Artist artistBefore = Artist.builder()
                .artistName(artistName)
                .recommendationTags(recommendationTagsBefore).build();

        Artist artistAfter = Artist.builder()
                .artistName(artistName)
                .recommendationTags(recommendationTagsAfter).build();

        when(timeUtils.now()).thenReturn(after);
        when(artistMongoDb.findById(artistName)).thenReturn(Optional.of(artistBefore));

        //WHEN
        RecommendationTags actual = recommendationService.changeRecommendationTags(recommendationTagsDto);

        //THEN
        verify(artistMongoDb).save(artistAfter);
        assertTrue(actual.getChangeDate().after(Date.from(before)));
        assertThat(actual, equalTo(recommendationTagsAfter));
    }

    @Test
    @DisplayName("Set recommendation tags for empty recommendation tags")
    public void setRecommendationTags(){
        //GIVEN
        String artistName = "artistName";
        List<String> genresAfter = List.of("Genre1", "Genre5");
        List<String> rolesAfter = List.of("Role0","Role2");
        List<String> genderAfter = List.of("differentGender");
        Instant after = Instant.ofEpochSecond(Instant.now().getEpochSecond());

        RecommendationTagsDto recommendationTagsDto = RecommendationTagsDto.builder()
                .artistName(artistName)
                .genres(genresAfter)
                .roles(rolesAfter)
                .gender(genderAfter).build();

        RecommendationTags recommendationTagsAfter = RecommendationTags.builder()
                .recommended(true)
                .genres(genresAfter)
                .roles(rolesAfter)
                .changeDate(Date.from(after))
                .gender(genderAfter).build();

        Artist artistBefore = Artist.builder()
                .artistName(artistName).build();

        Artist artistAfter = Artist.builder()
                .artistName(artistName)
                .recommendationTags(recommendationTagsAfter).build();

        when(timeUtils.now()).thenReturn(after);
        when(artistMongoDb.findById(artistName)).thenReturn(Optional.of(artistBefore));

        //WHEN
        RecommendationTags actual = recommendationService.changeRecommendationTags(recommendationTagsDto);

        //THEN
        verify(artistMongoDb).save(artistAfter);
        assertThat(actual, equalTo(recommendationTagsAfter));
    }
/*
    @Test
    @DisplayName("Get Artist Recommendation which is not available")*/
}
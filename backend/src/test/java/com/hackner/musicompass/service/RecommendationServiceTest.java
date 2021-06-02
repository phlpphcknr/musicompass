package com.hackner.musicompass.service;

import com.hackner.musicompass.controller.model.RecommendationRequestDto;
import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.db.RecommendationCategoryMongoDb;
import com.hackner.musicompass.helper.TestData;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.RecommendationCategory;
import com.hackner.musicompass.model.RecommendationTags;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
        List<RecommendationCategory> categoryValues = List.of(
                RecommendationCategory.builder().categoryName("Genre").categoryValues(List.of("Genre1", "Genre2")).build(),
                RecommendationCategory.builder().categoryName("Role").categoryValues(List.of("Role1","Role2")).build(),
                RecommendationCategory.builder().categoryName("Gender").categoryValues(List.of("Gender")).build());

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
        Instant before = Instant.ofEpochSecond(Instant.now().getEpochSecond());
        Instant after = Instant.ofEpochSecond(Instant.now().getEpochSecond()+5);

        when(timeUtils.now()).thenReturn(after);
        when(artistMongoDb.findById(artistName)).thenReturn(Optional.of(TestData.createRecommendedArtist(artistName, "before", before)));

        //WHEN
        RecommendationTags actual = recommendationService.changeRecommendationTags(TestData.createRecommendationTagsDto(artistName));

        //THEN
        verify(artistMongoDb).save(TestData.createRecommendedArtist(artistName, "after", after));
        assertTrue(actual.getChangeDate().isAfter(LocalDateTime.ofInstant(before, ZoneOffset.UTC)));
        assertThat(actual, equalTo(TestData.createRecommendedArtist(artistName, "after", after).getRecommendationTags()));
    }

    @Test
    @DisplayName("Set recommendation tags for empty recommendation tags")
    public void setRecommendationTags(){
        //GIVEN
        Instant after = Instant.ofEpochSecond(Instant.now().getEpochSecond());
        String artistName = "artistName";

        when(timeUtils.now()).thenReturn(after);
        when(artistMongoDb.findById(artistName)).thenReturn(Optional.of(TestData.createRecommendedArtist(artistName, "none")));

        //WHEN
        RecommendationTags actual = recommendationService.changeRecommendationTags(TestData.createRecommendationTagsDto(artistName));

        //THEN
        verify(artistMongoDb).save(TestData.createRecommendedArtist(artistName, "after", after));
        assertThat(actual, equalTo(TestData.createRecommendedArtist(artistName, "after", after).getRecommendationTags()));
    }

    @Test
    @DisplayName("Request Artist Recommendation with tag combination which is not available and throw error")
    public void getErrorForNotExistingTagCombination(){
        //GIVEN
        List<String> gender = List.of("Female");
        List<String> genres = List.of("Jazz", "Rock");
        List<String> roles = List.of("Singer");
        RecommendationRequestDto recommendationRequestDto = RecommendationRequestDto.builder()
                .gender(gender)
                .genres(genres)
                .roles(roles).build();
        when(artistMongoDb.findAll()).thenReturn(TestData.createArtistList(5));

        //WHEN
        assertThrows(ResponseStatusException.class, () -> recommendationService.getArtistRecommendation(recommendationRequestDto));
    }

    @Test
    @DisplayName("Request Artist Recommendation with tag combination which is available")
    public void getArtistRecommendationForExistingTagCombination(){
        //GIVEN
        List<String> gender = List.of("Non-binary");
        List<String> genres = List.of("Hip Hop");
        List<String> roles = List.of("Producer");
        RecommendationRequestDto recommendationRequestDto = RecommendationRequestDto.builder()
                .gender(gender)
                .genres(genres)
                .roles(roles).build();
        when(artistMongoDb.findAll()).thenReturn(TestData.createArtistList(5));

        //WHEN
        String actual = recommendationService.getArtistRecommendation(recommendationRequestDto);

        //THEN
        assertThat(actual, is("artist2"));
    }

    @Test
    @DisplayName("Request Artist Recommendation with tag combination which is not available")
    public void getArtistRecommendationForExistingSingleTag(){
        //GIVEN
        List<String> gender = List.of();
        List<String> genres = List.of("Latin");
        List<String> roles = List.of();
        RecommendationRequestDto recommendationRequestDto = RecommendationRequestDto.builder()
                .gender(gender)
                .genres(genres)
                .roles(roles).build();
        when(artistMongoDb.findAll()).thenReturn(TestData.createArtistList(5));

        //WHEN
        String actual = recommendationService.getArtistRecommendation(recommendationRequestDto);

        //THEN
        assertThat(actual, is("artist3"));
    }

    @Test
    @DisplayName("Get 3 latest artist recommendation with date in descending order")
    public void getLatestArtistRecommendationsList(){
        //GIVEN
        when(artistMongoDb.findAll()).thenReturn(TestData.createArtistList(5));

        //WHEN
        List<Artist> actual = recommendationService.getLatestArtistRecommendations();

        //THEN
        assertTrue(actual.get(0).getRecommendationTags().getChangeDate()
                .isAfter(actual.get(1).getRecommendationTags().getChangeDate()));
        assertTrue(actual.get(1).getRecommendationTags().getChangeDate()
                .isAfter(actual.get(2).getRecommendationTags().getChangeDate()));
        assertTrue(actual.size() <= 3);
    }
}

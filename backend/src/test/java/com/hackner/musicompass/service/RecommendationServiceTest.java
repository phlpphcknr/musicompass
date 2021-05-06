package com.hackner.musicompass.service;

import com.hackner.musicompass.controller.model.RecommendationRequestDto;
import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.db.RecommendationCategoryMongoDb;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.RecommendationCategory;
import com.hackner.musicompass.model.RecommendationTags;
import com.hackner.musicompass.controller.model.RecommendationTagsDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
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
                .changeDate(LocalDateTime.ofInstant(before, ZoneOffset.UTC))
                .gender(genderBefore).build();

        RecommendationTags recommendationTagsAfter = RecommendationTags.builder()
                .recommended(true)
                .genres(genresAfter)
                .roles(rolesAfter)
                .changeDate(LocalDateTime.ofInstant(after, ZoneOffset.UTC))
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
        assertTrue(actual.getChangeDate().isAfter(LocalDateTime.ofInstant(before, ZoneOffset.UTC)));
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
                .changeDate(LocalDateTime.ofInstant(after, ZoneOffset.UTC))
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
        when(artistMongoDb.findAll()).thenReturn(getArtistList());

        //WHEN
        assertThrows(ResponseStatusException.class, () -> recommendationService.getArtistRecommendation(recommendationRequestDto));

    }

    @Test
    @DisplayName("Request Artist Recommendation with tag combination which is not available")
    public void getArtistRecommendationForExistingTagCombination(){
        //GIVEN
        List<String> gender = List.of("Non-binary");
        List<String> genres = List.of("Hip Hop");
        List<String> roles = List.of("Producer");
        RecommendationRequestDto recommendationRequestDto = RecommendationRequestDto.builder()
                .gender(gender)
                .genres(genres)
                .roles(roles).build();
        when(artistMongoDb.findAll()).thenReturn(getArtistList());

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
        when(artistMongoDb.findAll()).thenReturn(getArtistList());

        //WHEN
        String actual = recommendationService.getArtistRecommendation(recommendationRequestDto);

        //THEN
        assertThat(actual, is("artist3"));
    }

    @Test
    @DisplayName("Get 3 latest artist recommendation with date in descending order")
    public void getLatestArtistRecommendationsList(){
        //GIVEN
        when(artistMongoDb.findAll()).thenReturn(getArtistList());

        //WHEN
        List<Artist> actual = recommendationService.getLatestArtistRecommendations();

        //THEN
        assertTrue(actual.get(0).getRecommendationTags().getChangeDate()
                .isAfter(actual.get(1).getRecommendationTags().getChangeDate()));
        assertTrue(actual.get(1).getRecommendationTags().getChangeDate()
                .isAfter(actual.get(2).getRecommendationTags().getChangeDate()));
        assertTrue(actual.size() <= 3);

    }

    public List<Artist> getArtistList(){
        List<String> gender1 = List.of("Male");
        List<String> genres1 = List.of("Jazz", "Rock");
        List<String> roles1 = List.of("Singer");
        LocalDateTime date1 = LocalDateTime.of(2021, 4, 16, 14, 37);
        RecommendationTags recommendationTags1 = RecommendationTags.builder()
                .recommended(true)
                .genres(genres1)
                .roles(roles1)
                .gender(gender1)
                .changeDate(date1).build();
        Artist artist1 = Artist.builder().artistName("artist1").recommendationTags(recommendationTags1).build();

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
        Artist artist2 = Artist.builder().artistName("artist2").recommendationTags(recommendationTags2).build();

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
        Artist artist3 = Artist.builder().artistName("artist3").recommendationTags(recommendationTags3).build();

        return List.of(artist1, artist2, artist3);
    }
}
package com.hackner.musicompass.controller;

import com.hackner.musicompass.controller.model.RecommendationRequestDto;
import com.hackner.musicompass.controller.model.RecommendationTagsDto;
import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.db.RecommendationCategoryMongoDb;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.RecommendationCategory;
import com.hackner.musicompass.model.RecommendationTags;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RecommendationControllerTest {

    @LocalServerPort
    private int port;

    private String getUrl() {return "http://localhost:" + port + "api/recommendation";}

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ArtistMongoDb artistMongoDb;

    @Autowired
    private RecommendationCategoryMongoDb recommendationCategoryMongoDb;

    @BeforeEach
    public void setup(){artistMongoDb.deleteAll();}

    @Test
    @DisplayName("Get recommendation category values")
    public void getRecommendationCategoryValues(){
        //GIVEN
        recommendationCategoryMongoDb.save(RecommendationCategory.builder().categoryName("Genre").categoryValues(List.of("Genre1", "Genre2")).build());
        recommendationCategoryMongoDb.save(RecommendationCategory.builder().categoryName("Role").categoryValues(List.of("Role1","Role2")).build());

        //WHEN
        ResponseEntity <List<RecommendationCategory>> response = testRestTemplate.exchange(getUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RecommendationCategory>>() {});

        //THEN
        assertThat(response.getBody(), hasItem(RecommendationCategory.builder()
                .categoryName("Genre")
                .categoryValues(List.of("Genre1", "Genre2")).build()));
    }

    @Test
    @DisplayName("Change recommendation tags for already existing recommendation tags")
    public void changeExistingRecommendationTags(){
        //GIVEN
        String artistName ="artistName";
        List<String> genresBefore = List.of("Genre1", "Genre2");
        List<String> rolesBefore = List.of("Role1","Role2");
        List<String> genderBefore = List.of("Gender");
        List<String> genresAfter = List.of("Genre1", "Genre5");
        List<String> rolesAfter = List.of("Role0","Role2");
        List<String> genderAfter = List.of("differentGender");
        Instant before = Instant.ofEpochSecond(Instant.now().getEpochSecond());

        RecommendationTagsDto recommendationTagsDto = RecommendationTagsDto.builder()
                .artistName(artistName)
                .genres(genresAfter)
                .roles(rolesAfter)
                .gender(genderAfter).build();

        RecommendationTags recommendationTagsBefore = RecommendationTags.builder()
                .recommended(true)
                .genres(genresBefore)
                .roles(rolesBefore)
                .changeDate(LocalDateTime.from(before))
                .gender(genderBefore).build();

        RecommendationTags recommendationTagsAfter = RecommendationTags.builder()
                .recommended(true)
                .genres(genresAfter)
                .roles(rolesAfter)
                .gender(genderAfter).build();

        artistMongoDb.save(Artist.builder()
                .artistName(artistName)
                .recommendationTags(recommendationTagsBefore).build());

        //WHEN
        HttpEntity<RecommendationTagsDto> entity = new HttpEntity<>(recommendationTagsDto);
        ResponseEntity <RecommendationTags> response = testRestTemplate.exchange(getUrl(),
                HttpMethod.POST,
                entity,
                RecommendationTags.class);

        //THEN
        assertThat(response.getBody(), equalTo(recommendationTagsAfter));
        assertTrue(response.getBody().getChangeDate().isAfter(LocalDateTime.from(before)));
    }

    @Test
    @DisplayName("Get Artist Recommendation for existing tag combination")
    public void getArtistRecommendationForExistingTagCombination(){
        //GIVEN
        List<String> gender = List.of("Non-binary");
        List<String> genres = List.of("Hip Hop");
        List<String> roles = List.of("Producer");
        RecommendationRequestDto recommendationRequestDto = RecommendationRequestDto.builder()
                .gender(gender)
                .genres(genres)
                .roles(roles).build();
        artistMongoDb.saveAll(getArtistList());

        //WHEN
        HttpEntity<RecommendationRequestDto> entity = new HttpEntity<>(recommendationRequestDto);
        ResponseEntity <String> response = testRestTemplate.exchange(getUrl() + "/get",
                HttpMethod.POST,
                entity,
                String.class);

        //THEN
        assertThat(response.getBody(), equalTo("artist2"));
    }

    private static List<Artist> getArtistList(){
        List<String> gender1 = List.of("Male");
        List<String> genres1 = List.of("Jazz", "Rock");
        List<String> roles1 = List.of("Singer");
        RecommendationTags recommendationTags1 = RecommendationTags.builder()
                .recommended(true)
                .genres(genres1)
                .roles(roles1)
                .gender(gender1).build();
        Artist artist1 = Artist.builder().artistName("artist1").recommendationTags(recommendationTags1).build();

        List<String> gender2 = List.of("Non-binary");
        List<String> genres2 = List.of("Hip Hop","African");
        List<String> roles2 = List.of("Producer","Drummer/Percussionist");
        RecommendationTags recommendationTags2 = RecommendationTags.builder()
                .recommended(true)
                .genres(genres2)
                .roles(roles2)
                .gender(gender2).build();
        Artist artist2 = Artist.builder().artistName("artist2").recommendationTags(recommendationTags2).build();

        List<String> gender3 = List.of("Female");
        List<String> genres3 = List.of("Latin");
        List<String> roles3 = List.of("Trumpeter");
        RecommendationTags recommendationTags3 = RecommendationTags.builder()
                .recommended(true)
                .genres(genres3)
                .roles(roles3)
                .gender(gender3).build();
        Artist artist3 = Artist.builder().artistName("artist3").recommendationTags(recommendationTags3).build();

        return List.of(artist1, artist2, artist3);
    }


}
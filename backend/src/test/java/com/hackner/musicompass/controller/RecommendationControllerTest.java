package com.hackner.musicompass.controller;

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
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

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
        String genderBefore = "Gender";
        List<String> genresAfter = List.of("Genre1", "Genre5");
        List<String> rolesAfter = List.of("Role0","Role2");
        String genderAfter = "differentGender";
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
                .changeDate(Date.from(before))
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
        assertTrue(response.getBody().getChangeDate().after(Date.from(before)));

    }


}
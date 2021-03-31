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

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
        recommendationCategoryMongoDb.save(RecommendationCategory.builder().categoryName("Genre").categoryValues(Arrays.asList("Genre1", "Genre2")).build());
        recommendationCategoryMongoDb.save(RecommendationCategory.builder().categoryName("Role").categoryValues(Arrays.asList("Role1","Role2")).build());

        //WHEN
        ResponseEntity <List<RecommendationCategory>> response = testRestTemplate.exchange(getUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RecommendationCategory>>() {});

        //THEN
        assertThat(response.getBody(), hasItem(RecommendationCategory.builder()
                .categoryName("Genre")
                .categoryValues(Arrays.asList("Genre1", "Genre2")).build()));
    }

    @Test
    @DisplayName("Change recommendation tags for already existing recommendation tags")
    public void changeExistingRecommendationTags(){
        //GIVEN
        String artistName ="artistName";
        List<String> genres = Arrays.asList("Genre1","Genre2");
        List<String> roles = Arrays.asList("Role1","Role2");
        RecommendationTagsDto recommendationTagsDto = RecommendationTagsDto.builder()
                .artistName(artistName)
                .genres(genres)
                .roles(roles)
                .gender("Gender").build();
        artistMongoDb.save(Artist.builder().artistName(artistName).build());

        //WHEN
        HttpEntity<RecommendationTagsDto> entity = new HttpEntity<>(recommendationTagsDto);
        ResponseEntity <RecommendationTags> response = testRestTemplate.exchange(getUrl() + artistName,
                HttpMethod.POST,
                entity,
                RecommendationTags.class);

        //THEN
        assertThat(response.getBody(), equalTo());

    }


}
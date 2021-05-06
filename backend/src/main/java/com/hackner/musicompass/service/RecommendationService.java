package com.hackner.musicompass.service;

import com.hackner.musicompass.controller.model.RecommendationRequestDto;
import com.hackner.musicompass.db.ArtistMongoDb;
import com.hackner.musicompass.db.RecommendationCategoryMongoDb;
import com.hackner.musicompass.model.Artist;
import com.hackner.musicompass.model.RecommendationCategory;
import com.hackner.musicompass.model.RecommendationTags;
import com.hackner.musicompass.controller.model.RecommendationTagsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final ArtistMongoDb artistMongoDb;
    private final RecommendationCategoryMongoDb recommendationCategoryMongoDb;
    private final TimeUtils timeUtils;

    @Autowired
    public RecommendationService(ArtistMongoDb artistMongoDb, RecommendationCategoryMongoDb recommendationCategoryMongoDb, TimeUtils timeUtils) {
        this.artistMongoDb = artistMongoDb;
        this.recommendationCategoryMongoDb = recommendationCategoryMongoDb;
        this.timeUtils = timeUtils;
    }


    public RecommendationTags changeRecommendationTags (RecommendationTagsDto recommendationTagsDto){

        Artist artist = artistMongoDb.findById(recommendationTagsDto.getArtistName()).get();

        RecommendationTags recommendationTags = RecommendationTags.builder()
                .recommended(true)
                .genres(recommendationTagsDto.getGenres())
                .roles(recommendationTagsDto.getRoles())
                .gender(recommendationTagsDto.getGender())
                .changeDate(LocalDateTime.ofInstant(timeUtils.now(), ZoneOffset.UTC)).build();

        artist.setRecommendationTags(recommendationTags);

        artistMongoDb.save(artist);

        return recommendationTags;
    }

    public List<RecommendationCategory> getRecommendationCategoryValues(){
        return recommendationCategoryMongoDb.findAll();
    }

    public List<Artist> getLatestArtistRecommendations() {

        List<Artist> recommendedArtists = getRecommendedArtists();

        return recommendedArtists;
    }

    enum FilterCategory{
        GENRES, ROLES, GENDER
    }

    public String getArtistRecommendation(RecommendationRequestDto recommendationRequestDto) {

        List<Artist> recommendedArtists = getRecommendedArtists();

        FilterCategory genres = FilterCategory.GENRES;
        List<Artist> genreFilteredArtists = filterByRecommendationTags(recommendedArtists, recommendationRequestDto.getGenres(), genres);
        FilterCategory roles = FilterCategory.ROLES;
        List<Artist> genreRoleFilteredArtists = filterByRecommendationTags(genreFilteredArtists, recommendationRequestDto.getRoles(), roles);
        FilterCategory gender = FilterCategory.GENDER;
        List<Artist> fullyFilteredArtists = filterByRecommendationTags(genreRoleFilteredArtists, recommendationRequestDto.getGender(), gender);

        if (fullyFilteredArtists.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Artist for the specified tags not in the recommended database");
        }

        Random rand = new Random();
        return fullyFilteredArtists.get(rand.nextInt(fullyFilteredArtists.size())).getArtistName();
    }

    public List<Artist> getRecommendedArtists(){

        List<Artist> artists = artistMongoDb.findAll();

        List<Artist> recommendedArtists = artists.stream()
                .filter(artist -> artist.getRecommendationTags().getRecommended() == true)
                .collect(Collectors.toList());

        return recommendedArtists;
    }

    public List<Artist> filterByRecommendationTags(List<Artist> artists, List<String> filterCriteria, FilterCategory filterCategory) {

        if (filterCriteria.isEmpty()) {
            return artists;
        }

        if (filterCategory == FilterCategory.GENRES) {
            List<Artist> criteriaFilteredList = artists.stream()
                    .filter(artist ->
                            artist.getRecommendationTags().getGenres().containsAll(filterCriteria))
                    .collect(Collectors.toList());
            return criteriaFilteredList;
        }

        if (filterCategory == FilterCategory.ROLES) {
            List<Artist> criteriaFilteredList = artists.stream()
                    .filter(artist ->
                            artist.getRecommendationTags().getRoles().containsAll(filterCriteria))
                    .collect(Collectors.toList());
            return criteriaFilteredList;
        }

        if (filterCategory == FilterCategory.GENDER) {
            List<Artist> criteriaFilteredList = artists.stream()
                    .filter(artist ->
                            artist.getRecommendationTags().getGender().containsAll(filterCriteria))
                    .collect(Collectors.toList());
            return criteriaFilteredList;
        }

        return artists;
    }
}

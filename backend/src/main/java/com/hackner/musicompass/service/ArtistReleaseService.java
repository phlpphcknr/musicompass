package com.hackner.musicompass.service;

import com.hackner.musicompass.discogsapi.model.DiscogsMasterRelease;
import com.hackner.musicompass.model.ArtistRelease;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

@Service
public class ArtistReleaseService {

    public List<ArtistRelease> getSortedReleaseList(String format, List<DiscogsMasterRelease> discogsMasterReleaseList){

        List<DiscogsMasterRelease> formatFilteredList = filterDiscogsMasterReleaseListForFormat(format, discogsMasterReleaseList);
        List<ArtistRelease> convertedFormattedList = formatFilteredList.stream()
                .map(discogsMasterRelease -> convertDiscogsMasterReleaseToArtistRelease(format, discogsMasterRelease))
                .collect(Collectors.toList());

        List<ArtistRelease> arrayList = new ArrayList<ArtistRelease>(convertedFormattedList);
        Collections.sort(arrayList);
        List<ArtistRelease> sortedList = arrayList;

        return sortedList;
    }

    public ArtistRelease convertDiscogsMasterReleaseToArtistRelease (String format, DiscogsMasterRelease discogsMasterRelease){
        return new ArtistRelease().builder()
                .fullTitle(discogsMasterRelease.getFullAlbumTitle())
                .discogsMasterReleaseId(discogsMasterRelease.getMasterId())
                .format(format)
                .coverImageUrl(discogsMasterRelease.getCoverImageUrl())
                .releaseYear(discogsMasterRelease.getYear())
                .discogsWant(discogsMasterRelease.getReleaseStats().getNumberOfWants())
                .discogsHave(discogsMasterRelease.getReleaseStats().getNumberOfHaves())
                .globalRating(calculateGlobalRating(
                        discogsMasterRelease.getReleaseStats().getNumberOfHaves(),
                        discogsMasterRelease.getReleaseStats().getNumberOfWants()))
                .build();
    }

    public List<DiscogsMasterRelease> filterDiscogsMasterReleaseListForFormat(String format, List<DiscogsMasterRelease> discogsMasterReleaseList) {
        List<String> discogsFormat = convertToDiscogsFormat(format);
        return discogsMasterReleaseList.stream()
                .filter(discogsMasterRelease -> discogsMasterRelease.getFormat().stream()
                        .anyMatch(discogsFormat::contains))
                .collect(Collectors.toList());
    }

    public List<String> convertToDiscogsFormat (String format){
        if(format.equals("Album")){
            return Arrays.asList("Album", "LP", "Mini-Album");
        }
        if(format.equals("Single/EP")){
            return Arrays.asList("7\"","10\"","12\"", "EP", "Single", "Maxi-Single");
        }
        return null;
    }

    public double calculateGlobalRating ( int have, int want){
        double doubleHave = have;
        double doubleWant = want;
        double exp = 0.125;
        if (doubleWant/doubleHave < 1){
            double fraction = doubleWant/doubleHave;
        }else{
            double fraction = pow(doubleWant/doubleHave, 0.5);
        }
        return (doubleWant/doubleHave) * pow(doubleHave,exp);
    }
}

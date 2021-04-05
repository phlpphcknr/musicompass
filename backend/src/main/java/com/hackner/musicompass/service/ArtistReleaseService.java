package com.hackner.musicompass.service;

import com.hackner.musicompass.discogsapi.model.DiscogsMasterRelease;
import com.hackner.musicompass.model.ArtistRelease;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
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
        Collections.sort(arrayList, Collections.reverseOrder());
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
        List<DiscogsMasterRelease> filteredList = formatFilter(format, discogsMasterReleaseList);
        return removeDuplicates(filteredList);
    }

    public List<DiscogsMasterRelease> formatFilter(String format, List<DiscogsMasterRelease> discogsMasterReleaseList){
        if(format == "Album"){
            return  discogsMasterReleaseList.stream()
                    .filter(discogsMasterRelease -> discogsMasterRelease.getFormat().contains(format))
                    .collect(Collectors.toList());
        }

        if(format == "Single/EP") {
            return discogsMasterReleaseList.stream()
                    .filter(discogsMasterRelease -> !discogsMasterRelease.getFormat().contains("Album")
                            && !discogsMasterRelease.getFormat().contains("Compilation"))
                    .collect(Collectors.toList());
        }
        return discogsMasterReleaseList;
    }

    public List<DiscogsMasterRelease> removeDuplicates (List<DiscogsMasterRelease> duplicateList) {
        Map<String, DiscogsMasterRelease> map = duplicateList.stream()
                .collect(Collectors.toMap(DiscogsMasterRelease::getMasterId, DiscogsMasterRelease));
        List<DiscogsMasterRelease> result = new ArrayList<DiscogsMasterRelease>(map.values());
        return result;
    }

    public double calculateGlobalRating ( int have, int want){
        if(have == 0 || want == 0){
            double nullRating = 0.000;
            return nullRating;
        };
        double doubleHave = have;
        double doubleWant = want;
        double exp = 0.2;
        double fraction = doubleWant/doubleHave;
        if (fraction > 1){
            fraction = pow(fraction, 0.4);
        }
        double value = fraction * pow(doubleHave,exp);
        return round(value, 3);
    }

    public static double round(double value, int places){
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

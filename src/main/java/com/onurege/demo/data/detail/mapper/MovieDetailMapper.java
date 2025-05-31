package com.onurege.demo.data.detail.mapper;

import com.onurege.demo.data.detail.dto.CastDto;
import com.onurege.demo.data.detail.dto.MovieDetailDto;
import com.onurege.demo.data.detail.model.Cast;
import com.onurege.demo.data.detail.model.MovieDetail;
import com.onurege.demo.data.detail.model.Review;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MovieDetailMapper {

    public MovieDetail map(MovieDetailDto dto) {
        MovieDetail movie = new MovieDetail();

        movie.setBackdropPath(formatEmptyValue(dto.getBackdrop_path(),""));

        if (dto.getGenres() != null) {
            List<String> genres = dto.getGenres().stream()
                    .map(g -> formatEmptyValue(g != null ? g.getName() : null,""))
                    .toList();
            movie.setGenreIds(genres);
        } else {
            movie.setGenreIds(List.of());
        }

        movie.setId(dto.getId() != null ? dto.getId() : 0);
        movie.setOriginalLanguage(formatEmptyValue(dto.getOriginal_language(), "language"));
        movie.setOriginalTitle(formatEmptyValue(dto.getOriginal_title(), "title"));
        movie.setOverview(formatEmptyValue(dto.getOverview(), "overview"));
        movie.setPopularity(dto.getPopularity() != null ? dto.getPopularity() : 0.0);
        movie.setPosterPath(formatEmptyValue(dto.getPoster_path(),""));
        movie.setReleaseDate(formatEmptyValue(dto.getRelease_date(), "date"));
        movie.setTitle(formatEmptyValue(dto.getTitle(), "title"));
        movie.setVoteAverage(dto.getVote_average() != null ? dto.getVote_average() : 0.0);
        movie.setVoteCount(dto.getVote_count() != null ? dto.getVote_count() : 0);
        movie.setVideo(dto.getVideo() != null ? dto.getVideo() : false);

        // Cast
        if (dto.getCredits() != null) {
            movie.setCast(formatCast(dto.getCredits().getCast()));
        } else {
            movie.setCast(List.of());
        }

        // Languages
        if (dto.getSpoken_languages() != null) {
            List<String> langs = dto.getSpoken_languages().stream()
                    .map(l -> formatEmptyValue(l != null ? l.getEnglishName() : null,""))
                    .toList();
            movie.setLanguage(langs);
        } else {
            movie.setLanguage(List.of());
        }

        // Countries
        if (dto.getProduction_countries() != null) {
            List<String> countries = dto.getProduction_countries().stream()
                    .map(c -> formatEmptyValue(c != null ? c.getName() : null,""))
                    .toList();
            movie.setProductionCountry(countries);
        } else {
            movie.setProductionCountry(List.of());
        }

        // Reviews
        if (dto.getReviews() != null && dto.getReviews().getResults() != null) {
            List<Review> reviews = dto.getReviews().getResults().stream()
                    .map(r -> {
                        Review review = new Review();
                        review.setAuthor(formatEmptyValue(r.getAuthor(),""));
                        review.setContent(formatEmptyValue(r.getContent(),""));
                        review.setCreatedAt(formatTimeStamp(r.getCreated_at() != null ? r.getCreated_at() : "0"));
                        review.setId(formatEmptyValue(r.getId(),""));
                        review.setRating((r.getAuthor_details() != null && r.getAuthor_details().getRating() != null)
                                ? r.getAuthor_details().getRating()
                                : 0.0);
                        return review;
                    })
                    .toList();
            movie.setReviews(reviews);
        } else {
            movie.setReviews(List.of());
        }

        movie.setRunTime(convertMinutesToHours(dto.getRuntime() != null ? dto.getRuntime() : 0));

        return movie;
    }

    private String formatTimeStamp(String time) {
        return formatTimeStamp("dd.MM.yy", time);
    }

    private String formatTimeStamp(String pattern, String time) {
        try {
            SimpleDateFormat inputDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            inputDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            SimpleDateFormat outputDateFormatter = new SimpleDateFormat(pattern, Locale.getDefault());

            Date date = inputDateFormatter.parse(time);
            return (date != null) ? outputDateFormatter.format(date) : time;
        } catch (Exception e) {
            return time; // Fallback to original if parsing fails
        }
    }

    private String convertMinutesToHours(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        return hours + "h:" + remainingMinutes + "m";
    }

    private List<Cast> formatCast(List<CastDto> castDtoList) {
        if (castDtoList == null) {
            return List.of();
        }

        List<Cast> result = new ArrayList<>();

        for (CastDto dto : castDtoList) {
            if (dto == null) continue;

            String genderRole = (dto.getGender() != null && dto.getGender() == 2) ? "Actor" : "Actress";

            if (dto.getProfile_path() == null){
                dto.setProfile_path("/kOvh6paITOTaE7H1NnuTB01DiVV.jpg");
            }

            Cast cast = new Cast(
                    dto.getId() != null ? dto.getId() : 0,
                    formatEmptyValue(dto.getName(),""),
                    genderRole,
                    formatEmptyValue(dto.getCharacter(),""),
                    dto.getProfile_path()
            );

            result.add(cast);
        }

        return result;
    }

    private String formatEmptyValue(String value, String defaultLabel) {
        if (value == null || value.trim().isEmpty()) {
            return "Unknown " + defaultLabel;
        }
        return value;
    }
}

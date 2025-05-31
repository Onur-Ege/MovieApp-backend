package com.onurege.demo.Service;

import com.onurege.demo.data.detail.dto.MovieDetailDto;
import com.onurege.demo.data.detail.mapper.MovieDetailMapper;
import com.onurege.demo.data.detail.model.MovieDetail;
import com.onurege.demo.data.movie.model.MovieDto;
import com.onurege.demo.utils.GenreConstants;
import com.onurege.demo.utils.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TmdbService {

    private final RestTemplate restTemplate;
    private final MovieDetailMapper mapper;

    private static final String API_KEY = K.API_KEY;  // replace this
    private static final String BASE_URL = K.BASE_URL;

    @Autowired
    public TmdbService(RestTemplate restTemplate, MovieDetailMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }

    public List<MovieDto> getPopularMovies() {
        String url = BASE_URL + "/trending/movie/day?api_key=" + API_KEY;
        return fetchMovieList(url);
    }

    public List<MovieDto> getDiscoverMovies() {
        String url = BASE_URL + "/discover/movie?api_key=" + API_KEY;
        return fetchMovieList(url);
    }

    public List<MovieDto> getUpcomingMovies() {
        String url = BASE_URL
                    + "/discover/movie"
                    +"?api_key=" + API_KEY
                    +"&include_adult=false"
                    +"&include_video=false"
                    +"&sort_by=popularity.desc"
                    +"&page=1"
                    +"&with_release_type=2|3"
                    +"&release_date.gte=2025-06-06";

        List<MovieDto> allMovies = fetchMovieList(url);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate cutoffDate = LocalDate.of(2025, 5, 1);

        return allMovies.stream()
                .filter(movie -> {
                    try {
                        if (movie.getReleaseDate() == null || movie.getReleaseDate().isEmpty()) return false;
                        LocalDate date = LocalDate.parse(movie.getReleaseDate(), formatter);
                        return date.isAfter(cutoffDate.minusDays(1)); // inclusive of July 1
                    } catch (Exception e) {
                        return false; // Skip if parsing fails
                    }
                })
                .collect(Collectors.toList());
    }

    public MovieDetail fetchMovieDetail(int movieId) {
        String url = String.format(
                "https://api.themoviedb.org/3/movie/%d?api_key=%s&append_to_response=credits,reviews",
                movieId, API_KEY
        );
        MovieDetailDto dto = restTemplate.getForObject(url, MovieDetailDto.class);
        return mapper.map(dto);
    }

    public String fetchImdbIdFromTmdb(Integer tmdbId) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/external_ids?api_key=%s", tmdbId, API_KEY);

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return (String) response.getBody().get("imdb_id");
        } catch (Exception e) {
            return ""; // fallback
        }
    }

    public List<String> getGenresByTmdbId(Integer tmdbId) {
        String url = BASE_URL + "/movie/" + tmdbId + "?api_key=" + API_KEY;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        List<Map<String, Object>> genres = (List<Map<String, Object>>) response.getBody().get("genres");

        return genres.stream()
                .map(g -> (String) g.get("name"))
                .collect(Collectors.toList());
    }

    public List<MovieDto> searchMovies(String query) {
        String url = String.format(
                "%s/search/movie?api_key=%s&query=%s",
                BASE_URL, API_KEY, UriUtils.encodeQuery(query, "UTF-8")
        );

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.getBody().get("results");

        return results.stream()
                .sorted((m1, m2) -> {
                    int voteCount1 = ((Number) m1.getOrDefault("vote_count", 0)).intValue();
                    int voteCount2 = ((Number) m2.getOrDefault("vote_count", 0)).intValue();
                    return Integer.compare(voteCount2, voteCount1); // descending
                })
                .map(this::mapToDto)
                .toList();
    }

    public MovieDto getMovieByImdbId(String imdbId){
        String url = String.format("%s/find/%s?api_key=%s&external_source=imdb_id",BASE_URL,imdbId, API_KEY);

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> movieResults = (List<Map<String, Object>>) response.get("movie_results");

        if (movieResults == null || movieResults.isEmpty()) return null;

        Map<String,Object> movie = movieResults.get(0);
        return mapToDto(movie);
    }

    private List<MovieDto> fetchMovieList(String url) {
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

        if (results == null) return List.of();

        return results.stream()
                .map(this::mapToDto)
                .toList();
    }

    private MovieDto mapToDto(Map<String, Object> movie) {
        List<String> genreNames = ((List<?>) movie.get("genre_ids")).stream()
                .map(Object::toString)
                .map(Integer::parseInt)
                .map(GenreConstants::getGenreNameById)
                .toList();

        return new MovieDto(
                (String) movie.get("backdrop_path"),
                genreNames,
                (Integer) movie.get("id"),
                (String) movie.get("original_language"),
                (String) movie.get("original_title"),
                (String) movie.get("overview"),
                movie.get("popularity") != null ? ((Number) movie.get("popularity")).doubleValue() : null,
                (String) movie.get("poster_path"),
                (String) movie.get("release_date"),
                (String) movie.get("title"),
                movie.get("vote_average") != null ? ((Number) movie.get("vote_average")).doubleValue() : null,
                (Integer) movie.get("vote_count"),
                (Boolean) movie.get("video")
        );
    }
}
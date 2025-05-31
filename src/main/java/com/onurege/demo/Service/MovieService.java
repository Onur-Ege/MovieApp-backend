package com.onurege.demo.Service;

import com.onurege.demo.data.movie.model.MovieDto;
import com.onurege.demo.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final TmdbService tmdbService;


    @Autowired
    public MovieService(MovieRepository movieRepository, TmdbService tmdbService) {
        this.movieRepository = movieRepository;
        this.tmdbService = tmdbService;
    }

    public List<MovieDto> getFavMovies(String userId) {
        List<String> imdbIds = movieRepository.findImdbIdsOfFavMovies(userId);

        return imdbIds.stream()
                .map(tmdbService::getMovieByImdbId)
                .filter(Objects::nonNull)
                .toList();
    }

}


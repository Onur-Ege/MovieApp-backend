package com.onurege.demo.Service;

import com.onurege.demo.Repository.RecommendationRepository;
import com.onurege.demo.data.movie.model.MovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class RecommendationService {

    private final TmdbService tmdbService;
    private final RecommendationRepository repo;

    @Autowired
    public RecommendationService(TmdbService tmdbService, RecommendationRepository recommendationRepository){
        this.repo = recommendationRepository;
        this.tmdbService = tmdbService;
    }

    public List<MovieDto> getUserBasedRecommendations(String userId){
        List<String> imdbs = repo.fetchUserBasedRecommendations(userId);

        if(imdbs.isEmpty()){
            return tmdbService.getPopularMovies();

        }

        return  imdbs.stream()
                .map(tmdbService::getMovieByImdbId)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<MovieDto> getMovieBasedRecommendations(Integer tmdbId){
        String imdb = tmdbService.fetchImdbIdFromTmdb(tmdbId);
        List<String> imdbs = repo.fetchMovieBasedRecommendations(imdb);

        if(imdbs.isEmpty()){
            List<String> genres = tmdbService.getGenresByTmdbId(tmdbId);
            if (!genres.isEmpty()) {
                imdbs = repo.findMoviesByGenreNames(genres);
            }
        }

        return  imdbs.stream()
                .map(tmdbService::getMovieByImdbId)
                .filter(Objects::nonNull)
                .toList();
    }

    @Async
    public void updateUserBasedRecommendations(String userId) {
        repo.recalculateUserBasedRecommendations(userId);
    }
}

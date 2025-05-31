package com.onurege.demo.Controller;

import com.onurege.demo.Service.RecommendationService;
import com.onurege.demo.data.movie.model.MovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService){
        this.recommendationService = recommendationService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MovieDto>> getRecommendationsCF(@PathVariable("userId") String userId) {
        List<MovieDto> movies = recommendationService.getUserBasedRecommendations(userId);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/movie/{tmdbId}")
    public ResponseEntity<List<MovieDto>> getRecommendations(@PathVariable("tmdbId") Integer tmdbId) {
        List<MovieDto> movies = recommendationService.getMovieBasedRecommendations(tmdbId);
        return ResponseEntity.ok(movies);
    }
}

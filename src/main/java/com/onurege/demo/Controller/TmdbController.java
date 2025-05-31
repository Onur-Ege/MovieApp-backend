package com.onurege.demo.Controller;

import com.onurege.demo.Service.TmdbService;
import com.onurege.demo.data.movie.model.MovieDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/tmdb")
public class TmdbController {

    private final TmdbService tmdbService;

    public TmdbController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }

    @GetMapping("/popular")
    public ResponseEntity<List<MovieDto>> getPopularMovies() {
        return ResponseEntity.ok(tmdbService.getPopularMovies());
    }

    @GetMapping("/discover")
    public ResponseEntity<List<MovieDto>> getDiscoverMovies() {
        return ResponseEntity.ok(tmdbService.getDiscoverMovies());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MovieDto>> getUpcomingMovies() {
        return ResponseEntity.ok(tmdbService.getUpcomingMovies());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDto>> searchMovies(@RequestParam String query) {
        List<MovieDto> results = tmdbService.searchMovies(query);
        return ResponseEntity.ok(results);
    }
}



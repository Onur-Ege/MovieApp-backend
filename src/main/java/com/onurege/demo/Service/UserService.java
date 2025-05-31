package com.onurege.demo.Service;

import com.onurege.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TmdbService tmdbService;

    public UserService(UserRepository userRepository, TmdbService tmdbService){

        this.userRepository = userRepository;
        this.tmdbService = tmdbService;
    }

    public boolean rateMovie(String userId, Integer tmdbId, String imdbId, String title, Integer rating) {
        imdbId = tmdbService.fetchImdbIdFromTmdb(tmdbId).trim().toLowerCase();
        Optional<String> result = userRepository.createRatedRelation(userId, tmdbId, imdbId, title, rating);
        return result.isPresent();
    }

    public Integer getRating(String userId, Integer tmdbId) {
        String imdb = tmdbService.fetchImdbIdFromTmdb(tmdbId);
        return userRepository.findUserRating(userId, imdb);
    }
}

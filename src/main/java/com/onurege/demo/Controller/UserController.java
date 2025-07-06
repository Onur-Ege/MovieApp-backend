package com.onurege.demo.Controller;

import com.onurege.demo.data.FavoriteRequest;
import com.onurege.demo.Service.UserService;
import com.onurege.demo.data.FavoriteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<FavoriteResponse> markAsFavorite(@RequestBody FavoriteRequest request) {
        boolean success = userService.rateMovie(request.getUserId(), request.getTmdbId(),request.getImdbId(),request.getTitle(),request.getRating());
        if (success) {
            return ResponseEntity.ok(new FavoriteResponse("relation created", true));
        } else {
            return ResponseEntity.ok(new FavoriteResponse("❌ Movie or User not found in Neo4j", false));
        }
    }

    @GetMapping
    public ResponseEntity<Integer> getUserRating(
            @RequestParam String userId,
            @RequestParam Integer tmdbId) {

        Integer rating = userService.getRating(userId, tmdbId);
        return ResponseEntity.ok(rating);
    }
}


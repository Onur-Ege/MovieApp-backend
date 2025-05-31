package com.onurege.demo.Repository;

import com.onurege.demo.data.MovieNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecommendationRepository extends Neo4jRepository<MovieNode, String> {


    @Query("""
            MATCH (:User {userId: $userId})-[r:CF]->(m:Movie)
            WITH r,m
            ORDER BY r.score DESC
            RETURN m.id AS imdbId LIMIT 25
        """)
    List<String> fetchUserBasedRecommendations(@Param("userId") String userId);

    @Query("""
            MATCH (:Movie {id: $imdbId})-[r:RECOMMENDS|RELATED_TO|SIMILAR_TO]->(m:Movie)
            WITH r,m
            RETURN m.id AS imdbId LIMIT 25
        """)
    List<String> fetchMovieBasedRecommendations(@Param("imdbId") String imdbId);

    @Query("""
            MATCH (g:Genre)<-[:IN_GENRE]-(m:Movie)
            WHERE g.name IN $genreNames AND m.votes > 100000
            WITH m ORDER BY m.rating DESC
            RETURN m.id
            LIMIT 25
        """)
    List<String> findMoviesByGenreNames(@Param("genreNames") List<String> genreNames);
}
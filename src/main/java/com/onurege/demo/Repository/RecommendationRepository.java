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
            MATCH (:Movie {id: $imdbId})-[r:RECOMMENDS|RELATED_TO]->(m:Movie)
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

    @Query("""
        // Step 1: Collect movies rated by user
        MATCH (m1:Movie)<-[:RATED]-(u1:User {userId:$userId})
        WITH u1, collect(m1) AS ratedMovies, count(m1) AS countm
    
        // Step 2: Find similar users
        MATCH (u1)-[r1:RATED]->(m1:Movie)<-[r2:RATED]-(u2:User)
        WHERE u1 <> u2 AND abs(r1.rating - r2.rating) <= 1
        WITH u1, u2, countm, count(DISTINCT m1) AS commonCount
        WITH u1, u2, countm, toFloat(commonCount) / countm AS sim
        WHERE sim > 0.5

        // Step 3: Get movies rated by similar users but not by u1
        MATCH (u2)-[r:RATED]->(m:Movie)
        WHERE NOT (u1)-[:RATED]->(m)
        WITH u1, m, sum(r.rating) AS score
        ORDER BY score DESC
        LIMIT 20
    
        // Step 4: Delete old recommendations
        OPTIONAL MATCH (u1)-[old:CF]->()
        DELETE old
    
        // Step 5: Create new recommendations
        WITH u1, m, score
        MERGE (u1)-[:CF {score: score}]->(m)
    """)
    void recalculateUserBasedRecommendations(@Param("userId") String userId);

}
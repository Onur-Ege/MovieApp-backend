package com.onurege.demo.Repository;

import com.onurege.demo.data.MovieNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<MovieNode, String> {

    @Query("""
                MERGE (u:User {userId: $userId})
                WITH u
            
                OPTIONAL MATCH (m:Movie {id: $imdbId})
                WITH u, m
                CALL apoc.do.when(
                    m IS NULL,
                    '
                        CREATE (newMovie:Movie {id: $imdbId, title: $title, tmdbId: $tmdbId})
                        RETURN newMovie AS movie
                    ',
                    '
                        RETURN m AS movie
                    ',
                    {imdbId: $imdbId, title: $title,tmdbId: $tmdbId, m: m}
                ) YIELD value
                WITH u, value.movie AS movie
                MERGE (u)-[r:RATED]->(movie)
                SET r.rating = $rating
                RETURN 'success' AS result
            
            """)
    Optional<String> createRatedRelation(
            @Param("userId") String userId,
            @Param("tmdbId") Integer tmdbId,
            @Param("imdbId") String imdbId,
            @Param("title") String title,
            @Param("rating") Integer rating
    );

    @Query("""
                MATCH (u:User {userId: $userId})-[r:RATED]->(m:Movie {id: $imdbId})
                RETURN r.rating
            """)
    Integer findUserRating(String userId, String imdbId);
}


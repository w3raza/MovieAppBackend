package com.movie.cho.repository;

import com.movie.cho.model.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public interface MovieRepository extends Neo4jRepository<Movie, String> {

    @Query("MATCH (movie:Movie) WHERE movie.title CONTAINS $title RETURN movie")
    List<Movie> findSearchResults(@Param("title") String title);

    @Query("MATCH (m:Movie {title: $title}), (cm:CastMember {name:$name}) CREATE (cm)-[:DIRECTED]->(m)")
    void addDirectorToMovie(@Param("title") String title, @Param("name") String name);

    @Query("MATCH (m:Movie {title: $title}), (cm:CastMember {name:$name}) CREATE (cm)-[:ACTED_IN]->(m)")
    void addActorToMovie(@Param("title") String title, @Param("name") String name);

    @Query("MATCH (m:Movie {title: $title}) SET m = $props RETURN m")
    Movie updateMovie(@Param("title") String title, @Param("props") Map<String,Object> updatedTitle);
}

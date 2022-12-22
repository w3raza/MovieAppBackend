package com.movie.cho.repository;

import com.movie.cho.model.CastMember;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public interface CastMemberRepository extends Neo4jRepository<CastMember, String> {

    @Query("MATCH (cm:CastMember) WHERE cm.name CONTAINS name RETURN cm.name")
    List<String> findSearchResults(@Param("name") String name);

    @Query("MATCH (m:Movie {title: $title})<-[:DIRECTED]-(director)\n" +
            "RETURN director LIMIT 25")
    List<CastMember> findAllDirectorsByTitle(String title);

    @Query("MATCH (m:Movie {title: $title})<-[:ACTED_IN]-(actor)\n" +
            "RETURN actor LIMIT 25")
    List<CastMember> findAllActorsByTitle(String title);

    @Query("MATCH (cm:CastMember {name: $name}) SET cm = $props")
    void updateCastMember(@Param("name") String name, @Param("props") Map<String,Object> updatedCast);
}

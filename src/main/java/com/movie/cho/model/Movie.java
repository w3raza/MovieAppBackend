package com.movie.cho.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
@Getter
@Setter
@AllArgsConstructor
public class Movie {
    @Id
    @NonNull
    private String title;
    private String description;
    private String year;
    private String genre;
    @Relationship(type = "ACTED_IN", direction = Relationship.Direction.INCOMING)
    private Set<CastMember> actors;
    @Relationship(type = "DIRECTED", direction = Relationship.Direction.INCOMING)
    private Set<CastMember> directors;
}

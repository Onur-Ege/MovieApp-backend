package com.onurege.demo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieNode {
    @Id
    private String id;

    @Property("imdbId")
    private String imdbId;

    @Property("tmdbId")
    private Integer tmdbId;

    @Property("title")
    private String title;
}
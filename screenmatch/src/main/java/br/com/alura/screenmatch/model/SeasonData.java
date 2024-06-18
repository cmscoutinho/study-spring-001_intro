package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(@JsonAlias("Title") String title,
                          @JsonAlias("totalSeasons") Integer seasons,
                          @JsonAlias("Episodes") List<EpisodeData> episodes) {
}


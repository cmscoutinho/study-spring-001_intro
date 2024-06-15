package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record SeriesData(@JsonAlias("Title") String title,
                         @JsonAlias("totalSeasons") Integer seasons,
                         @JsonAlias("imdbRating") Double rating) {
}

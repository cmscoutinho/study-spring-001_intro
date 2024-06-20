package br.com.alura.screenmatch.model;

import java.time.LocalDate;

public class Episodes {
    private String title;
    private Integer number;
    private Integer season;
    private Double rating;
    private LocalDate releaseDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", number=" + number +
                ", season=" + season +
                ", rating=" + rating +
                ", releaseDate=" + releaseDate;
    }
}

package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumer;
import br.com.alura.screenmatch.service.DataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);
    ApiConsumer consumer = new ApiConsumer();
    DataConverter converter = new DataConverter();

    private final String BASE_URL = "https://www.omdbapi.com/";
    private final String API_KEY = "337e4e55";
    public void showMenu() {
        System.out.print("Which title are you searching?: ");
        var title = scanner.nextLine();
        var fullUrl = BASE_URL + "?t=" + title.replace(" ", "+") + "&apikey=" + API_KEY;
        var json = consumer.getData(fullUrl);

        SeriesData series = converter.getData(json, SeriesData.class);
        System.out.println(series);

//        EpisodeData episode = converter.getData(json, EpisodeData.class);

        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i < series.seasons(); i++) {
            json = consumer.getData("https://www.omdbapi.com/?t=" + title + "&season=" + i + "&apikey="+ API_KEY);
            SeasonData season = converter.getData(json, SeasonData.class);
            seasons.add(season);
        }
        seasons.forEach(System.out::println);

    }
}

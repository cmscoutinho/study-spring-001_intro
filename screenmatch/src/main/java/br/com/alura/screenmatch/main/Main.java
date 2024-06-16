package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumer;
import br.com.alura.screenmatch.service.DataConverter;

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

        System.out.println(json);

        SeriesData series = converter.getData(json, SeriesData.class);
        SeasonData season = converter.getData(json, SeasonData.class);
        EpisodeData episode = converter.getData(json, EpisodeData.class);

    }
}

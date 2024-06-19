package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumer;
import br.com.alura.screenmatch.service.DataConverter;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    Scanner scanner = new Scanner(System.in);
    ApiConsumer consumer = new ApiConsumer();
    DataConverter converter = new DataConverter();

    private final String BASE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=337e4e55";

    public void showMenu() {
        System.out.print("Which title are you searching?: ");
        var title = scanner.nextLine();
        var fullUrl = BASE_URL + title.replace(" ", "+") + API_KEY;
        var json = consumer.getData(fullUrl);

        SeriesData series = converter.getData(json, SeriesData.class);
        System.out.println(series);

//        EpisodeData episode = converter.getData(json, EpisodeData.class);

        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i <= series.seasons(); i++) {
            json = consumer.getData("https://www.omdbapi.com/?t=" + title.replace(" ", "+") + "&season=" + i + API_KEY);
            SeasonData season = converter.getData(json, SeasonData.class);
            seasons.add(season);
        }
//        seasons.forEach(System.out::println);

//        seasons.forEach(s -> s.episodes().forEach(e -> System.out.println(e.title())));

//         Stream and lambda example
//        List<String> names = Arrays.asList("Claudio", "Carol", "Cecília", "Dalva", "Brena", "Ana Carla", "Alcir", "Edna", "Camila", "Luiz");

//        names.stream()
//                .sorted()
//                .filter(n -> !n.endsWith("o"))
//                .map(n -> n.toUpperCase())
//                .limit(4)
//                .forEach(System.out::println);

        List<EpisodeData> topEpisodes = seasons.stream()
                .flatMap(t -> t.episodes().stream())
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(5)
                .collect(Collectors.toList());

        topEpisodes.forEach(System.out::println);
    }
}

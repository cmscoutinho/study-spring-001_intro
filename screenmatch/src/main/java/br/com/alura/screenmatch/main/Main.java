package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumer;
import br.com.alura.screenmatch.service.DataConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

//        List<EpisodeData> topEpisodes = seasons.stream()
//                .flatMap(t -> t.episodes().stream())
//                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
//                .limit(5)
//                .collect(Collectors.toList());
//
//        topEpisodes.forEach(System.out::println);

        List<Episode> episodes = seasons.stream()
                .flatMap(s -> s.episodes().stream()
                        .map(d -> new Episode(s.number(), d))
                ).collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.print("From which year do you want to filter?: ");
        var year = scanner.nextInt();
        scanner.nextLine();

        LocalDate searchDate = LocalDate.of(year, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodes.stream()
                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
                .forEach(e -> System.out.println(
                        "Season: " + e.getSeason() +
                                " Episode: " + e.getNumber() +
                                " Release date: " + e.getReleaseDate().format(formatter)
                ));

    }
}

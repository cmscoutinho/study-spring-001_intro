package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumer;
import br.com.alura.screenmatch.service.DataConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        seasons.forEach(System.out::println);

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
//                .flatMap(s -> s.episodes().stream())
//                .collect(Collectors.toList());
//
//        System.out.println("\nTop 10 episódios:");
//        topEpisodes.stream()
//                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Filtro N/A: " + e))
//                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
//                .peek(e -> System.out.println("Ordenação: " + e))
//                .limit(10)
//                .peek(e -> System.out.println("Limitação em 10: " + e))
//                .map(e -> e.title().toUpperCase())
//                .peek(e -> System.out.println("Map de uppercase: " + e))
//                .forEach(System.out::println);
//

        List<Episode> episodes = seasons.stream()
                .flatMap(s -> s.episodes().stream()
                        .map(d -> new Episode(s.number(), d))
                ).collect(Collectors.toList());

//        System.out.print("Which episode are you looking for?: ");
//        var episodeSnippet = scanner.nextLine();

//        Optional<Episode> searchedEpisode = episodes.stream()
//                .filter(e -> e.getTitle().toUpperCase().contains(episodeSnippet.toUpperCase()))
//                .findFirst();
//
//        if (searchedEpisode.isPresent()) {
//            System.out.println("Episode found!");
//            Episode ep = searchedEpisode.get();
//            System.out.println("S" + ep.getSeason() + " E" + ep.getNumber());
//        } else {
//            System.out.println("Episode not found!");
//        }

        Map<Integer, Double> ratingPerSeason = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)));

        System.out.println(ratingPerSeason);

        DoubleSummaryStatistics est = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Total de episódios: " + est.getCount());

//        Optional<Episode> topRatedEps = episodes.stream()
//                .filter(e -> e.getRating() > 9.5)
//                .peek(System.out::println)
//                .collect(Collectors.toSet()).stream()
//                .findFirst();
//
//        System.out.println(topRatedEps);
//        episodes.forEach(System.out::println);

//        System.out.print("From which year do you want to filter?: ");
//        var year = scanner.nextInt();
//        scanner.nextLine();
//
//        LocalDate searchDate = LocalDate.of(year, 1, 1);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodes.stream()
//                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
//                .forEach(e -> System.out.println(
//                        "Season: " + e.getSeason() +
//                                " Episode: " + e.getNumber() +
//                                " Release date: " + e.getReleaseDate().format(formatter)
//                ));

    }
}

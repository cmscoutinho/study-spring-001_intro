package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumer;
import br.com.alura.screenmatch.service.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello, Spring!");

		var consumer = new ApiConsumer();
//		var seriesUrl = "https://www.omdbapi.com/?t=friends&Season=1&apikey=337e4e55";
		var seriesUrl = "https://www.omdbapi.com/?t=friends&apikey=337e4e55";
		var json = consumer.getData(seriesUrl);

//		json = consumer.getData("https://coffee.alexflipnote.dev/random.json");
		DataConverter converter = new DataConverter();
		SeriesData data = converter.getData(json, SeriesData.class);
		System.out.println(json);
	}

}

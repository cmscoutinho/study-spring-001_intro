package br.com.alura.screenmatch;

import br.com.alura.screenmatch.service.ApiConsumer;
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
		var json = consumer.getData("https://www.omdbapi.com/?t=friends&Season=1&apikey=337e4e55");
		System.out.println(json);

		json = consumer.getData("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
	}

}

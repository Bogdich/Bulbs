package com.bogdevich.bulbs;

import com.bogdevich.bulbs.model.Bulb;
import com.bogdevich.bulbs.repository.IBulbRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BulbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BulbsApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(IBulbRepository repository) {
		return (args) -> {
			repository.save(new Bulb(false));
			repository.save(new Bulb(false));
			repository.save(new Bulb(false));
		};
	}
}

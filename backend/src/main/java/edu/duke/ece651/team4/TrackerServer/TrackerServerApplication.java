package edu.duke.ece651.team4.TrackerServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrackerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackerServerApplication.class, args);
	}

}

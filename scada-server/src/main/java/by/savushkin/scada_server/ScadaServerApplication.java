package by.savushkin.scada_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Активируем планировщик
public class ScadaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScadaServerApplication.class, args);
	}

}

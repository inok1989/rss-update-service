package de.kgrupp.rssupdateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RssUpdateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssUpdateServiceApplication.class, args);
    }
}

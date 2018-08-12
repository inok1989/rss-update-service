package de.kgrupp.ttrss.windowsupdateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TtRssWindowsUpdateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtRssWindowsUpdateServiceApplication.class, args);
    }
}

package org.radon.teleeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TeleEatApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeleEatApplication.class, args);
    }

}

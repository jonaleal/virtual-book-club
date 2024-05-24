package com.udea.bookclub;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BookClubApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(BookClubApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
    }
}


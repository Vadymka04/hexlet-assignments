package exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.LocalTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

// BEGIN

// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Primary
    public Daytime daytime(){
        LocalTime now = LocalTime.now();
        if (now.isAfter(LocalTime.of(6, 0)) && now.isBefore(LocalTime.of(22, 0))) {
            return new Day();
        } else {
            return new Night();
        }
    }
}

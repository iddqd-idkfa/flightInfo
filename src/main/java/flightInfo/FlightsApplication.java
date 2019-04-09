package flightInfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot app for FlightsApplication, intializing FlightsController
 */
@SpringBootApplication
public class FlightsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightsApplication.class, args);
    }
}

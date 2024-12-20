package rs.edu.raf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import rs.edu.raf.annotations.Generated;

@SpringBootApplication
@EnableScheduling
public class StockMarketApplication {
    @Generated
    public static void main(String[] args) {
        SpringApplication.run(StockMarketApplication.class,args);
    }

}
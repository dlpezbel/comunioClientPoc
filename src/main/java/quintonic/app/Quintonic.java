package quintonic.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("quintonic")
public class Quintonic {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Quintonic.class, args);
    }

}

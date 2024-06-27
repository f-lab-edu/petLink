package app.partner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"app"})
public class ModulePartnerUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModulePartnerUserApplication.class, args);
    }

}

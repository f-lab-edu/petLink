package app.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ModuleClientUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModuleClientUserApplication.class, args);
    }
}

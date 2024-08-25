package lk.ijse.weavyapiintegration;

import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeavyApiIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeavyApiIntegrationApplication.class, args);
    }

    @Bean
    OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }

}

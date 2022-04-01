package producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    @Bean
    public MessagePublisher sender() {
        return new MessagePublisher();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }
}

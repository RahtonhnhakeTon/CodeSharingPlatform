package platform;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;


@Configuration
public class CodeConfiguration {
    @Bean
    public Integer getInteger() {
        return 0;
    }
    @Bean
    public String getString(){
        return new String();
    }
    @Bean
    public LocalDateTime getDate() {
        return LocalDateTime.now();
    }
}

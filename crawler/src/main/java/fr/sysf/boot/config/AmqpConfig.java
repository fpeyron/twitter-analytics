package fr.sysf.boot.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by florent on 10/02/2016.
 */
@Configuration
public class AmqpConfig {


    @Bean
    Queue follows() {
        return new Queue("twitter.follows", true, false, false);
    }

    @Bean
    Queue followers() {
        return new Queue("twitter.followers", true, false, false);
    }

}

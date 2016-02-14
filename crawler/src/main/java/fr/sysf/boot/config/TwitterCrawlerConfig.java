package fr.sysf.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

/**
 * Created by florent on 10/02/2016.
 */
@Configuration
public class TwitterCrawlerConfig {

    @Value("${spring.social.twitter.appId}")
    private String appId;

    @Value("${spring.social.twitter.appSecret}")
    private String appSecret;

    @Value("${spring.social.twitter.accessToken}")
    private String accessToken;

    @Value("${spring.social.twitter.accessTokenSecret}")
    private String accessTokenSecret;

    @Bean
    Twitter twitter(final @Value("${spring.social.twitter.appId}") String appId,
                    final @Value("${spring.social.twitter.appSecret}") String appSecret,
                    final @Value("${spring.social.twitter.accessToken}") String accessToken,
                    final @Value("${spring.social.twitter.accessTokenSecret}") String accessTokenSecret) {
        return new TwitterTemplate(appId, appSecret, accessToken, accessTokenSecret);
    }
}

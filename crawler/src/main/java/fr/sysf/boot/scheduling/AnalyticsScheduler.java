package fr.sysf.boot.scheduling;

import fr.sysf.boot.dao.UserRepository;
import fr.sysf.boot.model.User;
import fr.sysf.boot.service.TwitterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by florent on 11/02/2016.
 */
@Component
public class AnalyticsScheduler {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final Log logger = LogFactory.getLog(AnalyticsScheduler.class);
    //private static final String PAGERANK_JOB_URL = "%s/service/mazerunner/analysis/pagerank/FOLLOWS";

    private final TwitterService twitterService;
    private final UserRepository userRepository;

    public static Boolean resetTimer = false;

    @Autowired
    public AnalyticsScheduler(TwitterService twitterService, UserRepository userRepository) {
        this.twitterService = twitterService;
        this.userRepository = userRepository;
    }

    /**
     * Every five minutes a PageRank job is scheduled with Neo4j's Mazerunner service
     */
    @Scheduled(fixedRate = 300000, initialDelay = 20000)
    public void schedulePageRank() {
        logger.info("PageRank scheduled on follows graph " + dateFormat.format(new Date()));

/*
        if (userRepository.findNextUserToCrawl() != null) {
            String jobUrl = String.format(PAGERANK_JOB_URL, neo4jServer.url());
            restTemplate.getForEntity(jobUrl, null);
        }
*/
    }

    /**
     * Every minute, an attempt to discover a new user to be imported is attempted. This only succeeds if
     * the API is not restricted by a temporary rate limit. This makes sure that only relevant users are
     * discovered over time, to keep the API crawling relevant.
     */
    @Scheduled(fixedRate = 60000)
    public void scheduleDiscoverUser() {
        if (!resetTimer) {
            // Use ranked users when possible
            User user = userRepository.findRankedUserToCrawl();

            if (user == null) {
                user = userRepository.findNextUserToCrawl();
            }

            if (user != null) {
                user = twitterService.discoverUserByProfileId(user.getProfileId());
                logger.info(String.format("Discover user %s", user.getScreenName()));
                userRepository.save(user);
            }
        } else {
            resetTimer = false;
        }

        // Update rankings
        logger.info("Updating last ranks...");
        userRepository.setLastPageRank();
        logger.info("Updating current rank...");
        userRepository.updateUserCurrentRank();
        logger.info("Current ranks updated!");
    }
}

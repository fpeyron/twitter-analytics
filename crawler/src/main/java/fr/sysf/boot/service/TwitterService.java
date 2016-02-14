package fr.sysf.boot.service;

import fr.sysf.boot.model.User;

/**
 * Created by florent on 10/02/2016.
 */
public interface TwitterService {

    User discoverUserByScreenName(String screenName);
    User discoverUserByProfileId(Long profileId);
}

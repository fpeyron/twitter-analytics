package fr.sysf.boot.dao;

import fr.sysf.boot.model.Follows;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

/**
 * Created by florent on 10/02/2016.
 */
@RepositoryRestResource(collectionResourceRel = "follows", path = "follows")
public interface FollowsRepository extends PagingAndSortingRepository<Follows, Long> {


    /**
     * Efficiently batches the creation of many FOLLOWS relationships
     * between {@link User} nodes
     *
     * @param follows a set of relationship entities containing a user "A" who follows a user "B"
     */
    @Query("FOREACH(x in {follows} | MERGE (a:User { profileId: x.userA.profileId })\n" +
            "MERGE (b:User { profileId: x.userB.profileId })\n" +
            "MERGE (a)-[:FOLLOWS]->(b))")
    void saveFollows(@Param("follows") Set<Follows> follows);
}

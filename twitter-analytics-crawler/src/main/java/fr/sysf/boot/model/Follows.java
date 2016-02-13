package fr.sysf.boot.model;


import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;


/**
 * Created by florent on 10/02/2016.
 */
@RelationshipEntity(type = "FOLLOWS")
public class Follows {

    @GraphId
    private Long relationshipId;
    @StartNode
    private User userA;
    @EndNode
    private User userB;

    public Follows() {
    }

    public Follows(User userA, User userB) {
        this.userA = userA;
        this.userB = userB;
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public User getUserA() {
        return userA;
    }

    public void setUserA(User userA) {
        this.userA = userA;
    }

    public User getUserB() {
        return userB;
    }

    public void setUserB(User userB) {
        this.userB = userB;
    }


    @Override
    public String toString() {
        return "Follows{" +
                "relationshipId=" + relationshipId +
                ", userA=" + userA +
                ", userB=" + userB +
                '}';
    }
}

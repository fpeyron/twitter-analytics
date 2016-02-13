package fr.sysf.boot.config;

import fr.sysf.boot.dao.UserRepository;
import fr.sysf.boot.model.Follows;
import fr.sysf.boot.model.User;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by florent on 10/02/2016.
 */
@EnableNeo4jRepositories(basePackageClasses = {UserRepository.class, Follows.class})
@EnableTransactionManagement
@Configuration
public class GraphConfig extends Neo4jConfiguration {

    @Value("${spring.neo4j.host:127.0.0.1}")
    private String host;

    @Value("${spring.neo4j.port:7474}")
    private String port;


    @Bean
    public Neo4jServer neo4jServer() {
        // return new RemoteServer(String.format("http://%s:%s", host, port), "admin", "admin");
        return new RemoteServer(String.format("http://%s:%s", host, port));
    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory(User.class.getPackage().getName());
    }

}

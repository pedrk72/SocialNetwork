package pedrk72.quarkusSocial.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pedrk72.quarkusSocial.domain.model.Follower;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {
}

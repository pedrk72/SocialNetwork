package pedrk72.quarkusSocial.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import pedrk72.quarkusSocial.domain.model.Follower;
import pedrk72.quarkusSocial.domain.model.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

    public boolean follows(User follower, User user){

        var parameters = Parameters.with("follower", follower)
                .and("user", user).map();

        PanacheQuery<Follower> query = find("follower = :follower and user = :user", parameters);
        Optional<Follower> result = query.firstResultOptional();

        return result.isPresent();
    }
}

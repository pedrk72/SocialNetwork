package pedrk72.quarkusSocial.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pedrk72.quarkusSocial.domain.model.User;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped //To create an instance of UserRepository class inside of application context, to use it where you want to use it
public class UserRepository implements PanacheRepository<User> {
}

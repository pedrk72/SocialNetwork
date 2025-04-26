package pedrk72.quarkusSocial.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import pedrk72.quarkusSocial.domain.model.Post;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {
}

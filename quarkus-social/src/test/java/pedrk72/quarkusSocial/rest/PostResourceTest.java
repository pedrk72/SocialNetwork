package pedrk72.quarkusSocial.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pedrk72.quarkusSocial.domain.model.Follower;
import pedrk72.quarkusSocial.domain.model.Post;
import pedrk72.quarkusSocial.domain.model.User;
import pedrk72.quarkusSocial.domain.repository.FollowerRepository;
import pedrk72.quarkusSocial.domain.repository.PostRepository;
import pedrk72.quarkusSocial.domain.repository.UserRepository;
import pedrk72.quarkusSocial.rest.dto.CreatePostRequest;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {

    @Inject
    UserRepository userRepository;
    @Inject
    FollowerRepository followerRepository;
    @Inject
    PostRepository postRepository;
    Long userId;
    Long userIdNotFollows;
    Long userIdFollows;

    @BeforeEach
    @Transactional
    public void setUp(){
        //User
        var user = new User();
        user.setName("Pedro");
        user.setAge(27);
        userRepository.persist(user);
        userId = user.getId();

        //User that not follows
        var user2 = new User();
        user.setName("Ruffles");
        user.setAge(25);
        userRepository.persist(user2);
        userIdNotFollows = user2.getId();

        //User that follows
        var user3 = new User();
        user.setName("Trombone");
        user.setAge(25);
        userRepository.persist(user3);
        userIdFollows = user3.getId();

        var follower = new Follower();
        follower.setUser(user);
        follower.setFollower(user3);
        followerRepository.persist(follower);

        var post = new Post();
        post.setUser(user);
        post.setPostText("SLB");
        postRepository.persist(post);
    }

    @Test
    @DisplayName("Should create a post for a user")
    public void createTestPost(){
        var postRequest = new CreatePostRequest();
        postRequest.setPostText("Ola Pedro");
        //postRequest.setDateTime(LocalDateTime.now());

        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParam("userId", userId)
                .when()
                    .post()
                .then()
                    .statusCode(202);
    }

    @Test
    @DisplayName("Should returo 404 when trying to make a post for an inexistent user")
    public void createTestErrorPost(){
        var postRequest = new CreatePostRequest();
        postRequest.setPostText("Some text");

        var inexistentUserId = 999;

        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParam("userId", inexistentUserId)
                .when()
                .post()
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Should return 400 when followerId header is not sent")
    public void listPostFollowerHeaderNotSendTest(){
        var notUserId = 10;

        given()
                .pathParam("userId", notUserId)
                .when()
                .get()
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Should return 404 when follower does not exist")
    public void listPostFollowerNotFoundTest(){
        given()
                .pathParam("userId", userId)
                .when()
                .get()
                .then()
                .statusCode(400)
                .body(Matchers.is("You forgot to indicate the follower id"));
    }

    @Test
    @DisplayName("Should return 400 when follower does not found")
    public void listPostFollowerNotFound(){
        var notAFollower = 10;

        given()
                .pathParam("userId", userId)
                .header("followerId", notAFollower)
                .when()
                .get()
                .then()
                .statusCode(400)
                .body(Matchers.is("The respective follower does not exist"));
    }

    @Test
    @DisplayName("Should return 403 when follower does not follow the user")
    public void listPostFollowerCantSeeThePosts(){
        given()
                .pathParam("userId", userId)
                .header("followerId", userIdNotFollows)
                .when()
                .get()
                .then()
                .statusCode(403)
                .body(Matchers.is("You can not see these posts"));
    }

    @Test
    @DisplayName("Should return all tests")
    public void listPostTest(){
        given()
                .pathParam("userId", userId)
                .header("followerId", userIdFollows)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(1));

    }
}
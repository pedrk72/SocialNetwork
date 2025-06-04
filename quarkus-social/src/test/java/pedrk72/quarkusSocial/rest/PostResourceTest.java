package pedrk72.quarkusSocial.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pedrk72.quarkusSocial.domain.model.User;
import pedrk72.quarkusSocial.domain.repository.UserRepository;
import pedrk72.quarkusSocial.rest.dto.CreatePostRequest;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {

    @Inject
    UserRepository userRepository;
    Long userId;

    @BeforeEach
    @Transactional
    public void setUp(){
        var user = new User();
        user.setName("Pedro");
        user.setAge(27);
        userRepository.persist(user);
        userId = user.getId();
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

    }

    @Test
    @DisplayName("Should return 404 when follower does not exist")
    public void listPostFollowerNotFoundTest(){

    }

    @Test
    @DisplayName("Should return 403 when follower does not follow")
    public void listPostNotAFollowerTest(){

    }

    @Test
    @DisplayName("Should return all tests")
    public void listPostTest(){

    }
}
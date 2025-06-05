package pedrk72.quarkusSocial.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pedrk72.quarkusSocial.domain.model.User;
import pedrk72.quarkusSocial.domain.repository.FollowerRepository;
import pedrk72.quarkusSocial.domain.repository.UserRepository;
import pedrk72.quarkusSocial.rest.dto.CreateFollowerRequest;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(FollowerResource.class)
class FollowerResourceTest {

    @Inject
    FollowerRepository followerRepository;
    @Inject
    UserRepository userRepository;
    Long userId;
    Long user2Id;

    @BeforeEach
    @Transactional
    public void setUp(){
        //User
        var user = new User();
        user.setName("Pedro");
        user.setAge(27);
        userRepository.persist(user);
        userId = user.getId();

        //User
        var user2 = new User();
        user2.setName("Ruffles");
        user2.setAge(25);
        userRepository.persist(user2);
        user2Id = user2.getId();
    }

    @Test
    @Order(1)
    @DisplayName("Should return 409 (conflict) when followerId is equal to user Id")
    public void sameUserAsFollowerTest(){
        CreateFollowerRequest followerRequest = new CreateFollowerRequest();
        followerRequest.setFollowerId(userId);

        given()
                .contentType(ContentType.JSON)
                .pathParam("userId", userId)
                .body(followerRequest)
                .when()
                .put()
                .then()
                .statusCode(409)
                .body(Matchers.is("You can not follow yourself"));

    }

    @Test
    @Order(2)
    @DisplayName("Should return 404 (Not Found) when user id is equal to null")
    public void nullUserFollowerTest(){
        CreateFollowerRequest followerRequest = new CreateFollowerRequest();
        followerRequest.setFollowerId(userId);

        var nullUserId = 72;

        given()
                .contentType(ContentType.JSON)
                .pathParam("userId", nullUserId)
                .body(followerRequest)
                .when()
                .put()
                .then()
                .statusCode(404);

    }

    @Test
    @Order(3)
    @DisplayName("Should follow a user")
    public void followerUserTest(){
        CreateFollowerRequest followerRequest = new CreateFollowerRequest();
        followerRequest.setFollowerId(user2Id);

        given()
                .contentType(ContentType.JSON)
                .pathParam("userId", userId)
                .body(followerRequest)
        .when()
                .put()
        .then()
                .statusCode(204);

    }

}
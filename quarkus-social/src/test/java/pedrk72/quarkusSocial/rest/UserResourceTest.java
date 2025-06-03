package pedrk72.quarkusSocial.rest;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import pedrk72.quarkusSocial.rest.dto.CreateUserRequest;

import java.net.URL;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {

    @TestHTTPResource("/users")
    URL apiURL;

    @Test
    @DisplayName("Sould create an user successfully")
    @Order(1)
    public void createUserTest(){
        var user = new CreateUserRequest();
        user.setName("Pedro");
        user.setAge(27);

        var response =
                given()
                    .contentType(ContentType.JSON)
                    .body(user)
                .when()
                    .post(apiURL)
                .then()
                    .extract()
                    .response();

        assertEquals(201, response.statusCode());
        assertNotNull(response.jsonPath().getString("id"));

    }

    @Test
    @DisplayName("Should return error when json is not valid")
    @Order(2)
    public void createUserValidationErrorTest(){
        var user = new CreateUserRequest();
        user.setName(null);
        user.setAge(null);

        var response =
                given()
                            .contentType(ContentType.JSON)
                            .body(user)
                        .when()
                            .post(apiURL)
                        .then()
                            .extract()
                            .response();

        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Should return a list with all users")
    @Order(3)
    public void listAllUsersTest(){
        var response =
                        given()
                            .contentType(ContentType.JSON)
                        .when()
                            .get(apiURL)
                        .then()
                                .statusCode(200)
                                .body("size()", Matchers.is(1));
    }
}
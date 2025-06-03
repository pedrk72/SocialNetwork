package pedrk72.quarkusSocial.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pedrk72.quarkusSocial.rest.dto.CreateUserRequest;
import pedrk72.quarkusSocial.rest.dto.ResponseError;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserResourceTest {

    @Test
    @DisplayName("Sould create an user successfully")
    public void createUserTest(){
        var user = new CreateUserRequest();
        user.setName("Pedro");
        user.setAge(27);

        var response =
                given()
                    .contentType(ContentType.JSON)
                    .body(user)
                .when()
                    .post("/users")
                .then()
                    .extract()
                    .response();

        assertEquals(201, response.statusCode());
        assertNotNull(response.jsonPath().getString("id"));

    }

    @Test
    @DisplayName("Should return error when json is not valid")
    public void createUserValidationErrorTest(){
        var user = new CreateUserRequest();
        user.setName(null);
        user.setAge(null);

        var response =
                given()
                            .contentType(ContentType.JSON)
                            .body(user)
                        .when()
                            .post("/users")
                        .then()
                            .extract()
                            .response();

        assertEquals(400, response.statusCode());
    }
}
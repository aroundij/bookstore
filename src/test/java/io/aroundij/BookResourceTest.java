package io.aroundij;

import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class BookResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/books")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .body("results", CoreMatchers.hasItems());
    }

}
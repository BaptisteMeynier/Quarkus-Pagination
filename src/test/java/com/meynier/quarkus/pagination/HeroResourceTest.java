package com.meynier.quarkus.pagination;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class HeroResourceTest {

    @Test
    public void testDefaultParam() {
        given()
                .when().get("/hero")
                .then()
                .statusCode(200)
                .body(
                        allOf(
                                containsStringIgnoringCase("\"id\":1"),
                                containsStringIgnoringCase("\"id\":2"),
                                containsStringIgnoringCase("\"id\":3"),
                                containsStringIgnoringCase("\"id\":4"),
                                containsStringIgnoringCase("\"id\":5")
                        ));
    }

    @Test
    public void testFirstPage() {
        given()
                .when().get("/hero?page=0&size=2")
                .then()
                .statusCode(200)
                .body(is("[{\"id\":1,\"age\":12,\"name\":\"Hercule\"},{\"id\":2,\"age\":21,\"name\":\"Lanfeust\"}]"));
    }

    @Test
    public void testSecondPage() {
        given()
                .when().get("/hero?page=1&size=3")
                .then()
                .statusCode(200)
                .body(is("[{\"id\":4,\"age\":123,\"name\":\"Batman\"},{\"id\":5,\"age\":21,\"name\":\"SuperMan\"},{\"id\":6,\"age\":35,\"name\":\"SpiderMan\"}]"));
    }

    @Test
    public void testPageTooLarge() {
        given()
                .when().get("/hero?page=10&size=5")
                .then()
                .statusCode(404);
    }
}
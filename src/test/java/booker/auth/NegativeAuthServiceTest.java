package booker.auth;

import booker.BaseBookerTest;
import booker.model.Credentials;
import booker.util.BookingHelper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NegativeAuthServiceTest extends BaseBookerTest {

    @BeforeClass
    public static void setup() {
        RestAssured.basePath = "/auth";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.TEXT)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void noLoginWithEmptyCreds() {
        RestAssured.given()
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void noLoginWithEmptyUserName() {
        Credentials creds = Credentials.builder()
                .password("password123")
                .build();

        RestAssured.given()
                .body(BookingHelper.toJson(creds, Credentials.class))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void noLoginWithEmptyPassword() {
        Credentials creds = Credentials.builder()
                .username("admin")
                .build();

        RestAssured.given()
                .body(BookingHelper.toJson(creds, Credentials.class))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void noLoginWithWrongUserName() {
        Credentials creds = Credentials.builder()
                .username("smth_else")
                .password("password123")
                .build();

        RestAssured.given()
                .body(BookingHelper.toJson(creds, Credentials.class))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void noLoginWithWrongPassword() {
        Credentials creds = Credentials.builder()
                .username("admin")
                .password("smth_else")
                .build();

        RestAssured.given()
                .body(BookingHelper.toJson(creds, Credentials.class))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void noLoginWithWrongCreds() {
        Credentials creds = Credentials.builder()
                .username("smth_else")
                .password("smth_else")
                .build();

        RestAssured.given()
                .body(BookingHelper.toJson(creds, Credentials.class))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

}

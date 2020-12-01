package booker.auth;

import static io.restassured.RestAssured.config;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.hasKey;

import booker.BaseBookerTest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class AuthServiceTest extends BaseBookerTest {

  private static final String BASE_PATH = "/auth";

  static {
    RestAssured.responseSpecification = new ResponseSpecBuilder()
        .expectContentType(ContentType.JSON)
        .expectStatusCode(200)
        .build();
  }

  @Test
  public void canCreateToken() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setBody("{ \"username\" : \"admin\", \"password\" : \"password123\"}")
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.TEXT)))
        .build();

    RestAssured.given(requestSpec)
        .when()
        .post(BASE_PATH)
        .then()
        .body("$", hasKey("token"));
  }

}

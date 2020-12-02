package booker.auth;

import static io.restassured.RestAssured.config;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.hasValue;

import booker.BaseBookerTest;
import booker.model.Credentials;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NegativeAuthServiceTest extends BaseBookerTest {

  private static final String BAD_CREDS = "Bad credentials";

  @BeforeClass
  private static void setup() {
    RestAssured.basePath = "/auth";
    RestAssured.responseSpecification = new ResponseSpecBuilder()
        .expectContentType(ContentType.JSON)
        .build();
  }

  @Test
  public void noLoginWithEmptyCreds() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.TEXT)))
        .build();

    RestAssured.given(requestSpec)
        .when()
        .post()
        .then()
        .body("$", hasValue(BAD_CREDS));
  }

  @Test
  public void noLoginWithEmptyUserName() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.TEXT)))
        .build();

    Credentials creds = Credentials.builder()
        .password("password123")
        .build();

    RestAssured.given(requestSpec)
        .when()
        .body(creds)
        .post()
        .then()
        .body("$", hasValue(BAD_CREDS));
  }

  @Test
  public void noLoginWithEmptyPassword() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.TEXT)))
        .build();

    Credentials creds = Credentials.builder()
        .username("admin")
        .build();

    RestAssured.given(requestSpec)
        .when()
        .body(creds)
        .post()
        .then()
        .body("$", hasValue(BAD_CREDS));
  }

  @Test
  public void noLoginWithWrongUserName() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.TEXT)))
        .build();

    Credentials creds = Credentials.builder()
        .username("smth_else")
        .password("password123")
        .build();

    RestAssured.given(requestSpec)
        .when()
        .body(creds)
        .post()
        .then()
        .body("$", hasValue(BAD_CREDS));
  }

  @Test
  public void noLoginWithWrongPassword() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.TEXT)))
        .build();

    Credentials creds = Credentials.builder()
        .username("admin")
        .password("smth_else")
        .build();

    RestAssured.given(requestSpec)
        .when()
        .body(creds)
        .post()
        .then()
        .body("$", hasValue(BAD_CREDS));
  }

  @Test
  public void noLoginWithWrongCreds() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.TEXT)))
        .build();

    Credentials creds = Credentials.builder()
        .username("smth_else")
        .password("smth_else")
        .build();

    RestAssured.given(requestSpec)
        .when()
        .body(creds)
        .post()
        .then()
        .body("$", hasValue(BAD_CREDS));
  }

}

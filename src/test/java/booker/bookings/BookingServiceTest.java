package booker.bookings;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasValue;

import booker.BaseBookerTest;
import booker.model.BookingInfo;
import booker.model.BookingInfo.CheckInOutDate;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.Test;

public class BookingServiceTest extends BaseBookerTest {

  private static final String BASE_PATH = "/booking";

  static {
    RestAssured.responseSpecification = new ResponseSpecBuilder()
        .expectStatusCode(200)
        .build();
  }

  @Test
  public void canGetAllBookings() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .build();

    given(requestSpec)
        .when()
        .get(BASE_PATH)
        .then()
        .contentType(ContentType.JSON);
  }

  @Test
  public void canGetBooking() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .build();

    String bookingIDpath =
        "/" + given(requestSpec).get(BASE_PATH).then().extract().response().jsonPath().getString("bookingid[0]");

    given(requestSpec)
        .when()
        .get(BASE_PATH + bookingIDpath)
        .then()
        .contentType(ContentType.JSON)
        .body("$", hasKey("bookingdates"));
  }

  @Test
  public void canCreateBooking() {
    BookingInfo bookingInfo = BookingInfo.builder()
        .firstname("Jane")
        .lastname("Doe")
        .totalprice(42)
        .depositpaid(true)
        .bookingdates(CheckInOutDate.builder()
            .checkin("2020-11-11")
            .checkout("2020-11-12")
            .build())
        .additionalneeds("Wakeup Call")
        .build();

    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.JSON)))
        .setBody(bookingInfo)
        .build();

    RestAssured.given(requestSpec)
        .when()
        .post(BASE_PATH)
        .then()
        .contentType(ContentType.JSON);
  }

  @Test
  public void canUpdateBooking() {
    String bookingID = randomBookingID();

    BookingInfo bookingInfo = BookingInfo.builder()
        .firstname("Jane")
        .lastname("Doe")
        .totalprice(42)
        .depositpaid(true)
        .bookingdates(CheckInOutDate.builder()
            .checkin("2020-11-11")
            .checkout("2020-11-13")
            .build())
        .additionalneeds("WakeupCall")
        .build();

    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.JSON)))
        .addCookie("token", token())
        .setBody(bookingInfo)
        .build();

    RestAssured.given(requestSpec)
        .when()
        .put(BASE_PATH + "/" + bookingID)
        .then()
        .contentType(ContentType.JSON);
  }

  @Test
  public void canPatchBooking() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.JSON)))
        .addCookie("token", token())
        .build();

    String bookingID = randomBookingID();
    Response initInfo = given(requestSpec)
        .when()
        .get(BASE_PATH + bookingID);

    BookingInfo updateInfo = BookingInfo.builder()
        .firstname("Jane")
        .lastname("Doe")
        .build();

    RestAssured.given(requestSpec)
        .when()
        .body(updateInfo)
        .patch(BASE_PATH + "/" + bookingID)
        .then()
        .contentType(ContentType.JSON)
        .body("firstname", hasValue(updateInfo.getFirstname()));

    RestAssured.given(requestSpec)
        .when()
        .body(initInfo)
        .patch(BASE_PATH + "/" + bookingID);
  }

  private static String token() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setBody("{ \"username\" : \"admin\", \"password\" : \"password123\"}")
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.TEXT)))
        .build();

    return given(requestSpec).post("https://restful-booker.herokuapp.com/auth").path("token");
  }

  private static String randomBookingID() {
    Response resp = given().header("content-type", ContentType.JSON.toString())
        .get(BASE_PATH).then().extract().response();
    List<String> bookingIDs = resp.jsonPath().getList("$");
    return resp.jsonPath().getString("bookingid[" + RandomUtils.nextInt(0, bookingIDs.size()) + "]");
  }
}

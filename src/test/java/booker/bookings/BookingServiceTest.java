package booker.bookings;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.hasKey;

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
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookingServiceTest extends BaseBookerTest {

  @BeforeClass
  private static void setup() {
    RestAssured.basePath = "/booking";
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
        .get()
        .then()
        .contentType(ContentType.JSON);
  }

  @Test
  public void canGetBooking() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .build();

    String bookingIDpath =
        "/" + given(requestSpec).get().then().extract().response().jsonPath().getString("bookingid[0]");

    given(requestSpec)
        .when()
        .get(bookingIDpath)
        .then()
        .contentType(ContentType.JSON)
        .body("$", hasKey("bookingdates"));
  }

  @Test
  public void canCreateBooking() {
    BookingInfo bookingInfo = BookingInfo.builder()
        .firstName("Jane")
        .lastName("Doe")
        .totalPrice(42)
        .depositPaid(true)
        .bookingDates(CheckInOutDate.builder()
            .checkIn("2020-11-11")
            .checkOut("2020-11-12")
            .build())
        .additionalNeeds("Wakeup Call")
        .build();

    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.JSON)))
        .setBody(BookingInfo.toJson(bookingInfo))
        .build();

    RestAssured.given(requestSpec)
        .when()
        .post()
        .then()
        .contentType(ContentType.JSON);
  }

  @Test
  public void canUpdateBooking() {
    String bookingID = randomBookingID();

    BookingInfo bookingInfo = bookingEntry();

    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.JSON)))
        .addCookie("token", token())
        .setBody(BookingInfo.toJson(bookingInfo))
        .build();

    RestAssured.given(requestSpec)
        .when()
        .put("/" + bookingID)
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
        .setBody("{ \"firstname\" : \"Jane\", \"lastname\" : \"Doe\"}")
        .build();

    String bookingID = randomBookingID();

    RestAssured.given(requestSpec)
        .when()
        .patch("/" + bookingID)
        .then()
        .contentType(ContentType.JSON)
        .body("firstname", Matchers.hasToString("Jane"));
  }

  private static String token() {
    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setBody("{ \"username\" : \"admin\", \"password\" : \"password123\"}")
        .build();

    return given(requestSpec).post("https://restful-booker.herokuapp.com/auth").path("token");
  }

  private static String randomBookingID() {
    Response resp = given()
        .header("content-type", ContentType.JSON.toString())
        .get()
        .then()
        .extract()
        .response();
    List<String> bookingIDs = resp.jsonPath().getList("$");
    return resp.jsonPath()
        .getString("bookingid[" + RandomUtils.nextInt(0, bookingIDs.size()) + "]");
  }

  private static BookingInfo bookingEntry() {
    return BookingInfo.builder()
        .firstName("Jane")
        .lastName("Doe")
        .totalPrice(42)
        .depositPaid(true)
        .bookingDates(CheckInOutDate.builder()
            .checkIn("2020-11-11")
            .checkOut("2020-11-13")
            .build())
        .additionalNeeds("WakeupCall")
        .build();
  }
}

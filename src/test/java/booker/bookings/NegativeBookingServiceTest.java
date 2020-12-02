package booker.bookings;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

import booker.BaseBookerTest;
import booker.model.BookingInfo;
import booker.model.BookingInfo.CheckInOutDate;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NegativeBookingServiceTest extends BaseBookerTest {

  @BeforeClass
  private static void setup() {
    RestAssured.basePath = "/booking";
  }

  @Test
  public void cannotPatchWithoutToken() {
    String bookingIDpath =
        "/" + given().header("content-type", ContentType.JSON.toString())
            .get().then().extract().response()
            .jsonPath().getString("bookingid[0]");

    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.TEXT)))
        .setBody("{ \"firstname\" : \"Jane\", \"lastname\" : \"Doe\"}")
        .build();

    RestAssured.given(requestSpec)
        .when()
        .patch(bookingIDpath)
        .then()
        .statusCode(403);
  }

  @Test
  public void cannotUpdateWithoutToken() {
    String bookingIDpath =
        "/" + given().header("content-type", ContentType.JSON.toString())
            .get().then().extract().response()
            .jsonPath().getString("bookingid[0]");

    BookingInfo bookingInfo = BookingInfo.builder()
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

    RequestSpecification requestSpec = new RequestSpecBuilder()
        .addHeader("content-type", ContentType.JSON.toString())
        .setConfig(
            config().encoderConfig(encoderConfig().encodeContentTypeAs("Accept: application/json", ContentType.JSON)))
        .setBody(bookingInfo)
        .build();

    RestAssured.given(requestSpec)
        .when()
        .put(bookingIDpath)
        .then()
        .statusCode(403);
  }

}

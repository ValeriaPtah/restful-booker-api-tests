package booker.bookings;

import booker.BaseBookerTest;
import booker.model.Booking;
import booker.util.BookingHelper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NegativeBookingServiceTest extends BaseBookerTest {

    @BeforeClass
    public static void setup() {
        RestAssured.basePath = "/booking";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void cannotPatchWithoutToken() {
        String bookingIDpath = "/" + BookingHelper.randomBookingID();

        RestAssured.given()
                .accept(ContentType.JSON)
                .body("{ \"firstname\" : \"Jane\", \"lastname\" : \"Doe\"}")
                .when()
                .patch(bookingIDpath)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void cannotUpdateWithoutToken() {
        String bookingIDpath = "/" + BookingHelper.randomBookingID();
        Booking testBooking = BookingHelper.testBookingEntry();

        RestAssured.given()
                .body(BookingHelper.toJson(testBooking, Booking.class))
                .when()
                .put(bookingIDpath)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}

package booker.bookings;

import booker.BaseBookerTest;
import booker.model.Booking;
import booker.util.BookingHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static booker.util.BookingHelper.token;

public class BookingDeletionTest extends BaseBookerTest {

    @BeforeClass
    public static void setup() {
        RestAssured.basePath = "/booking";
    }

    @Test
    public void canDeleteBooking() {
        Booking testBooking = BookingHelper.testBookingEntry();
        Integer bookingID = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(BookingHelper.toJson(testBooking, Booking.class))
                .post()
                .then()
                .extract().response().path("bookingid");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie("token", token())
                .delete("/" + bookingID)
                .then()
                .statusCode(HttpStatus.SC_OK);

        RestAssured.given()
                .contentType(ContentType.TEXT)
                .when()
                .get("/" + bookingID)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}

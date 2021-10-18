package booker.bookings;

import booker.BaseBookerTest;
import booker.model.Booking;
import booker.util.BookingHelper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static booker.util.BookingHelper.token;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.hasToString;

public class BookingServiceTest extends BaseBookerTest {

    private final File bookingListSchema = BookingHelper.getJsonSchema("booking-list-schema.json");
    private final File existingBookingSchema = BookingHelper.getJsonSchema("existing-booking-schema.json");
    private final Booking testBooking = BookingHelper.testBookingEntry();

    @BeforeClass
    public static void setup() {
        RestAssured.basePath = "/booking";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void canGetAllBookings() {
        RestAssured.given()
                .when()
                .get()
                .then()
                .body(matchesJsonSchema(bookingListSchema));
    }

    @Test
    public void canGetBooking() {
        String bookingIDpath = "/" + BookingHelper.randomBookingID();

        RestAssured.given()
                .when()
                .get(bookingIDpath)
                .then()
                .body(matchesJsonSchema(existingBookingSchema));
    }

    @Test
    public void canUpdateBooking() {
        String bookingIDpath = "/" + BookingHelper.randomBookingID();

        RestAssured.given()
                .cookie("token", token())
                .body(BookingHelper.toJson(testBooking, Booking.class))
                .when()
                .put(bookingIDpath)
                .then()
                .body("firstname", hasToString(testBooking.getFirstName()));
    }

    @Test
    public void canPatchBooking() {
        String bookingIDpath = "/" + BookingHelper.randomBookingID();

        RestAssured.given()
                .cookie("token", token())
                .body("{ \"firstname\" : \"Jane\", \"lastname\" : \"Doe\"}")
                .when()
                .patch(bookingIDpath)
                .then()
                .body("firstname", hasToString("Jane"));
    }

}

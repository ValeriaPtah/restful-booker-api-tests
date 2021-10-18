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

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class BookingCreationTest extends BaseBookerTest {

    @BeforeClass
    public static void setup() {
        RestAssured.basePath = "/booking";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_CREATED)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void canCreateBooking() {
        final File createdBookingSchema = BookingHelper.getJsonSchema("created-booking-schema.json");
        final Booking testBooking = BookingHelper.testBookingEntry();

        RestAssured.given()
                .body(BookingHelper.toJson(testBooking, Booking.class))
                .when()
                .post()
                .then()
                .body(matchesJsonSchema(createdBookingSchema));
    }
}

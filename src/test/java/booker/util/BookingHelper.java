package booker.util;

import booker.BaseBookerTest;
import booker.model.Booking;
import com.squareup.moshi.Moshi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

@UtilityClass
public class BookingHelper {

    private static final Moshi MOSHI = new Moshi.Builder().build();

    public static String token() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBody("{ \"username\" : \"admin\", \"password\" : \"password123\"}")
                .build();

        return given(requestSpec).post("https://restful-booker.herokuapp.com/auth").path("token");
    }

    public static Integer randomBookingID() {
        Response resp = given().contentType(ContentType.JSON)
                .get()
                .then().extract().response();
        List<Map<String, Integer>> bookingIDs = resp.jsonPath().getList("$");
        return bookingIDs.get(RandomUtils.nextInt(0, bookingIDs.size())).get("bookingid");
    }

    public static Booking testBookingEntry() {
        return Booking.builder()
                .firstName("Jane")
                .lastName("Doe")
                .totalPrice(42)
                .depositPaid(true)
                .bookingDates(Booking.CheckInOutDate.builder()
                        .checkIn("2020-11-11")
                        .checkOut("2020-11-13")
                        .build())
                .additionalNeeds("WakeupCall")
                .build();
    }

    public static <T> String toJson(T object, Class<T> type) {
        return MOSHI
                .adapter(type)
                .lenient()
                .toJson(object);
    }

    public static File getJsonSchema(String schemaPath) {
        ClassLoader classLoader = BaseBookerTest.class.getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(schemaPath)).getFile());
    }

}

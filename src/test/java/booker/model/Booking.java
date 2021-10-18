package booker.model;

import com.squareup.moshi.Json;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Booking {

    @Json(name = "firstname")
    String firstName;

    @Json(name = "lastname")
    String lastName;

    @Json(name = "totalprice")
    Integer totalPrice;

    @Json(name = "depositpaid")
    Boolean depositPaid;

    @Json(name = "bookingdates")
    CheckInOutDate bookingDates;

    @Json(name = "additionalneeds")
    String additionalNeeds;

    @Value
    @Builder
    public static class CheckInOutDate {

        @Json(name = "checkin")
        String checkIn;

        @Json(name = "checkout")
        String checkOut;

    }

}

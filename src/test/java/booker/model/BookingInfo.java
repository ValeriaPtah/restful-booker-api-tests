package booker.model;

import com.squareup.moshi.Json;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookingInfo {

  @Json(name = "firstname")
  String firstname;

  @Json(name = "lastname")
  String lastname;

  @Json(name = "totalprice")
  Integer totalprice;

  @Json(name = "depositpaid")
  Boolean depositpaid;

  @Json(name = "bookingdates")
  CheckInOutDate bookingdates;

  @Json(name = "additionalneeds")
  String additionalneeds;

  @Value
  @Builder
  public static class CheckInOutDate {

    @Json(name = "checkin")
    String checkin;

    @Json(name = "checkout")
    String checkout;

  }

}

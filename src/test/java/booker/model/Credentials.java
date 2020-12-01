package booker.model;

import com.squareup.moshi.Json;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Credentials {

  @Json(name = "username")
  String username;

  @Json(name = "password")
  String password;

}

package booker.healthcheck;

import booker.BaseBookerTest;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PingServiceTest extends BaseBookerTest {

  @BeforeClass
  private static void setup() {
    RestAssured.basePath = "/ping";
    RestAssured.responseSpecification = new ResponseSpecBuilder()
        .expectStatusCode(201)
        .build();
  }

  @Test
  public void healthCheck() {
    RestAssured.given().get();
  }

}

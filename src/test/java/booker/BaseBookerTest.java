package booker;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseBookerTest {

  @BeforeClass
  public void before() {
    RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
    RestAssured
        .filters(new io.restassured.filter.log.RequestLoggingFilter(),
            new io.restassured.filter.log.ResponseLoggingFilter());
  }

}

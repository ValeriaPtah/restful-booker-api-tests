package booker.healthcheck;

import booker.BaseBookerTest;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PingServiceTest extends BaseBookerTest {

    @BeforeClass
    public static void setup() {
        RestAssured.basePath = "/ping";
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    @Test
    public void healthCheck() {
        RestAssured.given().get();
    }

}

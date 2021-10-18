package booker;

import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseBookerTest {

    @BeforeClass
    public static void before() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
        RestAssured
                .filters(new io.restassured.filter.log.RequestLoggingFilter(),
                        new io.restassured.filter.log.ResponseLoggingFilter());
    }

    @AfterClass
    public static void after() {
        RestAssured.basePath = "";
        RestAssured.requestSpecification = null;
        RestAssured.responseSpecification = null;
    }

}

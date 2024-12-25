import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class US_102 extends Setup {

    static String cityId;

    @Test(priority = 1)
    public void getCountryList() {
        given().
                spec(reqSpec).
                when().
                get("/school-service/api/countries").
                then().
                statusCode(200).
                body("", hasSize(greaterThan(0)));
    }

    @Test(priority = 2)
    public void getCities() {
        given().
                spec(reqSpec).
                when().
                get("/school-service/api/cities").
                then().
                statusCode(200).
                body("", hasSize(greaterThan(0)));
    }

    @Test(priority = 3)
    public void createCity() {
        String requestBody = "{\n" +
                "  \"name\": \"TestCity\",\n" +
                "  \"country\": { \"id\": \"673f711277a27b779aafb1cc\" },\n" +
                "  \"hasState\": false,\n" +
                "  \"translateName\": []\n" +
                "}";

        Response response = given().
                spec(reqSpec).
                body(requestBody).
                when().
                post("/school-service/api/cities");

        response.then().
                statusCode(201).
                body("name", equalTo("TestCity"));

        // Save the city ID for further operations
        cityId = response.jsonPath().getString("id");
    }

    @Test(priority = 4)
    public void updateCity() {
        String updatedRequestBody = "{\n" +
                "  \"id\": \"" + cityId + "\",\n" +
                "  \"name\": \"UpdatedCity\",\n" +
                "  \"country\": { \"id\": \"673f711277a27b779aafb1cc\" },\n" +
                "  \"hasState\": false,\n" +
                "  \"translateName\": []\n" +
                "}";

        given().
                spec(reqSpec).
                body(updatedRequestBody).
                when().
                put("/school-service/api/cities").
                then().
                statusCode(200).
                body("name", equalTo("UpdatedCity"));
    }

    @Test(priority = 5)
    public void deleteCity() {
        given().
                spec(reqSpec).
                when().
                delete("/school-service/api/cities/" + cityId).
                then().
                statusCode(anyOf(equalTo(200), equalTo(204)));
    }
}

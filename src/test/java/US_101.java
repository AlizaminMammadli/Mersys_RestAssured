import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import org.testng.annotations.Test;

public class US_101 extends Setup {
    static String stateId;

    @Test(priority = 1)
    public void getStateList() {
        given().
                spec(reqSpec).
                when().
                get("/school-service/api/states").
                then().
                statusCode(200).
                body("", hasSize(greaterThan(0)));
    }

    @Test(priority = 2)
    public void createState() {
        String requestBody = "{\n" +
                "  \"name\": \"TestState\",\n" +
                "  \"shortName\": \"TS\",\n" +
                "  \"country\": { \"id\": \"632cfcea0d5736269c5aa7c1\" }\n" +
                "}";

        Response response = given().
                spec(reqSpec).
                body(requestBody).
                when().
                post("/school-service/api/states");

        response.then().
                statusCode(201).
                body("name", equalTo("TestState"));

        // Save the state ID for further operations
        stateId = response.jsonPath().getString("id");
    }

    @Test(priority = 3)
    public void updateState() {
        String updatedRequestBody = "{\n" +
                "  \"id\": \"" + stateId + "\",\n" +
                "  \"name\": \"UpdatedState\",\n" +
                "  \"shortName\": \"US\",\n" +
                "  \"country\": { \"id\": \"632cfcea0d5736269c5aa7c1\" }\n" +
                "}";

        given().
                spec(reqSpec).
                body(updatedRequestBody).
                when().
                put("/school-service/api/states").
                then().
                statusCode(200).
                body("name", equalTo("UpdatedState"));
    }

    @Test(priority = 4)
    public void deleteState() {
        given().
                spec(reqSpec).
                when().
                delete("/school-service/api/states/" + stateId).
                then().
                statusCode(anyOf(equalTo(200), equalTo(204)));
    }
}




import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class US_109 extends Setup {
    Faker faker = new Faker();
    String schoolId = "6576fd8f8af7ce488ac69b89";
    String gradingId;

    @Test(priority = 1)
    public void listGradingSchemes() {
        given()
                .spec(reqSpec)
                .body("")

                .when()
                .get("/school-service/api/grading-schemes/school/" + schoolId + "/search")

                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(priority = 2)
    public void addAGradingSchemes() {
        Map<String, String> entityField = new HashMap<>();
        entityField.put("name", faker.lorem().word() + faker.number().digits(3));
        entityField.put("schoolId", "6576fd8f8af7ce488ac69b89");
        entityField.put("type", "POINT");

        gradingId =
                given()
                        .spec(reqSpec)
                        .body(entityField)

                        .when()
                        .post("/school-service/api/grading-schemes")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");
    }

    @Test(priority = 3, dependsOnMethods = "addAGradingSchemes")
    public void editTheGradingSchemes() {
        Map<String, String> entityField = new HashMap<>();
        entityField.put("id", gradingId);
        entityField.put("name", "Updated " + faker.lorem().word() + faker.number().digits(3));
        entityField.put("schoolId", "6576fd8f8af7ce488ac69b89");
        entityField.put("type", "POINT");
        given()
                .spec(reqSpec)
                .body(entityField)

                .when()
                .put("/school-service/api/grading-schemes")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", containsString("Updated"));
    }

    @Test(priority = 4, dependsOnMethods = "addAGradingSchemes")
    public void deleteTheGradingSchemes() {
        reqSpec = given().spec(reqSpec)
                .header("x-school", "6576fd8f8af7ce488ac69b89")
                .request();


        given()
                .spec(reqSpec)
                .body("")

                .when()
                .delete("/school-service/api/grading-schemes/" + gradingId)

                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(priority = 5, dependsOnMethods = "addAGradingSchemes")
    public void deleteTheGradingSchemesNegative() {
        reqSpec = given().spec(reqSpec)
                .header("x-school", "6576fd8f8af7ce488ac69b89")
                .request();


        given()
                .spec(reqSpec)
                .body("")

                .when()
                .delete("/school-service/api/grading-schemes/" + gradingId)

                .then()
                .log().body()
                .statusCode(400);
    }
}

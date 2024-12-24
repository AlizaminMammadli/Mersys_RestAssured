import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class US_108 extends Setup {
    Faker faker = new Faker();
    String educationId;
    String schoolId;
    String gradeLevelId;
    String subjectId;
    String gradeCategoriesTemplateId;
    String gradeCategoryId;
    String packageId;


    @Test
    public void addAnEducationStandard() {
        Map<String, String> entityField = new HashMap<>();
        entityField.put("name", faker.lorem().word());
        entityField.put("description", faker.lorem().sentence());
        entityField.put("schoolId", "6576fd8f8af7ce488ac69b89");
        entityField.put("gradeLevelId", "654898fae70d9e34a8331e51");
        entityField.put("subjectId", "657704ff8af7ce488ac69b9e");
        entityField.put("gradeCategoriesTemplateId", "657707e18af7ce488ac69ba9");
        entityField.put("gradeCategoryId", "59ef-d8ab");
        entityField.put("packageId", "6761dcbca7945338bd1ff0a7");

        Response response = given()
                .spec(reqSpec)
                .body(entityField)

                .when()
                .post("/school-service/api/education-standard")

                .then()
                .log().body()
                .statusCode(201)
                .extract().response();

        educationId = response.path("id");
        schoolId = response.path("schoolId");
        gradeLevelId = response.path("gradeLevelId");
        subjectId = response.path("subjectId");
        gradeCategoriesTemplateId = response.path("gradeCategoriesTemplateId");
        gradeCategoryId = response.path("gradeCategoryId");
        packageId = response.path("packageId");
    }

    @Test(priority = 2,dependsOnMethods = "addAnEducationStandard")
    public void listEducationStandards() {

        given()
                .spec(reqSpec)
                .body("")

                .when()
                .get("/school-service/api/education-standard/school/" + schoolId)

                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(priority = 3,dependsOnMethods = "addAnEducationStandard")
    public void editTheEducationStandard() {

        Map<String, String> entityField = new HashMap<>();
        entityField.put("name", "Updated " + faker.lorem().word());
        entityField.put("description", "Updated " + faker.lorem().sentence());
        entityField.put("id", educationId);
        entityField.put("schoolId", schoolId);
        entityField.put("gradeLevelId", gradeLevelId);
        entityField.put("subjectId", subjectId);
        entityField.put("gradeCategoriesTemplateId", gradeCategoriesTemplateId);
        entityField.put("gradeCategoryId", gradeCategoryId);
        entityField.put("packageId", packageId);

        given()
                .spec(reqSpec)
                .body(entityField)

                .when()
                .put("/school-service/api/education-standard")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", containsString("Updated"))
                .body("description", containsString("Updated"));
    }

    @Test(priority = 4,dependsOnMethods = "addAnEducationStandard")
    public void deleteTheEducationStandard() {

        given()
                .spec(reqSpec)
                .body("")

                .when()
                .delete("/school-service/api/education-standard/" + educationId)

                .then()
                .log().body()
                .statusCode(204);
    }

    @Test(priority = 5,dependsOnMethods = "addAnEducationStandard")
    public void deleteTheEducationStandardNegative() {

        given()
                .spec(reqSpec)
                .body("")

                .when()
                .delete("/school-service/api/education-standard/" + educationId)

                .then()
                .log().body()
                .statusCode(400);
    }
}

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class US_104 extends Setup {
    Faker faker = new Faker();
    String schoolId = "6576fd8f8af7ce488ac69b89";
    String fieldId;
    String name;
    String code;
    String defaultValue;

    @Test(priority = 1)
    public void listCustomFields() {
        Map<String, String> entityField = new HashMap<>();
        entityField.put("id", "");
        entityField.put("schoolId", schoolId);
        entityField.put("name", "");
        entityField.put("code", "");
        entityField.put("type", "");
        entityField.put("constant", "false");
        entityField.put("systemField", "false");
        entityField.put("maxLength", "0");
        entityField.put("rows", "0");
        entityField.put("multiple", "false");
        entityField.put("createdDate", "");
        entityField.put("changedDate", "");

        given()
                .spec(reqSpec)
                .body(entityField)

                .when()
                .post("/school-service/api/entity-field/search")

                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(priority = 2)
    public void createACustomField() {
        Map<String, String> entityField = new HashMap<>();
        entityField.put("schoolId", schoolId);
        entityField.put("name", faker.lorem().word() + faker.number().digits(3));
        entityField.put("code", faker.lorem().word() + faker.number().digits(3));
        entityField.put("type", "AUTOCOMPLETE");
        entityField.put("constant", "true");
        entityField.put("systemField", "false");
        entityField.put("hint", faker.lorem().word());
        entityField.put("maxLength", "0");
        entityField.put("rows", "2");
        entityField.put("multiple", "false");
        entityField.put("offLabel", "");
        entityField.put("onLabel", "");
        entityField.put("defaultValue", faker.lorem().word());
        entityField.put("createdDate", "");
        entityField.put("changedDate", "");

        Response response =
                given()
                        .spec(reqSpec)
                        .body(entityField)

                        .when()
                        .post("/school-service/api/entity-field")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().response();

        fieldId = response.path("id");
        name = response.path("name");
        code = response.path("code");
        defaultValue = response.path("defaultValue");

    }

    @Test(priority = 3,dependsOnMethods = "createACustomField")
    public void createACustomFieldNegative() {
        Map<String, String> entityField = new HashMap<>();
        entityField.put("schoolId", schoolId);
        entityField.put("name", name);
        entityField.put("code", code);
        entityField.put("type", "AUTOCOMPLETE");
        entityField.put("constant", "true");
        entityField.put("systemField", "false");
        entityField.put("hint", faker.lorem().word());
        entityField.put("maxLength", "0");
        entityField.put("rows", "2");
        entityField.put("multiple", "false");
        entityField.put("offLabel", "");
        entityField.put("onLabel", "");
        entityField.put("defaultValue", defaultValue);
        entityField.put("createdDate", "");
        entityField.put("changedDate", "");

        given()
                .spec(reqSpec)
                .body(entityField)

                .when()
                .post("/school-service/api/entity-field")

                .then()
                .log().body()
                .statusCode(400);
    }

    @Test(priority = 4,dependsOnMethods = "createACustomField")
    public void editTheCustomField() {
        Map<String, String> entityField = new HashMap<>();
        entityField.put("id", fieldId);
        entityField.put("schoolId", schoolId);
        entityField.put("name", "Updated "+faker.lorem().word() + faker.number().digits(3));
        entityField.put("code", "Updated "+faker.lorem().word() + faker.number().digits(3));
        entityField.put("type", "AUTOCOMPLETE");
        entityField.put("constant", "true");
        entityField.put("systemField", "false");
        entityField.put("hint", faker.lorem().word());
        entityField.put("maxLength", "0");
        entityField.put("rows", "2");
        entityField.put("multiple", "false");
        entityField.put("defaultValue", defaultValue);
        entityField.put("createdDate", "");
        entityField.put("changedDate", "");

        given()
                .spec(reqSpec)
                .body(entityField)

                .when()
                .put("/school-service/api/entity-field")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",containsString("Updated"))
                .body("code",containsString("Updated"));
    }

    @Test(priority = 5,dependsOnMethods = "createACustomField")
    public void deleteTheCustomField(){

        given()
                .spec(reqSpec)
                .body("")

                .when()
                .delete("/school-service/api/entity-field/"+fieldId)

                .then()
                .log().body()
                .statusCode(204);
    }

    @Test(priority = 6,dependsOnMethods = "createACustomField")
    public void deleteTheCustomFieldNegative(){

        given()
                .spec(reqSpec)
                .body("")

                .when()
                .delete("/school-service/api/entity-field/"+fieldId)

                .then()
                .log().body()
                .statusCode(400)
                .body("message",containsString("EntityField not found"));
    }

}

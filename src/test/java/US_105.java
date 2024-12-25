import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_105 extends Setup {

    String schoolId = "6576fd8f8af7ce488ac69b89";
    String groupId;

    @Test
    public void listStudentGroups() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("schoolId", schoolId);

        Response response =
                given()
                        .spec(reqSpec)
                        .body(requestBody)

                        .when()
                        .post("/school-service/api/student-group/search")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();
    }

    @Test(dependsOnMethods = "listStudentGroups")
    public void addStudentGroup() {
        Map<String, Object> groupDetails = new HashMap<>();
        groupDetails.put("schoolId", schoolId);
        groupDetails.put("name", "Test Group Name");
        groupDetails.put("description", "Test Group Description");
        groupDetails.put("active", true);
        groupDetails.put("publicGroup", false);
        groupDetails.put("showToStudent", true);

        Response response =
                given()
                        .spec(reqSpec)
                        .body(groupDetails)

                        .when()
                        .post("/school-service/api/student-group")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().response();

        groupId = response.path("id");
    }

    @Test(dependsOnMethods = "addStudentGroup")
    public void editStudentGroup() {
        Map<String, Object> updatedGroupDetails = new HashMap<>();
        updatedGroupDetails.put("id", groupId);
        updatedGroupDetails.put("schoolId", schoolId);
        updatedGroupDetails.put("name", "Updated Test Group Name");
        updatedGroupDetails.put("description", "Updated Test Group Description");
        updatedGroupDetails.put("active", false);
        updatedGroupDetails.put("publicGroup", true);
        updatedGroupDetails.put("showToStudent", false);

        given()
                .spec(reqSpec)
                .body(updatedGroupDetails)

                .when()
                .put("/school-service/api/student-group")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", org.hamcrest.Matchers.containsString("Updated"));
    }

    @Test(dependsOnMethods = "editStudentGroup")
    public void deleteStudentGroup() {
        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/student-group/" + groupId)

                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "deleteStudentGroup")
    public void deleteStudentGroupNegative() {
        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/student-group/" + groupId)

                .then()
                .log().body()
                .statusCode(400)
                .body("message", org.hamcrest.Matchers.containsString("Group with given id does not exist!"));
    }
}

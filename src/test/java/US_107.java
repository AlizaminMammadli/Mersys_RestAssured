import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class US_107 extends Setup {
    String studenId;

    @Test
    public void ListStudents() {
        Response response =

                given()
                        .spec(reqSpec)

                        .when()
                        .get("/school-service/api/students/group/6657e4bfdf68c443afcc793a?page=0&size=10")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();
        studenId = response.jsonPath().getString("content[0].id");
    }

    @Test
    public void deleteStudent() {
        List<String> students = new ArrayList<>();
        students.add(studenId);
        given()
                .spec(reqSpec)
                .body(students)

                .when()
                .post("/school-service/api/student-group/6657e4bfdf68c443afcc793a/remove-students?page=0&size=10")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test
    public void deletedID_Control() {
        String studentLastId =
                given()
                        .spec(reqSpec)

                        .when()
                        .get("/school-service/api/students/group/6657e4bfdf68c443afcc793a?page=0&size=10")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().jsonPath().getString("content[0].id");
        Assert.assertNotEquals(studenId, studentLastId, "Student not deleted!!!");


    }

}

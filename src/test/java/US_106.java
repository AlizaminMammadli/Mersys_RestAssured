import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class US_106 extends Setup{

    List<String> studentIds;
    List<String> groupStudentIds;
    String groupID = "66689d4744a9e3277bf1451b";

    @Test
    public void listStudents() {
        Response response =

                given()
                        .spec(reqSpec)

                        .when()
                        .get("/school-service/api/students/group/66537d4d4f67ac4b1de8b9cb?page=0&size=10")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();
        studentIds = response.jsonPath().getList("content.id", String.class);
        System.out.println("Toplanan Öğrenci ID'leri: " + studentIds);
    }

    // /school-service/api/student-group/6765568c76cc353638648c86/add-students

    @Test(dependsOnMethods = "listStudents" )
    public void addStudents(){

        given()
                .spec(reqSpec)
                .body(studentIds)

                .when()
                .post("/school-service/api/student-group/" + groupID + "/add-students")

                .then()
                .log().body()
                .statusCode(200);
    }


    @Test(dependsOnMethods = "addStudents" )
    public void getStudentsGroup() {
        Response response =

                given()
                        .spec(reqSpec)

                        .when()
                        .get("/school-service/api/students/group/6765568c76cc353638648c86?page=0&size=10")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().response();

        groupStudentIds = response.jsonPath().getList("content.id", String.class);
        System.out.println("Eklenen Yeni Öğrenci ID'leri: " + groupStudentIds);

        Assert.assertTrue(groupStudentIds.containsAll(studentIds), "Eklenecek Students liste bulunamadi");

    }

}

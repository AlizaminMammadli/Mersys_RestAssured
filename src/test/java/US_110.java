import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


import static io.restassured.RestAssured.*;

public class US_110 extends Setup {

    Faker faker = new Faker();
    String incidentID;
    String name;

    @Test
    public void IncidentTypeList() {

        Map<String, String> incidentList = new HashMap<>();
        incidentList.put("schoolId", "6576fd8f8af7ce488ac69b89");


        given()
                .spec(reqSpec)
                .body(incidentList)

                .when()
                .post("/school-service/api/incident-type/search")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = {"IncidentTypeList"})
    public void AddIncidentType() {
        String rndFullName = faker.name().fullName();

        Map<String, String> addIncident = new HashMap<>();
        addIncident.put("name", rndFullName);
        addIncident.put("schoolId", "6576fd8f8af7ce488ac69b89");
        addIncident.put("minPoint", "45");
        addIncident.put("maxPoint", "77");

         Response response =
                 given()
                        .spec(reqSpec)
                        .body(addIncident)

                        .when()
                        .post("/school-service/api/incident-type")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().response()
                 ;
        incidentID = response.jsonPath().getString("id");
        name = response.jsonPath().getString("name");
    }

    @Test(dependsOnMethods = {"AddIncidentType"})
    public void SearchIncidentType() {

        Map<String, String> searchIncident = new HashMap<>();
        searchIncident.put("name",name);
        searchIncident.put("schoolId", "6576fd8f8af7ce488ac69b89");

                given()
                        .spec(reqSpec)
                        .body(searchIncident)

                        .when()
                        .post("/school-service/api/incident-type/search")

                        .then()
                        .log().body()
                        .statusCode(200)
                ;
    }

    @Test(dependsOnMethods = {"SearchIncidentType"})
    public void EditIncidentType() {

        Map<String, String> editIncidentType = new HashMap<>();
        editIncidentType.put("id",incidentID);
        editIncidentType.put("name","Jonny6");
        editIncidentType.put("active","true");
        editIncidentType.put("schoolId", "6576fd8f8af7ce488ac69b89");
        editIncidentType.put("minPoint", "20");
        editIncidentType.put("maxPoint", "60");

        given()
                .spec(reqSpec)
                .body(editIncidentType)

                .when()
                .put("/school-service/api/incident-type")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = {"EditIncidentType"})
    public void DeleteIncidentType() {

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/incident-type/"+incidentID)

                .then()
                .log().body()
                .statusCode(200)
        ;
    }
}

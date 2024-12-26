import com.github.javafaker.Faker;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;


public class US_103 extends Setup{

    Faker faker = new Faker();
    String registrationsID;
    Map<String, Object> gradeLevelInfo = new HashMap<>();
    Map<String, Object> fieldsInfo= new HashMap<>();


    @Test
    public void AddRegistration(){

        String rndFullName=faker.name().fullName();

        gradeLevelInfo.put("id","65460e0ce814a84585f1d223");


        Map<String, Object> addRegistrations = new HashMap<>();
        addRegistrations.put("name",rndFullName);
        addRegistrations.put("type", "REGISTRATION");
        addRegistrations.put("school", "6576fd8f8af7ce488ac69b89");
        addRegistrations.put("gradeLevel", gradeLevelInfo);
        addRegistrations.put("academicPeriod", "6577022e8af7ce488ac69b96");
        addRegistrations.put("active", "true");
        addRegistrations.put("sendSMS", "false");
        addRegistrations.put("sendEmailEnabled", "false");

        registrationsID=
        given()
                .spec(reqSpec)
                .body(addRegistrations)

                .when()
                .post("/school-service/api/exams")

                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id")
        ;
        System.out.println("registrationsID = " + registrationsID);
    }

    @Test(dependsOnMethods = {"AddRegistration"})
    public void editRegistration(){

       List<Map<String,Object>> fields=new ArrayList<>();

        fieldsInfo.put("id","411910b8-bd36-11ef-b2e8-0242ac19000a");
        fieldsInfo.put("name","Student's Last Name");
        fieldsInfo.put("customFieldType","STRING");
        fieldsInfo.put("type","DEFAULT");
        fieldsInfo.put("defaultFieldType","STUDENT_LAST_NAME");
        fieldsInfo.put("required", true);

        fields.add(fieldsInfo);

        Map<String, Object> editRegistrations = new HashMap<>();
        editRegistrations.put("id",registrationsID);
        editRegistrations.put("name","jessony228");
        editRegistrations.put("registrationStartDate", "2024-12-17");
        editRegistrations.put("registrationEndDate", "2024-12-26");
        editRegistrations.put("gradeLevel", gradeLevelInfo);
        editRegistrations.put("academicPeriod", "6577022e8af7ce488ac69b96");
        editRegistrations.put("hasDocuments", false);
        editRegistrations.put("type", "REGISTRATION");
        editRegistrations.put("school", "6576fd8f8af7ce488ac69b89");
        editRegistrations.put("deleted", false);
        editRegistrations.put("active", true);
        editRegistrations.put("sendEmailToRegistrar", false);
        editRegistrations.put("fields", fields);

                given()
                        .spec(reqSpec)
                        .body(editRegistrations)

                        .when()
                        .put("/school-service/api/exams")

                        .then()
                        .log().body()
                        .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = {"editRegistration"})
    public void deleteRegistrations(){

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/exams/"+registrationsID)

                .then()
                .log().body()
                .statusCode(204)

        ;
    }

}






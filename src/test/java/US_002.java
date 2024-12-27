import com.github.javafaker.Faker;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class US_002 extends Setup {
    Faker faker = new Faker();

    @Test
    public void Add_Country() {
        String rndmCountryName = faker.country().name() + faker.number().digits(5);
        String rndmCountryCode = faker.country().countryCode2();

        Map<String, Object> addConutry = new HashMap<>();

        addConutry.put("id", null);
        addConutry.put("name", rndmCountryName);
        addConutry.put("code", rndmCountryCode);
        addConutry.put("translateName", null);
        addConutry.put("hasState", true);

        given()

                .spec(reqSpec)
                .body(addConutry)

                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(201);
    }
}
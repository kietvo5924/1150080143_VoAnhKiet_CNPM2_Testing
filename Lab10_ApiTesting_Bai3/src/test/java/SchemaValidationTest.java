import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidationTest extends ApiBaseTest {

    @Test(description = "1. Kiem tra Schema danh sach User (GET /users)")
    public void testUserListSchema() {
        given(requestSpec)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-list-schema.json"));
    }

    @Test(description = "2. Kiem tra Schema chi tiet 1 User voi nested address (GET /users/2)")
    public void testSingleUserSchema() {
        given(requestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"));
    }

    @Test(description = "3. Kiem tra Schema tao moi User (POST /users)")
    public void testCreateUserSchema() {
        String requestBody = "{\"name\": \"Vo Anh Kiet\", \"job\": \"Software Engineer\"}";

        given(requestSpec)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec)
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/create-user-schema.json"));
    }

    @Test(description = "4. DEMO FAIL: Kiem tra body thua field so voi Schema")
    public void testDemoFailSchema() {
        String requestBody = "{\"name\": \"Vo Anh Kiet\", \"job\": \"Software Engineer\"}";

        given(requestSpec)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                // Lệnh này CHẮC CHẮN FAIL vì API trả về thêm trường "id",
                // nhưng file demo-fail-schema.json thiết lập "additionalProperties": false
                .body(matchesJsonSchemaInClasspath("schemas/demo-fail-schema.json"));
    }
}
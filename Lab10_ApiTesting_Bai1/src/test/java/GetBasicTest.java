import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetBasicTest extends ApiBaseTest {

    @Test(description = "Test 1: Lấy danh sách posts và kiểm tra số lượng")
    public void testGetPostsList() {
        given(requestSpec)
                .when()
                .get("/posts")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test(description = "Test 2: Kiểm tra cấu trúc object trong mảng users")
    public void testGetUsersStructure() {
        given(requestSpec)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", everyItem(notNullValue()))
                .body("name", everyItem(notNullValue()))
                .body("email", everyItem(notNullValue()))
                .body("username", everyItem(notNullValue()));
    }

    @Test(description = "Test 3: Lấy 1 user cụ thể và kiểm tra email")
    public void testGetSingleUser3() {
        given(requestSpec)
                .when()
                .get("/users/3")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("id", equalTo(3))
                .body("name", not(emptyString()))
                .body("email", containsString("@"));
    }

    @Test(description = "Test 4: GET resource không tồn tại (404)")
    public void testUserNotFound() {
        given(requestSpec)
                .when()
                .get("/users/9999")
                .then()
                .spec(responseSpec)
                .statusCode(404)
                .body("$", anEmptyMap());
    }
}
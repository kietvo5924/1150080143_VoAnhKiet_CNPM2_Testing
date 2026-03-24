import io.restassured.response.ValidatableResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthAndErrorTest extends ApiBaseTest {

    @Test(description = "Test register thanh cong")
    public void testRegisterSuccess() {
        Map<String, String> body = new HashMap<>();
        body.put("firstName", "Kiet");
        body.put("lastName", "Vo");
        body.put("username", "kietvo2026");

        given(requestSpec)
                .body(body)
                .when()
                .post("/users/add")
                .then()
                .statusCode(201) // dummyjson trả 201 cho tạo mới
                .body("id", notNullValue())
                .body("username", equalTo("kietvo2026"));
    }


    @DataProvider(name = "loginScenarios")
    public Object[][] loginScenarios() {
        return new Object[][] {
                // username, password, expectedStatus, expectedError
                {"emilys", "emilyspass", 200, null}, // Thành công
                {"emilys", "", 400, "Password is required"}, // Thiếu pass
                {"", "emilyspass", 400, "Username is required"}, // Thiếu user
                {"user_khong_ton_tai", "pass123", 400, "Invalid credentials"}, // Sai user
                {"emilys", "sai_pass", 400, "Invalid credentials"} // Sai pass
        };
    }

    @Test(dataProvider = "loginScenarios", description = "Kiem tra cac kich ban login voi DataProvider")
    public void testLoginScenarios(String username, String password, int expectedStatus, String expectedError) {
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        ValidatableResponse response = given(requestSpec)
                .body(body)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(expectedStatus);

        if (expectedError != null) {
            // Dummyjson trả lỗi trong trường "message" thay vì "error"
            response.body("message", containsString(expectedError));
        } else {
            response.body("token", notNullValue());
        }
    }
}
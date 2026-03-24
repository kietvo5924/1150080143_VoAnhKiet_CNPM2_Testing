import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CrudUserTest extends ApiBaseTest {

    private Integer savedUserId; // Biến lưu ID để chain API

    @Test(priority = 1, description = "POST tạo user")
    public void testCreateUser() {
        // 1. Dùng POJO thay vì String JSON
        CreateUserRequest requestBody = new CreateUserRequest("Vo Anh Kiet", "Software Engineer");

        UserResponse response = given(requestSpec)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec)
                .statusCode(201) // Mã HTTP 201 Created
                .body("name", equalTo("Vo Anh Kiet"))
                .body("id", notNullValue())
                // (Đã bỏ qua assert createdAt vì API này không cấp trường này)
                .extract().as(UserResponse.class); // Map thẳng vào POJO

        // 2. Lưu lại ID từ POST
        savedUserId = response.getId();
        System.out.println("Đã tạo user thành công với ID: " + savedUserId);
    }

    @Test(priority = 2, dependsOnMethods = "testCreateUser", description = "GET xác nhận data")
    public void testGetCreatedUser() {
        // 3. Chuyền API: Lấy ID từ bước trên truyền vào bước này
        given(requestSpec)
                .when()
                .get("/users/" + savedUserId)
                .then()
                .spec(responseSpec)
                // DO ĐÂY LÀ FAKE API KHÔNG LƯU DB, TÌM ID VỪA TẠO SẼ RA 404 (Chuẩn logic server)
                .statusCode(404)
                .body("$", anEmptyMap());
    }

    @Test(priority = 3, description = "PUT cập nhật user")
    public void testUpdateUser() {
        CreateUserRequest updateBody = new CreateUserRequest("Vo Anh Kiet", "Senior Test Engineer");

        given(requestSpec)
                .body(updateBody)
                .when()
                .put("/users/2") // Cập nhật user có ID = 2 (user tồn tại sẵn)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("job", equalTo("Senior Test Engineer"));
    }

    @Test(priority = 4, description = "PATCH cập nhật một phần")
    public void testPatchUser() {
        // PATCH thường chỉ gửi trường cần đổi. Dùng string JSON cho trường hợp này là hợp lý.
        String patchBody = "{\"job\":\"Automation Tester\"}";

        given(requestSpec)
                .body(patchBody)
                .when()
                .patch("/users/2")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("job", equalTo("Automation Tester"));
    }

    @Test(priority = 5, description = "DELETE xóa user")
    public void testDeleteUser() {
        given(requestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(responseSpec)
                // Jsonplaceholder trả về 200 khi xóa thành công thay vì 204
                .statusCode(200)
                .body("$", anEmptyMap()); // Kiểm tra response rỗng {}
    }
}
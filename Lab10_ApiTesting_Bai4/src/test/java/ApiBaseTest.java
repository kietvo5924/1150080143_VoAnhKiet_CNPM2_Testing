import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class ApiBaseTest {
    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setupApiSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .setContentType(ContentType.JSON)
                .build();
    }
}
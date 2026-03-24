import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static org.hamcrest.Matchers.lessThan;

public class ApiBaseTest {
    protected RequestSpecification requestSpec;
    protected ResponseSpecification responseSpec;

    @BeforeClass
    public void setupApiSpec() {
        requestSpec = new RequestSpecBuilder()
                // ĐỔI SANG JSONPLACEHOLDER ĐỂ KHÔNG BỊ CLOUDFLARE CHẶN
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        responseSpec = new ResponseSpecBuilder()
                // Do dùng server khác có thể chậm, tôi nới lỏng response time lên 5000ms
                .expectResponseTime(lessThan(5000L))
                .build();
    }
}
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SlaMonitoringTest extends ApiBaseTest {

    @DataProvider(name = "slaData")
    public Object[][] createSlaData() {
        return new Object[][]{
                {"GET", "/users", 2000L, 200},
                {"GET", "/users/2", 1500L, 200},
                {"POST", "/users", 3000L, 201},
                {"POST", "/posts", 2000L, 201}, // Thay cho /api/login
                {"DELETE", "/users/2", 1000L, 200} // Jsonplaceholder trả 200 cho DELETE
        };
    }

    @Test(dataProvider = "slaData", description = "Kiem tra SLA cho cac endpoint")
    public void testApiSla(String method, String endpoint, Long maxMs, int expectedStatus) {
        callApiWithSla(method, endpoint, maxMs, expectedStatus);
    }

    @Step("Gọi {method} {endpoint} - SLA: {maxMs}ms")
    public void callApiWithSla(String method, String endpoint, Long maxMs, int expectedStatus) {
        Response response;
        var request = given(requestSpec);

        if (method.equals("POST")) {
            request.body("{\"name\": \"Kiet\", \"job\": \"Engineer\"}");
            response = request.post(endpoint);
        } else if (method.equals("DELETE")) {
            response = request.delete(endpoint);
        } else {
            response = request.get(endpoint);
        }

        long actualTime = response.getTime();
        System.out.println("Endpoint: " + endpoint + " | SLA: " + maxMs + "ms | Actual: " + actualTime + "ms");

        response.then()
                .statusCode(expectedStatus)
                .time(lessThan(maxMs));
    }

    @Test(description = "Monitoring: Chay 10 lan lien tiep va tinh trung binh")
    public void testResponseTimeMonitoring() {
        List<Long> times = new ArrayList<>();
        String endpoint = "/posts";

        System.out.println("--- Bat dau monitoring cho " + endpoint + " (10 lan) ---");

        for (int i = 1; i <= 10; i++) {
            long time = given(requestSpec)
                    .when()
                    .get(endpoint)
                    .then()
                    .extract()
                    .time();
            times.add(time);
            System.out.println("Lan " + i + ": " + time + "ms");
        }

        long min = Collections.min(times);
        long max = Collections.max(times);
        double avg = times.stream().mapToLong(Long::longValue).average().orElse(0.0);

        System.out.println("--- Ket qua Monitoring ---");
        System.out.println("Min Response Time: " + min + "ms");
        System.out.println("Max Response Time: " + max + "ms");
        System.out.println("Average Response Time: " + String.format("%.2f", avg) + "ms");
    }
}
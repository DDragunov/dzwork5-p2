package Steps;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StepAPI {
    public void APIpostBody() throws IOException {
        JSONObject body=new JSONObject(new String(Files.readAllBytes(Paths.get("src/test/resources/JSON/APItest.json"))));

        body.put("name","Tomato");
        body.put("job","Eat market");

        RequestSpecification request = given();
        request
                .baseUri(Utils.Configuration.getConfigurationValue("baseUrl"))
                .header("Content-type","application/json")
        ;
        Response response = request
                .body(body.toString())
                .when()
                .post("/users")
                .then()
                .extract()
                .response();
        int statusCod = response.statusCode();
        Assertions.assertEquals(statusCod, 201, "Статус код неверный");
        //Статус код будет 201 - что объект новый создан
        Assertions.assertEquals((new JSONObject(response.getBody().asString()).get("name")),(body.get("name")),"Ошибка! НЕ совпадает");
        Assertions.assertEquals((new JSONObject(response.getBody().asString()).get("job")),(body.get("job")),"Ошибка! НЕ совпадает");
    }
}

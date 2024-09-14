package demo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetUserExample2Test {
    private Response response;
    private ResponseBody resBody;
    private JsonPath bodyJson;
    private int userId;

    @BeforeClass
    public void init() {
        userId = 40; // Invalid user ID
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users";

        RequestSpecification req = given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("userId", userId);

        response = req.get("/{userId}");
        resBody = response.getBody();
        bodyJson = resBody.jsonPath();
    }

    @Test
    public void T01_StatusCodeTest() {
        assertEquals(404, response.getStatusCode(), "Status Check Failed!");
    }

    @Test
    public void T02_MessageChecked() {
        assertTrue(resBody.asString().contains("message"), "Message field check Failed!");
    }

    @Test
    public void T03_verifyMessageContent() {
        String message = bodyJson.get("message");
        assertEquals("User not found!", message, "Incorrect error message!");
    }

    @AfterClass
    public void afterTest() {
        RestAssured.baseURI = null;
        RestAssured.basePath = null;
    }
}

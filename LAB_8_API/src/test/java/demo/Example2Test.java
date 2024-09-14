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
import data.UserInfo;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Example2Test {
    private Response response;
    private UserInfo user;
    private ResponseBody resBody;
    private JsonPath bodyJson;

    @BeforeClass
    public void init() {
        user = new UserInfo(null, "nam", 24, "Ky su");
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api/users";

        RequestSpecification req = given()
                .contentType(ContentType.JSON)
                .when()
                .body(user);

        response = req.post();
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
    public void T03_verifyOnMessageContainName() {
        String resMessage = bodyJson.get("message");
        if (resMessage == null) resMessage = "";
        assertTrue(resMessage.contains("name"), "Message not contain invalid field!");
    }

    @AfterClass
    public void afterTest() {
        RestAssured.baseURI = null;
        RestAssured.basePath = null;
    }
}
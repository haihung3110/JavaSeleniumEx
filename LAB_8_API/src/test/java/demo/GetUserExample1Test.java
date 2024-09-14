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

public class GetUserExample1Test {
    private Response response;
    private ResponseBody resBody;
    private JsonPath bodyJson;
    private int userId;

    @BeforeClass
    public void init() {
        userId = 2; // Valid user ID
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
        assertEquals(200, response.getStatusCode(), "Status Check Failed!");
    }

    @Test
    public void T02_IdChecked() {
        assertTrue(resBody.asString().contains("id"), "id field check Failed!");
    }

    @Test
    public void T03_EmailChecked() {
        assertTrue(resBody.asString().contains("email"), "email field check Failed!");
    }

    @Test
    public void T04_FirstNameChecked() {
        assertTrue(resBody.asString().contains("first_name"), "first_name field check Failed!");
    }

    @Test
    public void T05_LastNameChecked() {
        assertTrue(resBody.asString().contains("last_name"), "last_name field check Failed!");
    }

    @Test
    public void T06_AvatarChecked() {
        assertTrue(resBody.asString().contains("avatar"), "avatar field check Failed!");
    }

    @Test
    public void T07_verifyOnMatchingId() {
        int id = bodyJson.getInt("data.id");
        assertEquals(userId, id, "User ID is not matched!");
    }

    @AfterClass
    public void afterTest() {
        RestAssured.baseURI = null;
        RestAssured.basePath = null;
    }
}

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

public class Example1Test {
    private Response response;
    private UserInfo user;
    private ResponseBody resBody;
    private JsonPath bodyJson;

    @BeforeClass
    public void init() {
        user = new UserInfo("Minh", "nam", 24, "Ky su");
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
        assertEquals(201, response.getStatusCode(), "Status Check Failed!");
    }

    @Test
    public void T02_IdChecked() {
        assertTrue(resBody.asString().contains("id"), "id field check Failed!");
    }

    @Test
    public void T03_CreatedAtChecked() {
        assertTrue(resBody.asString().contains("createdAt"), "createdAt field check Failed!");
    }

    @Test
    public void T04_verifyOnMatchingName() {
        String resName = bodyJson.get("name");
        assertEquals(user.getName(), resName, "Name is not matched!");
    }

    @Test
    public void T05_verifyOnMatchingGender() {
        String resGender = bodyJson.get("gender");
        assertEquals(user.getGender(), resGender, "Gender is not matched!");
    }

    @Test
    public void T06_verifyOnMatchingAge() {
        int resAge = bodyJson.getInt("age");
        assertEquals(user.getAge(), resAge, "Age is not matched!");
    }

    @Test
    public void T07_verifyOnMatchingJob() {
        String resJob = bodyJson.get("job");
        assertEquals(user.getJob(), resJob, "Job is not matched!");
    }

    @AfterClass
    public void afterTest() {
        RestAssured.baseURI = null;
        RestAssured.basePath = null;
    }
}
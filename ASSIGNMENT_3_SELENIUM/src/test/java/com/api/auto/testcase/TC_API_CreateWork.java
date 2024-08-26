package com.api.auto.testcase;

import com.api.auto.utils.PropertiesFileUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TC_API_CreateWork {
    private String token;
    private Response response;
    private ResponseBody responseBody;
    private JsonPath jsonBody;

    private String myWork = "Software Developer";
    private String myExperience = "3 years";
    private String myEducation = "Bachelor's Degree";

    @BeforeClass
    public void init() {
        String baseUrl = PropertiesFileUtils.getProperty("baseUrl");
        String createWorkPath = PropertiesFileUtils.getProperty("createWorkPath");
        token = PropertiesFileUtils.getToken();

        RestAssured.baseURI = baseUrl;

        // Create request body
        Map<String, Object> body = new HashMap<>();
        body.put("nameWork", myWork);
        body.put("experience", myExperience);
        body.put("education", myEducation);

        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("token", token)
                .body(body);

        response = request.post(createWorkPath);
        responseBody = response.getBody();
        jsonBody = responseBody.jsonPath();

        System.out.println("Response Body: " + responseBody.asPrettyString());
    }

    @Test(priority = 0)
    public void TC01_Validate201Created() {
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201 Created");
    }

    @Test(priority = 1)
    public void TC02_ValidateWorkId() {
        Integer workId = jsonBody.getInt("id");
        Assert.assertNotNull(workId, "Work ID should not be null");
    }

    @Test(priority = 2)
    public void TC03_ValidateNameOfWorkMatched() {
        String nameWork = jsonBody.getString("nameWork");
        Assert.assertEquals(nameWork, myWork, "Name of work should match the created work");
    }

    @Test(priority = 3)
    public void TC04_ValidateExperienceMatched() {
        String experience = jsonBody.getString("experience");
        Assert.assertEquals(experience, myExperience, "Experience should match the created experience");
    }

    @Test(priority = 4)
    public void TC05_ValidateEducationMatched() {
        String education = jsonBody.getString("education");
        Assert.assertEquals(education, myEducation, "Education should match the created education");
    }
}
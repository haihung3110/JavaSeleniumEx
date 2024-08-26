package com.api.auto.testcase;

import com.api.auto.utils.PropertiesFileUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TC_API_Login {

    @Test
    public void TC01_Validate200Ok() {
        Response response = login();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void TC02_ValidateMessage() {
        Response response = login();
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "Đăng nhập thành công");
    }

    @Test
    public void TC03_ValidateToken() {
        Response response = login();
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token);
        PropertiesFileUtils.saveToken(token);
    }

    @Test
    public void TC05_ValidateUserType() {
        Response response = login();
        String userType = response.jsonPath().getString("user.type");
        Assert.assertEquals(userType, "UNGVIEN");
    }

    @Test
    public void TC06_ValidateAccount() {
        Response response = login();
        String account = response.jsonPath().getString("user.account");
        String expectedAccount = PropertiesFileUtils.getProperty("account");
        Assert.assertEquals(account, expectedAccount);
    }

    public Response login() {
        String baseUrl = PropertiesFileUtils.getProperty("baseUrl");
        String loginPath = PropertiesFileUtils.getProperty("loginPath");
        String account = PropertiesFileUtils.getProperty("account");
        String password = PropertiesFileUtils.getProperty("password");

        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("account", account);
        bodyParams.put("password", password);

        return RestAssured.given()
                .contentType("application/json")
                .body(bodyParams)
                .post(baseUrl + loginPath);
    }
}

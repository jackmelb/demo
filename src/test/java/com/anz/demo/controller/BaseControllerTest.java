package com.anz.demo.controller;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;


public interface BaseControllerTest {
    String CLIENT_ID = "RY2BesK1msUSYhg2gek4OXOSd0pWeHTy";
    String CLIENT_SECRET = "T1KkKwkC3RD7JsWzjQW2EKVF8uQTfhHt5GqX9pvRF9kES9udKqk7vnkkWtHCNVl-";
    String CLIENT_ID_WITHOUT_ROLES = "1ZIUGLZKoNR1cGJZT5CxRvUHn8IXmxzP";
    String CLIENT_SECRET_WITHOUT_ROLES = "7q-M0WX_pitd3unWIpyNL85bfRFZssUWB9ukbNGhwf7gTtK8PsFIval7XDGd_Y_E";
    String GRANT_TYPE = "client_credentials";
    String AUDIENCE = "https://account-anz-demo.com.au";
    String TOKEN_END_POINT = "https://dev-udjfs1ppdebevxim.au.auth0.com/oauth/token";

    @SneakyThrows
    default String getAccessToken(String clientId, String clientSecret) {
        JSONObject requestParams = new JSONObject();
        requestParams.put("client_id", clientId);
        requestParams.put("client_secret", clientSecret);
        requestParams.put("grant_type", GRANT_TYPE);
        requestParams.put("audience", AUDIENCE);

        Response response =
                given().auth().preemptive().basic(clientId, clientSecret)
                .contentType(ContentType.JSON).body(requestParams.toString())
                .when()
                .post(TOKEN_END_POINT);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        String accessToken = jsonObject.get("access_token").toString();
        return accessToken;
    }

    default String getValidAccessToken() {
        return getAccessToken(CLIENT_ID, CLIENT_SECRET);
    }

    default String getValidAccessTokenWithoutRoles() {
        return getAccessToken(CLIENT_ID_WITHOUT_ROLES, CLIENT_SECRET_WITHOUT_ROLES);
    }
}

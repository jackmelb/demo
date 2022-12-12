package com.anz.demo.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AccountControllerIntegrationTest implements BaseControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void accountEndpoint_givenValidAccessToken_andValidAccountPermissions_shouldReturnAccounts() {
        given().auth()
                .oauth2(getValidAccessToken())
                .when().get("/accounts")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", is(2))
                .body("find { it.id == 3 }.accountNumber", equalTo(123))
                .body("find { it.id == 3 }.accountType", equalTo("SAVINGS"))
                .body("find { it.id == 3 }.currency", equalTo("AUD"))
                .body("find { it.id == 4 }.accountNumber", equalTo(456))
                .body("find { it.id == 4 }.accountType", equalTo("CURRENT"))
                .body("find { it.id == 4 }.currency", equalTo("AUD"));
    }

    @Test
    public void accountEndpoint_givenValidAccessToken_andValidAccountPermissions_shouldReturnOneAccountOnly() {
        given().auth()
                .oauth2(getValidAccessToken())
                .when().get("/accounts?page=0&size=1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", is(1))
                .body("find { it.id == 3 }.accountNumber", equalTo(123))
                .body("find { it.id == 3 }.accountType", equalTo("SAVINGS"))
                .body("find { it.id == 3 }.currency", equalTo("AUD"));
    }

    @Test
    public void accountEndpoint_givenValidAccessToken_andValidAccountPermissions_shouldReturn404_whenPageIsOverLimit() {
        given().auth()
                .oauth2(getValidAccessToken())
                .when().get("/accounts?page=10&size=10")
                .then()
                .statusCode(404);
    }

    @Test
    public void accountEndpoint_givenInvalidAccessToken_shouldReturn401() {
        given().auth()
                .oauth2("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkpiNmJtZmlxa012N2RQOXZ6RzlyayJ9.eyJpc3MiOiJodHRwczovL2Rldi11ZGpmczFwcGRlYmV2eGltLmF1LmF1dGgwLmNvbS8iLCJzdWIiOiJnb29nbGUtb2F1dGgyfDExNzUzMzI1NjQzMjg3NDg2NzIxMyIsImF1ZCI6WyJodHRwczovL2FjY291bnQtYW56LWRlbW8uY29tLmF1IiwiaHR0cHM6Ly9kZXYtdWRqZnMxcHBkZWJldnhpbS5hdS5hdXRoMC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNjcwNTYwNjA2LCJleHAiOjE2NzA2NDcwMDYsImF6cCI6IkdHQ25nRkhvZ1g3RXZhTUdHS1V1aTluQW5SN0k0YzllIiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCJ9.CJjcbumNKEVtgeufrUwYITbUW1l3xgn00L-QOnFwxv8G-hIONRYqzP1n1Ucbzprs-V_DXmFfCD5C9NreQGpRGdSd2D24qY8RGnunRTxXPAgH_QtoNTv_HDRf_RLzKc2I9XUMp5MqkdpWkawvempeoMxx4O7oI0E6-i5L2SeL6oI_WCz8x-Ha17kEw0YncL2gMtEbI17p0gvgKWnBiseYNZrnd0nVN55ZHc-B2OLzFqPXXwRqe4diT6oaRmp1WeQvnUoKwP138BThFi7enxj-DvPi5VDHsN8jhq9o3iadsZviLkowvOZ4J03qduXgw1YyEk-zAaZgmTTI23ANj83VbA")
                .when().get("/accounts?page=10&size=10")
                .then()
                .statusCode(401);
    }

    @Test
    public void accountEndpoint_givenValidAccessTokenWithoutRoles_shouldReturn403() {
        given().auth()
                .oauth2(getValidAccessTokenWithoutRoles())
                .when().get("/accounts")
                .then()
                .statusCode(403);
    }

    @Test
    public void accountTransactionEndpoint_givenValidAccessToken_andValidAccountPermissions_shouldReturnTransactions() {
        given().auth()
                .oauth2(getValidAccessToken())
                .when().get("/accounts/1/transactions?page=0&size=10")
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", is(10))
                .body("find { it.id == 1 }.accountNumber", equalTo(123))
                .body("find { it.id == 1 }.accountName", equalTo("Jack1"))
                .body("find { it.id == 1 }.currency", equalTo("AUD"))
                .body("find { it.id == 1 }.transactionType", equalTo("CREDIT"))
                .body("find { it.id == 1 }.narrative", equalTo("Test transaction"))
                .body("find { it.id == 2 }.accountNumber", equalTo(123))
                .body("find { it.id == 2 }.accountName", equalTo("Jack1"))
                .body("find { it.id == 2 }.currency", equalTo("AUD"))
                .body("find { it.id == 2 }.transactionType", equalTo("CREDIT"))
                .body("find { it.id == 2 }.narrative", equalTo("Test transaction"));
    }

    @Test
    public void accountTransactionEndpoint_givenValidAccessToken_andValidAccountPermissions_shouldReturn404_whenPageIsOverLimit() {
        given().auth()
                .oauth2(getValidAccessToken())
                .when().get("/accounts/1/transactions?page=10&size=10")
                .then()
                .statusCode(404);
    }

    @Test
    public void accountTransactionEndpoint_givenInvalidAccessToken_shouldReturn401() {
        given().auth()
                .oauth2("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkpiNmJtZmlxa012N2RQOXZ6RzlyayJ9.eyJpc3MiOiJodHRwczovL2Rldi11ZGpmczFwcGRlYmV2eGltLmF1LmF1dGgwLmNvbS8iLCJzdWIiOiJnb29nbGUtb2F1dGgyfDExNzUzMzI1NjQzMjg3NDg2NzIxMyIsImF1ZCI6WyJodHRwczovL2FjY291bnQtYW56LWRlbW8uY29tLmF1IiwiaHR0cHM6Ly9kZXYtdWRqZnMxcHBkZWJldnhpbS5hdS5hdXRoMC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNjcwNTYwNjA2LCJleHAiOjE2NzA2NDcwMDYsImF6cCI6IkdHQ25nRkhvZ1g3RXZhTUdHS1V1aTluQW5SN0k0YzllIiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCJ9.CJjcbumNKEVtgeufrUwYITbUW1l3xgn00L-QOnFwxv8G-hIONRYqzP1n1Ucbzprs-V_DXmFfCD5C9NreQGpRGdSd2D24qY8RGnunRTxXPAgH_QtoNTv_HDRf_RLzKc2I9XUMp5MqkdpWkawvempeoMxx4O7oI0E6-i5L2SeL6oI_WCz8x-Ha17kEw0YncL2gMtEbI17p0gvgKWnBiseYNZrnd0nVN55ZHc-B2OLzFqPXXwRqe4diT6oaRmp1WeQvnUoKwP138BThFi7enxj-DvPi5VDHsN8jhq9o3iadsZviLkowvOZ4J03qduXgw1YyEk-zAaZgmTTI23ANj83VbA")
                .when().get("/accounts/1/transactions")
                .then()
                .statusCode(401);
    }

    @Test
    public void accountTransactionEndpoint_givenValidAccessTokenWithoutRoles_shouldReturn403() {
        given().auth()
                .oauth2(getValidAccessTokenWithoutRoles())
                .when().get("/accounts/1/transactions")
                .then()
                .statusCode(403);
    }
}


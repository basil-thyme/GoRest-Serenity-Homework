package com.gorest.userinfo;

import com.gorest.constants.EndPoints;
import com.gorest.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class UserSteps {
    @Step("Creating user with name : {0}, gender : {1}, email : {2}, status : {3}")
    public ValidatableResponse createUser(String name, String gender, String email, String status) {
        UserPojo userPojo = UserPojo.getUserPojo(name, gender, email, status);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 61aafcb694ad2a184e92adcee5aae71f2288cf87d88930daf6a8b86ae21da215")
                .body(userPojo)
                .when()
                .post(EndPoints.CREATE_USER)
                .then();

    }

    @Step("Getting the Product information with productID : {0}")
    public HashMap<String, Object> getProductInfoById(int userId) {

        HashMap<String, Object> userMap = SerenityRest.given().log().all()
                .header("Authorization", "Bearer 61aafcb694ad2a184e92adcee5aae71f2288cf87d88930daf6a8b86ae21da215")
                .when()
                .pathParam("userId", userId)
                .get(EndPoints.GET_SINGLE_USER_BY_ID)
                .then().statusCode(200)
                .extract()
                .path("");
        return userMap;
    }

    @Step("Update the user with name : {0}, gender : {1}, email : {2}, status : {3}, userId : {4}")
    public ValidatableResponse updateUser(String name, String gender, String email, String status, int userId) {
        UserPojo userPojo = UserPojo.getUserPojo(name, gender, email, status);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 61aafcb694ad2a184e92adcee5aae71f2288cf87d88930daf6a8b86ae21da215")
                .pathParam("userId", userId)
                .body(userPojo)
                .when()
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then();

    }


    @Step("Delete the user with information userId : {0}")
    public ValidatableResponse deleteUser(int userId) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 61aafcb694ad2a184e92adcee5aae71f2288cf87d88930daf6a8b86ae21da215")
                .pathParam("userId", userId)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then();
    }


    @Step("Getting all the User information")
    public ValidatableResponse getAllUserInfo() {
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_USERS)
                .then();
    }


}

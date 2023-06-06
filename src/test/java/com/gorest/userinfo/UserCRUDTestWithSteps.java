package com.gorest.userinfo;

import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCRUDTestWithSteps extends TestBase {

    static int userId;
    static String name = "testprime" + TestUtils.getRandomValue();
    static String gender = "male";
    static String email = TestUtils.getRandomValue() + "testprime@gmail.com";
    static String status = "active";


    @Steps
    UserSteps userSteps;

    @Title("This will a create a new user")
    @Test
    public void test001() {
        ValidatableResponse response = userSteps.createUser(name,gender,email,status);
        response.log().all().statusCode(201);
        userId = response.log().all().extract().path("id");
        System.out.println(userId);

    }

    @Title("Verify if the user was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> studentMap = userSteps.getProductInfoById(userId);
        Assert.assertThat(studentMap, hasValue(userId));

    }
    @Title("Update the user information and verify the updated information")
    @Test
    public void test003() {
        name = name + "_updated";
        userSteps.updateUser(name,gender,email,status, userId).log().all().statusCode(200);
        HashMap<String, Object> userMap = userSteps.getProductInfoById(userId);
        Assert.assertThat(userMap, hasValue(userId));

    }
    @Title("Delete the user and verify if the user is deleted!")
    @Test
    public void test004() {
        userSteps.deleteUser(userId).statusCode(204);
        userSteps.getAllUserInfo().log().all().statusCode(200);
    }


}

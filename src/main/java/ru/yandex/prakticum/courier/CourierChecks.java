package ru.yandex.prakticum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.greaterThan;

public class CourierChecks {

    @Step("Courier created successfully")
    public void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", is(true));
    }

    @Step("Courier creation should fail with expected StatusCode and some message")
    public String creationFailed(ValidatableResponse response, int expectedStatusCode) {
        return response
                .assertThat()
                .statusCode(expectedStatusCode)
                .and()
                .body("message", not(blankOrNullString()))
                .extract()
                .path("message");
    }

    @Step("Courier loggedIn successfully, courierID returned")
    public int loggedInSuccessfully(ValidatableResponse loginResponse) {
        return loginResponse
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", greaterThan(0))
                .extract()
                .path("id");
    }

    @Step("Courier loggIn failed with message")
    public String loginFailed(ValidatableResponse response, int expectedStatusCode) {
        return response.assertThat()
                .statusCode(expectedStatusCode)
                .and()
                .body("message", not(blankOrNullString()))
                .extract()
                .path("message");
    }

    @Step("Compare error message to specification for NonUnique courier creation")
    public void compareMsgToSpecCreateNonUniqueCourier(String message){
        assertThat(message, is("Этот логин уже используется"));
    }

    @Step("Compare error message to specification.  Создание курьера без логина и пароля")
    public void compareMsgToSpecCreateCourierWithoutLoginPassword(String message){
        assertThat(message, is("Недостаточно данных для создания учетной записи"));
    }

    @Step("Compare error message to specification. Логин без логина, пароля")
    public void compareMsgToSpecLoginWithoutLoginPassword(String message){
        assertThat(message, is("Недостаточно данных для входа"));
    }

    @Step("Compare error message to specification. Логин с несуществующей парой логина, пароля")
    public void  compareMsgToSpecLoginFailsWithWrongLoginPassword(String message){
        assertThat(message, is("Учетная запись не найдена"));
    }

    @Step("Courier deleted successfully")
    public void deletedSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("ok", is(true));
    }
}

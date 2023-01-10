package ru.yandex.prakticum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class CourierChecks {
    @Step("Courier created successfully")
    public void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok", is(true));
    }
    @Step("Courier creation should fail for duplicate courier")
    public void creationFailedForNonUnique(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
    @Step("Courier creation should fail without Login, or without Password")
    public void creationFailedWithoutLoginPassword(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
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
    @Step("Courier login should fail without Login, or without Password")
    public void loginFailedWithInsufficientData(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Step("Courier login should fail with wrong Login, Password")
    public void loginFailedWithWrongData(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Step("Courier loggIn failed with message")
    public void loginFailed(ValidatableResponse response, int expectedStatusCode, String errorMessage) {
        response
                .assertThat()
                .statusCode(expectedStatusCode)
                .and()
                .body("message", equalTo(errorMessage));
    }
}

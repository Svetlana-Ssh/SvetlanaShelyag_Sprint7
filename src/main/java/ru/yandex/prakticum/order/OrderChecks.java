package ru.yandex.prakticum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.blankOrNullString;

public class OrderChecks {

    @Step("Order created successfully with StatusCode 201 and track in response graterThan 0")
    public void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", is(greaterThan(0)));
    }

    @Step("OrderList returned successfully with StatusCode 200 and not empty orders section in response")
    public void returnedSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(200)
                .and()
                .body("orders", not(blankOrNullString()));
    }
}

package ru.yandex.prakticum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierClient extends ru.yandex.prakticum.Client {
    public static final String ROOT = "/courier";

    @Step("Создать курьера")
    public ValidatableResponse create(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }
    @Step("Залогиниться с кредами курьера")
    public ValidatableResponse login(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(ROOT + "/login")
                .then().log().all();
    }
    @Step("Получить courierID курьера")
    public int getID(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(ROOT + "/login")
                .then().log().all()
                .extract()
                .path("id");
    }
    @Step("Удалить курьера по courierId")
    public ValidatableResponse delete(int courierId) {
        String json = String.format("{\"id\": \"%d\"}", courierId);
        return spec()
                .body(json)
                .when()
                .delete(ROOT + "/" + courierId)
                .then().log().all();
    }
}

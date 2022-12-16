package ru.yandex.prakticum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.prakticum.Client;

public class OrderClient extends Client {
    public static final String ROOT = "/orders";

    @Step("Создать заказ")
    public ValidatableResponse create(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Получить список заказов. Без параметров")
    public ValidatableResponse getOrderList() {
        return spec()
                .get(ROOT)
                .then().log().all();
    }

}

package ru.yandex.prakticum.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;


public class TestGetOrderListSuccess {
    private final OrderClient client = new OrderClient();
    private final OrderChecks check = new OrderChecks();

    @Test
    @DisplayName("Проверяем, что список заказов возвращается в response body. Запрос без параметров.")
    public void getOrderListWithoutParameters(){

        ValidatableResponse getOrderListResponse = client.getOrderList();
        check.returnedSuccessfully(getOrderListResponse);
    }
}

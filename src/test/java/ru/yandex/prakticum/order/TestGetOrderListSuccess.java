package ru.yandex.prakticum.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;


public class TestGetOrderListSuccess {

    private final OrderClient client = new OrderClient();
    private final OrderChecks check = new OrderChecks();

    @Test
    @DisplayName("Check order list is returned in response body. Get without parameters.")
    public void getOrderListWithoutParameters(){

        ValidatableResponse getOrderListResponse = client.getOrderList();
        check.returnedSuccessfully(getOrderListResponse);

    }
}

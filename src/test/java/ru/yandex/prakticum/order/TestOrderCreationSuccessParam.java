package ru.yandex.prakticum.order;

import com.google.gson.Gson;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;



@RunWith(Parameterized.class)
public class TestOrderCreationSuccessParam {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;


    private final OrderClient client = new OrderClient();
    private final OrderChecks check = new OrderChecks();


    public TestOrderCreationSuccessParam(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.metroStation = metroStation;
            this.phone = phone;
            this.rentTime = rentTime;
            this.deliveryDate = deliveryDate;
            this.comment = comment;
            this.color = color;
    }

    @Parameterized.Parameters(name = "Комментарий: {7}")
    //Набор тестовых данных. Проверяются разные комбинации параметров при создании заказа. Набор следует расширить, указав разные допустимые значения, не только для цвета. Уточнить спецификацию, какие необходимые.
    public static Object[][] checkCreateOrder() {
        return new Object[][]{
                {"SSh_Client1", "Uchiha", "Konoha, 142 apt.", "4","+7 800 355 35 35", 5,"2020-06-06","GRAY or BLACK", new String[] {"GRAY", "BLACK"}},
                {"SSh_Client2", "Uchiha", "Konoha, 142 apt.", "4","+7 800 355 35 35", 5,"2020-06-06","GRAY", new String[] {"GRAY"}},
                {"SSh_Client3", "Uchiha", "Konoha, 142 apt.", "4","+7 800 355 35 35", 5,"2020-06-06","BLACK", new String[] {"BLACK"}},
                {"SSh_Client4", "Uchiha", "Konoha, 142 apt.", "4","+7 800 355 35 35", 5,"2020-06-06","Color not specified", null},
                {"SSh_Client4", null, "Konoha, 142 apt.", "4","+7 800 355 35 35", 5,"2020-06-06","last name & color are not specified", null},
        };
    }
    @Test
    public void shouldCreateOrder() {

        Order order = new Order(firstName,lastName,address,metroStation,phone,rentTime,deliveryDate,comment,color);
        Gson gson = new Gson();
        System.out.println(gson.toJson(order));

        ValidatableResponse orderCreateResponse = client.create(order);
        check.createdSuccessfully(orderCreateResponse);

    }
}

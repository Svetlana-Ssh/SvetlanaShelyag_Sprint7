package ru.yandex.prakticum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class TestCourierCreation {
    private final CourierGenerator generator = new CourierGenerator();
    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    private int courierId;

    @After
    public void deleteCourier() {
        if (courierId > 0) {
            System.out.println("Удаляем тестового курьера id: " + courierId);
            client.delete(courierId);
        }
    }

    @Test
    @DisplayName("Успешное создание уникального курьера, status code and body")
    public void createNewUniqueCourier(){
        Courier courier = generator.random();
        ValidatableResponse creationResponse = client.create(courier);
        Credentials creds = Credentials.from(courier);
        courierId = client.getID(creds);
        check.createdSuccessfully(creationResponse);
    }

    @Test
    @DisplayName("Курьер с такими же параметрами не может быть создан второй раз. Check status code and message for non-unique courier")
    public void cannotCreateTheSameCourierTwice() {
        Courier courier = generator.random();
        client.create(courier);

        Credentials creds = Credentials.from(courier);
        courierId = client.getID(creds);

        ValidatableResponse creationResponseForNonUnique = client.create(courier);
        check.creationFailedForNonUnique(creationResponseForNonUnique);
    }

    @Test
    @DisplayName("Попытка создать пользователя с логином, который уже есть: возвращается ошибка. Password and FirstName отличаются")
    public void cannotCreateCourierWithExistingLogin() {
        Courier courier = generator.random();
        client.create(courier);

        Credentials creds = Credentials.from(courier);
        courierId = client.getID(creds);

        courier.setFirstName(courier.getFirstName()+"new");
        courier.setPassword(courier.getPassword()+"new");

        ValidatableResponse creationResponseForNonUnique = client.create(courier);
        check.creationFailedForNonUnique(creationResponseForNonUnique);
    }

    @Test
    @DisplayName("Попытка создания курьера без Login. Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    public void cannotCreateCourierWithoutLogin() {
        var courier = generator.random();
        courier.setLogin(null);

        ValidatableResponse response = client.create(courier);
        check.creationFailedWithoutLoginPassword(response);
    }

    @Test
    @DisplayName("Попытка создать курьера без Password. Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    public void cannotCreateCourierWithoutPassword() {
        var courier = generator.random();
        courier.setPassword(null);

        ValidatableResponse response = client.create(courier);
        check.creationFailedWithoutLoginPassword(response);
    }

    @Test
    @DisplayName("Попытка создать курьера без FirstName. Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    // По спецификации, firstName являтся обязательным. И поведение должно быть как и для login, password.
    // Но курьер создается, и по данной спецификации - это ошибка, тест фейлится.
    // В данном тесте не предусмотрено удаление курьера. Но т.к. тест отрабатывает not as expected, курьер создается и не удаляется.
    public void cannotCreateCourierWithoutFirstName() {
        var courier = generator.random();
        courier.setFirstName(null);

        ValidatableResponse response = client.create(courier);
        check.creationFailedWithoutLoginPassword(response);
    }
}






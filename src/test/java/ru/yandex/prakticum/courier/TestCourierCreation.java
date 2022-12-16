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
            ValidatableResponse response = client.delete(courierId);
            check.deletedSuccessfully(response);
        }
    }

    @Test
    @DisplayName("Check new unique courier creation, status code and body")
    public void createNewUniqueCourier(){

        Courier courier = generator.random();
        ValidatableResponse creationResponse = client.create(courier);
        check.createdSuccessfully(creationResponse);


        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Check second courier with the same login, password and firstName can't be created. Check status code and message for non-unique courier")
    public void cannotCreateTheSameCourierTwice() {
        Courier courier = generator.random();
        ValidatableResponse creationResponse = client.create(courier);
        check.createdSuccessfully(creationResponse);

        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);

        ValidatableResponse creationResponseForNonUnique = client.create(courier);
        String message = check.creationFailed(creationResponseForNonUnique, 409);
        check.compareMsgToSpecCreateNonUniqueCourier(message);
    }

    @Test
    @DisplayName("если создать пользователя с логином, который уже есть, возвращается ошибка. Password and FirstName отличаются")
    public void cannotCreateTheSameCourierWithExistingLogin() {
        Courier courier = generator.random();
        ValidatableResponse creationResponse = client.create(courier);
        check.createdSuccessfully(creationResponse);

        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);

        courier.setFirstName(courier.getFirstName()+"new");
        courier.setPassword(courier.getPassword()+"new");

        ValidatableResponse creationResponseForNonUnique = client.create(courier);
        String message = check.creationFailed(creationResponseForNonUnique, 409);
        check.compareMsgToSpecCreateNonUniqueCourier(message);
    }

    @Test
    @DisplayName("Login. Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    public void cannotCreateCourierWithoutLogin() {
        var courier = generator.random();
        courier.setLogin(null);

        ValidatableResponse response = client.create(courier);

        var message = check.creationFailed(response, 400);

        check.compareMsgToSpecCreateCourierWithoutLoginPassword(message);
    }

    @Test
    @DisplayName("Password. Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    public void cannotCreateCourierWithoutPassword() {
        var courier = generator.random();
        courier.setPassword(null);

        ValidatableResponse response = client.create(courier);

        var message = check.creationFailed(response, 400);

        check.compareMsgToSpecCreateCourierWithoutLoginPassword(message);
    }

    @Test
    @DisplayName("FirstName. Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    // Требуется уточнить спецификацию. Является ли поле firstName обязательным? Какое expected behavior на его отсутстввие?
    // После этого поправить или тест или код.
    //? В данном тесте не предусмотрено удаление курьера. Но т.к. тест отрабатывает not as expected, курьер создается и не удаляется. Как быть в таких случаях?
    public void cannotCreateCourierWithoutFirstName() {
        var courier = generator.random();
        courier.setFirstName(null);

        ValidatableResponse response = client.create(courier);

        var message = check.creationFailed(response, 400);

        check.compareMsgToSpecCreateCourierWithoutLoginPassword(message);
    }

}






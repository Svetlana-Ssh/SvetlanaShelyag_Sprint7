package ru.yandex.prakticum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class TestCourierLogin {

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
    @DisplayName("Курьер может авторизоваться. При заданных Login & Password - проверяется в TestCourierCreation.createNewUniqueCourier()")
    public void CourierCanLogin(){}

    @Test
    @DisplayName("Повторная авторизация с существующими Login и Password проходит так же успешно и возвращает id.")
    public void CourierCanLoginSecondTime(){
        Courier courier = generator.random();
        client.create(courier);

        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);

        ValidatableResponse secondLoginResponse = client.login(creds);
        assertEquals(courierId, check.loggedInSuccessfully(secondLoginResponse));
    }

    @Test
    @DisplayName("Для авторизации нужно передать все обязательные поля. Логин с несущетсвующими Login and Password")
    public void CannotLoginWithNotExistingLoginAndPassword(){
        Courier courier = generator.random();
        client.create(courier);

        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);

        creds.setLogin(creds.getLogin() + "NotExist");
        creds.setPassword(creds.getPassword() + "NotExist");

        loginResponse = client.login(creds);
        var message = check.loginFailed(loginResponse, 404);
        check.compareMsgToSpecLoginFailsWithWrongLoginPassword(message);
    }

    @Test
    @DisplayName("Для авторизации нужно передать все обязательные поля. Не проходит логин без Login")
    public void CannotLoginWithoutLogin(){
        Courier courier = generator.random();
        client.create(courier);

        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);

        creds.setLogin(null);
        ValidatableResponse loginResponseLoginNULL = client.login(creds);
        var message = check.loginFailed(loginResponseLoginNULL, 400);
        check.compareMsgToSpecLoginWithoutLoginPassword(message);
    }

    //  Остальные тесты на обработку ошибок для неверных пар Логин, Пароль вынесла в параметризованный тест: TestCourierLoginErrHndlrParam

}

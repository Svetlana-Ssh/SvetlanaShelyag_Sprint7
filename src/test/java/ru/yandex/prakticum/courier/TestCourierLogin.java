package ru.yandex.prakticum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class TestCourierLogin {
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
    @DisplayName("Созданный курьер может успешно авторизоваться.")
    public void CourierCanLogin(){
        Courier courier = generator.random();
        client.create(courier);

        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Для авторизации нужно передать все обязательные поля. Логин с несущетсвующими Login and Password")
    public void CannotLoginWithNotExistingLoginAndPassword(){
        Courier courier = generator.random();
        client.create(courier);
        Credentials creds = Credentials.from(courier);
        courierId = client.getID(creds);

        creds.setLogin(creds.getLogin() + "NotExist");
        creds.setPassword(creds.getPassword() + "NotExist");
        ValidatableResponse loginResponse = client.login(creds);
        check.loginFailedWithWrongData(loginResponse);
    }

    @Test
    @DisplayName("Для авторизации нужно передать все обязательные поля. Не проходит логин без Login")
    public void CannotLoginWithoutLogin(){
        Courier courier = generator.random();
        client.create(courier);
        Credentials creds = Credentials.from(courier);
        courierId = client.getID(creds);

        creds.setLogin(null);
        ValidatableResponse loginResponseLoginNULL = client.login(creds);
        check.loginFailedWithInsufficientData(loginResponseLoginNULL);
    }
}
//  Остальные тесты на обработку ошибок для неверных пар Логин, Пароль вынесла в параметризованный тест: TestCourierLoginErrHndlrParam
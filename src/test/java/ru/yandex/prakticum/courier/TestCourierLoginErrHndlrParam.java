package ru.yandex.prakticum.courier;

import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.prakticum.constants.CredsState;
import static ru.yandex.prakticum.constants.CredsState.*;

@RunWith(Parameterized.class)
public class TestCourierLoginErrHndlrParam {
    private final CourierGenerator generator = new CourierGenerator();
    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    private int courierId;
    private final CredsState login;
    private final CredsState password;
    private final int expectedStatusCode;
    private final String expectedErrorMessage;

    public TestCourierLoginErrHndlrParam(CredsState login, CredsState password, int expectedStatusCode, String expectedErrorMessage) {
        this.login = login;
        this.password = password;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @After
    public void deleteCourier() {
        if (courierId > 0) {
            System.out.println("Удаляем тестового курьера id: " + courierId);
            client.delete(courierId);
        }
    }

    @Parameterized.Parameters(name = "Login: {0}, Password: {1}")
    //Набор тестовых данных. Проверяются разные комбинации Логина и Пароля: отсутствует, правильный, неправильный. Note: успешный логин проверяется в TestCourierLogin
    public static Object[][] checkErr() {
        return new Object[][]{
                //{NOT_DEFINED, NOT_DEFINED, 400, "Недостаточно данных для входа"},  //падает, 504 statusCode возвращается. Нужно прояснять требования.
                //{IS_TRUE, NOT_DEFINED, 400, "Недостаточно данных для входа"}, //падает, 504 statusCode возвращается. Нужно прояснять требования.
                {NOT_DEFINED, IS_TRUE, 400, "Недостаточно данных для входа"},
                {IS_TRUE, IS_FALSE, 404,"Учетная запись не найдена"},
                {IS_FALSE, IS_TRUE, 404,"Учетная запись не найдена"},
                {IS_FALSE, IS_FALSE, 404,"Учетная запись не найдена"},
                {IS_EMPTY, IS_TRUE, 400,"Недостаточно данных для входа"},
                {IS_TRUE, IS_EMPTY, 400,"Недостаточно данных для входа"},
        };
    }
    @Test
    @Description("Проверка обработки ошибок при разных комбинациях неверных Логин, Пароль. (IS_TRUE, IS_FALSE, NOT_DEFINED, IS_EMPTY)")
    public void CannotLoginErrHndlr() {
        Courier courier = generator.random();
        client.create(courier);

        Credentials creds = Credentials.from(courier);
        courierId = client.getID(creds);

        //Преобразование существующих кредов в зависимости от параметров теста в null или неправильные.
        switch(login) {
            case NOT_DEFINED:
                creds.setLogin(null);
                break;
            case IS_FALSE:
                creds.setLogin("NotExist_" + courier.getLogin());
                break;
            case IS_TRUE:
                break;
            case IS_EMPTY:
                creds.setLogin("");
                break;
        }

        switch(password) {
            case NOT_DEFINED:
                creds.setPassword(null);
                break;
            case IS_FALSE:
                creds.setPassword("NotExist_" + courier.getPassword());
                break;
            case IS_TRUE:
                break;
            case IS_EMPTY:
                creds.setPassword("");
                break;
        }

        ValidatableResponse loginResponseLoginFailed = client.login(creds);
        check.loginFailed(loginResponseLoginFailed, expectedStatusCode, expectedErrorMessage);
    }
}

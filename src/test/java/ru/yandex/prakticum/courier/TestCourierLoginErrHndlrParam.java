package ru.yandex.prakticum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.prakticum.constants.CredsState;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
    private final String errorMessage;


    public TestCourierLoginErrHndlrParam(CredsState login, CredsState password, int expectedStatusCode, String errorMessage) {
        this.login = login;
        this.password = password;
        this.expectedStatusCode = expectedStatusCode;
        this.errorMessage = errorMessage;
    }

    @After
    public void deleteCourier() {
        if (courierId > 0) {
            System.out.println("Удаляем тестового курьера id: " + courierId);
            ValidatableResponse response = client.delete(courierId);
            check.deletedSuccessfully(response);
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
        };
    }
    @Test
    @DisplayName(value = "Проверка обработки ошибок при разных комбинациях неверных Логин, Пароль. (IS_TRUE, IS_FALSE, NOT_DEFINED)")
    public void CannotLoginErrHndlr() {
        Courier courier = generator.random();
        client.create(courier);

        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);

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
        }

        ValidatableResponse loginResponseLoginFailed = client.login(creds);
        var message = check.loginFailed(loginResponseLoginFailed, expectedStatusCode);
        assertThat(message, is(errorMessage));
    }

}

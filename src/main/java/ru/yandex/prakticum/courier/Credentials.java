package ru.yandex.prakticum.courier;

public class Credentials {

    private String login;
    private String password;

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /* ?Есть ли принципиальный момент в создании кредов из курьера через метод, а не через конструктор?

    public Credentials(Courier courier) {
        this.login = courier.getLogin();
        this.password = courier.getPassword();
    }
    */

    public static Credentials from(Courier courier) {
        return new Credentials(courier.getLogin(), courier.getPassword());
    }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Credentials{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

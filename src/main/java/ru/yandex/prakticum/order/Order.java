package ru.yandex.prakticum.order;

    /* Specification at https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CreateOrder
    firstName	string  Имя заказчика, записывается в поле firstName таблицы Orders
    lastName	string  Фамилия заказчика, записывается в поле lastName таблицы Orders
    address	string  Адрес заказчика, записывается в поле adress таблицы Orders
    metroStation	string  Ближайшая к заказчику станция метро, записывается в поле metroStation таблицы Orders
    phone	string  Телефон заказчика, записывается в поле phone таблицы Orders
    rentTime	number  Количество дней аренды, записывается в поле rentTime таблицы Orders
    deliveryDate	string  Дата доставки, записывается в поле deliveryDate таблицы Orders
    comment	string  Комментарий от заказчика, записывается в поле comment таблицы Orders
    color   (optional)	string[]    Предпочитаемые цвета, записываются в поле color таблицы Orders
     */

import java.util.Arrays;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public Order() {}

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
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


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(short rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    @Override
    public String toString() {

        return "Order{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                "metroStation='" + metroStation + '\'' +
                "phone='" + phone + '\'' +
                "rentTime='" + rentTime + '\'' +
                "deliveryDate='" + deliveryDate + '\'' +
                "comment='" + comment + '\'' +
                "color='" + Arrays.toString(color) + '\'' +
                '}';
    }

}

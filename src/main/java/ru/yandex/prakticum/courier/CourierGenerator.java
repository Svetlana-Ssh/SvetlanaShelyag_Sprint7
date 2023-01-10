package ru.yandex.prakticum.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public Courier generic() {
        return new Courier("Ssh_Test_Courier", "123qweASD", "FN Ssh_Test_Courier");
    }

    public Courier random() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10));
    }
}

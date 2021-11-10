package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class RegistrationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldLogIn() {
        RequestData validUser = DataGenerator.generateUser("active");
        $("[name=login]").setValue(validUser.getLogin());
        $("[name=password]").setValue(validUser.getPassword());
        $(byClassName("button__text")).click();
        $(byCssSelector("h2")).shouldHave(Condition.exactText("Личный кабинет"), Duration.ofSeconds(5));
    }

    @Test
    void shouldLogInBlockedUser() {
        RequestData validUser = DataGenerator.generateUser("blocked");
        $("[name=login]").setValue(validUser.getLogin());
        $("[name=password]").setValue(validUser.getPassword());
        $(byClassName("button__text")).click();
        $("[data-test-id=error-notification]").shouldHave(Condition.exactText("Ошибка Ошибка! Пользователь заблокирован"), Duration.ofSeconds(5));
    }

    @Test
    void shouldLogInWithWrongPassword() {
        RequestData invalidUser = DataGenerator.generateUserWithWrongPassword();
        $("[name=login]").setValue(invalidUser.getLogin());
        $("[name=password]").setValue(invalidUser.getPassword());
        $(byClassName("button__text")).click();
        $("[data-test-id=error-notification]").shouldHave(Condition.exactText("Ошибка Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(5));
    }

    @Test
    void shouldLogInWithWrongLogin() {
        RequestData invalidUser = DataGenerator.generateUserWithWrongLogin();
        $("[name=login]").setValue(invalidUser.getLogin());
        $("[name=password]").setValue(invalidUser.getPassword());
        $(byClassName("button__text")).click();
        $("[data-test-id=error-notification]").shouldHave(Condition.exactText("Ошибка Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(5));
    }

    @Test
    void shouldLogInNotRegisteredUser() {
        RequestData invalidUser = DataGenerator.generateNotRegisteredUser();
        $("[name=login]").setValue(invalidUser.getLogin());
        $("[name=password]").setValue(invalidUser.getPassword());
        $(byClassName("button__text")).click();
        $("[data-test-id=error-notification]").shouldHave(Condition.exactText("Ошибка Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(5));
    }

}
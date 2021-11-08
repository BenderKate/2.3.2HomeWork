package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class RegistrationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldLogIn() {
        RegistrationInfo.RequestData requestData = RegistrationInfo.RequestData.generateUser();
        $("[data-test-id='login'] [name='login']").setValue(requestData.getLogin());
        $("[data-test-id='password'] [name='password']").setValue(requestData.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
    }

}
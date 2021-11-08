package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.Locale;
import static io.restassured.RestAssured.given;

public class RegistrationInfo {

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class RequestData {

        private String login;
        private String password;
        private String status;


        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();


        public static void register(RequestData requestData) {
            // сам запрос
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(requestData) // передаём в теле объект, который будет преобразован в JSON
                    .when() // "когда"
                    .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
        }


        public static RequestData generateUser() {
            Faker faker = new Faker(new Locale("en"));
            String login = faker.name().username();
            String password = faker.internet().password();
            register(new RequestData(login, password, "active"));
            return new RequestData(login, password, "active");
        }

        public static RequestData generateBlockedUser() {
            Faker faker = new Faker(new Locale("en"));
            String login = faker.name().username();
            String password = faker.internet().password();
            register(new RequestData(login, password, "blocked"));
            return new RequestData(login, password, "blocked");
        }

        public RequestData generateUserWithWrongPassword() {
            Faker faker = new Faker(new Locale("en"));
            String login = faker.name().username();
            String status = "active";
            register(new RequestData(login, "12345", status));
            return new RequestData(login, "12345", status);
        }

        public static RequestData generateUserWithWrongLogin() {
            Faker faker = new Faker(new Locale("en"));
            String password = faker.internet().password();
            String status = "active";
            register(new RequestData("kate", password, status));
            return new RequestData("kate", password, status);

        }

        public static RequestData generateNotRegisteredUser() {
            Faker faker = new Faker(new Locale("en"));
            String login = faker.name().username();
            String password = faker.internet().password();
            RequestData notRegistredUser = new RequestData(login, password, "active");
            return notRegistredUser;
        }
    }
}

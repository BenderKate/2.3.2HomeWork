package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        private static Faker faker = new Faker(new Locale("en"));

        public static void register(RequestData requestData) {
            given()
                    .spec(requestSpec)
                    .body(requestData)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static RequestData generateUser(String status) {
            String login = faker.name().username();
            String password = faker.internet().password();
            register(new RequestData(login, password, status));
            return new RequestData(login, password, status);
        }

        public static RequestData generateUserWithWrongPassword() {
            String login = faker.name().username();
            String status = "active";
            register(new RequestData(login, "12345", status));
            return new RequestData(login, "1234", status);
        }

        public static RequestData generateUserWithWrongLogin() {
            String password = faker.internet().password();
            String status = "active";
            register(new RequestData("kate", password, status));
            return new RequestData("катя", password, status);

        }

        public static RequestData generateNotRegisteredUser() {
            String login = faker.name().username();
            String password = faker.internet().password();
            RequestData notRegistredUser = new RequestData(login, password, "active");
            return notRegistredUser;
        }
    }

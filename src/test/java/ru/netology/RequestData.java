package ru.netology;

import lombok.Value;

@Value
public class RequestData {
        private String login;
        private String password;
        private String status;
    }


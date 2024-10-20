package com.autocat.humusontest.handler;

public class InvalidOrderException extends RuntimeException{

        public InvalidOrderException(String message) {
            super(message);
        }
}

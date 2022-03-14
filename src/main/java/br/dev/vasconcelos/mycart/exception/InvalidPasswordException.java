package br.dev.vasconcelos.mycart.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Invalid password.");
    }
}

package br.dev.vasconcelos.mycart.exception;

public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException(String message) {
        super(message);
    }
}

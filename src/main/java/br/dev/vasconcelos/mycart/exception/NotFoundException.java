package br.dev.vasconcelos.mycart.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("User not found!");
    }
}

package org.example.exceptions;

public class InvalidCodeException extends InventoryException {

    public InvalidCodeException(String code) {
        super("Mã không hợp lệ: " + code);
    }
}

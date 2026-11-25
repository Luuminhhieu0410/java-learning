package org.example.modernsystax;


public final class InvalidCodeException extends InventoryException {
    private final String code;

    public InvalidCodeException(String code) {
        super("Mã không hợp lệ: " + code);
        this.code = code;
    }

    public String code() {
        return code;
    }
}

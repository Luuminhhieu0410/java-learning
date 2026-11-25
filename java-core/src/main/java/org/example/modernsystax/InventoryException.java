package org.example.modernsystax;

public sealed class InventoryException extends Exception permits OutOfStockException , InvalidCodeException{
     public InventoryException(String message){
        super(message);
    }
    public InventoryException(String message, Throwable cause){
         super(message,cause);
    }
}

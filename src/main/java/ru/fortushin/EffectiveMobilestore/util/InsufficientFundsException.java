package ru.fortushin.EffectiveMobilestore.util;

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String message){
        super(message);
    }
}

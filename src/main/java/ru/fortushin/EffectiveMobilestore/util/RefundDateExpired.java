package ru.fortushin.EffectiveMobilestore.util;

public class RefundDateExpired extends Exception{
    public RefundDateExpired(String message){
        super(message);
    }
}

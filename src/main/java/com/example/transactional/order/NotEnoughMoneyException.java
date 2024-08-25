package com.example.transactional.order;

/**
 * 체크예외, 롤백안할꺼임
 */
public class NotEnoughMoneyException extends Exception{

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}

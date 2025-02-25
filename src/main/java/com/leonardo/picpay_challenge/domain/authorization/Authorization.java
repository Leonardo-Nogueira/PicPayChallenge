package com.leonardo.picpay_challenge.domain.authorization;

public record Authorization(String message) {

    public boolean isAuthorized(){
        return message.equals("Autorizado");
    }
}

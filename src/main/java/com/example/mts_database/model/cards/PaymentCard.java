package com.example.mts_database.model.cards;


import java.math.BigDecimal;

public interface PaymentCard {
    public boolean checkCardCvv(String cardCVV);
    public BigDecimal subtractAmount(int subtract);
    public void addAmount(int add);
}

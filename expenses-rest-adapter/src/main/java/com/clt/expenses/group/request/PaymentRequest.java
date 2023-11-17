package com.clt.expenses.group.request;

import com.clt.expenses.expense.request.MoneyDto;

public class PaymentRequest {
    MoneyDto amountToPay;
    String payedUserId;

    public PaymentRequest() {
    }

    public PaymentRequest(MoneyDto amountToPay, String payedUserId) {
        this.amountToPay = amountToPay;
        this.payedUserId = payedUserId;
    }

    public MoneyDto getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(MoneyDto amountToPay) {
        this.amountToPay = amountToPay;
    }

    public String getPayedUserId() {
        return payedUserId;
    }

    public void setPayedUserId(String payedUserId) {
        this.payedUserId = payedUserId;
    }
}

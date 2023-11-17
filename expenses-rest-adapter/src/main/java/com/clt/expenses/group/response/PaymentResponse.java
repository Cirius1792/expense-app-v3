package com.clt.expenses.group.response;

import com.clt.expenses.expense.request.MoneyDto;

public class PaymentResponse {
    String paymentId;
    MoneyDto paidAmount;

    public PaymentResponse() {
    }

    public PaymentResponse(String paymentId, MoneyDto paidAmount) {
        this.paymentId = paymentId;
        this.paidAmount = paidAmount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public MoneyDto getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(MoneyDto paidAmount) {
        this.paidAmount = paidAmount;
    }
}

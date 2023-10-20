package com.clt.expenses.expense.request;

import java.math.BigDecimal;

public class MoneyDto {

  public enum CurrencyEnum {
    EUR
  }

  private BigDecimal amount;
  private CurrencyEnum currency;

  public MoneyDto() {}

  public MoneyDto(BigDecimal amount, CurrencyEnum currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public CurrencyEnum getCurrency() {
    return currency;
  }

  public void setCurrency(CurrencyEnum currency) {
    this.currency = currency;
  }
}

package com.clt.expenses.ledger.response;

import java.math.BigDecimal;

public class ExpenseSplitDto {
  String id;

  String expense;

  String groupId;

  BigDecimal dueAmount;
  String currency;

  String debtor;

  String creditor;

  public ExpenseSplitDto(
      String id,
      String expense,
      String groupId,
      BigDecimal dueAmount,
      String currency,
      String debtor,
      String creditor) {

    this.id = id;
    this.expense = expense;
    this.groupId = groupId;
    this.dueAmount = dueAmount;
    this.currency = currency;
    this.debtor = debtor;
    this.creditor = creditor;
  }

  public ExpenseSplitDto() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getExpense() {
    return expense;
  }

  public void setExpense(String expense) {
    this.expense = expense;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public BigDecimal getDueAmount() {
    return dueAmount;
  }

  public void setDueAmount(BigDecimal dueAmount) {
    this.dueAmount = dueAmount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getDebtor() {
    return debtor;
  }

  public void setDebtor(String debtor) {
    this.debtor = debtor;
  }

  public String getCreditor() {
    return creditor;
  }

  public void setCreditor(String creditor) {
    this.creditor = creditor;
  }
}

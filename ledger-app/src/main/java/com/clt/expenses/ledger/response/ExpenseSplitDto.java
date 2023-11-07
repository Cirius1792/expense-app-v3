package clt.com.expense.ledger.response;

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
}

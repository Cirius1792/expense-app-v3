package com.clt.expenses.expense;

import com.clt.expenses.expense.request.MoneyDto;
import com.clt.expenses.expense.response.ExpenseResponse;
import com.clt.expenses.user.PersonMapper;
import com.clt.domain.view.ExpenseAggregate;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {
  private final PersonMapper personMapper;

  public ExpenseMapper(PersonMapper personMapper) {
    this.personMapper = personMapper;
  }

  public ExpenseResponse toDto(ExpenseAggregate expense) {
    ExpenseResponse expenseResponse = new ExpenseResponse();
    expenseResponse.setId(expense.getId());
    expenseResponse.setDescription(expense.getDescription());
    expenseResponse.setGroupId(expense.getGroupId());
    expenseResponse.setAmount(
        new MoneyDto(expense.getAmount().getAmount(), MoneyDto.CurrencyEnum.EUR));
    expenseResponse.setOwner(personMapper.toDto(expense.getOwner()));
    return expenseResponse;
  }
}

package com.clt.expenses.expense;

import com.clt.domain.expense.Expense;
import com.clt.expenses.expense.response.ExpenseResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ExpenseMapper {
  public ExpenseResponse toDto(Mono<Expense> expenseMono) {
    return null;
  }
}
